package eu.aronnax.omnivalid;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.immutables.value.Value;

@Value.Immutable
abstract class SourceClassDto {

    @NotNull
    public abstract String packageName();

    @NotNull
    public abstract String qualifiedName();

    @NotNull
    public abstract String simpleName();

    @Size(min = 1)
    public abstract List<SourceElemDto> annotElements();
}
