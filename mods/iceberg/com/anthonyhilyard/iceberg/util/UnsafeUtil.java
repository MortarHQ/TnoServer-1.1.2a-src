package com.anthonyhilyard.iceberg.util;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeUtil {
   private static final Unsafe UNSAFE;

   public static float readFloat(long address) {
      return UNSAFE.getFloat(address);
   }

   public static int readInt(long address) {
      return UNSAFE.getInt(address);
   }

   public static byte readByte(long address) {
      return UNSAFE.getByte(address);
   }

   public static <T> T newInstance(Class<T> clazz) {
      try {
         return cast(UNSAFE.allocateInstance(clazz));
      } catch (InstantiationException e) {
         return sneak(e);
      }
   }

   public static void setField(Field field, Object instance, Object value) {
      UNSAFE.putObject(instance, UNSAFE.objectFieldOffset(field), value);
   }

   public static <T> T getField(Field field, Object instance) {
      return cast(UNSAFE.getObject(instance, UNSAFE.objectFieldOffset(field)));
   }

   private static <E extends Throwable, R> R sneak(Throwable e) throws E {
      throw e;
   }

   private static <T> T cast(Object inst) {
      return (T)inst;
   }

   static {
      try {
         Field field = Unsafe.class.getDeclaredField("theUnsafe");
         field.setAccessible(true);
         UNSAFE = (Unsafe)field.get(null);
      } catch (NoSuchFieldException | IllegalAccessException e) {
         throw new RuntimeException("Couldn't obtain reference to sun.misc.Unsafe", e);
      }
   }
}
