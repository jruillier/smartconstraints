package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.List;

public record TargetComposedAnnotDto(
        String name, TargetAnnotDto constraintAnnot, TargetAnyClassDto payload, List<TargetAnnotDto> annots) {}
