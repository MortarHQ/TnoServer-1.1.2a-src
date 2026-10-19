package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.HourglassGolemModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.HourglassGolemEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class HourglassGolemRenderer extends MobRenderer<HourglassGolemEntity, HourglassGolemModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/hourglass_golem.png");

   public HourglassGolemRenderer(Context context) {
      super(context, new HourglassGolemModel(context.bakeLayer(HourglassGolemModel.LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(HourglassGolemEntity entity) {
      return TEXTURE;
   }
}
