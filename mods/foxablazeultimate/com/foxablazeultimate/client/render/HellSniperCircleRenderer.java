package com.foxablazeultimate.client.render;

import com.foxablazeultimate.client.model.HellSniperCircleModel;
import com.foxablazeultimate.entity.HellSniperCircleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.Color;

public final class HellSniperCircleRenderer extends GeoEntityRenderer<HellSniperCircleEntity> {
   private static final float MODEL_CENTER_X = 0.040039062F;
   private static final float MODEL_CENTER_Y = 0.56152344F;

   public HellSniperCircleRenderer(Context context) {
      super(context, new HellSniperCircleModel());
      this.shadowRadius = 0.0F;
      this.withScale(5.0F);
      this.addRenderLayer(new HellSniperCircleGlowLayer(this));
   }

   protected int getBlockLightLevel(HellSniperCircleEntity entity, BlockPos blockPos) {
      return 15;
   }

   public boolean shouldRender(HellSniperCircleEntity entity, Frustum frustum, double cameraX, double cameraY, double cameraZ) {
      AABB bounds = entity.getBoundingBoxForCulling().inflate(8.0);
      return entity.noCulling || frustum.isVisible(bounds);
   }

   public Color getRenderColor(HellSniperCircleEntity entity, float partialTick, int packedLight) {
      Color color = super.getRenderColor(entity, partialTick, packedLight);
      float animationAge = entity.getAnimationAge(partialTick);
      float appearanceAge = animationAge - entity.getAppearanceDelayTicks();
      float appearanceProgress = Mth.clamp(appearanceAge / 2.5F, 0.0F, 1.0F);
      int fadeTicks = entity.getPostFireDurationTicks();
      if (fadeTicks <= 0) {
         int alpha = Mth.clamp(Mth.ceil(color.getAlpha() * appearanceProgress), 0, 255);
         return Color.ofARGB(alpha, color.getRed(), color.getGreen(), color.getBlue());
      } else {
         float fadeStart = entity.isInfiniteSniping()
            ? Math.max(0.0F, (float)(entity.getInfiniteAttackEnd() - entity.getCastStart()))
            : entity.getFormationAppearanceTicks() + entity.getAimingDurationTicks() + entity.getFiringWarmupTicks();
         float fade = Mth.clamp((animationAge - fadeStart) / fadeTicks, 0.0F, 1.0F);
         int alpha = Mth.clamp(Mth.ceil(color.getAlpha() * appearanceProgress * (1.0F - fade)), 0, 255);
         return Color.ofARGB(alpha, color.getRed(), color.getGreen(), color.getBlue());
      }
   }

   protected void applyRotations(HellSniperCircleEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
      float yaw = Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
      float pitch = entity.getRenderPitch(partialTick);
      poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
      poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
      poseStack.translate(-0.040039062F, -0.56152344F, 0.0);
   }
}
