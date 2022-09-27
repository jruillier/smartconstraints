package eu.aronnax.omnivalid.annotationprocessor;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import eu.aronnax.omnivalid.domain.BuildSourceUC;
import eu.aronnax.omnivalid.domain.CollectElementsUC;
import eu.aronnax.omnivalid.domain.RenderSourceUC;
import eu.aronnax.omnivalid.domain.RenderingDto;
import eu.aronnax.omnivalid.domain.SourceClassDto;

@SupportedAnnotationTypes({"javax.validation.constraints.*", "eu.aronnax.omnivalid.annotation.CopyConstraints"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class OmnivalidProcessor extends AbstractProcessor {

    public static final Logger LOGGER = Logger.getLogger(OmnivalidProcessor.class.getName());

    private final CollectElementsUC collectElementsUC;
    private final BuildSourceUC buildSourceUC;
    private final RenderSourceUC renderSourceUC;

    @SuppressWarnings("unused")
    public OmnivalidProcessor() {
        ServicesFactory servicesFactory = DaggerServicesFactory.builder().build();
        this.collectElementsUC = servicesFactory.collectElementsUC();
        this.buildSourceUC = servicesFactory.buildSourceUC();
        this.renderSourceUC = servicesFactory.renderSourceUC();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.collectAnnotElements(annotations, roundEnv)
                .map(this::buildSourceDto)
                .map(this::renderSource)
                .forEach(this::writeSource);
        return false;
    }

    private RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry) {
        return this.renderSourceUC.renderSource(entry);
    }

    private Map.Entry<CharSequence, SourceClassDto> buildSourceDto(
            Map.Entry<String, List<Element>> typeElementListEntry) {
        return this.buildSourceUC.buildSourceDto(typeElementListEntry);
    }

    private Stream<Map.Entry<String, List<Element>>> collectAnnotElements(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return collectElementsUC.collectAnnotElements(annotations, roundEnv, this.processingEnv.getElementUtils());
    }

    private void writeSource(RenderingDto entry) {

        try {
            LOGGER.info("Writing source " + entry.classQualifiedName());
            JavaFileObject sourceFile = this.processingEnv.getFiler().createSourceFile(entry.classQualifiedName());
            Writer writer = sourceFile.openWriter();
            writer.write(entry.sourceRendering());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
