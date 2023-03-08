@import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotDto
@import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetClassDto
@import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetMetaAnnotDto
@import eu.aronnax.smartconstraints.sourcerenderer.jte.GeneratorInfo
@import eu.aronnax.smartconstraints.sourcerenderer.jte.JteHelper

@param GeneratorInfo generatorInfo
@param TargetClassDto sourceClass
@param JteHelper helper

package ${sourceClass.packageName()};

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.annotation.processing.Generated;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Generated(value = "${generatorInfo.name()}", date = "${generatorInfo.dateTime().toString()}")
class ${sourceClass.simpleName()} {
@for(TargetMetaAnnotDto sourceElement : sourceClass.metaAnnots())
    @for(TargetAnnotDto sourceAnnot : sourceElement.annots())
    @${sourceAnnot.simpleName()}(
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