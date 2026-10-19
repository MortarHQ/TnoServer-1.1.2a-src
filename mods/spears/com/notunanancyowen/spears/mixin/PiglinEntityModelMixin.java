package com.notunanancyowen.spears.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.components.SwingAnimation;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PiglinModel.class)
public abstract class PiglinEntityModelMixin<T extends Mob> extends PlayerModel<T> {
   public PiglinEntityModelMixin(ModelPart root, boolean thinArms) {
      super(root, thinArms);
   }

   @Inject(method = "holdWeaponHigh(Lnet/minecraft/world/entity/Mob;)V", at = @At("HEAD"), cancellable = true)
   private void stabWithSpear(T entity, CallbackInfo ci) {
      if (entity.getMainHandItem().get(Spears.SWING_ANIMATION) instanceof SwingAnimation s && s.swingType().equals("stab")) {
         ci.cancel();
      }
   }

   @WrapOperation(
      method = "setupAnim(Lnet/minecraft/world/entity/Mob;FFFFF)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/model/AnimationUtils;animateZombieArms(Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;ZFF)V"
      )
   )
   private void stabWithSpear1(
      ModelPart leftArm,
      ModelPart rightArm,
      boolean attacking,
      float swingProgress,
      float animationProgress,
      Operation<Void> original,
      @Local(argsOnly = true) T entity
   ) {
      if (!(entity.getMainHandItem().get(Spears.SWING_ANIMATION) instanceof SwingAnimation s && s.swingType().equals("stab"))) {
         original.call(new Object[]{leftArm, rightArm, attacking, swingProgress, animationProgress});
      }
   }

   @Inject(method = "setupAttackAnimation(Lnet/minecraft/world/entity/Mob;F)V", at = @At("HEAD"), cancellable = true)
   private void stabWithSpear2(T mobEntity, float f, CallbackInfo ci) {
      if (mobEntity.getMainHandItem().get(Spears.SWING_ANIMATION) instanceof SwingAnimation s && s.swingType().equals("stab")) {
         super.setupAttackAnimation(mobEntity, f);
         ci.cancel();
      }
   }
}
