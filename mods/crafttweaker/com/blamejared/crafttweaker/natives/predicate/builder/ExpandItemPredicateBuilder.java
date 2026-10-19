package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Arrays;
import java.util.List;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemCustomDataPredicate;
import net.minecraft.advancements.critereon.ItemDamagePredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import net.minecraft.advancements.critereon.ItemPotionsPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicates;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate.StoredEnchantments;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/ItemPredicateBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.ItemPredicateBuilder")
public final class ExpandItemPredicateBuilder {
   @ZenCodeType.Method
   public static Builder items(Builder internal, Item... items) {
      return internal.of(items);
   }

   @ZenCodeType.Method
   public static Builder items(Builder internal, IItemStack... items) {
      return items(internal, Arrays.stream(items).map(IItemStack::getInternal).map(ItemStack::getItem).toArray(Item[]::new));
   }

   @ZenCodeType.Method
   public static Builder tag(Builder internal, KnownTag<Item> tag) {
      return internal.of(tag.getTagKey());
   }

   @ZenCodeType.Method
   public static Builder amount(Builder internal, Ints amount) {
      return internal.withCount(amount);
   }

   @ZenCodeType.Method
   public static Builder durability(Builder internal, Ints durability) {
      return internal.withSubPredicate(ItemSubPredicates.DAMAGE, ItemDamagePredicate.durability(durability));
   }

   @ZenCodeType.Method
   public static Builder potion(Builder internal, Potion potion) {
      return internal.withSubPredicate(
         ItemSubPredicates.POTIONS, new ItemPotionsPredicate(HolderSet.direct(new Holder[]{Services.REGISTRY.holderOrThrow(Registries.POTION, potion)}))
      );
   }

   @ZenCodeType.Method
   public static Builder customData(Builder internal, MapData nbt) {
      return internal.withSubPredicate(ItemSubPredicates.CUSTOM_DATA, new ItemCustomDataPredicate(new NbtPredicate(nbt.getInternal())));
   }

   @ZenCodeType.Method
   public static Builder customData(Builder internal, IData nbt) {
      return customData(internal, new MapData(nbt.asMap()));
   }

   @ZenCodeType.Method
   public static Builder enchantedWith(Builder internal, EnchantmentPredicate predicate) {
      return internal.withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(List.of(predicate)));
   }

   @ZenCodeType.Method
   public static Builder storingEnchantment(Builder internal, EnchantmentPredicate predicate) {
      return internal.withSubPredicate(ItemSubPredicates.STORED_ENCHANTMENTS, StoredEnchantments.storedEnchantments(List.of(predicate)));
   }

   @ZenCodeType.Method
   public static ItemPredicate build(Builder internal) {
      return internal.build();
   }
}
