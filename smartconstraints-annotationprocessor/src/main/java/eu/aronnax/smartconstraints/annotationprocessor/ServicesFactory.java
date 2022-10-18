package eu.aronnax.smartconstraints.annotationprocessor;

import dagger.Component;
import eu.aronnax.smartconstraints.domain.usecase.ProcessCopyConstraintsAnnotationUC;
import eu.aronnax.smartconstraints.javaxvalidation.JavaxValidationModule;
import eu.aronnax.smartconstraints.sourcerenderer.jte.JteSourceRendererModule;
import eu.aronnax.smartconstraints.utils.UtilsModuleApacheCommons3;

@Component(modules = {UtilsModuleApacheCommons3.class, JteSourceRendererModule.class, JavaxValidationModule.class})
public interface ServicesFactory {

    ProcessCopyConstraintsAnnotationUC processSmartConstraintsAnnotationUC();
}
