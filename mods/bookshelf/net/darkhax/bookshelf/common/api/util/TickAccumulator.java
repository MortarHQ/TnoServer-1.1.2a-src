package net.darkhax.bookshelf.common.api.util;

import net.minecraft.world.level.Level;

public class TickAccumulator {
   private final float defaultValue;
   private float ticks;

   public TickAccumulator(float defaultValue) {
      this.ticks = defaultValue;
      this.defaultValue = defaultValue;
   }

   public void tickUp(Level level) {
      this.tick(level.isClientSide ? level.tickRateManager().tickrate() / 20.0F : 1.0F);
   }

   public void tickDown(Level level) {
      this.tick(-(level.isClientSide ? level.tickRateManager().tickrate() / 20.0F : 1.0F));
   }

   public void tick(float amount) {
      this.ticks += amount;
   }

   public float getTicks() {
      return this.ticks;
   }

   public void setTicks(float ticks) {
      this.ticks = ticks;
   }

   public void reset() {
      this.ticks = this.defaultValue;
   }
}
