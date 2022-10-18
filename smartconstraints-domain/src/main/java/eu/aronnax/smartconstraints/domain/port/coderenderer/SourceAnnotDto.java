package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.List;

public record SourceAnnotDto(String name, String simpleName, List<SourceAnnotParamDto> params) {}
