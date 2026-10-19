package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.RovingCube;

public class CubeMoveToRedstoneSymbolsGoal extends Goal {
   private final RovingCube myCube;
   private final double speed;
   private BlockPos targetPos;

   public CubeMoveToRedstoneSymbolsGoal(RovingCube cube, double speed) {
      this.myCube = cube;
      this.speed = speed;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (this.myCube.getRandom().nextInt(20) != 0) {
         return false;
      }

      BlockPos pos = this.searchForRedstoneSymbol(this.myCube);
      if (pos == null) {
         return false;
      }

      this.targetPos = pos;
      return true;
   }

   public boolean canContinueToUse() {
      return !this.myCube.getNavigation().isDone();
   }

   public void start() {
      this.myCube.getNavigation().moveTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), this.speed);
   }

   @Nullable
   private BlockPos searchForRedstoneSymbol(RovingCube cube) {
      BlockPos curPos = new BlockPos(cube.blockPosition());

      for (int x = -16; x < 16; x++) {
         for (int z = -16; z < 16; z++) {
            for (int y = -5; y < 5; y++) {
               if (this.isRedstoneSymbol(curPos.offset(x, y, z))) {
                  this.myCube.hasFoundSymbol = true;
                  this.myCube.symbolX = curPos.getX() + x;
                  this.myCube.symbolY = curPos.getY() + y;
                  this.myCube.symbolZ = curPos.getZ() + z;
                  return curPos.offset(x, y, z);
               }
            }
         }
      }

      return null;
   }

   private boolean isRedstoneSymbol(BlockPos pos) {
      if (this.myCube.level().hasChunkAt(pos) && this.myCube.level().isEmptyBlock(pos)) {
         for (Direction e : Direction.values()) {
            if (this.myCube.level().getBlockState(pos.relative(e)).getBlock() != Blocks.REDSTONE_WIRE) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
