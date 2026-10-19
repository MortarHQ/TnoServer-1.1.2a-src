package org.crypticdev.ancient_artifacts.platform.neoforge;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute.Sentiment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class PlatformAttributesImpl {
   private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, "ancient_artifacts");

   private PlatformAttributesImpl() {
   }

   public static Holder<Attribute> register(String id, String name, double amount, double min, double max, boolean syncable, Sentiment sentiment) {
      return ATTRIBUTES.register(id, () -> new RangedAttribute(name, amount, min, max).setSyncable(syncable).setSentiment(sentiment));
   }

   public static void init(IEventBus iEventBus) {
      ATTRIBUTES.register(iEventBus);
   }
}
