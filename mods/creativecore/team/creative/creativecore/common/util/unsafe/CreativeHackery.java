package team.creative.creativecore.common.util.unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

public class CreativeHackery {
   private static final Unsafe UNSAFE;

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
               public Unsafe run() throws Exception {
                  Class<Unsafe> k = Unsafe.class;

                  for (Field f : k.getDeclaredFields()) {
                     f.setAccessible(true);
                     Object x = f.get(null);
                     if (k.isInstance(x)) {
                        return k.cast(x);
                     }
                  }

                  throw new NoSuchFieldError("the Unsafe");
               }
            });
         } catch (PrivilegedActionException e) {
            throw new RuntimeException("Could not initialize intrinsics", e.getCause());
         }
      }
   }

   public static <T> T allocateInstance(Class<T> clazz) {
      try {
         return (T)UNSAFE.allocateInstance(clazz);
      } catch (InstantiationException e) {
         throw new Error(e);
      }
   }

   static {
      try {
         UNSAFE = getUnsafe();
      } catch (Exception e) {
         throw new Error(e);
      }
   }
}
