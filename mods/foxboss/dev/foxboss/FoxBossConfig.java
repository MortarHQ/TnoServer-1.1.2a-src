package dev.foxboss;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public final class FoxBossConfig {
   public static final ModConfigSpec SPEC;
   public static final DoubleValue BURST_RANGE;
   public static final DoubleValue STAR_DAMAGE;
   public static final IntValue VOLLEY_INTERVAL;
   public static final IntValue BURST_COOLDOWN_MIN;
   public static final IntValue BURST_COOLDOWN_MAX;
   public static final DoubleValue SATANAEL_COMBAT_WRATH;
   public static final DoubleValue SATANAEL_INJURED_WRATH;
   public static final EnumValue<FoxBossConfig.SpawnReference> NATURAL_SPAWN_REFERENCE;

   private FoxBossConfig() {
   }

   static {
      Builder b = new Builder();
      b.push("milim");
      NATURAL_SPAWN_REFERENCE = b.comment("Natural spawn rarity follows the selected Tensura otherworlder and its current spawn_rate_config values.")
         .defineEnum("naturalSpawnReference", FoxBossConfig.SpawnReference.HINATA);
      BURST_RANGE = b.comment("Dragon Star searches every living target in the forward half-space within this distance.")
         .defineInRange("burstRange", 48.0, 4.0, 96.0);
      STAR_DAMAGE = b.comment("Damage per star hit. Gameplay default, not a canonical Tensura power value.")
         .defineInRange("dragonStarDamage", 300.0, 0.0, 1000000.0);
      VOLLEY_INTERVAL = b.comment("Ticks between volleys during the 4.43-10.8 second release phase.").defineInRange("volleyIntervalTicks", 20, 10, 60);
      BURST_COOLDOWN_MIN = b.comment("Minimum recovery ticks after Dragon Star; default 3 minutes.").defineInRange("burstCooldownMinTicks", 3600, 20, 72000);
      BURST_COOLDOWN_MAX = b.comment("Maximum recovery ticks after Dragon Star; default 5 minutes.").defineInRange("burstCooldownMaxTicks", 6000, 20, 72000);
      SATANAEL_COMBAT_WRATH = b.comment("Satanael wrath gained per second of recent hostile contact by Milim; no gain during Stampede, weakness or cooldown.")
         .defineInRange("satanaelCombatWrathPerSecond", 4.0, 0.0, 100.0);
      SATANAEL_INJURED_WRATH = b.comment("Satanael contact wrath per second while Milim is below half health.")
         .defineInRange("satanaelInjuredWrathPerSecond", 6.0, 0.0, 100.0);
      b.pop();
      SPEC = b.build();
   }

   public enum SpawnReference {
      HINATA,
      SHIZU;
   }
}
