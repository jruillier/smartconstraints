package eu.aronnax.smartconstraints.domainport;

import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
public abstract class SourcePropertyDto {

    public abstract String name();

    public abstract List<SourceAnnotDto> annots();
}