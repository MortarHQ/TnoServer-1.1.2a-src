package com.github.b4ndithelps.tensuraenigmatic.config;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class TensuraEnigmaticConfig {
   public static final ModConfigSpec SPEC;
   public static final IntValue RING_OF_ARCANE_SOURCE_MAGICULE_BONUS;
   public static final DoubleValue RING_OF_ARCANE_SOURCE_DROP_CHANCE;
   public static final IntValue RING_OF_THE_ELVES_ARMOR_BONUS;
   public static final IntValue RING_OF_THE_ELVES_HEALTH_BONUS;
   public static final IntValue RING_OF_THE_ELVES_MAGICULE_BONUS;
   public static final IntValue RING_OF_THE_ELVES_PROTECTION_LEVEL;
   public static final IntValue MANA_RING_RECOVERY_AMOUNT;
   public static final IntValue MANA_RING_RECOVERY_INTERVAL;
   public static final DoubleValue POWER_OF_FORCE_AURA_RECOVERY_PERCENT;
   public static final DoubleValue POWER_OF_FORCE_DROP_CHANCE;
   public static final DoubleValue CURSED_RING_MAGICULE_RECOVERY_PERCENT;

   public static void register() {
      ModLoadingContext.get().getActiveContainer().registerConfig(Type.COMMON, SPEC, "tensura-enigmatic.toml");
   }

   private TensuraEnigmaticConfig() {
   }

   static {
      Builder builder = new Builder();
      builder.push("ring_of_arcane_source");
      RING_OF_ARCANE_SOURCE_MAGICULE_BONUS = builder.defineInRange("magicule_recovery", 150, 0, 10000);
      RING_OF_ARCANE_SOURCE_DROP_CHANCE = builder.defineInRange("drop_chance", 0.05, 0.0, 1.0);
      builder.pop();
      builder.push("ring_of_the_elves");
      RING_OF_THE_ELVES_ARMOR_BONUS = builder.defineInRange("armor_bonus", 10, 0, 100);
      RING_OF_THE_ELVES_HEALTH_BONUS = builder.defineInRange("health_bonus", 20, 0, 1000);
      RING_OF_THE_ELVES_MAGICULE_BONUS = builder.defineInRange("magicule_recovery", 1500, 0, 10000);
      RING_OF_THE_ELVES_PROTECTION_LEVEL = builder.defineInRange("protection_level", 4, 1, 10);
      builder.pop();
      builder.push("mana_ring");
      MANA_RING_RECOVERY_AMOUNT = builder.defineInRange("recovery_amount", 70, 1, 1000);
      MANA_RING_RECOVERY_INTERVAL = builder.defineInRange("recovery_interval", 20, 1, 200);
      builder.pop();
      builder.push("power_of_force");
      POWER_OF_FORCE_AURA_RECOVERY_PERCENT = builder.defineInRange("aura_recovery_percent", 1.0, 0.0, 100.0);
      POWER_OF_FORCE_DROP_CHANCE = builder.defineInRange("drop_chance", 0.5, 0.0, 1.0);
      builder.pop();
      builder.push("cursed_ring");
      CURSED_RING_MAGICULE_RECOVERY_PERCENT = builder.defineInRange("magicule_recovery_percent", 0.4, 0.0, 10.0);
      builder.pop();
      SPEC = builder.build();
   }
}
