package eu.aronnax.smartconstraints.test.entity;

import javax.validation.constraints.NotNull;

/**
 * Constrained bean should produce meta-annotations.
 */
public record PersonEntity(@NotNull String firstName, String lastName) {}
