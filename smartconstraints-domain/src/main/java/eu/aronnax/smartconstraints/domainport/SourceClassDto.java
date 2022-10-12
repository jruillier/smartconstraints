package eu.aronnax.smartconstraints.domainport;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record SourceClassDto(
        @NotNull String packageName,
        @NotNull String qualifiedName,
        @NotNull String simpleName,
        @Size(min = 1) List<SourcePropertyDto> properties) {}
