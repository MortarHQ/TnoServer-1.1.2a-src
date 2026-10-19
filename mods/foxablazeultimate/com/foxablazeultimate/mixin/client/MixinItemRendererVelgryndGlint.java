package com.foxablazeultimate.mixin.client;

import com.foxablazeultimate.client.VelgryndGlintRenderState;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRendererVelgryndGlint {
   @ModifyVariable(
      method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",
      at = @At("HEAD"),
      argsOnly = true
   )
   private MultiBufferSource foxablazeultimate$velgryndGlintBuffers(MultiBufferSource original, @Local(argsOnly = true) ItemStack stack) {
      return VelgryndGlintRenderState.wrap(stack, original);
   }
}
