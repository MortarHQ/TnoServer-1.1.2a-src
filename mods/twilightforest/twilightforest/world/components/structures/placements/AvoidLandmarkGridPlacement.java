package twilightforest.world.components.structures.placements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.ExclusionZone;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.FrequencyReductionMethod;
import twilightforest.init.TFStructurePlacementTypes;
import twilightforest.util.landmarks.LegacyLandmarkPlacements;

public class AvoidLandmarkGridPlacement extends RandomSpreadStructurePlacement {
   public static final MapCodec<AvoidLandmarkGridPlacement> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(rec$ -> rec$.locateOffset()),
               FrequencyReductionMethod.CODEC
                  .optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT)
                  .forGetter(rec$ -> rec$.frequencyReductionMethod()),
               Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(rec$ -> rec$.frequency()),
               ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter(rec$ -> rec$.salt()),
               ExclusionZone.CODEC.optionalFieldOf("exclusion_zone").forGetter(rec$ -> rec$.exclusionZone()),
               Codec.intRange(0, 4096).fieldOf("spacing").forGetter(RandomSpreadStructurePlacement::spacing),
               Codec.intRange(0, 4096).fieldOf("separation").forGetter(RandomSpreadStructurePlacement::separation),
               RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(RandomSpreadStructurePlacement::spreadType),
               AvoidLandmarkGridPlacement.AvoidAdditionalStructures.CODEC
                  .optionalFieldOf("avoid_additional_structures")
                  .forGetter(AvoidLandmarkGridPlacement::otherStructuresToAvoid)
            )
            .apply(instance, AvoidLandmarkGridPlacement::new)
      )
      .validate(AvoidLandmarkGridPlacement::validate);
   private final Optional<AvoidLandmarkGridPlacement.AvoidAdditionalStructures> avoidOtherStructures;

   private static DataResult<AvoidLandmarkGridPlacement> validate(AvoidLandmarkGridPlacement placement) {
      return placement.spacing() <= placement.separation() ? DataResult.error(() -> "Spacing has to be larger than separation") : DataResult.success(placement);
   }

   public AvoidLandmarkGridPlacement(
      int spacing, int separation, RandomSpreadType spreadType, int salt, Optional<AvoidLandmarkGridPlacement.AvoidAdditionalStructures> avoidOtherStructures
   ) {
      super(spacing, separation, spreadType, salt);
      this.avoidOtherStructures = avoidOtherStructures;
   }

   public AvoidLandmarkGridPlacement(
      Vec3i locateOffset,
      FrequencyReductionMethod reduction,
      float frequencyModifier,
      int salt,
      Optional<ExclusionZone> exclusionZone,
      int spacing,
      int separation,
      RandomSpreadType spreadType,
      Optional<AvoidLandmarkGridPlacement.AvoidAdditionalStructures> avoidOtherStructures
   ) {
      super(locateOffset, reduction, frequencyModifier, salt, exclusionZone, spacing, separation, spreadType);
      this.avoidOtherStructures = avoidOtherStructures;
   }

   protected boolean isPlacementChunk(ChunkGeneratorStructureState state, int chunkX, int chunkZ) {
      ChunkPos chunkpos = this.getPotentialStructureChunk(state.getLevelSeed(), chunkX, chunkZ);
      if (chunkpos.x == chunkX && chunkpos.z == chunkZ) {
         MutableBlockPos featurePos = LegacyLandmarkPlacements.getNearestCenterXZ(chunkX, chunkZ).mutable();
         featurePos.set(Math.abs(featurePos.getX() - chunkpos.getWorldPosition().getX()), 0, Math.abs(featurePos.getZ() - chunkpos.getWorldPosition().getZ()));
         int size = 80;
         return featurePos.getX() >= size || featurePos.getZ() >= size;
      } else {
         return false;
      }
   }

   public boolean applyInteractionsWithOtherStructures(ChunkGeneratorStructureState structureState, int x, int z) {
      return super.applyInteractionsWithOtherStructures(structureState, x, z)
         && (this.avoidOtherStructures.isEmpty() || !this.avoidOtherStructures.get().isPlacementForbidden(structureState, x, z));
   }

   private Optional<AvoidLandmarkGridPlacement.AvoidAdditionalStructures> otherStructuresToAvoid() {
      return this.avoidOtherStructures;
   }

   public StructurePlacementType<?> type() {
      return (StructurePlacementType<?>)TFStructurePlacementTypes.AVOID_GRID_LANDMARK_PLACEMENT_TYPE.get();
   }

   public record AvoidAdditionalStructures(Object2IntArrayMap<Holder<StructureSet>> avoidStructures) {
      public static final Codec<AvoidLandmarkGridPlacement.AvoidAdditionalStructures> CODEC = Codec.unboundedMap(StructureSet.CODEC, Codec.intRange(0, 16))
         .xmap(
            map -> new AvoidLandmarkGridPlacement.AvoidAdditionalStructures(new Object2IntArrayMap(map)),
            AvoidLandmarkGridPlacement.AvoidAdditionalStructures::avoidStructures
         );

      private boolean isPlacementForbidden(ChunkGeneratorStructureState structureState, int x, int z) {
         ObjectIterator var4 = this.avoidStructures.object2IntEntrySet().iterator();

         while (var4.hasNext()) {
            Entry<Holder<StructureSet>> structureSetEntry = (Entry<Holder<StructureSet>>)var4.next();
            if (structureState.hasStructureChunkInRange((Holder)structureSetEntry.getKey(), x, z, structureSetEntry.getIntValue())) {
               return true;
            }
         }

         return false;
      }
   }
}
