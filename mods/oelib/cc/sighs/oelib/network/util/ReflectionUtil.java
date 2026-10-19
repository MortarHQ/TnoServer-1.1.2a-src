package cc.sighs.oelib.network.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public final class ReflectionUtil {
   private ReflectionUtil() {
   }

   public static <T> Constructor<T> findCanonicalConstructor(Class<T> clazz, Class<?>[] paramTypes) {
      try {
         Constructor<T> ctor = clazz.getDeclaredConstructor(paramTypes);
         ctor.setAccessible(true);
         return ctor;
      } catch (NoSuchMethodException e) {
         throw new IllegalStateException("Canonical constructor not found for record: " + clazz.getName(), e);
      }
   }

   public static <T> T newInstance(Constructor<T> ctor, Object... args) {
      try {
         return ctor.newInstance(args);
      } catch (Exception e) {
         throw new IllegalStateException("Failed to instantiate record: " + ctor.getDeclaringClass().getName(), e);
      }
   }

   public static Object invoke(Method method, Object instance) {
      try {
         return method.invoke(instance);
      } catch (Exception e) {
         throw new IllegalStateException("Failed to invoke method: " + method.getName(), e);
      }
   }

   public static StreamCodec<RegistryFriendlyByteBuf, Object> getStaticFieldAsCodec(Class<?> recordClass, Class<?> holder, Field field) {
      try {
         field.setAccessible(true);
         String errorInfo = "Field " + holder.getName() + "#" + field.getName() + " is not a StreamCodec";

         try {
            MethodHandle getter = MethodHandles.lookup().unreflectGetter(field);
            if (getter.invokeWithArguments() instanceof StreamCodec<?, ?> codec) {
               return (StreamCodec<RegistryFriendlyByteBuf, Object>)codec;
            } else {
               throw new IllegalStateException(errorInfo);
            }
         } catch (IllegalAccessException e) {
            if (field.get(null) instanceof StreamCodec<?, ?> codec) {
               return (StreamCodec<RegistryFriendlyByteBuf, Object>)codec;
            } else {
               throw new IllegalStateException(errorInfo);
            }
         }
      } catch (Throwable t) {
         throw new IllegalStateException(
            "Failed to resolve codec field " + holder.getName() + "#" + field.getName() + " for record " + recordClass.getName(), t
         );
      }
   }

   public static <T> T getStaticField(Class<?> recordClass, Class<?> holder, Field field, Class<T> expectedType) {
      try {
         field.setAccessible(true);
         String errorInfo = "Field " + holder.getName() + "#" + field.getName() + " is not a " + expectedType.getSimpleName();

         try {
            MethodHandle getter = MethodHandles.lookup().unreflectGetter(field);
            Object obj = getter.invokeWithArguments();
            if (!expectedType.isInstance(obj)) {
               throw new IllegalStateException(errorInfo);
            } else {
               return (T)obj;
            }
         } catch (IllegalAccessException e) {
            Object obj = field.get(null);
            if (!expectedType.isInstance(obj)) {
               throw new IllegalStateException(errorInfo);
            } else {
               return (T)obj;
            }
         }
      } catch (Throwable t) {
         throw new IllegalStateException("Failed to resolve field " + holder.getName() + "#" + field.getName() + " for record " + recordClass.getName(), t);
      }
   }
}
