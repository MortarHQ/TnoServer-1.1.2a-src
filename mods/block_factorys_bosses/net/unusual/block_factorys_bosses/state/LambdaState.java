package net.unusual.block_factorys_bosses.state;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.world.entity.Entity;

@ParametersAreNonnullByDefault
public record LambdaState<E extends Entity>(
   @Nullable Consumer<ActiveState<E>> startCallback, Function<ActiveState<E>, State.Result> tickCallback, @Nullable Consumer<ActiveState<E>> endCallback
) implements State<E> {
   @Override
   public void onStart(ActiveState<E> state) {
      if (this.startCallback != null) {
         this.startCallback.accept(state);
      }
   }

   @Nonnull
   @Override
   public State.Result onTick(ActiveState<E> state) {
      return this.tickCallback.apply(state);
   }

   @Override
   public void onEnd(ActiveState<E> state) {
      if (this.endCallback != null) {
         this.endCallback.accept(state);
      }
   }

   public static class LambdaStateBuilder<E extends Entity> implements State.StateBuilder<E> {
      @Nullable
      private Consumer<ActiveState<E>> startCallback = null;
      @Nullable
      private Function<ActiveState<E>, State.Result> tickCallback = null;
      @Nullable
      private Consumer<ActiveState<E>> endCallback = null;

      public LambdaState.LambdaStateBuilder<E> onStart(Consumer<ActiveState<E>> callback) {
         if (this.startCallback != null) {
            throw new IllegalStateException("Tried to replace the start callback. You probably didn't mean to do this.");
         }

         this.startCallback = callback;
         return this;
      }

      private void checkTickCallbackNotSet() {
         if (this.tickCallback != null) {
            throw new IllegalStateException("Tried to replace the tick callback. You probably didn't mean to do this.");
         }
      }

      public LambdaState.LambdaStateBuilder<E> onTick(Function<ActiveState<E>, State.Result> callback) {
         this.checkTickCallbackNotSet();
         this.tickCallback = callback;
         return this;
      }

      public LambdaState.LambdaStateBuilder<E> onTickFor(int ticks, Consumer<ActiveState<E>> callback) {
         this.checkTickCallbackNotSet();
         this.tickCallback = state -> {
            callback.accept(state);
            return state.getTimer() >= ticks ? State.Result.END : State.Result.CONTINUE;
         };
         return this;
      }

      public LambdaState.LambdaStateBuilder<E> endAfter(int ticks) {
         this.checkTickCallbackNotSet();
         this.tickCallback = state -> state.getTimer() >= ticks ? State.Result.END : State.Result.CONTINUE;
         return this;
      }

      public LambdaState.LambdaStateBuilder<E> onEnd(Consumer<ActiveState<E>> callback) {
         if (this.endCallback != null) {
            throw new IllegalStateException("Tried to replace the end callback. You probably didn't mean to do this.");
         }

         this.endCallback = callback;
         return this;
      }

      public LambdaState<E> build() {
         if (this.tickCallback == null) {
            throw new IllegalStateException("StateBuilder has a null tickCallback. Call onTick or endsAfter.");
         } else {
            return new LambdaState<>(this.startCallback, this.tickCallback, this.endCallback);
         }
      }
   }
}
