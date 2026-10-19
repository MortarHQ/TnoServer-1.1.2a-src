package net.tslat.smartbrainlib.api.core.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.tslat.smartbrainlib.api.core.navigation.nodeevaluator.MultiFluidWalkNodeEvaluator;

public class MultiFluidSmoothGroundNavigation extends SmoothGroundNavigation implements MultiFluidNavigationElement {
   public MultiFluidSmoothGroundNavigation(Mob mob, Level level) {
      super(mob, level);
   }

   @Override
   protected PathFinder createPathFinder(int maxVisitedNodes) {
      this.nodeEvaluator = new MultiFluidWalkNodeEvaluator();
      this.nodeEvaluator.setCanPassDoors(true);
      return this.createSmoothPathFinder(this.nodeEvaluator, maxVisitedNodes);
   }

   protected boolean canUpdatePath() {
      return this.mob.onGround()
         || this.mob.isInFluidType((fluidType, height) -> this.canSwimInFluid(this.mob, fluidType, height), true)
         || this.mob.isPassenger();
   }

   @Override
   public int getSurfaceY() {
      if (this.mob.isInFluidType((fluidType, height) -> this.canSwimInFluid(this.mob, fluidType, height), true) && this.canFloat()) {
         int basePos = this.mob.getBlockY();
         MutableBlockPos pos = BlockPos.containing(this.mob.getX(), basePos, this.mob.getZ()).mutable();
         BlockState state = this.level.getBlockState(pos);
         FluidState fluidState = state.getFluidState();

         while (this.canSwimInFluid(this.mob, fluidState.getFluidType(), fluidState.getHeight(this.level, pos))) {
            state = this.level.getBlockState(pos.move(Direction.UP));
            fluidState = state.getFluidState();
            if (pos.getY() - basePos > 16) {
               return basePos;
            }
         }

         return pos.getY();
      } else {
         return Mth.floor(this.mob.getY() + 0.5);
      }
   }
}
