package net.unusual.block_factorys_bosses.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.network.AttachmentDiedMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IParticleAttachment {
   @EventBusSubscriber
   class Listen {
      @SubscribeEvent
      private static void onDied(EntityLeaveLevelEvent event) {
         if (event.getLevel() instanceof ServerLevel level && event.getEntity() instanceof IParticleAttachment) {
            PacketDistributor.sendToPlayersTrackingChunk(
               level, event.getEntity().chunkPosition(), new AttachmentDiedMessage(event.getEntity().getId()), new CustomPacketPayload[0]
            );
         }
      }
   }
}
