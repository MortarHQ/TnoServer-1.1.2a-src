package org.openzen.zencode.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ZenCodeGlobals {
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD, ElementType.METHOD})
   @interface Global {
      String value() default "";
   }
}
