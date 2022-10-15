package eu.aronnax.smartconstraints.domain.port.coderenderer;

import java.util.Map;

public interface CodeRendererPort {

    RenderingDto render(Map.Entry<CharSequence, TargetClassDto> entry);
}
