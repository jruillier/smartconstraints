package eu.aronnax.smartconstraints.test.sto;

import eu.aronnax.smartconstraints.test.entity.AddressEntity_Constraints;

public class AddressSto {

    @AddressEntity_Constraints.Street
    private String street;

    @AddressEntity_Constraints.ZipCode
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
