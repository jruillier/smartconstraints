package eu.aronnax.smartconstraints.domainport;

import java.util.List;

public record SourcePropertyDto(String name, List<SourceAnnotDto> annots) {}
