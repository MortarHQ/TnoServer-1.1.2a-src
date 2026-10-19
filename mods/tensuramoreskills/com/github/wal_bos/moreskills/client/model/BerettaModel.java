package com.github.wal_bos.moreskills.client.model;

import com.github.wal_bos.moreskills.world.entity.BerettaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class BerettaModel extends GeoModel<BerettaEntity> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "geo/entity/beretta.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/entity/beretta.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "animations/entity/beretta.animation.json");

   public ResourceLocation getModelResource(BerettaEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(BerettaEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(BerettaEntity animatable) {
      return ANIMATION;
   }
}
