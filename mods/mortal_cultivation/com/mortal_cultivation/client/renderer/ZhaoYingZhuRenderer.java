package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mortal_cultivation.client.model.ZhaoYingZhuModel;
import com.mortal_cultivation.entity.ZhaoYingZhuEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class ZhaoYingZhuRenderer extends GeoEntityRenderer<ZhaoYingZhuEntity> {
   public ZhaoYingZhuRenderer(Context context) {
      super(context, new ZhaoYingZhuModel());
      this.shadowRadius = 0.0F;
      this.addRenderLayer(new ZhaoYingZhuStoredTextureLayer(this));
      this.addRenderLayer(new ZhaoYingZhuGlowLayer(this));
   }

   protected void applyRotations(ZhaoYingZhuEntity orb, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
      super.applyRotations(orb, poseStack, ageInTicks, rotationYaw, partialTick, nativeScale);
      float tilt = 12.0F + Mth.sin(ageInTicks * 0.075F) * 4.0F;
      float roll = Mth.sin(ageInTicks * 0.055F) * 7.0F;
      poseStack.translate(0.0F, 0.1875F, 0.0F);
      poseStack.mulPose(Axis.YP.rotationDegrees(ageInTicks * 9.0F));
      poseStack.mulPose(Axis.XP.rotationDegrees(tilt));
      poseStack.mulPose(Axis.ZP.rotationDegrees(roll));
      poseStack.translate(0.0F, -0.1875F, 0.0F);
   }
}
