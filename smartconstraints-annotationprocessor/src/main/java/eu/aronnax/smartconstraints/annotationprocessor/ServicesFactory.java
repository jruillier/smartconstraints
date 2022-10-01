package eu.aronnax.smartconstraints.annotationprocessor;

import dagger.Component;
import eu.aronnax.smartconstraints.domain.ProcessCopyConstraintsAnnotationUC;
import eu.aronnax.smartconstraints.sourcerenderer.jte.JteSourceRendererModule;
import eu.aronnax.smartconstraints.utils.UtilsModuleApacheCommons3;

@Component(modules = {UtilsModuleApacheCommons3.class, JteSourceRendererModule.class})
public interface ServicesFactory {

    ProcessCopyConstraintsAnnotationUC processSmartConstraintsraintsAnnotationUC();
}
