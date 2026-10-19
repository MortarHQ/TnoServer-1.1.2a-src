package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class TimeWoodLeaves extends LeavesBlock {
   private static final float FRUIT_SPAWN_CHANCE = 0.01F;

   public TimeWoodLeaves(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(DISTANCE, 7)).setValue(PERSISTENT, false))
            .setValue(WATERLOGGED, false)
      );
   }

   public boolean isRandomlyTicking(BlockState state) {
      return true;
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      super.randomTick(state, level, pos, random);
      if ((Integer)state.getValue(DISTANCE) < 7 || (Boolean)state.getValue(PERSISTENT)) {
         BlockPos belowPos = pos.below();
         if (level.isEmptyBlock(belowPos) && random.nextFloat() < 0.01F) {
            level.setBlock(belowPos, (BlockState)((Block)ModBlocks.FRUIT_OF_TIME_BLOCK.get()).defaultBlockState().setValue(FruitOfTimeBlock.AGE, 0), 2);
         }
      }
   }

   public static Properties createProperties() {
      return Properties.of()
         .mapColor(MapColor.PLANT)
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
