package eu.aronnax.smartconstraints.domainport;

public record RenderingDto(
        String packageName, String classSimpleName, String classQualifiedName, String sourceRendering) {}
