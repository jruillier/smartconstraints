package eu.aronnax.smartconstraints.domain;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;

import eu.aronnax.smartconstraints.domainport.SourceAnnotDto;
import eu.aronnax.smartconstraints.domainport.SourceClassDto;
import eu.aronnax.smartconstraints.domainport.SourceParamDto;
import eu.aronnax.smartconstraints.domainport.SourcePropertyDto;
import eu.aronnax.smartconstraints.domainport.StringUtilsPort;
import jakarta.inject.Inject;

class BuildSourceHelper {

    private static final Logger LOGGER = Logger.getLogger(BuildSourceHelper.class.getName());

    private final StringUtilsPort stringUtils;
    private final ConstraintsHelper constraintsHelper;

    private final AnnotElemSourceParamsBuilder annotElemSourceParamsBuilder;

    @Inject
    BuildSourceHelper(final StringUtilsPort stringUtils, ConstraintsHelper constraintsHelper, AnnotElemSourceParamsBuilder annotElemSourceParamsBuilder) {
        this.stringUtils = stringUtils;
        this.constraintsHelper = constraintsHelper;
        this.annotElemSourceParamsBuilder = annotElemSourceParamsBuilder;
    }

    Map.Entry<CharSequence, SourceClassDto> buildSourceDto(final Map.Entry<String, List<Element>> entry) {

        String classQualifiedName = entry.getKey() + "Constraints";
        String classSimpleName = NamingUtil.extractSimpleName(classQualifiedName);
        String packageName = NamingUtil.extractPackageName(classQualifiedName);

        MapEntryDto<SourceClassDto> result = new MapEntryDto<>(
                classQualifiedName,
                new SourceClassDto(
                        packageName,
                        classQualifiedName,
                        classSimpleName,
                        this.getSourceProperties(entry)));
        LOGGER.info(result.getValue().toString());
        return result;
    }

    private List<SourcePropertyDto> getSourceProperties(Map.Entry<String, List<Element>> entry) {
        return entry.getValue().stream()
                .map(propertyElem -> new SourcePropertyDto(
                        this.stringUtils.capitalize(
                                propertyElem.getSimpleName().toString()),
                        this.getSourceAnnots(propertyElem)))
                .collect(Collectors.toList());
    }

    private List<SourceAnnotDto> getSourceAnnots(Element propertyElem) {
        return this.constraintsHelper
                .getConstraintClasses()
                .map(propertyElem::getAnnotation)
                .filter(Objects::nonNull)
                .map(annotElmt -> new SourceAnnotDto(
                        annotElmt.annotationType().getCanonicalName(),
                        annotElmt.annotationType().getSimpleName(),
                        this.buildAnnotParams(annotElmt)))
                .collect(Collectors.toList());
    }

    private List<SourceParamDto> buildAnnotParams(Annotation annotElmt) {
        return this.annotElemSourceParamsBuilder.process(annotElmt);
    }
}
