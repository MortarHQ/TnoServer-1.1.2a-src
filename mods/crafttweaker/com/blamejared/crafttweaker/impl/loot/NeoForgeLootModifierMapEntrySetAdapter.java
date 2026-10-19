package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

final class NeoForgeLootModifierMapEntrySetAdapter extends AbstractSet<Entry<ResourceLocation, ILootModifier>> {
   private final Set<Entry<ResourceLocation, IGlobalLootModifier>> wrapped;

   private NeoForgeLootModifierMapEntrySetAdapter(Set<Entry<ResourceLocation, IGlobalLootModifier>> wrapped) {
      this.wrapped = wrapped;
   }

   static Set<Entry<ResourceLocation, ILootModifier>> adapt(Set<Entry<ResourceLocation, IGlobalLootModifier>> set) {
      return new NeoForgeLootModifierMapEntrySetAdapter(set);
   }

   public boolean add(Entry<ResourceLocation, ILootModifier> entry) {
      return this.wrapped.add(Map.entry(entry.getKey(), CraftTweakerLootModifierAdapter.adapt(entry.getValue())));
   }

   @Override
   public boolean isEmpty() {
      return this.wrapped.isEmpty();
   }

   @Override
   public boolean contains(Object o) {
      if (o instanceof IGlobalLootModifier) {
         return this.wrapped.contains(o);
      } else {
         return o instanceof NeoForgeLootModifierAdapter wrapper ? this.wrapped.contains(wrapper.modifier()) : super.contains(o);
      }
   }

   @Override
   public boolean remove(Object o) {
      if (o instanceof IGlobalLootModifier) {
         return this.wrapped.remove(o);
      } else {
         return o instanceof NeoForgeLootModifierAdapter wrapper ? this.wrapped.remove(wrapper.modifier()) : super.contains(o);
      }
   }

   @Override
   public void clear() {
      this.wrapped.clear();
   }

   @Override
   public Iterator<Entry<ResourceLocation, ILootModifier>> iterator() {
      return NeoForgeLootModifierIteratorAdapter.adapt(
         this.wrapped.iterator(), entry -> Map.entry(entry.getKey(), NeoForgeLootModifierAdapter.adapt(entry.getValue()))
      );
   }

   @Override
   public int size() {
      return this.wrapped.size();
   }
}
