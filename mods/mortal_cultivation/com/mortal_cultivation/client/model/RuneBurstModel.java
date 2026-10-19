package com.mortal_cultivation.client.model;

import com.mortal_cultivation.entity.RuneBurstEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class RuneBurstModel extends GeoModel<RuneBurstEntity> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "geo/block/ancient_stele_runes.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/block/ancient_stele_runes.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "animations/entity/rune_burst.animation.json");

   public RenderType getRenderType(RuneBurstEntity animatable, ResourceLocation texture) {
      return RenderType.entityTranslucentEmissive(texture, false);
   }

   public ResourceLocation getModelResource(RuneBurstEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(RuneBurstEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(RuneBurstEntity animatable) {
      return ANIMATION;
   }
}
