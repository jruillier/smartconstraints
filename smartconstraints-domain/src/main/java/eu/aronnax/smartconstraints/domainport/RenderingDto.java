package eu.aronnax.smartconstraints.domainport;

import org.immutables.value.Value;

@Value.Immutable
public abstract class RenderingDto {

    abstract String packageName();

    abstract String classSimpleName();

    public abstract String classQualifiedName();

    public abstract String sourceRendering();
}
