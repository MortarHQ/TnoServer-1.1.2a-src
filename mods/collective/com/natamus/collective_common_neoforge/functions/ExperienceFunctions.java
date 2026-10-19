package com.natamus.collective_common_neoforge.functions;

import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ExperienceFunctions {
   public static boolean canConsumeXp(Player ep, int xp) {
      return ep.isCreative() ? true : xp <= 0 || getPlayerXP(ep) >= xp;
   }

   public static void consumeXp(Player ep, int xp) {
      if (xp > 0) {
         int playerXP = getPlayerXP(ep);
         if (playerXP >= xp) {
            addPlayerXP(ep, -xp);
         }

         if (ep instanceof ServerPlayer) {
            ((ServerPlayer)ep).connection.send(new ClientboundEntityEventPacket(ep, (byte)9));
         }
      }
   }

   public static int getPlayerXP(Player player) {
      return (int)(getExperienceForLevel(player.experienceLevel) + player.experienceProgress * player.getXpNeededForNextLevel());
   }

   public static void addPlayerXP(Player player, int amount) {
      int experience = getPlayerXP(player) + amount;
      player.totalExperience = experience;
      player.experienceLevel = getLevelForExperience(experience);
      int expForLevel = getExperienceForLevel(player.experienceLevel);
      player.experienceProgress = (float)(experience - expForLevel) / player.getXpNeededForNextLevel();
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
