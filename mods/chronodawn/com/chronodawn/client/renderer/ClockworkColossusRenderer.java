package com.chronodawn.client.renderer;

import com.chronodawn.client.model.ClockworkColossusModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.bosses.ClockworkColossusEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ClockworkColossusRenderer extends MobRenderer<ClockworkColossusEntity, ClockworkColossusModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/clockwork_colossus.png");
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CompatResourceLocation.create("chronodawn", "clockwork_colossus"), "main");

   public ClockworkColossusRenderer(Context context) {
      super(context, new ClockworkColossusModel(context.bakeLayer(LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(ClockworkColossusEntity entity) {
      return TEXTURE;
   }
}
