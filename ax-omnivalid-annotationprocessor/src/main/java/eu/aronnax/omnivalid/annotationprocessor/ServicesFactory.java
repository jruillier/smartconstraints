package eu.aronnax.omnivalid.annotationprocessor;

import dagger.Component;
import eu.aronnax.omnivalid.domain.BuildSourceUC;
import eu.aronnax.omnivalid.domain.CollectElementsUC;
import eu.aronnax.omnivalid.domain.RenderSourceUC;
import eu.aronnax.omnivalid.sourcerenderer.simple.SourceRendererModuleSimple;
import eu.aronnax.omnivalid.utils.UtilsModuleApacheCommons3;

@Component(modules = {UtilsModuleApacheCommons3.class, SourceRendererModuleSimple.class})
public interface ServicesFactory {

    CollectElementsUC collectElementsUC();

    BuildSourceUC buildSourceUC();

    RenderSourceUC renderSourceUC();
}
