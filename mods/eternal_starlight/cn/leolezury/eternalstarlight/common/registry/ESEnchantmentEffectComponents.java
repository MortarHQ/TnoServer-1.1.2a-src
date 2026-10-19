package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import java.util.List;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class ESEnchantmentEffectComponents {
   public static final RegistrationProvider<DataComponentType<?>> ENCHANTMENT_EFFECT_COMPONENTS = RegistrationProvider.get(
      BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE.key(), "eternal_starlight"
   );
   public static final RegistryObject<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> BOOMERANG_CRIT_CHANCE = ENCHANTMENT_EFFECT_COMPONENTS.register(
      "boomerang_crit_chance",
      () -> DataComponentType.builder()
         .persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf())
         .build()
   );
   public static final RegistryObject<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> BOOMERANG_HOMING_STRENGTH = ENCHANTMENT_EFFECT_COMPONENTS.register(
      "boomerang_homing_strength",
      () -> DataComponentType.builder().persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf()).build()
   );
   public static final RegistryObject<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> BOOMERANG_PICKUP_RADIUS = ENCHANTMENT_EFFECT_COMPONENTS.register(
      "boomerang_pickup_radius",
      () -> DataComponentType.builder().persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf()).build()
   );

   public static void loadClass() {
   }
}
