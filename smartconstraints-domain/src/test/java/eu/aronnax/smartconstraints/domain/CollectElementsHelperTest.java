package eu.aronnax.smartconstraints.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CollectElementsHelperTest {

    @InjectMocks
    private CollectElementsHelper collectElementsHelper;

    @BeforeEach
    void setUp() {}

    @Test
    void collectAnnotElements() {
        // Prepare
        Set<? extends TypeElement> annots =
                new HashSet<>(Arrays.asList(mock(TypeElement.class), mock(TypeElement.class)));
        RoundEnvironment roundEnv = mock(RoundEnvironment.class);
        ProcessingEnvironment processingEnv = mock(ProcessingEnvironment.class);

        // Run
        List<Map.Entry<String, List<Element>>> result = this.collectElementsHelper
                .collectAnnotElements(annots, roundEnv, processingEnv)
                .collect(Collectors.toList());

        // Verify
        assertEquals(0, result.size());
    }
}
