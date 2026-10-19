package cc.sighs.oelib.data.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.ApiStatus.Internal;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataDriven {
   String modid() default "";

   String folder();

   boolean syncToClient() default false;

   Class<? extends DataValidator<?>> validator() default DataValidator.NoValidator.class;

   boolean enableCache() default true;

   int priority() default 1000;

   boolean supportArray() default false;

   @Internal
   DataDriven.ValidatorBinding[] namespaceValidators() default {};

   @Target({})
   @Retention(RetentionPolicy.RUNTIME)
   @Internal
   @interface ValidatorBinding {
      String namespace();

      Class<? extends DataValidator<?>> validator();
   }
}
