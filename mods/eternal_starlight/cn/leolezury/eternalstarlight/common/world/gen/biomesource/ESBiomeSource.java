package cn.leolezury.eternalstarlight.common.world.gen.biomesource;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.util.FastNoise;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.biome.Climate.TargetPoint;

public class ESBiomeSource extends BiomeSource {
   public static final MapCodec<ESBiomeSource> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            ParameterList.codec(RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("biome_data"))
               .fieldOf("biome_data_parameters")
               .forGetter(o -> o.climateList),
            ESBiomeSource.RiverEntry.CODEC.listOf().fieldOf("rivers").forGetter(o -> o.rivers)
         )
         .apply(instance, instance.stable(ESBiomeSource::new))
   );
   private static final float NOISE_FREQUENCY = 0.005F;
   private static final float RIVER_NOISE_FREQUENCY = 0.001F;
   private static final float RIVER_WARP_NOISE_FREQUENCY = 0.002F;
   private static final int HEIGHT_GRID_SIZE = 10;
   private static final int CACHE_SIZE = 8192;
   private final ParameterList<Holder<BiomeData>> climateList;
   private final List<ESBiomeSource.RiverEntry> rivers;
   private long seed = 0L;
   private final FastNoise[] noises = new FastNoise[]{this.makeNoise(this.seed), this.makeNoise(this.seed * 2L + 5L), this.makeNoise(this.seed * 3L + 10L)};
   private final FastNoise riverNoise = this.makeRiverNoise(this.seed);
   private final FastNoise riverWarpNoise = this.makeRiverWarpNoise(this.seed * 2L + 5L);
   private final Long2ObjectLinkedOpenHashMap<Holder<BiomeData>> biomeCache = new Long2ObjectLinkedOpenHashMap(8193, 0.75F);
   private final Long2ObjectLinkedOpenHashMap<Integer> rawHeightCache = new Long2ObjectLinkedOpenHashMap(8193, 0.75F);
   private final Long2ObjectLinkedOpenHashMap<Integer> heightCache = new Long2ObjectLinkedOpenHashMap(8193, 0.75F);

   public ESBiomeSource(ParameterList<Holder<BiomeData>> climateList, List<ESBiomeSource.RiverEntry> rivers) {
      this.climateList = climateList;
      this.rivers = rivers;
   }

   public void setSeed(long seed) {
      if (this.seed != seed) {
         this.seed = seed;
         this.noises[0].setSeed((int)seed);
         this.noises[1].setSeed((int)(seed * 2L + 5L));
         this.noises[2].setSeed((int)(seed * 3L + 10L));
         this.riverNoise.setSeed((int)seed);
         this.riverWarpNoise.setSeed((int)(seed * 2L + 5L));
         this.biomeCache.clear();
         this.rawHeightCache.clear();
         this.heightCache.clear();
      }
   }

   private FastNoise makeNoise(long seed) {
      FastNoise noise = new FastNoise((int)seed);
      noise.setFrequency(0.005F);
      noise.setFractalType(FastNoise.FractalType.FBM);
      return noise;
   }

   private FastNoise makeRiverNoise(long seed) {
      FastNoise noise = new FastNoise((int)seed);
      noise.setFrequency(0.001F);
      noise.setFractalType(FastNoise.FractalType.FBM);
      noise.setDomainWarpAmp(80.0F);
      return noise;
   }

   private FastNoise makeRiverWarpNoise(long seed) {
      FastNoise noise = new FastNoise((int)seed);
      noise.setFrequency(0.002F);
      return noise;
   }

   protected MapCodec<? extends BiomeSource> codec() {
      return CODEC;
   }

   protected Stream<Holder<Biome>> collectPossibleBiomes() {
      List<Holder<Biome>> transitions = this.rivers
         .stream()
         .map(r -> r.transitionData().map(t -> ((BiomeData)t.value()).biome()))
         .filter(Optional::isPresent)
         .map(Optional::get)
         .toList();
      return Stream.concat(
         this.climateList.values().stream().map(p -> ((BiomeData)((Holder)p.getSecond()).value()).biome()),
         Stream.concat(this.rivers.stream().map(r -> ((BiomeData)r.riverData().value()).biome()), transitions.stream())
      );
   }

   public Holder<BiomeData> getBiomeData(int blockX, int blockY, int blockZ, Sampler sampler) {
      int gx = blockX >> 2;
      int gy = blockY >> 2;
      int gz = blockZ >> 2;
      long key = posAsLong(gx, gz);
      if (blockY == 48) {
         synchronized (this.biomeCache) {
            Holder<BiomeData> cached = (Holder<BiomeData>)this.biomeCache.get(key);
            if (cached != null) {
               return cached;
            }
         }
      }

      Holder<BiomeData> result = this.computeBiomeData(gx << 2, gy << 2, gz << 2, sampler);
      if (blockY == 48) {
         synchronized (this.biomeCache) {
            this.biomeCache.putAndMoveToLast(key, result);
            if (this.biomeCache.size() > 8192) {
               this.biomeCache.removeFirst();
            }
         }
      }

      return result;
   }

   private Holder<BiomeData> computeBiomeData(int bx, int by, int bz, Sampler sampler) {
      TargetPoint target = sampler.sample(bx >> 2, by >> 2, bz >> 2);
      Holder<BiomeData> base = (Holder<BiomeData>)this.climateList.findValue(target);
      BiomeData value = (BiomeData)base.value();
      if (value.hasRivers() && !this.rivers.isEmpty()) {
         for (ESBiomeSource.RiverEntry river : this.rivers) {
            boolean isOcean = value.isOcean();
            if ((!isOcean || river.canGenerateInOcean()) && (isOcean || !river.canGenerateInOceanOnly())) {
               int off = river.offset();
               int riverX = bx + off;
               int riverZ = bz + off;
               this.riverNoise.domainWarp(new FastNoise.Vector2(riverX, riverZ));
               float noiseValue = this.riverNoise.getNoise(riverX, riverZ);
               float delta = 1.0F;
               float nx1 = this.riverNoise.getNoise(riverX + delta, riverZ);
               float nx2 = this.riverNoise.getNoise(riverX - delta, riverZ);
               float nz1 = this.riverNoise.getNoise(riverX, riverZ + delta);
               float nz2 = this.riverNoise.getNoise(riverX, riverZ - delta);
               float gradX = (nx1 - nx2) / (2.0F * delta);
               float gradZ = (nz1 - nz2) / (2.0F * delta);
               float gradLength = (float)Math.sqrt(gradX * gradX + gradZ * gradZ);
               if (gradLength < 1.0E-4F) {
                  gradLength = 1.0E-4F;
               }

               double riverValue = Math.abs(noiseValue) / gradLength;
               if (riverValue < river.size()) {
                  return river.riverData();
               }

               if (riverValue < river.transitionSize() && river.transitionData().isPresent()) {
                  return river.transitionData().get();
               }
            }
         }
      }

      return base;
   }

   public int getHeight(int blockX, int blockZ, Sampler sampler) {
      long key = posAsLong(blockX, blockZ);
      synchronized (this.heightCache) {
         Integer cached = (Integer)this.heightCache.get(key);
         if (cached != null) {
            return cached;
         }
      }

      int result = this.computeHeight(blockX, blockZ, sampler);
      synchronized (this.heightCache) {
         this.heightCache.putAndMoveToLast(key, result);
         if (this.heightCache.size() > 8192) {
            this.heightCache.removeFirst();
         }

         return result;
      }
   }

   private int computeHeight(int blockX, int blockZ, Sampler sampler) {
      float totalWeight = 0.0F;
      float totalHeight = 0.0F;

      for (int i = -10; i <= 10; i++) {
         for (int j = -10; j <= 10; j++) {
            if (i * i + j * j < 100) {
               totalHeight += this.getRawHeight(blockX + i, blockZ + j, sampler) * (10.0F - Mth.sqrt(i * i + j * j));
               totalWeight += 10.0F - Mth.sqrt(i * i + j * j);
            }
         }
      }

      return Math.round(totalHeight / totalWeight);
   }

   private int getRawHeight(int bx, int bz, Sampler sampler) {
      long key = posAsLong(bx, bz);
      synchronized (this.rawHeightCache) {
         Integer cached = (Integer)this.rawHeightCache.get(key);
         if (cached != null) {
            return cached;
         }
      }

      int h = this.computeRawHeight(bx, bz, sampler);
      synchronized (this.rawHeightCache) {
         this.rawHeightCache.putAndMoveToLast(key, h);
         if (this.rawHeightCache.size() > 8192) {
            this.rawHeightCache.removeFirst();
         }

         return h;
      }
   }

   private int computeRawHeight(int bx, int bz, Sampler sampler) {
      BiomeData data = (BiomeData)this.getBiomeData(bx, 48, bz, sampler).value();
      int base = data.height();
      int variance = data.variance();
      if (variance > 0) {
         float n = this.noises[0].getNoise(bx, bz) * 0.7F
            + this.noises[1].getNoise(bx * 0.4F, bz * 0.4F) * 0.2F
            + this.noises[0].getNoise(bx * 0.15F, bz * 0.15F) * 0.1F;
         base += (int)(n * variance);
      }

      return base;
   }

   public static long posAsLong(int x, int z) {
      return (long)x << 32 | z & 4294967295L;
   }

   public Holder<Biome> getNoiseBiome(int x, int y, int z, Sampler sampler) {
      return ((BiomeData)this.getBiomeData(x << 2, y << 2, z << 2, sampler).value()).biome();
   }

   public record RiverEntry(
      Holder<BiomeData> riverData,
      float size,
      Optional<Holder<BiomeData>> transitionData,
      float transitionSize,
      int offset,
      boolean canGenerateInOcean,
      boolean canGenerateInOceanOnly
   ) {
      public static final Codec<ESBiomeSource.RiverEntry> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("river").forGetter(ESBiomeSource.RiverEntry::riverData),
               Codec.FLOAT.fieldOf("size").forGetter(ESBiomeSource.RiverEntry::size),
               RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC)
                  .optionalFieldOf("transition")
                  .forGetter(ESBiomeSource.RiverEntry::transitionData),
               Codec.FLOAT.optionalFieldOf("transition_size", 0.0F).forGetter(ESBiomeSource.RiverEntry::transitionSize),
               Codec.INT.fieldOf("offset").forGetter(ESBiomeSource.RiverEntry::offset),
               Codec.BOOL.optionalFieldOf("can_generate_in_ocean", false).forGetter(ESBiomeSource.RiverEntry::canGenerateInOcean),
               Codec.BOOL.optionalFieldOf("can_generate_in_ocean_only", false).forGetter(ESBiomeSource.RiverEntry::canGenerateInOceanOnly)
            )
            .apply(instance, ESBiomeSource.RiverEntry::new)
      );
   }
}
