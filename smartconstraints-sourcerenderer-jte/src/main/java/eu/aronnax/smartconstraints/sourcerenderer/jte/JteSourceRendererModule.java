package eu.aronnax.smartconstraints.sourcerenderer.jte;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.smartconstraints.domain.port.sourcerenderer.SourceRendererPort;

@Module
public abstract class JteSourceRendererModule {

    @Binds
    abstract SourceRendererPort jteSourceRenderer(JteSourceRenderer jteSourceRenderer);
}
