package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

final class NeoForgeLootModifierValuesCollectionAdapter extends AbstractCollection<ILootModifier> {
   private final Collection<IGlobalLootModifier> wrapped;

   private NeoForgeLootModifierValuesCollectionAdapter(Collection<IGlobalLootModifier> wrapped) {
      this.wrapped = wrapped;
   }

   static Collection<ILootModifier> adapt(Collection<IGlobalLootModifier> wrapped) {
      return new NeoForgeLootModifierValuesCollectionAdapter(wrapped);
   }

   @Override
   public Iterator<ILootModifier> iterator() {
      return NeoForgeLootModifierIteratorAdapter.adapt(this.wrapped.iterator(), NeoForgeLootModifierAdapter::adapt);
   }

   @Override
   public int size() {
      return this.wrapped.size();
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

   public boolean add(ILootModifier lootModifier) {
      return this.wrapped.add(CraftTweakerLootModifierAdapter.adapt(lootModifier));
   }

   @Override
   public boolean remove(Object o) {
      if (o instanceof IGlobalLootModifier) {
         return this.wrapped.remove(o);
      } else {
         return o instanceof NeoForgeLootModifierAdapter wrapper ? this.wrapped.remove(wrapper.modifier()) : super.remove(o);
      }
   }

   @Override
   public void clear() {
      this.wrapped.clear();
   }
}
