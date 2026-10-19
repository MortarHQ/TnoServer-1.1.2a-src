package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlockEntities;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.capability.ChunkBlockCache;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MonolithBlock extends BaseEntityBlock {
   public static final MapCodec<MonolithBlock> CODEC = simpleCodec(MonolithBlock::new);
   private static final VoxelShape xAxisShape = box(3.5, 0.0, 1.5, 12.5, 16.0, 14.5);
   private static final VoxelShape zAxisShape = box(1.5, 0.0, 3.5, 14.5, 16.0, 12.5);

   public MonolithBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH))
            .setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
      );
   }

   @NotNull
   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      tooltipComponents.add(Component.translatable("item.bosses_of_mass_destruction.monolith_block.tooltip_0").withStyle(ChatFormatting.DARK_GRAY));
      tooltipComponents.add(Component.translatable("item.bosses_of_mass_destruction.monolith_block.tooltip_1").withStyle(ChatFormatting.DARK_GRAY));
   }

   @Nullable
   public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new MonolithBlockEntity(pos, state);
   }

   @NotNull
   public RenderShape getRenderShape(@NotNull BlockState state) {
      return RenderShape.MODEL;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
      return createTickerHelper(blockEntityType, (BlockEntityType)BMDBlockEntities.MONOLITH_BLOCK_ENTITY.get(), ChunkCacheBlockEntity::tick);
   }

   @NotNull
   public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return ((Direction)state.getValue(HorizontalDirectionalBlock.FACING)).getAxis() == Axis.X ? xAxisShape : zAxisShape;
   }

   @NotNull
   public BlockState updateShape(
      BlockState state, Direction direction, @NotNull BlockState newState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos posFrom
   ) {
      DoubleBlockHalf doubleBlockHalf = (DoubleBlockHalf)state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
      BlockState airState = Blocks.AIR.defaultBlockState();
      if (direction.getAxis() != Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP)) {
         return super.updateShape(state, direction, newState, level, pos, posFrom);
      } else {
         return newState.is(this) && newState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) != doubleBlockHalf
            ? (BlockState)state.setValue(HorizontalDirectionalBlock.FACING, (Direction)newState.getValue(HorizontalDirectionalBlock.FACING))
            : airState;
      }
   }

   @NotNull
   public BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
      if (!level.isClientSide && player.isCreative()) {
         VanillaCopiesServer.onBreakInCreative(level, pos, state, player);
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   @Nullable
   public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
      BlockPos blockPos = ctx.getClickedPos();
      return blockPos.getY() < 255 && ctx.getLevel().getBlockState(blockPos.above()).canBeReplaced(ctx)
         ? (BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(HorizontalDirectionalBlock.FACING, ctx.getHorizontalDirection()))
            .setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
         : null;
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
      level.setBlock(pos.above(), (BlockState)state.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 3);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockPos = pos.below();
      BlockState blockState = level.getBlockState(blockPos);
      return state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER
         ? blockState.isFaceSturdy(level, blockPos, Direction.UP)
         : blockState.is(this);
   }

   @NotNull
   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate((Direction)state.getValue(HorizontalDirectionalBlock.FACING)));
   }

   @NotNull
   public BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirror) {
      return mirror == Mirror.NONE
         ? state
         : (BlockState)state.rotate(mirror.getRotation((Direction)state.getValue(HorizontalDirectionalBlock.FACING))).cycle(DoorBlock.HINGE);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{BlockStateProperties.DOUBLE_BLOCK_HALF, HorizontalDirectionalBlock.FACING});
   }

   public static float getExplosionPower(ServerLevel level, BlockPos pos, float power) {
      ChunkPos chunkPos = new ChunkPos(pos);
      Optional<ChunkBlockCache> blockCache = BMDCapabilities.getChunkBlockCache(level);
      if (blockCache.isPresent()) {
         for (int x = chunkPos.x - 4; x <= chunkPos.x + 4; x++) {
            for (int z = chunkPos.z - 4; z <= chunkPos.z + 4; z++) {
               for (BlockPos blockPos : blockCache.get().getBlocksFromChunk(new ChunkPos(x, z), (Block)BMDBlocks.MONOLITH_BLOCK.get())) {
                  if (Math.abs(blockPos.getX() - pos.getX()) < 64 && Math.abs(blockPos.getY() - pos.getY()) < 64 && Math.abs(blockPos.getZ() - pos.getZ()) < 64
                     )
                   {
                     return power * 1.3F;
                  }
               }
            }
         }
      }

      return power;
   }
}
