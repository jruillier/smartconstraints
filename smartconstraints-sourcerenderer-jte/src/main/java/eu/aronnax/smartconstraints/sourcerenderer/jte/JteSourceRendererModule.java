package eu.aronnax.smartconstraints.sourcerenderer.jte;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.smartconstraints.domain.port.coderenderer.CodeRendererPort;

@Module
public abstract class JteSourceRendererModule {

    @Binds
    abstract CodeRendererPort jteSourceRenderer(JteCodeRenderer jteSourceRenderer);
}
