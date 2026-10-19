package com.smoothchunk.mixin;

import com.smoothchunk.world.IChunkTimeSave;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChunkAccess.class)
public abstract class ChunkAccessMixin implements IChunkTimeSave {
   @Unique
   long saveTimePoint = 0L;

   @Override
   public long smoothchunk$getNextSaveTime() {
      return this.saveTimePoint;
   }

   @Override
   public void smoothchunk$setSaveTimePoint(long timePoint) {
      this.saveTimePoint = timePoint;
   }
}
