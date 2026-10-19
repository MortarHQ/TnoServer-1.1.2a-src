package artifacts.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class NetworkHandler {
   public static final List<NetworkHandler.PayloadHandler<?>> SERVERBOUND_HANDLERS = new ArrayList<>();
   public static final List<NetworkHandler.PayloadHandler<?>> CLIENTBOUND_HANDLERS = new ArrayList<>();

   public static void initPayloads() {
      registerClientbound(ChorusTotemUsedPacket.TYPE, ChorusTotemUsedPacket.CODEC, ChorusTotemUsedPacket::apply);
      registerClientbound(PlaySoundAtPlayerPacket.TYPE, PlaySoundAtPlayerPacket.CODEC, PlaySoundAtPlayerPacket::apply);
      registerClientbound(UpdateItemConfigPacket.TYPE, UpdateItemConfigPacket.CODEC, UpdateItemConfigPacket::apply);
      registerClientbound(SwimPacket.TYPE, SwimPacket.CODEC, SwimPacket::apply);
      registerServerbound(DoubleJumpPacket.TYPE, DoubleJumpPacket.CODEC, DoubleJumpPacket::apply);
      registerServerbound(SwimPacket.TYPE, SwimPacket.CODEC, SwimPacket::apply);
      registerServerbound(ToggleKeyPressedPacket.TYPE, ToggleKeyPressedPacket.CODEC, ToggleKeyPressedPacket::apply);
   }

   private static <T extends CustomPacketPayload> void registerServerbound(
      Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, NetworkHandler.Receiver<T> receiver
   ) {
      SERVERBOUND_HANDLERS.add(new NetworkHandler.PayloadHandler<>(type, codec, receiver));
   }

   private static <T extends CustomPacketPayload> void registerClientbound(
      Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, NetworkHandler.Receiver<T> receiver
   ) {
      CLIENTBOUND_HANDLERS.add(new NetworkHandler.PayloadHandler<>(type, codec, receiver));
   }

   public static void sendToServer(CustomPacketPayload payload) {
      ClientPacketListener listener = Objects.requireNonNull(Minecraft.getInstance().getConnection());
      listener.send(new ServerboundCustomPayloadPacket(payload));
   }

   public static void sendToPlayers(Iterable<ServerPlayer> players, CustomPacketPayload payload) {
      for (ServerPlayer player : players) {
         sendToPlayer(player, payload);
      }
   }

   public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
      player.connection.send(new ClientboundCustomPayloadPacket(payload));
   }

   public interface PayloadContext {
      Player player();

      void queue(Runnable var1);
   }

   public record PayloadHandler<T extends CustomPacketPayload>(
      Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, NetworkHandler.Receiver<T> receiver
   ) {
   }

   @FunctionalInterface
   public interface Receiver<T extends CustomPacketPayload> {
      void receive(T var1, NetworkHandler.PayloadContext var2);
   }
}
