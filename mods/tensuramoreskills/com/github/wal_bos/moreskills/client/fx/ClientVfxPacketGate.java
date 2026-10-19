package com.github.wal_bos.moreskills.client.fx;

import dev.architectury.networking.NetworkManager.PacketContext;
import java.util.function.BiConsumer;

public final class ClientVfxPacketGate {
   private ClientVfxPacketGate() {
   }

   public static <T> void run(String id, T payload, PacketContext context, BiConsumer<T, PacketContext> handler) {
      if (ClientVfxGate.allowed(id)) {
         handler.accept(payload, context);
      }
   }
}
