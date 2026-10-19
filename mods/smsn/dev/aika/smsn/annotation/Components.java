package dev.aika.smsn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface Components {
   @Target(ElementType.FIELD)
   @Retention(RetentionPolicy.RUNTIME)
   @interface Switch {
      String checked() default "true";

      String unchecked() default "false";
   }
}
