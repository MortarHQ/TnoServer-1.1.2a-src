package net.darkhax.pricklemc.common.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RangedInt {
   int min() default Integer.MIN_VALUE;

   int max() default Integer.MAX_VALUE;
}
