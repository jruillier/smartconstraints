package eu.aronnax.smartconstraints.domain;

import eu.aronnax.smartconstraints.domainport.RenderingDto;
import eu.aronnax.smartconstraints.domainport.SourceClassDto;
import eu.aronnax.smartconstraints.domainport.SourceRendererPort;
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
