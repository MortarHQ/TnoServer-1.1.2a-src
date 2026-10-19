package twilightforest.world.components.structures.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.LandmarkStructure;

public class LandmarkWrappedStructure extends LandmarkStructure {
   public static final MapCodec<LandmarkWrappedStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Structure.DIRECT_CODEC.fieldOf("wrapped").forGetter(o -> o.wrappedStructure))
         .and(landmarkCodecNoSettings(instance))
         .apply(instance, LandmarkWrappedStructure::new)
   );
   private final Structure wrappedStructure;

   public LandmarkWrappedStructure(
      Structure wrappedStructure,
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon
   ) {
      super(decorationConfig, centerInChunk, structureIcon, new StructureSettings(HolderSet.empty()));
      this.wrappedStructure = wrappedStructure;
   }

   @Override
   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      return this.wrappedStructure.findGenerationPoint(context);
   }

   @Nullable
   @Override
   protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
      return null;
   }

   public HolderSet<Biome> biomes() {
      return this.wrappedStructure.biomes();
   }

   public Map<MobCategory, StructureSpawnOverride> spawnOverrides() {
      return this.wrappedStructure.spawnOverrides();
   }

   public Decoration step() {
      return this.wrappedStructure.step();
   }

   public TerrainAdjustment terrainAdaptation() {
      return this.wrappedStructure.terrainAdaptation();
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.LANDMARK_WRAPPED.get();
   }
}
