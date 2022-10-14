package eu.aronnax.smartconstraints.domain.port.sourcerenderer;

public record RenderingDto(
        String packageName, String classSimpleName, String classQualifiedName, String sourceRendering) {}
