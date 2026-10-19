package com.notunanancyowen.spears.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.SpearsClient;
import com.notunanancyowen.spears.components.KineticWeapon;
import com.notunanancyowen.spears.components.SwingAnimation;
import com.notunanancyowen.spears.dataholders.SpearUser;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class HeldItemRendererMixin {
   @WrapOperation(
      method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 12)
   )
   private void suppressNormalSwing(PoseStack instance, float x, float y, float z, Operation<Void> original, @Local(argsOnly = true) ItemStack itemStack) {
      if (!(itemStack.get(Spears.SWING_ANIMATION) instanceof SwingAnimation s && (s.swingType().equals("stab") || s.swingType().equals("none")))) {
         original.call(new Object[]{instance, x, y, z});
      }
   }

   @WrapOperation(
      method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V",
         ordinal = 8
      )
   )
   private void suppressSwapAnimation(
      ItemInHandRenderer instance,
      PoseStack matrices,
      HumanoidArm arm,
      float equipProgress,
      Operation<Void> original,
      @Local(argsOnly = true) AbstractClientPlayer player,
      @Local(argsOnly = true) ItemStack itemStack
   ) {
      if (itemStack.get(Spears.SWING_ANIMATION) instanceof SwingAnimation s && s.swingType().equals("stab") && player.attackAnim > 0.0F) {
         original.call(new Object[]{instance, matrices, arm, 0.0F});
      } else {
         original.call(new Object[]{instance, matrices, arm, equipProgress});
      }
   }

   @WrapOperation(
      method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmAttackTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"
      )
   )
   private void spearAnimation(
      ItemInHandRenderer instance,
      PoseStack matrices,
      HumanoidArm arm,
      float swingProgress,
      Operation<Void> original,
      @Local(argsOnly = true) ItemStack itemStack
   ) {
      if (itemStack.get(Spears.SWING_ANIMATION) instanceof SwingAnimation s) {
         if (s.swingType().equals("stab")) {
            float g = -(Mth.cos((float) Math.PI * Mth.clamp(Mth.inverseLerp(swingProgress, 0.0F, 0.05F), 0.0F, 1.0F)) - 1.0F) / 2.0F;
            float h = Mth.clamp(Mth.inverseLerp(swingProgress, 0.05F, 0.2F), 0.0F, 1.0F);
            h *= h;
            float j = Mth.clamp(Mth.inverseLerp(swingProgress, 0.4F, 1.0F), 0.0F, 1.0F);
            if (j < 0.5F) {
               j = j == 0.0F ? 0.0F : (float)(Math.pow(2.0, 20.0 * j - 10.0) / 2.0);
            } else {
               j = j == 1.0F ? 1.0F : (float)((2.0 - Math.pow(2.0, -20.0 * j + 10.0)) / 2.0);
            }

            boolean trident = itemStack.getItem() instanceof TridentItem;
            matrices.translate(j * 0.1F * (g - h), -0.075F * (g - j), (trident ? -0.4F * (g - j) : 0.0F) + 0.65F * (g - h));
            matrices.mulPose(Axis.XP.rotationDegrees(-(trident ? 50.0F : 70.0F) * (g - j)));
            matrices.translate(0.0, 0.0, -0.25 * (j - h));
            return;
         }

         if (s.swingType().equals("none")) {
            return;
         }
      }

      original.call(new Object[]{instance, matrices, arm, swingProgress});
   }

   @Inject(
      method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;")
   )
   private void insertSpearAnimation(
      AbstractClientPlayer player,
      float tickDelta,
      float pitch,
      InteractionHand hand,
      float swingProgress,
      ItemStack item,
      float equipProgress,
      PoseStack matrices,
      MultiBufferSource vertexConsumers,
      int light,
      CallbackInfo ci,
      @Share("suppress") LocalBooleanRef suppress
   ) {
      if (player.getUsedItemHand() == hand && item.get(Spears.KINETIC_WEAPON) instanceof KineticWeapon k) {
         HumanoidArm arm = hand == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
         int i = arm == HumanoidArm.RIGHT ? 1 : -1;
         matrices.translate(i * 0.56F, -0.52F, -0.72F);
         SpearsClient.holdUpAnimation lv = SpearsClient.holdUpAnimation.play(
            k, item.getUseDuration(player) - (player.getUseItemRemainingTicks() - tickDelta + 1.0F)
         );
         matrices.translate(
            i * (lv.raiseProgress() * 0.15F + lv.raiseProgressEnd() * -0.05F + lv.swayProgress() * -0.1F + lv.swayScaleSlow() * 0.005F),
            lv.raiseProgress() * -0.075F + lv.raiseProgressMiddle() * 0.075F + lv.swayScaleFast() * 0.01F,
            lv.raiseProgressStart() * 0.05 + lv.raiseProgressEnd() * -0.05 + lv.swayScaleSlow() * 0.005F
         );
         float f = lv.raiseProgress();
         if (f < 0.5F) {
            f = 4.0F * f * f * (7.189819F * f - 2.5949094F) / 2.0F;
         } else {
            float g = 2.0F * f - 2.0F;
            f = (g * g * (3.5949094F * g + 2.5949094F) + 2.0F) / 2.0F;
         }

         matrices.rotateAround(
            Axis.XP.rotationDegrees(-65.0F * f - 35.0F * (1.0F - lv.lowerProgress()) + 100.0F * lv.raiseBackProgress() + -0.5F * lv.swayScaleFast()),
            0.0F,
            0.1F,
            0.0F
         );
         matrices.rotateAround(
            Axis.YN
               .rotationDegrees(
                  i
                     * (
                        -90.0F * Mth.clamp(Mth.inverseLerp(lv.raiseProgress(), 0.5F, 0.55F), 0.0F, 1.0F)
                           + 90.0F * lv.swayProgress()
                           + 2.0F * lv.swayScaleSlow()
                     )
               ),
            i * 0.15F,
            0.0F,
            0.0F
         );
         float h = 0.0F;
         if (player instanceof SpearUser s) {
            h = s.getTimeSinceLastKineticAttack(tickDelta);
         }

         h = 1.0F
            - Mth.square(Mth.square(1.0F - Mth.clamp(Mth.inverseLerp(h, 1.0F, 3.0F), 0.0F, 1.0F)))
            + (Mth.cos((float) Math.PI * Mth.clamp(Mth.inverseLerp(h, 3.0F, 10.0F), 0.0F, 1.0F)) - 1.0F) / 2.0F;
         h *= 0.4F;
         if (h >= 10.0F) {
            h = 0.0F;
         }

         matrices.translate(0.0F, -h, 0.0F);
         suppress.set(true);
      }
   }

   @WrapOperation(
      method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V",
         ordinal = 2
      )
   )
   private void suppressEquipOffsetForUseAnimation(
      ItemInHandRenderer instance,
      PoseStack matrices,
      HumanoidArm arm,
      float equipProgress,
      Operation<Void> original,
      @Share("suppress") LocalBooleanRef suppress
   ) {
      if (!suppress.get()) {
         original.call(new Object[]{instance, matrices, arm, equipProgress});
      }
   }
}
