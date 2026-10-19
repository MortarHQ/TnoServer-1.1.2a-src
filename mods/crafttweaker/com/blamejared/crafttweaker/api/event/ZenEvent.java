package com.blamejared.crafttweaker.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZenEvent {
   @Target(ElementType.FIELD)
   @Retention(RetentionPolicy.RUNTIME)
   @interface Bus {
      Class<?> value() default ZenEvent.Bus.Auto.class;

      @Target({})
      @Retention(RetentionPolicy.RUNTIME)
      @interface Auto {
      }
   }

   @Target(ElementType.TYPE)
   @Retention(RetentionPolicy.RUNTIME)
   @interface BusCarrier {
   }
}
