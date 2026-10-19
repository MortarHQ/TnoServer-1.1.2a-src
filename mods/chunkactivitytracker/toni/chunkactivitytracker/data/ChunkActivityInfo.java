package toni.chunkactivitytracker.data;

import io.netty.buffer.ByteBuf;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Generated;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import toni.chunkactivitytracker.foundation.config.AllConfigs;

public class ChunkActivityInfo implements Serializable {
   public static int CODEC_VERSION = 1;
   static StreamCodec<ByteBuf, ChunkActivityInfo> CODEC = new StreamCodec<ByteBuf, ChunkActivityInfo>() {
      public ChunkActivityInfo decode(ByteBuf buffer) {
         FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
         int verson = buf.readInt();
         Map<UUID, Long> time = buf.readMap(key -> key.readUUID(), FriendlyByteBuf::readLong);
         Map<UUID, Integer> blocks = buf.readMap(key -> key.readUUID(), FriendlyByteBuf::readInt);
         boolean hasHeightMap = buf.readBoolean();
         if (hasHeightMap) {
            long[] height = buf.readLongArray();
            return new ChunkActivityInfo(time, blocks, height);
         } else {
            return new ChunkActivityInfo(time, blocks, null);
         }
      }

      public void encode(ByteBuf buffer, ChunkActivityInfo info) {
         FriendlyByteBuf buf = new FriendlyByteBuf(buffer);
         buf.writeInt(ChunkActivityInfo.CODEC_VERSION);
         buf.writeMap(info.playerTimeMap, (a, b) -> a.writeUUID(b), FriendlyByteBuf::writeLong);
         buf.writeMap(info.blocksPlacedMap, (a, b) -> a.writeUUID(b), FriendlyByteBuf::writeInt);
         if (info.initialHeightmap != null) {
            buf.writeBoolean(true);
            buf.writeLongArray(info.initialHeightmap);
         } else {
            buf.writeBoolean(false);
         }
      }
   };
   private final ConcurrentHashMap<UUID, Long> playerTimeMap;
   private final ConcurrentHashMap<UUID, Integer> blocksPlacedMap;
   private long[] initialHeightmap;

   public ChunkActivityInfo(Map<UUID, Long> playerTimeInSeconds, Map<UUID, Integer> blocksPlacedByPlayer, long[] initialHeightmap) {
      this.playerTimeMap = new ConcurrentHashMap<>(playerTimeInSeconds);
      this.blocksPlacedMap = new ConcurrentHashMap<>(blocksPlacedByPlayer);
      this.initialHeightmap = initialHeightmap;
   }

   public ChunkActivityInfo(LevelChunk chunk) {
      this.playerTimeMap = new ConcurrentHashMap<>();
      this.blocksPlacedMap = new ConcurrentHashMap<>();
      if ((Boolean)AllConfigs.server().storeHeightmaps.get()) {
         Heightmap heightmap = chunk.getOrCreateHeightmapUnprimed(Types.MOTION_BLOCKING_NO_LEAVES);
         this.initialHeightmap = heightmap.getRawData();
      }
   }

   public void updatePlayerTime(UUID player) {
      this.playerTimeMap.merge(player, 1L, Long::sum);
   }

   public Long getPlayerTime(UUID player) {
      return this.playerTimeMap.getOrDefault(player, 0L);
   }

   public void incrementBlocksPlaced(UUID player) {
      this.blocksPlacedMap.merge(player, 1, Integer::sum);
   }

   @Generated
   public ConcurrentHashMap<UUID, Long> getPlayerTimeMap() {
      return this.playerTimeMap;
   }

   @Generated
   public ConcurrentHashMap<UUID, Integer> getBlocksPlacedMap() {
      return this.blocksPlacedMap;
   }

   @Generated
   public long[] getInitialHeightmap() {
      return this.initialHeightmap;
   }
}
