package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.TemporalCapridModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.TemporalCapridEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TemporalCapridRenderer extends MobRenderer<TemporalCapridEntity, TemporalCapridModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/temporal_caprid.png");

   public TemporalCapridRenderer(Context context) {
      super(context, new TemporalCapridModel(context.bakeLayer(TemporalCapridModel.LAYER_LOCATION)), 0.7F);
   }

   public ResourceLocation getTextureLocation(TemporalCapridEntity entity) {
      return TEXTURE;
   }
}
