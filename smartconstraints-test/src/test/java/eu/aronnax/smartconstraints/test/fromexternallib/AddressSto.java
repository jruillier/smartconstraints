package eu.aronnax.smartconstraints.test.fromexternallib;

import static eu.aronnax.smartconstraints.test.fromexternallib.AddressEntity_Constraints.*;

public class AddressSto {

    @ValidStreet
    private String street;

    @ValidZipCode
    private String zipCode;

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
