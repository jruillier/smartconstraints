package eu.aronnax.omnivalid;

import javax.validation.constraints.NotNull;

public class AddressEntity {

//    @ValidAlphanumeric
    private String street;

    @NotNull
//    @Size(min = 5, max = 5)
    private String zipCode;

    @NotNull
    private String countryCode;


}
