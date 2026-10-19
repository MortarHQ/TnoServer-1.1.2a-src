package com.shin.tensura_kumodesu.registry.attributes;

import com.shin.tensura_kumodesu.bridge.KumodesuAttributeRegister;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attribute.Sentiment;

public class KumodesuAttributes {
   private static final DeferredRegister<Attribute> attributes = DeferredRegister.create("tensura_kumodesu", Registries.ATTRIBUTE);
   public static final Holder<Attribute> EYE_HEIGHT = KumodesuAttributeRegister.registerGenericAttribute(
      "tensura_kumodesu", "eye_height", "tensura_kumodesu.attribute.eye_height", 1.0, 0.0, 1024.0, true, Sentiment.NEUTRAL
   );
   public static final Holder<Attribute> SHADOW_SIZE = KumodesuAttributeRegister.registerGenericAttribute(
      "tensura_kumodesu", "shadow_size", "tensura_kumodesu.attribute.shadow_size", 1.0, 0.0, 1024.0, true, Sentiment.NEUTRAL
   );

   public static void init() {
      attributes.register();
   }
}
