package eu.aronnax.smartconstraints.annotationprocessor;

import com.google.auto.service.AutoService;
import eu.aronnax.smartconstraints.annotation.CopyJavaxConstraints;
import eu.aronnax.smartconstraints.domain.usecase.ProcessCopyConstraintsAnnotationUC;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * The entry point of SmartConstraints. This annotation processor will :
 * <ul>
 *     <li>look for @CopyConstraints annotations</li>
 *     <li>scan the package indicated in the 'from' property</li>
 *     <li>find any entities annotated with validation constraints</li>
 *     <li>extract objects that describe all validation constraints in entities</li>
 *     <li>prepare objects that describe what should be generated</li>
 *     <li>generate as a string one class per entity with all meta annotations within</li>
 *     <li>write generated classes</li>
 * </ul>
 */
@SupportedAnnotationTypes({
    "javax.validation.constraints.*",
    "eu.aronnax.smartconstraints.annotation.CopyJavaxConstraints"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class CopyJavaxConstraintsProcessor extends AbstractProcessor {

    private final ProcessCopyConstraintsAnnotationUC processCopyConstraintsAnnotationUC;

    @SuppressWarnings("unused")
    public CopyJavaxConstraintsProcessor() {
        super();

        ServicesFactory servicesFactory = DaggerServicesFactory.builder().build();
        this.processCopyConstraintsAnnotationUC = servicesFactory.processSmartConstraintsAnnotationUC();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.processingEnv
                .getMessager()
                .printMessage(
                        Diagnostic.Kind.NOTE,
                        "Processing any " + CopyJavaxConstraints.class.getSimpleName() + " annotations...");
        this.processCopyConstraintsAnnotationUC.exec(annotations, roundEnv, this.processingEnv);

        return false;
    }
}
