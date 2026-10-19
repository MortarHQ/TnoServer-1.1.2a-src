package com.chen1335.curioEnchantment.data;

import com.chen1335.curioEnchantment.CurioEnchantment;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

public class LootModifiers extends GlobalLootModifierProvider {
   public LootModifiers(PackOutput output, CompletableFuture<Provider> registries, String modid) {
      super(output, registries, modid);
   }

   protected void start() {
      this.add(
         "entities/dead_king",
         new AddTableLootModifier(
            new LootItemCondition[]{LootTableIdCondition.builder(ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "entities/dead_king")).build()},
            LootTables.LootModifierLoot.DEAD_KING_LOOT_MODIFIER
         ),
         new ICondition[0]
      );
   }

   private static ResourceKey<LootTable> key(String pName) {
      return ResourceKey.create(Registries.LOOT_TABLE, CurioEnchantment.id(pName));
   }
}
