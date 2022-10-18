package eu.aronnax.smartconstraints.domain.usecase;

import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotParamDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetClassDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetMetaAnnotDto;
import eu.aronnax.smartconstraints.domain.port.stringutils.StringUtilsPort;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class BuildSourceHelper {

    private static final Logger LOGGER = Logger.getLogger(BuildSourceHelper.class.getName());

    private final StringUtilsPort stringUtils;
    private final ConstraintsHelper constraintsHelper;
    private final AnnotElemSourceParamsBuilder annotElemSourceParamsBuilder;

    @Inject
    BuildSourceHelper(
            final StringUtilsPort stringUtils,
            final ConstraintsHelper constraintsHelper,
            final AnnotElemSourceParamsBuilder annotElemSourceParamsBuilder) {
        this.stringUtils = stringUtils;
        this.constraintsHelper = constraintsHelper;
        this.annotElemSourceParamsBuilder = annotElemSourceParamsBuilder;
    }

    Map.Entry<CharSequence, TargetClassDto> buildSourceDto(final SourceEntityDto sourceEntity) {

        String classQualifiedName = sourceEntity.classQualifiedName() + "_Constraints";
        String classSimpleName = NamingUtil.extractSimpleName(classQualifiedName);
        String packageName = NamingUtil.extractPackageName(classQualifiedName);

        MapEntryDto<TargetClassDto> result = new MapEntryDto<>(
                classQualifiedName,
                new TargetClassDto(
                        packageName, classQualifiedName, classSimpleName, this.getSourceProperties(sourceEntity)));
        LOGGER.info(result.getValue().toString());
        return result;
    }

    private List<TargetMetaAnnotDto> getSourceProperties(SourceEntityDto entry) {
        return entry.sourceProperties().stream()
                .map(propertyElem -> new TargetMetaAnnotDto(
                        this.stringUtils.capitalize(propertyElem.propertyName()),
                        this.getSourceAnnots(propertyElem.annots())))
                .collect(Collectors.toList());
    }

    private List<TargetAnnotDto> getSourceAnnots(List<SourceAnnotDto> propertyAnnots) {
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
