package eu.aronnax.smartconstraints.domainport;

import org.immutables.value.Value;

@Value.Immutable
public interface SourceParamDto {

    String name();

    Object value();

}
