package eu.aronnax.smartconstraints.domain;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

enum SourceBuilderForAnnot {

    NotNull(javax.validation.constraints.NotNull.class) {
        @Override
        <R> R process(AnnotElementProcessor<R> elemProc, Annotation annotElmt) {
            return elemProc.processNotNull(annotElmt);
        }
    },

    Size(javax.validation.constraints.Size.class) {
        @Override
        <R> R process(AnnotElementProcessor<R> elemProc, Annotation annotElmt) {
            return elemProc.processSize(annotElmt);
        }
    };

    static Optional<SourceBuilderForAnnot> getByAnnotType(Class<? extends Annotation> annotType) {
        return Arrays.stream(values())
                .filter((value) -> annotType.equals(value.getAnnotClass()))
                .findAny();
    }

    private final Class<? extends Annotation> annotClass;

    SourceBuilderForAnnot(Class<? extends Annotation> annotClass) {
        this.annotClass = annotClass;
    }

    public Class<? extends Annotation> getAnnotClass() {
        return this.annotClass;
    }

    abstract <R> R process(AnnotElementProcessor<R> elemProc, Annotation annotElmt);

}
