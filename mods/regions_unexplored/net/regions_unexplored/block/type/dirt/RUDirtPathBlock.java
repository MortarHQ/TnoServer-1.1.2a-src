package net.regions_unexplored.block.type.dirt;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.block.RUBlockUtils;

public class RUDirtPathBlock extends Block {
   private static final VoxelShape SHAPE = RUBlockUtils.column(16.0, 0.0, 15.0);
   public static final MapCodec<RUDirtPathBlock> CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(ResourceKey.codec(Registries.BLOCK).fieldOf("base_block").forGetter(block -> block.baseBlock), propertiesCodec())
         .apply(i, RUDirtPathBlock::new)
   );
   private final ResourceKey<Block> baseBlock;

   public MapCodec<RUDirtPathBlock> codec() {
      return CODEC;
   }

   public RUDirtPathBlock(ResourceKey<Block> baseBlock, Properties properties) {
      super(properties);
      this.baseBlock = baseBlock;
   }

   protected boolean useShapeForLightOcclusion(BlockState state) {
      return true;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos())
         ? super.getStateForPlacement(context)
         : Block.pushEntitiesUp(this.defaultBlockState(), this.getBaseBlock(context.getLevel()), context.getLevel(), context.getClickedPos());
   }

   protected BlockState updateShape(
      BlockState state, Direction directionToNeighbour, BlockState neighbourState, LevelAccessor level, BlockPos pos, BlockPos neighbourPos
   ) {
      if (directionToNeighbour == Direction.UP && !state.canSurvive(level, pos)) {
         level.scheduleTick(pos, this, 1);
      }

      return super.updateShape(state, directionToNeighbour, neighbourState, level, pos, neighbourPos);
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      this.turnToDirt(null, state, level, pos);
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockState aboveState = level.getBlockState(pos.above());
      return !aboveState.isSolid() || aboveState.getBlock() instanceof FenceGateBlock;
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   protected boolean isPathfindable(BlockState state, PathComputationType type) {
      return false;
   }

   protected void turnToDirt(@Nullable Entity sourceEntity, BlockState state, Level level, BlockPos pos) {
      BlockState newState = pushEntitiesUp(state, this.getBaseBlock(level), level, pos);
      level.setBlockAndUpdate(pos, newState);
      level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(sourceEntity, newState));
   }

   protected BlockState getBaseBlock(Level level) {
      return ((Block)level.registryAccess().registryOrThrow(Registries.BLOCK).getOrThrow(this.baseBlock)).defaultBlockState();
   }
}
