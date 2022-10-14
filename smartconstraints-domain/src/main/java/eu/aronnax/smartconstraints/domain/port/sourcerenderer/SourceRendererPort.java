package eu.aronnax.smartconstraints.domain.port.sourcerenderer;

import java.util.Map;

public interface SourceRendererPort {

    RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry);
}
