package net.jadenxgamer.netherexp.util;

import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.Optional;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders.WorldVFXBuilder;
import team.lodestar.lodestone.systems.rendering.trail.TrailPointBuilder;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance.ScreenshakePositionData;

public class VFXHelper {
   public static void shotgunScreenShake(Vec3 pos, float fallOffDistance, Easing falloffCurve) {
      if ((Boolean)JNEConfigs.SHOTGUN_SCREENSHAKE.get()) {
         ScreenshakeHandler.addScreenshake(
            new ScreenshakeInstance(
               10, 1.0F, 0.0F, 0.0F, Easing.LINEAR, Easing.LINEAR, 1.0F, Optional.of(new ScreenshakePositionData(pos, fallOffDistance, falloffCurve))
            )
         );
      }
   }

   public static void explosionScreenShake(Vec3 pos, float fallOffDistance, Easing falloffCurve) {
      if ((Boolean)JNEConfigs.SHOTGUN_SCREENSHAKE.get()) {
         ScreenshakeHandler.addScreenshake(
            new ScreenshakeInstance(
               10, 3.0F, 2.0F, 1.0F, Easing.LINEAR, Easing.LINEAR, 1.0F, Optional.of(new ScreenshakePositionData(pos, fallOffDistance, falloffCurve))
            )
         );
      }
   }

   public static void renderEntityTrail(
      PoseStack poseStack,
      WorldVFXBuilder builder,
      TrailPointBuilder trailPointBuilder,
      Entity entity,
      Color color,
      float size,
      float alpha,
      float partialTicks
   ) {
      poseStack.pushPose();
      float offsetX = (float)Mth.lerp(partialTicks, entity.xOld, entity.getX());
      float offsetY = (float)Mth.lerp(partialTicks, entity.yOld, entity.getY());
      float offsetZ = (float)Mth.lerp(partialTicks, entity.zOld, entity.getZ());
      poseStack.translate(-offsetX, -offsetY, -offsetZ);
      builder.setColor(color).setAlpha(alpha).renderTrail(trailPointBuilder, f -> size);
      poseStack.translate(offsetX, offsetY, offsetZ);
      poseStack.popPose();
   }
}
