package org.crypticdev.ancient_artifacts.registry;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attribute.Sentiment;
import org.crypticdev.ancient_artifacts.platform.PlatformAttributes;

public final class ArtifactAttributesRegistry {
   private static final String TRANSLATION_PREFIX = "attribute.name.ancient_artifacts.";
   public static final Holder<Attribute> MAGIC_BULLET_CHARGE_SPEED = register(
      "magic_bullet_charge_speed", 1.0, 0.0, Double.MAX_VALUE, false, Sentiment.POSITIVE
   );
   public static final Holder<Attribute> HELL_FLARE_DAMAGE_MULTIPLIER = register(
      "hell_flare_damage_multiplier", 1.0, 0.0, Double.MAX_VALUE, false, Sentiment.POSITIVE
   );

   private ArtifactAttributesRegistry() {
   }

   public static void init() {
   }

   private static Holder<Attribute> register(String id, double defaultValue, double min, double max, boolean syncable, Sentiment sentiment) {
      return PlatformAttributes.register(id, "attribute.name.ancient_artifacts." + id, defaultValue, min, max, syncable, sentiment);
   }
}
