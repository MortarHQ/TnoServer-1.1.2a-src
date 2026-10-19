package twilightforest.world.components.structures.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride.BoundingBoxType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import org.jetbrains.annotations.Nullable;
import twilightforest.TFRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFMapDecorations;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.init.TFStructureTypes;
import twilightforest.init.custom.StructureSpeleothemConfigs;
import twilightforest.world.components.structures.StructureSpeleothemConfig;
import twilightforest.world.components.structures.trollcave.TrollCaveMainComponent;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.ConfigurableSpawns;
import twilightforest.world.components.structures.util.ControlledSpawns;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.ProgressionStructure;
import twilightforest.world.components.structures.util.StructureHints;

public class TrollCaveStructure extends ProgressionStructure implements ConfigurableSpawns {
   public static final MapCodec<TrollCaveStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            ControlledSpawns.ControlledSpawningConfig.CODEC.fieldOf("controlled_spawns").forGetter(ConfigurableSpawns::getConfig),
            StructureSpeleothemConfigs.CODEC.fieldOf("speleothem_config").forGetter(s -> s.speleothemConfig)
         )
         .and(progressionCodec(instance))
         .apply(instance, TrollCaveStructure::new)
   );
   private final ControlledSpawns.ControlledSpawningConfig controlledSpawningConfig;
   private final Reference<StructureSpeleothemConfig> speleothemConfig;

   public TrollCaveStructure(
      ControlledSpawns.ControlledSpawningConfig controlledSpawningConfig,
      Holder<StructureSpeleothemConfig> speleothemConfig,
      AdvancementLockedStructure.AdvancementLockConfig advancementLockConfig,
      Optional<StructureHints.HintConfig> hintConfig,
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings
   ) {
      super(advancementLockConfig, hintConfig, decorationConfig, centerInChunk, structureIcon, structureSettings);
      this.controlledSpawningConfig = controlledSpawningConfig;
      this.speleothemConfig = (Reference<StructureSpeleothemConfig>)speleothemConfig;
   }

   @Nullable
   @Override
   protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
      return new TrollCaveMainComponent((StructurePieceType)TFStructurePieceTypes.TFTCMai.get(), 0, x, y + 11, z, this.speleothemConfig);
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.TROLL_CAVE.get();
   }

   @Override
   public ControlledSpawns.ControlledSpawningConfig getConfig() {
      return this.controlledSpawningConfig;
   }

   public static TrollCaveStructure buildTrollCaveConfig(BootstrapContext<Structure> context) {
      return new TrollCaveStructure(
         ControlledSpawns.ControlledSpawningConfig.create(
            List.of(
               List.of(
                  new SpawnerData(EntityType.CREEPER, 5, 1, 2),
                  new SpawnerData(EntityType.SKELETON, 10, 1, 2),
                  new SpawnerData((EntityType)TFEntities.TROLL.get(), 20, 1, 2),
                  new SpawnerData(EntityType.WITCH, 5, 1, 1)
               ),
               List.of(
                  new SpawnerData((EntityType)TFEntities.GIANT_MINER.get(), 10, 1, 1), new SpawnerData((EntityType)TFEntities.ARMORED_GIANT.get(), 10, 1, 1)
               )
            ),
            List.of(),
            List.of()
         ),
         context.lookup(TFRegistries.Keys.STRUCTURE_SPELEOTHEM_SETTINGS).getOrThrow(StructureSpeleothemConfigs.TROLL_CAVE),
         new AdvancementLockedStructure.AdvancementLockConfig(List.of(TwilightForestMod.prefix("progress_merge"))),
         Optional.of(new StructureHints.HintConfig(StructureHints.HintConfig.book("trollcave", 3), (EntityType<? extends Mob>)TFEntities.KOBOLD.get())),
         Optional.of(new DecorationClearance.DecorationConfig(4.0F, true, true, false)),
         false,
         Optional.of(TFMapDecorations.TROLL_CAVES),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_TROLL_CAVE_BIOMES),
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
}
