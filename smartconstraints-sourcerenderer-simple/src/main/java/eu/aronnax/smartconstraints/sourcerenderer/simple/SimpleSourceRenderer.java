package eu.aronnax.smartconstraints.sourcerenderer.simple;

import eu.aronnax.smartconstraints.domain.NamingUtil;
import eu.aronnax.smartconstraints.domainport.ImmutableRenderingDto;
import eu.aronnax.smartconstraints.domainport.RenderingDto;
import eu.aronnax.smartconstraints.domainport.SourceClassDto;
import eu.aronnax.smartconstraints.domainport.SourceRendererPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class SimpleSourceRenderer implements SourceRendererPort {

    @Inject
    public SimpleSourceRenderer() {}

    @Override
    public RenderingDto renderSource(Map.Entry<CharSequence, SourceClassDto> entry) {
        SourceClassDto sourceClassDto = entry.getValue();
        String prefix = "package " + sourceClassDto.packageName() + ";\n"
                + "\n"
                + "import java.lang.annotation.Documented;\n"
                + "import java.lang.annotation.Retention;\n"
                + "import java.lang.annotation.Target;\n"
                + "\n"
                + "import javax.validation.Constraint;\n"
                + "\n"
                + "import static java.lang.annotation.ElementType.ANNOTATION_TYPE;\n"
                + "import static java.lang.annotation.ElementType.CONSTRUCTOR;\n"
                + "import static java.lang.annotation.ElementType.FIELD;\n"
                + "import static java.lang.annotation.ElementType.METHOD;\n"
                + "import static java.lang.annotation.ElementType.PARAMETER;\n"
                + "import static java.lang.annotation.RetentionPolicy.RUNTIME;\n"
                + "\n"
                + "\npublic class " + sourceClassDto.simpleName() + " { ";
        String suffix = "\n }";

        String content = entry.getValue().properties().stream()
                .map(elem -> elem.annots().stream()
                                .map(annot -> "\n@" + annot.qualifiedName())
                                .collect(Collectors.joining())
                        + "\n@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })"
                        + "\n@Retention(RUNTIME)"
                        + "\n@Documented"
                        + "\n@Constraint(validatedBy = {})"
                        + "\npublic @interface " + elem.name() + " {}")
                .collect(Collectors.joining("\n\n"));

        String qualifiedName = entry.getKey().toString();
        String packageName = NamingUtil.extractPackageName(qualifiedName);
        String classSimpleName = NamingUtil.extractSimpleName(qualifiedName);
        return ImmutableRenderingDto.builder()
                .packageName(packageName)
                .classSimpleName(classSimpleName)
                .classQualifiedName(qualifiedName)
                .sourceRendering(prefix + content + suffix)
                .build();
    }
}
