package eu.aronnax.smartconstraints.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import eu.aronnax.smartconstraints.domain.port.sourcerenderer.SourceClassDto;
import eu.aronnax.smartconstraints.domain.port.sourcerenderer.SourcePropertyDto;
import eu.aronnax.smartconstraints.domain.port.stringutils.StringUtilsPort;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuildSourceHelperTest {

    @InjectMocks
    private BuildSourceHelper instance;

    @Mock
    private StringUtilsPort stringUtils;

    @Mock
    private ConstraintsHelper constraintsHelper;

    @BeforeEach
    void setUp() {
        when(this.stringUtils.capitalize(anyString())).thenAnswer(invocation -> {
            String str = invocation.getArgument(0, String.class);
            return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
        });
        when(this.constraintsHelper.getConstraintClasses()).thenCallRealMethod();
    }

    @Test
    void buildSourceDto() {
        // Prepare
        VariableElement propStreetName = mock(VariableElement.class);
        when(propStreetName.getSimpleName()).thenReturn(new AName("streetName"));

        Map.Entry<String, List<Element>> elementsByName =
                new MapEntryDto("gp.fake.FakeAddress", List.<Element>of(propStreetName));

        // Run
        Map.Entry<CharSequence, SourceClassDto> result = this.instance.buildSourceDto(elementsByName);

        // Verify
        assertEquals("FakeAddressConstraints", result.getValue().simpleName());
        // TODO JERUI1       assertEquals("gp.fake.FakeAddressConstraints", result.getValue().qualifiedName());
        List<SourcePropertyDto> resultProperties = result.getValue().properties();
        //        assertEquals(new AName("streetName"), resultProperties.get(0).name());
    }
}
