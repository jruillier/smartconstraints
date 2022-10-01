package eu.aronnax.smartconstraints.annotationprocessor;

import com.google.auto.service.AutoService;
import eu.aronnax.smartconstraints.domain.ProcessCopyConstraintsAnnotationUC;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({"javax.validation.constraints.*", "eu.aronnax.smartconstraints.annotation.CopyConstraints"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
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
