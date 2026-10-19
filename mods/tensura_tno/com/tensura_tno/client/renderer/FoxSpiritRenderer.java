package com.tensura_tno.client.renderer;

import com.tensura_tno.entity.spirit.FoxSpiritEntity;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class FoxSpiritRenderer extends MobRenderer<FoxSpiritEntity, FoxModel<FoxSpiritEntity>> {
   private static final ResourceLocation FOX_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/fox/fox.png");

   public FoxSpiritRenderer(Context context) {
      super(context, new FoxModel(context.bakeLayer(ModelLayers.FOX)), 0.4F);
   }

   public ResourceLocation getTextureLocation(FoxSpiritEntity entity) {
      return FOX_TEXTURE;
   }
}
