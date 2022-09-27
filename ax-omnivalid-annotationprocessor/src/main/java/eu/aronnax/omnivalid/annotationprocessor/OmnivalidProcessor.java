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
import eu.aronnax.omnivalid.sourcerenderer.simple.SourceRendererSimple;

class MapEntry<V> implements Map.Entry<CharSequence, V> {

    private CharSequence key;
    private V value;

    public MapEntry(CharSequence key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public CharSequence getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}

@SupportedAnnotationTypes("javax.validation.constraints.*")
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
        this.collectElementsUC = new CollectElementsUC();
        this.buildSourceUC = servicesFactory.buildSourceUC();
        this.renderSourceUC = new RenderSourceUC(new SourceRendererSimple());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.collectElementsUC
                .collectAnnotElements(annotations, roundEnv)
                .map(this::buildSourceDto)
                .map(this::renderSource)
                .forEach(this::writeSource);
        return false;
    }

    private RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry) {
        return this.renderSourceUC.renderSource(entry);
    }

    private Map.Entry<CharSequence, SourceClassDto> buildSourceDto(
            Map.Entry<TypeElement, List<Element>> typeElementListEntry) {
        return this.buildSourceUC.buildSourceDto(typeElementListEntry);
    }

    private Stream<Map.Entry<TypeElement, List<Element>>> collectAnnotElements(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return collectElementsUC.collectAnnotElements(annotations, roundEnv);
    }

    private void writeSource(RenderingDto entry) {

        try {
            LOGGER.info("Writing source " + entry.classQualifiedName());
            JavaFileObject sourceFile =
                    this.processingEnv.getFiler().createSourceFile(entry.classQualifiedName());
            Writer writer = sourceFile.openWriter();
            writer.write(entry.sourceRendering());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
