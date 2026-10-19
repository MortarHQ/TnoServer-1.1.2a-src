package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderSunstrike;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderUmvuthi;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoRenderPlayer;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy.SolarFlareAbility;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class SolarFlareLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private GeckoRenderPlayer renderPlayerAnimated;

   public SolarFlareLayer(GeckoRenderPlayer entityRendererIn) {
      super(entityRendererIn);
      this.renderPlayerAnimated = entityRendererIn;
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      AbstractClientPlayer player,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      SolarFlareAbility ability = (SolarFlareAbility)AbilityHandler.INSTANCE.getAbility(player, AbilityHandler.SOLAR_FLARE_ABILITY);
      if (ability != null && ability.isUsing() && ability.getTicksInUse() > 12 && ability.getTicksInUse() < 21) {
         matrixStackIn.pushPose();
         MowzieGeoBone bone = this.renderPlayerAnimated.getAnimatedPlayerModel().getMowzieBone("Body");
         Vector4f vecTranslation = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
         vecTranslation.mul(bone.getWorldSpaceMatrix());
         PoseStack newMatrixStack = new PoseStack();
         newMatrixStack.translate(vecTranslation.x(), vecTranslation.y(), vecTranslation.z());
         newMatrixStack.scale(0.8F, 0.8F, 0.8F);
         VertexConsumer ivertexbuilder = bufferIn.getBuffer(MMRenderType.getSolarFlare(RenderSunstrike.TEXTURE));
         Pose matrixstack$entry2 = newMatrixStack.last();
         Matrix4f matrix4f2 = matrixstack$entry2.pose();
         Matrix3f matrix3f = matrixstack$entry2.normal();
         RenderUmvuthi.drawBurst(matrix4f2, matrix3f, ivertexbuilder, ability.getTicksInUse() - 12 + partialTicks, packedLightIn);
         matrixStackIn.popPose();
      }
   }
}
