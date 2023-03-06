package eu.aronnax.smartconstraints.domain.port.coderenderer;

import eu.aronnax.smartconstraints.domain.port.KeyValueDto;

public interface CodeRendererPort {

    RenderingDto render(KeyValueDto<TargetClassDto> entry);
}
