package com.github.wal_bos.moreskills.client.fx.avalon;

import com.github.wal_bos.moreskills.client.fx.ArthurAvalonVfxChannel;
import com.github.wal_bos.moreskills.net.msg.ArthurAvalonVfxData;

public final class AvalonLawSilentThroneVfxClient {
   private static final ArthurAvalonVfxChannel CHANNEL = new ArthurAvalonVfxChannel(
      "avalon_law_silent_throne", ArthurAvalonVfxChannel.Style.DOMAIN, 230, 194, 92
   );

   private AvalonLawSilentThroneVfxClient() {
   }

   public static void init() {
      CHANNEL.init();
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      CHANNEL.spawn(payload);
   }
}
