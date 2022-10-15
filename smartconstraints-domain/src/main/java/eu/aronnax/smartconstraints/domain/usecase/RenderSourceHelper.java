package eu.aronnax.smartconstraints.domain.usecase;

import eu.aronnax.smartconstraints.domain.port.coderenderer.CodeRendererPort;
import eu.aronnax.smartconstraints.domain.port.coderenderer.RenderingDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetClassDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;

@ApplicationScoped
class RenderSourceHelper {

    private final CodeRendererPort codeRendererPort;

    @Inject
    RenderSourceHelper(CodeRendererPort codeRendererPort) {
        this.codeRendererPort = codeRendererPort;
    }

    RenderingDto renderSource(Map.Entry<CharSequence, TargetClassDto> entry) {
        return this.codeRendererPort.render(entry);
    }
}
