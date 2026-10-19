package dev.ftb.mods.ftbchunks.client.map;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.zip.InflaterInputStream;

public class SyncRXTask implements MapTask {
   public final RegionSyncKey key;
   public final byte[] data;
   public final long now;

   public SyncRXTask(RegionSyncKey k, byte[] d) {
      this.key = k;
      this.data = d;
      this.now = System.currentTimeMillis();
   }

   @Override
   public void runMapTask() throws Exception {
      ByteArrayInputStream in = new ByteArrayInputStream(this.data);
      DataInputStream stream = new DataInputStream(new BufferedInputStream(new InflaterInputStream(in)));
      stream.close();
   }

   @Override
   public boolean cancelOtherTasks() {
      return true;
   }
}
