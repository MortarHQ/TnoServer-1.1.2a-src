package com.chronodawn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TimelessMushroomBlock extends BushBlock implements BonemealableBlock {
   public static final MapCodec<TimelessMushroomBlock> CODEC = simpleCodec(TimelessMushroomBlock::new);
   private static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);

   public TimelessMushroomBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(BlockTags.DIRT) || state.is(Blocks.MYCELIUM) || state.is(Blocks.PODZOL) || state.is(Blocks.MOSS_BLOCK);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos belowPos = pos.below();
      BlockState belowState = level.getBlockState(belowPos);
      if (!this.mayPlaceOn(belowState, level, belowPos)) {
         return false;
      }

      if (level.getBlockState(pos).is(BlockTags.LOGS)) {
         return false;
      }

      if (belowState.is(BlockTags.LOGS)) {
         return false;
      }

      BlockPos abovePos = pos.above();
      return level.getBlockState(abovePos).is(BlockTags.LOGS) ? false : level.getRawBrightness(pos, 0) <= 12;
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (random.nextInt(25) == 0) {
         int nearbyMushrooms = 0;
         MutableBlockPos mutablePos = new MutableBlockPos();

         for (int x = -4; x <= 4; x++) {
            for (int y = -1; y <= 1; y++) {
               for (int z = -4; z <= 4; z++) {
                  mutablePos.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                  if (level.getBlockState(mutablePos).is(this)) {
                     if (++nearbyMushrooms > 4) {
                        return;
                     }
                  }
               }
            }
         }

         BlockPos targetPos = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
         if (level.getBlockState(targetPos).isAir() && this.canSurvive(this.defaultBlockState(), level, targetPos)) {
            level.setBlock(targetPos, this.defaultBlockState(), 2);
         }
      }
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return false;
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return false;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
   }
}
