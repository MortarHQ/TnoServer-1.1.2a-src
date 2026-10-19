package bigchadguys.sellingbin.net;

import bigchadguys.sellingbin.SellingBinMod;
import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import bigchadguys.sellingbin.world.data.SellingBinData;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public class SellingBinEntriesS2CPacket extends ModPacket<ClientPacketListener> {
   public static final Type<SellingBinEntriesS2CPacket> ID = new Type(SellingBinMod.id("selling_bin_entries"));
   private final Map<String, Integer> timeLeft;

   public SellingBinEntriesS2CPacket() {
      this.timeLeft = new HashMap<>();
   }

   public SellingBinEntriesS2CPacket(Map<String, Integer> timeLeft) {
      this.timeLeft = timeLeft;
   }

   public Type<? extends CustomPacketPayload> type() {
      return ID;
   }

   public void onReceive(ClientPacketListener listener) {
      SellingBinData.CLIENT_TIME_LEFT = this.timeLeft;
   }

   @Override
   public void writeBits(BitBuffer buffer) {
      Adapters.INT_SEGMENTED_3.writeBits(this.timeLeft.size(), buffer);
      this.timeLeft.forEach((id, time) -> {
         Adapters.UTF_8.writeBits(id, buffer);
         Adapters.INT.writeBits(time, buffer);
      });
   }

   @Override
   public void readBits(BitBuffer buffer) {
      this.timeLeft.clear();
      int size = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();

      for (int i = 0; i < size; i++) {
         this.timeLeft.put(Adapters.UTF_8.readBits(buffer).orElseThrow(), Adapters.INT.readBits(buffer).orElseThrow());
      }
   }
}
