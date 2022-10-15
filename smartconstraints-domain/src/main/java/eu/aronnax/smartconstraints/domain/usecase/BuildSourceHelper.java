package eu.aronnax.smartconstraints.domain.usecase;

import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetClassDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetMetaAnnotDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotParamDto;
import eu.aronnax.smartconstraints.domain.port.stringutils.StringUtilsPort;
import jakarta.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;

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

    Map.Entry<CharSequence, TargetClassDto> buildSourceDto(final Map.Entry<String, List<Element>> entry) {

        String classQualifiedName = entry.getKey() + "_Constraints";
        String classSimpleName = NamingUtil.extractSimpleName(classQualifiedName);
        String packageName = NamingUtil.extractPackageName(classQualifiedName);

        MapEntryDto<TargetClassDto> result = new MapEntryDto<>(
                classQualifiedName,
                new TargetClassDto(packageName, classQualifiedName, classSimpleName, this.getSourceProperties(entry)));
        LOGGER.info(result.getValue().toString());
        return result;
    }

    private List<TargetMetaAnnotDto> getSourceProperties(Map.Entry<String, List<Element>> entry) {
        return entry.getValue().stream()
                .map(propertyElem -> new TargetMetaAnnotDto(
                        this.stringUtils.capitalize(propertyElem.getSimpleName().toString()),
                        this.getSourceAnnots(propertyElem)))
                .collect(Collectors.toList());
    }

    private List<TargetAnnotDto> getSourceAnnots(Element propertyElem) {
        return this.constraintsHelper
                .getConstraintClasses()
                .map(propertyElem::getAnnotation)
                .filter(Objects::nonNull)
                .map(annotElmt -> new TargetAnnotDto(
                        annotElmt.annotationType().getCanonicalName(),
                        annotElmt.annotationType().getSimpleName(),
                        this.buildAnnotParams(annotElmt)))
                .collect(Collectors.toList());
    }

    private List<TargetAnnotParamDto> buildAnnotParams(Annotation annotElmt) {
        return this.annotElemSourceParamsBuilder.process(annotElmt);
    }
}
