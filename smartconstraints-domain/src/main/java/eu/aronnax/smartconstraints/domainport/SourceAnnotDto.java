package eu.aronnax.smartconstraints.domainport;

import org.immutables.value.Value;

@Value.Immutable
public abstract class SourceAnnotDto {

    public abstract String qualifiedName();

    public abstract String simpleName();
}