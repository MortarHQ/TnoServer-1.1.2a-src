package com.benji.netherman.common.block;

import com.benji.netherman.common.block.entity.PointedBlackstoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class PointedBlackstoneBlock extends PointedDripstoneBlock implements EntityBlock {
   public PointedBlackstoneBlock(Properties properties) {
      super(properties);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PointedBlackstoneBlockEntity(pos, state);
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      LevelAccessor level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      Direction dir = context.getNearestLookingVerticalDirection().getOpposite();
      Direction placementDir = context.getClickedFace();
      boolean isTipMerge = false;
      if (placementDir.getAxis() == Axis.Y) {
         BlockState stateTarget = level.getBlockState(pos.relative(placementDir.getOpposite()));
         if (this.isPointedBlackstoneWithDirection(stateTarget, placementDir)) {
            dir = placementDir;
            isTipMerge = true;
         }
      }

      BlockState state = (BlockState)((BlockState)this.defaultBlockState().setValue(TIP_DIRECTION, dir)).setValue(THICKNESS, DripstoneThickness.TIP);
      DripstoneThickness thickness = this.calculateThickness(level, pos, dir, isTipMerge);
      return (BlockState)state.setValue(THICKNESS, thickness);
   }

   public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      if (dir != Direction.UP && dir != Direction.DOWN) {
         return state;
      } else {
         Direction tipDir = (Direction)state.getValue(TIP_DIRECTION);
         if (tipDir == Direction.DOWN && level.getBlockTicks().hasScheduledTick(pos, this)) {
            return state;
         } else if (dir == tipDir.getOpposite() && !this.canSurvive(state, level, pos)) {
            this.destroyChain(level, pos, tipDir);
            return Blocks.AIR.defaultBlockState();
         } else {
            boolean isTipMerge = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
            DripstoneThickness newThickness = this.calculateThickness(level, pos, tipDir, isTipMerge);
            return (BlockState)state.setValue(THICKNESS, newThickness);
         }
      }
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      Direction tipDirection = (Direction)state.getValue(TIP_DIRECTION);
      BlockPos supportPos = pos.relative(tipDirection.getOpposite());
      BlockState supportState = level.getBlockState(supportPos);
      return supportState.isFaceSturdy(level, supportPos, tipDirection) || this.isPointedBlackstoneWithDirection(supportState, tipDirection);
   }

   private boolean isPointedBlackstoneWithDirection(BlockState state, Direction dir) {
      return state.is(this) && state.hasProperty(TIP_DIRECTION) && state.getValue(TIP_DIRECTION) == dir;
   }

   private void destroyChain(LevelAccessor level, BlockPos pos, Direction dir) {
      MutableBlockPos mutable = pos.mutable();

      while (true) {
         BlockState state = level.getBlockState(mutable);
         if (!this.isPointedBlackstoneWithDirection(state, dir)) {
            mutable.move(dir.getOpposite());

            while (true) {
               state = level.getBlockState(mutable);
               if (!this.isPointedBlackstoneWithDirection(state, dir)) {
                  return;
               }

               level.destroyBlock(mutable, true);
               mutable.move(dir.getOpposite());
            }
         }

         mutable.move(dir);
      }
   }

   private DripstoneThickness calculateThickness(LevelReader level, BlockPos pos, Direction dir, boolean isPlacementMerge) {
      Direction opposite = dir.getOpposite();
      BlockState stateFront = level.getBlockState(pos.relative(dir));
      BlockState stateBehind = level.getBlockState(pos.relative(opposite));
      boolean hasFront = this.isPointedBlackstoneWithDirection(stateFront, dir);
      boolean hasBehind = this.isPointedBlackstoneWithDirection(stateBehind, dir);
      if (hasFront) {
         if (!hasBehind) {
            return DripstoneThickness.BASE;
         }

         BlockState stateFrontOfFront = level.getBlockState(pos.relative(dir, 2));
         boolean frontHasFront = this.isPointedBlackstoneWithDirection(stateFrontOfFront, dir);
         return frontHasFront ? DripstoneThickness.MIDDLE : DripstoneThickness.FRUSTUM;
      } else {
         boolean isMerge = isPlacementMerge || this.isPointedBlackstoneWithDirection(stateFront, opposite);
         return isMerge ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP;
      }
   }
}
