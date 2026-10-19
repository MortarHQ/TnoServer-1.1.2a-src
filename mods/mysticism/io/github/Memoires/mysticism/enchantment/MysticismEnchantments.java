package io.github.Memoires.mysticism.enchantment;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.data.TensuraItemTags;
import io.github.manasmods.tensura.data.TensuraTags.Enchantments;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.ApplyMobEffect;

public class MysticismEnchantments {
   public static final ResourceKey<Enchantment> HOLY_FLAMES = create("holy_flames");
   public static final ResourceKey<Enchantment> BOREAL_FROST = create("boreal_frost");

   public static void bootstrap(BootstrapContext<Enchantment> context) {
      HolderGetter<Item> items = context.lookup(Registries.ITEM);
      HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
      context.register(
         HOLY_FLAMES,
         Enchantment.enchantment(
               Enchantment.definition(
                  items.getOrThrow(TensuraItemTags.HANDHELD_ENCHANTABLE),
                  1,
                  1,
                  Enchantment.constantCost(50),
                  Enchantment.dynamicCost(50, 50),
                  1,
                  new EquipmentSlotGroup[]{EquipmentSlotGroup.MAINHAND}
               )
            )
            .withEffect(EnchantmentEffectComponents.ARMOR_EFFECTIVENESS, new AddValue(LevelBasedValue.perLevel(-0.5F)))
            .withEffect(
               EnchantmentEffectComponents.POST_ATTACK,
               EnchantmentTarget.ATTACKER,
               EnchantmentTarget.VICTIM,
               new ApplyMobEffect(
                  HolderSet.direct(new Holder[]{TensuraMobEffects.getReference(MysticismMobEffects.BRIMSTONE_FLAMES)}),
                  LevelBasedValue.constant(5.0F),
                  LevelBasedValue.constant(5.0F),
                  LevelBasedValue.constant(0.0F),
                  LevelBasedValue.constant(0.0F)
               )
            )
            .exclusiveWith(enchantments.getOrThrow(Enchantments.ENGRAVING_EXCLUSIVE))
            .build(HOLY_FLAMES.location())
      );
      context.register(
         BOREAL_FROST,
         Enchantment.enchantment(
               Enchantment.definition(
                  items.getOrThrow(TensuraItemTags.HANDHELD_ENCHANTABLE),
                  1,
                  1,
                  Enchantment.constantCost(50),
                  Enchantment.dynamicCost(50, 50),
                  1,
                  new EquipmentSlotGroup[]{EquipmentSlotGroup.MAINHAND}
               )
            )
            .withEffect(
               EnchantmentEffectComponents.POST_ATTACK,
               EnchantmentTarget.ATTACKER,
               EnchantmentTarget.VICTIM,
               new ApplyMobEffect(
                  HolderSet.direct(new Holder[]{TensuraMobEffects.getReference(MysticismMobEffects.ETERNAL_PERMAFROST)}),
                  LevelBasedValue.constant(10.0F),
                  LevelBasedValue.constant(10.0F),
                  LevelBasedValue.constant(5.0F),
                  LevelBasedValue.constant(5.0F)
               )
            )
            .exclusiveWith(enchantments.getOrThrow(Enchantments.ENGRAVING_EXCLUSIVE))
            .build(BOREAL_FROST.location())
      );
   }

   public static ResourceKey<Enchantment> create(String name) {
      return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }
}
