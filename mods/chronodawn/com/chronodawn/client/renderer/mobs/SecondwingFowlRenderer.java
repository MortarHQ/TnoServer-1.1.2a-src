package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.SecondwingFowlModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.SecondwingFowlEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class SecondwingFowlRenderer extends MobRenderer<SecondwingFowlEntity, SecondwingFowlModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/secondwing_fowl.png");

   public SecondwingFowlRenderer(Context context) {
      super(context, new SecondwingFowlModel(context.bakeLayer(SecondwingFowlModel.LAYER_LOCATION)), 0.3F);
   }

   public ResourceLocation getTextureLocation(SecondwingFowlEntity entity) {
      return TEXTURE;
   }
}
