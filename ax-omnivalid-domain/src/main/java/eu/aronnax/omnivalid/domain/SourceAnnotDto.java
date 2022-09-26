package eu.aronnax.omnivalid.domain;

import org.immutables.value.Value;

@Value.Immutable
public abstract class SourceAnnotDto {

    public abstract String qualifiedName();

    public abstract String simpleName();
}
