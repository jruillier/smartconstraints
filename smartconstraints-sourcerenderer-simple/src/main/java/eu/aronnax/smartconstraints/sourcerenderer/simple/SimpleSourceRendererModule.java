package eu.aronnax.smartconstraints.sourcerenderer.simple;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.smartconstraints.domainport.SourceRendererPort;

@Module
public abstract class SimpleSourceRendererModule {

    @Binds
    public abstract SourceRendererPort simpleSourceRenderer(SimpleSourceRenderer sourceRendererPort);
}
