package eu.aronnax.omnivalid.domain;

import java.util.Map;

public interface SourceRendererPort {

    RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry);

}
