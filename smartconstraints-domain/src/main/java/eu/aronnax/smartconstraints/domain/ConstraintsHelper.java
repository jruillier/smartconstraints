package eu.aronnax.smartconstraints.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;
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

@ApplicationScoped
class ConstraintsHelper {

    @Inject
    ConstraintsHelper() {}

    Stream<Class<? extends Annotation>> getConstraintClasses() {
        return Stream.of(
                AssertFalse.class,
                AssertTrue.class,
                DecimalMax.class,
                DecimalMin.class,
                Digits.class,
                Email.class,
                Future.class,
                FutureOrPresent.class,
                Max.class,
                Min.class,
                Negative.class,
                NegativeOrZero.class,
                NotBlank.class,
                NotEmpty.class,
                NotNull.class,
                Null.class,
                Past.class,
                PastOrPresent.class,
                Pattern.class,
                Positive.class,
                PositiveOrZero.class,
                Size.class);
    }
}
