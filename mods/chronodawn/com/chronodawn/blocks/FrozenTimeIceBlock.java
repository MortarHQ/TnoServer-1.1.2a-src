package com.chronodawn.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class FrozenTimeIceBlock extends IceBlock {
   public FrozenTimeIceBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of()
         .mapColor(MapColor.ICE)
         .friction(0.98F)
         .strength(0.5F, 0.5F)
         .sound(SoundType.GLASS)
         .noOcclusion()
         .isValidSpawn((state, level, pos, entityType) -> false)
         .isRedstoneConductor((state, level, pos) -> false);
   }

   protected void melt(BlockState state, Level level, BlockPos pos) {
   }
}
