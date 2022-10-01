package eu.aronnax.smartconstraints.domain;

import eu.aronnax.smartconstraints.domainport.ImmutableSourceAnnotDto;
import eu.aronnax.smartconstraints.domainport.ImmutableSourceClassDto;
import eu.aronnax.smartconstraints.domainport.ImmutableSourcePropertyDto;
import eu.aronnax.smartconstraints.domainport.SourceClassDto;
import eu.aronnax.smartconstraints.domainport.StringUtilsPort;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;

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
                .filter(aClass -> propertyElem.getAnnotation(aClass) != null)
                .map(aClass -> ImmutableSourceAnnotDto.builder()
                        .qualifiedName(propertyElem
                                .getAnnotation(aClass)
                                .annotationType()
                                .getCanonicalName())
                        .simpleName(propertyElem
                                .getAnnotation(aClass)
                                .annotationType()
                                .getSimpleName())
                        .build())
                .toArray(ImmutableSourceAnnotDto[]::new);
    }
}
