package it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.data;

import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;

public class RECLootEntries {
   public static final LootEntry CURSED_PYRAMID = LootEntry.builder()
      .dimension(new String[]{"minecraft:overworld"})
      .biome(new String[]{".*"})
      .table(new String[]{"cataclysm:archaeology\\/cursed_pyramid(_necklace)?"})
      .weight(500)
      .build();
   public static final LootEntry FROSTED_PRISON = LootEntry.builder()
      .dimension(new String[]{"minecraft:overworld"})
      .biome(new String[]{".*"})
      .table(new String[]{"cataclysm:chests/frosted_prison_treasure"})
      .weight(500)
      .build();
}
