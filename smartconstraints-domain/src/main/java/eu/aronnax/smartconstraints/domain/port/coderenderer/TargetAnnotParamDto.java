package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.Optional;

public record TargetAnnotParamDto(String name, Object nonStringValue, String stringValue) {

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
