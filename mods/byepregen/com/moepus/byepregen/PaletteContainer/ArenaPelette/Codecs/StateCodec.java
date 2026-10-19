package com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs;

import com.moepus.byepregen.PaletteContainer.ByteStreamTag;
import com.moepus.byepregen.PaletteContainer.ArenaPelette.ArenaBlockStatePalettedContainer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.PalettedContainer;

public final class StateCodec implements Codec<PalettedContainer<BlockState>> {
   private final Codec<PalettedContainer<BlockState>> fallback;

   public StateCodec(Codec<PalettedContainer<BlockState>> fallback) {
      this.fallback = fallback;
   }

   public <T> DataResult<Pair<PalettedContainer<BlockState>, T>> decode(DynamicOps<T> ops, T input) {
      return this.fallback.decode(ops, input);
   }

   public <T> DataResult<T> encode(PalettedContainer<BlockState> input, DynamicOps<T> ops, T prefix) {
      return ops == NbtOps.INSTANCE && input instanceof ArenaBlockStatePalettedContainer arena
         ? DataResult.success(cast(encodeArena(arena)))
         : this.fallback.encode(input, ops, prefix);
   }

   private static CompoundTag encodeArena(ArenaBlockStatePalettedContainer container) {
      if (container.isUniform()) {
         return ByteStreamTag.uniform(container.uniformRawId());
      }

      SerializationScratch scratch = SerializationScratch.get();

      try {
         scratch.collect(container);
         return ByteStreamTag.packed(scratch, container);
      } finally {
         scratch.clear();
      }
   }

   private static <T> T cast(CompoundTag tag) {
      return (T)tag;
   }
}
