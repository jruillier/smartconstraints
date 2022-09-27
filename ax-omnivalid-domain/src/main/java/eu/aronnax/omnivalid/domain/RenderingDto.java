package eu.aronnax.omnivalid.domain;

import org.immutables.value.Value;

@Value.Immutable
public abstract class RenderingDto {

    public abstract String packageName();

    public abstract String classSimpleName();

    public abstract String classQualifiedName();

    public abstract String sourceRendering();
}
