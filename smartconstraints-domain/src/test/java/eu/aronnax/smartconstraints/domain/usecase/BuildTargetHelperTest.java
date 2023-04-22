package eu.aronnax.smartconstraints.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import eu.aronnax.smartconstraints.domain.port.KeyValueDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.*;
import eu.aronnax.smartconstraints.domain.port.stringutils.StringUtilsPort;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuildSourceHelperTest {

    @InjectMocks
    private BuildTargetHelper instance;

    @Mock
    private StringUtilsPort stringUtils;

    @Mock
    private TargetConstraintHelperPort targetConstraintHelperPort;

    @BeforeEach
    void setUp() {
        when(this.stringUtils.capitalize(anyString())).thenAnswer(invocation -> {
            String str = invocation.getArgument(0, String.class);
            return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
        });
    }

    @Test
    void buildSourceDto() {
        // Prepare
        var propStreetName = new SourcePropertyDto("streetName", Collections.emptyList());

        var elementsByName = new SourceEntityDto("gp.fake.FakeAddress", List.of(propStreetName), "gp.faketarget");

        // Run
        KeyValueDto<TargetClassDto> result = this.instance.buildTargetClass(elementsByName);

        // Verify
        assertEquals("FakeAddress_Constraints", result.value().simpleName());
        assertEquals("gp.faketarget.FakeAddress_Constraints", result.value().qualifiedName());
        List<TargetComposedAnnotDto> resultProperties = result.value().composedAnnots();
        TargetComposedAnnotDto streetNameComposedAnnot = resultProperties.get(0);
        assertEquals("ValidStreetName", streetNameComposedAnnot.name());
        //        List<SourceAnnotDto> streetNameAnnots = streetNameComposedAnnot.annots();
        //        assertEquals("NotNull", streetNameAnnots.get(0).simpleName());
    }
}
