package net.pixeldreamstudios.mythika.entity;

public final class AttackTimeline {
   private final int impactTick;
   private final int lockTicks;
   private final int cooldownTicks;
   private int tick = -1;
   private int lastTick = -1;
   private int cooldown;

   public AttackTimeline(int impactTick, int lockTicks, int cooldownTicks) {
      this.impactTick = impactTick;
      this.lockTicks = lockTicks;
      this.cooldownTicks = cooldownTicks;
   }

   public boolean canStart() {
      return this.tick < 0 && this.cooldown <= 0;
   }

   public void start() {
      this.tick = 0;
      this.cooldown = this.cooldownTicks;
   }

   public void hold(int ticks) {
      this.cooldown = Math.max(this.cooldown, ticks);
   }

   public int currentTick() {
      return this.lastTick;
   }

   public boolean isSwinging() {
      return this.tick >= 0;
   }

   public boolean isLocked() {
      return this.tick >= 0 && this.tick < this.lockTicks;
   }

   public boolean tick() {
      if (this.cooldown > 0) {
         this.cooldown--;
      }

      if (this.tick < 0) {
         this.lastTick = -1;
         return false;
      }

      this.tick++;
      this.lastTick = this.tick;
      boolean impact = this.tick == this.impactTick;
      if (this.tick >= Math.max(this.impactTick, this.lockTicks)) {
         this.tick = -1;
      }

      return impact;
   }
}
