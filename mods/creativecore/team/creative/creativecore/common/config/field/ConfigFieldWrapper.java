package team.creative.creativecore.common.config.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public record ConfigFieldWrapper(Object parent, Field field) implements ConfigField {
   @Override
   public Object get() {
      try {
         return this.field.get(this.parent);
      } catch (IllegalArgumentException | IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public void set(Object value) {
      try {
         this.field.set(this.parent, value);
      } catch (IllegalArgumentException | IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
      return this.field.getAnnotation(annotationClass);
   }

   @Override
   public Type getGenericType() {
      return this.field.getGenericType();
   }

   @Override
   public Class getType() {
      return this.field.getType();
   }
}
