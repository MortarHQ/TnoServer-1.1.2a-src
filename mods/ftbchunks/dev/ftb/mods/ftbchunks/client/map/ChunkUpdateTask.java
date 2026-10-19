package dev.ftb.mods.ftbchunks.client.map;

import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.util.HeightUtils;
import dev.ftb.mods.ftblibrary.math.XZ;
import net.minecraft.Util;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager.NoiseBiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.Nullable;

public class ChunkUpdateTask implements MapTask, NoiseBiomeSource {
   private static final int[] ALL_BLOCKS = (int[])Util.make(new int[256], array -> {
      int i = 0;

      while (i < 256) {
         array[i] = i++;
      }
   });
   private static final ResourceLocation AIR = ResourceLocation.fromNamespaceAndPath("minecraft", "air");
   private static long debugLastTime = 0L;
   private MapManager manager;
   private final Level level;
   private final ChunkAccess chunkAccess;
   private final ChunkPos chunkPos;
   private final int[] blocksToUpdate;
   private final long taskStartTime;

   public ChunkUpdateTask(@Nullable MapManager manager, Level level, ChunkAccess chunkAccess, ChunkPos chunkPos) {
      this(manager, level, chunkAccess, chunkPos, ALL_BLOCKS);
   }

   public ChunkUpdateTask(@Nullable MapManager manager, Level level, ChunkAccess chunkAccess, ChunkPos chunkPos, int[] blocksToUpdate) {
      this.manager = manager;
      this.level = level;
      this.chunkAccess = chunkAccess;
      this.chunkPos = chunkPos;
      this.blocksToUpdate = blocksToUpdate;
      this.taskStartTime = System.currentTimeMillis();
   }

   public static void init() {
      debugLastTime = 0L;
   }

   public static long getDebugLastTime() {
      return debugLastTime;
   }

   @Override
   public void runMapTask() throws Exception {
      while (this.manager == null) {
         this.manager = MapManager.getInstance().orElse(null);
         if (this.manager == null) {
            if (System.currentTimeMillis() - this.taskStartTime >= 30000L) {
               return;
            }

            Thread.sleep(1L);
         }
      }

      if (!this.manager.isInvalid()) {
         long startTime = System.nanoTime();
         ResourceKey<Level> dimId = this.level.dimension();
         MapChunk mapChunk = this.manager.getDimension(dimId).getRegion(XZ.regionFromChunk(this.chunkPos)).getDataBlocking().getChunk(XZ.of(this.chunkPos));
         MapRegionData data = mapChunk.getRegionData();
         Registry<Biome> biomes = this.level.registryAccess().registryOrThrow(Registries.BIOME);
         MutableBlockPos blockPos = new MutableBlockPos();
         int blockX = this.chunkPos.getMinBlockX();
         int blockZ = this.chunkPos.getMinBlockZ();
         boolean changed = false;
         boolean versionChange = mapChunk.getVersion() != 4;
         if (versionChange) {
            mapChunk.setVersion(4);
            changed = true;
         }

         for (int wi : this.blocksToUpdate) {
            int wx = wi % 16;
            int wz = wi / 16;
            blockPos.set(blockX + wx, this.chunkAccess.getHeight(Types.MOTION_BLOCKING, blockX + wx, blockZ + wz) + 2, blockZ + wz);
            int waterY = Mth.clamp(HeightUtils.getHeight(this.level, this.chunkAccess, blockPos), -32768, 32767);
            int height = blockPos.getY();
            BlockState state = this.chunkAccess.getBlockState(blockPos);
            int ax = mapChunk.getPos().x() * 16 + wx;
            int az = mapChunk.getPos().z() * 16 + wz;
            int index = ax + az * 512;
            int waterLightAndBiome0 = data.waterLightAndBiome[index] & '\uffff';
            int blockIndex0 = data.getBlockIndex(index);
            int height0 = data.height[index];
            blockPos.setY(waterY == -32767 ? height : waterY);
            int waterLightAndBiome = waterLightAndBiome0 & 2047;
            waterLightAndBiome |= waterY != -32767 ? 32768 : 0;
            waterLightAndBiome |= (this.level.getBrightness(LightLayer.BLOCK, blockPos) & 15) << 11;
            ResourceLocation id = state == null ? AIR : FTBChunks.BLOCK_REGISTRY.getId(state.getBlock());
            int blockIndex = this.manager.getBlockColorIndex(id == null ? AIR : id);
            Biome biome = (Biome)this.getNoiseBiome(blockPos.getX() >> 2, blockPos.getY() >> 2, blockPos.getZ() >> 2).value();
            waterLightAndBiome &= 63488;
            waterLightAndBiome |= this.manager.getBiomeColorIndex(biomes, biome, biome) & 2047;
            if (versionChange || height0 != height) {
               data.height[index] = (short)height;
               changed = true;
            }

            if (versionChange || waterLightAndBiome0 != waterLightAndBiome) {
               data.waterLightAndBiome[index] = (short)waterLightAndBiome;
               if (biome != null && (versionChange || (waterLightAndBiome0 & 2047) != (waterLightAndBiome & 2047))) {
                  double cx = blockPos.getX();
                  double cz = blockPos.getZ();
                  data.foliage[index] = data.foliage[index] & 0xFF000000 | BiomeColors.FOLIAGE_COLOR_RESOLVER.getColor(biome, cx, cz) & 16777215;
                  data.grass[index] = data.grass[index] & 0xFF000000 | BiomeColors.GRASS_COLOR_RESOLVER.getColor(biome, cx, cz) & 16777215;
                  data.water[index] = data.water[index] & 0xFF000000 | BiomeColors.WATER_COLOR_RESOLVER.getColor(biome, cx, cz) & 16777215;
               }

               changed = true;
            }

            if (versionChange || blockIndex0 != blockIndex) {
               data.setBlockIndex(index, blockIndex);
               changed = true;
            }
         }

         if (changed) {
            mapChunk.forceUpdate();
         }

         debugLastTime = System.nanoTime() - startTime;
      }
   }

   @Override
   public String toString() {
      return "ChunkUpdateTask@" + this.chunkPos;
   }

   public Holder<Biome> getNoiseBiome(int x, int y, int z) {
      return x >> 2 == this.chunkPos.x && z >> 2 == this.chunkPos.z ? this.chunkAccess.getNoiseBiome(x, y, z) : this.level.getNoiseBiome(x, y, z);
   }
}
