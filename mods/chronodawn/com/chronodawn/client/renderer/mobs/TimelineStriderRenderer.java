package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.TimelineStriderModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.TimelineStriderEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TimelineStriderRenderer extends MobRenderer<TimelineStriderEntity, TimelineStriderModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/timeline_strider.png");

   public TimelineStriderRenderer(Context context) {
      super(context, new TimelineStriderModel(context.bakeLayer(TimelineStriderModel.LAYER_LOCATION)), 0.5F);
      this.addLayer(new TimelineStriderEmissiveLayer(this));
   }

   public ResourceLocation getTextureLocation(TimelineStriderEntity entity) {
      return TEXTURE;
   }
}
