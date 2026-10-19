package dev.ftb.mods.ftblibrary.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.config.manager.ConfigManagerClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record EditConfigPacket(String configName) implements CustomPacketPayload {
   public static final Type<EditConfigPacket> TYPE = new Type(FTBLibrary.rl("edit_config"));
   public static final StreamCodec<FriendlyByteBuf, EditConfigPacket> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8, EditConfigPacket::configName, EditConfigPacket::new
   );

   public static void handle(EditConfigPacket packet, PacketContext context) {
      context.queue(() -> ConfigManagerClient.editConfig(packet.configName));
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
