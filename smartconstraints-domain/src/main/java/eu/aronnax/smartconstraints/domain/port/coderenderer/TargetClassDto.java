package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record TargetClassDto(
        @NotNull String packageName,
        @NotNull String qualifiedName,
        @NotNull String simpleName,
        @Size(min = 1) List<TargetMetaAnnotDto> properties) {}
