package com.tensurabossstructure.registry;

import com.mojang.serialization.MapCodec;
import com.tensurabossstructure.loot.AddItemLootModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class ModLootModifiers {
   public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(
      Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "tensura_boss_structure"
   );
   public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<AddItemLootModifier>> ADD_ITEM = LOOT_MODIFIERS.register(
      "add_item", () -> AddItemLootModifier.CODEC
   );

   public static void init(IEventBus bus) {
      LOOT_MODIFIERS.register(bus);
   }
}
