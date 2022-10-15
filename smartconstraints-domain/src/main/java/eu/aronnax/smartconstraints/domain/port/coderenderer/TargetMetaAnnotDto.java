package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.List;

public record TargetMetaAnnotDto(String name, List<TargetAnnotDto> annots) {}
