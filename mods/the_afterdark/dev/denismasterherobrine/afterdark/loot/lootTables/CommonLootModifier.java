package dev.denismasterherobrine.afterdark.loot.lootTables;

import dev.denismasterherobrine.afterdark.Config;
import dev.denismasterherobrine.afterdark.registry.AfterdarkRegistry;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootPool.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class CommonLootModifier {
   private static final List<ResourceLocation> lootTablesIds = List.of(
      ResourceLocation.fromNamespaceAndPath("minecraft", "chests/abandoned_mineshaft"),
      ResourceLocation.fromNamespaceAndPath("minecraft", "chests/ancient_city"),
      ResourceLocation.fromNamespaceAndPath("minecraft", "chests/buried_treasure"),
      ResourceLocation.fromNamespaceAndPath("minecraft", "chests/end_city_treasure"),
      ResourceLocation.fromNamespaceAndPath("minecraft", "chests/ruined_portal"),
      ResourceLocation.fromNamespaceAndPath("minecraft", "chests/nether_bridge")
   );

   public static Builder createLootPool() {
      return LootPool.lootPool()
         .setRolls(ConstantValue.exactly(Config.INSTANCE.shouldSpawnCatalyst ? 1.0F : 0.0F))
         .when(LootItemRandomChanceCondition.randomChance(Config.INSTANCE.catalystSpawnChance))
         .add(LootItem.lootTableItem(AfterdarkRegistry.TELEPORT_CATALYST_ITEM))
         .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)));
   }

   public static boolean isTargetLootTable(ResourceLocation id) {
      for (String lootTable : List.of(Config.INSTANCE.lootTables)) {
         if (id.toString().equals(lootTable)) {
            return true;
         }
      }

      return false;
   }
}
