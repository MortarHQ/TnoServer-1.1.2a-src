package com.finderfeed.fdlib.systems.multiblock;

import com.finderfeed.fdlib.data_structures.Pair;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class MultiblockBlock extends Block {
   public static final BooleanProperty MAIN_BLOCK = BooleanProperty.create("main_block");
   protected boolean cacheShape = true;
   private MultiblockShape cachedShape = null;
   private Supplier<MultiblockShape> firstTimeShapeGetter;

   public MultiblockBlock(Properties properties, Supplier<MultiblockShape> multiblockShape) {
      super(properties);
      this.firstTimeShapeGetter = multiblockShape;
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(MAIN_BLOCK, true));
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      BlockPos mainblock = this.findMainBlock(level, pos);
      if (mainblock != null) {
         BlockState mainBlock = level.getBlockState(mainblock);
         Direction direction = (Direction)mainBlock.getValue(BlockStateProperties.HORIZONTAL_FACING);

         for (Pair<BlockState, BlockPos> pair : this.getMultiblockShape()) {
            BlockPos offset = pair.second;
            offset = this.rotateBaseOffset(offset, direction);
            BlockPos setpos = mainblock.offset(offset);
            level.setBlock(setpos, Blocks.AIR.defaultBlockState(), 2);
         }
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      Level level = ctx.getLevel();
      BlockPos setPos = ctx.getClickedPos();
      Direction horizontalDirection = ctx.getHorizontalDirection();
      BlockState mainstate = (BlockState)this.getMultiblockShape().getMainState().setValue(BlockStateProperties.HORIZONTAL_FACING, horizontalDirection);

      for (Pair<BlockState, BlockPos> pair : this.getMultiblockShape()) {
         BlockPos offset = this.rotateBaseOffset(pair.second, horizontalDirection);
         BlockPos pos = setPos.offset(offset);
         BlockState stateAtPos = level.getBlockState(pos);
         if (!stateAtPos.canBeReplaced()) {
            return null;
         }
      }

      return mainstate;
   }

   protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean idk) {
      super.onPlace(state, level, pos, state2, idk);
      if (this.isMainBlock(state)) {
         Direction direction = (Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING);

         for (Pair<BlockState, BlockPos> pair : this.getMultiblockShape()) {
            BlockPos offset = pair.second;
            if (!offset.equals(BlockPos.ZERO)) {
               offset = this.rotateBaseOffset(offset, direction);
               BlockState stateToPlace = (BlockState)pair.first.setValue(BlockStateProperties.HORIZONTAL_FACING, direction);
               BlockPos setPos = pos.offset(offset);
               level.setBlock(setPos, stateToPlace, 2);
            }
         }
      }
   }

   public BlockPos findMainBlock(Level level, BlockPos currentPos) {
      BlockState current = level.getBlockState(currentPos);
      if (current.is(this) && this.isMainBlock(current)) {
         return currentPos;
      }

      int maxDist = this.getMultiblockShape().getMaxDistanceFromMainBlock();

      for (int x = -maxDist; x <= maxDist; x++) {
         for (int y = -maxDist; y <= maxDist; y++) {
            for (int z = -maxDist; z <= maxDist; z++) {
               BlockPos pos = currentPos.offset(x, y, z);
               BlockState state = level.getBlockState(pos);
               if (state.is(this) && this.isMainBlock(state) && this.getMultiblockShape().isAPartOfShape(current, pos, currentPos)) {
                  return pos;
               }
            }
         }
      }

      return null;
   }

   public BlockPos rotateBaseOffset(BlockPos pos, Direction direction) {
      int yRot = -((int)direction.toYRot());
      Vec3 v = new Vec3(pos.getX(), 0.0, pos.getZ()).yRot((float)Math.toRadians(yRot));
      return new BlockPos((int)Math.floor(v.x + 0.5), pos.getY(), (int)Math.floor(v.z + 0.5));
   }

   public BlockPos rotateOffsetBack(BlockPos pos, Direction direction) {
      int yRot = (int)direction.toYRot();
      Vec3 v = new Vec3(pos.getX(), 0.0, pos.getZ()).yRot((float)Math.toRadians(yRot));
      return new BlockPos((int)Math.floor(v.x + 0.5), pos.getY(), (int)Math.floor(v.z + 0.5));
   }

   public boolean isMainBlock(BlockState blockState) {
      return (Boolean)blockState.getValue(MAIN_BLOCK);
   }

   public MultiblockShape getMultiblockShape() {
      if (!this.cacheShape) {
         return this.firstTimeShapeGetter.get();
      }

      if (this.cachedShape == null) {
         this.cachedShape = this.firstTimeShapeGetter.get();
      }

      return this.cachedShape;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{MAIN_BLOCK});
      builder.add(new Property[]{BlockStateProperties.HORIZONTAL_FACING});
   }
}
