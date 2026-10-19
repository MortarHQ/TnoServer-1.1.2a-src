package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.client.render.IOverlayOverride;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class GauntletOverlay implements IRenderer<GauntletEntity>, IOverlayOverride {
   private GauntletEntity entity;
   private float partialTicks;

   @Override
   public int getOverlay() {
      if (this.entity == null) {
         return OverlayTexture.pack(OverlayTexture.u(0.0F), OverlayTexture.v(false));
      }

      float partialTicks = this.partialTicks != 0.0F ? this.partialTicks : 0.0F;
      float deathProgress = this.entity.deathTime == 0 ? 0.0F : (this.entity.deathTime + partialTicks) / 50.0F;
      float flash = (float)Math.sin(deathProgress * 50.0) * 0.1F + deathProgress * 0.9F;
      return OverlayTexture.pack(OverlayTexture.u(flash), OverlayTexture.v(this.entity.hurtTime > 0));
   }

   public void render(GauntletEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      this.entity = entity;
      this.partialTicks = partialTicks;
   }
}
