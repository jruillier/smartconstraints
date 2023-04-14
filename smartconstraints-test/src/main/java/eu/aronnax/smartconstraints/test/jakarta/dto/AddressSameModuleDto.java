package eu.aronnax.smartconstraints.test.jakarta.dto;

import static eu.aronnax.smartconstraints.test.jakarta.dto.AddressSameModule_Constraints.ValidZipCode;

public record AddressSameModuleDto(@ValidZipCode String streetName) {}
