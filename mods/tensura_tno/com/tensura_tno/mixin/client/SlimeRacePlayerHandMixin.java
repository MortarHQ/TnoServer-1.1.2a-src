package com.tensura_tno.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tensura_tno.client.race.PlayerFoxSpiritRenderManager;
import com.tensura_tno.client.race.PlayerKumodesuRenderManager;
import com.tensura_tno.client.race.PlayerLizardmanRenderManager;
import com.tensura_tno.client.race.PlayerOrcRenderManager;
import com.tensura_tno.client.race.PlayerSlimeRenderManager;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerRenderer.class, priority = 900)
public abstract class SlimeRacePlayerHandMixin {
   private static final ResourceLocation SLIME_ARM_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/entity/slime_player_arm.png");
   private static final ResourceLocation LIZARDMAN_ARM_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tensura_tno", "textures/entity/lizardman_player_arm.png"
   );
   private static final ResourceLocation SPIDER_ARM_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/entity/spider_player_arm.png");
   private static final ResourceLocation ORC_ARM_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/entity/orc_player_arm.png");
   private static final ResourceLocation FOX_SPIRIT_ARM_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tensura_tno", "textures/entity/fox_spirit_player_arm.png"
   );

   @Inject(
      method = "renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;)V",
      at = @At("HEAD"),
      cancellable = true,
      require = 0
   )
   private void tensuraTno$replaceArmTexture(
      PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, ModelPart arm, ModelPart sleeve, CallbackInfo ci
   ) {
      boolean slime = PlayerSlimeRenderManager.shouldRenderAsSlime(player);
      boolean lizardman = PlayerLizardmanRenderManager.shouldRenderAsLizardman(player);
      boolean spider = PlayerKumodesuRenderManager.shouldRenderAsSpider(player);
      boolean orc = PlayerOrcRenderManager.shouldRenderAsOrc(player);
      boolean foxSpirit = PlayerFoxSpiritRenderManager.shouldRenderAsFoxSpirit(player);
      if (slime || lizardman || spider || orc || foxSpirit) {
         ResourceLocation texture = slime
            ? SLIME_ARM_TEXTURE
            : (spider ? SPIDER_ARM_TEXTURE : (orc ? ORC_ARM_TEXTURE : (foxSpirit ? FOX_SPIRIT_ARM_TEXTURE : LIZARDMAN_ARM_TEXTURE)));
         RenderType renderType = slime ? RenderType.entityTranslucent(texture) : RenderType.entityCutoutNoCull(texture);
         int tintColor = slime ? -1056964609 : -1;
         VertexConsumer consumer = bufferSource.getBuffer(renderType);
         boolean armVisible = arm.visible;
         boolean sleeveVisible = sleeve.visible;
         arm.visible = true;
         sleeve.visible = true;

         try {
            arm.xRot = 0.0F;
            arm.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, tintColor);
            sleeve.xRot = 0.0F;
            sleeve.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, tintColor);
         } finally {
            arm.visible = armVisible;
            sleeve.visible = sleeveVisible;
         }

         ci.cancel();
      }
   }
}
