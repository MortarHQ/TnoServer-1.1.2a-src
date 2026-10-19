package com.tensura_tno.client.compat;

public final class MoreSkillsAsmodayHotPathCache {
   private static final int CACHE_SIZE = 8192;
   private static final int CACHE_MASK = 8191;
   private static final byte SIN_PRESENT = 1;
   private static final byte COS_PRESENT = 2;
   private static final ThreadLocal<MoreSkillsAsmodayHotPathCache.TrigonometryCache> CACHE = ThreadLocal.withInitial(
      MoreSkillsAsmodayHotPathCache.TrigonometryCache::new
   );

   private MoreSkillsAsmodayHotPathCache() {
   }

   public static double sin(double angle) {
      return CACHE.get().sin(angle);
   }

   public static double cos(double angle) {
      return CACHE.get().cos(angle);
   }

   static void resetForTests() {
      CACHE.remove();
   }

   private static int slot(long bits) {
      long mixed = bits;
      mixed ^= mixed >>> 33;
      mixed *= -49064778989728563L;
      mixed ^= mixed >>> 33;
      mixed *= -4265267296055464877L;
      mixed ^= mixed >>> 33;
      return (int)mixed & 8191;
   }

   private static final class TrigonometryCache {
      private final long[] keys = new long[8192];
      private final byte[] present = new byte[8192];
      private final double[] sine = new double[8192];
      private final double[] cosine = new double[8192];

      private double sin(double angle) {
         long bits = Double.doubleToRawLongBits(angle);
         int slot = MoreSkillsAsmodayHotPathCache.slot(bits);
         byte flags = this.present[slot];
         if (flags != 0 && this.keys[slot] == bits && (flags & 1) != 0) {
            return this.sine[slot];
         }

         double result = Math.sin(angle);
         if (flags != 0 && this.keys[slot] == bits) {
            this.present[slot] = (byte)(flags | 1);
         } else {
            this.keys[slot] = bits;
            this.present[slot] = 1;
         }

         this.sine[slot] = result;
         return result;
      }

      private double cos(double angle) {
         long bits = Double.doubleToRawLongBits(angle);
         int slot = MoreSkillsAsmodayHotPathCache.slot(bits);
         byte flags = this.present[slot];
         if (flags != 0 && this.keys[slot] == bits && (flags & 2) != 0) {
            return this.cosine[slot];
         }

         double result = Math.cos(angle);
         if (flags != 0 && this.keys[slot] == bits) {
            this.present[slot] = (byte)(flags | 2);
         } else {
            this.keys[slot] = bits;
            this.present[slot] = 2;
         }

         this.cosine[slot] = result;
         return result;
      }
   }
}
