package io.github.Memoires.mysticism.handler;

import dev.architectury.event.events.common.LootEvent;
import dev.architectury.event.events.common.LootEvent.ModifyLootTable;
import io.github.Memoires.mysticism.registry.item.MysticismMaterialItems;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class LootHandler {
   public static void init() {
      LootEvent.MODIFY_LOOT_TABLE
         .register(
            (ModifyLootTable)(key, context, builtin) -> {
               if (builtin) {
                  if (key.location().toString().equals("tensura:entities/army_wasp") || key.location().toString().equals("tensura:entities/hell_moth")) {
                     context.addPool(
                        LootPool.lootPool()
                           .add(
                              LootItem.lootTableItem((ItemLike)MysticismMobDropItems.CRYPTID_ESSENCE.get())
                                 .when(LootItemRandomChanceCondition.randomChance(0.2F))
                           )
                     );
                  }

                  if (key.location().toString().equals("tensura:entities/ifrit")) {
                     context.addPool(
                        LootPool.lootPool()
                           .add(
                              LootItem.lootTableItem((ItemLike)MysticismMobDropItems.FLAME_ESSENCE.get())
                                 .when(LootItemRandomChanceCondition.randomChance(1.0F))
                           )
                     );
                  }

                  if (key.location().toString().equals("tensura:entities/undine")) {
                     context.addPool(
                        LootPool.lootPool()
                           .add(
                              LootItem.lootTableItem((ItemLike)MysticismMobDropItems.ICE_ESSENCE.get()).when(LootItemRandomChanceCondition.randomChance(1.0F))
                           )
                     );
                  }

                  if (key.location().toString().equals("tensura:entities/sylphide")) {
                     context.addPool(
                        LootPool.lootPool()
                           .add(
                              LootItem.lootTableItem((ItemLike)MysticismMobDropItems.LIGHTNING_ESSENCE.get())
                                 .when(LootItemRandomChanceCondition.randomChance(1.0F))
                           )
                     );
                  }

                  if (key.equals(BuiltInLootTables.ANCIENT_CITY)) {
                     context.addPool(
                        LootPool.lootPool()
                           .add(
                              LootItem.lootTableItem((ItemLike)MysticismMaterialItems.DARKNESS_CORE.get())
                                 .when(LootItemRandomChanceCondition.randomChance(0.15F))
                           )
                     );
                  }

                  if (key.location().toString().equals("minecraft:entities/blaze")) {
                     context.addPool(
                        LootPool.lootPool()
                           .add(
                              LootItem.lootTableItem((ItemLike)MysticismMobDropItems.FLAME_ESSENCE.get())
                                 .when(LootItemRandomChanceCondition.randomChance(0.01F))
                           )
                     );
                  }
               }
            }
         );
   }
}
