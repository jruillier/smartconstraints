package eu.aronnax.omnivalid.sourcerenderer.simple;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.omnivalid.domain.SourceRendererPort;

@Module
public abstract class SourceRendererModuleSimple {

    @Binds
    public abstract SourceRendererPort sourceRendererSimple(SourceRendererSimple sourceRendererPort);

}
