// package eu.aronnax.smartconstraints;
//
// import java.lang.annotation.Documented;
// import java.lang.annotation.Retention;
// import java.lang.annotation.Target;
//
// import javax.validation.Constraint;
// import javax.validation.Payload;
// import javax.validation.constraints.NotNull;
// import javax.validation.constraints.Pattern;
// import javax.validation.constraints.Size;
//
// import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
// import static java.lang.annotation.ElementType.CONSTRUCTOR;
// import static java.lang.annotation.ElementType.FIELD;
// import static java.lang.annotation.ElementType.METHOD;
// import static java.lang.annotation.ElementType.PARAMETER;
// import static java.lang.annotation.RetentionPolicy.RUNTIME;
//
// @NotNull
// @Pattern(regexp = ".*\\d.*", message = "must contain at least one numeric character")
// @Size(min = 6, max = 32, message = "must have between 6 and 32 characters")
// @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
// @Retention(RUNTIME)
// @Documented
// @Constraint(validatedBy = {})
// public @interface ValidAlphanumeric {
//
//    String message() default "field should have a valid length and contain numeric character(s).";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
// }
