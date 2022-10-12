package eu.aronnax.smartconstraints.domainport;

import java.util.List;

public record SourceAnnotDto(String qualifiedName, String simpleName, List<SourceParamDto> annotParams) {}
