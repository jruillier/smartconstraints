@import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotDto
@import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetClassDto
@import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetMetaAnnotDto
@import eu.aronnax.smartconstraints.sourcerenderer.jte.JteHelper
@import gg.jte.support.ForSupport

@param TargetClassDto sourceClass
@param JteHelper helper

package ${sourceClass.packageName()};

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

class ${sourceClass.simpleName()} {
@for(TargetMetaAnnotDto sourceElement : sourceClass.properties())
    @for(TargetAnnotDto sourceAnnot : sourceElement.annots())
    @${sourceAnnot.qualifiedName()}(
        ${helper.formatParams(sourceAnnot.annotParams())}
    )
@endfor
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @Constraint(validatedBy = {})
    public @interface ${sourceElement.name()} {
        String message() default "{${sourceClass.qualifiedName()}.message}";
        Class<?>[] groups() default { };
        Class<? extends Payload>[] payload() default { };
    }
@endfor
}