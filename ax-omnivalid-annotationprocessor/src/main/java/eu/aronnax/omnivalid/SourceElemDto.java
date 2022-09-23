package eu.aronnax.omnivalid;

import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
abstract class SourceElemDto {

    public abstract String name();

    public abstract List<SourceAnnotDto> annots();
}
