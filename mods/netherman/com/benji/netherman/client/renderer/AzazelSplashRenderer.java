package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.AzazelSplashModel;
import com.benji.netherman.common.entity.AzazelSplashEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AzazelSplashRenderer extends GeoEntityRenderer<AzazelSplashEntity> {
   public AzazelSplashRenderer(Context renderManager) {
      super(renderManager, new AzazelSplashModel());
      this.shadowRadius = 0.0F;
      ResourceLocation emissiveTexture = NetherExp.location("textures/entity/splash_emissive.png");
      this.addRenderLayer(new GenericEmissiveLayer(this, emissiveTexture));
   }

   public void render(AzazelSplashEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      poseStack.pushPose();
      float yaw = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
      float pitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
      poseStack.mulPose(Axis.YP.rotationDegrees(yaw - 90.0F));
      poseStack.mulPose(Axis.ZP.rotationDegrees(pitch));
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      poseStack.popPose();
   }

   protected int getBlockLightLevel(AzazelSplashEntity entity, BlockPos pos) {
      return 15;
   }
}
