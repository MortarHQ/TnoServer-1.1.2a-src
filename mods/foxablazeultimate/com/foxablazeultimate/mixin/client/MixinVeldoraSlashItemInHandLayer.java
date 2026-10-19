package com.foxablazeultimate.mixin.client;

import com.foxablazeultimate.client.VeldoraSlashAnimation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public abstract class MixinVeldoraSlashItemInHandLayer {
   @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
   private void foxablazeultimate$hideOffhandDuringVeldoraSlash(
      LivingEntity entity,
      ItemStack stack,
      ItemDisplayContext displayContext,
      HumanoidArm arm,
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      CallbackInfo callback
   ) {
      if (entity instanceof Player player && arm != player.getMainArm() && VeldoraSlashAnimation.isActive(player)) {
         callback.cancel();
      }
   }
}
