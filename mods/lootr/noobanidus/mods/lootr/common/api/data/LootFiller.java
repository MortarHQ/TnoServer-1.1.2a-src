package noobanidus.mods.lootr.common.api.data;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface LootFiller {
   void unpackLootTable(@NotNull ILootrInfoProvider var1, @NotNull Player var2, Container var3);

   default void fill(
      ILootrInfoProvider provider,
      Player player,
      ResourceKey<LootTable> lootTableKey,
      LootTable lootTable,
      Container container,
      LootParams parameters,
      long seed
   ) {
      DefaultLootFiller.performFill(provider, player, lootTableKey, lootTable, container, parameters, seed);
   }

   record LootFillerState(
      ILootrInfoProvider provider,
      Player player,
      ResourceKey<LootTable> lootTableKey,
      LootTable lootTable,
      Container container,
      LootParams parameters,
      long seed
   ) {
   }
}
