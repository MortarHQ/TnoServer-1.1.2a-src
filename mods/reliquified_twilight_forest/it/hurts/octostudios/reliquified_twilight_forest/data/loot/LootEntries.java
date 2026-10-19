package it.hurts.octostudios.reliquified_twilight_forest.data.loot;

import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import twilightforest.init.TFDimension;

public class LootEntries {
   public static final LootEntry TWILIGHT = LootEntry.builder()
      .dimension(new String[]{TFDimension.DIMENSION.toString()})
      .biome(new String[]{".*"})
      .table(
         new String[]{
            "twilightforest:troll_vault_with_lamp",
            "twilightforest:troll_vault",
            "twilightforest:troll_garden",
            "twilightforest:stronghold_cache",
            "twilightforest:stronghold_room",
            "twilightforest:labyrinth_vault_jackpot",
            "twilightforest:labyrinth_vault",
            "twilightforest:labyrinth_room",
            "twilightforest:labyrinth_dead_end",
            "twilightforest:hedge_maze",
            "twilightforest:hedge_cloth",
            "twilightforest:tree_cache",
            "[\\w]+:chests\\/[\\w_\\/]*[\\w]+[\\w_\\/]*"
         }
      )
      .weight(500)
      .build();
   public static final LootEntry TREE_CACHE = LootEntry.builder()
      .dimension(new String[]{TFDimension.DIMENSION.toString()})
      .biome(new String[]{".*"})
      .table(new String[]{"twilightforest:tree_cache"})
      .weight(500)
      .build();
   public static final LootEntry HEDGE = LootEntry.builder()
      .dimension(new String[]{TFDimension.DIMENSION.toString()})
      .biome(new String[]{".*"})
      .table(new String[]{"twilightforest:hedge_maze", "twilightforest:hedge_cloth"})
      .weight(500)
      .build();
   public static final LootEntry LABYRINTH = LootEntry.builder()
      .dimension(new String[]{TFDimension.DIMENSION.toString()})
      .biome(new String[]{".*"})
      .table(
         new String[]{
            "twilightforest:labyrinth_vault_jackpot", "twilightforest:labyrinth_vault", "twilightforest:labyrinth_room", "twilightforest:labyrinth_dead_end"
         }
      )
      .weight(500)
      .build();
   public static final LootEntry STRONGHOLD = LootEntry.builder()
      .dimension(new String[]{TFDimension.DIMENSION.toString()})
      .biome(new String[]{".*"})
      .table(new String[]{"twilightforest:stronghold_cache", "twilightforest:stronghold_room"})
      .weight(500)
      .build();
   public static final LootEntry TROLL = LootEntry.builder()
      .dimension(new String[]{TFDimension.DIMENSION.toString()})
      .biome(new String[]{".*"})
      .table(new String[]{"twilightforest:troll_vault_with_lamp", "twilightforest:troll_vault", "twilightforest:troll_garden"})
      .weight(500)
      .build();
   public static final LootEntry DARK_TOWER = LootEntry.builder()
      .dimension(new String[]{TFDimension.DIMENSION.toString()})
      .biome(new String[]{".*"})
      .table(new String[]{"twilightforest:darktower_key", "twilightforest:darktower_cache"})
      .weight(500)
      .build();
}
