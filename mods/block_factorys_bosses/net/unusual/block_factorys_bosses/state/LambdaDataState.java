package net.unusual.block_factorys_bosses.state;

import com.mojang.serialization.Codec;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@ParametersAreNonnullByDefault
public record LambdaDataState<E extends Entity, D extends StateData>(
   Codec<D> dataCodec,
   Supplier<D> dataStateSupplier,
   @Nullable Consumer<ActiveDataState<E, D>> startCallback,
   Function<ActiveDataState<E, D>, State.Result> tickCallback,
   @Nullable Consumer<ActiveDataState<E, D>> endCallback
) implements DataState<E, D> {
   @NotNull
   @Override
   public D createData() {
      return this.dataStateSupplier.get();
   }

   @Override
   public Codec<D> getDataCodec() {
      return this.dataCodec;
   }

   @Override
   public void onStart(ActiveDataState<E, D> state) {
      if (this.startCallback != null) {
         this.startCallback.accept(state);
      }
   }

   @Nonnull
   @Override
   public State.Result onTick(ActiveDataState<E, D> state) {
      return this.tickCallback.apply(state);
   }

   @Override
   public void onEnd(ActiveDataState<E, D> state) {
      if (this.endCallback != null) {
         this.endCallback.accept(state);
      }
   }

   public static class LambdaDataStateBuilder<E extends Entity, D extends StateData> implements State.StateBuilder<E> {
      @Nullable
      private Codec<D> dataCodec = null;
      @Nullable
      private Supplier<D> createDataCallback = null;
      @Nullable
      private Consumer<ActiveDataState<E, D>> startCallback = null;
      @Nullable
      private Function<ActiveDataState<E, D>, State.Result> tickCallback = null;
      @Nullable
      private Consumer<ActiveDataState<E, D>> endCallback = null;

      public LambdaDataState.LambdaDataStateBuilder<E, D> withData(Codec<D> codec, Supplier<D> callback) {
         this.dataCodec = codec;
         this.createDataCallback = callback;
         return this;
      }

      public LambdaDataState.LambdaDataStateBuilder<E, D> onStart(Consumer<ActiveDataState<E, D>> callback) {
         this.startCallback = callback;
         return this;
      }

      private void checkTickCallbackNotSet() {
         if (this.tickCallback != null) {
            throw new IllegalStateException("Tried to replace the tick callback. You probably didn't mean to do this.");
         }
      }

      public LambdaDataState.LambdaDataStateBuilder<E, D> onTick(Function<ActiveDataState<E, D>, State.Result> callback) {
         this.checkTickCallbackNotSet();
         this.tickCallback = callback;
         return this;
      }

      public LambdaDataState.LambdaDataStateBuilder<E, D> onTickFor(int ticks, Consumer<ActiveDataState<E, D>> callback) {
         this.checkTickCallbackNotSet();
         this.tickCallback = state -> {
            callback.accept(state);
            return state.getTimer() >= ticks ? State.Result.END : State.Result.CONTINUE;
         };
         return this;
      }

      public LambdaDataState.LambdaDataStateBuilder<E, D> endAfter(int ticks) {
         this.checkTickCallbackNotSet();
         this.tickCallback = state -> state.getTimer() >= ticks ? State.Result.END : State.Result.CONTINUE;
         return this;
      }

      public LambdaDataState.LambdaDataStateBuilder<E, D> onEnd(Consumer<ActiveDataState<E, D>> callback) {
         this.endCallback = callback;
         return this;
      }

      public LambdaDataState<E, D> build() {
         if (this.dataCodec == null) {
            throw new IllegalStateException("StateBuilder has a null dataCodec.");
         } else if (this.createDataCallback == null) {
            throw new IllegalStateException("StateBuilder has a null createDataCallback.");
         } else if (this.tickCallback == null) {
            throw new IllegalStateException("StateBuilder has a null tickCallback. Call onTick or endsAfter.");
         } else {
            return new LambdaDataState<>(this.dataCodec, this.createDataCallback, this.startCallback, this.tickCallback, this.endCallback);
         }
      }
   }
}
