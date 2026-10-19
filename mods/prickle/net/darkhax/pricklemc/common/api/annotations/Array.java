package net.darkhax.pricklemc.common.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Array {
   int inlineCount() default 5;

   boolean inlineComplex() default false;

   boolean allowEmpty() default true;
}
