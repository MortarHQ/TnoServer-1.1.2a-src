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
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.structures.trollcave.CloudCastleComponent;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.ConfigurableSpawns;
import twilightforest.world.components.structures.util.ControlledSpawns;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.ProgressionStructure;
import twilightforest.world.components.structures.util.StructureHints;

public class GiantHouseStructure extends ProgressionStructure implements ConfigurableSpawns {
   public static final MapCodec<GiantHouseStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> progressionCodec(instance)
         .and(ControlledSpawns.ControlledSpawningConfig.CODEC.fieldOf("controlled_spawns").forGetter(ConfigurableSpawns::getConfig))
         .apply(instance, GiantHouseStructure::new)
   );
   private final ControlledSpawns.ControlledSpawningConfig controlledSpawningConfig;

   public GiantHouseStructure(
      AdvancementLockedStructure.AdvancementLockConfig advancementLockConfig,
      Optional<StructureHints.HintConfig> hintConfig,
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings,
      ControlledSpawns.ControlledSpawningConfig controlledSpawningConfig
   ) {
      super(advancementLockConfig, hintConfig, decorationConfig, centerInChunk, structureIcon, structureSettings);
      this.controlledSpawningConfig = controlledSpawningConfig;
   }

   @Override
   protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
      return new CloudCastleComponent(1, x, y + 168, z);
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.GIANT_HOUSE.get();
   }

   @Override
   public ControlledSpawns.ControlledSpawningConfig getConfig() {
      return this.controlledSpawningConfig;
   }

   public static GiantHouseStructure buildGiantHouseConfig(BootstrapContext<Structure> context) {
      return new GiantHouseStructure(
         new AdvancementLockedStructure.AdvancementLockConfig(List.of(TwilightForestMod.prefix("progress_merge"))),
         Optional.of(new StructureHints.HintConfig(StructureHints.HintConfig.book("trollcave", 3), (EntityType<? extends Mob>)TFEntities.KOBOLD.get())),
         Optional.of(new DecorationClearance.DecorationConfig(1.0F, true, true, false)),
         false,
         Optional.empty(),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_GIANT_HOUSE_BIOMES),
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.TOP_LAYER_MODIFICATION,
            TerrainAdjustment.NONE
         ),
         ControlledSpawns.ControlledSpawningConfig.create(
            List.of(
               List.of(
                  new SpawnerData((EntityType)TFEntities.GIANT_MINER.get(), 10, 1, 1), new SpawnerData((EntityType)TFEntities.ARMORED_GIANT.get(), 10, 1, 1)
               )
            ),
            List.of(),
            List.of()
         )
      );
   }
}
