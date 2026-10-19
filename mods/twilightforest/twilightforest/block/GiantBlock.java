package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;

public class GiantBlock extends Block {
   private boolean isSelfDestructing;

   public GiantBlock(Properties properties) {
      super(properties);
   }

   public static Iterable<BlockPos> getVolume(BlockPos pos) {
      return BlockPos.betweenClosed(pos.getX() & -4, pos.getY() & -4, pos.getZ() & -4, pos.getX() | 3, pos.getY() | 3, pos.getZ() | 3);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      for (BlockPos dPos : getVolume(context.getClickedPos())) {
         if (!context.getLevel().getBlockState(dPos).canBeReplaced(context)) {
            return null;
         }
      }

      return super.getStateForPlacement(context);
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      if (!level.isClientSide()) {
         for (BlockPos dPos : getVolume(pos)) {
            level.setBlockAndUpdate(dPos, this.defaultBlockState());
         }
      }
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      super.onRemove(state, level, pos, newState, isMoving);
      if (!this.isSelfDestructing && !this.isVolumeFilled(level, pos)) {
         this.setGiantBlockToAir(level, pos);
      }
   }

   private void setGiantBlockToAir(Level level, BlockPos pos) {
      this.isSelfDestructing = true;

      for (BlockPos iterPos : getVolume(pos)) {
         if (!pos.equals(iterPos) && level.getBlockState(iterPos).getBlock() == this) {
            level.destroyBlock(iterPos, false);
         }
      }

      this.isSelfDestructing = false;
   }

   private boolean isVolumeFilled(Level level, BlockPos pos) {
      for (BlockPos dPos : getVolume(pos)) {
         if (level.getBlockState(dPos).getBlock() != this) {
            return false;
         }
      }

      return true;
   }
}
