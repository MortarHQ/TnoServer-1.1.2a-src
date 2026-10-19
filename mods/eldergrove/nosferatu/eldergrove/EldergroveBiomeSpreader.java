package nosferatu.eldergrove;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.PalettedContainer;

public final class EldergroveBiomeSpreader {
   private EldergroveBiomeSpreader() {
   }

   public static boolean setEldergroveBiome(ServerLevel level, BlockPos pos) {
      return setBiomeColumn(level, pos, EldergroveBiomes.ELDERGROVE, true);
   }

   public static boolean setTaintedBiome(ServerLevel level, BlockPos pos) {
      return setBiomeColumn(level, pos, EldergroveBiomes.TAINTED_GROVE, false);
   }

   private static boolean setBiomeColumn(ServerLevel level, BlockPos pos, ResourceKey<Biome> targetKey, boolean protectTaintedGrove) {
      if (pos.getY() >= level.getMinBuildHeight() && pos.getY() < level.getMaxBuildHeight()) {
         LevelChunk chunk = level.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
         Holder<Biome> target = level.registryAccess().lookupOrThrow(Registries.BIOME).getOrThrow(targetKey);
         int quartX = pos.getX() >> 2;
         int quartZ = pos.getZ() >> 2;
         int localQuartX = quartX & 3;
         int localQuartZ = quartZ & 3;
         boolean changed = false;

         for (int sectionIndex = 0; sectionIndex < chunk.getSections().length; sectionIndex++) {
            PalettedContainer<Holder<Biome>> biomes = (PalettedContainer<Holder<Biome>>)chunk.getSection(sectionIndex).getBiomes();

            for (int localQuartY = 0; localQuartY < 4; localQuartY++) {
               Holder<Biome> current = (Holder<Biome>)biomes.get(localQuartX, localQuartY, localQuartZ);
               if (!current.is(targetKey) && (!protectTaintedGrove || !current.is(EldergroveBiomes.TAINTED_GROVE))) {
                  biomes.getAndSetUnchecked(localQuartX, localQuartY, localQuartZ, target);
                  changed = true;
               }
            }
         }

         if (!changed) {
            return false;
         }

         chunk.setUnsaved(true);
         sendBiomeUpdate(level, chunk, pos);
         refreshSurface(level, pos);
         return true;
      } else {
         return false;
      }
   }

   private static void sendBiomeUpdate(ServerLevel level, LevelChunk chunk, BlockPos pos) {
      ClientboundChunksBiomesPacket packet = ClientboundChunksBiomesPacket.forChunks(List.of(chunk));

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <= 25600.0) {
            player.connection.send(packet);
         }
      }
   }

   private static void refreshSurface(ServerLevel level, BlockPos pos) {
      for (int y = -1; y <= 2; y++) {
         BlockPos refreshPos = pos.above(y);
         level.sendBlockUpdated(refreshPos, level.getBlockState(refreshPos), level.getBlockState(refreshPos), 2);
      }
   }

   public static boolean isNaturalSurface(ServerLevel level, BlockPos pos) {
      return level.getBlockState(pos).is(BlockTags.DIRT) && !level.getBlockState(pos.above()).isSolidRender(level, pos.above());
   }
}
