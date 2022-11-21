package eu.aronnax.smartconstraints.test.samemodule.dto;

import static eu.aronnax.smartconstraints.test.samemodule.dto.AddressSameModuleEntity_Constraints.*;

public record AddressSameModuleDto(@ValidZipCode String streetName) {}
