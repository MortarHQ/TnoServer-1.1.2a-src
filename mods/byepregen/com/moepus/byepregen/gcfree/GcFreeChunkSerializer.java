package com.moepus.byepregen.gcfree;

import com.moepus.byepregen.MixinPlugin;
import com.moepus.byepregen.compat.C2MEAsyncSerializationCompat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkType;

public final class GcFreeChunkSerializer {
   private static final int MAX_RAW_BUFFER_SLACK_BYTES = 8192;
   private static final String C2ME_ASYNC_SERIALIZATION_MANAGER = "com.ishland.c2me.rewrites.chunksystem.common.async_chunkio.AsyncSerializationManager";
   private static final boolean HAS_C2ME_ASYNC_SERIALIZATION_MANAGER = MixinPlugin.hasClass(
      "com.ishland.c2me.rewrites.chunksystem.common.async_chunkio.AsyncSerializationManager"
   );

   private GcFreeChunkSerializer() {
   }

   public static byte[] serializeRaw(ServerLevel level, ChunkAccess chunk) {
      NbtWriter writer = writeRaw(level, chunk);

      try {
         return writer.toByteArray();
      } finally {
         writer.release();
      }
   }

   public static RawChunkData serializeRawData(ServerLevel level, ChunkAccess chunk) {
      NbtWriter writer = writeRaw(level, chunk);

      try {
         return writer.toRawChunkData(8192);
      } finally {
         writer.release();
      }
   }

   private static NbtWriter writeRaw(ServerLevel level, ChunkAccess chunk) {
      NbtWriter writer = new NbtWriter();

      try {
         writer.startRootCompound();
         ChunkDataSerializer.write(level, chunk, writer);
         writer.finishCompound();
         if (chunk instanceof WorldgenChunkState state) {
            state.byepregen$setFreshWorldgenChunk(false);
         }

         return writer;
      } catch (RuntimeException | Error throwable) {
         writer.release();
         throw throwable;
      }
   }

   public static boolean shouldUseGcFree(ChunkAccess chunk) {
      return !ChunkSaveHookGate.CAN_USE_RAW_SAVE ? false : isEligible(chunk);
   }

   private static boolean isEligible(ChunkAccess chunk) {
      ChunkType chunkType = chunk.getPersistedStatus().getChunkType();
      if (chunkType == ChunkType.PROTOCHUNK) {
         return true;
      } else {
         return isFreshLevelChunk(chunk, chunkType) ? true : !hasBlockEntities(chunk);
      }
   }

   private static boolean isFreshLevelChunk(ChunkAccess chunk, ChunkType chunkType) {
      return chunkType == ChunkType.LEVELCHUNK && chunk instanceof WorldgenChunkState state && state.byepregen$isFreshWorldgenChunk();
   }

   static boolean hasC2MEAsyncSerializationManager() {
      return HAS_C2ME_ASYNC_SERIALIZATION_MANAGER;
   }

   private static boolean hasBlockEntities(ChunkAccess chunk) {
      return hasC2MEAsyncSerializationManager() ? C2MEAsyncSerializationCompat.hasBlockEntities(chunk) : !chunk.getBlockEntitiesPos().isEmpty();
   }

   public record SerializedChunk(CompoundTag tag, byte[] rawBytes) {
      public static GcFreeChunkSerializer.SerializedChunk vanilla(CompoundTag tag) {
         return new GcFreeChunkSerializer.SerializedChunk(tag, null);
      }

      public static GcFreeChunkSerializer.SerializedChunk raw(byte[] rawBytes) {
         return new GcFreeChunkSerializer.SerializedChunk(null, rawBytes);
      }

      public boolean isRaw() {
         return this.rawBytes != null;
      }
   }
}
