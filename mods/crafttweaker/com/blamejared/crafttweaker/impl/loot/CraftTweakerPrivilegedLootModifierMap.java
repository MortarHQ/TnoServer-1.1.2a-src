package com.blamejared.crafttweaker.impl.loot;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

public final class CraftTweakerPrivilegedLootModifierMap extends AbstractMap<ResourceLocation, IGlobalLootModifier> {
   private final Map<ResourceLocation, IGlobalLootModifier> defaultMap;
   private final Map<ResourceLocation, IGlobalLootModifier> ctMap;

   private CraftTweakerPrivilegedLootModifierMap(Map<ResourceLocation, IGlobalLootModifier> clone) {
      if (clone instanceof CraftTweakerPrivilegedLootModifierMap other) {
         this.defaultMap = new HashMap<>(other.defaultMap);
         this.ctMap = new LinkedHashMap<>(other.ctMap);
      } else {
         this.defaultMap = new HashMap<>();
         this.ctMap = new LinkedHashMap<>();
         this.putAll(clone);
      }
   }

   public static Map<ResourceLocation, IGlobalLootModifier> of(Map<ResourceLocation, IGlobalLootModifier> clone) {
      return new CraftTweakerPrivilegedLootModifierMap(clone);
   }

   private static boolean isCtModifier(ResourceLocation key) {
      return "crafttweaker".equals(key.getNamespace());
   }

   @Override
   public int size() {
      return this.defaultMap.size() + this.ctMap.size();
   }

   @Override
   public boolean containsValue(Object value) {
      return this.defaultMap.containsValue(value) || this.ctMap.containsValue(value);
   }

   @Override
   public boolean containsKey(Object key) {
      return this.defaultMap.containsKey(key) || this.ctMap.containsKey(key);
   }

   public IGlobalLootModifier get(Object key) {
      IGlobalLootModifier v = this.defaultMap.get(key);
      return v == null ? this.ctMap.get(key) : v;
   }

   public IGlobalLootModifier put(ResourceLocation key, IGlobalLootModifier value) {
      return isCtModifier(key) ? this.ctMap.put(key, value) : this.defaultMap.put(key, value);
   }

   public IGlobalLootModifier remove(Object key) {
      IGlobalLootModifier v = this.defaultMap.remove(key);
      return v == null ? this.ctMap.remove(key) : v;
   }

   @Override
   public void clear() {
      this.defaultMap.clear();
      this.ctMap.clear();
   }

   @Override
   public Set<Entry<ResourceLocation, IGlobalLootModifier>> entrySet() {
      return CraftTweakerPrivilegedLootModifierEntrySet.wrap(
         this.defaultMap.entrySet(), this.ctMap.entrySet(), CraftTweakerPrivilegedLootModifierMap::isCtModifier
      );
   }
}
