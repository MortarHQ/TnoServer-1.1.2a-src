package com.tno_tensura_extras.client;

import com.tno_tensura_extras.TensuraExtras;
import com.tno_tensura_extras.entity.DiabloEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class DiabloModel extends GeoModel<DiabloEntity> {
   private static final ResourceLocation MODEL = TensuraExtras.id("geo/entity/diablo.geo.json");
   private static final ResourceLocation TEXTURE = TensuraExtras.id("textures/entity/diablo.png");
   private static final ResourceLocation ANIMATION = TensuraExtras.id("animations/entity/diablo.animation.json");

   public ResourceLocation getModelResource(DiabloEntity entity) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(DiabloEntity entity) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(DiabloEntity entity) {
      return ANIMATION;
   }

   public RenderType getRenderType(DiabloEntity entity, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture);
   }
}
