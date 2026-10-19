package io.github.Memoires.mysticism.client.model;

import io.github.Memoires.mysticism.entity.skill.SentryEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SentryModel extends GeoModel<SentryEntity> {
   public ResourceLocation getModelResource(SentryEntity sentryEntity) {
      return switch (sentryEntity.getSentryLevel()) {
         case 2 -> ResourceLocation.fromNamespaceAndPath("mysticism", "geo/sentry/sentry_2.geo.json");
         case 3 -> ResourceLocation.fromNamespaceAndPath("mysticism", "geo/sentry/sentry_3.geo.json");
         default -> ResourceLocation.fromNamespaceAndPath("mysticism", "geo/sentry/sentry_1.geo.json");
      };
   }

   public ResourceLocation getTextureResource(SentryEntity sentryEntity) {
      return switch (sentryEntity.getSentryLevel()) {
         case 2 -> ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/sentry/sentry_2.png");
         case 3 -> ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/sentry/sentry_3.png");
         default -> ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/sentry/sentry_1.png");
      };
   }

   public ResourceLocation getAnimationResource(SentryEntity sentryEntity) {
      return switch (sentryEntity.getSentryLevel()) {
         case 2 -> ResourceLocation.fromNamespaceAndPath("mysticism", "animations/sentry/sentry_2.anim.json");
         case 3 -> ResourceLocation.fromNamespaceAndPath("mysticism", "animations/sentry/sentry_3.anim.json");
         default -> ResourceLocation.fromNamespaceAndPath("mysticism", "animations/sentry/sentry_1.anim.json");
      };
   }

   public ResourceLocation getEmissiveTexture(SentryEntity sentryEntity) {
      return switch (sentryEntity.getSentryLevel()) {
         case 2 -> ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/sentry/sentry_2_glowmask.png");
         case 3 -> ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/sentry/sentry_3_glowmask.png");
         default -> ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/sentry/sentry_1_glowmask.png");
      };
   }
}
