package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.List;

public record SourceEntityDto(
        String classQualifiedName, List<SourcePropertyDto> sourceProperties, String targetPackage) {}
