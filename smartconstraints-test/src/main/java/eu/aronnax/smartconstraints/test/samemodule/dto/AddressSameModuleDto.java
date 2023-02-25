package eu.aronnax.smartconstraints.test.samemodule.dto;

import static eu.aronnax.smartconstraints.test.samemodule.dto.AddressSameModule_Constraints.*;

public record AddressSameModuleDto(@ValidZipCode String streetName) {}
