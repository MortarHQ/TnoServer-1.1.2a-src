package com.mongoose.royalvariations.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.client.render.model.RoyalBombProjectileModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

public class RoyalBombProjectileRenderer<T extends Projectile> extends EntityRenderer<T> {
   private static final ResourceLocation TEXTURE = RoyalVariations.location("textures/entity/projectiles/royal_bomb.png");
   public RoyalBombProjectileModel<T> model;

   public RoyalBombProjectileRenderer(Context p_174008_) {
      super(p_174008_);
      this.model = new RoyalBombProjectileModel(p_174008_.bakeLayer(RVModelLayer.ROYAL_BOMB_PROJECTILE));
      this.shadowRadius = 0.5F;
   }

   public void render(T pEntity, float pYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
      pPoseStack.pushPose();
      pPoseStack.translate(0.0, 0.25, 0.0);
      Vec3 vec3 = pEntity.getDeltaMovement();
      float xRot = -((float)(Mth.atan2(vec3.horizontalDistance(), vec3.y) * 180.0F / (float)Math.PI) - 90.0F);
      float yRot = -((float)(Mth.atan2(vec3.z, vec3.x) * 180.0F / (float)Math.PI) + 90.0F);
      pPoseStack.mulPose(Axis.YP.rotationDegrees(yRot));
      pPoseStack.mulPose(Axis.XP.rotationDegrees(xRot * (pEntity.tickCount * 0.25F)));
      VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity)));
      this.model.renderToBuffer(pPoseStack, consumer, 15728880, OverlayTexture.NO_OVERLAY, ARGB32.color(Mth.floor(127.5F), -1));
      pPoseStack.popPose();
      super.render(pEntity, pYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
   }

   public ResourceLocation getTextureLocation(T p_114482_) {
      return TEXTURE;
   }
}
