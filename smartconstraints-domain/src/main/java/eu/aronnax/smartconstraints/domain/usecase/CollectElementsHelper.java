package eu.aronnax.smartconstraints.domain.usecase;

import eu.aronnax.smartconstraints.annotation.CopyConstraints;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
class CollectElementsHelper {

    private static final Logger LOGGER = Logger.getLogger(CollectElementsHelper.class.getName());

    private final ConstraintsHelper constraintsHelper;

    @Inject
    CollectElementsHelper(ConstraintsHelper constraintsHelper) {
        this.constraintsHelper = constraintsHelper;
    }

    Stream<Map.Entry<String, List<Element>>> collectAnnotElements(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {

        Map<String, List<Element>> anotElementsPerClass = new HashMap<>();

        annotations.stream()
                .filter(annot -> annot.getQualifiedName().contentEquals(CopyConstraints.class.getName()))
                .flatMap(smartConstraintsAnnot -> roundEnv.getElementsAnnotatedWith(smartConstraintsAnnot).stream())
                .map(this::buildSourceTarget)
                .flatMap(sourceTargetVO -> getProperties(sourceTargetVO, processingEnv))
                .forEach(elementTarget -> mergeIntoMap(anotElementsPerClass, elementTarget));

        return anotElementsPerClass.entrySet().stream().peek(this::logProcessedElement);
    }

    private SourceTargetVO buildSourceTarget(Element targetPackage) {
        return new SourceTargetVO(
                targetPackage.getAnnotation(CopyConstraints.class).from(),
                ((PackageElement) targetPackage).getQualifiedName());
    }

    private Stream<ElemTargetVO> getProperties(SourceTargetVO sourceTargetVO, ProcessingEnvironment processingEnv) {
        return processingEnv
                .getElementUtils()
                .getPackageElement(sourceTargetVO.sourcePackage)
                .getEnclosedElements()
                .stream()
                .filter(element -> !element.getSimpleName().toString().endsWith("_Constraints"))
                .flatMap(typeElem -> typeElem.getEnclosedElements().stream())
                .filter(typeElem -> typeElem.getKind().equals(ElementKind.FIELD))
                .filter(element -> this.constraintsHelper
                        .getConstraintClasses()
                        .anyMatch(constClass -> element.getAnnotation(constClass) != null))
                .map(element -> new ElemTargetVO(element, sourceTargetVO.targetPackage));
    }

    private void mergeIntoMap(Map<String, List<Element>> anotElementsPerClass, ElemTargetVO elementTarget) {
        anotElementsPerClass.merge(
                elementTarget.targetPackage + "."
                        + elementTarget.element.getEnclosingElement().getSimpleName(),
                Collections.singletonList(elementTarget.element),
                (elements, elements2) -> {
                    ArrayList<Element> merge = new ArrayList<>();
                    merge.addAll(elements);
                    merge.addAll(elements2);
                    return merge;
                });
    }

    private void logProcessedElement(Map.Entry<String, List<Element>> entry) {
        LOGGER.info("CLASSE: " + entry.getKey() + " ELEMS : "
                + entry.getValue().stream()
                        .map(element -> element.getSimpleName() + " ("
                                + element.getAnnotationMirrors().stream()
                                        .map(Objects::toString)
                                        .collect(Collectors.joining(", "))
                                + ") ")
                        .collect(Collectors.joining(", ")));
    }

    static class ElemTargetVO {

        final Element element;
        final CharSequence targetPackage;

        ElemTargetVO(Element element, CharSequence targetPackage) {
            this.element = element;
            this.targetPackage = targetPackage;
        }
    }

    static class SourceTargetVO {

        final CharSequence sourcePackage;
        final CharSequence targetPackage;

        SourceTargetVO(CharSequence sourcePackage, CharSequence targetPackage) {
            this.sourcePackage = sourcePackage;
            this.targetPackage = targetPackage;
        }
    }
}
