package eu.aronnax.smartconstraints.domain;

import java.lang.annotation.Annotation;

interface AnnotElementVisitor<R> {

    R processAssertFalse(Annotation annotElmt);
    R processAssertTrue(Annotation annotElmt);
    R processDecimalMax(Annotation annotElmt);
    R processDecimalMin(Annotation annotElmt);
    R processDigits(Annotation annotElmt);
    R processEmail(Annotation annotElmt);
    R processFuture(Annotation annotElmt);
    R processFutureOrPresent(Annotation annotElmt);
    R processMax(Annotation annotElmt);
    R processMin(Annotation annotElmt);
    R processNegative(Annotation annotElmt);
    R processNegativeOrZero(Annotation annotElmt);
    R processNotBlank(Annotation annotElmt);
    R processNotEmpty(Annotation annotElmt);
    R processNotNull(Annotation annotElmt);
    R processNull(Annotation annotElmt);
    R processPast(Annotation annotElmt);
    R processPastOrPresent(Annotation annotElmt);
    R processPattern(Annotation annotElmt);
    R processPositive(Annotation annotElmt);
    R processPositiveOrZero(Annotation annotElmt);
    R processSize(Annotation annotElmt);

}
