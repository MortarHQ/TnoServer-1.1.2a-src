package com.github.wal_bos.moreutils.handler;

import com.github.wal_bos.moreutils.data.MoreUtilsData;
import com.github.wal_bos.moreutils.data.SkillCapType;
import com.github.wal_bos.moreutils.registry.MoreUtilsGameRules;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.server.level.ServerPlayer;

public final class MoreUtilsSkillCap {
   private MoreUtilsSkillCap() {
   }

   public static SkillCapType getCapType(ManasSkillInstance instance) {
      if (!(instance != null && !instance.isTemporarySkill() && instance.getSkill() instanceof Skill skill)) {
         return null;
      } else if (skill.getType() == SkillType.UNIQUE) {
         return SkillCapType.UNIQUE;
      } else {
         return skill.getType() == SkillType.ULTIMATE ? SkillCapType.ULTIMATE : null;
      }
   }

   public static boolean isEnabled(ServerPlayer player, SkillCapType type) {
      return player.level()
         .getGameRules()
         .getBoolean(type == SkillCapType.UNIQUE ? MoreUtilsGameRules.UNIQUE_SKILL_CAP : MoreUtilsGameRules.ULTIMATE_SKILL_CAP);
   }

   public static int count(ServerPlayer player, SkillCapType type) {
      int count = 0;

      for (ManasSkillInstance learned : SkillAPI.getSkillsFrom(player).getLearnedSkills()) {
         if (!learned.isTemporarySkill() && learned.getSkill() instanceof Skill skill && skill.getType() == type.skillType()) {
            count++;
         }
      }

      return count;
   }

   public static void processResetCounter(ServerPlayer player) {
      boolean enabled = player.level().getGameRules().getBoolean(MoreUtilsGameRules.REBIRTH_CAP_INCREASE);
      int resets = Math.max(0, TensuraStorages.getPlayerDataFrom(player).getResetCounter());
      MoreUtilsData.processResetCounter(player.server, player.getUUID(), player.getGameProfile().getName(), resets, enabled);
   }

   public static int effectiveCap(ServerPlayer player, SkillCapType type) {
      Integer personal = MoreUtilsData.getPlayerCap(player.getUUID(), type);
      int base = personal == null ? MoreUtilsData.getGlobalCap(type) : personal;
      return base < 0 ? -1 : base + MoreUtilsData.getResetBonus(player.getUUID(), type);
   }

   public static boolean blocksUnlock(ServerPlayer player, ManasSkillInstance incoming) {
      SkillCapType type = getCapType(incoming);
      if (type != null && isEnabled(player, type)) {
         if (SkillAPI.getSkillsFrom(player).getSkill(incoming.getSkill()).isPresent()) {
            return false;
         }

         int cap = effectiveCap(player, type);
         return cap < 0 ? false : count(player, type) >= cap;
      } else {
         return false;
      }
   }
}
