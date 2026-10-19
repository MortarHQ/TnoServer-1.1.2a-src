package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.platform.Services;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.core.util.ObjectArrayIterator;

public final class HandleUtil {
   private static final Lookup LOOKUP = findLookup();

   private HandleUtil() {
   }

   public static MethodHandle linkMethod(Class<?> type, HandleUtil.AccessType accessType, String methodName, Class<?> returnType, Class<?>... arguments) {
      return linkMethod(type, accessType, HandleUtil.Names.of(methodName), returnType, arguments);
   }

   public static MethodHandle linkMethod(
      Class<?> type, HandleUtil.AccessType accessType, HandleUtil.Names methodNames, Class<?> returnType, Class<?>... arguments
   ) {
      MethodType signature = MethodType.methodType(returnType, arguments);
      List<NoSuchMethodException> exceptions = null;

      for (String methodName : methodNames) {
         try {
            String targetName = Services.PLATFORM.findMappedMethodName(type, methodName, returnType, arguments);

            return switch (accessType) {
               case VIRTUAL -> LOOKUP.findVirtual(type, targetName, signature);
               case STATIC -> LOOKUP.findStatic(type, targetName, signature);
            };
         } catch (NoSuchMethodException e) {
            (exceptions == null ? (exceptions = new ArrayList<>()) : exceptions).add(e);
         } catch (IllegalAccessException e) {
            throw new HandleUtil.UnableToLinkHandleException("Unable to access method " + StringUtil.quoteAndEscape(methodName), e);
         }
      }

      Throwable cause = exceptions == null ? new NullPointerException("No method name specified") : exceptions.get(0);
      HandleUtil.UnableToLinkHandleException e = new HandleUtil.UnableToLinkHandleException(
         "No method with names " + methodNames + " found in target type " + type.getName(), cause
      );
      int i = 1;

      for (int s = exceptions == null ? 0 : exceptions.size(); i < s; i++) {
         e.addSuppressed(exceptions.get(i));
      }

      throw e;
   }

   public static VarHandle linkField(Class<?> owner, HandleUtil.AccessType accessType, String fieldName, Class<?> type) {
      return linkField(owner, accessType, HandleUtil.Names.of(fieldName), type);
   }

   public static VarHandle linkField(Class<?> owner, HandleUtil.AccessType accessType, HandleUtil.Names fieldNames, Class<?> type) {
      List<NoSuchFieldException> exceptions = null;

      for (String fieldName : fieldNames) {
         try {
            String targetName = Services.PLATFORM.findMappedFieldName(owner, fieldName, type);

            return switch (accessType) {
               case VIRTUAL -> LOOKUP.findVarHandle(owner, targetName, type);
               case STATIC -> LOOKUP.findStaticVarHandle(owner, targetName, type);
            };
         } catch (NoSuchFieldException e) {
            (exceptions == null ? (exceptions = new ArrayList<>()) : exceptions).add(e);
         } catch (IllegalAccessException e) {
            throw new HandleUtil.UnableToLinkHandleException("Unable to access field " + StringUtil.quoteAndEscape(fieldName), e);
         }
      }

      Throwable cause = exceptions == null ? new NullPointerException("No method name specified") : exceptions.get(0);
      HandleUtil.UnableToLinkHandleException e = new HandleUtil.UnableToLinkHandleException(
         "No field with names " + fieldNames + " found in target type " + type.getName(), cause
      );
      int i = 1;

      for (int s = exceptions == null ? 0 : exceptions.size(); i < s; i++) {
         e.addSuppressed(exceptions.get(i));
      }

      throw e;
   }

   public static <R> R invoke(HandleUtil.MethodHandleInvoker<R> invoker) {
      try {
         return invoker.invoke();
      } catch (WrongMethodTypeException e) {
         throw new HandleUtil.FailedInvocationException("Unable to invoke target handle: check your arguments", e);
      } catch (Throwable throwable) {
         if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
         } else {
            throw new RuntimeException("Invoked handle threw an exception", throwable);
         }
      }
   }

   public static void invokeVoid(HandleUtil.MethodHandleVoidInvoker invoker) {
      try {
         invoker.invoke();
      } catch (WrongMethodTypeException e) {
         throw new HandleUtil.FailedInvocationException("Unable to invoke target handle: check your arguments", e);
      } catch (Throwable throwable) {
         if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
         } else {
            throw new RuntimeException("Invoked handle threw an exception", throwable);
         }
      }
   }

   private static Lookup findLookup() {
      try {
         Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
         Lookup unsafeLookup = MethodHandles.privateLookupIn(unsafeClass, MethodHandles.lookup());
         VarHandle unsafeField = unsafeLookup.findStaticVarHandle(unsafeClass, "theUnsafe", unsafeClass);
         MethodHandle staticFieldBase = unsafeLookup.findVirtual(unsafeClass, "staticFieldBase", MethodType.methodType(Object.class, Field.class));
         MethodHandle staticFieldOffset = unsafeLookup.findVirtual(unsafeClass, "staticFieldOffset", MethodType.methodType(long.class, Field.class));
         MethodHandle getObject = unsafeLookup.findVirtual(unsafeClass, "getObject", MethodType.methodType(Object.class, Object.class, long.class));
         Object unsafe = (Object)unsafeField.get();
         Class<?> methodHandlesLookupClass = Lookup.class;
         Field[] declaredFields = methodHandlesLookupClass.getDeclaredFields();

         for (Field declaredField : declaredFields) {
            if (declaredField.getType() == Lookup.class) {
               Object base = (Object)staticFieldBase.invoke((Object)unsafe, (Field)declaredField);
               long offset = (long)staticFieldOffset.invoke((Object)unsafe, (Field)declaredField);
               Object lookupObject = (Object)getObject.invoke((Object)unsafe, (Object)base, (long)offset);
               Lookup lookup = GenericUtil.uncheck(lookupObject);
               if (lookup.lookupModes() == 127) {
                  return lookup;
               }
            }
         }
      } catch (Throwable e) {
         throw new IllegalStateException("Unable to find lookup", e);
      }

      throw new IllegalStateException("Unable to find lookup");
   }

   public enum AccessType {
      VIRTUAL,
      STATIC;
   }

   public static final class FailedInvocationException extends RuntimeException {
      private FailedInvocationException(String message, Throwable cause) {
         super(message, cause);
      }
   }

   @FunctionalInterface
   public interface MethodHandleInvoker<R> {
      R invoke() throws Throwable;
   }

   public interface MethodHandleVoidInvoker {
      void invoke() throws Throwable;
   }

   public static final class Names implements Iterable<String> {
      private final String[] names;

      private Names(String... names) {
         this.names = names;
      }

      public static HandleUtil.Names of(String... names) {
         return new HandleUtil.Names(Set.of(names).toArray(String[]::new));
      }

      @Override
      public Iterator<String> iterator() {
         return new ObjectArrayIterator(this.names);
      }

      @Override
      public String toString() {
         return Arrays.toString(this.names);
      }
   }

   public static final class UnableToLinkHandleException extends RuntimeException {
      private UnableToLinkHandleException(String message, Throwable cause) {
         super(message, cause);
      }
   }
}
