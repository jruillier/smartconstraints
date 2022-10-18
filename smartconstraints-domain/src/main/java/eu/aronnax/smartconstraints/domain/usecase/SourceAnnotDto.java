package eu.aronnax.smartconstraints.domain.usecase;

import java.util.List;

record SourceAnnotDto(String name, String simpleName, List<SourceAnnotParamDto> params) {}
