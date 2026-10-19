package org.openzen.zencode.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ZenCodeType {
   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE_USE)
   @interface BorrowForCall {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE_USE)
   @interface BorrowForThis {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.METHOD)
   @interface Caster {
      boolean implicit() default false;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.CONSTRUCTOR)
   @interface Constructor {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE)
   @interface Expansion {
      String value();
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   @interface Field {
      String value() default "";
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.METHOD)
   @interface Getter {
      String value() default "";
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.METHOD)
   @interface Method {
      String value() default "";
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE)
   @interface Name {
      String value();
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE_USE)
   @interface Nullable {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE_USE)
   @interface NullableUSize {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.METHOD)
   @interface Operator {
      ZenCodeType.OperatorType value();
   }

   enum OperatorType {
      ADD,
      SUB,
      MUL,
      DIV,
      MOD,
      CAT,
      OR,
      AND,
      XOR,
      NEG,
      INVERT,
      NOT,
      INDEXSET,
      INDEXGET,
      CONTAINS,
      COMPARE,
      MEMBERGETTER,
      MEMBERSETTER,
      EQUALS,
      NOTEQUALS,
      SHL,
      SHR,
      ADDASSIGN,
      SUBASSIGN,
      MULASSIGN,
      DIVASSIGN,
      MODASSIGN,
      CATASSIGN,
      ORASSIGN,
      ANDASSIGN,
      XORASSIGN,
      SHLASSIGN,
      SHRASSIGN;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.PARAMETER)
   @interface Optional {
      String value() default "";
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.PARAMETER)
   @interface OptionalBoolean {
      boolean value() default false;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.PARAMETER)
   @interface OptionalChar {
      char value() default ' ';
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.PARAMETER)
   @interface OptionalDouble {
      double value() default 0.0;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.PARAMETER)
   @interface OptionalFloat {
      float value() default 0.0F;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.PARAMETER)
   @interface OptionalInt {
      int value() default 0;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.PARAMETER)
   @interface OptionalLong {
      long value() default 0L;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.PARAMETER)
   @interface OptionalString {
      String value() default "";
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.METHOD)
   @interface Setter {
      String value() default "";
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.METHOD)
   @interface StaticExpansionMethod {
      String value() default "";
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE)
   @interface Struct {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE_USE)
   @interface USize {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE_USE)
   @interface Unsigned {
   }
}
