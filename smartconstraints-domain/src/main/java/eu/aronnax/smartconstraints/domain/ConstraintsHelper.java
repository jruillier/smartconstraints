package eu.aronnax.smartconstraints.domain;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Stream;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class ConstraintsHelper {

    @Inject
    ConstraintsHelper() {}

    Stream<Class<? extends Annotation>> getConstraintClasses() {
        return Arrays.stream(AnnotEnum.values()).map(AnnotEnum::getAnnotClass);
    }
}
