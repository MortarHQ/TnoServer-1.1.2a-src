package com.craisinlord.apple_cows.content.client.model;

import com.craisinlord.apple_cows.content.entity.AppleCowEntity;
import com.craisinlord.apple_cows.content.entity.AppleCowEntityVariants;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AppleCowModel extends GeoModel<AppleCowEntity> {
   private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("apple_cows", "geo/apple_cow.geo.json");
   private static final ResourceLocation BABY_MODEL = ResourceLocation.fromNamespaceAndPath("apple_cows", "geo/baby_apple_cow.geo.json");
   private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath("apple_cows", "animations/apple_cow.animation.json");
   private static final ResourceLocation BABY_ANIMATION = ResourceLocation.fromNamespaceAndPath("apple_cows", "animations/baby_apple_cow.animation.json");
   private static final ResourceLocation APPLE_TEXTURE = ResourceLocation.fromNamespaceAndPath("apple_cows", "textures/entity/apple_cow/apple_cow.png");
   private static final ResourceLocation APPLE_SHEARED_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "apple_cows", "textures/entity/apple_cow/apple_cow_sheared.png"
   );
   private static final ResourceLocation BABY_APPLE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "apple_cows", "textures/entity/apple_cow/baby_apple_cow.png"
   );
   private static final ResourceLocation GOLDEN_TEXTURE = ResourceLocation.fromNamespaceAndPath("apple_cows", "textures/entity/apple_cow/golden_apple_cow.png");
   private static final ResourceLocation GOLDEN_SHEARED_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "apple_cows", "textures/entity/apple_cow/golden_apple_cow_sheared.png"
   );
   private static final ResourceLocation BABY_GOLDEN_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "apple_cows", "textures/entity/apple_cow/baby_golden_apple_cow.png"
   );
   private static final ResourceLocation ENCHANTED_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "apple_cows", "textures/entity/apple_cow/enchanted_golden_apple_cow.png"
   );
   private static final ResourceLocation ENCHANTED_SHEARED_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "apple_cows", "textures/entity/apple_cow/enchanted_golden_apple_cow_sheared.png"
   );

   public ResourceLocation getModelResource(AppleCowEntity animatable) {
      return animatable.isBaby() ? BABY_MODEL : MODEL;
   }

   public ResourceLocation getTextureResource(AppleCowEntity animatable) {
      return textureFor(animatable);
   }

   public ResourceLocation getAnimationResource(AppleCowEntity animatable) {
      return animatable.isBaby() ? BABY_ANIMATION : ANIMATION;
   }

   public static ResourceLocation textureFor(AppleCowEntity entity) {
      if (entity.isBaby()) {
         if (entity instanceof AppleCowEntityVariants.EnchantedGoldenAppleCow) {
            return entity.isSheared() ? ENCHANTED_SHEARED_TEXTURE : ENCHANTED_TEXTURE;
         } else if (entity instanceof AppleCowEntityVariants.GoldenAppleCow) {
            return entity.isSheared() ? GOLDEN_SHEARED_TEXTURE : BABY_GOLDEN_TEXTURE;
         } else {
            return entity.isSheared() ? APPLE_SHEARED_TEXTURE : BABY_APPLE_TEXTURE;
         }
      } else if (entity instanceof AppleCowEntityVariants.EnchantedGoldenAppleCow) {
         return entity.isSheared() ? ENCHANTED_SHEARED_TEXTURE : ENCHANTED_TEXTURE;
      } else if (entity instanceof AppleCowEntityVariants.GoldenAppleCow) {
         return entity.isSheared() ? GOLDEN_SHEARED_TEXTURE : GOLDEN_TEXTURE;
      } else {
         return entity.isSheared() ? APPLE_SHEARED_TEXTURE : APPLE_TEXTURE;
      }
   }
}
