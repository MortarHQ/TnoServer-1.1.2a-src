package com.almostreliable.lootjs.loot.extension;

import com.almostreliable.lootjs.loot.LootConditionList;
import com.almostreliable.lootjs.loot.LootEntryList;
import com.almostreliable.lootjs.loot.LootFunctionList;
import com.almostreliable.lootjs.util.DebugInfo;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public interface LootPoolExtension {
   static LootPoolExtension cast(LootPool pool) {
      return (LootPoolExtension)pool;
   }

   LootPool lootjs$asVanillaPool();

   LootEntryList lootjs$getEntries();

   LootConditionList lootjs$getConditions();

   LootFunctionList lootjs$getFunctions();

   void lootjs$setName(String var1);

   default void lootjs$collectDebugInfo(DebugInfo info) {
      JsonElement rollStr = (JsonElement)NumberProviders.CODEC.encodeStart(JsonOps.INSTANCE, this.lootjs$asVanillaPool().getRolls()).getOrThrow();
      JsonElement bonusStr = (JsonElement)NumberProviders.CODEC.encodeStart(JsonOps.INSTANCE, this.lootjs$asVanillaPool().getBonusRolls()).getOrThrow();
      info.add("% Rolls -> " + rollStr.toString());
      info.add("% Bonus rolls -> " + bonusStr.toString());
      this.lootjs$getEntries().collectDebugInfo(info);
      this.lootjs$getConditions().collectDebugInfo(info);
      this.lootjs$getFunctions().collectDebugInfo(info);
   }

   @HideFromJS
   void lootjs$recompose();
}
