package com.chronodawn.client.renderer;

import com.chronodawn.client.model.TimeGuardianModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.bosses.TimeGuardianEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TimeGuardianRenderer extends MobRenderer<TimeGuardianEntity, TimeGuardianModel<TimeGuardianEntity>> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/time_guardian.png");
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CompatResourceLocation.create("chronodawn", "time_guardian"), "main");

   public TimeGuardianRenderer(Context context) {
      super(context, new TimeGuardianModel(context.bakeLayer(LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(TimeGuardianEntity entity) {
      return TEXTURE;
   }
}
