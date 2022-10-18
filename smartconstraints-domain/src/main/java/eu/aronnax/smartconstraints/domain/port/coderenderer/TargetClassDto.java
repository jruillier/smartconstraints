package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.List;

public record TargetClassDto(
        String packageName, String qualifiedName, String simpleName, List<TargetMetaAnnotDto> properties) {}
