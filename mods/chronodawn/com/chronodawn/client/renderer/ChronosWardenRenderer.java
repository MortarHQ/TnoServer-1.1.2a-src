package com.chronodawn.client.renderer;

import com.chronodawn.client.model.ChronosWardenModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.bosses.ChronosWardenEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ChronosWardenRenderer extends MobRenderer<ChronosWardenEntity, ChronosWardenModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/chronos_warden.png");

   public ChronosWardenRenderer(Context context) {
      super(context, new ChronosWardenModel(context.bakeLayer(ChronosWardenModel.LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(ChronosWardenEntity entity) {
      return TEXTURE;
   }
}
