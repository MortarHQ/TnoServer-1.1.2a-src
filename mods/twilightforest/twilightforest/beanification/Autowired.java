package twilightforest.beanification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.Nullable;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Nullable
public @interface Autowired {
   String value() default "<beanification_shade_value>!beanification:internal:bean:DEFAULT!";

   Dist[] dist() default {};
}
