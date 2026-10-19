package twilightforest.beanification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.neoforged.api.distmarker.Dist;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
   String DEFAULT_VALUE = "<beanification_shade_value>!beanification:internal:bean:DEFAULT!";

   String value() default "<beanification_shade_value>!beanification:internal:bean:DEFAULT!";

   Dist[] dist() default {};
}
