package eu.aronnax.smartconstraints.test.entity;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class AddressEntity {

    //    @ValidAlphanumeric
    private String street;

    @NotNull
    @Size(min = 5, max = 5)
    private String zipCode;

    @NotNull
    private String countryCode;

    @Past
    private LocalDate existsSince;
}
