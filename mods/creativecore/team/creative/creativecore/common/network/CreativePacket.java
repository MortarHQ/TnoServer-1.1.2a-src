package team.creative.creativecore.common.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public abstract class CreativePacket implements CustomPacketPayload {
   private Type<? extends CreativePacket> type;

   public void execute(Player player) {
      if (player.level().isClientSide) {
         this.executeClient(player);
      } else {
         this.executeServer((ServerPlayer)player);
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return this.type;
   }

   void setType(Type<? extends CreativePacket> type) {
      this.type = type;
   }

   public abstract void executeClient(Player var1);

   public abstract void executeServer(ServerPlayer var1);

   public void requiresClient(Player player) {
      if (!player.level().isClientSide) {
         throw new InvalidSideException(player);
      }
   }

   public void requiresServer(Player player) {
      if (player.level().isClientSide) {
         throw new InvalidSideException(player);
      }
   }
}
