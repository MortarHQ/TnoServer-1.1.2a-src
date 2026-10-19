package com.tensura_tno.compat.stextras;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public final class STExtrasConsumptionCompat {
   private static final ThreadLocal<ServerPlayer> REPLACING = new ThreadLocal<>();

   public static ItemStack finishUsing(ServerPlayer player, ItemStack stack, Supplier<ItemStack> operation) {
      Method event = STExtrasConsumptionCompat.Legacy.FIRE_CONSUME;
      if (event != null && !stack.isEmpty()) {
         ItemStack consumed = stack.copyWithCount(1);
         return finishAndReport(REPLACING, player, operation, () -> {
            try {
               event.invoke(null, player, consumed);
            } catch (ReflectiveOperationException e) {
               TensuraTNOMod.LOGGER.warn("[TNO] Failed to report legacy STExtras consumption", e);
            }
         });
      } else {
         return operation.get();
      }
   }

   static <P, R> R finishAndReport(ThreadLocal<P> replacing, P actor, Supplier<R> operation, Runnable report) {
      P previous = replacing.get();
      replacing.set(actor);

      R result;
      try {
         result = operation.get();
      } finally {
         restore(replacing, previous);
      }

      replacing.remove();

      try {
         report.run();
      } finally {
         restore(replacing, previous);
      }

      return result;
   }

   public static boolean isReplacing(ServerPlayer player) {
      return REPLACING.get() == player;
   }

   private static <P> void restore(ThreadLocal<P> replacing, P previous) {
      if (previous == null) {
         replacing.remove();
      } else {
         replacing.set(previous);
      }
   }

   private STExtrasConsumptionCompat() {
   }

   private static final class Legacy {
      private static final Method FIRE_CONSUME = findEvent();

      private static Method findEvent() {
         try {
            Class<?> events = Class.forName("org.crypticdev.stextras.event.STPlayerEvents", false, STExtrasConsumptionCompat.class.getClassLoader());
            return events.getMethod("fireConsume", ServerPlayer.class, ItemStack.class);
         } catch (ClassNotFoundException | NoSuchMethodException | LinkageError ignored) {
            return null;
         }
      }
   }
}
