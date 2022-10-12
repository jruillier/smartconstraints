package eu.aronnax.smartconstraints.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import eu.aronnax.smartconstraints.annotation.CopyConstraints;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.validation.constraints.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CollectElementsHelperTest {

    @InjectMocks
    private CollectElementsHelper collectElementsHelper;

    @Mock
    private ConstraintsHelper constraintsHelper;

    @BeforeEach
    void setUp() {
        when(this.constraintsHelper.getConstraintClasses()).thenCallRealMethod();
    }

    @Test
    void collectAnnotElements() {
        // Prepare
        TypeElement copyConstraintsAnnotDef = buildTypeElement(CopyConstraints.class.getName());
        Set<? extends TypeElement> annots =
                new HashSet<>(Arrays.asList(buildTypeElement("gp.fake.AnotherAnnotation"), copyConstraintsAnnotDef));

        PackageElement annotPackage = this.buildCCPackageElement("gp.fake.targetpkg", "gp.fake.frompkg");

        RoundEnvironment roundEnv = mock(RoundEnvironment.class);
        when(roundEnv.getElementsAnnotatedWith(copyConstraintsAnnotDef)).thenReturn(new HashSet(List.of(annotPackage)));

        Size fakeSizeAnnot = mock(Size.class);

        VariableElement propStreetName = mock(VariableElement.class);
        lenient().when(propStreetName.getAnnotation(Size.class)).thenReturn(fakeSizeAnnot);

        TypeElement srcClass = mock(TypeElement.class);
        when(srcClass.getSimpleName()).thenReturn(new AName("FakeAddress"));
        List srcProperties = List.of(propStreetName);
        when(srcClass.getEnclosedElements()).thenReturn(srcProperties);
        when(propStreetName.getEnclosingElement()).thenReturn(srcClass);

        List srcClasses = List.of(srcClass);

        PackageElement srcPkgElem = mock(PackageElement.class);

        when(srcPkgElem.getEnclosedElements()).thenReturn(srcClasses);

        Elements mockElemUtils = mock(Elements.class);
        when(mockElemUtils.getPackageElement("gp.fake.frompkg")).thenReturn(srcPkgElem);

        ProcessingEnvironment processingEnv = mock(ProcessingEnvironment.class);
        when(processingEnv.getElementUtils()).thenReturn(mockElemUtils);

        // Run
        List<Map.Entry<String, List<Element>>> result = this.collectElementsHelper
                .collectAnnotElements(annots, roundEnv, processingEnv)
                .collect(Collectors.toList());

        // Verify
        assertEquals(1, result.size());
    }

    private PackageElement buildCCPackageElement(String curPkg, String fromPkg) {
        CopyConstraints mockCC = mock(CopyConstraints.class);
        when(mockCC.from()).thenReturn(fromPkg);

        PackageElement mock = mock(PackageElement.class);
        when(mock.getQualifiedName()).thenReturn(new AName(curPkg));
        when(mock.getAnnotation(CopyConstraints.class)).thenReturn(mockCC);

        return mock;
    }

    private TypeElement buildTypeElement(String str) {
        TypeElement mock = mock(TypeElement.class);
        when(mock.getQualifiedName()).thenReturn(new AName(str));
        return mock;
    }
}
