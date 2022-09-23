package eu.aronnax.omnivalid;

import org.immutables.value.Value;

@Value.Immutable
public abstract class RenderingDto {

    public abstract String packageName();

    public abstract String classSimpleName();

    public abstract String sourceRendering();
}
