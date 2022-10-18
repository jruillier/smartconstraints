package eu.aronnax.smartconstraints.domain.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

@ApplicationScoped
public class ProcessCopyConstraintsAnnotationUC {

    private final CollectElementsHelper collectElementsHelper;
    private final BuildTargetHelper buildTargetHelper;
    private final RenderSourceHelper renderSourceHelper;
    private final WriteSourceHelper writeSourceHelper;

    @Inject
    ProcessCopyConstraintsAnnotationUC(
            CollectElementsHelper collectElementsHelper,
            BuildTargetHelper buildTargetHelper,
            RenderSourceHelper renderSourceHelper,
            WriteSourceHelper writeSourceHelper) {
        this.collectElementsHelper = collectElementsHelper;
        this.buildTargetHelper = buildTargetHelper;
        this.renderSourceHelper = renderSourceHelper;
        this.writeSourceHelper = writeSourceHelper;
    }

    public void exec(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
        this.collectElementsHelper
                .collectAnnotElements(annotations, roundEnv, processingEnv)
                .map(this.buildTargetHelper::buildTargetClass)
                .map(this.renderSourceHelper::renderSource)
                .forEach(entry -> this.writeSourceHelper.writeSource(entry, processingEnv));
    }
}
