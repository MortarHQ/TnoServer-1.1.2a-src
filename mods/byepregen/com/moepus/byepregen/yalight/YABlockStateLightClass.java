package com.moepus.byepregen.yalight;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class YABlockStateLightClass {
   public static final int CLEAR = 0;
   public static final int FULL = 1;
   public static final int SHAPED = 2;
   public static final int SLOW = 3;
   private static final int VALUES_PER_WORD = 16;
   private static final int BITS_PER_VALUE = 2;
   private static final int VALUE_MASK = 3;
   private static final int[] PACKED = buildPackedTable();

   private YABlockStateLightClass() {
   }

   public static void initialize() {
   }

   public static int fromRawId(int rawId) {
      if (rawId >= 0 && rawId < Block.BLOCK_STATE_REGISTRY.size()) {
         int wordIndex = rawId / 16;
         if (wordIndex >= PACKED.length) {
            return 3;
         }

         int word = PACKED[wordIndex];
         int shift = rawId % 16 * 2;
         return word >>> shift & 3;
      } else {
         return 3;
      }
   }

   private static int[] buildPackedTable() {
      int size = Block.BLOCK_STATE_REGISTRY.size();
      int[] packed = new int[(size + 16 - 1) / 16];

      for (int rawId = 0; rawId < size; rawId++) {
         int lightClass = classifySafely(Block.stateById(rawId));
         packed[rawId / 16] = packed[rawId / 16] | lightClass << rawId % 16 * 2;
      }

      return packed;
   }

   private static int classifySafely(BlockState state) {
      if (state == null) {
         return 3;
      }

      try {
         return classify(state);
      } catch (Throwable throwable) {
         return 3;
      }
   }

   private static int classify(BlockState state) {
      if (needsStateLightColor(state)) {
         return 3;
      } else {
         int lightBlock = state.getLightBlock(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
         boolean emptyShape = YALightMath.isEmptyShape(state);
         if (lightBlock == 0 && emptyShape) {
            return 0;
         } else if (state.getBlock().hasDynamicShape()) {
            return 3;
         } else if (lightBlock == 0) {
            return 2;
         } else {
            return lightBlock >= 15 && state.isSolidRender(EmptyBlockGetter.INSTANCE, BlockPos.ZERO) ? 1 : 3;
         }
      }
   }

   private static boolean needsStateLightColor(BlockState state) {
      return state.hasDynamicLightEmission() || state.getLightEmission() != 0 || state.emissiveRendering(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
   }
}
