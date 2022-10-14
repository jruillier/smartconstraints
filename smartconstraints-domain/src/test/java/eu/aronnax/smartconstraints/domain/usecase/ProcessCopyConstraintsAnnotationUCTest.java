package eu.aronnax.smartconstraints.domain.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
class ProcessCopyConstraintsAnnotationUCTest {

    @InjectMocks
    private ProcessCopyConstraintsAnnotationUC instance;

    @Mock
    private CollectElementsHelper collectElementsHelper;

    @Mock
    private BuildSourceHelper buildSourceHelper;

    @Mock
    private RenderSourceHelper renderSourceHelper;

    @Mock
    private WriteSourceHelper writeSourceHelper;

    @BeforeEach
    void setUp() {
        when(this.collectElementsHelper.collectAnnotElements(any(), any(), any()))
                .thenReturn(Stream.of(mock(Map.Entry.class)));
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
        verify(this.buildSourceHelper, times(1)).buildSourceDto(any());
        verify(this.renderSourceHelper, times(1)).renderSource(any());
        verify(this.writeSourceHelper, times(1)).writeSource(any(), any());
    }
}
