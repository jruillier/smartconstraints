package eu.aronnax.smartconstraints.domain.port.sourcerenderer;

import java.util.List;

public record SourcePropertyDto(String name, List<SourceAnnotDto> annots) {}
