package eu.aronnax.smartconstraints.test.entity;

import java.time.LocalDate;
import javax.validation.constraints.*;

public class AddressEntity {

    //    @ValidAlphanumeric
    @NotNull
    private String street;

    @Min(1)
    @Max(100)
    private Integer streetNumber;

    @NotNull(message = "ZipCode must not be null")
    @Size(min = 5, max = 5)
    private String zipCode;

    @Past
    private LocalDate existsSince;

    @PastOrPresent
    private LocalDate lastUpdate;

    @NotNull
    public String getAsCustomString() {
        return this.street + "\n" + this.zipCode;
    }

    /**
     * Should be ignored.
     */
    @NotNull
    public String getSuffixedString(@NotNull String suffix) {
        return this.street + " " + suffix;
    }
}
