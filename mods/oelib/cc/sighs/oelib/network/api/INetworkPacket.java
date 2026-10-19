package cc.sighs.oelib.network.api;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public interface INetworkPacket<T extends INetworkPacket<T> & CustomPacketPayload> extends CustomPacketPayload {
   void handle(INetworkContext var1);

   @NotNull
   default Type<T> type() {
      Class<?> clazz = this.getClass();
      if (clazz.isAnnotationPresent(NetworkPacket.class)) {
         return NetworkPacketTypes.typeOf((Class<T>)clazz);
      } else {
         throw new IllegalStateException("Packet class " + clazz.getName() + " is missing @NetworkPacket");
      }
   }

   default void sendTo(ServerPlayer player) {
      NetworkManager.sendToPlayer(this.self(), player);
   }

   default void sendToAll() {
      NetworkManager.sendToAll(this.self());
   }

   default void sendToServer() {
      NetworkManager.sendToServer(this.self());
   }

   default void sendToWorld(ServerLevel level) {
      NetworkManager.sendToWorld(this.self(), level);
   }

   default void sendToNear(ServerLevel level, Vec3 pos, double radius) {
      NetworkManager.sendToNear(this.self(), level, pos, radius);
   }

   default void sendToNearExcept(ServerLevel level, Vec3 pos, double radius, ServerPlayer excluded) {
      NetworkManager.sendToNearExcept(this.self(), level, pos, radius, excluded);
   }

   default void sendToTrackingEntity(Entity entity) {
      NetworkManager.sendToTrackingEntity(this.self(), entity);
   }

   default void sendToTrackingEntityAndSelf(Entity entity) {
      NetworkManager.sendToTrackingEntityAndSelf(this.self(), entity);
   }

   default void sendToTrackingChunk(ServerLevel level, ChunkPos chunkPos) {
      NetworkManager.sendToTrackingChunk(this.self(), level, chunkPos);
   }

   default T self() {
      return (T)this;
   }
}
