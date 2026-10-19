package it.hurts.octostudios.octolib.module.config.util.properties;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import org.yaml.snakeyaml.introspector.GenericProperty;

public class GenericPropertyExt extends GenericProperty {
   private String inlineComment;
   private String blockComment;
   private Type genTypeOverride;

   public GenericPropertyExt(String name, Class<?> aClass, Type aType) {
      super(name, aClass, aType);
   }

   public Type getGenTypeOverride() {
      return this.genTypeOverride;
   }

   public void setGenTypeOverride(Type genTypeOverride) {
      this.genTypeOverride = genTypeOverride;
   }

   public Class<?>[] getActualTypeArguments() {
      if (this.genTypeOverride != null) {
         Class<?> classType = (Class<?>)this.genTypeOverride;
         if (classType.isArray()) {
            return new Class[]{this.getType().getComponentType()};
         }
      }

      return super.getActualTypeArguments();
   }

   public void set(Object o, Object o1) throws Exception {
   }

   public Object get(Object o) {
      return null;
   }

   public List<Annotation> getAnnotations() {
      return null;
   }

   public <A extends Annotation> A getAnnotation(Class<A> aClass) {
      return null;
   }

   public String getInlineComment() {
      return this.inlineComment;
   }

   public void setInlineComment(String inlineComment) {
      this.inlineComment = inlineComment;
   }

   public String getBlockComment() {
      return this.blockComment;
   }

   public void setBlockComment(String blockComment) {
      this.blockComment = blockComment;
   }
}
