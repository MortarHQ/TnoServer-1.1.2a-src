package com.chronodawn.items.shield;

public enum ChronoShieldTier {
   T1(400, 100, 0.15F, false, false),
   T2(600, 60, 0.15F, true, false),
   T3(800, 40, 0.15F, true, true);

   public final int durability;
   public final int axeDisableTicks;
   public final float blockDelaySeconds;
   public final boolean hasSpeedOnBlock;
   public final boolean hasTimeEcho;

   ChronoShieldTier(int durability, int axeDisableTicks, float blockDelaySeconds, boolean hasSpeedOnBlock, boolean hasTimeEcho) {
      this.durability = durability;
      this.axeDisableTicks = axeDisableTicks;
      this.blockDelaySeconds = blockDelaySeconds;
      this.hasSpeedOnBlock = hasSpeedOnBlock;
      this.hasTimeEcho = hasTimeEcho;
   }
}
