package eu.aronnax.omnivalid.sourcerenderer.simple;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.omnivalid.domainport.SourceRendererPort;

@Module
public abstract class SimpleSourceRendererModule {

    @Binds
    public abstract SourceRendererPort simpleSourceRenderer(SimpleSourceRenderer sourceRendererPort);
}
