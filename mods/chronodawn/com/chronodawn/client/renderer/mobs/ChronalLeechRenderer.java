package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.ChronalLeechModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.ChronalLeechEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ChronalLeechRenderer extends MobRenderer<ChronalLeechEntity, ChronalLeechModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/chronal_leech.png");

   public ChronalLeechRenderer(Context context) {
      super(context, new ChronalLeechModel(context.bakeLayer(ChronalLeechModel.LAYER_LOCATION)), 0.3F);
   }

   public ResourceLocation getTextureLocation(ChronalLeechEntity entity) {
      return TEXTURE;
   }
}
