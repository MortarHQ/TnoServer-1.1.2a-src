package com.github.b4ndithelps.tensuraenigmatic.registry;

import com.github.b4ndithelps.tensuraenigmatic.loot.RingOfArcaneSourceLootModifier;
import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class TensuraEnigmaticLoots {
   public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(
      NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "tensuraenigmatic"
   );
   public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ARCANE_SOURCE_RING = LOOT_MODIFIERS.register(
      "ring_of_arcane_source", RingOfArcaneSourceLootModifier.CODEC
   );

   private TensuraEnigmaticLoots() {
   }
}
