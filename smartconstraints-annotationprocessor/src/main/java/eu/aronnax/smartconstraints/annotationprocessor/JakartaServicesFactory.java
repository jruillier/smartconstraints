package eu.aronnax.smartconstraints.annotationprocessor;

import dagger.Component;
import eu.aronnax.smartconstraints.domain.usecase.ProcessCopyConstraintsAnnotationUC;
import eu.aronnax.smartconstraints.jakartavalidation.JakartaValidationModule;
import eu.aronnax.smartconstraints.sourcerenderer.jte.JteSourceRendererModule;
import eu.aronnax.smartconstraints.utils.UtilsModuleApacheCommons3;

@Component(modules = {UtilsModuleApacheCommons3.class, JteSourceRendererModule.class, JakartaValidationModule.class})
public interface JakartaServicesFactory {

    ProcessCopyConstraintsAnnotationUC processSmartConstraintsAnnotationUC();
}
