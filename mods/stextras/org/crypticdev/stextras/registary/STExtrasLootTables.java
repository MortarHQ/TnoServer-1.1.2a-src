package org.crypticdev.stextras.registary;

import dev.architectury.event.events.common.LootEvent;
import dev.architectury.event.events.common.LootEvent.ModifyLootTable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.crypticdev.stextras.registary.item.STExtrasItems;

public final class STExtrasLootTables {
   private static final ResourceLocation RIMURU_OGRE_FIGHT_LOOT_TABLE = ResourceLocation.fromNamespaceAndPath("tensura_neb", "entities/rimuru_ogre_fight");

   private STExtrasLootTables() {
   }

   public static void init() {
      LootEvent.MODIFY_LOOT_TABLE
         .register(
            (ModifyLootTable)(key, context, builtin) -> {
               if (builtin && RIMURU_OGRE_FIGHT_LOOT_TABLE.equals(key.location())) {
                  context.addPool(
                     LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem((ItemLike)STExtrasItems.RIMURU_KATANA.get()).when(LootItemRandomChanceCondition.randomChance(0.15F)))
                  );
               }
            }
         );
   }
}
