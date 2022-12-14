package eu.aronnax.smartconstraints.sourcerenderer.jte;

import eu.aronnax.smartconstraints.domain.port.coderenderer.CodeRendererPort;
import eu.aronnax.smartconstraints.domain.port.coderenderer.RenderingDto;
import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetClassDto;
import eu.aronnax.smartconstraints.domain.usecase.NamingUtil;
import gg.jte.TemplateOutput;
import gg.jte.generated.precompiled.eu.aronnax.omnivalid.sourcerenderer.jte.JteJavaClassTemplateGenerated;
import gg.jte.output.StringOutput;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;
import javax.inject.Inject;

@ApplicationScoped
public class JteCodeRenderer implements CodeRendererPort {

    private final JteHelper jteHelper;

    @Inject
    public JteCodeRenderer(JteHelper jteHelper) {
        this.jteHelper = jteHelper;
    }

    @Override
    public RenderingDto render(Map.Entry<CharSequence, TargetClassDto> entry) {
        TemplateOutput output = new StringOutput();
        JteJavaClassTemplateGenerated.render(output, null, entry.getValue(), this.jteHelper);
        String qualifiedName = entry.getKey().toString();
        String packageName = NamingUtil.extractPackageName(qualifiedName);
        String classSimpleName = NamingUtil.extractSimpleName(qualifiedName);
        return new RenderingDto(packageName, classSimpleName, qualifiedName, output.toString());
    }
}
