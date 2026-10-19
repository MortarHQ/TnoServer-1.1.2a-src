package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

public final class NeoForgeLootModifierMapAdapter extends AbstractMap<ResourceLocation, ILootModifier> {
   private final Map<ResourceLocation, IGlobalLootModifier> wrapped;

   private NeoForgeLootModifierMapAdapter(Map<ResourceLocation, IGlobalLootModifier> wrapped) {
      this.wrapped = wrapped;
   }

   public static Map<ResourceLocation, ILootModifier> adapt(Map<ResourceLocation, IGlobalLootModifier> map) {
      return new NeoForgeLootModifierMapAdapter(map);
   }

   @Override
   public boolean isEmpty() {
      return this.wrapped.isEmpty();
   }

   @Override
   public int size() {
      return this.wrapped.size();
   }

   @Override
   public boolean containsValue(Object value) {
      if (value instanceof IGlobalLootModifier) {
         return this.wrapped.containsValue(value);
      } else {
         return value instanceof NeoForgeLootModifierAdapter wrapper ? this.wrapped.containsValue(wrapper.modifier()) : super.containsValue(value);
      }
   }

   @Override
   public boolean containsKey(Object key) {
      return this.wrapped.containsKey(key);
   }

   public ILootModifier get(Object key) {
      return NeoForgeLootModifierAdapter.adapt(this.wrapped.get(key));
   }

   public ILootModifier put(ResourceLocation key, ILootModifier value) {
      return NeoForgeLootModifierAdapter.adapt(this.wrapped.put(key, CraftTweakerLootModifierAdapter.adapt(value)));
   }

   public ILootModifier remove(Object key) {
      return NeoForgeLootModifierAdapter.adapt(this.wrapped.remove(key));
   }

   @Override
   public void clear() {
      this.wrapped.clear();
   }

   @Nonnull
   @Override
   public Set<ResourceLocation> keySet() {
      return this.wrapped.keySet();
   }

   @Nonnull
   @Override
   public Collection<ILootModifier> values() {
      return NeoForgeLootModifierValuesCollectionAdapter.adapt(this.wrapped.values());
   }

   @Nonnull
   @Override
   public Set<Entry<ResourceLocation, ILootModifier>> entrySet() {
      return NeoForgeLootModifierMapEntrySetAdapter.adapt(this.wrapped.entrySet());
   }
}
