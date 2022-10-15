package eu.aronnax.smartconstraints.sourcerenderer.jte;

import eu.aronnax.smartconstraints.domain.port.sourcerenderer.RenderingDto;
import eu.aronnax.smartconstraints.domain.port.sourcerenderer.SourceClassDto;
import eu.aronnax.smartconstraints.domain.port.sourcerenderer.SourceRendererPort;
import eu.aronnax.smartconstraints.domain.usecase.NamingUtil;
import gg.jte.TemplateOutput;
import gg.jte.generated.precompiled.eu.aronnax.omnivalid.sourcerenderer.jte.JteJavaClassTemplateGenerated;
import gg.jte.output.StringOutput;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;
import javax.inject.Inject;

@ApplicationScoped
public class JteSourceRenderer implements SourceRendererPort {

    @Inject
    public JteSourceRenderer() {}

    @Override
    public RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry) {
        TemplateOutput output = new StringOutput();
        JteJavaClassTemplateGenerated.render(output, null, entry.getValue());
        String qualifiedName = entry.getKey().toString();
        String packageName = NamingUtil.extractPackageName(qualifiedName);
        String classSimpleName = NamingUtil.extractSimpleName(qualifiedName);
        return new RenderingDto(packageName, classSimpleName, qualifiedName, output.toString());
    }
}