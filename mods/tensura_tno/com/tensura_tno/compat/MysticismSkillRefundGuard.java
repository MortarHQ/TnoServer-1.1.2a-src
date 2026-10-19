package com.tensura_tno.compat;

import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

public final class MysticismSkillRefundGuard {
   private static final ThreadLocal<MysticismSkillRefundGuard.Unlock> ACTIVE = new ThreadLocal<>();

   private MysticismSkillRefundGuard() {
   }

   public static <T> T duringUnlock(Object maxAttribute, double maxBefore, Object existence, double currentBefore, Supplier<T> unlock) {
      MysticismSkillRefundGuard.Unlock previous = ACTIVE.get();
      ACTIVE.set(new MysticismSkillRefundGuard.Unlock(maxAttribute, maxBefore, existence, currentBefore));

      try {
         return unlock.get();
      } finally {
         if (previous == null) {
            ACTIVE.remove();
         } else {
            ACTIVE.set(previous);
         }
      }
   }

   public static void refundMaxMagicule(Object target, double current, double requested, DoubleConsumer write) {
      MysticismSkillRefundGuard.Unlock unlock = ACTIVE.get();
      if (unlock != null && unlock.maxAttribute == target) {
         refund(current, requested, unlock.maxBefore, write);
      } else {
         write.accept(requested);
      }
   }

   public static void refundMagicule(Object target, double current, double requested, DoubleConsumer write) {
      MysticismSkillRefundGuard.Unlock unlock = ACTIVE.get();
      if (unlock != null && unlock.existence == target) {
         refund(current, requested, unlock.currentBefore, write);
      } else {
         write.accept(requested);
      }
   }

   private static void refund(double current, double requested, double before, DoubleConsumer write) {
      double allowed = requested > current ? Math.max(current, Math.min(requested, before)) : requested;
      if (allowed != current) {
         write.accept(allowed);
      }
   }

   private record Unlock(Object maxAttribute, double maxBefore, Object existence, double currentBefore) {
   }
}
