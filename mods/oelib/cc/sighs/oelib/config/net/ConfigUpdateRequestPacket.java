package cc.sighs.oelib.config.net;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.config.ConfigManager;
import cc.sighs.oelib.config.ConfigUnit;
import cc.sighs.oelib.config.model.ConfigStorageFormat;
import cc.sighs.oelib.config.util.ConfigIOUtil;
import cc.sighs.oelib.network.api.INetworkContext;
import cc.sighs.oelib.network.api.INetworkPacket;
import cc.sighs.oelib.network.api.NetworkPacket;
import cc.sighs.oelib.network.api.Side;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@NetworkPacket(modId = "oelib", id = "config_update_request", side = Side.SERVER)
public record ConfigUpdateRequestPacket(ResourceLocation configId, String payload, ConfigStorageFormat format, boolean save)
   implements INetworkPacket<ConfigUpdateRequestPacket> {
   @Override
   public void handle(INetworkContext context) {
      context.enqueueWork(() -> {
         ServerPlayer player = context.sender();
         if (player != null) {
            Optional<ConfigUnit<?>> unitOpt = ConfigManager.get(this.configId);
            if (unitOpt.isEmpty()) {
               OELib.LOGGER.warn("Config {} not found for update request", this.configId);
            } else {
               ConfigIOUtil.applyUpdate(unitOpt.get(), player, this.payload, this.format, this.save);
            }
         }
      });
   }
}
