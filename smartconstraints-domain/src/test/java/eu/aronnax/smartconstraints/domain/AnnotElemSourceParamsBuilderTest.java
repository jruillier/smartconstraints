package eu.aronnax.smartconstraints.domain;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eu.aronnax.smartconstraints.domainport.SourceParamDto;
import eu.aronnax.smartconstraints.domainport.StringUtilsPort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnnotElemSourceParamsBuilderTest {

    @InjectMocks
    private AnnotElemSourceParamsBuilder instance;

    @Mock
    private StringUtilsPort stringUtilsPort;

    @BeforeEach
    void setUp() {
    }

    @Test
    void processAssertFalse() {
        // Prepare
        AssertFalse annotElmt = buildAnnotElmt(AssertFalse.class);
        when(annotElmt.message()).thenReturn("A AssertFalse msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A AssertFalse msg")
        ), params);
    }

    @Test
    void processAssertTrue() {
        // Prepare
        AssertTrue annotElmt = buildAnnotElmt(AssertTrue.class);
        when(annotElmt.message()).thenReturn("A AssertTrue msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A AssertTrue msg")
        ), params);
    }

    @Test
    void processDecimalMax() {
        // Prepare
        when(this.stringUtilsPort.isNotBlank(any()))
                .thenAnswer(invocation -> !Optional.ofNullable(invocation.getArgument(0, String.class))
                        .map(String::isBlank)
                        .orElse(false));

        DecimalMax annotElmt = buildAnnotElmt(DecimalMax.class);
        when(annotElmt.message()).thenReturn("A DecimalMax msg");
        when(annotElmt.value()).thenReturn("42");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("inclusive", false, null),
                new SourceParamDto("value", null, "42"),
                new SourceParamDto("message", null, "A DecimalMax msg")
        ), params);
    }

    @Test
    void processDecimalMin() {
        // Prepare
        DecimalMin annotElmt = buildAnnotElmt(DecimalMin.class);
        when(annotElmt.message()).thenReturn("A DecimalMin msg");
        when(annotElmt.value()).thenReturn("42");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("inclusive", false, null),
                new SourceParamDto("value", null, "42"),
                new SourceParamDto("message", null, "A DecimalMin msg")
        ), params);
    }

    @Test
    void processDigits() {
        // Prepare
        Digits annotElmt = buildAnnotElmt(Digits.class);
        when(annotElmt.message()).thenReturn("A Digits msg");
        when(annotElmt.integer()).thenReturn(2);
        when(annotElmt.fraction()).thenReturn(3);

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("fraction", 3, null),
                new SourceParamDto("integer", 2, null),
                new SourceParamDto("message", null, "A Digits msg")
        ), params);
    }

    @Test
    void processEmail() {
        // Prepare
        Email annotElmt = buildAnnotElmt(Email.class);
        when(annotElmt.message()).thenReturn("A Email msg");
        when(annotElmt.flags()).thenReturn(new Pattern.Flag[0]);

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A Email msg")
        ), params);
    }

    @Test
    void processFuture() {
        // Prepare
        Future annotElmt = buildAnnotElmt(Future.class);
        when(annotElmt.message()).thenReturn("A Future msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A Future msg")
        ), params);
    }

    @Test
    void processFutureOrPresent() {
        // Prepare
        FutureOrPresent annotElmt = buildAnnotElmt(FutureOrPresent.class);
        when(annotElmt.message()).thenReturn("A FutureOrPresent msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A FutureOrPresent msg")
        ), params);
    }

    @Test
    void processMax() {
        // Prepare
        Max annotElmt = buildAnnotElmt(Max.class);
        when(annotElmt.message()).thenReturn("A Max msg");
        when(annotElmt.value()).thenReturn(5L);

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("value", 5L, null),
                new SourceParamDto("message", null, "A Max msg")
        ), params);
    }

    @Test
    void processMin() {
        // Prepare
        Min annotElmt = buildAnnotElmt(Min.class);
        when(annotElmt.message()).thenReturn("A Min msg");
        when(annotElmt.value()).thenReturn(5L);

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("value", 5L, null),
                new SourceParamDto("message", null, "A Min msg")
        ), params);
    }

    @Test
    void processNegative() {
        // Prepare
        Negative annotElmt = buildAnnotElmt(Negative.class);
        when(annotElmt.message()).thenReturn("A Negative msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A Negative msg")
        ), params);
    }

    @Test
    void processNegativeOrZero() {
        // Prepare
        NegativeOrZero annotElmt = buildAnnotElmt(NegativeOrZero.class);
        when(annotElmt.message()).thenReturn("A NegativeOrZero msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A NegativeOrZero msg")
        ), params);
    }

    @Test
    void processNotBlank() {
        // Prepare
        NotBlank annotElmt = buildAnnotElmt(NotBlank.class);
        when(annotElmt.message()).thenReturn("A NotBlank msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A NotBlank msg")
        ), params);
    }

    @Test
    void processNotEmpty() {
        // Prepare
        NotEmpty annotElmt = buildAnnotElmt(NotEmpty.class);
        when(annotElmt.message()).thenReturn("A NotEmpty msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A NotEmpty msg")
        ), params);
    }

    @Test
    void processNotNull() {
        // Prepare
        NotNull annotElmt = buildAnnotElmt(NotNull.class);
        when(annotElmt.message()).thenReturn("A NotNull msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A NotNull msg")
        ), params);
    }

    @Test
    void processNull() {
        // Prepare
        Null annotElmt = buildAnnotElmt(Null.class);
        when(annotElmt.message()).thenReturn("A Null msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A Null msg")
        ), params);
    }

    @Test
    void processPast() {
        // Prepare
        Past annotElmt = buildAnnotElmt(Past.class);
        when(annotElmt.message()).thenReturn("A Past msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A Past msg")
        ), params);
    }

    @Test
    void processPastOrPresent() {
        // Prepare
        PastOrPresent annotElmt = buildAnnotElmt(PastOrPresent.class);
        when(annotElmt.message()).thenReturn("A PastOrPresent msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A PastOrPresent msg")
        ), params);
    }

    @Test
    void processPattern() {
        // Prepare
        Pattern annotElmt = buildAnnotElmt(Pattern.class);
        when(annotElmt.message()).thenReturn("A Pattern msg");
        when(annotElmt.regexp()).thenReturn("aSmartRegexp");
        when(annotElmt.flags()).thenReturn(new Pattern.Flag[0]);

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("regexp", null, "aSmartRegexp"),
                new SourceParamDto("message", null, "A Pattern msg")
        ), params);
    }

    @Test
    void processPositive() {
        // Prepare
        Positive annotElmt = buildAnnotElmt(Positive.class);
        when(annotElmt.message()).thenReturn("A Positive msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A Positive msg")
        ), params);
    }

    @Test
    void processPositiveOrZero() {
        // Prepare
        PositiveOrZero annotElmt = buildAnnotElmt(PositiveOrZero.class);
        when(annotElmt.message()).thenReturn("A PositiveOrZero msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A PositiveOrZero msg")
        ), params);
    }

    @Test
    void processSize() {
        // Prepare
        Size annotElmt = buildAnnotElmt(Size.class);
        when(annotElmt.message()).thenReturn("A Size msg");

        // Run
        List<SourceParamDto> params = instance.process(annotElmt);

        // Verify
        assertEquals(Arrays.asList(
                new SourceParamDto("message", null, "A Size msg")
        ), params);
    }

    private <R extends Annotation> R buildAnnotElmt(Class<R> classToMock) {
        R annotElmt = mock(classToMock);
        when(annotElmt.annotationType()).thenReturn((Class) classToMock);
        return annotElmt;
    }
}