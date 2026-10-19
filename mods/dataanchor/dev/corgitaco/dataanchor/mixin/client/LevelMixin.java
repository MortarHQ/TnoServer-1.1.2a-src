package dev.corgitaco.dataanchor.mixin.client;

import dev.corgitaco.dataanchor.data.TickableTrackedData;
import dev.corgitaco.dataanchor.data.TrackedDataContainer;
import dev.corgitaco.dataanchor.data.registry.TrackedDataKey;
import dev.corgitaco.dataanchor.data.type.blockentity.BlockEntityTrackedData;
import dev.corgitaco.dataanchor.data.type.chunk.ChunkTrackedData;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReferenceArray;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public abstract class LevelMixin implements LevelAccessor {
   @Shadow
   @Final
   public boolean isClientSide;

   @Inject(method = "tickBlockEntities()V", at = @At("RETURN"))
   private void tickClient(CallbackInfo ci) {
      if (this.isClientSide && this.getChunkSource() instanceof ClientChunkCache clientChunkCache) {
         AtomicReferenceArray<LevelChunk> chunks = this.dataAnchor$getChunks(clientChunkCache);
         int length = chunks.length();

         for (int i = 0; i < length; i++) {
            LevelChunk levelChunk = chunks.get(i);
            if (levelChunk != null) {
               if (levelChunk instanceof TrackedDataContainer dataContainer) {
                  for (TrackedDataKey<ChunkTrackedData> key : dataContainer.dataAnchor$getTrackedDataKeys()) {
                     Optional optional = dataContainer.dataAnchor$getTrackedData(key);
                     if (optional.isPresent() && optional.get() instanceof TickableTrackedData trackedData) {
                        trackedData.tick();
                     }
                  }
               }

               for (BlockEntity value : levelChunk.getBlockEntities().values()) {
                  if (value instanceof TrackedDataContainer container) {
                     for (TrackedDataKey<BlockEntityTrackedData> key : container.dataAnchor$getTrackedDataKeys()) {
                        container.dataAnchor$getTrackedData(key).ifPresent(data -> {
                           if (data instanceof TickableTrackedData tickableData) {
                              tickableData.tick();
                           }
                        });
                     }
                  }
               }
            }
         }
      }
   }

   @Unique
   private synchronized AtomicReferenceArray<LevelChunk> dataAnchor$getChunks(ClientChunkCache clientChunkCache) {
      return clientChunkCache.storage.chunks;
   }
}
