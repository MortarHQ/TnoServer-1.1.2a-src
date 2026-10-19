package com.github.L_Ender.cataclysm.world.structures.placements;

import com.github.L_Ender.cataclysm.init.ModStructurePlacementType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.ExclusionZone;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.FrequencyReductionMethod;

public class CataclysmRandomSpread extends RandomSpreadStructurePlacement {
   public static final MapCodec<CataclysmRandomSpread> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(rec$ -> rec$.locateOffset()),
            FrequencyReductionMethod.CODEC
               .optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT)
               .forGetter(rec$ -> rec$.frequencyReductionMethod()),
            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(rec$ -> rec$.frequency()),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter(rec$ -> rec$.salt()),
            ExclusionZone.CODEC.optionalFieldOf("exclusion_zone").forGetter(rec$ -> rec$.exclusionZone()),
            CataclysmRandomSpread.SuperExclusionZone.CODEC.optionalFieldOf("super_exclusion_zone").forGetter(CataclysmRandomSpread::superExclusionZone),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("spacing").forGetter(CataclysmRandomSpread::spacing),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("separation").forGetter(CataclysmRandomSpread::separation),
            RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(CataclysmRandomSpread::spreadType),
            Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("min_distance_from_world_origin").forGetter(CataclysmRandomSpread::minDistanceFromWorldOrigin)
         )
         .apply(instance, instance.stable(CataclysmRandomSpread::new))
   );
   private final int spacing;
   private final int separation;
   private final RandomSpreadType spreadType;
   private final Optional<Integer> minDistanceFromWorldOrigin;
   private final Optional<CataclysmRandomSpread.SuperExclusionZone> superExclusionZone;

   public CataclysmRandomSpread(
      Vec3i locationOffset,
      FrequencyReductionMethod frequencyReductionMethod,
      float frequency,
      int salt,
      Optional<ExclusionZone> exclusionZone,
      Optional<CataclysmRandomSpread.SuperExclusionZone> superExclusionZone,
      int spacing,
      int separation,
      RandomSpreadType spreadType,
      Optional<Integer> minDistanceFromWorldOrigin
   ) {
      super(locationOffset, frequencyReductionMethod, frequency, salt, exclusionZone, spacing, separation, spreadType);
      this.spacing = spacing;
      this.separation = separation;
      this.spreadType = spreadType;
      this.minDistanceFromWorldOrigin = minDistanceFromWorldOrigin;
      this.superExclusionZone = superExclusionZone;
      if (spacing <= separation) {
         throw new RuntimeException(
            "    Repurposed Structures: Spacing cannot be less or equal to separation.\n    Please correct this error as there's no way to spawn this structure properly\n        Spacing: %s\n        Separation: %s.\n"
               .formatted(spacing, separation)
         );
      }
   }

   public int spacing() {
      return this.spacing;
   }

   public int separation() {
      return this.separation;
   }

   public RandomSpreadType spreadType() {
      return this.spreadType;
   }

   public Optional<Integer> minDistanceFromWorldOrigin() {
      return this.minDistanceFromWorldOrigin;
   }

   public Optional<CataclysmRandomSpread.SuperExclusionZone> superExclusionZone() {
      return this.superExclusionZone;
   }

   public boolean isStructureChunk(ChunkGeneratorStructureState chunkGeneratorStructureState, int i, int j) {
      return !super.isStructureChunk(chunkGeneratorStructureState, i, j)
         ? false
         : this.superExclusionZone.isEmpty() || !this.superExclusionZone.get().isPlacementForbidden(chunkGeneratorStructureState, i, j);
   }

   public ChunkPos getPotentialStructureChunk(long seed, int x, int z) {
      int regionX = Math.floorDiv(x, this.spacing);
      int regionZ = Math.floorDiv(z, this.spacing);
      WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
      worldgenrandom.setLargeFeatureWithSalt(seed, regionX, regionZ, this.salt());
      int diff = this.spacing - this.separation;
      int offsetX = this.spreadType.evaluate(worldgenrandom, diff);
      int offsetZ = this.spreadType.evaluate(worldgenrandom, diff);
      return new ChunkPos(regionX * this.spacing + offsetX, regionZ * this.spacing + offsetZ);
   }

   protected boolean isPlacementChunk(ChunkGeneratorStructureState chunkGeneratorStructureState, int x, int z) {
      if (this.minDistanceFromWorldOrigin.isPresent()) {
         long xBlockPos = x * 16L;
         long zBlockPos = z * 16L;
         if (xBlockPos * xBlockPos + zBlockPos * zBlockPos
            < (long)this.minDistanceFromWorldOrigin.get().intValue() * this.minDistanceFromWorldOrigin.get().intValue()) {
            return false;
         }
      }

      ChunkPos chunkpos = this.getPotentialStructureChunk(chunkGeneratorStructureState.getLevelSeed(), x, z);
      return chunkpos.x == x && chunkpos.z == z;
   }

   public StructurePlacementType<?> type() {
      return (StructurePlacementType<?>)ModStructurePlacementType.ADVANCED_RANDOM_SPREAD.get();
   }

   public record SuperExclusionZone(HolderSet<StructureSet> otherSet, int chunkCount, Optional<Integer> allowedChunkCount) {
      public static final Codec<CataclysmRandomSpread.SuperExclusionZone> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
               RegistryCodecs.homogeneousList(Registries.STRUCTURE_SET, StructureSet.DIRECT_CODEC)
                  .fieldOf("other_set")
                  .forGetter(CataclysmRandomSpread.SuperExclusionZone::otherSet),
               Codec.intRange(1, Integer.MAX_VALUE).fieldOf("chunk_count").forGetter(CataclysmRandomSpread.SuperExclusionZone::chunkCount),
               Codec.intRange(1, Integer.MAX_VALUE)
                  .optionalFieldOf("allowed_chunk_count")
                  .forGetter(CataclysmRandomSpread.SuperExclusionZone::allowedChunkCount)
            )
            .apply(builder, CataclysmRandomSpread.SuperExclusionZone::new)
      );

      boolean isPlacementForbidden(ChunkGeneratorStructureState chunkGeneratorStructureState, int l, int j) {
         for (Holder<StructureSet> holder : this.otherSet) {
            if (chunkGeneratorStructureState.hasStructureChunkInRange(holder, l, j, this.chunkCount)) {
               return true;
            }
         }

         if (this.allowedChunkCount.isPresent() && this.allowedChunkCount.get() > this.chunkCount) {
            boolean isAnyInRange = false;

            for (Holder<StructureSet> holder : this.otherSet) {
               if (chunkGeneratorStructureState.hasStructureChunkInRange(holder, l, j, this.allowedChunkCount.get())) {
                  isAnyInRange = true;
               }
            }

            if (!isAnyInRange) {
               return false;
            }
         }

         return false;
      }
   }
}
