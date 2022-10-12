package eu.aronnax.smartconstraints.annotationprocessor;

import com.google.auto.service.AutoService;
import eu.aronnax.smartconstraints.domain.ProcessCopyConstraintsAnnotationUC;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;
import java.util.logging.Logger;

@SupportedAnnotationTypes({"javax.validation.constraints.*", "eu.aronnax.smartconstraints.annotation.CopyConstraints"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class CopyConstraintsProcessor extends AbstractProcessor {

    public static final Logger LOGGER = Logger.getLogger(CopyConstraintsProcessor.class.getName());

    private final ProcessCopyConstraintsAnnotationUC processCopyConstraintsAnnotationUC;

    @SuppressWarnings("unused")
    public CopyConstraintsProcessor() {
        super();

        ServicesFactory servicesFactory = DaggerServicesFactory.builder().build();
        this.processCopyConstraintsAnnotationUC = servicesFactory.processSmartConstraintsraintsAnnotationUC();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.processCopyConstraintsAnnotationUC.exec(annotations, roundEnv, processingEnv);
        return false;
    }
}
