package eu.aronnax.smartconstraints.domain.usecase;

import java.util.List;

record SourceEntityDto(String classQualifiedName, List<SourcePropertyDto> sourceProperties, String targetPackage) {}
