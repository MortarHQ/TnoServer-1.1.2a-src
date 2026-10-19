package net.unusual.block_factorys_bosses.state;

import com.mojang.serialization.Codec;
import javax.annotation.Nonnull;
import net.minecraft.world.entity.Entity;

public interface DataState<E extends Entity, D extends StateData> extends State<E> {
   @Nonnull
   D createData();

   Codec<D> getDataCodec();

   default void onStart(ActiveDataState<E, D> state) {
   }

   @Nonnull
   State.Result onTick(ActiveDataState<E, D> var1);

   default void onEnd(ActiveDataState<E, D> state) {
   }

   @Override
   default void onStart(ActiveState<E> state) {
      this.onStart((ActiveDataState<E, D>)state);
   }

   @Nonnull
   @Override
   default State.Result onTick(ActiveState<E> state) {
      return this.onTick((ActiveDataState<E, D>)state);
   }

   @Override
   default void onEnd(ActiveState<E> state) {
      this.onEnd((ActiveDataState<E, D>)state);
   }
}
