package eu.aronnax.smartconstraints.javaxvalidation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import eu.aronnax.smartconstraints.annotation.CopyJavaxConstraints;
import java.util.*;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ElementKind;
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
        TypeElement copyConstraintsAnnotDef = this.buildTypeElement(CopyJavaxConstraints.class.getName());
        Set<? extends TypeElement> annots = new HashSet<>(
                Arrays.asList(this.buildTypeElement("gp.fake.AnotherAnnotation"), copyConstraintsAnnotDef));

        PackageElement annotPackage = this.buildCCPackageElement("gp.fake.targetpkg", "gp.fake.frompkg");

        RoundEnvironment roundEnv = mock(RoundEnvironment.class);
        when(roundEnv.getElementsAnnotatedWith(copyConstraintsAnnotDef)).thenReturn(new HashSet(List.of(annotPackage)));

        Size fakeSizeAnnot = mock(Size.class);

        VariableElement propStreetName = mock(VariableElement.class);
        lenient().when(propStreetName.getAnnotation(Size.class)).thenReturn(fakeSizeAnnot);
        lenient().when(propStreetName.getKind()).thenReturn(ElementKind.FIELD);
        lenient().when(propStreetName.getSimpleName()).thenReturn(new AName("streetName"));

        TypeElement srcClass = mock(TypeElement.class);
        when(srcClass.getSimpleName()).thenReturn(new AName("FakeAddress"));
        when(srcClass.getQualifiedName()).thenReturn(new AName("gp.fake.frompkg.FakeAddress"));
        List srcProperties = List.of(propStreetName);
        when(srcClass.getEnclosedElements()).thenReturn(srcProperties);

        List srcClasses = List.of(srcClass);

        PackageElement srcPkgElem = mock(PackageElement.class);

        when(srcPkgElem.getEnclosedElements()).thenReturn(srcClasses);

        Elements mockElemUtils = mock(Elements.class);
        when(mockElemUtils.getPackageElement("gp.fake.frompkg")).thenReturn(srcPkgElem);

        ProcessingEnvironment processingEnv = mock(ProcessingEnvironment.class);
        when(processingEnv.getElementUtils()).thenReturn(mockElemUtils);

        // Run
        var result = this.collectElementsHelper
                .collectAnnotElements(annots, roundEnv, processingEnv)
                .toList();

        // Verify
        assertEquals(1, result.size());
    }

    private PackageElement buildCCPackageElement(String curPkg, String fromPkg) {
        CopyJavaxConstraints mockCC = mock(CopyJavaxConstraints.class);
        when(mockCC.from()).thenReturn(fromPkg);

        PackageElement mock = mock(PackageElement.class);
        when(mock.getQualifiedName()).thenReturn(new AName(curPkg));
        when(mock.getAnnotation(CopyJavaxConstraints.class)).thenReturn(mockCC);

        return mock;
    }

    private TypeElement buildTypeElement(String str) {
        TypeElement mock = mock(TypeElement.class);
        when(mock.getQualifiedName()).thenReturn(new AName(str));
        return mock;
    }
}
