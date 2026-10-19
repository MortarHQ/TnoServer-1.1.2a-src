package net.unusual.block_factorys_bosses.network;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import net.unusual.block_factorys_bosses.client.particle.util.SubParticleEmitter;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record AttachmentDiedMessage(int id) implements CustomPacketPayload {
   public static final Type<AttachmentDiedMessage> TYPE = new Type(BossesRise.prefix("attachment_died"));
   public static final StreamCodec<RegistryFriendlyByteBuf, AttachmentDiedMessage> STREAM_CODEC = StreamCodec.of(
      (buffer, message) -> buffer.writeInt(message.id), buffer -> new AttachmentDiedMessage(buffer.readInt())
   );

   public Type<AttachmentDiedMessage> type() {
      return TYPE;
   }

   public static void handleData(AttachmentDiedMessage message, IPayloadContext context) {
      if (context.flow() == PacketFlow.CLIENTBOUND) {
         context.enqueueWork(() -> AttachmentDiedMessage.ClientHandler.handle(message, context)).exceptionally(e -> {
            context.connection().disconnect(Component.literal(e.getMessage()));
            return null;
         });
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      BossesRise.addNetworkMessage(TYPE, STREAM_CODEC, AttachmentDiedMessage::handleData);
   }

   public interface ClientHandler {
      static void handle(AttachmentDiedMessage message, IPayloadContext context) {
         List<SubParticleEmitter> toRemove = new ArrayList<>();
         SubParticleEmitter.BoneAttachment.ATTACHMENTS.forEach((emitter, entity) -> {
            if (entity.getId() == message.id()) {
               emitter.remove();
               toRemove.add(emitter);
            }
         });
         toRemove.forEach(SubParticleEmitter.BoneAttachment.ATTACHMENTS::remove);
      }
   }
}
