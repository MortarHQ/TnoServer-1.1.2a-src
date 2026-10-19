package com.almostreliable.lootjs.core.entry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class DynamicLootEntry extends AbstractSimpleLootEntry<DynamicLoot> {
   public DynamicLootEntry(DynamicLoot vanillaEntry) {
      super(vanillaEntry);
   }

   public ResourceLocation getLocation() {
      return this.vanillaEntry.name;
   }

   public void setLocation(ResourceLocation reference) {
      this.vanillaEntry.name = reference;
   }

   public DynamicLootEntry addCondition(LootItemCondition condition) {
      this.getConditions().add(condition);
      return this;
   }
}
