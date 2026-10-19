package com.github.b4ndithelps.tennogamenolife.client.renderer;

import com.github.b4ndithelps.tennogamenolife.client.model.BlackRodModel;
import com.github.b4ndithelps.tennogamenolife.entity.projectile.BlackRodProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public final class BlackRodRenderer extends GeoEntityRenderer<BlackRodProjectile> {
   public BlackRodRenderer(Context context) {
      super(context, new BlackRodModel());
      this.addRenderLayer(new AutoGlowingGeoLayer(this));
   }

   protected void applyRotations(BlackRodProjectile animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
      float yaw = Mth.lerp(partialTick, animatable.yRotO, animatable.getYRot());
      float pitch = Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot());
      poseStack.mulPose(Axis.YP.rotationDegrees(yaw - 90.0F));
      poseStack.mulPose(Axis.ZP.rotationDegrees(pitch - 90.0F));
      poseStack.translate(0.0, -1.9, 0.0);
   }

   protected float getShadowRadius(BlackRodProjectile entity) {
      return 0.0F;
   }

   protected int getBlockLightLevel(BlackRodProjectile entity, BlockPos pos) {
      return 15;
   }
}
