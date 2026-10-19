package net.tslat.smartbrainlib.api.core.navigation.nodeevaluator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.tslat.smartbrainlib.api.core.navigation.MultiFluidNavigationElement;

public class MultiFluidWalkNodeEvaluator extends WalkNodeEvaluator implements MultiFluidNavigationElement {
   public Node getStart() {
      int groundY = this.mob.getBlockY();
      MutableBlockPos testPos = new MutableBlockPos(this.mob.getX(), groundY, this.mob.getZ());
      BlockState groundState = this.currentContext.getBlockState(testPos);
      if (!this.mob.canStandOnFluid(groundState.getFluidState())) {
         if (this.canFloat() && this.mob.isInFluidType((fluidType, height) -> this.canSwimInFluid(this.mob, fluidType, height))) {
            while (!groundState.getFluidState().isEmpty() && this.canSwimInFluid(this.mob, groundState.getFluidState().getFluidType())) {
               groundState = this.currentContext.getBlockState(testPos.setY(++groundY));
            }

            groundY--;
         } else if (this.mob.onGround()) {
            groundY = Mth.floor(this.mob.getY() + 0.5);
         } else {
            testPos.setY(Mth.floor(this.mob.getY() + 1.0));

            while (testPos.getY() > this.currentContext.level().getMinBuildHeight()) {
               groundY = testPos.getY();
               testPos.setY(testPos.getY() - 1);
               groundState = this.currentContext.getBlockState(testPos);
               if (!groundState.isAir() && !groundState.isPathfindable(PathComputationType.LAND)) {
                  break;
               }
            }
         }
      } else {
         while (this.mob.canStandOnFluid(groundState.getFluidState())) {
            groundState = this.currentContext.getBlockState(testPos.setY(++groundY));
         }

         groundY--;
      }

      if (!this.canStartAt(testPos.setY(groundY))) {
         AABB entityBounds = this.mob.getBoundingBox();
         if (this.canStartAt(testPos.set(entityBounds.minX, groundY, entityBounds.minZ))
            || this.canStartAt(testPos.set(entityBounds.minX, groundY, entityBounds.maxZ))
            || this.canStartAt(testPos.set(entityBounds.maxX, groundY, entityBounds.minZ))
            || this.canStartAt(testPos.set(entityBounds.maxX, groundY, entityBounds.maxZ))) {
            return this.getStartNode(testPos);
         }
      }

      return this.getStartNode(testPos.set(this.mob.getX(), groundY, this.mob.getZ()));
   }

   protected double getFloorLevel(BlockPos pos) {
      BlockGetter blockGetter = this.currentContext.level();
      FluidState fluidState = blockGetter.getFluidState(pos);
      return (this.canFloat() || this.isAmphibious()) && this.canSwimInFluid(this.mob, fluidState.getFluidType(), fluidState.getHeight(blockGetter, pos))
         ? pos.getY() + 0.5
         : getFloorLevel(blockGetter, pos);
   }
}
