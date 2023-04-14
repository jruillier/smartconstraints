package eu.aronnax.smartconstraints.javaxvalidation;

import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotParamDto;
import eu.aronnax.smartconstraints.domain.port.stringutils.StringUtilsPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JavaxAnnotElemSourceParamsBuilderTest {

    @InjectMocks
    private JavaxAnnotElemSourceParamsBuilder instance;

    @Mock
    private StringUtilsPort stringUtilsPort;

    @BeforeEach
    void setUp() {}

    @Test
    void processAssertFalse() {
        // Prepare
        AssertFalse annotElmt = this.buildAnnotElmt(AssertFalse.class);
        when(annotElmt.message()).thenReturn("A AssertFalse msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A AssertFalse msg")), params);
    }

    @Test
    void processAssertTrue() {
        // Prepare
        AssertTrue annotElmt = this.buildAnnotElmt(AssertTrue.class);
        when(annotElmt.message()).thenReturn("A AssertTrue msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A AssertTrue msg")), params);
    }

    @Test
    void processDecimalMax() {
        // Prepare
        when(this.stringUtilsPort.isNotBlank(any()))
                .thenAnswer(invocation -> !Optional.ofNullable(invocation.getArgument(0, String.class))
                        .map(String::isBlank)
                        .orElse(false));

        DecimalMax annotElmt = this.buildAnnotElmt(DecimalMax.class);
        when(annotElmt.message()).thenReturn("A DecimalMax msg");
        when(annotElmt.value()).thenReturn("42");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(
                Arrays.asList(
                        new TargetAnnotParamDto("inclusive", false, null),
                        new TargetAnnotParamDto("value", null, "42"),
                        new TargetAnnotParamDto("message", null, "A DecimalMax msg")),
                params);
    }

    @Test
    void processDecimalMin() {
        // Prepare
        DecimalMin annotElmt = this.buildAnnotElmt(DecimalMin.class);
        when(annotElmt.message()).thenReturn("A DecimalMin msg");
        when(annotElmt.value()).thenReturn("42");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(
                Arrays.asList(
                        new TargetAnnotParamDto("inclusive", false, null),
                        new TargetAnnotParamDto("value", null, "42"),
                        new TargetAnnotParamDto("message", null, "A DecimalMin msg")),
                params);
    }

    @Test
    void processDigits() {
        // Prepare
        Digits annotElmt = this.buildAnnotElmt(Digits.class);
        when(annotElmt.message()).thenReturn("A Digits msg");
        when(annotElmt.integer()).thenReturn(2);
        when(annotElmt.fraction()).thenReturn(3);

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(
                Arrays.asList(
                        new TargetAnnotParamDto("fraction", 3, null),
                        new TargetAnnotParamDto("integer", 2, null),
                        new TargetAnnotParamDto("message", null, "A Digits msg")),
                params);
    }

    @Test
    void processEmail() {
        // Prepare
        Email annotElmt = this.buildAnnotElmt(Email.class);
        when(annotElmt.message()).thenReturn("A Email msg");
        when(annotElmt.flags()).thenReturn(new Pattern.Flag[0]);

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A Email msg")), params);
    }

    @Test
    void processFuture() {
        // Prepare
        Future annotElmt = this.buildAnnotElmt(Future.class);
        when(annotElmt.message()).thenReturn("A Future msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A Future msg")), params);
    }

    @Test
    void processFutureOrPresent() {
        // Prepare
        FutureOrPresent annotElmt = this.buildAnnotElmt(FutureOrPresent.class);
        when(annotElmt.message()).thenReturn("A FutureOrPresent msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A FutureOrPresent msg")), params);
    }

    @Test
    void processMax() {
        // Prepare
        Max annotElmt = this.buildAnnotElmt(Max.class);
        when(annotElmt.message()).thenReturn("A Max msg");
        when(annotElmt.value()).thenReturn(5L);

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(
                Arrays.asList(
                        new TargetAnnotParamDto("value", 5L, null),
                        new TargetAnnotParamDto("message", null, "A Max msg")),
                params);
    }

    @Test
    void processMin() {
        // Prepare
        Min annotElmt = this.buildAnnotElmt(Min.class);
        when(annotElmt.message()).thenReturn("A Min msg");
        when(annotElmt.value()).thenReturn(5L);

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(
                Arrays.asList(
                        new TargetAnnotParamDto("value", 5L, null),
                        new TargetAnnotParamDto("message", null, "A Min msg")),
                params);
    }

    @Test
    void processNegative() {
        // Prepare
        Negative annotElmt = this.buildAnnotElmt(Negative.class);
        when(annotElmt.message()).thenReturn("A Negative msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A Negative msg")), params);
    }

    @Test
    void processNegativeOrZero() {
        // Prepare
        NegativeOrZero annotElmt = this.buildAnnotElmt(NegativeOrZero.class);
        when(annotElmt.message()).thenReturn("A NegativeOrZero msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A NegativeOrZero msg")), params);
    }

    @Test
    void processNotBlank() {
        // Prepare
        NotBlank annotElmt = this.buildAnnotElmt(NotBlank.class);
        when(annotElmt.message()).thenReturn("A NotBlank msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A NotBlank msg")), params);
    }

    @Test
    void processNotEmpty() {
        // Prepare
        NotEmpty annotElmt = this.buildAnnotElmt(NotEmpty.class);
        when(annotElmt.message()).thenReturn("A NotEmpty msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A NotEmpty msg")), params);
    }

    @Test
    void processNotNull() {
        // Prepare
        NotNull annotElmt = this.buildAnnotElmt(NotNull.class);
        when(annotElmt.message()).thenReturn("A NotNull msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A NotNull msg")), params);
    }

    @Test
    void processNull() {
        // Prepare
        Null annotElmt = this.buildAnnotElmt(Null.class);
        when(annotElmt.message()).thenReturn("A Null msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A Null msg")), params);
    }

    @Test
    void processPast() {
        // Prepare
        Past annotElmt = this.buildAnnotElmt(Past.class);
        when(annotElmt.message()).thenReturn("A Past msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A Past msg")), params);
    }

    @Test
    void processPastOrPresent() {
        // Prepare
        PastOrPresent annotElmt = this.buildAnnotElmt(PastOrPresent.class);
        when(annotElmt.message()).thenReturn("A PastOrPresent msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A PastOrPresent msg")), params);
    }

    @Test
    void processPattern() {
        // Prepare
        Pattern annotElmt = this.buildAnnotElmt(Pattern.class);
        when(annotElmt.message()).thenReturn("A Pattern msg");
        when(annotElmt.regexp()).thenReturn("aSmartRegexp");
        when(annotElmt.flags()).thenReturn(new Pattern.Flag[0]);

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(
                Arrays.asList(
                        new TargetAnnotParamDto("regexp", null, "aSmartRegexp"),
                        new TargetAnnotParamDto("message", null, "A Pattern msg")),
                params);
    }

    @Test
    void processPositive() {
        // Prepare
        Positive annotElmt = this.buildAnnotElmt(Positive.class);
        when(annotElmt.message()).thenReturn("A Positive msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A Positive msg")), params);
    }

    @Test
    void processPositiveOrZero() {
        // Prepare
        PositiveOrZero annotElmt = this.buildAnnotElmt(PositiveOrZero.class);
        when(annotElmt.message()).thenReturn("A PositiveOrZero msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A PositiveOrZero msg")), params);
    }

    @Test
    void processSize() {
        // Prepare
        Size annotElmt = this.buildAnnotElmt(Size.class);
        when(annotElmt.message()).thenReturn("A Size msg");

        // Run
        List<TargetAnnotParamDto> params = this.instance.process(annotElmt);

        // Verify
        assertEquals(List.of(new TargetAnnotParamDto("message", null, "A Size msg")), params);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <R extends Annotation> R buildAnnotElmt(Class<R> classToMock) {
        R annotElmt = mock(classToMock);
        when(annotElmt.annotationType()).thenReturn((Class) classToMock);
        return annotElmt;
    }
}
