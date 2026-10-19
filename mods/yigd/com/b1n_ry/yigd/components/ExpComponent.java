package com.b1n_ry.yigd.components;

import com.b1n_ry.yigd.config.YigdConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.phys.Vec3;

public class ExpComponent {
   private final double originalXp;
   private int storedXp;

   public ExpComponent(ServerPlayer player) {
      this.originalXp = this.getTotalExperience(player);
      this.storedXp = this.getXpDropAmount(player);
   }

   private ExpComponent(int storedXp, double originalXp) {
      this.storedXp = storedXp;
      this.originalXp = originalXp;
   }

   public void setStoredXp(int storedXp) {
      this.storedXp = storedXp;
   }

   public int getStoredXp() {
      return this.storedXp;
   }

   public double getOriginalXp() {
      return this.originalXp;
   }

   public int getXpDropAmount(ServerPlayer player) {
      YigdConfig config = YigdConfig.getConfig();
      double totalExperience = this.getTotalExperience(player);
      int percentageDrop = (int)(config.expConfig.dropPercentage / 100.0F * totalExperience);
      int vanillaDrop = player.getExperienceReward(player.serverLevel(), null);

      return switch (config.expConfig.dropBehaviour) {
         case BEST_OF_BOTH -> Math.max(vanillaDrop, percentageDrop);
         case WORST_OF_BOTH -> Math.min(vanillaDrop, percentageDrop);
         case PERCENTAGE -> percentageDrop;
         case VANILLA -> vanillaDrop;
      };
   }

   private double getTotalExperience(ServerPlayer player) {
      int currentLevel = player.experienceLevel;
      double totalExperience;
      if (currentLevel >= 32) {
         totalExperience = 4.5 * Math.pow(currentLevel, 2.0) - 162.5 * currentLevel + 2220.0;
      } else if (currentLevel >= 17) {
         totalExperience = 2.5 * Math.pow(currentLevel, 2.0) - 40.5 * currentLevel + 360.0;
      } else {
         totalExperience = Math.pow(currentLevel, 2.0) + 6 * currentLevel;
      }

      return totalExperience + player.getXpNeededForNextLevel() * player.experienceProgress;
   }

   public int getXpLevel() {
      return xpToLevels(this.storedXp);
   }

   public boolean isEmpty() {
      return this.storedXp == 0;
   }

   public ExpComponent getSoulboundExp() {
      YigdConfig config = YigdConfig.getConfig();
      float soulboundFactor = config.expConfig.keepPercentage / 100.0F;
      int keepXp = (int)(this.originalXp * soulboundFactor);
      return new ExpComponent(keepXp, this.originalXp);
   }

   public void dropAll(ServerLevel world, Vec3 pos) {
      ExperienceOrb.award(world, pos, this.storedXp);
   }

   public void applyToPlayer(ServerPlayer player) {
      player.giveExperiencePoints(this.storedXp);
   }

   public void clear() {
      this.storedXp = 0;
   }

   public CompoundTag toNbt() {
      CompoundTag nbt = new CompoundTag();
      nbt.putInt("value", this.storedXp);
      nbt.putDouble("original", this.originalXp);
      return nbt;
   }

   public static ExpComponent fromNbt(CompoundTag nbt) {
      int xpToDrop = nbt.getInt("value");
      double originalXp = nbt.contains("original") ? nbt.getDouble("original") : xpToDrop;
      return new ExpComponent(xpToDrop, originalXp);
   }

   public static void clearXp(ServerPlayer player) {
      player.totalExperience = 0;
      player.experienceLevel = 0;
      player.experienceProgress = 0.0F;
   }

   public static int xpToLevels(int totalXp) {
      int i;
      for (i = 0; totalXp >= 0; i++) {
         if (i < 16) {
            totalXp -= 2 * i + 7;
         } else if (i < 31) {
            totalXp -= 5 * i - 38;
         } else {
            totalXp -= 9 * i - 158;
         }
      }

      return i - 1;
   }

   public ExpComponent copy() {
      return new ExpComponent(this.storedXp, this.originalXp);
   }
}
