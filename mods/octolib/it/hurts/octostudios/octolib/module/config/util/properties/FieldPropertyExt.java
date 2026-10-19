package it.hurts.octostudios.octolib.module.config.util.properties;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.util.ArrayUtils;

public class FieldPropertyExt extends GenericPropertyExt {
   private final Field field;

   public FieldPropertyExt(Field field) {
      super(field.getName(), field.getType(), field.getGenericType());
      this.field = field;
      field.setAccessible(true);
   }

   public FieldPropertyExt(Field field, String name) {
      super(name, field.getType(), field.getGenericType());
      this.field = field;
      field.setAccessible(true);
   }

   @Override
   public void set(Object object, Object value) throws Exception {
      this.field.set(object, value);
   }

   @Override
   public Object get(Object object) {
      try {
         this.field.setAccessible(true);
         return this.field.get(object);
      } catch (Exception var3) {
         throw new YAMLException("Unable to access field " + this.field.getName() + " on object " + object + " : " + var3);
      }
   }

   @Override
   public List<Annotation> getAnnotations() {
      return ArrayUtils.toUnmodifiableList(this.field.getAnnotations());
   }

   @Override
   public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
      return this.field.getAnnotation(annotationType);
   }
}
