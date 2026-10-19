package twilightforest.item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.RopeBlock;

public class RopeItem extends BlockItem {
   public static final int EXTEND_RANGE = 7;

   public RopeItem(Block block, Properties properties) {
      super(block, properties);
   }

   @Nullable
   public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
      BlockPos blockpos = context.getClickedPos();
      Level level = context.getLevel();
      BlockState blockstate = level.getBlockState(blockpos);
      if (!blockstate.is(this.getBlock())) {
         return context;
      }

      Direction direction = this.getForward(context, blockstate, blockpos, level);
      if (direction == null) {
         return null;
      }

      if (direction == Direction.UP) {
         direction = Direction.DOWN;
      }

      int i = 0;
      MutableBlockPos mutableBlockPos = blockpos.mutable();

      while (i < 7) {
         if (!level.isClientSide && !level.isInWorldBounds(mutableBlockPos)) {
            Player player = context.getPlayer();
            int j = level.getMaxBuildHeight();
            if (player instanceof ServerPlayer serverPlayer && mutableBlockPos.getY() >= j) {
               serverPlayer.sendSystemMessage(Component.translatable("build.tooHigh", new Object[]{j - 1}).withStyle(ChatFormatting.RED), true);
            }
            break;
         }

         blockstate = level.getBlockState(mutableBlockPos);
         if (!blockstate.is(this.getBlock())) {
            if (blockstate.canBeReplaced(context)) {
               return BlockPlaceContext.at(context, mutableBlockPos, direction);
            }
            break;
         }

         if (!this.stateHasValue(blockstate, direction)) {
            return BlockPlaceContext.at(context, mutableBlockPos, direction);
         }

         mutableBlockPos.move(direction);
         if (direction.getAxis().isHorizontal()) {
            i++;
         }
      }

      return null;
   }

   protected boolean stateHasValue(BlockState state, Direction direction) {
      return switch (direction.getAxis()) {
         case X -> state.getValue(RopeBlock.X);
         case Y -> state.getValue(RopeBlock.Y);
         default -> state.getValue(RopeBlock.Z);
      };
   }

   @Nullable
   protected BlockState getPlacementState(BlockPlaceContext context) {
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      BlockState state = level.getBlockState(pos);
      if (state.is(this.getBlock())) {
         Direction direction = context.getClickedFace();
         if (direction.getAxis() == Axis.X && !(Boolean)state.getValue(RopeBlock.X)) {
            return (BlockState)state.setValue(RopeBlock.X, true);
         }

         if (direction.getAxis() == Axis.Y && !(Boolean)state.getValue(RopeBlock.Y)) {
            return (BlockState)state.setValue(RopeBlock.Y, true);
         }

         if (direction.getAxis() == Axis.Z && !(Boolean)state.getValue(RopeBlock.Z)) {
            return (BlockState)state.setValue(RopeBlock.Z, true);
         }
      }

      return super.getPlacementState(context);
   }

   @Nullable
   protected Direction getForward(BlockPlaceContext context, BlockState state, BlockPos pos, Level level) {
      Direction face = context.getClickedFace();
      BlockPos relativePos = pos.relative(face);
      if (RopeBlock.canConnectTo(level.getBlockState(relativePos), face, level, relativePos)) {
         return face;
      }

      face = face.getOpposite();
      relativePos = pos.relative(face);
      if (RopeBlock.canConnectTo(level.getBlockState(relativePos), face, level, relativePos)) {
         return face;
      }

      List<Direction> directions = Arrays.asList(context.getNearestLookingDirections());
      if (context.isSecondaryUseActive()) {
         Collections.reverse(directions);
      }

      for (int i = 0; i < 3; i++) {
         Direction dir = directions.get(i);
         if ((Boolean)state.getValue(RopeBlock.Y)) {
            return Direction.DOWN;
         }

         if (dir.getAxis() == Axis.X && (Boolean)state.getValue(RopeBlock.X) || dir.getAxis() == Axis.Z && (Boolean)state.getValue(RopeBlock.Z)) {
            for (int j = 0; j < 2; j++) {
               if (j == 1) {
                  dir = dir.getOpposite();
               }

               MutableBlockPos mutableBlockPos = pos.mutable();

               for (int k = 1; k < 7; k++) {
                  mutableBlockPos.move(dir);
                  BlockState relativeState = level.getBlockState(mutableBlockPos);
                  if (relativeState.canBeReplaced() || relativeState.is(this.getBlock())) {
                     return dir;
                  }

                  if (!relativeState.is(this.getBlock())) {
                     break;
                  }
               }
            }
         }
      }

      return null;
   }
}
