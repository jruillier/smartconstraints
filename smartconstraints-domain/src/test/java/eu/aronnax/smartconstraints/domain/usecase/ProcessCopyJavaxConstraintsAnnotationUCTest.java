package eu.aronnax.smartconstraints.domain.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import eu.aronnax.smartconstraints.domain.port.coderenderer.ElementCollectorPort;
import eu.aronnax.smartconstraints.domain.port.coderenderer.SourceEntityDto;
import java.util.*;
import java.util.stream.Stream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProcessCopyJavaxConstraintsAnnotationUCTest {

    @InjectMocks
    private ProcessCopyConstraintsAnnotationUC instance;

    @Mock
    private ElementCollectorPort collectElementsHelper;

    @Mock
    private BuildTargetHelper buildTargetHelper;

    @Mock
    private RenderTargetHelper renderTargetHelper;

    @Mock
    private WriteSourceHelper writeSourceHelper;

    @BeforeEach
    void setUp() {
        when(this.collectElementsHelper.collectAnnotElements(any(), any(), any()))
                .thenReturn(Stream.of(new SourceEntityDto("", Collections.emptyList(), "")));
    }

    @Test
    void exec() {
        // Prepare
        Set<? extends TypeElement> annotations =
                new HashSet<>(Arrays.asList(mock(TypeElement.class), mock(TypeElement.class)));
        RoundEnvironment roundEnv = mock(RoundEnvironment.class);
        ProcessingEnvironment processingEnv = mock(ProcessingEnvironment.class);

        // Run
        this.instance.exec(annotations, roundEnv, processingEnv);

        // Verify
        verify(this.collectElementsHelper, times(1)).collectAnnotElements(any(), any(), any());
        verify(this.buildTargetHelper, times(1)).buildTargetClass(any());
        verify(this.renderTargetHelper, times(1)).renderSource(any());
        verify(this.writeSourceHelper, times(1)).writeSource(any(), any());
    }
}
