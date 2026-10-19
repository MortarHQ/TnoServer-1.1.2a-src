package com.chunksending.mixin;

import com.chunksending.ChunkSending;
import com.chunksending.IBatchedUpdateSender;
import com.chunksending.config.CommonConfiguration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.PlayerChunkSender;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerChunkSender.class)
public class PlayerChunkSenderMixin implements IBatchedUpdateSender {
   @Shadow
   @Final
   @Mutable
   private static float START_CHUNKS_PER_TICK = ((CommonConfiguration)ChunkSending.config.getCommonConfig()).maxChunksPerTick;
   @Shadow
   @Final
   @Mutable
   public static float MAX_CHUNKS_PER_TICK = ((CommonConfiguration)ChunkSending.config.getCommonConfig()).maxChunksPerTick * 2;
   @Shadow
   private float desiredChunksPerTick = ((CommonConfiguration)ChunkSending.config.getCommonConfig()).maxChunksPerTick;
   @Unique
   private Map<ChunkPos, List<Packet<?>>> packetsToSend = new HashMap<>();

   @Override
   public boolean attachToPending(ChunkPos pos, Packet<?> packet) {
      List<Packet<?>> packetList = this.packetsToSend.get(pos);
      if (packetList == null) {
         packetList = new ArrayList<>();
         this.packetsToSend.put(pos, packetList);
      }

      packetList.add(packet);
      return true;
   }

   @Override
   public void reset() {
      this.packetsToSend.clear();
   }

   @Override
   public void tick(ServerPlayer player) {
      if (!this.packetsToSend.isEmpty()) {
         List<Entry<ChunkPos, List<Packet<?>>>> packets = new ArrayList<>(this.packetsToSend.entrySet());
         packets.sort(Comparator.comparingDouble(e -> e.getKey().getMiddleBlockPosition(player.getBlockY()).distSqr(player.blockPosition())));
         int max = ((CommonConfiguration)ChunkSending.config.getCommonConfig()).maxChunksPerTick + packets.size() / 10;
         int count = 0;

         for (int i = 0; i < packets.size() && i < max; i++) {
            Entry<ChunkPos, List<Packet<?>>> entry = packets.get(i);

            for (Packet packet : entry.getValue()) {
               count++;
               player.connection.send(packet);
            }

            this.packetsToSend.remove(entry.getKey());
         }

         if (((CommonConfiguration)ChunkSending.config.getCommonConfig()).debugLogging) {
            ChunkSending.LOGGER.info("Sent: " + count + " packets to " + player.getDisplayName().getString() + ", still in queue:" + this.packetsToSend.size());
         }
      }
   }
}
