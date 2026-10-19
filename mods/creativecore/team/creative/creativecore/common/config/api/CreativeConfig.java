package team.creative.creativecore.common.config.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import team.creative.creativecore.common.config.sync.ConfigSynchronization;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CreativeConfig {
   String name() default "";

   ConfigSynchronization type() default ConfigSynchronization.UNIVERSAL;

   boolean requiresRestart() default false;

   boolean hideFromGUI() default false;

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   @interface DecimalRange {
      double min();

      double max();

      boolean slider() default true;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   @interface DecimalRangeSupplier {
      Class<?> supplier();

      boolean slider() default true;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   @interface IntRange {
      int min();

      int max();

      boolean slider() default true;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   @interface IntRangeSupplier {
      Class<?> supplier();

      boolean slider() default true;
   }
}
