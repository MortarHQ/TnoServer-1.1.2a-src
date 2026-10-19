package com.github.wal_bos.tensuramorecult;

import java.util.Map;

public final class CultBalance {
   public static final Map<String, CultBalance.Profile> MOBS = Map.ofEntries(
      Map.entry("azazel", new CultBalance.Profile(1.0, 1.0, 1000000, 10800)),
      Map.entry("azazel_human", new CultBalance.Profile(1.0, 1.0, 2000000, 18000)),
      Map.entry("guardian", new CultBalance.Profile(5.0, 4.0, 9000, 2600)),
      Map.entry("bell_guardian", new CultBalance.Profile(5.0, 5.0, 9500, 3600)),
      Map.entry("manipulator", new CultBalance.Profile(4.0, 4.0, 8000, 2400)),
      Map.entry("welcomer", new CultBalance.Profile(4.0, 4.0, 7500, 2200)),
      Map.entry("statue_bossunit", new CultBalance.Profile(4.0, 4.0, 7000, 1400)),
      Map.entry("statue_entity", new CultBalance.Profile(3.0, 3.0, 4500, 650)),
      Map.entry("gilded_golem", new CultBalance.Profile(4.0, 3.0, 6000, 1000)),
      Map.entry("believer", new CultBalance.Profile(3.0, 2.0, 2500, 300)),
      Map.entry("believer_villager", new CultBalance.Profile(2.0, 2.0, 1800, 240)),
      Map.entry("trader", new CultBalance.Profile(2.0, 2.0, 1500, 200)),
      Map.entry("doctor", new CultBalance.Profile(2.0, 2.0, 1800, 240)),
      Map.entry("blacksmith", new CultBalance.Profile(2.0, 2.0, 2000, 300)),
      Map.entry("villager_prisoner", new CultBalance.Profile(2.0, 2.0, 700, 120)),
      Map.entry("piglin_prisoner", new CultBalance.Profile(2.0, 2.0, 900, 160)),
      Map.entry("ghastly", new CultBalance.Profile(2.0, 2.0, 1200, 180))
   );

   public static float rescaleHealth(float health, float oldMaximum, float newMaximum) {
      return !(health <= 0.0F) && !(oldMaximum <= 0.0F) ? Math.min(newMaximum, health / oldMaximum * newMaximum) : health;
   }

   public static float scriptedDamage(float amount, double attack, boolean human) {
      return (float)(amount * attack / (human ? 20.0 : 5.0));
   }

   private CultBalance() {
   }

   public record Profile(double healthMultiplier, double damageMultiplier, int ep, int spiritualHealth) {
   }
}
