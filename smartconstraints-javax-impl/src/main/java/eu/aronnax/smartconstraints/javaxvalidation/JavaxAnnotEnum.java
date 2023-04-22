package eu.aronnax.smartconstraints.javaxvalidation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

enum JavaxAnnotEnum {
    AssertFalse(javax.validation.constraints.AssertFalse.class) {},

    AssertTrue(javax.validation.constraints.AssertTrue.class) {},

    DecimalMax(javax.validation.constraints.DecimalMax.class) {},

    DecimalMin(javax.validation.constraints.DecimalMin.class) {},

    Digits(javax.validation.constraints.Digits.class) {},

    Email(javax.validation.constraints.Email.class) {},

    Future(javax.validation.constraints.Future.class) {},

    FutureOrPresent(javax.validation.constraints.FutureOrPresent.class) {},

    Max(javax.validation.constraints.Max.class) {},

    Min(javax.validation.constraints.Min.class) {},

    Negative(javax.validation.constraints.Negative.class) {},

    NegativeOrZero(javax.validation.constraints.NegativeOrZero.class) {},

    NotBlank(javax.validation.constraints.NotBlank.class) {},

    NotEmpty(javax.validation.constraints.NotEmpty.class) {},

    NotNull(javax.validation.constraints.NotNull.class) {},

    Null(javax.validation.constraints.Null.class) {},

    Past(javax.validation.constraints.Past.class) {},

    PastOrPresent(javax.validation.constraints.PastOrPresent.class) {},

    Pattern(javax.validation.constraints.Pattern.class) {},

    Positive(javax.validation.constraints.Positive.class) {},

    PositiveOrZero(javax.validation.constraints.PositiveOrZero.class) {},

    Size(javax.validation.constraints.Size.class) {};

    private final Class<? extends Annotation> annotClass;

    JavaxAnnotEnum(Class<? extends Annotation> annotClass) {
        this.annotClass = annotClass;
    }

    static Optional<JavaxAnnotEnum> getByAnnotType(Class<? extends Annotation> annotType) {
        return Arrays.stream(values())
                .filter((value) -> annotType.equals(value.getAnnotClass()))
                .findAny();
    }

    public Class<? extends Annotation> getAnnotClass() {
        return this.annotClass;
    }
}
