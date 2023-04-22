package eu.aronnax.smartconstraints.javaxvalidation;

import eu.aronnax.smartconstraints.annotation.CopyJavaxConstraints;
import eu.aronnax.smartconstraints.parser.common.ConstraintsHelperPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.lang.model.element.Element;

@ApplicationScoped
class JavaxConstraintsHelper implements ConstraintsHelperPort {

    @Inject
    JavaxConstraintsHelper() {}

    public Stream<Class<? extends Annotation>> getConstraintClasses() {
        return Arrays.stream(JavaxAnnotEnum.values()).map(JavaxAnnotEnum::getAnnotClass);
    }

    @Override
    public Class<? extends Annotation> getCopyConstraintsAnnotation() {
        return CopyJavaxConstraints.class;
    }

    @Override
    public CharSequence extractFromPackage(Element targetPackage) {
        return targetPackage.getAnnotation(CopyJavaxConstraints.class).from();
    }
}
