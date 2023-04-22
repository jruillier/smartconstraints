package eu.aronnax.smartconstraints.javaxvalidation;

import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnyClassDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetConstraintHelperPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.util.Collections;

@ApplicationScoped
public class JavaxTargetConstraintHelper implements TargetConstraintHelperPort {

    @Inject
    public JavaxTargetConstraintHelper() {}

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
