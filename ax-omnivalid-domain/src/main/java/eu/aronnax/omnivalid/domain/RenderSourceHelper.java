package eu.aronnax.omnivalid.domain;

import java.util.Map;

import eu.aronnax.omnivalid.domainport.RenderingDto;
import eu.aronnax.omnivalid.domainport.SourceClassDto;
import eu.aronnax.omnivalid.domainport.SourceRendererPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class RenderSourceHelper {

    private final SourceRendererPort sourceRendererPort;

    @Inject
    RenderSourceHelper(SourceRendererPort sourceRendererPort) {
        this.sourceRendererPort = sourceRendererPort;
    }

    RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry) {
        return this.sourceRendererPort.renderSource(entry);
    }
}
