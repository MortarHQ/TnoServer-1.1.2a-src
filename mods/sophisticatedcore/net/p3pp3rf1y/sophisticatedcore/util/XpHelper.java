package net.p3pp3rf1y.sophisticatedcore.util;

import net.minecraft.world.entity.player.Player;

public class XpHelper {
   private static final int RATIO = 20;

   private XpHelper() {
   }

   public static float liquidToExperience(int liquid) {
      return liquid / 20.0F;
   }

   public static int experienceToLiquid(float xp) {
      return (int)(xp * 20.0F);
   }

   public static int getExperienceForLevel(int level) {
      if (level == 0) {
         return 0;
      } else if (level > 0 && level < 16) {
         return level * (12 + level * 2) / 2;
      } else {
         return level > 15 && level < 31
            ? (level - 15) * (69 + (level - 15) * 5) / 2 + 315
            : (int)Math.min(2147483647L, (level - 30L) * (215L + (level - 30) * 9L) / 2L + 1395L);
      }
   }

   public static int getExperienceLimitOnLevel(int level) {
      if (level >= 30) {
         return 112 + (level - 30) * 9;
      } else {
         return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
      }
   }

   public static int getLevelForExperience(int experience) {
      int i = 0;
      int xp = getExperienceForLevel(i);

      for (int maxXp = 0; xp <= experience; maxXp = xp) {
         xp = getExperienceForLevel(++i);
         if (xp <= maxXp) {
            break;
         }
      }

      return i - 1;
   }

   public static double getLevelsForExperience(int experience) {
      int baseLevel = getLevelForExperience(experience);
      int remainingExperience = experience - getExperienceForLevel(baseLevel);
      return baseLevel + (double)remainingExperience / getExperienceLimitOnLevel(baseLevel);
   }

   public static int getPlayerTotalExperience(Player player) {
      int experienceForLevel = getExperienceForLevel(player.experienceLevel);
      return (int)Math.min(2147483647L, experienceForLevel + (long)(player.experienceProgress * player.getXpNeededForNextLevel()));
   }
}
