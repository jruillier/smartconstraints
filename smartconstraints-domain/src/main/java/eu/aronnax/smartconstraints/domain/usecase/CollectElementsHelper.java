package eu.aronnax.smartconstraints.domain.usecase;

import eu.aronnax.smartconstraints.annotation.CopyConstraints;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ElementDescriptor;
import javax.validation.metadata.PropertyDescriptor;

@ApplicationScoped
class CollectElementsHelper {

    private static final Logger LOGGER = Logger.getLogger(CollectElementsHelper.class.getName());

    private final ConstraintsHelper constraintsHelper;

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Inject
    CollectElementsHelper(ConstraintsHelper constraintsHelper) {
        this.constraintsHelper = constraintsHelper;
    }

    Stream<SourceEntityDto> collectAnnotElements(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
        return annotations.stream()
                .filter(annot -> annot.getQualifiedName().contentEquals(CopyConstraints.class.getName()))
                .flatMap(copyConstraintsAnnot -> roundEnv.getElementsAnnotatedWith(copyConstraintsAnnot).stream())
                .map(this::buildSourceTarget)
                .flatMap(sourceTargetVO ->
                        this.getSourceEntitiesFromPackage(processingEnv, sourceTargetVO.sourcePackage))
                .peek(this::logProcessedElement);
    }

    private Stream<SourceEntityDto> getSourceEntitiesFromPackage(
            ProcessingEnvironment processingEnv, CharSequence sourcePackage) {

        return processingEnv.getElementUtils().getPackageElement(sourcePackage).getEnclosedElements().stream()
                .filter(element -> element.getKind().isClass())
                .map(element -> this.getBeanNameAndDescriptor(this.validator, (TypeElement) element))
                .map(beanNameAndDesc ->
                        beanNameAndDesc.newWithValue(beanNameAndDesc.value().getConstrainedProperties()))
                .filter(beanNameAndConstProps -> !beanNameAndConstProps.value().isEmpty())
                .map(beanNameAndConstProps -> new SourceEntityDto(
                        beanNameAndConstProps.key(), this.getSourceProperties(beanNameAndConstProps.value())));
    }

    private List<SourcePropertyDto> getSourceProperties(Set<PropertyDescriptor> properties) {
        return properties.stream()
                .filter(ElementDescriptor::hasConstraints)
                .map(prop -> new SourcePropertyDto(
                        prop.getPropertyName(), this.buildSourceAnnots(prop.getConstraintDescriptors())))
                .toList();
    }

    private List<SourceAnnotDto> buildSourceAnnots(Set<ConstraintDescriptor<?>> constraintDescriptors) {
        return constraintDescriptors.stream()
                .map(constDesc -> new SourceAnnotDto(
                        constDesc.getAnnotation().annotationType().getName(),
                        constDesc.getAnnotation().annotationType().getSimpleName(),
                        this.buildSourceAnnotParams(constDesc.getAttributes())))
                .toList();
    }

    private List<SourceAnnotParamDto> buildSourceAnnotParams(Map<String, Object> attributes) {
        return attributes.entrySet().stream()
                .map(entry -> new SourceAnnotParamDto(entry.getKey(), entry.getValue()))
                .toList();
    }

    private KeyValueDto<BeanDescriptor> getBeanNameAndDescriptor(Validator validator, TypeElement element) {
        try {
            String className = element.getQualifiedName().toString();
            return new KeyValueDto<>(className, validator.getConstraintsForClass(Class.forName(className)));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    static class ElemTargetVO {

        final Element element;
        final CharSequence targetPackage;

        ElemTargetVO(Element element, CharSequence targetPackage) {
            this.element = element;
            this.targetPackage = targetPackage;
        }
    }

    record SourceTargetVO(CharSequence sourcePackage, CharSequence targetPackage) {}
}
