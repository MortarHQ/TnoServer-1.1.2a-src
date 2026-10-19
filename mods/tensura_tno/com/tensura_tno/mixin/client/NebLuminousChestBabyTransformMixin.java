package com.tensura_tno.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tensura_tno.client.compat.NebLuminousChestTransform;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "io.github.manasmods.tensura_neb.client.entity.LuminousRenderer$Chest", remap = false)
public abstract class NebLuminousChestBabyTransformMixin extends RenderLayer<LivingEntity, PlayerModel<LivingEntity>> {
   private NebLuminousChestBabyTransformMixin() {
      super(null);
   }

   @Inject(
      method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILio/github/manasmods/tensura_neb/entity/LuminousValentineEntity;FFFFFF)V",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = Shift.AFTER),
      remap = false,
      require = 0
   )
   private void tensuraTno$followBabyBody(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      @Coerce LivingEntity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      CallbackInfo ci
   ) {
      NebLuminousChestTransform.applyBodyAge(poseStack, ((PlayerModel)this.getParentModel()).young);
   }
}
