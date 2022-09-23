package eu.aronnax.omnivalid;

import org.immutables.value.Value;

@Value.Immutable
abstract class SourceAnnotDto {

    public abstract String qualifiedName();

    public abstract String simpleName();
}
