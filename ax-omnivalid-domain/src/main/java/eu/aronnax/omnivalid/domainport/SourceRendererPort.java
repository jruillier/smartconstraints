package eu.aronnax.omnivalid.domainport;

import java.util.Map;

public interface SourceRendererPort {

    RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry);
}
