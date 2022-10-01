package eu.aronnax.smartconstraints.domain;

import eu.aronnax.smartconstraints.domainport.RenderingDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;

@ApplicationScoped
class WriteSourceHelper {

    private static final Logger LOGGER = Logger.getLogger(WriteSourceHelper.class.getName());

    @Inject
    WriteSourceHelper() {}

    void writeSource(RenderingDto entry, ProcessingEnvironment processingEnv) {

        try {
            LOGGER.info("Writing source " + entry.classQualifiedName());
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(entry.classQualifiedName());
            Writer writer = sourceFile.openWriter();
            writer.write(entry.sourceRendering());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
