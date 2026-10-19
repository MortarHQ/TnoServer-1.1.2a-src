package twilightforest.beanification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Nullable
@Internal
public @interface InternalAutowired {
   String value() default "<beanification_shade_value>!beanification:internal:bean:DEFAULT!";
}
