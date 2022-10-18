package eu.aronnax.smartconstraints.javaxvalidation;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.smartconstraints.domain.port.coderenderer.ElementCollectorPort;

@Module
public abstract class JavaxValidationModule {

    @Binds
    public abstract ElementCollectorPort collectElementsHelper(CollectElementsHelper helper);
}
