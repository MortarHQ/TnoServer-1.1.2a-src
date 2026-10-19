package com.github.wal_bos.moreskills.handler.phainon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.nbt.CompoundTag;

public final class PhainonProgress {
   public static final int SHATTER_REQUIRED_CYCLES = 34;
   private final CompoundTag tag;

   private PhainonProgress(CompoundTag tag) {
      this.tag = tag;
   }

   public static PhainonProgress get(ManasSkillInstance instance) {
      return new PhainonProgress(instance.getOrCreateTag());
   }

   public void setInitialized() {
      this.tag.putBoolean("PhainonInitialized", true);
   }

   public int getCoreMask() {
      return this.tag.getInt("PhainonCoreMask");
   }

   public void setCoreMask(int mask) {
      this.tag.putInt("PhainonCoreMask", mask);
   }

   public boolean has(PhainonProgress.CoreFlame flame) {
      return (this.getCoreMask() & 1 << flame.ordinal()) != 0;
   }

   public boolean grant(PhainonProgress.CoreFlame flame) {
      if (this.has(flame)) {
         return false;
      }

      this.setCoreMask(this.getCoreMask() | 1 << flame.ordinal());
      return true;
   }

   public void clearCores() {
      this.setCoreMask(0);
   }

   public int getCoreFlameCount() {
      return Integer.bitCount(this.getCoreMask());
   }

   public int getProgress(PhainonProgress.CoreFlame flame) {
      return Math.min(this.tag.getInt(flame.key()), flame.max);
   }

   public void setProgress(PhainonProgress.CoreFlame flame, int value) {
      this.tag.putInt(flame.key(), Math.max(0, Math.min(value, flame.max)));
   }

   public boolean add(PhainonProgress.CoreFlame flame, int value) {
      if (this.has(flame)) {
         return false;
      }

      int next = Math.min(this.getProgress(flame) + value, flame.max);
      this.setProgress(flame, next);
      return next >= flame.max && this.grant(flame);
   }

   public int getCycles() {
      return this.tag.getInt("PhainonCycles");
   }

   public void setCycles(int cycles) {
      this.tag.putInt("PhainonCycles", Math.max(0, cycles));
   }

   public void addCycle() {
      this.setCycles(this.getCycles() + 1);
   }

   public int getBlaze() {
      return this.tag.getInt("PhainonBlaze");
   }

   public void setBlaze(int value) {
      this.tag.putInt("PhainonBlaze", Math.max(0, Math.min(8, value)));
   }

   public void addBlaze(int amount) {
      this.setBlaze(this.getBlaze() + amount);
   }

   public boolean consumeBlaze(int amount) {
      if (this.getBlaze() < amount) {
         return false;
      }

      this.setBlaze(this.getBlaze() - amount);
      return true;
   }

   public enum CoreFlame {
      PASSAGE("Passage", "Travel through 100 portals made by Gate or Spatial Motion", 100),
      LAW("Law", "Cleanse 25,000 Cook or Severance damage", 25000),
      TIME("Time", "Keep a temporary buff running for 1 hour", 72000),
      EARTH("Earth", "Take 1,000,000 damage without dying", 1000000),
      OCEAN("Ocean", "Fish 500 times", 500),
      SKY("Sky", "Fly 20,000 blocks with Elytra", 20000),
      WORLDBEARING("Worldbearing", "Have Burden for 15 minutes", 18000),
      REASON("Reason", "Enchant 100 items", 100),
      ROMANCE("Romance", "Have 50 subordinates", 50),
      STRIFE("Strife", "Kill a Tensura boss using only fist, Battlewills, and no toggled skills", 1),
      DEATH("Death", "Kill 10,000 mobs", 10000),
      TRICKERY("Trickery", "Inflict 1,000 debuffs", 1000);

      public final String display;
      public final String requirement;
      public final int max;

      CoreFlame(String display, String requirement, int max) {
         this.display = display;
         this.requirement = requirement;
         this.max = max;
      }

      public String key() {
         return "PhainonProgress" + this.name();
      }
   }
}
