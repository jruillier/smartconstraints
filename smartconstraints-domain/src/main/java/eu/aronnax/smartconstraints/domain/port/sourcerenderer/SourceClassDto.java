package eu.aronnax.smartconstraints.domain.port.sourcerenderer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public record SourceClassDto(
        @NotNull String packageName,
        @NotNull String qualifiedName,
        @NotNull String simpleName,
        @Size(min = 1) List<SourcePropertyDto> properties) {}
