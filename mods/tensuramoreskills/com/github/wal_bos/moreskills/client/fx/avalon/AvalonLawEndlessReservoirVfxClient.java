package com.github.wal_bos.moreskills.client.fx.avalon;

import com.github.wal_bos.moreskills.client.fx.ArthurAvalonVfxChannel;
import com.github.wal_bos.moreskills.net.msg.ArthurAvalonVfxData;

public final class AvalonLawEndlessReservoirVfxClient {
   private static final ArthurAvalonVfxChannel CHANNEL = new ArthurAvalonVfxChannel(
      "avalon_law_endless_reservoir", ArthurAvalonVfxChannel.Style.RING, 255, 224, 82
   );

   private AvalonLawEndlessReservoirVfxClient() {
   }

   public static void init() {
      CHANNEL.init();
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      CHANNEL.spawn(payload);
   }
}
