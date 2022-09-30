package eu.aronnax.omnivalid.annotationprocessor;

import com.google.auto.service.AutoService;

import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import eu.aronnax.omnivalid.domain.ProcessCopyConstraintsAnnotationUC;

@SupportedAnnotationTypes({"javax.validation.constraints.*", "eu.aronnax.omnivalid.annotation.CopyConstraints"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class OmnivalidProcessor extends AbstractProcessor {

    public static final Logger LOGGER = Logger.getLogger(OmnivalidProcessor.class.getName());

    private final ProcessCopyConstraintsAnnotationUC processCopyConstraintsAnnotationUC;

    @SuppressWarnings("unused")
    public OmnivalidProcessor() {
        super();

            ServicesFactory servicesFactory = DaggerServicesFactory.builder().build();
            this.processCopyConstraintsAnnotationUC = servicesFactory.processCopyConstraintsAnnotationUC();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.processCopyConstraintsAnnotationUC.exec(annotations, roundEnv, processingEnv);
        return false;
    }
}
