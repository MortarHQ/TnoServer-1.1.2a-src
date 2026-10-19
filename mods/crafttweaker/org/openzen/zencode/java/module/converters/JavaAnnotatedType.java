package org.openzen.zencode.java.module.converters;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

public final class JavaAnnotatedType implements AnnotatedElement, Type {
   private final JavaAnnotatedType.ElementType elementType;
   private final AnnotatedElement annotatedElement;
   private final Type type;

   private JavaAnnotatedType(JavaAnnotatedType.ElementType elementType, AnnotatedElement element, Type type) {
      this.elementType = elementType;
      this.annotatedElement = element;
      this.type = type;
   }

   public static JavaAnnotatedType of(Object element) {
      if (element instanceof Type && element instanceof AnnotatedElement) {
         return checkNotNull(x$0 -> ofBoth(x$0), (Type & AnnotatedElement)element);
      } else if (element instanceof Type) {
         return checkNotNull(JavaAnnotatedType::of, (Type)element);
      } else if (element instanceof AnnotatedElement) {
         return checkNotNull(JavaAnnotatedType::of, (AnnotatedElement)element);
      } else {
         throw invalid(element);
      }
   }

   public static JavaAnnotatedType[] arrayOf(Object[] element) {
      return Arrays.stream(element).map(JavaAnnotatedType::of).toArray(JavaAnnotatedType[]::new);
   }

   private static <T> JavaAnnotatedType checkNotNull(Function<T, JavaAnnotatedType> creator, T element) {
      JavaAnnotatedType result = creator.apply(element);
      if (result == null) {
         throw invalid(element);
      } else {
         return result;
      }
   }

   private static <T extends Type & AnnotatedElement> JavaAnnotatedType ofBoth(T element) {
      if (element instanceof Class) {
         return of(JavaAnnotatedType.ElementType.CLASS, element, element);
      }

      if (element instanceof TypeVariable) {
         return of(JavaAnnotatedType.ElementType.TYPE_VARIABLE, element, element);
      }

      JavaAnnotatedType result = of(element);
      return result == null ? of(element) : result;
   }

   private static JavaAnnotatedType of(Type element) {
      if (element instanceof ParameterizedType) {
         return of(JavaAnnotatedType.ElementType.PARAMETERIZED_TYPE, null, element);
      } else if (element instanceof GenericArrayType) {
         return of(JavaAnnotatedType.ElementType.GENERIC_ARRAY, null, element);
      } else {
         return element instanceof WildcardType ? of(JavaAnnotatedType.ElementType.WILDCARD, null, element) : null;
      }
   }

   private static JavaAnnotatedType of(AnnotatedElement element) {
      if (element instanceof AnnotatedParameterizedType) {
         return of(JavaAnnotatedType.ElementType.ANNOTATED_PARAMETERIZED_TYPE, element, null);
      } else {
         return element instanceof AnnotatedType ? of(JavaAnnotatedType.ElementType.ANNOTATED_TYPE, element, null) : null;
      }
   }

   private static JavaAnnotatedType of(JavaAnnotatedType.ElementType elementType, AnnotatedElement annotatedElement, Type type) {
      return new JavaAnnotatedType(elementType, annotatedElement, type);
   }

   private static RuntimeException invalid(Object object) {
      String className = object == null ? null : object.getClass().getName();
      return new IllegalArgumentException("Unable to convert " + object + " (" + className + ") to a JavaAnnotatedType");
   }

   public JavaAnnotatedType.ElementType getElementType() {
      return this.elementType;
   }

   public AnnotatedElement getAnnotatedElement() {
      return this.annotatedElement;
   }

   public Type getType() {
      return this.type;
   }

   @Override
   public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
      return this.ifAnnotatedElement(it -> it.isAnnotationPresent(annotationClass), () -> false);
   }

   @Override
   public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
      return (T[])this.ifAnnotatedElement(it -> it.getAnnotationsByType(annotationClass), () -> (Annotation[])Array.newInstance(annotationClass, 0));
   }

   @Override
   public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
      return this.ifAnnotatedElement(it -> it.getDeclaredAnnotation(annotationClass), () -> null);
   }

   @Override
   public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
      return (T[])this.ifAnnotatedElement(it -> it.getDeclaredAnnotationsByType(annotationClass), () -> (Annotation[])Array.newInstance(annotationClass, 0));
   }

   @Override
   public String getTypeName() {
      return this.ifType(Type::getTypeName, () -> "invalid type");
   }

   @Override
   public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
      return this.ifAnnotatedElement(it -> it.getAnnotation(annotationClass), () -> null);
   }

   @Override
   public Annotation[] getAnnotations() {
      return this.ifAnnotatedElement(AnnotatedElement::getAnnotations, () -> new Annotation[0]);
   }

   @Override
   public Annotation[] getDeclaredAnnotations() {
      return this.ifAnnotatedElement(AnnotatedElement::getDeclaredAnnotations, () -> new Annotation[0]);
   }

   private <T> T ifAnnotatedElement(Function<AnnotatedElement, T> block, Supplier<T> orElse) {
      return this.annotatedElement == null ? orElse.get() : block.apply(this.annotatedElement);
   }

   private <T> T ifType(Function<Type, T> block, Supplier<T> orElse) {
      return this.type == null ? orElse.get() : block.apply(this.type);
   }

   @Override
   public String toString() {
      return "JavaAnnotatedType{elementType=" + this.elementType + ", annotatedElement=" + this.annotatedElement + ", type=" + this.type + "}";
   }

   public enum ElementType {
      ANNOTATED_PARAMETERIZED_TYPE,
      ANNOTATED_TYPE,
      CLASS,
      GENERIC_ARRAY,
      PARAMETERIZED_TYPE,
      TYPE_VARIABLE,
      WILDCARD;
   }
}
