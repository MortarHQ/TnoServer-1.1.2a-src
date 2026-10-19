package twilightforest.world.components.structures.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride.BoundingBoxType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFMapDecorations;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.structures.start.TFStructureStart;
import twilightforest.world.components.structures.stronghold.StrongholdEntranceComponent;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.ControlledSpawningStructure;
import twilightforest.world.components.structures.util.ControlledSpawns;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.StructureHints;

public class KnightStrongholdStructure extends ControlledSpawningStructure {
   public static final MapCodec<KnightStrongholdStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> controlledSpawningCodec(instance).apply(instance, KnightStrongholdStructure::new)
   );

   public KnightStrongholdStructure(
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
      return new StrongholdEntranceComponent(0, x, y + random.nextInt(3) == 0 ? 5 : 1, z);
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.KNIGHT_STRONGHOLD.get();
   }

   public static KnightStrongholdStructure buildKnightStrongholdConfig(BootstrapContext<Structure> context) {
      return new KnightStrongholdStructure(
         ControlledSpawns.ControlledSpawningConfig.firstIndexMonsters(
            new SpawnerData((EntityType)TFEntities.BLOCKCHAIN_GOBLIN.get(), 10, 1, 2),
            new SpawnerData((EntityType)TFEntities.LOWER_GOBLIN_KNIGHT.get(), 5, 1, 2),
            new SpawnerData((EntityType)TFEntities.HELMET_CRAB.get(), 10, 2, 4),
            new SpawnerData((EntityType)TFEntities.SLIME_BEETLE.get(), 10, 2, 3),
            new SpawnerData((EntityType)TFEntities.REDCAP_SAPPER.get(), 2, 1, 2),
            new SpawnerData((EntityType)TFEntities.KOBOLD.get(), 10, 2, 4),
            new SpawnerData(EntityType.CREEPER, 5, 1, 2),
            new SpawnerData(EntityType.SLIME, 5, 4, 4)
         ),
         new AdvancementLockedStructure.AdvancementLockConfig(List.of(TwilightForestMod.prefix("progress_trophy_pedestal"))),
         Optional.of(new StructureHints.HintConfig(StructureHints.HintConfig.book("tfstronghold", 4), (EntityType<? extends Mob>)TFEntities.KOBOLD.get())),
         Optional.of(new DecorationClearance.DecorationConfig(3.0F, true, false, false)),
         true,
         Optional.of(TFMapDecorations.KNIGHT_STRONGHOLD),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_KNIGHT_STRONGHOLD_BIOMES),
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
   protected StructureStart createStart(ChunkPos chunkPos, int reference, GenerationStub generationStub) {
      KnightStrongholdStructure.KnightStructureStart start = new KnightStrongholdStructure.KnightStructureStart(
         this, chunkPos, reference, generationStub.getPiecesBuilder().build()
      );
      start.setStartY(generationStub.position().getY() - 1);
      return start;
   }

   public static class KnightStructureStart extends TFStructureStart {
      private int startY = 0;

      public KnightStructureStart(Structure structure, ChunkPos chunkPos, int references, PiecesContainer pieces) {
         super(structure, chunkPos, references, pieces);
      }

      public void setStartY(int startY) {
         this.startY = startY;
      }

      public BoundingBox getBoundingBox() {
         BoundingBox boundingbox = this.cachedBoundingBox;
         if (boundingbox == null) {
            BoundingBox bBox = super.getBoundingBox();
            boundingbox = new BoundingBox(bBox.minX(), bBox.minY(), bBox.minZ(), bBox.maxX(), Math.min(bBox.maxY(), this.startY), bBox.maxZ());
            this.cachedBoundingBox = boundingbox;
         }

         return boundingbox;
      }

      @Override
      public CompoundTag createTag(StructurePieceSerializationContext level, ChunkPos chunkPos) {
         CompoundTag tag = super.createTag(level, chunkPos);
         tag.putInt("knight_y", this.startY);
         return tag;
      }

      @Override
      public void loadFromTag(CompoundTag nbt) {
         super.loadFromTag(nbt);
         this.startY = nbt.getInt("knight_y");
      }
   }
}
