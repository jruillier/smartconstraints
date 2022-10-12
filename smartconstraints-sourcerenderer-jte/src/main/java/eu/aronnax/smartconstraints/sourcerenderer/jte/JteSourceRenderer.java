package eu.aronnax.smartconstraints.sourcerenderer.jte;

import eu.aronnax.smartconstraints.domain.NamingUtil;
import eu.aronnax.smartconstraints.domainport.RenderingDto;
import eu.aronnax.smartconstraints.domainport.SourceClassDto;
import eu.aronnax.smartconstraints.domainport.SourceRendererPort;
import gg.jte.TemplateOutput;
import gg.jte.generated.precompiled.eu.aronnax.omnivalid.sourcerenderer.jte.JteJavaClassTemplateGenerated;
import gg.jte.output.StringOutput;
import jakarta.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import java.util.Map;

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
