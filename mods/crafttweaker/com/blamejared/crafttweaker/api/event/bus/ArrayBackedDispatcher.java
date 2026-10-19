package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.util.GenericUtil;

final class ArrayBackedDispatcher<T> {
   private static final IEventDispatcher<Object> UNREGISTERED = it -> {};
   private IEventDispatcher<T>[] dispatchers = GenericUtil.uncheck(new IEventDispatcher[0]);

   public IHandlerToken<T> register(IEventDispatcher<T> dispatcher) {
      IHandlerToken<T> token = this.doRegister(dispatcher, false);
      if (token != null) {
         return token;
      }

      this.grow();
      return this.doRegister(dispatcher, true);
   }

   public void unregister(IHandlerToken<T> token) {
      if (!(token instanceof ArrayBackedDispatcher.ArrayHandlerToken<T> arrayToken)) {
         throw new IllegalArgumentException("Invalid token object for dispatcher");
      } else {
         int position = arrayToken.index();
         int next = position + 1;
         IEventDispatcher<T> newConsumer = next != this.dispatchers.length && this.dispatchers[next] != null ? GenericUtil.uncheck(UNREGISTERED) : null;
         this.dispatchers[position] = newConsumer;
         if (newConsumer == null) {
            for (int i = position - 1; i >= 0 && this.dispatchers[i] == UNREGISTERED; i--) {
               this.dispatchers[i] = null;
            }
         }

         arrayToken.invalidate();
      }
   }

   public void dispatch(T event) {
      for (IEventDispatcher<T> dispatcher : this.dispatchers) {
         if (dispatcher == null) {
            break;
         }

         dispatcher.dispatch(event);
      }
   }

   private IHandlerToken<T> doRegister(IEventDispatcher<T> dispatcher, boolean exception) {
      int i = 0;

      for (int s = this.dispatchers.length; i < s; i++) {
         if (this.dispatchers[i] == null || this.dispatchers[i] == UNREGISTERED) {
            this.dispatchers[i] = new ArrayBackedDispatcher.CatchingDispatcher<>(dispatcher);
            return ArrayBackedDispatcher.ArrayHandlerToken.of(i);
         }
      }

      if (exception) {
         throw new IllegalStateException("Unable to register handler");
      } else {
         return null;
      }
   }

   private void grow() {
      int length = this.dispatchers.length;
      IEventDispatcher<T>[] replacement = GenericUtil.uncheck(new IEventDispatcher[length * 3 / 2 + 1]);
      System.arraycopy(this.dispatchers, 0, replacement, 0, length);
      this.dispatchers = replacement;
   }

   private static final class ArrayHandlerToken<T> implements IHandlerToken<T> {
      private final int index;
      private boolean valid;

      private ArrayHandlerToken(int index) {
         this.index = index;
         this.valid = true;
      }

      static <T> ArrayBackedDispatcher.ArrayHandlerToken<T> of(int index) {
         return new ArrayBackedDispatcher.ArrayHandlerToken<>(index);
      }

      int index() {
         this.valid();
         return this.index;
      }

      void invalidate() {
         this.valid = false;
      }

      private void valid() {
         if (!this.valid) {
            throw new IllegalStateException("Token has been invalidated");
         }
      }
   }

   private record CatchingDispatcher<T>(IEventDispatcher<T> dispatcher) implements IEventDispatcher<T> {
      @Override
      public void dispatch(T event) {
         try {
            this.dispatcher().dispatch(event);
         } catch (Exception e) {
            throw new BusHandlingException(e);
         }
      }
   }
}
