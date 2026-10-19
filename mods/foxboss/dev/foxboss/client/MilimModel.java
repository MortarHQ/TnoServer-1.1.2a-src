package dev.foxboss.client;

import dev.foxboss.entity.MilimEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class MilimModel extends GeoModel<MilimEntity> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("foxboss", "geo/milim.geo.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("foxboss", "textures/entity/milim.png");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("foxboss", "animations/milim.animation.json");

   public ResourceLocation getModelResource(MilimEntity entity) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(MilimEntity entity) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(MilimEntity entity) {
      return ANIMATION;
   }
}
