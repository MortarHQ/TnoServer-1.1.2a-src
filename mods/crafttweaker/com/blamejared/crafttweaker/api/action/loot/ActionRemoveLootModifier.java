package com.blamejared.crafttweaker.api.action.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class ActionRemoveLootModifier extends ActionLootModifier {
   private final String removalDescription;
   private final Predicate<Entry<ResourceLocation, ILootModifier>> predicate;

   public ActionRemoveLootModifier(
      String description, Predicate<Entry<ResourceLocation, ILootModifier>> predicate, Supplier<Map<ResourceLocation, ILootModifier>> mapGetter
   ) {
      super(mapGetter);
      this.removalDescription = description;
      this.predicate = predicate;
   }

   @Override
   public void apply() {
      Map<ResourceLocation, ILootModifier> map = this.modifiersMap();
      map.entrySet().stream().filter(this.predicate).map(Entry::getKey).toList().forEach(map::remove);
   }

   @Override
   public String describe() {
      return this.removalDescription == null ? "Removing all loot modifiers" : "Removing loot modifiers " + this.removalDescription;
   }
}
