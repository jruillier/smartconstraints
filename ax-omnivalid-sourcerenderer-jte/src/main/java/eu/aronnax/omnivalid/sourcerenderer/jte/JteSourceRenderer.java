package eu.aronnax.omnivalid.sourcerenderer.jte;

import java.nio.file.Path;
import java.util.Map;

import javax.inject.Inject;

import eu.aronnax.omnivalid.domain.NamingUtil;
import eu.aronnax.omnivalid.domainport.ImmutableRenderingDto;
import eu.aronnax.omnivalid.domainport.RenderingDto;
import eu.aronnax.omnivalid.domainport.SourceClassDto;
import eu.aronnax.omnivalid.domainport.SourceRendererPort;
import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.generated.precompiled.eu.aronnax.omnivalid.sourcerenderer.jte.JteJavaClassTemplateGenerated;
import gg.jte.output.StringOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JteSourceRenderer implements SourceRendererPort {

    private final TemplateEngine templateEngine;

    @Inject
    public JteSourceRenderer() {
        CodeResolver resolver = new DirectoryCodeResolver(
                Path.of(
                        "/Users/jraronnax/Workspace/JRFreelance/jr-omni-valid/ax-omni-valid/ax-omnivalid-sourcerenderer-jte/src/main/resources/eu/aronnax/omnivalid/sourcerenderer/jte"));
        //        templateEngine = TemplateEngine.create(resolver, ContentType.Plain);
        templateEngine = TemplateEngine.createPrecompiled(ContentType.Plain);
    }

    @Override
    public RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry) {
        TemplateOutput output = new StringOutput();
        //        templateEngine.render("JavaClassTemplate.jte", entry.getValue(), output);
        JteJavaClassTemplateGenerated.render(output, null, entry.getValue());
        String qualifiedName = entry.getKey().toString();
        String packageName = NamingUtil.extractPackageName(qualifiedName);
        String classSimpleName = NamingUtil.extractSimpleName(qualifiedName);
        return ImmutableRenderingDto.builder()
                .packageName(packageName)
                .classSimpleName(classSimpleName)
                .classQualifiedName(qualifiedName)
                .sourceRendering(output.toString())
                .build();
    }
}
