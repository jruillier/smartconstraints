package eu.aronnax.smartconstraints.jakartavalidation;

import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnyClassDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetConstraintHelperPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.util.Collections;

@ApplicationScoped
public class JakartaTargetConstraintHelper implements TargetConstraintHelperPort {

    @Inject
    public JakartaTargetConstraintHelper() {}

    @Override
    public TargetAnnotDto getConstraintAnnot() {
        return new TargetAnnotDto(
                Constraint.class.getName(), Constraint.class.getSimpleName(), Collections.emptyList());
    }

    @Override
    public TargetAnyClassDto getPayload() {
        return new TargetAnyClassDto(Payload.class.getName(), Payload.class.getSimpleName());
    }
}
