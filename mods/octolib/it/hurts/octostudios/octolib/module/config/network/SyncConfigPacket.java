package it.hurts.octostudios.octolib.module.config.network;

import dev.architectury.networking.NetworkManager.PacketContext;
import it.hurts.octostudios.octolib.module.config.ConfigManager;
import it.hurts.octostudios.octolib.module.network.Packet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SyncConfigPacket extends Packet {
   public static final Type<SyncConfigPacket> TYPE = Packet.createType("octolib", "config_sync");
   public static final StreamCodec<RegistryFriendlyByteBuf, SyncConfigPacket> STREAM_CODEC = Packet.createCodec(SyncConfigPacket::write, SyncConfigPacket::new);
   private final String configPath;
   private final String configFile;

   public SyncConfigPacket(RegistryFriendlyByteBuf buf) {
      this.configPath = buf.readUtf();
      this.configFile = buf.readUtf();
   }

   public SyncConfigPacket(String configPath) {
      this.configPath = configPath;
      this.configFile = ConfigManager.saveAsString(configPath);
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeUtf(this.configPath);
      buf.writeUtf(this.configFile);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   protected void handleClient(PacketContext packetContext) {
      ConfigManager.reloadStringConfig(this.configFile, this.configPath, false);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
