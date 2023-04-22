package eu.aronnax.smartconstraints.parser.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import eu.aronnax.smartconstraints.annotation.CopyJavaxConstraints;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
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
    private ConstraintsHelperPort constraintsHelper;

    @BeforeEach
    void setUp() {
        when(this.constraintsHelper.getConstraintClasses()).thenReturn(Stream.of(FakeSize.class));
    }

    @Test
    void collectAnnotElements() {
        // Prepare
        TypeElement copyConstraintsAnnotDef = this.buildTypeElement(CopyJavaxConstraints.class.getName());
        Set<? extends TypeElement> annots = new HashSet<>(
                Arrays.asList(this.buildTypeElement("gp.fake.AnotherAnnotation"), copyConstraintsAnnotDef));

        PackageElement annotPackage = this.buildCCPackageElement("gp.fake.targetpkg", "gp.fake.frompkg");

        RoundEnvironment roundEnv = mock(RoundEnvironment.class);
        //noinspection unchecked,rawtypes
        when(roundEnv.getElementsAnnotatedWith(copyConstraintsAnnotDef)).thenReturn((Set) Set.of(annotPackage));

        FakeSize fakeSizeAnnot = mock(FakeSize.class);

        VariableElement propStreetName = mock(VariableElement.class);
        lenient().when(propStreetName.getAnnotation(FakeSize.class)).thenReturn(fakeSizeAnnot);
        lenient().when(propStreetName.getKind()).thenReturn(ElementKind.FIELD);
        lenient().when(propStreetName.getSimpleName()).thenReturn(new AName("streetName"));

        TypeElement srcClass = mock(TypeElement.class);
        when(srcClass.getSimpleName()).thenReturn(new AName("FakeAddress"));
        when(srcClass.getQualifiedName()).thenReturn(new AName("gp.fake.frompkg.FakeAddress"));
        //noinspection unchecked,rawtypes
        when(srcClass.getEnclosedElements()).thenReturn((List) List.of(propStreetName));

        PackageElement srcPkgElem = mock(PackageElement.class);

        //noinspection unchecked,rawtypes
        when(srcPkgElem.getEnclosedElements()).thenReturn((List) List.of(srcClass));

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

    @SuppressWarnings("SameParameterValue")
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
