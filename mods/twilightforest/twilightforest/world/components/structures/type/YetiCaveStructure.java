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
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
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
import twilightforest.world.components.chunkgenerators.AbsoluteDifferenceFunction;
import twilightforest.world.components.structures.CustomDensitySource;
import twilightforest.world.components.structures.StructureSpeleothemConfig;
import twilightforest.world.components.structures.YetiCaveComponent;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.ControlledSpawningStructure;
import twilightforest.world.components.structures.util.ControlledSpawns;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.StructureHints;

public class YetiCaveStructure extends ControlledSpawningStructure implements CustomDensitySource {
   public static final MapCodec<YetiCaveStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> controlledSpawningCodec(instance)
         .and(StructureSpeleothemConfigs.CODEC.fieldOf("speleothem_config").forGetter(s -> s.speleothemConfig))
         .apply(instance, YetiCaveStructure::new)
   );
   private final Reference<StructureSpeleothemConfig> speleothemConfig;

   public YetiCaveStructure(
      ControlledSpawns.ControlledSpawningConfig controlledSpawningConfig,
      AdvancementLockedStructure.AdvancementLockConfig advancementLockConfig,
      Optional<StructureHints.HintConfig> hintConfig,
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings,
      Holder<StructureSpeleothemConfig> speleothemConfig
   ) {
      super(controlledSpawningConfig, advancementLockConfig, hintConfig, decorationConfig, centerInChunk, structureIcon, structureSettings);
      this.speleothemConfig = (Reference<StructureSpeleothemConfig>)speleothemConfig;
   }

   @Nullable
   @Override
   protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
      return new YetiCaveComponent(0, x, y + 4, z, this.speleothemConfig);
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.YETI_CAVE.get();
   }

   public static YetiCaveStructure buildYetiCaveConfig(BootstrapContext<Structure> context) {
      return new YetiCaveStructure(
         ControlledSpawns.ControlledSpawningConfig.firstIndexMonsters(new SpawnerData((EntityType)TFEntities.YETI.get(), 5, 1, 2)),
         new AdvancementLockedStructure.AdvancementLockConfig(List.of(TwilightForestMod.prefix("progress_lich"))),
         Optional.of(new StructureHints.HintConfig(StructureHints.HintConfig.book("yeticave", 3), (EntityType<? extends Mob>)TFEntities.KOBOLD.get())),
         Optional.of(new DecorationClearance.DecorationConfig(2.0F, true, false, false)),
         false,
         Optional.of(TFMapDecorations.YETI_LAIR),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_YETI_CAVE_BIOMES),
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.NONE
         ),
         context.lookup(TFRegistries.Keys.STRUCTURE_SPELEOTHEM_SETTINGS).getOrThrow(StructureSpeleothemConfigs.YETI_CAVE)
      );
   }

   @Override
   public DensityFunction getStructureTerraformer(ChunkPos chunkPosAt, StructureStart structurePieceSource) {
      int riser = 5;
      BlockPos centerPos = structurePieceSource.getBoundingBox().getCenter().above(riser);
      DensityFunction exteriorTrapezoidSq = DensityFunctions.add(
         DensityFunctions.mul(DensityFunctions.constant(-1.0), AbsoluteDifferenceFunction.max(40.0, centerPos)),
         DensityFunctions.add(
            DensityFunctions.yClampedGradient(0, 24 + riser, 40.0, 32.0), DensityFunctions.yClampedGradient(21 + riser, 22 + riser, 0.0, -100000.0)
         )
      );
      int deltaToGround = 8;
      int deltaToAir = 4;
      int yPosTop = 14 + riser;
      int yPosBottom = -3 + riser;
      DensityFunction floorCeiling = DensityFunctions.add(
         DensityFunctions.mul(
            DensityFunctions.constant(32.0),
            DensityFunctions.max(
               DensityFunctions.yClampedGradient(yPosTop - deltaToAir, yPosTop + deltaToGround, -1.0, 1.0),
               DensityFunctions.yClampedGradient(yPosBottom - deltaToGround, yPosBottom + deltaToAir, 1.0, -1.0)
            )
         ),
         DensityFunctions.add(DensityFunctions.constant(0.0), AbsoluteDifferenceFunction.min(64.0, centerPos)).clamp(1.0, 64.0)
      );
      DensityFunction walls = DensityFunctions.yClampedGradient(-3 + riser, 21 + riser, -9.5, -2.5);
      DensityFunction interiorCarver = DensityFunctions.min(
         AbsoluteDifferenceFunction.min(64.0, centerPos),
         DensityFunctions.add(DensityFunctions.constant(-20.0), AbsoluteDifferenceFunction.max(64.0, centerPos))
      );
      DensityFunction shapedInterior = DensityFunctions.add(walls, interiorCarver);
      DensityFunction interior = DensityFunctions.max(floorCeiling, shapedInterior);
      DensityFunction entranceSlope = DensityFunctions.add(
         DensityFunctions.yClampedGradient(-8, 16 + riser, -46.0, -48.0), AbsoluteDifferenceFunction.max(64.0, centerPos)
      );
      DensityFunction interiorWithSmoothedEntrances = DensityFunctions.max(
         interior, DensityFunctions.mul(DensityFunctions.constant(0.015625), DensityFunctions.add(DensityFunctions.constant(15.0), entranceSlope))
      );
      return DensityFunctions.lerp(
         DensityFunctions.mul(DensityFunctions.constant(0.020833334F), exteriorTrapezoidSq).clamp(0.0, 1.0),
         DensityFunctions.zero(),
         DensityFunctions.mul(DensityFunctions.constant(16.0), interiorWithSmoothedEntrances)
      );
   }
}
