package com.moepus.byepregen.compat;

import com.ishland.c2me.base.common.theinterface.IDirectStorage;
import com.moepus.byepregen.gcfree.RawChunkData;
import java.util.concurrent.CompletableFuture;
import net.minecraft.world.level.ChunkPos;

public final class C2MEDirectStorageCompat {
   private C2MEDirectStorageCompat() {
   }

   public static CompletableFuture<Void> setRawChunkData(Object worker, ChunkPos pos, RawChunkData data) {
      return ((IDirectStorage)worker).setRawChunkData(pos, data.toByteArray());
   }
}
