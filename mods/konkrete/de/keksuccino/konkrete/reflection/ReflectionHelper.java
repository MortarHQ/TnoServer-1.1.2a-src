package de.keksuccino.konkrete.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public class ReflectionHelper {
   public static Field findField(@NotNull Class<?> c, @NotNull String... names) throws NoSuchFieldException {
      Field f = null;

      for (String s : names) {
         try {
            f = c.getDeclaredField(s);
            f.setAccessible(true);
            break;
         } catch (Exception var8) {
         }
      }

      if (f == null) {
         throw new NoSuchFieldException("No field found matching one of the given names: " + Arrays.toString(names));
      } else {
         return f;
      }
   }

   public static Method findMethod(@NotNull Class<?> c, @NotNull String deobfName, @NotNull String obfName, Class<?>... args) throws NoSuchMethodException {
      Method m = null;

      try {
         m = c.getDeclaredMethod(deobfName, args);
         m.setAccessible(true);
      } catch (Exception var7) {
      }

      try {
         m = c.getDeclaredMethod(obfName, args);
         m.setAccessible(true);
      } catch (Exception var6) {
      }

      if (m == null) {
         throw new NoSuchMethodException("No method found matching one of the given names: " + deobfName + ", " + obfName);
      } else {
         return m;
      }
   }
}
