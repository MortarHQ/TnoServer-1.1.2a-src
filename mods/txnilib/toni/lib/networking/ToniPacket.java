package toni.lib.networking;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import toni.lib.utils.VersionUtils;

public abstract class ToniPacket<TPacket extends ToniPacket> implements CustomPacketPayload {
   public ResourceLocation Resource;
   public StreamCodec<FriendlyByteBuf, TPacket> CODEC;
   public Type<TPacket> ID;

   public Type<? extends CustomPacketPayload> type() {
      return this.ID;
   }

   public ToniPacket(String modid, String path, StreamCodec<FriendlyByteBuf, TPacket> codec) {
      this.Resource = VersionUtils.resource(modid, path);
      this.CODEC = codec;
      this.ID = new Type(this.Resource);
   }

   public void registerType() {
      PayloadTypeRegistry.playS2C().register(this.ID, this.CODEC);
   }

   public void registerClientHandler(BiConsumer<TPacket, LocalPlayer> consumer) {
      ClientPlayNetworking.registerGlobalReceiver(this.ID, (payload, context) -> consumer.accept((TPacket)payload, context.player()));
   }

   public void registerClientHandler(Consumer<TPacket> consumer) {
      ClientPlayNetworking.registerGlobalReceiver(this.ID, (payload, context) -> consumer.accept((TPacket)payload));
   }

   public void sendToAll(MinecraftServer server) {
      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         ServerPlayNetworking.send(player, this);
      }
   }

   public void send(ServerPlayer player) {
      ServerPlayNetworking.send(player, this);
   }
}
