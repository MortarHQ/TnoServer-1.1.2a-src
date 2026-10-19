package dev.ftb.mods.ftbchunks.data;

import com.google.common.primitives.Ints;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.client.map.MapChunk;
import java.util.Date;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

public record ChunkSyncInfo(
   int x, int z, boolean claimed, int relativeTimeClaimed, boolean forceLoaded, int relativeTimeForceLoaded, boolean expires, int relativeForceLoadExpiryTime
) {
   public static StreamCodec<FriendlyByteBuf, ChunkSyncInfo> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, ChunkSyncInfo>() {
      public ChunkSyncInfo decode(FriendlyByteBuf buf) {
         int x = buf.readInt();
         int z = buf.readInt();
         if (buf.readBoolean()) {
            int relativeTimeClaimed = buf.readInt();
            byte flags = buf.readByte();
            boolean forceLoaded = (flags & 1) != 0;
            boolean expires = (flags & 2) != 0;
            int relativeTimeForceLoaded = forceLoaded ? buf.readInt() : 0;
            int relativeForceLoadExpiryTime = expires ? buf.readInt() : 0;
            return new ChunkSyncInfo(x, z, true, relativeTimeClaimed, forceLoaded, relativeTimeForceLoaded, expires, relativeForceLoadExpiryTime);
         } else {
            return new ChunkSyncInfo(x, z, false, 0, false, 0, false, 0);
         }
      }

      public void encode(FriendlyByteBuf buf, ChunkSyncInfo chunkSyncInfo) {
         buf.writeInt(chunkSyncInfo.x);
         buf.writeInt(chunkSyncInfo.z);
         buf.writeBoolean(chunkSyncInfo.claimed);
         if (chunkSyncInfo.claimed) {
            byte flags = 0;
            if (chunkSyncInfo.forceLoaded) {
               flags = (byte)(flags | 1);
            }

            if (chunkSyncInfo.expires) {
               flags = (byte)(flags | 2);
            }

            buf.writeInt(chunkSyncInfo.relativeTimeClaimed);
            buf.writeByte(flags);
            if (chunkSyncInfo.forceLoaded) {
               buf.writeInt(chunkSyncInfo.relativeTimeForceLoaded);
            }

            if (chunkSyncInfo.expires) {
               buf.writeInt(chunkSyncInfo.relativeForceLoadExpiryTime);
            }
         }
      }
   };

   public static ChunkSyncInfo create(long now, int x, int z, @Nullable ClaimedChunk claimedChunk) {
      if (claimedChunk != null) {
         boolean forceLoaded = claimedChunk.isForceLoaded();
         boolean expires = claimedChunk.getForceLoadExpiryTime() > 0L;
         return new ChunkSyncInfo(
            x,
            z,
            true,
            millisToSeconds(now - claimedChunk.getTimeClaimed()),
            forceLoaded,
            forceLoaded ? millisToSeconds(now - claimedChunk.getForceLoadedTime()) : 0,
            expires,
            expires ? millisToSeconds(claimedChunk.getForceLoadExpiryTime() - now) : 0
         );
      } else {
         return new ChunkSyncInfo(x, z, false, 0, false, 0, false, 0);
      }
   }

   public ChunkSyncInfo hidden() {
      return create(0L, this.x, this.z, null);
   }

   private static int millisToSeconds(long ms) {
      return Ints.saturatedCast(ms / 1000L);
   }

   public MapChunk.DateInfo getDateInfo(boolean isClaimed, long now) {
      if (!isClaimed) {
         return MapChunk.NO_DATE_INFO;
      }

      Date claimed = new Date(now - this.relativeTimeClaimed * 1000L);
      Date forceLoaded = this.forceLoaded ? new Date(now - this.relativeTimeForceLoaded * 1000L) : null;
      Date expiry = this.forceLoaded && this.expires ? new Date(now + this.relativeForceLoadExpiryTime * 1000L) : null;
      return new MapChunk.DateInfo(claimed, forceLoaded, expiry);
   }
}
