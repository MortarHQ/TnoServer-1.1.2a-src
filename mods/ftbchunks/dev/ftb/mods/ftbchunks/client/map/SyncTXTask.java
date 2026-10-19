package dev.ftb.mods.ftbchunks.client.map;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbchunks.net.PartialPackets;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.zip.DeflaterOutputStream;

public class SyncTXTask implements MapTask {
   public final MapRegion region;
   public final long now;

   public SyncTXTask(MapRegion r, long s) {
      this.region = r;
      this.now = s;
   }

   @Override
   public void runMapTask() throws Exception {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(new DeflaterOutputStream(out)));
      stream.close();
      PartialPackets.REGION.write(this.region.getSyncKey(), out.toByteArray()).forEach(NetworkManager::sendToServer);
   }

   @Override
   public boolean cancelOtherTasks() {
      return true;
   }
}
