package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.List;

public record SourcePropertyDto(String propertyName, List<SourceAnnotDto> annots) {}
