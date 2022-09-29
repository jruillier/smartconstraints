package eu.aronnax.omnivalid.annotationprocessor;

import dagger.Component;
import eu.aronnax.omnivalid.domain.BuildSourceUC;
import eu.aronnax.omnivalid.domain.CollectElementsUC;
import eu.aronnax.omnivalid.domain.RenderSourceUC;
import eu.aronnax.omnivalid.sourcerenderer.jte.JteSourceRendererModule;
import eu.aronnax.omnivalid.utils.UtilsModuleApacheCommons3;

@Component(
        modules = {UtilsModuleApacheCommons3.class, JteSourceRendererModule.class
            //        , SimpleSourceRendererModule.class
        })
public interface ServicesFactory {

    CollectElementsUC collectElementsUC();

    BuildSourceUC buildSourceUC();

    RenderSourceUC renderSourceUC();
}
