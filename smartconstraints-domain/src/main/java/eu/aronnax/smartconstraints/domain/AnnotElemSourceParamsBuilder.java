package eu.aronnax.smartconstraints.domain;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

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

import eu.aronnax.smartconstraints.domainport.ImmutableSourceParamDto;
import eu.aronnax.smartconstraints.domainport.SourceParamDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AnnotElemSourceParamsBuilder implements AnnotElementProcessor<List<SourceParamDto>> {

    @Inject
    public AnnotElemSourceParamsBuilder() {
    }

    @Override
    public List<SourceParamDto> processAssertFalse(Annotation annotElmt) {
        AssertFalse annot = (AssertFalse) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, AssertFalse.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processAssertTrue(Annotation annotElmt) {
        AssertTrue annot = (AssertTrue) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, AssertTrue.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processDecimalMax(Annotation annotElmt) {
        DecimalMax annot = (DecimalMax) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, DecimalMax.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processDecimalMin(Annotation annotElmt) {
        DecimalMin annot = (DecimalMin) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, DecimalMin.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processDigits(Annotation annotElmt) {
        Digits annot = (Digits) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Digits.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processEmail(Annotation annotElmt) {
        Email annot = (Email) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Email.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processFuture(Annotation annotElmt) {
        Future annot = (Future) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Future.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processFutureOrPresent(Annotation annotElmt) {
        FutureOrPresent annot = (FutureOrPresent) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, FutureOrPresent.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processMax(Annotation annotElmt) {
        Max annot = (Max) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Max.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processMin(Annotation annotElmt) {
        Min annot = (Min) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Min.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processNegative(Annotation annotElmt) {
        Negative annot = (Negative) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Negative.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processNegativeOrZero(Annotation annotElmt) {
        NegativeOrZero annot = (NegativeOrZero) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NegativeOrZero.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processNotBlank(Annotation annotElmt) {
        NotBlank annot = (NotBlank) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NotBlank.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processNotEmpty(Annotation annotElmt) {
        NotEmpty annot = (NotEmpty) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NotEmpty.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processNotNull(Annotation annotElmt) {
        NotNull annot = (NotNull) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NotNull.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processNull(Annotation annotElmt) {
        Null annot = (Null) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Null.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processPast(Annotation annotElmt) {
        Past annot = (Past) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Past.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processPastOrPresent(Annotation annotElmt) {
        PastOrPresent annot = (PastOrPresent) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, PastOrPresent.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processPattern(Annotation annotElmt) {
        Pattern annot = (Pattern) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Pattern.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processPositive(Annotation annotElmt) {
        Positive annot = (Positive) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Positive.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processPositiveOrZero(Annotation annotElmt) {
        PositiveOrZero annot = (PositiveOrZero) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, PositiveOrZero.class, annot.message());
        return annotParams;
    }

    @Override
    public List<SourceParamDto> processSize(Annotation annotElmt) {
        List<SourceParamDto> annotParams = new ArrayList<>();

        Size annot = (Size) annotElmt;
        if (annot.min() != 0) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("min")
                    .nonStringValue(annot.min())
                    .build());
        }
        if (annot.max() != 0) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("max")
                    .nonStringValue(annot.max())
                    .build());
        }
        this.addMessageParam(annotParams, Size.class, annot.message());
        return annotParams;
    }

    private void addMessageParam(List<SourceParamDto> annotParams, Class<? extends Annotation> annotClass, String message) {
        if (!this.buildMessageKeyForParam(annotClass, "message").equals(message)) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("message")
                    .stringValue(message)
                    .build());
        }
    }

    private String buildMessageKeyForParam(Class<? extends Annotation> annotClass, String paramName) {
        return "{" + annotClass.getName() + "." + paramName + "}";
    }
}
