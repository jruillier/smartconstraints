package eu.aronnax.smartconstraints.parser.common;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;
import javax.lang.model.element.Element;

public interface ConstraintsHelperPort {

    Stream<Class<? extends Annotation>> getConstraintClasses();

    Class<? extends Annotation> getCopyConstraintsAnnotation();

    CharSequence extractFromPackage(Element targetPackage);
}
