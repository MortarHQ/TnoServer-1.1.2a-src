package twilightforest.world.components.structures.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFMapDecorations;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.chunkgenerators.AbsoluteDifferenceFunction;
import twilightforest.world.components.chunkgenerators.FocusedDensityFunction;
import twilightforest.world.components.chunkgenerators.HollowHillFunction;
import twilightforest.world.components.structures.CustomDensitySource;
import twilightforest.world.components.structures.minotaurmaze.MazeRuinsComponent;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.ConfigurableSpawns;
import twilightforest.world.components.structures.util.ControlledSpawningStructure;
import twilightforest.world.components.structures.util.ControlledSpawns;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.StructureHints;

public class LabyrinthStructure extends ControlledSpawningStructure implements ConfigurableSpawns, CustomDensitySource {
   public static final MapCodec<LabyrinthStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> controlledSpawningCodec(instance).apply(instance, LabyrinthStructure::new)
   );

   public LabyrinthStructure(
      ControlledSpawns.ControlledSpawningConfig controlledSpawningConfig,
      AdvancementLockedStructure.AdvancementLockConfig advancementLockConfig,
      Optional<StructureHints.HintConfig> hintConfig,
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings
   ) {
      super(controlledSpawningConfig, advancementLockConfig, hintConfig, decorationConfig, centerInChunk, structureIcon, structureSettings);
   }

   @Nullable
   @Override
   protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
      return new MazeRuinsComponent(0, x + 5, y, z + 5);
   }

   @Override
   public ControlledSpawns.ControlledSpawningConfig getConfig() {
      return this.controlledSpawningConfig;
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.LABYRINTH.get();
   }

   public static LabyrinthStructure buildLabyrinthConfig(BootstrapContext<Structure> context) {
      return new LabyrinthStructure(
         ControlledSpawns.ControlledSpawningConfig.firstIndexMonsters(
            new SpawnerData((EntityType)TFEntities.MINOTAUR.get(), 20, 2, 3),
            new SpawnerData(EntityType.CAVE_SPIDER, 10, 1, 2),
            new SpawnerData(EntityType.CREEPER, 10, 1, 2),
            new SpawnerData((EntityType)TFEntities.MAZE_SLIME.get(), 10, 2, 4),
            new SpawnerData(EntityType.ENDERMAN, 1, 1, 2),
            new SpawnerData((EntityType)TFEntities.FIRE_BEETLE.get(), 10, 1, 2),
            new SpawnerData((EntityType)TFEntities.SLIME_BEETLE.get(), 10, 1, 2),
            new SpawnerData((EntityType)TFEntities.PINCH_BEETLE.get(), 10, 1, 1)
         ),
         new AdvancementLockedStructure.AdvancementLockConfig(List.of(TwilightForestMod.prefix("progress_lich"))),
         Optional.of(new StructureHints.HintConfig(StructureHints.HintConfig.book("labyrinth", 5), (EntityType<? extends Mob>)TFEntities.KOBOLD.get())),
         Optional.of(new DecorationClearance.DecorationConfig(3.0F, true, false, false)),
         true,
         Optional.of(TFMapDecorations.LABYRINTH),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_LABYRINTH_BIOMES),
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.UNDERGROUND_STRUCTURES,
            TerrainAdjustment.BURY
         )
      );
   }

   @Override
   public DensityFunction getStructureTerraformer(ChunkPos chunkSliceAt, StructureStart structurePieceSource) {
      float radius = 35.0F;
      BoundingBox structureBox = structurePieceSource.getBoundingBox();
      BlockPos hillCenter = structureBox.getCenter();
      int yCeilingFocus = hillCenter.getY();
      DensityFunction hillMound = new HollowHillFunction(hillCenter.getX() + 1, hillCenter.getY() + 7, hillCenter.getZ() + 2.0F, 35.0F, 0.8F).clamp(0.0, 2.0);
      DensityFunction ceilingCapped = DensityFunctions.yClampedGradient(5, 6, -1.0, 1.0);
      BlockPos pos = hillCenter.offset(1, 0, 1);
      DensityFunction innerFloor = DensityFunctions.add(
         DensityFunctions.yClampedGradient(-4, 3, 26.0, -1.0),
         DensityFunctions.mul(DensityFunctions.constant(-1.0), new AbsoluteDifferenceFunction.Max(32.0, pos.getX() + 0.5F, pos.getZ() + 0.5F))
      );
      DensityFunction entrances = DensityFunctions.max(
         ceilingCapped, DensityFunctions.add(DensityFunctions.constant(-2.0), new AbsoluteDifferenceFunction.Min(32.0, pos.getX() + 0.5F, pos.getZ() + 0.5F))
      );
      DensityFunction interior = DensityFunctions.max(entrances, innerFloor).clamp(0.0, 1.0);
      DensityFunction interiorMask = FocusedDensityFunction.fromPos(hillCenter.atY(yCeilingFocus), 24.5F, 35.0F, 0.0F);
      DensityFunction interiorMasked = DensityFunctions.lerp(interiorMask.clamp(0.0, 1.0), DensityFunctions.zero(), interior);
      return DensityFunctions.min(hillMound, interiorMasked);
   }
}
