package net.regions_unexplored.block.type.dirt;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class RUFarmlandBlock extends FarmBlock {
   public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
   private final ResourceKey<Block> baseBlock;

   public RUFarmlandBlock(ResourceKey<Block> baseBlock, Properties properties) {
      super(properties);
      this.baseBlock = baseBlock;
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(MOISTURE, 0));
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos())
         ? super.getStateForPlacement(context)
         : this.getBaseBlock(context.getLevel());
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!state.canSurvive(level, pos)) {
         turnToDirt(this.getBaseBlock(level), null, state, level, pos);
      }
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      int moisture = (Integer)state.getValue(MOISTURE);
      if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
         if (moisture > 0) {
            level.setBlock(pos, (BlockState)state.setValue(MOISTURE, moisture - 1), 2);
         } else if (!shouldMaintainFarmland(level, pos)) {
            turnToDirt(this.getBaseBlock(level), null, state, level, pos);
         }
      } else if (moisture < 7) {
         level.setBlock(pos, (BlockState)state.setValue(MOISTURE, 7), 2);
      }
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      if (level instanceof ServerLevel serverLevel
         && level.getRandom().nextFloat() < fallDistance - 0.5
         && entity instanceof LivingEntity
         && (entity instanceof Player || serverLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING))
         && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512F) {
         turnToDirt(entity, state, level, pos);
      }

      super.fallOn(level, state, pos, entity, fallDistance);
   }

   public static void turnToDirt(BlockState baseBlock, @Nullable Entity sourceEntity, BlockState state, Level level, BlockPos pos) {
      BlockState newState = pushEntitiesUp(state, baseBlock, level, pos);
      level.setBlockAndUpdate(pos, newState);
      level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(sourceEntity, newState));
   }

   private static boolean shouldMaintainFarmland(BlockGetter level, BlockPos pos) {
      return level.getBlockState(pos.above()).is(BlockTags.MAINTAINS_FARMLAND);
   }

   private static boolean isNearWater(LevelReader level, BlockPos pos) {
      for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
         if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
            return true;
         }
      }

      return false;
   }

   protected BlockState getBaseBlock(Level level) {
      return ((Block)level.registryAccess().lookupOrThrow(Registries.BLOCK).getOrThrow(this.baseBlock).value()).defaultBlockState();
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{MOISTURE});
   }

   protected boolean isPathfindable(BlockState state, PathComputationType type) {
      return false;
   }
}
