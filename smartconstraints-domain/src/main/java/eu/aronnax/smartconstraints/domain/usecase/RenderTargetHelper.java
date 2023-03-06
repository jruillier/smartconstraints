package eu.aronnax.smartconstraints.domain.usecase;

import eu.aronnax.smartconstraints.domain.port.KeyValueDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.CodeRendererPort;
import eu.aronnax.smartconstraints.domain.port.coderenderer.RenderingDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetClassDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class RenderTargetHelper {

    private final CodeRendererPort codeRendererPort;

    @Inject
    RenderTargetHelper(CodeRendererPort codeRendererPort) {
        this.codeRendererPort = codeRendererPort;
    }

    RenderingDto renderSource(KeyValueDto<TargetClassDto> entry) {
        return this.codeRendererPort.render(entry);
    }
}
