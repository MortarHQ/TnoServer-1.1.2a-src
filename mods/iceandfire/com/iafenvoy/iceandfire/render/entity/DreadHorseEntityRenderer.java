package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.DreadHorseEntity;
import com.iafenvoy.iceandfire.render.entity.feature.GenericGlowingFeatureRenderer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class DreadHorseEntityRenderer extends MobRenderer<DreadHorseEntity, HorseModel<DreadHorseEntity>> {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/dread/dread_knight_horse.png");
   public static final ResourceLocation TEXTURE_EYES = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/dread/dread_knight_horse_eyes.png");

   public DreadHorseEntityRenderer(Context context) {
      super(context, new HorseModel(context.bakeLayer(ModelLayers.HORSE)), 0.75F);
      this.addLayer(new GenericGlowingFeatureRenderer(this, TEXTURE_EYES));
   }

   public ResourceLocation getTexture(DreadHorseEntity entity) {
      return TEXTURE;
   }
}
