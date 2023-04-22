package eu.aronnax.smartconstraints.test.entity;

import javax.validation.constraints.NotNull;

/**
 * Constrained bean should produce composed annotations.
 */
public record PersonEntity(@NotNull String firstName, String lastName) {}
