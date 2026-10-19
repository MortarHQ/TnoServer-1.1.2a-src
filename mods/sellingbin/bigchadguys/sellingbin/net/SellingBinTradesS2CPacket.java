package bigchadguys.sellingbin.net;

import bigchadguys.sellingbin.SellingBinMod;
import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.adapter.basic.SerializableAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import bigchadguys.sellingbin.trade.Trade;
import bigchadguys.sellingbin.world.data.SellingBinData;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public class SellingBinTradesS2CPacket extends ModPacket<ClientPacketListener> {
   public static final Type<SellingBinTradesS2CPacket> ID = new Type(SellingBinMod.id("selling_bin_trades"));
   private final List<Trade> trades;

   public SellingBinTradesS2CPacket() {
      this.trades = new ArrayList<>();
   }

   public SellingBinTradesS2CPacket(List<Trade> trades) {
      this.trades = trades;
   }

   public Type<? extends CustomPacketPayload> type() {
      return ID;
   }

   public void onReceive(ClientPacketListener listener) {
      SellingBinData.CLIENT_TRADES = this.trades;
   }

   @Override
   public void writeBits(BitBuffer buffer) {
      Adapters.INT_SEGMENTED_7.writeBits(this.trades.size(), buffer);
      this.trades.forEach(trade -> {
         JsonElement json = (JsonElement)Adapters.TRADE.writeJson(trade).orElseThrow();
         Adapters.UTF_8.writeBits(json.toString(), buffer);
      });
   }

   @Override
   public void readBits(BitBuffer buffer) {
      this.trades.clear();
      int size = Adapters.INT_SEGMENTED_7.readBits(buffer).orElseThrow();

      for (int i = 0; i < size; i++) {
         JsonElement json = JsonParser.parseString(Adapters.UTF_8.readBits(buffer).orElseThrow());
         this.trades.add(((SerializableAdapter<Trade, ?, JsonElement>)Adapters.TRADE).readJson(json).orElseThrow());
      }
   }
}
