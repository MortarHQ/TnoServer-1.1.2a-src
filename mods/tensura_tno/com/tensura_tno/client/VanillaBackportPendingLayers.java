package com.tensura_tno.client;

import com.tensura_tno.TensuraTNOCompatConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class VanillaBackportPendingLayers {
   private static final List<VanillaBackportPendingLayers.PendingLayerRegistration> pendingRegistrations = new ArrayList<>();

   private VanillaBackportPendingLayers() {
   }

   public static void enqueue(Supplier<?> supplier, Consumer<Object> consumer) {
      pendingRegistrations.add(new VanillaBackportPendingLayers.PendingLayerRegistration(supplier, consumer));
   }

   public static void flush() {
      if (TensuraTNOCompatConfig.isVanillaBackportDelayedSheepLayerEnabled()) {
         if (!pendingRegistrations.isEmpty()) {
            int index = 0;

            while (index < pendingRegistrations.size()) {
               VanillaBackportPendingLayers.PendingLayerRegistration pending = pendingRegistrations.get(index);
               if (pending.tryApply()) {
                  pendingRegistrations.remove(index);
               } else {
                  index++;
               }
            }
         }
      }
   }

   public static boolean isConfigNotLoaded(Throwable throwable) {
      for (Throwable current = throwable; current != null; current = current.getCause()) {
         if (current instanceof IllegalStateException
            && current.getMessage() != null
            && current.getMessage().contains("Cannot get config value before config is loaded")) {
            return true;
         }
      }

      return false;
   }

   private record PendingLayerRegistration(Supplier<?> supplier, Consumer<Object> consumer) {
      private boolean tryApply() {
         Object layer;
         try {
            layer = this.supplier.get();
         } catch (RuntimeException exception) {
            if (VanillaBackportPendingLayers.isConfigNotLoaded(exception)) {
               return false;
            }

            throw exception;
         }

         if (layer != null) {
            this.consumer.accept(layer);
         }

         return true;
      }
   }
}
