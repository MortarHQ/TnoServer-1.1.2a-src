package com.chronodawn.blocks;

import com.chronodawn.compat.CompatBlockProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TemporalGravel extends FallingBlock {
   public static final MapCodec<TemporalGravel> CODEC = simpleCodec(TemporalGravel::new);

   public TemporalGravel(Properties properties) {
      super(properties);
   }

   public MapCodec<? extends FallingBlock> codec() {
      return CODEC;
   }

   public static Properties createProperties() {
      return CompatBlockProperties.ofFullCopy(Blocks.GRAVEL);
   }
}
