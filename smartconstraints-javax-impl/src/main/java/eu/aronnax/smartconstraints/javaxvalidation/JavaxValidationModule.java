package eu.aronnax.smartconstraints.javaxvalidation;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.smartconstraints.domain.port.coderenderer.ElementCollectorPort;
import eu.aronnax.smartconstraints.parser.common.CollectElementsHelper;
import eu.aronnax.smartconstraints.parser.common.ConstraintsHelperPort;

@SuppressWarnings("unused")
@Module
public abstract class JavaxValidationModule {

    @Binds
    public abstract ElementCollectorPort collectElementsHelper(CollectElementsHelper helper);

    @Binds
    abstract ConstraintsHelperPort constraintsHelper(JavaxConstraintsHelper helper);
}
