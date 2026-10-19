package bigchadguys.sellingbin.init;

import bigchadguys.sellingbin.net.ModPacket;
import bigchadguys.sellingbin.net.SellingBinEntriesS2CPacket;
import bigchadguys.sellingbin.net.SellingBinTradesS2CPacket;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.NetworkManager.Side;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class ModNetwork extends ModRegistries {
   public static void register() {
      if (Platform.getEnvironment() == Env.CLIENT) {
         ModNetwork.Client.register();
      } else {
         ModNetwork.Server.register();
      }
   }

   public static <R extends PacketListener, T extends ModPacket<R>> void register(
      Side side, Type<T> id, Supplier<T> packetSupplier, Function<PacketContext, R> contextMapper
   ) {
      NetworkManager.registerReceiver(side, id, CustomPacketPayload.codec(ModPacket::write, buf -> {
         T packet = packetSupplier.get();
         packet.read(buf);
         return packet;
      }), (packet, context) -> {
         if (contextMapper != null) {
            packet.apply(contextMapper.apply(context));
         }
      });
   }

   public static class Client {
      public static final Function<PacketContext, ClientPacketListener> CLIENT_PLAY = context -> Minecraft.getInstance().getConnection();
      public static final Function<PacketContext, ServerGamePacketListenerImpl> SERVER_PLAY = context -> ((ServerPlayer)context.getPlayer()).connection;

      public static void register() {
         ModNetwork.register(NetworkManager.s2c(), SellingBinTradesS2CPacket.ID, SellingBinTradesS2CPacket::new, CLIENT_PLAY);
         ModNetwork.register(NetworkManager.s2c(), SellingBinEntriesS2CPacket.ID, SellingBinEntriesS2CPacket::new, CLIENT_PLAY);
      }
   }

   public static class Server {
      public static final Function<PacketContext, ServerGamePacketListenerImpl> SERVER_PLAY = context -> ((ServerPlayer)context.getPlayer()).connection;

      public static void register() {
         ModNetwork.register(NetworkManager.s2c(), SellingBinTradesS2CPacket.ID, SellingBinTradesS2CPacket::new, null);
         ModNetwork.register(NetworkManager.s2c(), SellingBinEntriesS2CPacket.ID, SellingBinEntriesS2CPacket::new, null);
      }
   }
}
