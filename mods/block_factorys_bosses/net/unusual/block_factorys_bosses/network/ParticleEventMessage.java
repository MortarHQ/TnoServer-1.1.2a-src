package net.unusual.block_factorys_bosses.network;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity;
import net.unusual.block_factorys_bosses.entity.boss.AbstractStateBossEntity;

@EventBusSubscriber
@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleEventMessage(int entityId, String name) implements CustomPacketPayload {
   public static final Type<ParticleEventMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", "particle_event"));
   public static final StreamCodec<RegistryFriendlyByteBuf, ParticleEventMessage> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_INT, ParticleEventMessage::entityId, ByteBufCodecs.STRING_UTF8, ParticleEventMessage::name, ParticleEventMessage::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handleData(ParticleEventMessage data, IPayloadContext context) {
      if (context.flow() == PacketFlow.CLIENTBOUND) {
         context.enqueueWork(() -> ParticleEventMessage.ClientHandler.handle(data, context)).exceptionally(e -> {
            context.connection().disconnect(Component.literal(e.getMessage()));
            return null;
         });
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      BossesRise.addNetworkMessage(TYPE, STREAM_CODEC, ParticleEventMessage::handleData);
   }

   private interface ClientHandler {
      static void handle(ParticleEventMessage data, IPayloadContext context) {
         Entity entity = context.player().level().getEntity(data.entityId);
         if (entity instanceof AbstractBossEntity boss) {
            boss.handleParticleEvent(data.name);
         } else if (entity instanceof AbstractStateBossEntity boss) {
            boss.handleParticleEvent(data.name);
         }
      }
   }
}
