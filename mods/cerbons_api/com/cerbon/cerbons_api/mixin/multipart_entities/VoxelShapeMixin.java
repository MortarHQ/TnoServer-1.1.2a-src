package com.cerbon.cerbons_api.mixin.multipart_entities;

import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VoxelShape.class)
public class VoxelShapeMixin {
   @Inject(method = "collide(Lnet/minecraft/core/Direction$Axis;Lnet/minecraft/world/phys/AABB;D)D", at = @At("HEAD"), cancellable = true)
   private void hook(Axis axis, AABB box, double maxDist, CallbackInfoReturnable<Double> cir) {
      if (box instanceof CompoundOrientedBox compoundOrientedBox) {
         cir.setReturnValue(compoundOrientedBox.calculateMaxDistance(axis, (VoxelShape)this, maxDist));
      }
   }
}
