package com.finndog.moogs_structures.world.structures.placements;

import com.finndog.moogs_structures.modinit.MoogsStructuresStructurePlacementType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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

public class AdvancedRandomSpread extends RandomSpreadStructurePlacement {
   public static final MapCodec<AdvancedRandomSpread> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(rec$ -> rec$.locateOffset()),
            FrequencyReductionMethod.CODEC
               .optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT)
               .forGetter(rec$ -> rec$.frequencyReductionMethod()),
            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(rec$ -> rec$.frequency()),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter(rec$ -> rec$.salt()),
            ExclusionZone.CODEC.optionalFieldOf("exclusion_zone").forGetter(rec$ -> rec$.exclusionZone()),
            AdvancedRandomSpread.SuperExclusionZone.CODEC.optionalFieldOf("super_exclusion_zone").forGetter(AdvancedRandomSpread::superExclusionZone),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("spacing").forGetter(AdvancedRandomSpread::spacing),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("separation").forGetter(AdvancedRandomSpread::separation),
            RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(AdvancedRandomSpread::spreadType),
            Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("min_distance_from_world_origin").forGetter(AdvancedRandomSpread::minDistanceFromWorldOrigin)
         )
         .apply(instance, instance.stable(AdvancedRandomSpread::new))
   );
   private final int spacing;
   private final int separation;
   private final RandomSpreadType spreadType;
   private final Optional<Integer> minDistanceFromWorldOrigin;
   private final Optional<AdvancedRandomSpread.SuperExclusionZone> superExclusionZone;

   public AdvancedRandomSpread(
      Vec3i locationOffset,
      FrequencyReductionMethod frequencyReductionMethod,
      float frequency,
      int salt,
      Optional<ExclusionZone> exclusionZone,
      Optional<AdvancedRandomSpread.SuperExclusionZone> superExclusionZone,
      int spacing,
      int separation,
      RandomSpreadType spreadType,
      Optional<Integer> minDistanceFromWorldOrigin
   ) {
      super(locationOffset, frequencyReductionMethod, frequency, salt, exclusionZone, spacing, separation, spreadType);
      this.spacing = (int)Math.round(spacing * 1.65);
      this.separation = (int)Math.round(separation * 1.65);
      this.spreadType = spreadType;
      this.minDistanceFromWorldOrigin = minDistanceFromWorldOrigin;
      this.superExclusionZone = superExclusionZone;
      if (spacing <= separation) {
         throw new RuntimeException(
            "    Moog's Structure Lib: Spacing cannot be less or equal to separation.\n    Please correct this error as there's no way to spawn this structure properly\n        Spacing: %s\n        Separation: %s.\n"
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

   public Optional<AdvancedRandomSpread.SuperExclusionZone> superExclusionZone() {
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
         int xBlockPos = x * 16;
         int zBlockPos = z * 16;
         if (xBlockPos * xBlockPos + zBlockPos * zBlockPos < this.minDistanceFromWorldOrigin.get() * this.minDistanceFromWorldOrigin.get()) {
            return false;
         }
      }

      ChunkPos chunkpos = this.getPotentialStructureChunk(chunkGeneratorStructureState.getLevelSeed(), x, z);
      return chunkpos.x == x && chunkpos.z == z;
   }

   public StructurePlacementType<?> type() {
      return MoogsStructuresStructurePlacementType.ADVANCED_RANDOM_SPREAD.get();
   }

   public record SuperExclusionZone(HolderSet<StructureSet> otherSet, int chunkCount, Optional<Integer> allowedChunkCount) {
      private static final ThreadLocal<Set<ResourceLocation>> EVALUATING_SETS = ThreadLocal.withInitial(HashSet::new);
      public static final Codec<AdvancedRandomSpread.SuperExclusionZone> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
               RegistryCodecs.homogeneousList(Registries.STRUCTURE_SET, StructureSet.DIRECT_CODEC)
                  .fieldOf("other_set")
                  .forGetter(AdvancedRandomSpread.SuperExclusionZone::otherSet),
               Codec.intRange(1, Integer.MAX_VALUE).fieldOf("chunk_count").forGetter(AdvancedRandomSpread.SuperExclusionZone::chunkCount),
               Codec.intRange(1, Integer.MAX_VALUE)
                  .optionalFieldOf("allowed_chunk_count")
                  .forGetter(AdvancedRandomSpread.SuperExclusionZone::allowedChunkCount)
            )
            .apply(builder, AdvancedRandomSpread.SuperExclusionZone::new)
      );

      boolean isPlacementForbidden(ChunkGeneratorStructureState chunkGeneratorStructureState, int l, int j) {
         Set<ResourceLocation> evaluating = EVALUATING_SETS.get();

         for (Holder<StructureSet> holder : this.otherSet) {
            ResourceLocation setId = holder.unwrapKey().map(key -> key.location()).orElse(null);
            if (setId != null && !evaluating.contains(setId)) {
               evaluating.add(setId);

               try {
                  if (chunkGeneratorStructureState.hasStructureChunkInRange(holder, l, j, this.chunkCount)) {
                     return true;
                  }
               } finally {
                  evaluating.remove(setId);
               }
            }
         }

         if (this.allowedChunkCount.isPresent() && this.allowedChunkCount.get() > this.chunkCount) {
            boolean isAnyInRange = false;

            for (Holder<StructureSet> holder : this.otherSet) {
               ResourceLocation setId = holder.unwrapKey().map(key -> key.location()).orElse(null);
               if (setId != null && !evaluating.contains(setId)) {
                  evaluating.add(setId);

                  try {
                     if (chunkGeneratorStructureState.hasStructureChunkInRange(holder, l, j, this.allowedChunkCount.get())) {
                        isAnyInRange = true;
                     }
                  } finally {
                     evaluating.remove(setId);
                  }
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
