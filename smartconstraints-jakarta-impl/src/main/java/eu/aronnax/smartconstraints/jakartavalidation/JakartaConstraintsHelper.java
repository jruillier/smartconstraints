package eu.aronnax.smartconstraints.jakartavalidation;

import eu.aronnax.smartconstraints.annotation.CopyJakartaConstraints;
import eu.aronnax.smartconstraints.parser.common.ConstraintsHelperPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.lang.model.element.Element;
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

    @Override
    public Class<? extends Annotation> getCopyConstraintsAnnotation() {
        return CopyJakartaConstraints.class;
    }

    @Override
    public CharSequence extractFromPackage(Element targetPackage) {
        return targetPackage.getAnnotation(CopyJakartaConstraints.class).from();
    }
}
