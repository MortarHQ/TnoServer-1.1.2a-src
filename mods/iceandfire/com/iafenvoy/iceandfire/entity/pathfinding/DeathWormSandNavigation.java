package com.iafenvoy.iceandfire.entity.pathfinding;

import com.iafenvoy.iceandfire.entity.DeathWormEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DeathWormSandNavigation extends WaterBoundPathNavigation {
   public DeathWormSandNavigation(DeathWormEntity deathworm, Level worldIn) {
      super(deathworm, worldIn);
   }

   public boolean canFloat() {
      return this.nodeEvaluator.canFloat();
   }

   protected PathFinder createPathFinder(int i) {
      this.nodeEvaluator = new DeathWormNodeMaker();
      this.nodeEvaluator.setCanPassDoors(true);
      this.nodeEvaluator.setCanFloat(true);
      return new PathFinder(this.nodeEvaluator, i);
   }

   protected boolean canUpdatePath() {
      return true;
   }

   protected Vec3 getTempMobPos() {
      return new Vec3(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ());
   }

   protected boolean canMoveDirectly(Vec3 start, Vec3 end) {
      HitResult raytraceresult = this.level.clip(new DeathWormSandNavigation.CustomRayTraceContext(start, end, Block.COLLIDER, Fluid.NONE, this.mob));
      if (raytraceresult.getType() == Type.BLOCK) {
         Vec3 vec3i = raytraceresult.getLocation();
         return this.mob.level().getBlockState(BlockPos.containing(vec3i)).is(BlockTags.SAND);
      } else {
         return raytraceresult.getType() == Type.MISS;
      }
   }

   public boolean isStableDestination(BlockPos pos) {
      return this.level.getBlockState(pos).canOcclude();
   }

   public static class CustomRayTraceContext extends ClipContext {
      private final Block blockMode;
      private final CollisionContext context;

      public CustomRayTraceContext(Vec3 startVecIn, Vec3 endVecIn, Block blockModeIn, Fluid fluidModeIn, Entity entityIn) {
         super(startVecIn, endVecIn, blockModeIn, fluidModeIn, entityIn);
         this.blockMode = blockModeIn;
         this.context = CollisionContext.of(entityIn);
      }

      public VoxelShape getBlockShape(BlockState blockState, BlockGetter world, BlockPos pos) {
         return blockState.is(BlockTags.SAND) ? Shapes.empty() : this.blockMode.get(blockState, world, pos, this.context);
      }
   }
}
