package com.github.wal_bos.moreskills.client.fx;

public final class MichaelVfxClients {
   private MichaelVfxClients() {
   }

   public static void init() {
      MichaelAuraVfxClient.init();
      MichaelSegvaVfxClient.init();
      MichaelLataibBarrageVfxClient.init();
      MichaelHalberdShotVfxClient.init();
      MichaelShifuruMeteorVfxClient.init();
      MichaelJerezVfxClient.init();
      MichaelKeterVfxClient.init();
   }
}
