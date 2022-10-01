package eu.aronnax.smartconstraints.sourcerenderer.jte;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.smartconstraints.domainport.SourceRendererPort;

@Module
public abstract class JteSourceRendererModule {

    @Binds
    abstract SourceRendererPort jteSourceRenderer(JteSourceRenderer jteSourceRenderer);
}
