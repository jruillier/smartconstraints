package eu.aronnax.smartconstraints.jakartavalidation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

enum JakartaAnnotEnum {
    AssertFalse(jakarta.validation.constraints.AssertFalse.class) {},

    AssertTrue(jakarta.validation.constraints.AssertTrue.class) {},

    DecimalMax(jakarta.validation.constraints.DecimalMax.class) {},

    DecimalMin(jakarta.validation.constraints.DecimalMin.class) {},

    Digits(jakarta.validation.constraints.Digits.class) {},

    Email(jakarta.validation.constraints.Email.class) {},

    Future(jakarta.validation.constraints.Future.class) {},

    FutureOrPresent(jakarta.validation.constraints.FutureOrPresent.class) {},

    Max(jakarta.validation.constraints.Max.class) {},

    Min(jakarta.validation.constraints.Min.class) {},

    Negative(jakarta.validation.constraints.Negative.class) {},

    NegativeOrZero(jakarta.validation.constraints.NegativeOrZero.class) {},

    NotBlank(jakarta.validation.constraints.NotBlank.class) {},

    NotEmpty(jakarta.validation.constraints.NotEmpty.class) {},

    NotNull(jakarta.validation.constraints.NotNull.class) {},

    Null(jakarta.validation.constraints.Null.class) {},

    Past(jakarta.validation.constraints.Past.class) {},

    PastOrPresent(jakarta.validation.constraints.PastOrPresent.class) {},

    Pattern(jakarta.validation.constraints.Pattern.class) {},

    Positive(jakarta.validation.constraints.Positive.class) {},

    PositiveOrZero(jakarta.validation.constraints.PositiveOrZero.class) {},

    Size(jakarta.validation.constraints.Size.class) {};

    private final Class<? extends Annotation> annotClass;

    JakartaAnnotEnum(Class<? extends Annotation> annotClass) {
        this.annotClass = annotClass;
    }

    static Optional<JakartaAnnotEnum> getByAnnotType(Class<? extends Annotation> annotType) {
        return Arrays.stream(values())
                .filter((value) -> annotType.equals(value.getAnnotClass()))
                .findAny();
    }

    public Class<? extends Annotation> getAnnotClass() {
        return this.annotClass;
    }
}
