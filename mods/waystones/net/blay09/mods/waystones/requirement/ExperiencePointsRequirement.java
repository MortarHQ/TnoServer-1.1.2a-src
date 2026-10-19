package net.blay09.mods.waystones.requirement;

import java.util.List;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ExperiencePointsRequirement implements WarpRequirement {
   private int points;

   public ExperiencePointsRequirement(int points) {
      this.points = Math.max(0, points);
   }

   @Override
   public boolean canAfford(Player player) {
      int xpForLevel = getCumulativeXpNeededForLevel(player.experienceLevel);
      double xpForProgress = Math.floor(player.experienceProgress * getXpNeededForNextLevel(player.experienceLevel));
      double totalXp = xpForLevel + xpForProgress;
      return totalXp >= this.points;
   }

   @Override
   public void consume(Player player) {
      player.giveExperiencePoints(-this.points);
   }

   @Override
   public void rollback(Player player) {
      player.giveExperiencePoints(this.points);
   }

   @Override
   public void appendHoverText(Player player, List<Component> tooltip) {
      if (this.points > 0) {
         tooltip.add(Component.translatable("gui.waystones.waystone_selection.xp_requirement", new Object[]{this.points}).withStyle(ChatFormatting.GREEN));
      }
   }

   @Override
   public boolean isEmpty() {
      return this.points <= 0;
   }

   public static int calculateLevelCostFromExperiencePoints(int currentLevel, int xpLoss) {
      return currentLevel - calculateLevelMinusExperiencePoints(currentLevel, xpLoss);
   }

   private static int calculateLevelMinusExperiencePoints(int currentLevel, int xpLoss) {
      int currentCumulativeXp = getCumulativeXpNeededForLevel(currentLevel);
      int remainingXp = currentCumulativeXp - xpLoss;
      int newLevel = 0;
      int newCumulativeXp = 0;

      for (int level = 0; level <= currentLevel; level++) {
         newCumulativeXp += getXpNeededForNextLevel(level);
         if (remainingXp < newCumulativeXp) {
            newLevel = level;
            break;
         }
      }

      return newLevel;
   }

   private static int getXpNeededForNextLevel(int level) {
      if (level >= 30) {
         return 112 + (level - 30) * 9;
      } else {
         return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
      }
   }

   private static int getCumulativeXpNeededForLevel(int targetLevel) {
      int currentCumulativeXp = 0;

      for (int level = 0; level < targetLevel; level++) {
         currentCumulativeXp += getXpNeededForNextLevel(level);
      }

      return currentCumulativeXp;
   }

   public void setPoints(int value) {
      this.points = value;
   }

   public int getPoints() {
      return this.points;
   }
}
