package net.unusual.block_factorys_bosses.network;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unusual.block_factorys_bosses.BossesRise;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record PlayerPushMessage(Vec3 push) implements CustomPacketPayload {
   public static final Type<PlayerPushMessage> TYPE = new Type(BossesRise.prefix("player_push"));
   public static final StreamCodec<RegistryFriendlyByteBuf, PlayerPushMessage> STREAM_CODEC = StreamCodec.of(
      (buffer, message) -> buffer.writeVec3(message.push), buffer -> new PlayerPushMessage(buffer.readVec3())
   );

   public Type<PlayerPushMessage> type() {
      return TYPE;
   }

   public static void handleData(PlayerPushMessage message, IPayloadContext context) {
      if (context.flow() == PacketFlow.CLIENTBOUND) {
         context.enqueueWork(() -> PlayerPushMessage.ClientHandler.handle(message, context)).exceptionally(e -> {
            context.connection().disconnect(Component.literal(e.getMessage()));
            return null;
         });
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      BossesRise.addNetworkMessage(TYPE, STREAM_CODEC, PlayerPushMessage::handleData);
   }

   private interface ClientHandler {
      static void handle(PlayerPushMessage message, IPayloadContext context) {
         context.player().push(message.push);
      }
   }
}
