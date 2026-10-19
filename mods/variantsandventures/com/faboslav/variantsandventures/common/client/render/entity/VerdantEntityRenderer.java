package com.faboslav.variantsandventures.common.client.render.entity;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.client.model.VerdantEntityModel;
import com.faboslav.variantsandventures.common.entity.mob.VerdantEntity;
import com.faboslav.variantsandventures.common.init.VariantsAndVenturesModelLayers;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class VerdantEntityRenderer extends HumanoidMobRenderer<VerdantEntity, VerdantEntityModel> {
   private static final ResourceLocation TEXTURE = VariantsAndVentures.makeID("textures/entity/verdant/verdant.png");

   public VerdantEntityRenderer(Context context) {
      super(context, new VerdantEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.VERDANT)), 0.5F);
      this.addLayer(
         new HumanoidArmorLayer(
            this,
            new VerdantEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.VERDANT_INNER_ARMOR)),
            new VerdantEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.VERDANT_OUTER_ARMOR)),
            context.getModelManager()
         )
      );
   }

   public ResourceLocation getTextureLocation(VerdantEntity verdant) {
      return TEXTURE;
   }
}
