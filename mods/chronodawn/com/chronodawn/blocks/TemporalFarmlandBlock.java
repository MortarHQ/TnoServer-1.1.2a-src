package com.chronodawn.blocks;

import com.chronodawn.compat.CompatBlockProperties;
import com.chronodawn.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;

public class TemporalFarmlandBlock extends FarmBlock {
   public static final MapCodec<FarmBlock> CODEC = simpleCodec(TemporalFarmlandBlock::new);

   public TemporalFarmlandBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<FarmBlock> codec() {
      return CODEC;
   }

   public static Properties createProperties() {
      return CompatBlockProperties.ofFullCopy(Blocks.FARMLAND);
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      return direction == Direction.UP && !state.canSurvive(level, pos)
         ? ((Block)ModBlocks.TEMPORAL_DIRT.get()).defaultBlockState()
         : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!state.canSurvive(level, pos)) {
         turnToTemporalDirt(null, state, level, pos);
      }
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      if (!level.isClientSide) {
         turnToTemporalDirt(entity, state, level, pos);
      }

      entity.causeFallDamage(fallDistance, 1.0F, level.damageSources().fall());
   }

   private static void turnToTemporalDirt(Entity entity, BlockState state, Level level, BlockPos pos) {
      BlockState blockstate = Block.pushEntitiesUp(state, ((Block)ModBlocks.TEMPORAL_DIRT.get()).defaultBlockState(), level, pos);
      level.setBlockAndUpdate(pos, blockstate);
      level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(entity, state));
   }
}
