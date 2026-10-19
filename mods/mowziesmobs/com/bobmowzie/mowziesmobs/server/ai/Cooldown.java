package com.bobmowzie.mowziesmobs.server.ai;

import net.minecraft.world.entity.Entity;

public class Cooldown {
   private int timer;
   private int durationMin;
   private int durationMax;
   private Entity entity;
   private boolean isPaused = false;
   private final String name;

   public Cooldown(Entity entity, int duration, String name) {
      this.durationMin = duration;
      this.durationMax = duration;
      this.entity = entity;
      this.name = name;
   }

   public Cooldown(Entity entity, int durationMin, int durationMax, String name) {
      this.durationMin = durationMin;
      this.durationMax = durationMax;
      this.entity = entity;
      this.name = name;
   }

   public boolean isFinished() {
      return this.timer <= 0;
   }

   public void finish() {
      this.timer = 0;
   }

   public void tick() {
      if (!this.isPaused) {
         this.timer = Math.max(0, this.timer - 1);
      }
   }

   public int getTimer() {
      return this.timer;
   }

   public void setTimer(int timer) {
      this.timer = timer;
   }

   public void startCooldown() {
      this.timer = this.entity.getRandom().nextInt(this.durationMin, this.durationMax);
   }

   public void startCooldown(int duration) {
      this.timer = duration;
   }

   public void startCooldown(int durationMin, int durationMax) {
      this.timer = this.entity.getRandom().nextInt(durationMin, durationMax);
   }

   public boolean isPaused() {
      return this.isPaused;
   }

   public void paused() {
      this.isPaused = true;
   }

   public void resume() {
      this.isPaused = false;
   }

   public String getName() {
      return this.name;
   }
}
