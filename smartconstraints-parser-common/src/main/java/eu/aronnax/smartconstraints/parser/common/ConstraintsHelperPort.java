package eu.aronnax.smartconstraints.parser.common;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;

public interface ConstraintsHelperPort {

    Stream<Class<? extends Annotation>> getConstraintClasses();

    Class<? extends Annotation> getCopyConstraintsAnnotation();

    CharSequence extractFromPackage(Element targetPackage);
}
