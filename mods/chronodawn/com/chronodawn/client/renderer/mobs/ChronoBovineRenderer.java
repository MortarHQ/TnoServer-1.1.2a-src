package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.ChronoBovineModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.ChronoBovineEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ChronoBovineRenderer extends MobRenderer<ChronoBovineEntity, ChronoBovineModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/chrono_bovine.png");

   public ChronoBovineRenderer(Context context) {
      super(context, new ChronoBovineModel(context.bakeLayer(ChronoBovineModel.LAYER_LOCATION)), 0.7F);
   }

   public ResourceLocation getTextureLocation(ChronoBovineEntity entity) {
      return TEXTURE;
   }
}
