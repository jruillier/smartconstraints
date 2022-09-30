package eu.aronnax.omnivalid.sourcerenderer.jte;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.omnivalid.domainport.SourceRendererPort;

@Module
public abstract class JteSourceRendererModule {

    @Binds
    abstract SourceRendererPort jteSourceRenderer(JteSourceRenderer jteSourceRenderer);
}
