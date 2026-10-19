package com.chronodawn.client.renderer;

import com.chronodawn.client.model.TimeTyrantModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.bosses.TimeTyrantEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TimeTyrantRenderer extends MobRenderer<TimeTyrantEntity, TimeTyrantModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/time_tyrant.png");
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CompatResourceLocation.create("chronodawn", "time_tyrant"), "main");

   public TimeTyrantRenderer(Context context) {
      super(context, new TimeTyrantModel(context.bakeLayer(LAYER_LOCATION)), 0.75F);
   }

   public ResourceLocation getTextureLocation(TimeTyrantEntity entity) {
      return TEXTURE;
   }
}
