package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.List;

public record TargetAnnotDto(String qualifiedName, String simpleName, List<TargetAnnotParamDto> annotParams) {}
