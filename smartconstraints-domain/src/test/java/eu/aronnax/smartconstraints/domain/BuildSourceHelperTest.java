package eu.aronnax.smartconstraints.domain;

import eu.aronnax.smartconstraints.domainport.SourceClassDto;
import eu.aronnax.smartconstraints.domainport.SourcePropertyDto;
import eu.aronnax.smartconstraints.domainport.StringUtilsPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        when(stringUtils.capitalize(anyString())).thenAnswer(invocation -> {
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
