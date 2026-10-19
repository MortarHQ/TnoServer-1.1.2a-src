package dev.isxander.yacl3.config.v2.impl;

import dev.isxander.yacl3.config.v2.api.FieldAccess;
import dev.isxander.yacl3.config.v2.impl.autogen.YACLAutoGenException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;

public record ReflectionFieldAccess<T>(Field field, Object instance) implements FieldAccess<T> {
   @Override
   public T get() {
      try {
         return (T)this.field.get(this.instance);
      } catch (IllegalAccessException e) {
         throw new YACLAutoGenException("Failed to access field '%s'".formatted(this.name()), e);
      }
   }

   @Override
   public void set(T value) {
      try {
         this.field.set(this.instance, value);
      } catch (IllegalAccessException e) {
         throw new YACLAutoGenException("Failed to set field '%s'".formatted(this.name()), e);
      }
   }

   @Override
   public String name() {
      return this.field.getName();
   }

   @Override
   public Type type() {
      return this.field.getGenericType();
   }

   @Override
   public Class<T> typeClass() {
      return (Class<T>)this.field.getType();
   }

   @Override
   public <A extends Annotation> Optional<A> getAnnotation(Class<A> annotationClass) {
      return Optional.ofNullable(this.field.getAnnotation(annotationClass));
   }
}
