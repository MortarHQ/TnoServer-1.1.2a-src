package it.hurts.octostudios.octolib.module.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.yaml.snakeyaml.introspector.BeanAccess;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TypeProp {
   String inlineComment() default "";

   String comment() default "";

   BeanAccess accessType() default BeanAccess.FIELD;

   boolean onlyProps() default false;
}
