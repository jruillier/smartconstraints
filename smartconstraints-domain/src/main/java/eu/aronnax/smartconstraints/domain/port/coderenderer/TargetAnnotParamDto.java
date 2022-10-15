package eu.aronnax.smartconstraints.domain.port.coderenderer;

import jakarta.annotation.Nullable;
import java.util.Optional;
import javax.validation.constraints.AssertTrue;

public record TargetAnnotParamDto(String name, @Nullable Object nonStringValue, @Nullable String stringValue) {

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
