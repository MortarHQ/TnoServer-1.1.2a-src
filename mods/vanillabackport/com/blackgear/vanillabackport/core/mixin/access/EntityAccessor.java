package com.blackgear.vanillabackport.core.mixin.access;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
   @Invoker
   static Vec3 callCollideWithShapes(Vec3 deltaMovement, AABB entityBB, List<VoxelShape> shapes) {
      throw new UnsupportedOperationException();
   }

   @Invoker
   BlockPos callGetBlockPosBelowThatAffectsMyMovement();

   @Accessor
   EntityDimensions getDimensions();

   @Invoker
   void callReapplyPosition();

   @Invoker
   void callSetRot(float var1, float var2);

   @Invoker
   Vec3 callCollide(Vec3 var1);

   @Accessor
   float getNextStep();

   @Accessor
   void setNextStep(float var1);

   @Invoker
   boolean callVibrationAndSoundEffectsFromBlock(BlockPos var1, BlockState var2, boolean var3, boolean var4, Vec3 var5);

   @Invoker
   boolean callIsStateClimbable(BlockState var1);

   @Invoker
   Vec3 callCalculateViewVector(float var1, float var2);
}
