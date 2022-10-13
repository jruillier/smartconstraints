package eu.aronnax.smartconstraints.test.sto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

public class ValidationTest {

    @Test
    public void testValidation_passing() {
        // Prepare
        AddressSto addressSto = new AddressSto();
        addressSto.setStreet("a street");
        addressSto.setZipCode("12345");

        // Run
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        Set<ConstraintViolation<AddressSto>> violations = validator.validate(addressSto);

        // Verify
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidation_violations() {
        // Prepare
        AddressSto addressSto = new AddressSto();

        // Run
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        Set<ConstraintViolation<AddressSto>> violations = validator.validate(addressSto);

        // Verify
        assertEquals(2, violations.size());
    }
}
