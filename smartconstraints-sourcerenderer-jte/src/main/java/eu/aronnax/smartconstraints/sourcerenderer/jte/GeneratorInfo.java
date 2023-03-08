package eu.aronnax.smartconstraints.sourcerenderer.jte;

import java.time.ZonedDateTime;

/**
 * Holds generator related data. Used in JTE template.
 * @param name FQCN of the generator
 * @param dateTime generation date+time
 */
public record GeneratorInfo(String name, ZonedDateTime dateTime) {}
