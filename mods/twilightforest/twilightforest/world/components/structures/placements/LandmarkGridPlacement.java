package twilightforest.world.components.structures.placements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.FrequencyReductionMethod;
import twilightforest.init.TFStructurePlacementTypes;
import twilightforest.util.landmarks.LegacyLandmarkPlacements;

public class LandmarkGridPlacement extends StructurePlacement {
   public static final MapCodec<LandmarkGridPlacement> CODEC = RecordCodecBuilder.mapCodec(
      inst -> inst.group(ResourceKey.codec(Registries.STRUCTURE).optionalFieldOf("structure_grid_lock").forGetter(p -> p.landmark))
         .apply(inst, LandmarkGridPlacement::new)
   );
   private final Optional<ResourceKey<Structure>> landmark;

   public static LandmarkGridPlacement forceStructureForCenters() {
      return new LandmarkGridPlacement(Optional.empty());
   }

   public LandmarkGridPlacement(Optional<ResourceKey<Structure>> landmark) {
      super(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1.0F, 0, Optional.empty());
      this.landmark = landmark;
   }

   protected boolean isPlacementChunk(ChunkGeneratorStructureState state, int chunkX, int chunkZ) {
      return !LegacyLandmarkPlacements.chunkHasLandmarkCenter(chunkX, chunkZ)
         ? false
         : this.landmark.isEmpty() || LegacyLandmarkPlacements.pickVarietyLandmark(chunkX, chunkZ) == this.landmark.get();
   }

   public StructurePlacementType<?> type() {
      return (StructurePlacementType<?>)TFStructurePlacementTypes.GRID_LANDMARK_PLACEMENT_TYPE.get();
   }
}
