package eu.aronnax.smartconstraints.parser.common;

import eu.aronnax.smartconstraints.annotation.CopyJavaxConstraints;
import eu.aronnax.smartconstraints.domain.port.coderenderer.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.*;

@ApplicationScoped
public class CollectElementsHelper implements ElementCollectorPort {

    private static final Logger LOGGER = Logger.getLogger(CollectElementsHelper.class.getName());

    private final ConstraintsHelperPort constraintsHelper;

    /**
     * According to injector setup, an appropriate {@link ConstraintsHelperPort} will be injected.
     * @param constraintsHelper matching processed annotations type
     */
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    CollectElementsHelper(ConstraintsHelperPort constraintsHelper) {
        this.constraintsHelper = constraintsHelper;
    }

    public Stream<SourceEntityDto> collectAnnotElements(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {

        return annotations.stream()
                .filter(annot -> annot.getQualifiedName().contentEquals(CopyJavaxConstraints.class.getName()))
                .flatMap(smartConstraintsAnnot -> roundEnv.getElementsAnnotatedWith(smartConstraintsAnnot).stream())
                .map(this::buildSourceTarget)
                .flatMap(sourceTargetVO -> this.collectSourceEntities(sourceTargetVO, processingEnv))
                .peek(this::logProcessedElement);
    }

    private SourceTargetVO buildSourceTarget(Element targetPackage) {
        return new SourceTargetVO(
                targetPackage.getAnnotation(CopyJavaxConstraints.class).from(),
                ((PackageElement) targetPackage).getQualifiedName());
    }

    private Stream<SourceEntityDto> collectSourceEntities(
            SourceTargetVO sourceTargetVO, ProcessingEnvironment processingEnv) {
        return processingEnv
                .getElementUtils()
                .getPackageElement(sourceTargetVO.sourcePackage)
                .getEnclosedElements()
                .stream()
                .filter(element -> !element.getSimpleName().toString().endsWith("_Constraints"))
                .map(element -> new SourceEntityDto(
                        ((TypeElement) element).getQualifiedName().toString(),
                        this.collectSourceProps((TypeElement) element),
                        sourceTargetVO.targetPackage().toString()));
    }

    private List<SourcePropertyDto> collectSourceProps(TypeElement entityElement) {
        return entityElement.getEnclosedElements().stream()
                .filter(typeElem -> typeElem.getKind().equals(ElementKind.FIELD))
                .filter(element -> this.constraintsHelper
                        .getConstraintClasses()
                        .anyMatch(constClass -> element.getAnnotation(constClass) != null))
                .map(element ->
                        new SourcePropertyDto(element.getSimpleName().toString(), this.collectSourceAnnots(element)))
                .toList();
    }

    private List<SourceAnnotDto> collectSourceAnnots(Element propElement) {
        return propElement.getAnnotationMirrors().stream()
                .map(annotMirror -> new SourceAnnotDto(
                        annotMirror.getAnnotationType().toString(),
                        annotMirror
                                .getAnnotationType()
                                .asElement()
                                .getSimpleName()
                                .toString(),
                        this.collectSourceAnnotParams(annotMirror)))
                .toList();
    }

    private List<SourceAnnotParamDto> collectSourceAnnotParams(AnnotationMirror annotMirror) {
        return annotMirror.getElementValues().entrySet().stream()
                .map(entry -> new SourceAnnotParamDto(
                        entry.getKey().getSimpleName().toString(),
                        entry.getValue().getValue()))
                .toList();
    }

    private void logProcessedElement(SourceEntityDto entry) {
        LOGGER.info("CLASSE: " + entry.classQualifiedName() + " ELEMS : "
                + entry.sourceProperties().stream()
                        .map(element -> element.propertyName() + " ("
                                + element.annots().stream()
                                        .map(Objects::toString)
                                        .collect(Collectors.joining(", "))
                                + ") ")
                        .collect(Collectors.joining(", ")));
    }

    record SourceTargetVO(CharSequence sourcePackage, CharSequence targetPackage) {}
}
