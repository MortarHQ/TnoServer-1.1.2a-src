package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.ChronoTurtleModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.ChronoTurtleEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ChronoTurtleRenderer extends MobRenderer<ChronoTurtleEntity, ChronoTurtleModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/chrono_turtle.png");

   public ChronoTurtleRenderer(Context context) {
      super(context, new ChronoTurtleModel(context.bakeLayer(ChronoTurtleModel.LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(ChronoTurtleEntity entity) {
      return TEXTURE;
   }
}
