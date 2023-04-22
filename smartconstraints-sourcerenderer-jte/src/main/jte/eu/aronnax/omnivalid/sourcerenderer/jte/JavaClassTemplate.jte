@import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotDto
@import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetClassDto
@import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetComposedAnnotDto
@import eu.aronnax.smartconstraints.sourcerenderer.jte.GeneratorInfo
@import eu.aronnax.smartconstraints.sourcerenderer.jte.JteHelper

@param GeneratorInfo generatorInfo
@param TargetClassDto targetClass
@param JteHelper helper

package ${targetClass.packageName()};

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.annotation.processing.Generated;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Generated(value = "${generatorInfo.name()}", date = "${generatorInfo.dateTime().toString()}")
class ${targetClass.simpleName()} {
@for(TargetComposedAnnotDto targetComposedAnnot : targetClass.composedAnnots())
    @for(TargetAnnotDto targetAnnot : targetComposedAnnot.annots())
        @${targetAnnot.qualifiedName()}(
        ${helper.formatParams(targetAnnot.annotParams())}
        )
    @endfor
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @${targetComposedAnnot.constraintAnnot().qualifiedName()}(validatedBy = {})
    public @interface ${targetComposedAnnot.name()} {
    String message() default "{${targetClass.qualifiedName()}.message}";
    Class<?>[] groups() default { };
    Class<? extends ${targetComposedAnnot.payload().qualifiedName()}>[] payload() default { };
    }
@endfor
}