package eu.aronnax.smartconstraints.domain.usecase;

import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotParamDto;
import eu.aronnax.smartconstraints.domain.port.stringutils.StringUtilsPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.*;

@ApplicationScoped
class AnnotElemSourceParamsBuilder {

    private final StringUtilsPort stringUtils;

    @Inject
    public AnnotElemSourceParamsBuilder(StringUtilsPort stringUtils) {
        this.stringUtils = stringUtils;
    }

    public List<TargetAnnotParamDto> process(Annotation annotElmt) {
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
                })
                .orElse(Collections.emptyList());
    }

    private List<TargetAnnotParamDto> processAssertFalse(Annotation annotElmt) {
        AssertFalse annot = (AssertFalse) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, AssertFalse.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processAssertTrue(Annotation annotElmt) {
        AssertTrue annot = (AssertTrue) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, AssertTrue.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processDecimalMax(Annotation annotElmt) {
        DecimalMax annot = (DecimalMax) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        if (!annot.inclusive()) {
            annotParams.add(new TargetAnnotParamDto("inclusive", annot.inclusive(), null));
        }
        if (this.stringUtils.isNotBlank(annot.value())) {
            annotParams.add(new TargetAnnotParamDto("value", null, annot.value()));
        }
        this.addMessageParam(annotParams, DecimalMax.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processDecimalMin(Annotation annotElmt) {
        DecimalMin annot = (DecimalMin) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        if (!annot.inclusive()) {
            annotParams.add(new TargetAnnotParamDto("inclusive", annot.inclusive(), null));
        }
        annotParams.add(new TargetAnnotParamDto("value", null, annot.value()));
        this.addMessageParam(annotParams, DecimalMin.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processDigits(Annotation annotElmt) {
        Digits annot = (Digits) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        annotParams.add(new TargetAnnotParamDto("fraction", annot.fraction(), null));
        annotParams.add(new TargetAnnotParamDto("integer", annot.integer(), null));
        this.addMessageParam(annotParams, Digits.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processEmail(Annotation annotElmt) {
        Email annot = (Email) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        if (".*".equals(annot.regexp())) {
            annotParams.add(new TargetAnnotParamDto("regexp", null, annot.regexp()));
        }
        if (annot.flags().length != 0) {
            annotParams.add(new TargetAnnotParamDto("flags", annot.flags(), null));
        }
        this.addMessageParam(annotParams, Email.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processFuture(Annotation annotElmt) {
        Future annot = (Future) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Future.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processFutureOrPresent(Annotation annotElmt) {
        FutureOrPresent annot = (FutureOrPresent) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, FutureOrPresent.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processMax(Annotation annotElmt) {
        Max annot = (Max) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        annotParams.add(new TargetAnnotParamDto("value", annot.value(), null));
        this.addMessageParam(annotParams, Max.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processMin(Annotation annotElmt) {
        Min annot = (Min) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        annotParams.add(new TargetAnnotParamDto("value", annot.value(), null));
        this.addMessageParam(annotParams, Min.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processNegative(Annotation annotElmt) {
        Negative annot = (Negative) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Negative.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processNegativeOrZero(Annotation annotElmt) {
        NegativeOrZero annot = (NegativeOrZero) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NegativeOrZero.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processNotBlank(Annotation annotElmt) {
        NotBlank annot = (NotBlank) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NotBlank.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processNotEmpty(Annotation annotElmt) {
        NotEmpty annot = (NotEmpty) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NotEmpty.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processNotNull(Annotation annotElmt) {
        NotNull annot = (NotNull) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, NotNull.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processNull(Annotation annotElmt) {
        Null annot = (Null) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Null.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processPast(Annotation annotElmt) {
        Past annot = (Past) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Past.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processPastOrPresent(Annotation annotElmt) {
        PastOrPresent annot = (PastOrPresent) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, PastOrPresent.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processPattern(Annotation annotElmt) {
        Pattern annot = (Pattern) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        annotParams.add(new TargetAnnotParamDto("regexp", null, annot.regexp()));
        if (annot.flags().length != 0) {
            annotParams.add(new TargetAnnotParamDto("flags", annot.flags(), null));
        }
        this.addMessageParam(annotParams, Pattern.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processPositive(Annotation annotElmt) {
        Positive annot = (Positive) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, Positive.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processPositiveOrZero(Annotation annotElmt) {
        PositiveOrZero annot = (PositiveOrZero) annotElmt;
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        this.addMessageParam(annotParams, PositiveOrZero.class, annot.message());
        return annotParams;
    }

    private List<TargetAnnotParamDto> processSize(Annotation annotElmt) {
        List<TargetAnnotParamDto> annotParams = new ArrayList<>();
        Size annot = (Size) annotElmt;
        if (annot.min() != 0) {
            annotParams.add(new TargetAnnotParamDto("min", annot.min(), null));
        }
        if (annot.max() != 0) {
            annotParams.add(new TargetAnnotParamDto("max", annot.max(), null));
        }
        this.addMessageParam(annotParams, Size.class, annot.message());
        return annotParams;
    }

    private void addMessageParam(
            List<TargetAnnotParamDto> annotParams, Class<? extends Annotation> annotClass, String message) {
        if (!this.buildMessageKeyForParam(annotClass, "message").equals(message)) {
            annotParams.add(new TargetAnnotParamDto("message", null, message));
        }
    }

    private String buildMessageKeyForParam(Class<? extends Annotation> annotClass, String paramName) {
        return "{" + annotClass.getName() + "." + paramName + "}";
    }
}
