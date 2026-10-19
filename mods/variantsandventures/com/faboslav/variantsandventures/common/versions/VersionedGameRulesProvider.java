package com.faboslav.variantsandventures.common.versions;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;

public final class VersionedGameRulesProvider {
   public static GameRules getGameRules(Entity entity) {
      return entity.level().getGameRules();
   }
}
