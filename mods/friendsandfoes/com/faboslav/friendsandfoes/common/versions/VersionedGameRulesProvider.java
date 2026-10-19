package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Key;

public final class VersionedGameRulesProvider {
   public static final Key<BooleanValue> SPAWN_MOBS = GameRules.RULE_DOMOBSPAWNING;
   public static final Key<BooleanValue> MOB_GRIEFING = GameRules.RULE_MOBGRIEFING;
   public static final Key<BooleanValue> MOB_DROPS = GameRules.RULE_DOMOBLOOT;

   public static boolean getBoolean(Entity entity, Key<BooleanValue> gameRule) {
      return ((ServerLevel)entity.level()).getGameRules().getBoolean(gameRule);
   }

   public static boolean getBoolean(ServerLevel serverLevel, Key<BooleanValue> gameRule) {
      return serverLevel.getGameRules().getBoolean(gameRule);
   }
}
