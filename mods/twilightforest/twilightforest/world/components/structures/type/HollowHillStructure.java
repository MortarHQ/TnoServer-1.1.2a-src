package twilightforest.world.components.structures.type;

import com.mojang.serialization.Codec;
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
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import twilightforest.TFRegistries;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFMapDecorations;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.init.TFStructureTypes;
import twilightforest.init.custom.StructureSpeleothemConfigs;
import twilightforest.world.components.chunkgenerators.FocusedDensityFunction;
import twilightforest.world.components.chunkgenerators.HollowHillFunction;
import twilightforest.world.components.structures.CustomDensitySource;
import twilightforest.world.components.structures.HollowHillComponent;
import twilightforest.world.components.structures.StructureSpeleothemConfig;
import twilightforest.world.components.structures.util.ConfigurableSpawns;
import twilightforest.world.components.structures.util.ControlledSpawns;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.LandmarkStructure;

public class HollowHillStructure extends LandmarkStructure implements ConfigurableSpawns, CustomDensitySource {
   public static final MapCodec<HollowHillStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Codec.intRange(1, 3).fieldOf("hill_size").forGetter(s -> s.size),
            ControlledSpawns.ControlledSpawningConfig.CODEC.fieldOf("controlled_spawns").forGetter(s -> s.controlledSpawningConfig),
            StructureSpeleothemConfigs.CODEC.fieldOf("speleothem_config").forGetter(s -> s.speleothemConfig)
         )
         .and(landmarkCodec(instance))
         .apply(instance, HollowHillStructure::new)
   );
   private final int size;
   private final ControlledSpawns.ControlledSpawningConfig controlledSpawningConfig;
   private final Reference<StructureSpeleothemConfig> speleothemConfig;

   public HollowHillStructure(
      int size,
      ControlledSpawns.ControlledSpawningConfig controlledSpawningConfig,
      Holder<StructureSpeleothemConfig> speleothemConfig,
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings
   ) {
      super(decorationConfig, centerInChunk, structureIcon, structureSettings);
      this.size = size;
      this.controlledSpawningConfig = controlledSpawningConfig;
      this.speleothemConfig = (Reference<StructureSpeleothemConfig>)speleothemConfig;
   }

   public boolean canSpawnMob(BlockPos spawnPos, BoundingBox structureStartBox) {
      float hX = Mth.inverseLerp(spawnPos.getX(), structureStartBox.minX(), structureStartBox.maxX()) * 2.0F - 1.0F;
      float hY = Mth.inverseLerp(spawnPos.getY(), structureStartBox.minY(), structureStartBox.maxY());
      float hZ = Mth.inverseLerp(spawnPos.getZ(), structureStartBox.minZ(), structureStartBox.maxZ()) * 2.0F - 1.0F;
      return Mth.length(hX, hY, hZ) < 0.975F;
   }

   @Override
   protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
      return switch (this.size) {
         case 1 -> new HollowHillComponent((StructurePieceType)TFStructurePieceTypes.TFHill.get(), 0, this.size, x - 3, y - 2, z - 3, this.speleothemConfig);
         case 2 -> new HollowHillComponent((StructurePieceType)TFStructurePieceTypes.TFHill.get(), 0, this.size, x - 7, y - 5, z - 7, this.speleothemConfig);
         default -> new HollowHillComponent((StructurePieceType)TFStructurePieceTypes.TFHill.get(), 0, this.size, x - 11, y - 5, z - 11, this.speleothemConfig);
      };
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.HOLLOW_HILL.get();
   }

   @Override
   public ControlledSpawns.ControlledSpawningConfig getConfig() {
      return this.controlledSpawningConfig;
   }

   public static HollowHillStructure buildSmallHillConfig(BootstrapContext<Structure> context) {
      return new HollowHillStructure(
         1,
         ControlledSpawns.ControlledSpawningConfig.create(
            List.of(
               List.of(
                  new SpawnerData(EntityType.SPIDER, 10, 4, 4),
                  new SpawnerData(EntityType.ZOMBIE, 10, 4, 4),
                  new SpawnerData((EntityType)TFEntities.REDCAP.get(), 10, 4, 4),
                  new SpawnerData((EntityType)TFEntities.SWARM_SPIDER.get(), 10, 4, 4),
                  new SpawnerData((EntityType)TFEntities.KOBOLD.get(), 10, 4, 8)
               )
            ),
            List.of(),
            List.of()
         ),
         context.lookup(TFRegistries.Keys.STRUCTURE_SPELEOTHEM_SETTINGS).getOrThrow(StructureSpeleothemConfigs.SMALL_HILL),
         Optional.of(new DecorationClearance.DecorationConfig(1.0F, true, false, false)),
         true,
         Optional.of(TFMapDecorations.SMALL_HOLLOW_HILL),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES),
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.NONE
         )
      );
   }

   public static HollowHillStructure buildMediumHillConfig(BootstrapContext<Structure> context) {
      return new HollowHillStructure(
         2,
         ControlledSpawns.ControlledSpawningConfig.create(
            List.of(
               List.of(
                  new SpawnerData((EntityType)TFEntities.REDCAP.get(), 10, 1, 2),
                  new SpawnerData((EntityType)TFEntities.REDCAP_SAPPER.get(), 2, 1, 2),
                  new SpawnerData((EntityType)TFEntities.KOBOLD.get(), 10, 2, 4),
                  new SpawnerData(EntityType.SKELETON, 10, 2, 3),
                  new SpawnerData((EntityType)TFEntities.SWARM_SPIDER.get(), 10, 2, 4),
                  new SpawnerData(EntityType.SPIDER, 10, 1, 3),
                  new SpawnerData(EntityType.CREEPER, 5, 1, 2),
                  new SpawnerData((EntityType)TFEntities.FIRE_BEETLE.get(), 5, 1, 1),
                  new SpawnerData((EntityType)TFEntities.SLIME_BEETLE.get(), 5, 1, 1),
                  new SpawnerData(EntityType.WITCH, 1, 1, 1)
               )
            ),
            List.of(),
            List.of()
         ),
         context.lookup(TFRegistries.Keys.STRUCTURE_SPELEOTHEM_SETTINGS).getOrThrow(StructureSpeleothemConfigs.MEDIUM_HILL),
         Optional.of(new DecorationClearance.DecorationConfig(2.0F, true, false, false)),
         true,
         Optional.of(TFMapDecorations.MEDIUM_HOLLOW_HILL),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES),
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.NONE
         )
      );
   }

   public static HollowHillStructure buildLargeHillConfig(BootstrapContext<Structure> context) {
      return new HollowHillStructure(
         3,
         ControlledSpawns.ControlledSpawningConfig.firstIndexMonsters(
            new SpawnerData((EntityType)TFEntities.REDCAP.get(), 10, 2, 4),
            new SpawnerData((EntityType)TFEntities.REDCAP_SAPPER.get(), 2, 1, 2),
            new SpawnerData(EntityType.SKELETON, 10, 2, 3),
            new SpawnerData(EntityType.CAVE_SPIDER, 10, 1, 2),
            new SpawnerData(EntityType.CREEPER, 10, 1, 1),
            new SpawnerData(EntityType.ENDERMAN, 1, 1, 1),
            new SpawnerData((EntityType)TFEntities.WRAITH.get(), 2, 1, 2),
            new SpawnerData((EntityType)TFEntities.FIRE_BEETLE.get(), 10, 1, 2),
            new SpawnerData((EntityType)TFEntities.SLIME_BEETLE.get(), 10, 1, 2),
            new SpawnerData((EntityType)TFEntities.PINCH_BEETLE.get(), 10, 1, 2),
            new SpawnerData(EntityType.WITCH, 1, 1, 1)
         ),
         context.lookup(TFRegistries.Keys.STRUCTURE_SPELEOTHEM_SETTINGS).getOrThrow(StructureSpeleothemConfigs.LARGE_HILL),
         Optional.of(new DecorationClearance.DecorationConfig(3.0F, true, false, false)),
         true,
         Optional.of(TFMapDecorations.LARGE_HOLLOW_HILL),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES),
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.NONE
         )
      );
   }

   @Override
   public DensityFunction getStructureTerraformer(ChunkPos chunkSliceAt, StructureStart structurePieceSource) {
      int hillSize = this.size;
      float radius = (hillSize * 4 + 0.8F) * 8.0F;
      float radiusInner = radius - 8.0F;
      BoundingBox structureBox = structurePieceSource.getBoundingBox();
      int width = Math.min(structureBox.getXSpan(), structureBox.getZSpan());
      int yCeilingFocus = structureBox.minY();
      BlockPos hillCenter = structureBox.getCenter();
      DensityFunction hillMound = HollowHillFunction.fromPos(hillCenter.atY(yCeilingFocus + 10), radius, 0.7F).clamp(0.0, 1.0);
      DensityFunction innerCeiling = DensityFunctions.mul(
         DensityFunctions.constant(-1.0), HollowHillFunction.fromPos(hillCenter.atY(yCeilingFocus + 6), radiusInner, 0.675F)
      );
      DensityFunction innerFloor = HollowHillFunction.fromPos(hillCenter.atY(yCeilingFocus + hillSize + hillSize / 2), 2.0F - radiusInner, 0.1F);
      DensityFunction interior = DensityFunctions.max(innerCeiling, innerFloor);
      DensityFunction interiorMask = FocusedDensityFunction.fromPos(hillCenter.atY(yCeilingFocus), radiusInner * 0.52F, -radiusInner, 1.0F);
      DensityFunction interiorMasked = DensityFunctions.max(interiorMask, interior);
      DensityFunction hollowHill = DensityFunctions.min(hillMound, interiorMasked);
      DensityFunction maskingSphere = FocusedDensityFunction.fromPos(hillCenter.below(Mth.ceil(radius * 0.1)), width * 0.5F + 5.0F, width * 0.25F, 0.0F)
         .clamp(0.0, 1.0);
      return DensityFunctions.mul(maskingSphere, DensityFunctions.mul(DensityFunctions.constant(8.0), hollowHill));
   }
}
