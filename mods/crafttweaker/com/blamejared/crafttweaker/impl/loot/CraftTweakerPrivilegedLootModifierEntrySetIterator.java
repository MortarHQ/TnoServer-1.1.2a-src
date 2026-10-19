package com.blamejared.crafttweaker.impl.loot;

import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

final class CraftTweakerPrivilegedLootModifierEntrySetIterator implements Iterator<Entry<ResourceLocation, IGlobalLootModifier>> {
   private final Iterator<Entry<ResourceLocation, IGlobalLootModifier>> defaultIterator;
   private final Iterator<Entry<ResourceLocation, IGlobalLootModifier>> ctIterator;
   private boolean targetCt;

   private CraftTweakerPrivilegedLootModifierEntrySetIterator(
      Iterator<Entry<ResourceLocation, IGlobalLootModifier>> defaultIterator, Iterator<Entry<ResourceLocation, IGlobalLootModifier>> ctIterator
   ) {
      this.defaultIterator = defaultIterator;
      this.ctIterator = ctIterator;
      this.targetCt = false;
   }

   static CraftTweakerPrivilegedLootModifierEntrySetIterator of(
      Iterator<Entry<ResourceLocation, IGlobalLootModifier>> defaultIterator, Iterator<Entry<ResourceLocation, IGlobalLootModifier>> ctIterator
   ) {
      return new CraftTweakerPrivilegedLootModifierEntrySetIterator(defaultIterator, ctIterator);
   }

   @Override
   public boolean hasNext() {
      return this.defaultIterator.hasNext() || this.ctIterator.hasNext();
   }

   public Entry<ResourceLocation, IGlobalLootModifier> next() {
      return !this.targetCt && !(this.targetCt = !this.defaultIterator.hasNext()) ? this.defaultIterator.next() : this.ctIterator.next();
   }

   @Override
   public void remove() {
      if (this.targetCt) {
         this.ctIterator.remove();
      } else {
         this.defaultIterator.remove();
      }
   }
}
