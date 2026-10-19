package net.unusual.block_factorys_bosses.network;

import com.mojang.blaze3d.audio.Channel;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record ShutUpPacket() implements CustomPacketPayload {
   public static final Type<ShutUpPacket> TYPE = new Type(BossesRise.prefix("shut_up"));
   public static final StreamCodec<RegistryFriendlyByteBuf, ShutUpPacket> STREAM_CODEC = StreamCodec.of((buffer, message) -> {}, buffer -> new ShutUpPacket());

   public Type<ShutUpPacket> type() {
      return TYPE;
   }

   public static void handleData(ShutUpPacket message, IPayloadContext context) {
      if (context.flow() == PacketFlow.CLIENTBOUND) {
         context.enqueueWork(() -> ShutUpPacket.ClientHandler.handle(message, context)).exceptionally(e -> {
            context.connection().disconnect(Component.literal(e.getMessage()));
            return null;
         });
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      BossesRise.addNetworkMessage(TYPE, STREAM_CODEC, ShutUpPacket::handleData);
   }

   private interface ClientHandler {
      static void handle(ShutUpPacket message, IPayloadContext context) {
         Minecraft.getInstance().getSoundManager().soundEngine.instanceToChannel.forEach((soundInstance, channelHandle) -> {
            if (BossesRiseSounds.CANCELABLE_SOUNDS.contains(soundInstance.getLocation())) {
               BossesRise.LOGGER.debug("YAY {}!", soundInstance.getLocation());
               channelHandle.execute(Channel::stop);
            }
         });
      }
   }
}
