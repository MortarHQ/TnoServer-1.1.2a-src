package com.tensura_tno.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tensura_tno.ability.skill.HumanFormSkill;
import com.tensura_tno.client.race.PlayerKumodesuRenderManager;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.shin.tensura_kumodesu.client.renderer.layers.TaratectLayer", remap = false)
public abstract class KumodesuTaratectLayerSuppressMixin extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private KumodesuTaratectLayerSuppressMixin() {
      super(null);
   }

   @Inject(
      method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
      at = @At("HEAD"),
      cancellable = true,
      remap = false,
      require = 0
   )
   private void tensuraTno$skipTaratectOverlay(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      AbstractClientPlayer player,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      CallbackInfo ci
   ) {
      if (player != null) {
         if (player.isSpectator() && PlayerKumodesuRenderManager.isSpiderRace(player)) {
            ci.cancel();
         } else {
            AttributeInstance scale = player.getAttribute(Attributes.SCALE);
            if (scale != null) {
               if (scale.getModifier(HumanFormSkill.HUMAN_FORM_SCALE) != null) {
                  ci.cancel();
               }
            }
         }
      }
   }
}
