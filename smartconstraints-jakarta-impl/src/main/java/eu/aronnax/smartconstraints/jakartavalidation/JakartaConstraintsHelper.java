package eu.aronnax.smartconstraints.jakartavalidation;

import eu.aronnax.smartconstraints.parser.common.ConstraintsHelperPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Stream;

@ApplicationScoped
class JakartaConstraintsHelper implements ConstraintsHelperPort {

    @Inject
    JakartaConstraintsHelper() {}

    public Stream<Class<? extends Annotation>> getConstraintClasses() {
        return Arrays.stream(JakartaAnnotEnum.values()).map(JakartaAnnotEnum::getAnnotClass);
    }
}
