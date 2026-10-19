package dev.ftb.mods.ftblibrary.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.config.manager.ConfigManagerClient;
import dev.ftb.mods.ftblibrary.config.ui.ChooseConfigScreen;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record EditConfigChoicePacket(EditConfigChoicePacket.ConfigType configType, String clientConfig, String serverConfig, Component title)
   implements CustomPacketPayload {
   public static final Type<EditConfigChoicePacket> TYPE = new Type(FTBLibrary.rl("edit_config_choice_packet"));
   public static final StreamCodec<RegistryFriendlyByteBuf, EditConfigChoicePacket> STREAM_CODEC = StreamCodec.composite(
      NetworkHelper.enumStreamCodec(EditConfigChoicePacket.ConfigType.class),
      EditConfigChoicePacket::configType,
      ByteBufCodecs.STRING_UTF8,
      EditConfigChoicePacket::clientConfig,
      ByteBufCodecs.STRING_UTF8,
      EditConfigChoicePacket::serverConfig,
      ComponentSerialization.STREAM_CODEC,
      EditConfigChoicePacket::title,
      EditConfigChoicePacket::new
   );

   public Type<EditConfigChoicePacket> type() {
      return TYPE;
   }

   public static EditConfigChoicePacket choose(String clientConfig, String serverConfig, Component title) {
      return new EditConfigChoicePacket(EditConfigChoicePacket.ConfigType.CHOOSE, clientConfig, serverConfig, title);
   }

   public static EditConfigChoicePacket client(String clientConfig) {
      return new EditConfigChoicePacket(EditConfigChoicePacket.ConfigType.CLIENT, clientConfig, "", Component.empty());
   }

   public static EditConfigChoicePacket server(String serverConfig) {
      return new EditConfigChoicePacket(EditConfigChoicePacket.ConfigType.SERVER, "", serverConfig, Component.empty());
   }

   public static void handle(EditConfigChoicePacket message, PacketContext context) {
      switch (message.configType) {
         case CLIENT:
            ConfigManagerClient.editConfig(message.clientConfig);
            break;
         case SERVER:
            if (context.getPlayer().hasPermissions(2)) {
               ConfigManagerClient.editConfig(message.serverConfig());
            }
            break;
         case CHOOSE:
            if (context.getPlayer().hasPermissions(2)) {
               ChooseConfigScreen.open(message);
            } else {
               ConfigManagerClient.editConfig(message.clientConfig());
            }
      }
   }

   public enum ConfigType {
      CLIENT,
      SERVER,
      CHOOSE;
   }
}
