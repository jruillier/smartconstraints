package eu.aronnax.smartconstraints.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Stream;

@ApplicationScoped
class ConstraintsHelper {

    @Inject
    ConstraintsHelper() {}

    Stream<Class<? extends Annotation>> getConstraintClasses() {
        return Arrays.stream(AnnotEnum.values()).map(AnnotEnum::getAnnotClass);
    }
}
