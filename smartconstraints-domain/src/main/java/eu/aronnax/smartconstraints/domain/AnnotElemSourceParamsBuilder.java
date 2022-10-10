package eu.aronnax.smartconstraints.domain;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
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
import eu.aronnax.smartconstraints.domainport.StringUtilsPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class AnnotElemSourceParamsBuilder {

    private final StringUtilsPort stringUtils;

    @Inject
    public AnnotElemSourceParamsBuilder(StringUtilsPort stringUtils) {
        this.stringUtils = stringUtils;
    }

    public List<SourceParamDto> process(Annotation annotElmt) {
        return AnnotEnum.getByAnnotType(annotElmt.annotationType())
                .map(annotEnum -> switch (annotEnum) {
                    case AssertFalse -> this.processAssertFalse(annotElmt);
                    case AssertTrue -> this.processAssertTrue(annotElmt);
                    case DecimalMax -> this.processDecimalMax(annotElmt);
                    case DecimalMin -> this.processDecimalMin(annotElmt);
                    case Digits -> this.processDigits(annotElmt);
                    case Email -> this.processEmail(annotElmt);
                    case Future -> this.processFuture(annotElmt);
                    case FutureOrPresent -> this.processFutureOrPresent(annotElmt);
                    case Max -> this.processMax(annotElmt);
                    case Min -> this.processMin(annotElmt);
                    case Negative -> this.processNegative(annotElmt);
                    case NegativeOrZero -> this.processNegativeOrZero(annotElmt);
                    case NotBlank -> this.processNotBlank(annotElmt);
                    case NotEmpty -> this.processNotEmpty(annotElmt);
                    case NotNull -> this.processNotNull(annotElmt);
                    case Null -> this.processNull(annotElmt);
                    case Past -> this.processPast(annotElmt);
                    case PastOrPresent -> this.processPastOrPresent(annotElmt);
                    case Pattern -> this.processPattern(annotElmt);
                    case Positive -> this.processPositive(annotElmt);
                    case PositiveOrZero -> this.processPositiveOrZero(annotElmt);
                    case Size -> this.processSize(annotElmt);
                }).orElse(Collections.emptyList());
    }

    private List<SourceParamDto> processAssertFalse(Annotation annotElmt) {
        AssertFalse annot = (AssertFalse) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, AssertFalse.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processAssertTrue(Annotation annotElmt) {
        AssertTrue annot = (AssertTrue) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, AssertTrue.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processDecimalMax(Annotation annotElmt) {
        DecimalMax annot = (DecimalMax) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        if (!annot.inclusive()) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("inclusive")
                    .nonStringValue(annot.inclusive())
                    .build());
        }
        if (this.stringUtils.isNotBlank(annot.value())) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("value")
                    .stringValue(annot.value())
                    .build());
        }
        this.addMessageParam(annotParams, DecimalMax.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processDecimalMin(Annotation annotElmt) {
        DecimalMin annot = (DecimalMin) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        if (!annot.inclusive()) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("inclusive")
                    .nonStringValue(annot.inclusive())
                    .build());
        }
        annotParams.add(ImmutableSourceParamDto.builder()
                .name("value")
                .stringValue(annot.value())
                .build());
        this.addMessageParam(annotParams, DecimalMin.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processDigits(Annotation annotElmt) {
        Digits annot = (Digits) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        annotParams.add(ImmutableSourceParamDto.builder()
                .name("fraction")
                .nonStringValue(annot.fraction())
                .build());
        annotParams.add(ImmutableSourceParamDto.builder()
                .name("integer")
                .nonStringValue(annot.integer())
                .build());
        this.addMessageParam(annotParams, Digits.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processEmail(Annotation annotElmt) {
        Email annot = (Email) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        if (".*".equals(annot.regexp())) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("regexp")
                    .nonStringValue(annot.regexp())
                    .build());
        }
        if (annot.flags().length != 0) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("flags")
                    .nonStringValue(annot.flags())
                    .build());
        }
        this.addMessageParam(annotParams, Email.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processFuture(Annotation annotElmt) {
        Future annot = (Future) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Future.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processFutureOrPresent(Annotation annotElmt) {
        FutureOrPresent annot = (FutureOrPresent) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, FutureOrPresent.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processMax(Annotation annotElmt) {
        Max annot = (Max) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        annotParams.add(ImmutableSourceParamDto.builder()
                .name("value")
                .nonStringValue(annot.value())
                .build());
        this.addMessageParam(annotParams, Max.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processMin(Annotation annotElmt) {
        Min annot = (Min) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        annotParams.add(ImmutableSourceParamDto.builder()
                .name("value")
                .nonStringValue(annot.value())
                .build());
        this.addMessageParam(annotParams, Min.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processNegative(Annotation annotElmt) {
        Negative annot = (Negative) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Negative.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processNegativeOrZero(Annotation annotElmt) {
        NegativeOrZero annot = (NegativeOrZero) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NegativeOrZero.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processNotBlank(Annotation annotElmt) {
        NotBlank annot = (NotBlank) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NotBlank.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processNotEmpty(Annotation annotElmt) {
        NotEmpty annot = (NotEmpty) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NotEmpty.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processNotNull(Annotation annotElmt) {
        NotNull annot = (NotNull) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NotNull.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processNull(Annotation annotElmt) {
        Null annot = (Null) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Null.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processPast(Annotation annotElmt) {
        Past annot = (Past) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Past.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processPastOrPresent(Annotation annotElmt) {
        PastOrPresent annot = (PastOrPresent) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, PastOrPresent.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processPattern(Annotation annotElmt) {
        Pattern annot = (Pattern) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        annotParams.add(ImmutableSourceParamDto.builder()
                .name("regexp")
                .nonStringValue(annot.regexp())
                .build());
        if (annot.flags().length != 0) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("flags")
                    .nonStringValue(annot.flags())
                    .build());
        }
        this.addMessageParam(annotParams, Pattern.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processPositive(Annotation annotElmt) {
        Positive annot = (Positive) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Positive.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processPositiveOrZero(Annotation annotElmt) {
        PositiveOrZero annot = (PositiveOrZero) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, PositiveOrZero.class, annot.message());
        return annotParams;
    }

    private List<SourceParamDto> processSize(Annotation annotElmt) {
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
