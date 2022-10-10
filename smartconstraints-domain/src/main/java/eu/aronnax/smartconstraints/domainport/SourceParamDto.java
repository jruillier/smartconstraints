package eu.aronnax.smartconstraints.domainport;

import java.util.Optional;

import javax.validation.constraints.AssertTrue;

import jakarta.annotation.Nullable;

public record SourceParamDto(
        String name,
        @Nullable Object nonStringValue,
        @Nullable String stringValue) {

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
