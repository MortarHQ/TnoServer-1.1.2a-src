package com.almostreliable.lootjs.loot;

import com.almostreliable.lootjs.core.filters.IdFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet.Direct;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.enchantment.Enchantment;

public interface Predicates {
   static Provider lookup() {
      return null;
   }

   static EnchantmentPredicate enchantment(IdFilter filter) {
      return enchantment(filter, Ints.ANY);
   }

   static EnchantmentPredicate enchantment(IdFilter filter, Ints levelBound) {
      List<Reference<Enchantment>> enchantments = lookup()
         .lookupOrThrow(Registries.ENCHANTMENT)
         .listElements()
         .filter(ref -> filter.test(ref.key().location()))
         .toList();
      Direct<Enchantment> holderSet = HolderSet.direct(enchantments);
      return new EnchantmentPredicate(Optional.of(holderSet), levelBound);
   }

   static ItemEnchantmentsPredicate itemEnchantments(EnchantmentPredicate[] predicates) {
      return ItemEnchantmentsPredicate.enchantments(Arrays.asList(predicates));
   }

   static ItemEnchantmentsPredicate storedEnchantments(EnchantmentPredicate[] predicates) {
      return ItemEnchantmentsPredicate.storedEnchantments(Arrays.asList(predicates));
   }

   static NbtPredicate nbt(CompoundTag nbt) {
      return new NbtPredicate(nbt);
   }
}
