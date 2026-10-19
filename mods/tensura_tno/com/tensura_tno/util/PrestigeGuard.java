package com.tensura_tno.util;

public final class PrestigeGuard {
   public static final ThreadLocal<Boolean> IN_PROGRESS = ThreadLocal.withInitial(() -> false);
   public static final ThreadLocal<Boolean> BLOCKED = ThreadLocal.withInitial(() -> false);

   private PrestigeGuard() {
   }
}
