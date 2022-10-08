package eu.aronnax.smartconstraints.domain;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import eu.aronnax.smartconstraints.domainport.ImmutableSourceAnnotDto;
import eu.aronnax.smartconstraints.domainport.ImmutableSourceClassDto;
import eu.aronnax.smartconstraints.domainport.ImmutableSourceParamDto;
import eu.aronnax.smartconstraints.domainport.ImmutableSourcePropertyDto;
import eu.aronnax.smartconstraints.domainport.SourceClassDto;
import eu.aronnax.smartconstraints.domainport.SourceParamDto;
import eu.aronnax.smartconstraints.domainport.StringUtilsPort;
import jakarta.inject.Inject;

class BuildSourceHelper {

    private static final Logger LOGGER = Logger.getLogger(BuildSourceHelper.class.getName());

    private final StringUtilsPort stringUtils;
    private final ConstraintsHelper constraintsHelper;

    @Inject
    BuildSourceHelper(final StringUtilsPort stringUtils, ConstraintsHelper constraintsHelper) {
        this.stringUtils = stringUtils;
        this.constraintsHelper = constraintsHelper;
    }

    Map.Entry<CharSequence, SourceClassDto> buildSourceDto(final Map.Entry<String, List<Element>> entry) {

        String classQualifiedName = entry.getKey() + "Constraints";
        String classSimpleName = NamingUtil.extractSimpleName(classQualifiedName);
        String packageName = NamingUtil.extractPackageName(classQualifiedName);

        MapEntryDto<SourceClassDto> result = new MapEntryDto<>(
                classQualifiedName,
                ImmutableSourceClassDto.builder()
                        .packageName(packageName)
                        .qualifiedName(classQualifiedName)
                        .simpleName(classSimpleName)
                        .addAllProperties(this.getSourceProperties(entry))
                        .build());
        LOGGER.info(result.getValue().toString());
        return result;
    }

    private List<ImmutableSourcePropertyDto> getSourceProperties(Map.Entry<String, List<Element>> entry) {
        return entry.getValue().stream()
                .map(propertyElem -> ImmutableSourcePropertyDto.builder()
                        .name(this.stringUtils.capitalize(
                                propertyElem.getSimpleName().toString()))
                        .addAnnots(getSourceAnnots(propertyElem))
                        .build())
                .collect(Collectors.toList());
    }

    private ImmutableSourceAnnotDto[] getSourceAnnots(Element propertyElem) {
        return this.constraintsHelper
                .getConstraintClasses()
                .map(propertyElem::getAnnotation)
                .filter(Objects::nonNull)
                .map(annotElmt -> ImmutableSourceAnnotDto.builder()
                        .qualifiedName(annotElmt.annotationType().getCanonicalName())
                        .simpleName(annotElmt.annotationType().getSimpleName())
                        .annotParams(this.buildAnnotParams(annotElmt))
                        .build())
                .toArray(ImmutableSourceAnnotDto[]::new);
    }

    private List<SourceParamDto> buildAnnotParams(Annotation annotElmt) {
        Class<? extends Annotation> annotType = annotElmt.annotationType();

        List<SourceParamDto> annotParams = new ArrayList<>();

        if (annotType.equals(NotNull.class)) {
            NotNull annot = (NotNull) annotElmt;
            if (!"{javax.validation.constraints.NotNull.message}".equals(annot.message())) {
                annotParams.add(ImmutableSourceParamDto.builder()
                        .name("message")
                        .stringValue(annot.message())
                        .build());
            }
        }
        if (annotType.equals(Size.class)) {
            Size annot = (Size) annotElmt;
            if (annot.min() != 0) {
                annotParams.add(ImmutableSourceParamDto.builder()
                        .name("min")
                        .nonStringValue(annot.min())
                        .build());
            }
            if (annot.max() != 0) {
                annotParams.add(ImmutableSourceParamDto.builder()
                        .name("max")
                        .nonStringValue(annot.max())
                        .build());
            }
            if (!"{javax.validation.constraints.Size.message}".equals(annot.message())) {
                annotParams.add(ImmutableSourceParamDto.builder()
                        .name("message")
                        .stringValue(annot.message())
                        .build());
            }
        }

        return annotParams;
    }
}
