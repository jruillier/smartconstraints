package eu.aronnax.smartconstraints.domainport;

import jakarta.annotation.Nullable;

import javax.validation.constraints.AssertTrue;
import java.util.Optional;

public record SourceParamDto(String name, @Nullable Object nonStringValue, @Nullable String stringValue) {

    @AssertTrue
    boolean isWithvalue() {
        return Optional.ofNullable(this.nonStringValue())
                .or(() -> Optional.ofNullable(this.stringValue()))
                .isPresent();
    }

    public String quotedValue() {
        return Optional.ofNullable(this.nonStringValue())
                .map(Object::toString)
                .orElse("\"" + this.stringValue() + "\"");
    }
}
