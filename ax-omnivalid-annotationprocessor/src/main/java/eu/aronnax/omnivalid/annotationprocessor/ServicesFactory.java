package eu.aronnax.omnivalid.annotationprocessor;

import dagger.Component;
import eu.aronnax.omnivalid.domain.ProcessCopyConstraintsAnnotationUC;
import eu.aronnax.omnivalid.sourcerenderer.jte.JteSourceRendererModule;
import eu.aronnax.omnivalid.utils.UtilsModuleApacheCommons3;

@Component(modules = {UtilsModuleApacheCommons3.class, JteSourceRendererModule.class})
public interface ServicesFactory {

    ProcessCopyConstraintsAnnotationUC processCopyConstraintsAnnotationUC();

}
