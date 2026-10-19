package com.blamejared.crafttweaker.api.util;

import java.util.function.Function;

public class GenericUtil {
   public static <T> T uncheck(Object o) {
      return (T)o;
   }

   public static <T, U, V> Function<U, T> uncheckFunc(Function<U, V> func) {
      return (Function<U, T>)func;
   }
}
