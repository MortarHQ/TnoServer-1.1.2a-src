package twilightforest.beanification.processors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import twilightforest.beanification.BeanLifeCycle;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanProcessor {
   int priority() default 0;

   BeanLifeCycle value();
}
