package eu.aronnax.omnivalid.domain;

import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RenderSourceUC {

    private final SourceRendererPort sourceRendererPort;

    @Inject
    public RenderSourceUC(SourceRendererPort sourceRendererPort) {
        this.sourceRendererPort = sourceRendererPort;
    }

    public RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry) {
        return this.sourceRendererPort.renderSource(entry);
    }
}
