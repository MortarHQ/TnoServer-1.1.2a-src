package com.github.wal_bos.moreskills.client.fx.avalon;

import com.github.wal_bos.moreskills.client.fx.ArthurAvalonVfxChannel;
import com.github.wal_bos.moreskills.net.msg.ArthurAvalonVfxData;

public final class AvalonLawSacredPurityVfxClient {
   private static final ArthurAvalonVfxChannel CHANNEL = new ArthurAvalonVfxChannel(
      "avalon_law_sacred_purity", ArthurAvalonVfxChannel.Style.AURA, 255, 255, 235
   );

   private AvalonLawSacredPurityVfxClient() {
   }

   public static void init() {
      CHANNEL.init();
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      CHANNEL.spawn(payload);
   }
}
