package eu.aronnax.smartconstraints.javaxvalidation;

import eu.aronnax.smartconstraints.annotation.CopyJavaxConstraints;
import eu.aronnax.smartconstraints.domain.port.coderenderer.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ElementDescriptor;
import javax.validation.metadata.PropertyDescriptor;

@ApplicationScoped
class CollectElementsHelper implements ElementCollectorPort {

    private static final Logger LOGGER = Logger.getLogger(CollectElementsHelper.class.getName());

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Inject
    CollectElementsHelper() {}

    public Stream<SourceEntityDto> collectAnnotElements(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
        return annotations.stream()
                .filter(annot -> annot.getQualifiedName().contentEquals(CopyJavaxConstraints.class.getName()))
                .flatMap(copyConstraintsAnnot -> roundEnv.getElementsAnnotatedWith(copyConstraintsAnnot).stream())
                .map(this::buildSourceTarget)
                .flatMap(sourceTargetVO -> this.getSourceEntitiesFromPackage(
                        processingEnv, sourceTargetVO.sourcePackage, sourceTargetVO.targetPackage))
                .peek(this::logProcessedElement);
    }

    private Stream<SourceEntityDto> getSourceEntitiesFromPackage(
            ProcessingEnvironment processingEnv, CharSequence sourcePackage, CharSequence targetPackage) {

        return processingEnv.getElementUtils().getPackageElement(sourcePackage).getEnclosedElements().stream()
                .filter(element -> element.getKind().isClass())
                .map(element -> this.getBeanNameAndDescriptor(this.validator, (TypeElement) element))
                .map(beanNameAndDesc ->
                        beanNameAndDesc.newWithValue(beanNameAndDesc.value().getConstrainedProperties()))
                .filter(beanNameAndConstProps -> !beanNameAndConstProps.value().isEmpty())
                .map(beanNameAndConstProps -> new SourceEntityDto(
                        beanNameAndConstProps.key(),
                        this.getSourceProperties(beanNameAndConstProps.value()),
                        targetPackage.toString()));
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
                targetPackage.getAnnotation(CopyJavaxConstraints.class).from(),
                ((PackageElement) targetPackage).getQualifiedName());
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
