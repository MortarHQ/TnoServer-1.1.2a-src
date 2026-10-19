package dev.ftb.mods.ftbchunks.net;

import dev.ftb.mods.ftbchunks.client.ClientTaskQueue;
import dev.ftb.mods.ftbchunks.client.map.RegionSyncKey;
import dev.ftb.mods.ftbchunks.client.map.SyncRXTask;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartialPackets<Key, Packet> {
   public static final PartialPackets<RegionSyncKey, SyncRXPacket> REGION = new PartialPackets<>(
      SyncRXPacket::new, (key, data) -> ClientTaskQueue.queue(new SyncRXTask(key, data))
   );
   private final Map<Key, PartialPackets.PartialData> map = new HashMap<>();
   private final PartialPackets.PacketFactory<Key, Packet> packetFactory;
   private final PartialPackets.Callback<Key> callback;

   public PartialPackets(PartialPackets.PacketFactory<Key, Packet> packetFactory, PartialPackets.Callback<Key> callback) {
      this.packetFactory = packetFactory;
      this.callback = callback;
   }

   public List<Packet> write(Key key, byte[] data) {
      if (data.length <= 30000) {
         return Collections.singletonList(this.packetFactory.create(key, 0, data.length, data));
      }

      List<Packet> list = new ArrayList<>();
      int r = data.length;

      int i;
      for (i = 0; r >= 30000; i += 30000) {
         byte[] data1 = new byte[30000];
         System.arraycopy(data, i, data1, 0, 30000);
         list.add(this.packetFactory.create(key, i, data.length, data1));
         r -= 30000;
      }

      if (r > 0) {
         byte[] data1 = new byte[r];
         System.arraycopy(data, i, data1, 0, r);
         list.add(this.packetFactory.create(key, i, data.length, data1));
      }

      return list;
   }

   public void read(Key key, int offset, int total, byte[] data) {
      PartialPackets.PartialData partialData = this.map.computeIfAbsent(key, s -> new PartialPackets.PartialData(total));
      System.arraycopy(data, 0, partialData.data, offset, data.length);
      partialData.remaining -= data.length;
      if (partialData.remaining == 0) {
         this.callback.accept(key, partialData.data);
         this.map.remove(key);
      } else if (partialData.remaining < 0) {
         throw new RuntimeException("Read more data than required");
      }
   }

   @FunctionalInterface
   public interface Callback<K> {
      void accept(K var1, byte[] var2);
   }

   @FunctionalInterface
   public interface PacketFactory<K, P> {
      P create(K var1, int var2, int var3, byte[] var4);
   }

   public static class PartialData {
      private int remaining;
      private final byte[] data;

      public PartialData(int s) {
         this.remaining = s;
         this.data = new byte[s];
      }
   }
}
