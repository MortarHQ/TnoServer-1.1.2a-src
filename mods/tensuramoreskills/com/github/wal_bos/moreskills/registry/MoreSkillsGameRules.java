package com.github.wal_bos.moreskills.registry;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;

public final class MoreSkillsGameRules {
   public static final Key<BooleanValue> UNIQUE_OWNER_DATA = GameRules.register("tmsUniqueOwnerData", Category.PLAYER, BooleanValue.create(true));
   public static final Key<BooleanValue> FELDWAY_REWARD_ONE_TIME = GameRules.register("tmsFeldwayRewardOneTime", Category.PLAYER, BooleanValue.create(true));

   private MoreSkillsGameRules() {
   }

   public static void init() {
   }
}
