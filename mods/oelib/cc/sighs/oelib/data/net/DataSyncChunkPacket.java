package cc.sighs.oelib.data.net;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.data.DataManager;
import cc.sighs.oelib.network.api.INetworkContext;
import cc.sighs.oelib.network.api.INetworkPacket;
import cc.sighs.oelib.network.api.NetworkPacket;
import cc.sighs.oelib.network.api.Side;
import cc.sighs.oelib.util.CodecUtils;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

@NetworkPacket(modId = "oelib", id = "data_sync_chunk", side = Side.BOTH, chunkThreshold = 30000)
public record DataSyncChunkPacket(String dataClassName, byte[] dataBytes) implements INetworkPacket<DataSyncChunkPacket> {
   @Override
   public void handle(INetworkContext context) {
      try {
         String json = new String(this.dataBytes, StandardCharsets.UTF_8);
         Class<?> dataClass = Class.forName(this.dataClassName);
         Optional<Map<ResourceLocation, ?>> dataOpt = CodecUtils.decodeFromJson(dataClass, json);
         if (dataOpt.isPresent()) {
            Map<ResourceLocation, ?> data = dataOpt.get();
            DataManager.updateClientDataRaw(dataClass, data);
            OELib.LOGGER.info("Processed {} {} data entries", data.size(), dataClass.getSimpleName());
         } else {
            OELib.LOGGER.error("Failed to parse JSON data for {}", this.dataClassName);
         }
      } catch (Exception e) {
         OELib.LOGGER.error("Failed to handle data sync packet: {}", e.getMessage(), e);
      }
   }
}
