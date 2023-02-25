package eu.aronnax.smartconstraints.annotationprocessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CopyJavaxConstraintsProcessorTest {

    @InjectMocks
    private CopyJavaxConstraintsProcessor instance;

    @Mock
    private RoundEnvironment roundEnv;

    @BeforeEach
    void setUp() {
        ProcessingEnvironment processingEnv = mock(ProcessingEnvironment.class);
        when(processingEnv.getMessager()).thenReturn(mock(Messager.class));

        this.instance.init(processingEnv);
    }

    @Test
    void process() {
        // Prepare
        // nothing to prepare

        // Execute
        boolean result = this.instance.process(Collections.emptySet(), this.roundEnv);

        // Verify
        assertFalse(result);
    }
}