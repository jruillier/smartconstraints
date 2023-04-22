package eu.aronnax.smartconstraints.jakartavalidation;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.smartconstraints.domain.port.coderenderer.ElementCollectorPort;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetConstraintHelperPort;
import eu.aronnax.smartconstraints.parser.common.CollectElementsHelper;
import eu.aronnax.smartconstraints.parser.common.ConstraintsHelperPort;

@SuppressWarnings("unused")
@Module
public abstract class JakartaValidationModule {

    @Binds
    public abstract ElementCollectorPort collectElementsHelper(CollectElementsHelper helper);

    @Binds
    public abstract ConstraintsHelperPort constraintsHelper(JakartaConstraintsHelper helper);

    @Binds
    public abstract TargetConstraintHelperPort targetConstraintHelperPort(JakartaTargetConstraintHelper helper);
}
