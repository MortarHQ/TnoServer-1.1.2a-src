package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.client.render.IOverlayOverride;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class NoRedOnDeathOverlay implements IRenderer<VoidBlossomEntity>, IOverlayOverride {
   private VoidBlossomEntity entity = null;

   @Override
   public int getOverlay() {
      return this.entity == null
         ? OverlayTexture.pack(OverlayTexture.u(0.0F), OverlayTexture.v(false))
         : OverlayTexture.pack(OverlayTexture.u(0.0F), OverlayTexture.v(this.entity.hurtTime > 0 && !this.entity.isDeadOrDying()));
   }

   public void render(VoidBlossomEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      this.entity = entity;
   }
}
