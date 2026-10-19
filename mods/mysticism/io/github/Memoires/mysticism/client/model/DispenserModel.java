package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.skill.DispenserEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DispenserModel extends GeoModel<DispenserEntity> {
   public ResourceLocation getModelResource(DispenserEntity dispenserEntity) {
      return switch (dispenserEntity.getDispenserLevel()) {
         case 2 -> ResourceLocation.fromNamespaceAndPath("mysticism", "geo/dispenser/dispenser_2.geo.json");
         case 3 -> ResourceLocation.fromNamespaceAndPath("mysticism", "geo/dispenser/dispenser_3.geo.json");
         default -> ResourceLocation.fromNamespaceAndPath("mysticism", "geo/dispenser/dispenser_1.geo.json");
      };
   }

   public ResourceLocation getTextureResource(DispenserEntity dispenserEntity) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/dispenser/dispenser.png");
   }

   public ResourceLocation getAnimationResource(DispenserEntity dispenserEntity) {
      return switch (dispenserEntity.getDispenserLevel()) {
         case 2 -> ResourceLocation.fromNamespaceAndPath("mysticism", "animations/dispenser/dispenser_2.animation.json");
         case 3 -> ResourceLocation.fromNamespaceAndPath("mysticism", "animations/dispenser/dispenser_3.animation.json");
         default -> ResourceLocation.fromNamespaceAndPath("mysticism", "animations/dispenser/dispenser_1.animation.json");
      };
   }

   public ResourceLocation getEmissiveTexture(DispenserEntity dispenserEntity) {
      return switch (dispenserEntity.getDispenserLevel()) {
         case 2 -> ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/dispenser/dispenser_2_glowmask.png");
         case 3 -> ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/dispenser/dispenser_3_glowmask.png");
         default -> ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/dispenser/dispenser_1_glowmask.png");
      };
   }
}
