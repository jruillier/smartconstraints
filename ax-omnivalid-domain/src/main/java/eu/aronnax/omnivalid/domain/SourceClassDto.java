package eu.aronnax.omnivalid.domain;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.immutables.value.Value;

@Value.Immutable
public abstract class SourceClassDto {

    @NotNull
    public abstract String packageName();

    @NotNull
    public abstract String qualifiedName();

    @NotNull
    public abstract String simpleName();

    @Size(min = 1)
    public abstract List<SourcePropertyDto> properties();
}
