package xaero.map.server.radar.tracker;

import net.minecraft.world.entity.player.Player;

public interface ISyncedPlayerTrackerSystem {
   int getTrackingLevel(Player var1, Player var2);

   boolean isPartySystem();
}
