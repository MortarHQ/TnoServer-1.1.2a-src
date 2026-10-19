package com.b1n_ry.yigd.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public interface YigdTags {
   TagKey<Block> REPLACE_SOFT_WHITELIST = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("yigd", "replace_soft_whitelist"));
   TagKey<Block> KEEP_STRICT_BLACKLIST = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("yigd", "keep_strict_blacklist"));
   TagKey<Block> REPLACE_GRAVE_BLACKLIST = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("yigd", "replace_grave_blacklist"));
   TagKey<Item> NATURAL_SOULBOUND = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("yigd", "natural_soulbound"));
   TagKey<Item> NATURAL_VANISHING = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("yigd", "natural_vanishing"));
   TagKey<Item> LOSS_IMMUNE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("yigd", "loss_immune"));
   TagKey<Item> GRAVE_INCOMPATIBLE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("yigd", "grave_incompatible"));
   TagKey<Enchantment> SOULBOUND = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("yigd", "soulbound"));
   TagKey<Enchantment> VANISHING = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("yigd", "vanishing"));
   TagKey<Enchantment> DEATH_SIGHT = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("yigd", "death_sight"));
}
