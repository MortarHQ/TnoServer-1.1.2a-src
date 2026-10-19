package com.blamejared.crafttweaker.impl.loot;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

final class CraftTweakerPrivilegedLootModifierEntrySet extends AbstractSet<Entry<ResourceLocation, IGlobalLootModifier>> {
   private final Set<Entry<ResourceLocation, IGlobalLootModifier>> defaultSet;
   private final Set<Entry<ResourceLocation, IGlobalLootModifier>> ctSet;
   private final Predicate<ResourceLocation> ctVerifier;

   private CraftTweakerPrivilegedLootModifierEntrySet(
      Set<Entry<ResourceLocation, IGlobalLootModifier>> defaultSet,
      Set<Entry<ResourceLocation, IGlobalLootModifier>> ctSet,
      Predicate<ResourceLocation> ctVerifier
   ) {
      this.defaultSet = defaultSet;
      this.ctSet = ctSet;
      this.ctVerifier = ctVerifier;
   }

   static Set<Entry<ResourceLocation, IGlobalLootModifier>> wrap(
      Set<Entry<ResourceLocation, IGlobalLootModifier>> defaultSet,
      Set<Entry<ResourceLocation, IGlobalLootModifier>> ctSet,
      Predicate<ResourceLocation> ctVerifier
   ) {
      return new CraftTweakerPrivilegedLootModifierEntrySet(defaultSet, ctSet, ctVerifier);
   }

   public boolean add(Entry<ResourceLocation, IGlobalLootModifier> entry) {
      return this.ctVerifier.test(entry.getKey()) ? this.ctSet.add(entry) : this.defaultSet.add(entry);
   }

   @Override
   public boolean isEmpty() {
      return this.defaultSet.isEmpty() && this.ctSet.isEmpty();
   }

   @Override
   public boolean contains(Object o) {
      return this.defaultSet.contains(o) || this.ctSet.contains(o);
   }

   @Override
   public boolean remove(Object o) {
      return this.defaultSet.remove(o) || this.ctSet.remove(o);
   }

   @Override
   public void clear() {
      this.defaultSet.clear();
      this.ctSet.clear();
   }

   @Override
   public Iterator<Entry<ResourceLocation, IGlobalLootModifier>> iterator() {
      return CraftTweakerPrivilegedLootModifierEntrySetIterator.of(this.defaultSet.iterator(), this.ctSet.iterator());
   }

   @Override
   public int size() {
      return this.defaultSet.size() + this.ctSet.size();
   }
}
