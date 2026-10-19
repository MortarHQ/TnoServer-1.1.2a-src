package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.net.msg.GreedCasinoResultPayload;

public final class GreedCasinoClientState {
   private static GreedCasinoResultPayload last;

   private GreedCasinoClientState() {
   }

   public static void setLast(GreedCasinoResultPayload payload) {
      last = payload;
   }

   public static GreedCasinoResultPayload getLast() {
      return last;
   }

   public static void clear() {
      last = null;
   }
}
