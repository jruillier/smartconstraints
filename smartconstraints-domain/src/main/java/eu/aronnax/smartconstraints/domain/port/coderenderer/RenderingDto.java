package eu.aronnax.smartconstraints.domain.port.coderenderer;

public record RenderingDto(
        String packageName, String classSimpleName, String classQualifiedName, String sourceRendering) {}
