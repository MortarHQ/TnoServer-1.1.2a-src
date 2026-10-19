package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.TimeboundRabbitModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.TimeboundRabbitEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TimeboundRabbitRenderer extends MobRenderer<TimeboundRabbitEntity, TimeboundRabbitModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/timebound_rabbit.png");

   public TimeboundRabbitRenderer(Context context) {
      super(context, new TimeboundRabbitModel(context.bakeLayer(TimeboundRabbitModel.LAYER_LOCATION)), 0.3F);
   }

   public ResourceLocation getTextureLocation(TimeboundRabbitEntity entity) {
      return TEXTURE;
   }
}
