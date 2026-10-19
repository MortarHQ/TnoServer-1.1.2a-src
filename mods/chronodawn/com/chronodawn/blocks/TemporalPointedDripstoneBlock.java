package com.chronodawn.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TemporalPointedDripstoneBlock extends Block {
   public static final EnumProperty<TemporalPointedDripstoneBlock.DripstoneDirection> DIRECTION = EnumProperty.create(
      "direction", TemporalPointedDripstoneBlock.DripstoneDirection.class
   );
   public static final EnumProperty<TemporalPointedDripstoneBlock.Thickness> THICKNESS = EnumProperty.create(
      "thickness", TemporalPointedDripstoneBlock.Thickness.class
   );
   private static final VoxelShape TIP_UP = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
   private static final VoxelShape TIP_DOWN = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
   private static final VoxelShape FRUSTUM = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);

   public TemporalPointedDripstoneBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(DIRECTION, TemporalPointedDripstoneBlock.DripstoneDirection.DOWN))
            .setValue(THICKNESS, TemporalPointedDripstoneBlock.Thickness.TIP)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{DIRECTION, THICKNESS});
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return getShapeForState(state);
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return getShapeForState(state);
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      if (state.getValue(DIRECTION) == TemporalPointedDripstoneBlock.DripstoneDirection.UP
         && state.getValue(THICKNESS) == TemporalPointedDripstoneBlock.Thickness.TIP) {
         entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().stalagmite());
      } else {
         super.fallOn(level, state, pos, entity, fallDistance);
      }
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
      super.onRemove(state, level, pos, newState, moved);
      if (!level.isClientSide && !state.is(newState.getBlock())) {
         cascadeBreak(level, pos, (TemporalPointedDripstoneBlock.DripstoneDirection)state.getValue(DIRECTION));
      }
   }

   private static void cascadeBreak(Level level, BlockPos pos, TemporalPointedDripstoneBlock.DripstoneDirection direction) {
      BlockPos next = direction == TemporalPointedDripstoneBlock.DripstoneDirection.UP ? pos.above() : pos.below();
      BlockState nextState = level.getBlockState(next);
      if (nextState.getBlock() instanceof TemporalPointedDripstoneBlock && nextState.getValue(DIRECTION) == direction) {
         Block.dropResources(nextState, level, next);
         level.removeBlock(next, false);
         cascadeBreak(level, next, direction);
      }
   }

   private static VoxelShape getShapeForState(BlockState state) {
      if (state.getValue(THICKNESS) == TemporalPointedDripstoneBlock.Thickness.TIP) {
         return state.getValue(DIRECTION) == TemporalPointedDripstoneBlock.DripstoneDirection.UP ? TIP_UP : TIP_DOWN;
      } else {
         return FRUSTUM;
      }
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      TemporalPointedDripstoneBlock.DripstoneDirection direction = context.getClickedFace() == Direction.DOWN
         ? TemporalPointedDripstoneBlock.DripstoneDirection.DOWN
         : TemporalPointedDripstoneBlock.DripstoneDirection.UP;
      TemporalPointedDripstoneBlock.Thickness thickness = calculateThickness(context.getLevel(), context.getClickedPos(), direction);
      return (BlockState)((BlockState)this.defaultBlockState().setValue(DIRECTION, direction)).setValue(THICKNESS, thickness);
   }

   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
      super.onPlace(state, level, pos, oldState, movedByPiston);
      if (!level.isClientSide) {
         TemporalPointedDripstoneBlock.DripstoneDirection direction = (TemporalPointedDripstoneBlock.DripstoneDirection)state.getValue(DIRECTION);
         BlockPos rootPos = direction == TemporalPointedDripstoneBlock.DripstoneDirection.UP ? pos.below() : pos.above();
         BlockState rootState = level.getBlockState(rootPos);
         if (isSameDripstoneWithDirection(rootState, direction)) {
            TemporalPointedDripstoneBlock.Thickness rootThickness = calculateThickness(level, rootPos, direction);
            if (rootState.getValue(THICKNESS) != rootThickness) {
               level.setBlock(rootPos, (BlockState)rootState.setValue(THICKNESS, rootThickness), 3);
            }
         }
      }
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
      TemporalPointedDripstoneBlock.DripstoneDirection direction = (TemporalPointedDripstoneBlock.DripstoneDirection)state.getValue(DIRECTION);
      BlockPos growthPos = direction == TemporalPointedDripstoneBlock.DripstoneDirection.UP ? pos.above() : pos.below();
      if (!facingPos.equals(growthPos)) {
         return state;
      }

      TemporalPointedDripstoneBlock.Thickness newThickness = calculateThickness(level, pos, direction);
      return state.getValue(THICKNESS) == newThickness ? state : (BlockState)state.setValue(THICKNESS, newThickness);
   }

   private static TemporalPointedDripstoneBlock.Thickness calculateThickness(
      LevelAccessor level, BlockPos pos, TemporalPointedDripstoneBlock.DripstoneDirection direction
   ) {
      BlockPos growthPos = direction == TemporalPointedDripstoneBlock.DripstoneDirection.UP ? pos.above() : pos.below();
      BlockState growthState = level.getBlockState(growthPos);
      return isSameDripstoneWithDirection(growthState, direction)
         ? TemporalPointedDripstoneBlock.Thickness.FRUSTUM
         : TemporalPointedDripstoneBlock.Thickness.TIP;
   }

   private static boolean isSameDripstoneWithDirection(BlockState state, TemporalPointedDripstoneBlock.DripstoneDirection direction) {
      return state.getBlock() instanceof TemporalPointedDripstoneBlock && state.getValue(DIRECTION) == direction;
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.STONE).strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.POINTED_DRIPSTONE).noOcclusion();
   }

   public enum DripstoneDirection implements StringRepresentable {
      UP("up"),
      DOWN("down");

      private final String serializedName;

      DripstoneDirection(String serializedName) {
         this.serializedName = serializedName;
      }

      public String getSerializedName() {
         return this.serializedName;
      }
   }

   public enum Thickness implements StringRepresentable {
      TIP("tip"),
      FRUSTUM("frustum"),
      MIDDLE("middle"),
      BASE("base");

      private final String serializedName;

      Thickness(String serializedName) {
         this.serializedName = serializedName;
      }

      public String getSerializedName() {
         return this.serializedName;
      }
   }
}
