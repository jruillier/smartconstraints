@import eu.aronnax.smartconstraints.domainport.SourceAnnotDto
@import eu.aronnax.smartconstraints.domainport.SourceClassDto
@import eu.aronnax.smartconstraints.domainport.SourcePropertyDto

@param SourceClassDto sourceClass

package ${sourceClass.packageName()};

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class ${sourceClass.simpleName()} {
@for(SourcePropertyDto sourceElement : sourceClass.properties())
    @for(SourceAnnotDto sourceAnnot : sourceElement.annots())
    @${sourceAnnot.qualifiedName()}@endfor
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @Constraint(validatedBy = {})
    public @interface ${sourceElement.name()} {}
@endfor
}