package com.blackgear.vanillabackport.core.mixin.common.entities;

import com.blackgear.vanillabackport.common.api.extensions.AirborneMoverEntity;
import com.blackgear.vanillabackport.common.registries.ModAttributes;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {
   @Shadow
   protected abstract boolean isAffectedByFluids();

   @ModifyReturnValue(method = "createLivingAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;", at = @At("RETURN"))
   private static Builder createLivingAttributes(Builder original) {
      original.add(ModAttributes.AIR_DRAG_MODIFIER);
      original.add(ModAttributes.BOUNCINESS);
      original.add(ModAttributes.FRICTION_MODIFIER);
      return original;
   }

   @Inject(
      method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;calculateEntityAnimation(Z)V")
   )
   private void vb$afterTravelInFluid(Vec3 input, CallbackInfo ci) {
      if (this instanceof AirborneMoverEntity airborne) {
         LivingEntity self = (LivingEntity)this;
         if (self.isControlledByLocalInstance()) {
            FluidState fluidState = self.level().getFluidState(self.blockPosition());
            if ((self.isInWater() || self.isInLava()) && this.isAffectedByFluids() && !self.canStandOnFluid(fluidState)) {
               airborne.afterTravelInFluid();
            }
         }
      }
   }

   @ModifyArg(
      method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/LivingEntity;handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;"
      ),
      index = 1
   )
   private float vb$modifyMovementFriction(float rawFriction) {
      LivingEntity self = (LivingEntity)this;
      if (!self.onGround()) {
         return 1.0F;
      }

      AttributeInstance inst = self.getAttribute(ModAttributes.FRICTION_MODIFIER);
      return inst == null ? rawFriction : vb$computeModifiedFriction(rawFriction, (float)inst.getValue());
   }

   @WrapOperation(
      method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V", ordinal = 3)
   )
   private void vb$applyAirDrag(LivingEntity self, double argX, double argY, double argZ, Operation<Void> original) {
      BlockPos posBelow = self.getBlockPosBelowThatAffectsMyMovement();
      float rawFriction = self.level().getBlockState(posBelow).getBlock().getFriction();
      float vanillaF = self.onGround() ? rawFriction * 0.91F : 0.91F;
      boolean isFlyingAnimal = self instanceof FlyingAnimal;
      float vanillaVertF = isFlyingAnimal ? vanillaF : 0.98F;
      double vec36x = vanillaF != 0.0F ? argX / vanillaF : argX;
      double vec36z = vanillaF != 0.0F ? argZ / vanillaF : argZ;
      double q = vanillaVertF != 0.0F ? argY / vanillaVertF : argY;
      AttributeInstance frictionInst = self.getAttribute(ModAttributes.FRICTION_MODIFIER);
      AttributeInstance airDragInst = self.getAttribute(ModAttributes.AIR_DRAG_MODIFIER);
      float frictionMod = frictionInst != null ? (float)frictionInst.getValue() : 1.0F;
      float airDragMod = airDragInst != null ? (float)airDragInst.getValue() : 1.0F;
      float blockFriction = self.onGround() ? vb$computeModifiedFriction(rawFriction, frictionMod) : 1.0F;
      float airDrag = vb$computeModifiedFriction(0.91F, airDragMod);
      float friction = blockFriction * airDrag;
      boolean omnidirectional = this instanceof AirborneMoverEntity a && a.omnidirectionalAirMover();
      float verticalFriction;
      if (omnidirectional) {
         verticalFriction = friction;
      } else if (isFlyingAnimal) {
         verticalFriction = friction;
      } else {
         verticalFriction = vb$computeModifiedFriction(0.98F, airDragMod);
      }

      original.call(new Object[]{self, vec36x * friction, q * verticalFriction, vec36z * friction});
   }

   @Unique
   private static float vb$computeModifiedFriction(float friction, float modifier) {
      return Mth.clamp(1.0F - (1.0F - friction) * modifier, 0.0F, 1.0F);
   }
}
