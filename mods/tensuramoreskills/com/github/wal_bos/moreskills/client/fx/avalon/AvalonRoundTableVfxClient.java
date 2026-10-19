package com.github.wal_bos.moreskills.client.fx.avalon;

import com.github.wal_bos.moreskills.client.fx.ArthurAvalonVfxChannel;
import com.github.wal_bos.moreskills.net.msg.ArthurAvalonVfxData;

public final class AvalonRoundTableVfxClient {
   private static final ArthurAvalonVfxChannel CHANNEL = new ArthurAvalonVfxChannel("avalon_round_table", ArthurAvalonVfxChannel.Style.KNIGHTS, 255, 224, 100);

   private AvalonRoundTableVfxClient() {
   }

   public static void init() {
      CHANNEL.init();
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      CHANNEL.spawn(payload);
   }
}
