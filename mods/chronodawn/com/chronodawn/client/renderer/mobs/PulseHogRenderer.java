package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.PulseHogModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.PulseHogEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class PulseHogRenderer extends MobRenderer<PulseHogEntity, PulseHogModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/pulse_hog.png");

   public PulseHogRenderer(Context context) {
      super(context, new PulseHogModel(context.bakeLayer(PulseHogModel.LAYER_LOCATION)), 0.7F);
      this.addLayer(new PulseHogEmissiveLayer(this));
   }

   public ResourceLocation getTextureLocation(PulseHogEntity entity) {
      return TEXTURE;
   }
}
