package com.notunanancyowen.spears.mixin;

import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.SpearsClient;
import com.notunanancyowen.spears.components.KineticWeapon;
import com.notunanancyowen.spears.components.SwingAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AgeableListModel<T> {
   @Shadow
   @Final
   public ModelPart rightArm;
   @Shadow
   @Final
   public ModelPart body;
   @Shadow
   @Final
   public ModelPart leftArm;
   @Shadow
   @Final
   public ModelPart head;
   @Shadow
   public ArmPose rightArmPose;
   @Shadow
   public ArmPose leftArmPose;

   @Shadow
   protected abstract ModelPart getArm(HumanoidArm var1);

   @Inject(method = "poseRightArm(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
   private void rightArmSpear(T entity, CallbackInfo ci) {
      if ((!this.rightArmPose.isTwoHanded() || this.attackTime > 0.0F)
         && this.spears$handleSpearAnimationPerArm(entity, HumanoidArm.RIGHT)
         && !entity.isUsingItem()
         && this.attackTime > 0.0F) {
         ci.cancel();
      }
   }

   @Inject(method = "poseLeftArm(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
   private void leftArmSpear(T entity, CallbackInfo ci) {
      if ((!this.leftArmPose.isTwoHanded() || this.attackTime > 0.0F)
         && this.spears$handleSpearAnimationPerArm(entity, HumanoidArm.LEFT)
         && !entity.isUsingItem()
         && this.attackTime > 0.0F) {
         ci.cancel();
      }
   }

   @Unique
   private boolean spears$handleSpearAnimationPerArm(T entity, HumanoidArm arm) {
      ItemStack itemStack = entity.getMainArm() == arm ? entity.getMainHandItem() : entity.getOffhandItem();
      if (itemStack.get(Spears.SWING_ANIMATION) instanceof SwingAnimation s && s.swingType().equals("stab")
         || itemStack.is(Spears.SPEARS) && this.attackTime <= 0.0F) {
         ModelPart usedArm = this.getArm(arm);
         usedArm.yRot = -0.1F * this.head.yRot;
         usedArm.xRot = (float) (-Math.PI / 2) + this.head.xRot + 0.8F;
         if (entity.isFallFlying() || entity.getSwimAmount(1.0F) > 0.0F) {
            usedArm.xRot -= 0.9599311F;
         }

         if (entity.isUsingItem()) {
            KineticWeapon kineticWeaponComponent = (KineticWeapon)itemStack.get(Spears.KINETIC_WEAPON);
            if (kineticWeaponComponent != null) {
               int i = arm == HumanoidArm.RIGHT ? 1 : -1;
               SpearsClient.holdUpAnimation lv = SpearsClient.holdUpAnimation.play(
                  kineticWeaponComponent, entity.getTicksUsingItem() + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true)
               );
               usedArm.yRot = usedArm.yRot + -i * lv.swayScaleFast() * (float) (Math.PI / 180.0) * lv.swayIntensity();
               usedArm.zRot = usedArm.zRot + -i * lv.swayScaleSlow() * (float) (Math.PI / 180.0) * lv.swayIntensity() * 0.5F;
               usedArm.xRot = usedArm.xRot
                  + (float) (Math.PI / 180.0)
                     * (
                        -40.0F * lv.raiseProgressStart()
                           + 30.0F * lv.raiseProgressMiddle()
                           + 20.0F * lv.raiseProgressEnd()
                           - 20.0F * lv.lowerProgress()
                           + 10.0F * lv.raiseBackProgress()
                           + 0.6F * lv.swayScaleSlow() * lv.swayIntensity()
                     );
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Inject(
      method = "setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;sin(F)F", ordinal = 3),
      cancellable = true
   )
   private void addSpearAnimation(T entity, float animationProgress, CallbackInfo ci) {
      if (entity.getMainHandItem().get(Spears.SWING_ANIMATION) instanceof SwingAnimation s) {
         if (s.swingType().equals("none")) {
            ci.cancel();
            return;
         }

         if (s.swingType().equals("stab")) {
            float f = entity.isUsingItem() ? 1.0F : this.attackTime;
            HumanoidArm arm = entity.getMainArm();
            this.rightArm.yRot = this.rightArm.yRot - this.body.yRot;
            this.leftArm.yRot = this.leftArm.yRot - this.body.yRot;
            this.leftArm.xRot = this.leftArm.xRot - this.body.yRot;
            float g = -(Mth.cos((float) Math.PI * Mth.clamp(Mth.inverseLerp(f, 0.0F, 0.05F), 0.0F, 1.0F)) - 1.0F) / 2.0F;
            float h = Mth.clamp(Mth.inverseLerp(f, 0.05F, 0.2F), 0.0F, 1.0F);
            h *= h;
            float i = Mth.clamp(Mth.inverseLerp(f, 0.4F, 1.0F), 0.0F, 1.0F);
            if (i < 0.5F) {
               i = i == 0.0F ? 0.0F : (float)(Math.pow(2.0, 20.0 * i - 10.0) / 2.0);
            } else {
               i = i == 1.0F ? 1.0F : (float)((2.0 - Math.pow(2.0, -20.0 * i + 10.0)) / 2.0);
            }

            this.getArm(arm).xRot += (90.0F * g - 120.0F * h + 30.0F * i) * (float) (Math.PI / 180.0);
            float trueYaw = this.head.yRot;

            while (trueYaw >= Math.PI * 2) {
               trueYaw -= (float) (Math.PI * 2);
            }

            while (trueYaw <= -Math.PI * 2) {
               trueYaw += (float) (Math.PI * 2);
            }

            this.getArm(arm).yRot += trueYaw * (h - i);
            ci.cancel();
         }
      }
   }

   @Inject(method = "setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V", at = @At("HEAD"), cancellable = true)
   private void animateLanceAttack(T entity, float animationProgress, CallbackInfo ci) {
      if ((entity.isUsingItem() ? entity.getUseItem() : entity.getMainHandItem()).get(Spears.KINETIC_WEAPON) instanceof KineticWeapon k && entity.isUsingItem()
         )
       {
         float f = entity.getTicksUsingItem() + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
         float g = k.delayTicks() == 0 ? 1.0F : k.delayTicks();
         if (f > k.getUseTicks()) {
            f = (1.0F - (f - k.getUseTicks())) / g;
            if (f < 0.0F) {
               f = 0.0F;
            }
         } else {
            f /= g;
            if (f > 1.0F) {
               f = 1.0F;
            }
         }

         ModelPart usedArm = this.getArm(entity.getUsedItemHand() == InteractionHand.MAIN_HAND ? entity.getMainArm() : entity.getMainArm().getOpposite());
         float trueYaw = this.head.yRot;

         while (trueYaw >= Math.PI * 2) {
            trueYaw -= (float) (Math.PI * 2);
         }

         while (trueYaw <= -Math.PI * 2) {
            trueYaw += (float) (Math.PI * 2);
         }

         usedArm.yRot = (float)Math.clamp(trueYaw, -Math.PI * 5.0 / 12.0, Math.PI * 5.0 / 12.0) * f;
         usedArm.xRot = usedArm.xRot
            + (this.head.xRot * 0.5F * f - (float)((Math.PI / 180.0) * (!entity.isFallFlying() && !(entity.getSwimAmount(1.0F) > 0.0F) ? 30.0 : 55.0)) * f);
         ci.cancel();
      }
   }
}
