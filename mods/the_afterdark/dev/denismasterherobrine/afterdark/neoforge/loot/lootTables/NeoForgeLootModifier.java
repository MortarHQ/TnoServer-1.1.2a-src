package dev.denismasterherobrine.afterdark.neoforge.loot.lootTables;

import dev.denismasterherobrine.afterdark.loot.lootTables.CommonLootModifier;
import net.minecraft.world.level.storage.loot.LootPool.Builder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;

public class NeoForgeLootModifier {
   @SubscribeEvent
   public static void onLootTableLoad(LootTableLoadEvent event) {
      if (event.getTable() != null && CommonLootModifier.isTargetLootTable(event.getName())) {
         Builder poolBuilder = CommonLootModifier.createLootPool();
         event.getTable().addPool(poolBuilder.build());
      }
   }
}
