package eu.aronnax.smartconstraints.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import eu.aronnax.smartconstraints.domainport.RenderingDto;
import java.io.IOException;
import java.io.Writer;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WriteSourceHelperTest {

    @InjectMocks
    private WriteSourceHelper instance;

    @Mock
    private ProcessingEnvironment processingEnv;

    @Mock
    private Filer filer;

    @Mock
    private JavaFileObject javaFileObject;

    @Mock
    private Writer writer;

    @BeforeEach
    void setUp() throws IOException {
        when(this.processingEnv.getFiler()).thenReturn(this.filer);
        when(this.filer.createSourceFile(any())).thenReturn(this.javaFileObject);
        when(this.javaFileObject.openWriter()).thenReturn(this.writer);
    }

    @Test
    void writeSource() throws IOException {
        // Prepare
        String classQualifiedName = "gp.fake.test.TestAddressConstraints";
        String sourceRendering =
                """
                package gp.fake.test;

                public class TestAddressConstraints {

                }
                """
                        .stripIndent();

        RenderingDto renderingDto =
                new RenderingDto("gp.fake.test", "TestAddressConstraints", classQualifiedName, sourceRendering);

        // Run
        this.instance.writeSource(renderingDto, processingEnv);

        // Verify
        verify(this.filer, times(1)).createSourceFile(classQualifiedName);
        verifyNoMoreInteractions(this.filer);
        verify(this.javaFileObject, times(1)).openWriter();
        verifyNoMoreInteractions(this.javaFileObject);
        verify(this.writer, times(1)).write(sourceRendering);
        verify(this.writer, times(1)).close();
        verifyNoMoreInteractions(this.writer);
    }
}
