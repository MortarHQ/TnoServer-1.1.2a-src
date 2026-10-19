package com.blamejared.crafttweaker.api.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

public class InstantiationUtil {
   public static <T> T getOrCreateInstance(Class<T> cls) {
      try {
         Optional<Field> any = Arrays.stream(cls.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()))
            .filter(f -> Modifier.isStatic(f.getModifiers()))
            .filter(f -> f.getType().equals(cls))
            .findAny();
         if (any.isPresent()) {
            return (T)any.get().get(null);
         }
      } catch (IllegalAccessException var3) {
      }

      try {
         return cls.newInstance();
      } catch (InstantiationException | IllegalAccessException var2) {
         return null;
      }
   }
}
