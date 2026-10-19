package cc.sighs.oelib.data.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.ApiStatus.Internal;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Internal
public @interface ExpressionFunction {
   String value() default "";

   String description() default "";

   String category() default "general";
}
