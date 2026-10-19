package com.bobmowzie.mowziesmobs.server.world.feature.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class BlockStateRandomizer {
   public static final Codec<BlockStateRandomizer> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            BlockStateRandomizer.Entry.CODEC.listOf().optionalFieldOf("entries").forGetter(selector -> selector.entries),
            BlockState.CODEC.fieldOf("default").forGetter(selector -> selector.defaultState)
         )
         .apply(instance, BlockStateRandomizer::new)
   );
   private final Optional<List<BlockStateRandomizer.Entry>> entries;
   private final BlockState defaultState;

   public BlockStateRandomizer(Optional<List<BlockStateRandomizer.Entry>> entries, BlockState defaultBlockState) {
      this.entries = entries;
      this.defaultState = defaultBlockState;
   }

   public BlockState chooseRandomState(RandomSource randomSource) {
      if (this.entries.isPresent()) {
         float total = 0.0F;

         for (BlockStateRandomizer.Entry entry : this.entries.get()) {
            total += entry.weight;
         }

         if (total != 0.0F) {
            float target = randomSource.nextFloat();
            float currBottom = 0.0F;

            for (BlockStateRandomizer.Entry entry : this.entries.get()) {
               if (currBottom <= target && target < currBottom + entry.weight / total) {
                  return entry.blockState;
               }

               currBottom += entry.weight / total;
            }
         }
      }

      return this.defaultState;
   }

   public static class Entry {
      public static Codec<BlockStateRandomizer.Entry> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BlockState.CODEC.fieldOf("blockState").forGetter(entry -> entry.blockState), Codec.INT.fieldOf("weight").forGetter(entry -> entry.weight)
            )
            .apply(instance, BlockStateRandomizer.Entry::new)
      );
      public BlockState blockState;
      public int weight;

      public Entry(BlockState blockState, int weight) {
         this.blockState = blockState;
         this.weight = weight;
      }
   }
}
