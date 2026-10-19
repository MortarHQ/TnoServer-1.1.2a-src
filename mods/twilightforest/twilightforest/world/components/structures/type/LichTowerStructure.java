package twilightforest.world.components.structures.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
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
import net.minecraft.world.level.block.Rotation;
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
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFMapDecorations;
import twilightforest.init.TFStructureTypes;
import twilightforest.util.WorldUtil;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.world.components.chunkgenerators.BoxDensityFunction;
import twilightforest.world.components.structures.CustomDensitySource;
import twilightforest.world.components.structures.TwilightJigsawPiece;
import twilightforest.world.components.structures.lichtowerrevamp.LichTowerBaseTrim;
import twilightforest.world.components.structures.lichtowerrevamp.LichTowerFoyer;
import twilightforest.world.components.structures.lichtowerrevamp.LichTowerWingBeard;
import twilightforest.world.components.structures.lichtowerrevamp.LichYardBox;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.ControlledSpawningStructure;
import twilightforest.world.components.structures.util.ControlledSpawns;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.StructureHints;

public class LichTowerStructure extends ControlledSpawningStructure implements CustomDensitySource {
   public static final MapCodec<LichTowerStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> controlledSpawningCodec(instance).apply(instance, LichTowerStructure::new)
   );

   public LichTowerStructure(
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

   @Override
   protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
      Direction direction = Rotation.getRandom(random).rotate(Direction.SOUTH);
      BlockPos placePos = new BlockPos(x, y, z).relative(direction, 24);
      FrontAndTop oriented = FrontAndTop.fromFrontAndTop(Direction.UP, direction);
      JigsawPlaceContext placeContext = JigsawPlaceContext.pickPlaceableJunction(
         placePos,
         BlockPos.ZERO,
         oriented,
         context.structureTemplateManager(),
         TwilightForestMod.prefix("lich_tower/tower_foyer"),
         "twilightforest:lich_tower/vestibule",
         random
      );
      return placeContext == null ? null : new LichTowerFoyer(context.structureTemplateManager(), placeContext, true, random.nextBoolean());
   }

   @Override
   protected void generateFromStartingPiece(StructurePiece startingPiece, GenerationContext context, StructurePiecesBuilder structurePiecesBuilder) {
      structurePiecesBuilder.addPiece(startingPiece);
      if (startingPiece instanceof TwilightJigsawPiece jigsaw) {
         jigsaw.addJigsaws(jigsaw, structurePiecesBuilder, context);
      } else {
         startingPiece.addChildren(startingPiece, structurePiecesBuilder, context.random());
      }

      if (startingPiece instanceof LichTowerFoyer foyerPiece) {
         LichYardBox.beginYard(foyerPiece, context, structurePiecesBuilder);
      }
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.LICH_TOWER.get();
   }

   public static LichTowerStructure buildLichTowerConfig(BootstrapContext<Structure> context) {
      List<SpawnerData> yardSpawns = List.of(new SpawnerData((EntityType)TFEntities.RISING_ZOMBIE.value(), 2, 1, 2));
      List<SpawnerData> interiorSpawns = List.of(
         new SpawnerData(EntityType.ZOMBIE, 10, 1, 2),
         new SpawnerData(EntityType.SKELETON, 10, 1, 2),
         new SpawnerData(EntityType.CREEPER, 1, 1, 1),
         new SpawnerData(EntityType.ENDERMAN, 1, 1, 2),
         new SpawnerData((EntityType)TFEntities.DEATH_TOME.value(), 10, 2, 3),
         new SpawnerData(EntityType.WITCH, 1, 1, 1)
      );
      ControlledSpawns.ControlledSpawningConfig monsters = ControlledSpawns.ControlledSpawningConfig.justMonsters(yardSpawns, interiorSpawns);
      return new LichTowerStructure(
         monsters,
         new AdvancementLockedStructure.AdvancementLockConfig(List.of(TwilightForestMod.prefix("progress_naga"))),
         Optional.of(new StructureHints.HintConfig(StructureHints.HintConfig.book("lichtower", 4), (EntityType<? extends Mob>)TFEntities.KOBOLD.get())),
         Optional.of(new DecorationClearance.DecorationConfig(0.0F, false, true, false, true)),
         true,
         Optional.of(TFMapDecorations.LICH_TOWER),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_LICH_TOWER_BIOMES),
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.BEARD_THIN
         )
      );
   }

   @Override
   public DensityFunction getStructureTerraformer(ChunkPos chunkPosAt, StructureStart structurePieceSource) {
      List<BoundingBox> trimBoxes = new ArrayList<>();
      int yBase = ((StructurePiece)structurePieceSource.getPieces().getFirst()).getBoundingBox().minY();
      DensityFunction activator = DensityFunctions.yClampedGradient(yBase - 2, yBase - 1, 1.0, 0.0);

      for (StructurePiece piece : structurePieceSource.getPieces()) {
         if (piece instanceof LichTowerFoyer || piece instanceof LichTowerBaseTrim || piece instanceof LichTowerWingBeard beard && beard.isTrim()) {
            trimBoxes.add(piece.getBoundingBox());
         } else if (piece instanceof LichYardBox yard && yard.getTerrainAdjustment() != TerrainAdjustment.NONE) {
            trimBoxes.add(piece.getBoundingBox().moved(0, -5, 0));
         }
      }

      return DensityFunctions.mul(activator, BoxDensityFunction.combine(trimBoxes, -5, -5, TerrainAdjustment.BURY));
   }

   @Override
   public int adjustForTerrain(GenerationContext context, int x, int z) {
      return WorldUtil.adjustForTerrain(context, x, z, 32, 4);
   }
}
