package com.tensura.naturesaura.integration;

import com.tensura.naturesaura.registry.LegacyAttachments;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.level.ChunkEvent.Load;

public final class LegacyBiomeAuraMigration {
   private static final int LEGACY_AURA_BONUS = 125000;

   private LegacyBiomeAuraMigration() {
   }

   public static void onChunkLoad(Load event) {
      if (event.getLevel() instanceof ServerLevel && event.getChunk() instanceof LevelChunk chunk) {
         AttachmentType var4 = LegacyAttachments.BIOME_AURA_INITIALIZED.get();
         if (chunk.hasData(var4)) {
            boolean initialized = Boolean.TRUE.equals(chunk.removeData(var4));
            if (initialized) {
               removeLegacyAuraSpot(chunk);
            }

            chunk.setUnsaved(true);
         }
      }
   }

   private static void removeLegacyAuraSpot(LevelChunk chunk) {
      int centerX = chunk.getPos().getMiddleBlockX();
      int centerZ = chunk.getPos().getMiddleBlockZ();
      int surfaceY = chunk.getHeight(Types.WORLD_SURFACE, centerX, centerZ);
      BlockPos auraPos = new BlockPos(centerX, surfaceY, centerZ);
      IAuraChunk auraChunk = (IAuraChunk)chunk.getData(NaturesAuraAPI.AURA_CHUNK_ATTACHMENT);
      int storedAura = auraChunk.getDrainSpot(auraPos);
      if (storedAura > 0) {
         auraChunk.drainAura(auraPos, Math.min(storedAura, 125000), false, false);
      }
   }
}
