package eu.aronnax.omnivalid.domain;

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

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CollectElementsUC {

    private static final Logger LOGGER = Logger.getLogger(CollectElementsUC.class.getName());

    @Inject
    public CollectElementsUC() {
    }

    public Stream<Map.Entry<TypeElement, List<Element>>> collectAnnotElements(
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
                                    ArrayList<Element> merge = new ArrayList<Element>();
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
}