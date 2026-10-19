package com.tno_tensura_extras.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tno_tensura_extras.entity.TeleportPortalEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TeleportPortalRenderer extends GeoEntityRenderer<TeleportPortalEntity> {
   public TeleportPortalRenderer(Context context) {
      super(context, new TeleportPortalModel());
   }

   protected float getShadowRadius(TeleportPortalEntity animatable) {
      return 0.0F;
   }

   protected int getBlockLightLevel(TeleportPortalEntity animatable, BlockPos pos) {
      return 15;
   }

   protected void applyRotations(
      TeleportPortalEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale
   ) {
      poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
   }
}
