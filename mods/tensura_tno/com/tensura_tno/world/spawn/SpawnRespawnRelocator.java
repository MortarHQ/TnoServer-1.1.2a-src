package com.tensura_tno.world.spawn;

import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

public final class SpawnRespawnRelocator {
   private SpawnRespawnRelocator() {
   }

   public static void onPlayerRespawn(PlayerRespawnEvent event) {
      SpawnRelocator.onPlayerRespawn(event);
   }
}
