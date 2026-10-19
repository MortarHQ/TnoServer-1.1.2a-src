package io.github.Memoires.mysticism.registry.attributes;

import dev.architectury.registry.registries.DeferredRegister;
import io.github.Memoires.mysticism.bridge.MysticismAttributeRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attribute.Sentiment;

public class MysticismAttributes {
   private static final DeferredRegister<Attribute> Attributes = DeferredRegister.create("mysticism", Registries.ATTRIBUTE);
   public static final Holder<Attribute> MAX_SOUL_ENERGY = MysticismAttributeRegister.registerGenericAttribute(
      "mysticism", "max_soul_energy", "mysticism.attribute.max_soul_energy", 50.0, 50.0, 2.0E9, true, Sentiment.NEUTRAL
   );
   public static final Holder<Attribute> COLD_BOOST = MysticismAttributeRegister.registerGenericAttribute(
      "mysticism", "cold_boost", "mysticism.attribute.cold_boost", 1.0, 0.0, 1024.0, false, Sentiment.POSITIVE
   );
   public static final Holder<Attribute> COLD_RESIST_DEGRADATION = MysticismAttributeRegister.registerGenericAttribute(
      "mysticism", "cold_resist_degradation", "mysticism.attribute.cold_resist_degradation", 0.0, 0.0, 1.0, false, Sentiment.POSITIVE
   );

   public static void init() {
      Attributes.register();
   }
}
