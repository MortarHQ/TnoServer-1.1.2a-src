package com.github.wal_bos.moreskills.ability.skill.shades.ronova;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;

public final class RonovaConfigAccess {
   private RonovaConfigAccess() {
   }

   public static MoreSkillsConfigs.RonovaConfig cfg() {
      return MoreSkillsConfigs.GRAND.ronova;
   }
}
