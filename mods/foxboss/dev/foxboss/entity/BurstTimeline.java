package dev.foxboss.entity;

public final class BurstTimeline {
   public static final float CHARGE_START = 24.0F;
   public static final float RELEASE = 88.6F;
   public static final int FIRST_VOLLEY = 89;
   public static final int LAST_VOLLEY = 216;
   public static final int RECOVER = 234;
   public static final int END = 250;

   public static float smooth(float x) {
      x = Math.max(0.0F, Math.min(1.0F, x));
      return x * x * (3.0F - 2.0F * x);
   }

   public static float charge(float tick) {
      return smooth((tick - 24.0F) / 64.6F);
   }

   public static boolean shouldFire(int tick, int interval) {
      return tick >= 89 && tick <= 216 && (tick - 89) % interval == 0;
   }

   public static boolean isInFront(double dx, double dy, double dz, double forwardX, double forwardZ, double range) {
      return dx * dx + dy * dy + dz * dz <= range * range && dx * forwardX + dz * forwardZ > 0.0;
   }

   private BurstTimeline() {
   }
}
