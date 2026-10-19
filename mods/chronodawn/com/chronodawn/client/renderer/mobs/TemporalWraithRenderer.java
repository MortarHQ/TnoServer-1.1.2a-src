package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.TemporalWraithModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.TemporalWraithEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TemporalWraithRenderer extends MobRenderer<TemporalWraithEntity, TemporalWraithModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/temporal_wraith.png");

   public TemporalWraithRenderer(Context context) {
      super(context, new TemporalWraithModel(context.bakeLayer(TemporalWraithModel.LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(TemporalWraithEntity entity) {
      return TEXTURE;
   }
}
