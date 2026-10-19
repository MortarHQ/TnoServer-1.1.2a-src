package net.unusual.block_factorys_bosses.network;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record IceGauntletMessage(Vec3 center, double range) implements CustomPacketPayload {
   public static final Type<IceGauntletMessage> TYPE = new Type(BossesRise.prefix("ice_gauntlet_msg"));
   public static final StreamCodec<RegistryFriendlyByteBuf, IceGauntletMessage> STREAM_CODEC = StreamCodec.of((buffer, message) -> {
      buffer.writeVec3(message.center);
      buffer.writeDouble(message.range);
   }, buffer -> new IceGauntletMessage(buffer.readVec3(), buffer.readDouble()));

   public Type<IceGauntletMessage> type() {
      return TYPE;
   }

   public static void handleData(IceGauntletMessage message, IPayloadContext context) {
      if (context.flow() == PacketFlow.CLIENTBOUND) {
         context.enqueueWork(() -> IceGauntletMessage.ClientHandler.handle(message, context));
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      BossesRise.addNetworkMessage(TYPE, STREAM_CODEC, IceGauntletMessage::handleData);
   }

   private interface ClientHandler {
      static void handle(IceGauntletMessage message, IPayloadContext context) {
         if (context.player() instanceof LocalPlayer player && player.level() instanceof ClientLevel level) {
            level.playLocalSound(
               message.center.x,
               message.center.y,
               message.center.z,
               (SoundEvent)BossesRiseSounds.ICE_GAUNTLET_BLAST_1.value(),
               SoundSource.PLAYERS,
               0.9F,
               (float)Mth.nextDouble(level.random, 0.5, 0.6),
               false
            );
            level.playLocalSound(
               message.center.x,
               message.center.y,
               message.center.z,
               (SoundEvent)BossesRiseSounds.ICE_GAUNTLET_BLAST_2.value(),
               SoundSource.PLAYERS,
               0.9F,
               (float)Mth.nextDouble(level.random, 1.0, 1.3),
               false
            );

            for (int i = 0; i < 64; i++) {
               level.addParticle(
                  (ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(),
                  message.center.x + Math.cos(level.random.nextFloat() * 360.0F) * message.range,
                  message.center.y,
                  message.center.z + Math.sin(level.random.nextFloat() * 360.0F) * message.range,
                  0.0,
                  0.05,
                  0.0
               );
            }
         }
      }
   }
}
