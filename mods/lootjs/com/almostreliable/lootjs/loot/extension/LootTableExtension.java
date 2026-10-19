package com.almostreliable.lootjs.loot.extension;

import com.almostreliable.lootjs.loot.LootFunctionList;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

public interface LootTableExtension {
   static LootTableExtension cast(LootTable table) {
      return (LootTableExtension)table;
   }

   List<LootPool> lootjs$getPools();

   void lootjs$setPools(List<LootPool> var1);

   LootFunctionList lootjs$createFunctionList();

   void lootjs$setRandomSequence(@Nullable ResourceLocation var1);

   @Nullable
   ResourceLocation lootjs$getRandomSequence();

   void lootjs$setParamSet(LootContextParamSet var1);

   LootContextParamSet lootjs$getParamSet();

   @HideFromJS
   void lootjs$recompose();
}
