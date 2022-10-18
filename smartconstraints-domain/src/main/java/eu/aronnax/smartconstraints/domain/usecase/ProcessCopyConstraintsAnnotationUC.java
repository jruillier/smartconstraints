package eu.aronnax.smartconstraints.domain.usecase;

import eu.aronnax.smartconstraints.domain.port.coderenderer.ElementCollectorPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

@ApplicationScoped
public class ProcessCopyConstraintsAnnotationUC {

    private final ElementCollectorPort collectElementsHelper;
    private final BuildTargetHelper buildTargetHelper;
    private final RenderTargetHelper renderTargetHelper;
    private final WriteSourceHelper writeSourceHelper;

    @Inject
    ProcessCopyConstraintsAnnotationUC(
            ElementCollectorPort collectElementsHelper,
            BuildTargetHelper buildTargetHelper,
            RenderTargetHelper renderTargetHelper,
            WriteSourceHelper writeSourceHelper) {
        this.collectElementsHelper = collectElementsHelper;
        this.buildTargetHelper = buildTargetHelper;
        this.renderTargetHelper = renderTargetHelper;
        this.writeSourceHelper = writeSourceHelper;
    }

    public void exec(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
        this.collectElementsHelper
                .collectAnnotElements(annotations, roundEnv, processingEnv)
                .map(this.buildTargetHelper::buildTargetClass)
                .map(this.renderTargetHelper::renderSource)
                .forEach(entry -> this.writeSourceHelper.writeSource(entry, processingEnv));
    }
}
