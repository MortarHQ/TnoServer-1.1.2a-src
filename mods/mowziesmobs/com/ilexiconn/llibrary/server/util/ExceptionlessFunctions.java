package com.ilexiconn.llibrary.server.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExceptionlessFunctions {
   public static <E extends Throwable> Runnable uncheckedRunnable(ExceptionlessFunctions.ThrowingRunnable<E> t) throws E {
      return () -> {
         try {
            t.accept();
         } catch (Throwable exception) {
            throwActualException(exception);
         }
      };
   }

   public static <T, E extends Exception> Consumer<T> uncheckedConsumer(ExceptionlessFunctions.ThrowingConsumer<T, E> consumer) throws E {
      return t -> {
         try {
            consumer.accept(t);
         } catch (Throwable exception) {
            throwActualException(exception);
         }
      };
   }

   public static <T, E extends Exception> Supplier<T> uncheckedSupplier(ExceptionlessFunctions.ThrowingSupplier<T, E> supplier) throws E {
      return () -> {
         try {
            return supplier.get();
         } catch (Throwable exception) {
            return throwActualException(exception);
         }
      };
   }

   public static <T, R, E extends Exception> Function<T, R> uncheckedFunction(ExceptionlessFunctions.ThrowingFunction<T, R, E> function) throws E {
      return t -> {
         try {
            return function.apply(t);
         } catch (Throwable exception) {
            return throwActualException(exception);
         }
      };
   }

   private static <E extends Exception, T> T throwActualException(Throwable exception) throws E {
      throw (Exception)exception;
   }

   public interface ThrowingConsumer<T, E extends Throwable> {
      void accept(T var1) throws E;
   }

   public interface ThrowingFunction<T, R, E extends Throwable> {
      R apply(T var1) throws E;
   }

   public interface ThrowingRunnable<E extends Throwable> {
      void accept() throws E;
   }

   public interface ThrowingSupplier<T, E extends Throwable> {
      T get() throws E;
   }
}
