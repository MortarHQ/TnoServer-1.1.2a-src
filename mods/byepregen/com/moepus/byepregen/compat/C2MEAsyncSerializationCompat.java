package com.moepus.byepregen.compat;

import com.ishland.c2me.rewrites.chunksystem.common.async_chunkio.AsyncSerializationManager;
import com.ishland.c2me.rewrites.chunksystem.common.async_chunkio.AsyncSerializationManager.Scope;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;

public final class C2MEAsyncSerializationCompat {
   private C2MEAsyncSerializationCompat() {
   }

   public static boolean hasBlockEntities(ChunkAccess chunk) {
      Scope scope = AsyncSerializationManager.getScope(chunk.getPos());
      return scope != null ? !scope.blockEntities.isEmpty() : !chunk.getBlockEntitiesPos().isEmpty();
   }

   public static Iterable<BlockPos> blockEntityPositions(ChunkAccess chunk) {
      Scope scope = AsyncSerializationManager.getScope(chunk.getPos());
      return scope != null ? scope.blockEntities.keySet() : chunk.getBlockEntitiesPos();
   }

   public static Tag blockEntityNbtForSaving(ServerLevel level, ChunkAccess chunk, BlockPos pos) {
      Scope scope = AsyncSerializationManager.getScope(chunk.getPos());
      return (Tag)(scope != null ? (Tag)scope.blockEntities.get(pos) : chunk.getBlockEntityNbtForSaving(pos, level.registryAccess()));
   }
}
