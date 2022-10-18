package eu.aronnax.smartconstraints.domain.usecase;

import java.util.List;

record SourcePropertyDto(String propertyName, List<SourceAnnotDto> annots) {}
