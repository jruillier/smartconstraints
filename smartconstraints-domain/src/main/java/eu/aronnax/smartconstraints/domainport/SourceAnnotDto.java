package eu.aronnax.smartconstraints.domainport;

import java.util.List;

import org.immutables.value.Value;

@Value.Immutable
public abstract class SourceAnnotDto {

    public abstract String qualifiedName();

    public abstract String simpleName();

    public abstract List<SourceParamDto> annotParams();
}
