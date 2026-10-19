package com.chronodawn.client.renderer;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.projectiles.TimeArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TimeArrowRenderer extends ArrowRenderer<TimeArrowEntity> {
   private static final ResourceLocation TEXTURE_LOCATION = CompatResourceLocation.create("chronodawn", "textures/entity/projectiles/time_arrow.png");

   public TimeArrowRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTextureLocation(TimeArrowEntity entity) {
      return TEXTURE_LOCATION;
   }
}
