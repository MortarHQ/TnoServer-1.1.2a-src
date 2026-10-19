package com.github.wal_bos.moreskills.client.fx.avalon;

import com.github.wal_bos.moreskills.client.fx.ArthurAvalonVfxChannel;
import com.github.wal_bos.moreskills.net.msg.ArthurAvalonVfxData;

public final class AvalonLawClosedPrisonVfxClient {
   private static final ArthurAvalonVfxChannel CHANNEL = new ArthurAvalonVfxChannel(
      "avalon_law_closed_prison", ArthurAvalonVfxChannel.Style.PRISON, 255, 218, 110
   );

   private AvalonLawClosedPrisonVfxClient() {
   }

   public static void init() {
      CHANNEL.init();
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      CHANNEL.spawn(payload);
   }
}
