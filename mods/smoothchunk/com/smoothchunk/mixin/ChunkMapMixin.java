package com.smoothchunk.mixin;

import com.smoothchunk.SmoothchunkMod;
import com.smoothchunk.config.CommonConfiguration;
import com.smoothchunk.world.IChunkTimeSave;
import com.smoothchunk.world.PosTimeEntry;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayDeque;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkMap.class)
public abstract class ChunkMapMixin {
   @Shadow
   @Final
   private ServerLevel level;
   @Shadow
   private volatile Long2ObjectLinkedOpenHashMap<ChunkHolder> visibleChunkMap;
   @Unique
   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> emptyMap = new Long2ObjectLinkedOpenHashMap();
   @Unique
   private final ArrayDeque<PosTimeEntry> toSave = new ArrayDeque<>();

   @Shadow
   protected abstract boolean saveChunkIfNeeded(ChunkHolder var1);

   @Redirect(
      method = "processUnloads",
      at = @At(
         value = "INVOKE",
         target = "Lit/unimi/dsi/fastutil/objects/ObjectCollection;iterator()Lit/unimi/dsi/fastutil/objects/ObjectIterator;",
         remap = false
      )
   )
   public ObjectIterator<ChunkHolder> smoothChunksaveChunks(ObjectCollection instance) {
      long currentGametime = this.level.getGameTime();
      if (currentGametime % 64L == 0L) {
         ObjectIterator savedChunks = this.visibleChunkMap.values().iterator();

         while (savedChunks.hasNext()) {
            ChunkHolder entry = (ChunkHolder)savedChunks.next();
            if (entry.wasAccessibleSinceLastSave() && entry.isReadyForSaving()) {
               ChunkAccess chunkaccess = entry.getLatestChunk();
               if ((chunkaccess instanceof ImposterProtoChunk || chunkaccess instanceof LevelChunk) && chunkaccess.isUnsaved()) {
                  long saveTimePoint = ((IChunkTimeSave)chunkaccess).smoothchunk$getNextSaveTime();
                  if (saveTimePoint == 0L) {
                     ((IChunkTimeSave)chunkaccess)
                        .smoothchunk$setSaveTimePoint(
                           currentGametime
                              + ((CommonConfiguration)SmoothchunkMod.config.getCommonConfig()).chunkSaveDelay * 20L
                              + SmoothchunkMod.rand.nextInt(20) * 20
                        );
                     this.toSave.addLast(new PosTimeEntry(((IChunkTimeSave)chunkaccess).smoothchunk$getNextSaveTime(), entry.getPos()));
                  } else if (currentGametime > saveTimePoint) {
                     ((IChunkTimeSave)chunkaccess).smoothchunk$setSaveTimePoint(0L);
                  }
               }
            }
         }
      }

      int savedChunks = 0;

      for (int i = 0; i < 10; i++) {
         PosTimeEntry posTimeEntry = this.toSave.peek();
         if (posTimeEntry == null || currentGametime <= posTimeEntry.savetime) {
            break;
         }

         ChunkHolder holder = (ChunkHolder)this.visibleChunkMap.get(posTimeEntry.pos.toLong());
         if (holder != null && this.saveChunkIfNeeded(holder)) {
            savedChunks++;
         }

         this.toSave.pop();
      }

      if (savedChunks > 0 && ((CommonConfiguration)SmoothchunkMod.config.getCommonConfig()).debugLogging) {
         SmoothchunkMod.LOGGER.info("Smoothchunks saved chunks this tick: " + savedChunks);
      }

      return this.emptyMap.values().iterator();
   }

   @Inject(
      method = "save",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ChunkMap;isExistingChunkFull(Lnet/minecraft/world/level/ChunkPos;)Z"),
      cancellable = true
   )
   private void checkExisting(ChunkAccess chunkAccess, CallbackInfoReturnable<Boolean> cir) {
      if (((CommonConfiguration)SmoothchunkMod.config.getCommonConfig()).disableProtoSave
         && chunkAccess.getPersistedStatus().getChunkType() != ChunkType.LEVELCHUNK) {
         cir.setReturnValue(false);
      }
   }
}
