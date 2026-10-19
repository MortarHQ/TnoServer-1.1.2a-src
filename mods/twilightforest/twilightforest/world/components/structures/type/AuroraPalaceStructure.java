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
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFMapDecorations;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.structures.icetower.IceTowerMainComponent;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.ControlledSpawningStructure;
import twilightforest.world.components.structures.util.ControlledSpawns;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.StructureHints;

public class AuroraPalaceStructure extends ControlledSpawningStructure {
   public static final MapCodec<AuroraPalaceStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> controlledSpawningCodec(instance).apply(instance, AuroraPalaceStructure::new)
   );

   public AuroraPalaceStructure(
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
      return new IceTowerMainComponent(random, 0, x, y, z);
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.AURORA_PALACE.get();
   }

   public static AuroraPalaceStructure buildAuroraPalaceConfig(BootstrapContext<Structure> context) {
      return new AuroraPalaceStructure(
         ControlledSpawns.ControlledSpawningConfig.firstIndexMonsters(
            new SpawnerData((EntityType)TFEntities.SNOW_GUARDIAN.get(), 10, 1, 2),
            new SpawnerData((EntityType)TFEntities.STABLE_ICE_CORE.get(), 10, 1, 2),
            new SpawnerData((EntityType)TFEntities.UNSTABLE_ICE_CORE.get(), 5, 1, 2)
         ),
         new AdvancementLockedStructure.AdvancementLockConfig(List.of(TwilightForestMod.prefix("progress_yeti"))),
         Optional.of(new StructureHints.HintConfig(StructureHints.HintConfig.book("icetower", 3), (EntityType<? extends Mob>)TFEntities.KOBOLD.get())),
         Optional.of(new DecorationClearance.DecorationConfig(2.0F, false, true, false)),
         true,
         Optional.of(TFMapDecorations.AURORA_PALACE),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_AURORA_PALACE_BIOMES),
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
}
