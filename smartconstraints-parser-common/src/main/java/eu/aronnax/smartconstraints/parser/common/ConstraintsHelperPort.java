package eu.aronnax.smartconstraints.parser.common;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

public interface ConstraintsHelperPort {

    Stream<Class<? extends Annotation>> getConstraintClasses();
}
