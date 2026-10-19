package com.natamus.collective_common_fabric.functions;

import net.minecraft.class_1657;
import net.minecraft.class_2663;
import net.minecraft.class_3222;

public class ExperienceFunctions {
   public static boolean canConsumeXp(class_1657 ep, int xp) {
      return ep.method_7337() ? true : xp <= 0 || getPlayerXP(ep) >= xp;
   }

   public static void consumeXp(class_1657 ep, int xp) {
      if (xp > 0) {
         int playerXP = getPlayerXP(ep);
         if (playerXP >= xp) {
            addPlayerXP(ep, -xp);
         }

         if (ep instanceof class_3222) {
            ((class_3222)ep).field_13987.method_14364(new class_2663(ep, (byte)9));
         }
      }
   }

   public static int getPlayerXP(class_1657 player) {
      return (int)(getExperienceForLevel(player.field_7520) + player.field_7510 * player.method_7349());
   }

   public static void addPlayerXP(class_1657 player, int amount) {
      int experience = getPlayerXP(player) + amount;
      player.field_7495 = experience;
      player.field_7520 = getLevelForExperience(experience);
      int expForLevel = getExperienceForLevel(player.field_7520);
      player.field_7510 = (float)(experience - expForLevel) / player.method_7349();
   }

   private static int sum(int n, int a0, int d) {
      return n * (2 * a0 + (n - 1) * d) / 2;
   }

   public static int getLevelForExperience(int targetXp) {
      int level = 0;

      while (true) {
         int xpToNextLevel = xpBarCap(level);
         if (targetXp < xpToNextLevel) {
            return level;
         }

         level++;
         targetXp -= xpToNextLevel;
      }
   }

   public static int xpBarCap(int level) {
      if (level >= 30) {
         return 112 + (level - 30) * 9;
      } else {
         return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
      }
   }

   public static int getExperienceForLevel(int level) {
      if (level == 0) {
         return 0;
      } else if (level <= 15) {
         return sum(level, 7, 2);
      } else {
         return level <= 30 ? 315 + sum(level - 15, 37, 5) : 1395 + sum(level - 30, 112, 9);
      }
   }
}
