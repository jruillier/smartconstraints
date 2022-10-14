package eu.aronnax.smartconstraints.domain.port.sourcerenderer;

import java.util.List;

public record SourceAnnotDto(String qualifiedName, String simpleName, List<SourceParamDto> annotParams) {}
