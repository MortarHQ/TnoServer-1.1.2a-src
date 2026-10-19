package com.blorb.morerelics.Loot;

import com.blorb.morerelics.MoreRelics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

@EventBusSubscriber
public class ModLootAdder {
   @SubscribeEvent
   private static void onLootTableLoad(LootTableLoadEvent event) {
      if (event.getName().equals(ResourceLocation.fromNamespaceAndPath("minecraft", "chests/buried_treasure"))) {
         event.getTable()
            .addPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     ((Builder)LootItem.lootTableItem((ItemLike)MoreRelics.SLUMBERING_AMULET.get()).when(LootItemRandomChanceCondition.randomChance(0.75F)))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                  )
                  .build()
            );
      } else if (event.getName().equals(ResourceLocation.fromNamespaceAndPath("minecraft", "chests/woodland_mansion"))) {
         event.getTable()
            .addPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     ((Builder)LootItem.lootTableItem((ItemLike)MoreRelics.CONVERGING_ORB.get()).when(LootItemRandomChanceCondition.randomChance(0.2F)))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                  )
                  .build()
            );
      } else if (event.getName().equals(ResourceLocation.fromNamespaceAndPath("minecraft", "chests/simple_dungeon"))) {
         event.getTable()
            .addPool(
               LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1.0F))
                  .add(
                     ((Builder)LootItem.lootTableItem((ItemLike)MoreRelics.EPOCH_APPLE.get()).when(LootItemRandomChanceCondition.randomChance(0.1F)))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                  )
                  .build()
            );
      }
   }
}
