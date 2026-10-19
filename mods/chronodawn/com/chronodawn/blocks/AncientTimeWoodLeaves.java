package com.chronodawn.blocks;

import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class AncientTimeWoodLeaves extends LeavesBlock {
   public AncientTimeWoodLeaves(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(DISTANCE, 7)).setValue(PERSISTENT, false))
            .setValue(WATERLOGGED, false)
      );
   }

   public static Properties createProperties() {
      return Properties.of()
         .mapColor(MapColor.TERRACOTTA_GREEN)
         .strength(0.2F)
         .randomTicks()
         .sound(SoundType.GRASS)
         .noOcclusion()
         .isValidSpawn((state, level, pos, entityType) -> false)
         .isSuffocating((state, level, pos) -> false)
         .isViewBlocking((state, level, pos) -> false)
         .pushReaction(PushReaction.DESTROY);
   }
}
