package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.projectile.SporeBallProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public class SporeBallSizeRenderer implements IRenderer<SporeBallProjectile> {
   public void render(SporeBallProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      float deathProgress = entity.impacted && !entity.isRemoved() ? (entity.impactedTicks + partialTicks) / 30.0F * 0.5F : 0.0F;
      float scaledDeathProgress = (float)(Math.pow(deathProgress, 2.0) + 1.0);
      poseStack.scale(scaledDeathProgress, scaledDeathProgress, scaledDeathProgress);
   }
}
