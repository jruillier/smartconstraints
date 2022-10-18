package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public interface ElementCollectorPort {

    Stream<SourceEntityDto> collectAnnotElements(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv);
}
