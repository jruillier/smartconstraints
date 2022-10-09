package eu.aronnax.smartconstraints.domain;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
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
        return null;
    }

    @Override
    public List<SourceParamDto> processAssertTrue(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processDecimalMax(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processDecimalMin(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processDigits(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processEmail(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processFuture(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processFutureOrPresent(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processMax(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processMin(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processNegative(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processNegativeOrZero(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processNotBlank(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processNotEmpty(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processNotNull(Annotation annotElmt) {
        javax.validation.constraints.NotNull annot = (NotNull) annotElmt;
        List<SourceParamDto> annotParams = new ArrayList<>();
        if (!"{javax.validation.constraints.NotNull.message}".equals(annot.message())) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("message")
                    .stringValue(annot.message())
                    .build());
        }
        return annotParams;    }

    @Override
    public List<SourceParamDto> processNull(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processPast(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processPastOrPresent(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processPattern(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processPositive(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processPositiveOrZero(Annotation annotElmt) {
        return null;
    }

    @Override
    public List<SourceParamDto> processSize(Annotation annotElmt) {
        List<SourceParamDto> annotParams = new ArrayList<>();

        javax.validation.constraints.Size annot = (Size) annotElmt;
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
        if (!"{javax.validation.constraints.Size.message}".equals(annot.message())) {
            annotParams.add(ImmutableSourceParamDto.builder()
                    .name("message")
                    .stringValue(annot.message())
                    .build());
        }
        return annotParams;
    }
}
