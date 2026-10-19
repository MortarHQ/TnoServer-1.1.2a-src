package twilightforest.world.components.structures.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride.BoundingBoxType;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import org.jetbrains.annotations.Nullable;
import twilightforest.TFRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFMapDecorations;
import twilightforest.init.TFStructureTypes;
import twilightforest.init.custom.StructureSpeleothemConfigs;
import twilightforest.world.components.chunkgenerators.FocusedDensityFunction;
import twilightforest.world.components.chunkgenerators.HollowHillFunction;
import twilightforest.world.components.structures.CustomDensitySource;
import twilightforest.world.components.structures.HydraLairComponent;
import twilightforest.world.components.structures.StructureSpeleothemConfig;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.ProgressionStructure;
import twilightforest.world.components.structures.util.StructureHints;

public class HydraLairStructure extends ProgressionStructure implements CustomDensitySource {
   public static final MapCodec<HydraLairStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> progressionCodec(instance)
         .and(StructureSpeleothemConfigs.CODEC.fieldOf("speleothem_config").forGetter(s -> s.speleothemConfig))
         .apply(instance, HydraLairStructure::new)
   );
   private final Reference<StructureSpeleothemConfig> speleothemConfig;

   public HydraLairStructure(
      AdvancementLockedStructure.AdvancementLockConfig advancementLockConfig,
      Optional<StructureHints.HintConfig> hintConfig,
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings,
      Holder<StructureSpeleothemConfig> speleothemConfig
   ) {
      super(advancementLockConfig, hintConfig, decorationConfig, centerInChunk, structureIcon, structureSettings);
      this.speleothemConfig = (Reference<StructureSpeleothemConfig>)speleothemConfig;
   }

   @Nullable
   @Override
   protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
      return new HydraLairComponent(0, x - 7, y, z - 7, this.speleothemConfig);
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.HYDRA_LAIR.get();
   }

   public static HydraLairStructure buildHydraLairConfig(BootstrapContext<Structure> context) {
      return new HydraLairStructure(
         new AdvancementLockedStructure.AdvancementLockConfig(List.of(TwilightForestMod.prefix("progress_labyrinth"))),
         Optional.of(new StructureHints.HintConfig(StructureHints.HintConfig.book("hydralair", 4), (EntityType<? extends Mob>)TFEntities.KOBOLD.get())),
         Optional.of(new DecorationClearance.DecorationConfig(2.0F, false, false, false)),
         true,
         Optional.of(TFMapDecorations.HYDRA_LAIR),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_HYDRA_LAIR_BIOMES),
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.NONE
         ),
         context.lookup(TFRegistries.Keys.STRUCTURE_SPELEOTHEM_SETTINGS).getOrThrow(StructureSpeleothemConfigs.HYDRA_LAIR)
      );
   }

   @Override
   public DensityFunction getStructureTerraformer(ChunkPos chunkSliceAt, StructureStart structurePieceSource) {
      int hillSize = 2;
      float radius = 70.4F;
      float radiusInner = 62.4F;
      BoundingBox structureBox = structurePieceSource.getBoundingBox();
      int width = Math.min(structureBox.getXSpan(), structureBox.getZSpan());
      int yCeilingFocus = structureBox.minY();
      BlockPos hillCenter = structureBox.getCenter();
      DensityFunction hillMound = HollowHillFunction.fromPos(hillCenter.atY(yCeilingFocus + 8), 70.4F, 0.7F).clamp(0.0, 1.0);
      DensityFunction innerFloor = DensityFunctions.yClampedGradient(-1, 0, 1.0, -4.0);
      DensityFunction innerCeiling = DensityFunctions.mul(
         DensityFunctions.constant(-1.0), HollowHillFunction.fromPos(hillCenter.atY(yCeilingFocus + 6), 62.4F, 0.675F)
      );
      DensityFunction interior = DensityFunctions.max(innerCeiling, innerFloor);
      DensityFunction interiorMask = FocusedDensityFunction.fromPos(hillCenter.atY(yCeilingFocus), 32.447998F, -62.4F, 1.0F);
      DensityFunction interiorMasked = DensityFunctions.max(interiorMask, interior);
      DensityFunction hollowHill = DensityFunctions.min(hillMound, interiorMasked);
      DensityFunction maskingSphere = FocusedDensityFunction.fromPos(hillCenter.below(Mth.ceil(7.040000152587891)), width * 0.5F + 5.0F, width * 0.25F, 0.0F)
         .clamp(0.0, 1.0);
      DensityFunction cutout = FocusedDensityFunction.fromPos(hillCenter.offset(-16, 0, -16), 23.0F, -23.0F, 0.0F).clamp(-4.0, 0.0);
      DensityFunction hillMasked = DensityFunctions.mul(maskingSphere, hollowHill);
      DensityFunction lair = DensityFunctions.add(hillMasked, DensityFunctions.max(cutout, innerFloor).clamp(-2.0, 0.0));
      return DensityFunctions.mul(DensityFunctions.constant(8.0), lair);
   }
}
