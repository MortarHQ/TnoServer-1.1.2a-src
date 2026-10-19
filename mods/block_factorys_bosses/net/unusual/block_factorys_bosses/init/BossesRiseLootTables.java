package net.unusual.block_factorys_bosses.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.unusual.block_factorys_bosses.BossesRise;

public class BossesRiseLootTables {
   public static final ResourceKey<LootTable> ANCIENT_TRIAL_KEY = ResourceKey.create(Registries.LOOT_TABLE, BossesRise.prefix("spawners/ancient_trial_key"));
}
