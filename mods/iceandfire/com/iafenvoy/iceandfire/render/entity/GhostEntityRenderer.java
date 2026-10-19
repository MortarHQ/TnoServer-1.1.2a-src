package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.GhostEntity;
import com.iafenvoy.iceandfire.registry.IafRenderLayers;
import com.iafenvoy.iceandfire.render.model.GhostModel;
import com.iafenvoy.iceandfire.util.Color4i;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.joml.Matrix4f;

public class GhostEntityRenderer extends MobRenderer<GhostEntity, GhostModel> {
   public static final ResourceLocation TEXTURE_0 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/ghost/ghost_white.png");
   public static final ResourceLocation TEXTURE_1 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/ghost/ghost_blue.png");
   public static final ResourceLocation TEXTURE_2 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/ghost/ghost_green.png");
   public static final ResourceLocation TEXTURE_SHOPPING_LIST = ResourceLocation.fromNamespaceAndPath(
      "iceandfire", "textures/entity/ghost/haunted_shopping_list.png"
   );

   public GhostEntityRenderer(Context renderManager) {
      super(renderManager, new GhostModel(0.0F), 0.55F);
   }

   public static ResourceLocation getGhostOverlayForType(int ghost) {
      return switch (ghost) {
         case -1 -> TEXTURE_SHOPPING_LIST;
         default -> TEXTURE_0;
         case 1 -> TEXTURE_1;
         case 2 -> TEXTURE_2;
      };
   }

   public void render(GhostEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      this.shadowRadius = 0.0F;
      matrixStackIn.pushPose();
      ((GhostModel)this.model).attackTime = this.getAttackAnim(entityIn, partialTicks);
      boolean shouldSit = entityIn.isPassenger() && entityIn.getVehicle() != null;
      ((GhostModel)this.model).riding = shouldSit;
      ((GhostModel)this.model).young = entityIn.isBaby();
      float f = Mth.rotLerp(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot);
      float f1 = Mth.rotLerp(partialTicks, entityIn.yHeadRotO, entityIn.yHeadRot);
      float f2 = f1 - f;
      if (shouldSit && entityIn.getVehicle() instanceof LivingEntity livingentity) {
         f = Mth.rotLerp(partialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
         f2 = f1 - f;
         float f3 = Mth.wrapDegrees(f2);
         if (f3 < -85.0F) {
            f3 = -85.0F;
         }

         if (f3 >= 85.0F) {
            f3 = 85.0F;
         }

         f = f1 - f3;
         if (f3 * f3 > 2500.0F) {
            f += f3 * 0.2F;
         }

         f2 = f1 - f;
      }

      float f6 = Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot());
      if (entityIn.getPose() == Pose.SLEEPING) {
         Direction direction = entityIn.getBedOrientation();
         if (direction != null) {
            float f4 = entityIn.getEyeHeight(Pose.STANDING) - 0.1F;
            matrixStackIn.translate(-direction.getStepX() * f4, 0.0, -direction.getStepZ() * f4);
         }
      }

      float f7 = this.getBob(entityIn, partialTicks);
      this.setupRotations(entityIn, matrixStackIn, f7, f, partialTicks, 1.0F);
      matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
      this.scale(entityIn, matrixStackIn, partialTicks);
      matrixStackIn.translate(0.0, -1.501F, 0.0);
      float f8 = 0.0F;
      float f5 = 0.0F;
      if (!shouldSit && entityIn.isAlive()) {
         f8 = entityIn.walkAnimation.speed();
         f5 = entityIn.walkAnimation.position();
         if (entityIn.isBaby()) {
            f5 *= 3.0F;
         }

         if (f8 > 1.0F) {
            f8 = 1.0F;
         }
      }

      ((GhostModel)this.model).prepareMobModel(entityIn, f5, f8, partialTicks);
      ((GhostModel)this.model).setAngles(entityIn, f5, f8, f7, f2, f6);
      float alphaForRender = this.getAlphaForRender(entityIn, partialTicks);
      RenderType rendertype = entityIn.isDaytimeMode()
         ? IafRenderLayers.getGhostDaytime(this.getTexture(entityIn))
         : IafRenderLayers.getGhost(this.getTexture(entityIn));
      if (!entityIn.isInvisible()) {
         VertexConsumer ivertexbuilder = bufferIn.getBuffer(rendertype);
         int i = getOverlayCoords(entityIn, this.getWhiteOverlayProgress(entityIn, partialTicks));
         if (entityIn.isHauntedShoppingList()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.0F, 0.8F + Mth.sin((entityIn.tickCount + partialTicks) * 0.15F) * 0.1F, 0.0F);
            matrixStackIn.scale(0.6F, 0.6F, 0.6F);
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F));
            matrixStackIn.pushPose();
            com.mojang.blaze3d.vertex.PoseStack.Pose entry = matrixStackIn.last();
            Matrix4f matrix4f = entry.pose();
            this.drawVertex(matrix4f, entry, ivertexbuilder, i, (int)(alphaForRender * 255.0F), -1, -2, 0, 1.0F, 0.0F, 0, 1, 0, 240);
            this.drawVertex(matrix4f, entry, ivertexbuilder, i, (int)(alphaForRender * 255.0F), 1, -2, 0, 0.5F, 0.0F, 0, 1, 0, 240);
            this.drawVertex(matrix4f, entry, ivertexbuilder, i, (int)(alphaForRender * 255.0F), 1, 2, 0, 0.5F, 1.0F, 0, 1, 0, 240);
            this.drawVertex(matrix4f, entry, ivertexbuilder, i, (int)(alphaForRender * 255.0F), -1, 2, 0, 1.0F, 1.0F, 0, 1, 0, 240);
            matrixStackIn.popPose();
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F));
            matrixStackIn.pushPose();
            entry = matrixStackIn.last();
            matrix4f = entry.pose();
            this.drawVertex(matrix4f, entry, ivertexbuilder, i, (int)(alphaForRender * 255.0F), -1, -2, 0, 0.0F, 0.0F, 0, 1, 0, 240);
            this.drawVertex(matrix4f, entry, ivertexbuilder, i, (int)(alphaForRender * 255.0F), 1, -2, 0, 0.5F, 0.0F, 0, 1, 0, 240);
            this.drawVertex(matrix4f, entry, ivertexbuilder, i, (int)(alphaForRender * 255.0F), 1, 2, 0, 0.5F, 1.0F, 0, 1, 0, 240);
            this.drawVertex(matrix4f, entry, ivertexbuilder, i, (int)(alphaForRender * 255.0F), -1, 2, 0, 0.0F, 1.0F, 0, 1, 0, 240);
            matrixStackIn.popPose();
            matrixStackIn.popPose();
         } else {
            ((GhostModel)this.model).renderToBuffer(matrixStackIn, ivertexbuilder, 240, i, new Color4i(1.0, 1.0, 1.0, alphaForRender).getIntValue());
         }
      }

      if (!entityIn.isSpectator()) {
         for (RenderLayer<GhostEntity, GhostModel> layerrenderer : this.layers) {
            layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f8, partialTicks, f7, f2, f6);
         }
      }

      matrixStackIn.popPose();
   }

   protected float getLyingAngle(GhostEntity ghost) {
      return 0.0F;
   }

   public float getAlphaForRender(GhostEntity entityIn, float partialTicks) {
      return entityIn.isDaytimeMode()
         ? Mth.clamp((101 - Math.min(entityIn.getDaytimeCounter(), 100)) / 100.0F, 0.0F, 1.0F)
         : Mth.clamp((Mth.sin((entityIn.tickCount + partialTicks) * 0.1F) + 1.0F) * 0.5F + 0.1F, 0.0F, 1.0F);
   }

   public void scale(GhostEntity LivingEntityIn, PoseStack stack, float partialTickTime) {
   }

   public ResourceLocation getTexture(GhostEntity ghost) {
      return switch (ghost.getColor()) {
         case -1 -> TEXTURE_SHOPPING_LIST;
         default -> TEXTURE_0;
         case 1 -> TEXTURE_1;
         case 2 -> TEXTURE_2;
      };
   }

   public void drawVertex(
      Matrix4f stack,
      com.mojang.blaze3d.vertex.PoseStack.Pose entry,
      VertexConsumer builder,
      int packedRed,
      int alphaInt,
      int x,
      int y,
      int z,
      float u,
      float v,
      int lightmap,
      int lightmap3,
      int lightmap2,
      int lightmap4
   ) {
      builder.addVertex(stack, x, y, z)
         .setColor(255, 255, 255, alphaInt)
         .setUv(u, v)
         .setOverlay(packedRed)
         .setLight(lightmap4)
         .setNormal(entry, lightmap, lightmap2, lightmap3);
   }
}
