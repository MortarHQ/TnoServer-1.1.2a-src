package twilightforest.beanification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.neoforged.api.distmarker.Dist;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
   String value() default "<beanification_shade_value>!beanification:internal:bean:DEFAULT!";

   Dist[] dist() default {};

   int priority() default 0;
}
