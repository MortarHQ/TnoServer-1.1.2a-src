package xaero.map.message.tracker;

import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import xaero.map.WorldMapSession;

public class ClientboundPlayerTrackerResetPacket {
   public void write(FriendlyByteBuf buffer) {
   }

   public static ClientboundPlayerTrackerResetPacket read(FriendlyByteBuf buffer) {
      return new ClientboundPlayerTrackerResetPacket();
   }

   public static class Handler implements Consumer<ClientboundPlayerTrackerResetPacket> {
      public void accept(ClientboundPlayerTrackerResetPacket t) {
         WorldMapSession session = WorldMapSession.getCurrentSession();
         if (session != null) {
            session.getMapProcessor().getClientSyncedTrackedPlayerManager().reset();
         }
      }
   }
}
