package eu.aronnax.smartconstraints.domainport;

import java.util.Optional;

import javax.validation.constraints.AssertTrue;

import org.immutables.value.Value;

import jakarta.annotation.Nullable;

@Value.Immutable
public interface SourceParamDto {

    String name();

    @Nullable
    Object nonStringValue();

    @Nullable
    String stringValue();

    @AssertTrue
    default boolean isWithvalue() {
        return Optional.ofNullable(nonStringValue())
                .or(() -> Optional.ofNullable(nonStringValue()))
                .isPresent();
    }

    default String quotedValue() {
        return Optional.ofNullable(nonStringValue())
                .map(Object::toString)
                .orElse("\"" + this.stringValue() + "\"");
    }

}
