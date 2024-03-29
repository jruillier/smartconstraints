package eu.aronnax.smartconstraints.domain.usecase;

import eu.aronnax.smartconstraints.domain.port.KeyValueDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.*;
import eu.aronnax.smartconstraints.domain.port.stringutils.StringUtilsPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
class BuildTargetHelper {

    private static final Logger LOGGER = Logger.getLogger(BuildTargetHelper.class.getName());

    private final StringUtilsPort stringUtils;
    private final TargetConstraintHelperPort targetConstraintHelperPort;

    @Inject
    BuildTargetHelper(final StringUtilsPort stringUtils, final TargetConstraintHelperPort targetConstraintHelperPort) {
        this.stringUtils = stringUtils;
        this.targetConstraintHelperPort = targetConstraintHelperPort;
    }

    KeyValueDto<TargetClassDto> buildTargetClass(final SourceEntityDto sourceEntity) {

        String packageName = sourceEntity.targetPackage();
        String classSimpleName = this.buildTargetClassSimpleName(sourceEntity);
        String classQualifiedName = packageName + "." + classSimpleName;

        KeyValueDto<TargetClassDto> result = new KeyValueDto<>(
                classQualifiedName,
                new TargetClassDto(
                        packageName,
                        classQualifiedName,
                        classSimpleName,
                        this.buildTargetComposedAnnots(sourceEntity)));
        LOGGER.info(result.value().toString());
        return result;
    }

    private String buildTargetClassSimpleName(SourceEntityDto sourceEntity) {
        String simpleName = NamingUtil.extractSimpleName(sourceEntity.classQualifiedName());
        simpleName = simpleName.endsWith("Entity") && simpleName.length() > 6
                ? simpleName.substring(0, simpleName.length() - 6)
                : simpleName;
        return simpleName + "_Constraints";
    }

    private List<TargetComposedAnnotDto> buildTargetComposedAnnots(SourceEntityDto entry) {
        return entry.sourceProperties().stream()
                .map(propertyElem -> new TargetComposedAnnotDto(
                        "Valid" + this.stringUtils.capitalize(propertyElem.propertyName()),
                        this.targetConstraintHelperPort.getConstraintAnnot(),
                        this.targetConstraintHelperPort.getPayload(),
                        this.buildTargetAnnots(propertyElem.annots())))
                .collect(Collectors.toList());
    }

    private List<TargetAnnotDto> buildTargetAnnots(List<SourceAnnotDto> propertyAnnots) {
        return propertyAnnots.stream()
                .map(annotElmt -> new TargetAnnotDto(
                        annotElmt.name(), annotElmt.simpleName(), this.buildAnnotParams(annotElmt.params())))
                .collect(Collectors.toList());
    }

    private List<TargetAnnotParamDto> buildAnnotParams(List<SourceAnnotParamDto> srcParams) {
        return srcParams.stream()
                .map(srcParam -> new TargetAnnotParamDto(
                        srcParam.name(),
                        !(srcParam.value() instanceof String) ? srcParam.value() : null,
                        srcParam.value() instanceof String ? (String) srcParam.value() : null))
                .toList();
    }
}
