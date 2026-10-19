package stdlib;

import java.util.function.Function;

public abstract class Result<T, E> {
   public static <T, E extends Exception> T unwrap(Class<T> typeOfT, Class<E> typeOfE, Result<T, E> self) throws E {
      switch (self.getDiscriminant()) {
         case Ok:
            return ((Result.Ok)self).value;
         case Error:
            E error = (E)((Result.Error)self).value;
            throw error;
         default:
            throw new AssertionError("Missing case");
      }
   }

   public <R> Result<R, E> then(Class<R> typeOfR, Function<T, Result<R, E>> fn) {
      Result<R, E> temp1;
      switch (this.getDiscriminant()) {
         case Ok:
            T result = ((Result.Ok)this).value;
            temp1 = fn.apply(result);
            break;
         case Error:
            E error = ((Result.Error)this).value;
            temp1 = (Result<R, E>)(new Result.Error<>(error));
            break;
         default:
            throw new AssertionError("Missing case");
      }

      return temp1;
   }

   public <X> Result<T, X> handle(Class<X> typeOfX, Function<E, Result<T, X>> handler) {
      Result<T, X> temp1;
      switch (this.getDiscriminant()) {
         case Ok:
            T result = ((Result.Ok)this).value;
            temp1 = (Result<T, X>)(new Result.Ok<>(result));
            break;
         case Error:
            E error = ((Result.Error)this).value;
            temp1 = handler.apply(error);
            break;
         default:
            throw new AssertionError("Missing case");
      }

      return temp1;
   }

   public T expect() {
      switch (this.getDiscriminant()) {
         case Ok:
            return ((Result.Ok)this).value;
         case Error:
            E error = ((Result.Error)this).value;
            throw new AssertionError("expect() called on an error value");
         default:
            throw new AssertionError("Missing case");
      }
   }

   public T orElse(T other) {
      T temp1;
      switch (this.getDiscriminant()) {
         case Ok:
            T result = ((Result.Ok)this).value;
            temp1 = result;
            break;
         case Error:
            E error = ((Result.Error)this).value;
            temp1 = other;
            break;
         default:
            throw new AssertionError("Missing case");
      }

      return temp1;
   }

   public T orElse(Function<E, T> other) {
      T temp1;
      switch (this.getDiscriminant()) {
         case Ok:
            T result = ((Result.Ok)this).value;
            temp1 = result;
            break;
         case Error:
            E error = ((Result.Error)this).value;
            temp1 = other.apply(error);
            break;
         default:
            throw new AssertionError("Missing case");
      }

      return temp1;
   }

   public abstract Result.Discriminant getDiscriminant();

   public enum Discriminant {
      Ok,
      Error;
   }

   public static class Error<T, E> extends Result<T, E> {
      public final E value;

      public Error(E value) {
         this.value = value;
      }

      @Override
      public Result.Discriminant getDiscriminant() {
         return Result.Discriminant.Error;
      }
   }

   public static class Ok<T, E> extends Result<T, E> {
      public final T value;

      public Ok(T value) {
         this.value = value;
      }

      @Override
      public Result.Discriminant getDiscriminant() {
         return Result.Discriminant.Ok;
      }
   }
}
