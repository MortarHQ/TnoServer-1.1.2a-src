package com.notunanancyowen.spears.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.notunanancyowen.spears.Spears;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
   @Shadow
   @Final
   private ItemModelShaper itemModelShaper;

   @WrapOperation(
      method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderModelLists(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;IILcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V"
      )
   )
   private void renderSpears(
      ItemRenderer instance,
      BakedModel model,
      ItemStack stack,
      int light,
      int overlay,
      PoseStack matrices,
      VertexConsumer vertices,
      Operation<Void> original,
      @Local(argsOnly = true) ItemDisplayContext renderMode
   ) {
      if (!stack.is(Spears.SPEARS) || renderMode != ItemDisplayContext.GUI && renderMode != ItemDisplayContext.GROUND && renderMode != ItemDisplayContext.FIXED
         )
       {
         original.call(new Object[]{instance, model, stack, light, overlay, matrices, vertices});
      } else {
         original.call(new Object[]{instance, this.itemModelShaper.getItemModel(stack), stack, light, overlay, matrices, vertices});
      }
   }
}
