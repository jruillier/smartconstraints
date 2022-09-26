package eu.aronnax.omnivalid.domain;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.validation.constraints.NotNull;

import jakarta.inject.Inject;

public class BuildSourceUC {

    private static final Logger LOGGER = Logger.getLogger(BuildSourceUC.class.getName());

    private final StringUtilsPort stringUtils;

    @Inject
    public BuildSourceUC(final StringUtilsPort stringUtils) {
        this.stringUtils = stringUtils;
    }

    public Map.Entry<CharSequence, SourceClassDto> buildSourceDto(final Map.Entry<TypeElement, List<Element>> entry) {

        String classQualifiedName = entry.getKey().getQualifiedName() + "Constraints";
        String classSimpleName = entry.getKey().getSimpleName() + "Constraints";
        String packageName = entry.getKey()
                .getQualifiedName()
                .subSequence(
                        0,
                        entry.getKey().getQualifiedName().length()
                                - entry.getKey().getSimpleName().length()
                                - 1)
                .toString();

        MapEntryDto<SourceClassDto> result = new MapEntryDto<>(
                classQualifiedName,
                ImmutableSourceClassDto.builder()
                        .packageName(packageName)
                        .qualifiedName(classQualifiedName)
                        .simpleName(classSimpleName)
                        .addAllAnnotElements(this.getAnnotElements(entry))
                        .build());
        LOGGER.info(result.getValue().toString());
        return result;
    }

    private List<ImmutableSourceElemDto> getAnnotElements(Map.Entry<TypeElement, List<Element>> entry) {
        return entry.getValue().stream()
                .map(annotElem -> ImmutableSourceElemDto.builder()
                        .name(this.stringUtils.capitalize(annotElem.getSimpleName().toString()))
                        .addAnnots(ImmutableSourceAnnotDto.builder()
                                .qualifiedName(annotElem
                                        .getAnnotation(NotNull.class)
                                        .annotationType()
                                        .getCanonicalName())
                                .simpleName(annotElem
                                        .getAnnotation(NotNull.class)
                                        .annotationType()
                                        .getSimpleName())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }
}