package eu.aronnax.smartconstraints.domain.usecase;

import eu.aronnax.smartconstraints.domain.port.sourcerenderer.RenderingDto;
import eu.aronnax.smartconstraints.domain.port.sourcerenderer.SourceClassDto;
import eu.aronnax.smartconstraints.domain.port.sourcerenderer.SourceRendererPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;

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
