package net.unusual.block_factorys_bosses.state;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.world.entity.Entity;

public interface State<E extends Entity> {
   default void onStart(ActiveState<E> state) {
   }

   @Nonnull
   State.Result onTick(ActiveState<E> var1);

   default void onEnd(ActiveState<E> state) {
   }

   static <E extends Entity> StateRef<E> named(String name) {
      return new StateRef<>(name);
   }

   static <E extends Entity> LambdaState.LambdaStateBuilder<E> builder() {
      return new LambdaState.LambdaStateBuilder<>();
   }

   static <E extends Entity, D extends StateData> LambdaDataState.LambdaDataStateBuilder<E, D> withData(Codec<D> dataCodec, Supplier<D> defaultDataSupplier) {
      return new LambdaDataState.LambdaDataStateBuilder<E, D>().withData(dataCodec, defaultDataSupplier);
   }

   enum Result {
      CONTINUE,
      END;
   }

   interface StateBuilder<E extends Entity> {
      State<E> build();
   }
}
