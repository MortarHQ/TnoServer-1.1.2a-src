package com.foxablazeultimate.mixin.client;

import com.foxablazeultimate.client.AzathothChargeVisuals;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRendererAzathoth {
   @WrapMethod(method = "renderItem")
   private void foxablazeultimate$renderChargedHeldItem(
      LivingEntity owner,
      ItemStack stack,
      ItemDisplayContext context,
      boolean leftHand,
      PoseStack pose,
      MultiBufferSource buffers,
      int light,
      Operation<Void> original
   ) {
      LivingEntity previous = AzathothChargeVisuals.pushItemOwner(owner);

      try {
         original.call(new Object[]{owner, stack, context, leftHand, pose, buffers, light});
         AzathothChargeVisuals.renderGrip(owner, stack, context, pose, buffers);
      } finally {
         AzathothChargeVisuals.restoreItemOwner(previous);
      }
   }
}
