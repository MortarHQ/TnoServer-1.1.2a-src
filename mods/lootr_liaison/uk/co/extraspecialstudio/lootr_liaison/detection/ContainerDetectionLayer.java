package uk.co.extraspecialstudio.lootr_liaison.detection;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.ChunkEvent.Load;
import uk.co.extraspecialstudio.lootr_liaison.LiaisonNbt;
import uk.co.extraspecialstudio.lootr_liaison.queue.FinalizationQueue;

public final class ContainerDetectionLayer {
   @SubscribeEvent
   public static void onChunkLoad(Load event) {
      if (!event.getLevel().isClientSide()) {
         Level level = (Level)event.getLevel();
         if (event.getChunk() instanceof LevelChunk lc) {
            for (BlockEntity be : lc.getBlockEntities().values()) {
               if (be instanceof RandomizableContainerBlockEntity rcb) {
                  BlockPos pos = be.getBlockPos();
                  CompoundTag tag = be.getPersistentData();
                  if (!LiaisonNbt.isFinalized(tag)) {
                     boolean hasLootTable = tag.contains("LootTable") && !tag.getString("LootTable").isEmpty();
                     boolean hasLootTableSeed = tag.contains("LootTableSeed");
                     if (hasLootTable || hasLootTableSeed) {
                        ResourceKey<Level> dim = level.dimension();
                        FinalizationQueue.enqueue(dim, pos, level.getGameTime());
                     }
                  }
               }
            }
         }
      }
   }
}
