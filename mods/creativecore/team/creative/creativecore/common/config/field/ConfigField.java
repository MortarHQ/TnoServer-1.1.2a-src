package team.creative.creativecore.common.config.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public interface ConfigField {
   Object get();

   void set(Object var1);

   <T extends Annotation> T getAnnotation(Class<T> var1);

   Type getGenericType();

   Class getType();
}
