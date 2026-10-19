package team.lodestar.lodestone.registry.common;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.TargetedConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.enchanting.LodestoneSlotBasedEnchantmentAttributeEffect;

public class LodestoneEnchantmentComponents {
   public static final DeferredRegister<DataComponentType<?>> ENCHANTMENT_COMPONENTS = DeferredRegister.create(
      BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, "lodestone"
   );
   public static final Supplier<DataComponentType<List<LodestoneSlotBasedEnchantmentAttributeEffect>>> SLOT_BOUND_ATTRIBUTES = ENCHANTMENT_COMPONENTS.register(
      "slot_bound_attributes", () -> DataComponentType.builder().persistent(LodestoneSlotBasedEnchantmentAttributeEffect.CODEC.codec().listOf()).build()
   );
   public static final LootContextParamSet ENCHANTED_ENTITY = LootContextParamSets.register(
      "lodestone_enchanted_entity",
      builder -> builder.required(LootContextParams.THIS_ENTITY)
         .required(LootContextParams.ENCHANTMENT_LEVEL)
         .required(LootContextParams.ORIGIN)
         .optional(LootContextParams.TOOL)
   );
   public static final LootContextParamSet ENCHANTED_DAMAGE = LootContextParamSets.register(
      "lodestone_enchanted_damage",
      builder -> builder.required(LootContextParams.THIS_ENTITY)
         .required(LootContextParams.ENCHANTMENT_LEVEL)
         .required(LootContextParams.ORIGIN)
         .required(LootContextParams.DAMAGE_SOURCE)
         .optional(LootContextParams.DIRECT_ATTACKING_ENTITY)
         .optional(LootContextParams.ATTACKING_ENTITY)
         .optional(LootContextParams.TOOL)
   );

   public static Supplier<DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> valueEffect(
      DeferredRegister<DataComponentType<?>> registry, String name
   ) {
      return registry.register(
         name,
         () -> DataComponentType.builder()
            .persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf())
            .build()
      );
   }

   public static Supplier<DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>>> entityEffect(
      DeferredRegister<DataComponentType<?>> registry, String name
   ) {
      return registry.register(
         name, () -> DataComponentType.builder().persistent(ConditionalEffect.codec(EnchantmentEntityEffect.CODEC, ENCHANTED_ENTITY).listOf()).build()
      );
   }

   public static Supplier<DataComponentType<List<TargetedConditionalEffect<EnchantmentEntityEffect>>>> targetedEffect(
      DeferredRegister<DataComponentType<?>> registry, String name
   ) {
      return registry.register(
         name, () -> DataComponentType.builder().persistent(TargetedConditionalEffect.codec(EnchantmentEntityEffect.CODEC, ENCHANTED_DAMAGE).listOf()).build()
      );
   }
}
