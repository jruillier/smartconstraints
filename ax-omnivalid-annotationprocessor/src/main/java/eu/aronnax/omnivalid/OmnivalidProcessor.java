package eu.aronnax.omnivalid;

import com.google.auto.service.AutoService;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

class MapEntry<V> implements Map.Entry<CharSequence, V> {

    private CharSequence key;
    private V value;

    public MapEntry(CharSequence key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public CharSequence getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}

@SupportedAnnotationTypes("javax.validation.constraints.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class OmnivalidProcessor extends AbstractProcessor {

    private static final Logger LOGGER = Logger.getLogger(OmnivalidProcessor.class.getName());

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        this.collectAnnotElements(annotations, roundEnv)
                .map(this::buildVO)
                .map(this::renderSource)
                .forEach(this::writeSource);

        return false;
    }

    private Stream<Map.Entry<TypeElement, List<Element>>> collectAnnotElements(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<TypeElement, List<Element>> anotElementsPerClass = new HashMap<>();

        annotations.forEach(annotation -> {
            LOGGER.info("Annotation : " + annotation.getSimpleName());
            Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(annotation);
            elementsAnnotatedWith.stream()
                    .filter(element -> !element.getEnclosingElement()
                            .getSimpleName()
                            .toString()
                            .endsWith("Constraints"))
                    .forEach(element -> {
                        anotElementsPerClass.merge(
                                ((TypeElement) element.getEnclosingElement()),
                                Collections.singletonList(element),
                                (elements, elements2) -> {
                                    ArrayList<Element> merge = new ArrayList<>();
                                    merge.addAll(elements);
                                    merge.addAll(elements2);
                                    return merge;
                                });
                    });
        });

        return anotElementsPerClass.entrySet().stream()
                .peek(entry -> LOGGER.info(
                        "CLASSE: " + entry.getKey().getQualifiedName().toString() + " ELEMS : "
                                + entry.getValue().stream()
                                        .map(element -> {
                                            return element.getSimpleName() + " ("
                                                    + element.getAnnotationMirrors().stream()
                                                            .map(Objects::toString)
                                                            .collect(Collectors.joining(", "))
                                                    + ") ";
                                        })
                                        .collect(Collectors.joining(", "))));
    }

    private List<ImmutableSourceElemDto> getAnnotElements(Map.Entry<TypeElement, List<Element>> entry) {
        return entry.getValue().stream()
                .map(annotElem -> ImmutableSourceElemDto.builder()
                        .name(StringUtils.capitalize(annotElem.getSimpleName().toString()))
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

    private Map.Entry<CharSequence, SourceClassDto> buildVO(final Map.Entry<TypeElement, List<Element>> entry) {

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

        MapEntry<SourceClassDto> result = new MapEntry<>(
                classQualifiedName,
                ImmutableSourceClassDto.builder()
                        .packageName(packageName)
                        .qualifiedName(classQualifiedName)
                        .simpleName(classSimpleName)
                        .addAllAnnotElements(getAnnotElements(entry))
                        .build());
        LOGGER.info(result.getValue().toString());
        return result;
    }

    private RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry) {
        SourceClassDto sourceClassDto = entry.getValue();
        String prefix = "package " + sourceClassDto.packageName() + ";\n"
                + "\n"
                + "import java.lang.annotation.Documented;\n"
                + "import java.lang.annotation.Retention;\n"
                + "import java.lang.annotation.Target;\n"
                + "\n"
                + "import javax.validation.Constraint;\n"
                + "\n"
                + "import static java.lang.annotation.ElementType.ANNOTATION_TYPE;\n"
                + "import static java.lang.annotation.ElementType.CONSTRUCTOR;\n"
                + "import static java.lang.annotation.ElementType.FIELD;\n"
                + "import static java.lang.annotation.ElementType.METHOD;\n"
                + "import static java.lang.annotation.ElementType.PARAMETER;\n"
                + "import static java.lang.annotation.RetentionPolicy.RUNTIME;\n"
                + "\n"
                + "\npublic class " + sourceClassDto.simpleName() + " { ";
        String suffix = "\n }";

        String content = entry.getValue().annotElements().stream()
                .map(elem -> elem.annots().stream()
                                .map(annot -> "\n@" + annot.qualifiedName())
                                .collect(Collectors.joining())
                        + "\n@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })"
                        + "\n@Retention(RUNTIME)"
                        + "\n@Documented"
                        + "\n@Constraint(validatedBy = {})"
                        + "\npublic @interface " + elem.name() + " {}")
                .collect(Collectors.joining("\n\n"));

        String qualifiedName = entry.getKey().toString();
        String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
        String classSimpleName = qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1);
        return ImmutableRenderingDto.builder()
                .packageName(packageName)
                .classSimpleName(classSimpleName)
                .sourceRendering(prefix + content + suffix)
                .build();
    }

    private void writeSource(RenderingDto entry) {

        PackageElement packageElement = this.processingEnv.getElementUtils().getPackageElement(entry.packageName());
        try {
            LOGGER.info("Writing source " + entry.packageName() + "." + entry.classSimpleName());
            JavaFileObject sourceFile =
                    this.processingEnv.getFiler().createSourceFile(entry.classSimpleName(), packageElement);
            Writer writer = sourceFile.openWriter();
            writer.write(entry.sourceRendering());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
