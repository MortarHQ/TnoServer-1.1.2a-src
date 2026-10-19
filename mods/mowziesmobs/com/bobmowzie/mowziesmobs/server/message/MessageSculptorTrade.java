package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerSculptorTrade;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record MessageSculptorTrade(int entityId) implements CustomPacketPayload {
   public static final Type<MessageSculptorTrade> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "message_sculptor_trade"));
   public static final StreamCodec<ByteBuf, MessageSculptorTrade> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, MessageSculptorTrade::entityId, MessageSculptorTrade::new
   );

   public static void handleServer(MessageSculptorTrade packet, IPayloadContext context) {
      context.enqueueWork(() -> {
         Player player = context.player();
         if (player.level().getEntity(packet.entityId()) instanceof EntitySculptor sculptor) {
            if (sculptor.getCustomer() == player) {
               if (player.containerMenu instanceof ContainerSculptorTrade trade) {
                  if (!sculptor.checkTestObstructed()) {
                     boolean satisfied = sculptor.fulfillDesire(trade.getSlot(0));
                     if (satisfied) {
                        trade.returnItems();
                        trade.broadcastChanges();
                        sculptor.setTestingPlayer(player);
                     }
                  }
               }
            }
         }
      });
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
