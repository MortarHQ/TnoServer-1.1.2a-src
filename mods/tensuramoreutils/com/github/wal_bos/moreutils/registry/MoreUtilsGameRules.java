package com.github.wal_bos.moreutils.registry;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;

public final class MoreUtilsGameRules {
   public static final Key<BooleanValue> ALLOW_NON_ADMIN_LEADERBOARD = GameRules.register(
      "tmuAllowNonAdminLeaderboard", Category.PLAYER, BooleanValue.create(true)
   );
   public static final Key<BooleanValue> UNIQUE_SKILL_CAP = GameRules.register("tmuUniqueCap", Category.PLAYER, BooleanValue.create(false));
   public static final Key<BooleanValue> ULTIMATE_SKILL_CAP = GameRules.register("tmuUltimateCap", Category.PLAYER, BooleanValue.create(false));
   public static final Key<BooleanValue> REBIRTH_CAP_INCREASE = GameRules.register("tmuRebirthCapIncrease", Category.PLAYER, BooleanValue.create(false));

   private MoreUtilsGameRules() {
   }

   public static void init() {
   }
}
