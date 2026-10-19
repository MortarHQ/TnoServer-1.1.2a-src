package net.unusual.block_factorys_bosses.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerData;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector.EntitySelector;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultConfig;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseLootTables;

public class DragonTowerStructure extends Structure {
   public static final MapCodec<DragonTowerStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            settingsCodec(instance),
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
            ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
            Codec.intRange(0, 20).fieldOf("size").forGetter(structure -> structure.maxDepth),
            HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
            Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
            Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
            Codec.intRange(1, 160).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
            DimensionPadding.CODEC.optionalFieldOf("dimension_padding", DimensionPadding.ZERO).forGetter(structure -> structure.dimensionPadding),
            LiquidSettings.CODEC.optionalFieldOf("liquid_settings", LiquidSettings.APPLY_WATERLOGGING).forGetter(structure -> structure.liquidSettings)
         )
         .apply(instance, DragonTowerStructure::new)
   );
   private final Holder<StructureTemplatePool> startPool;
   private final Optional<ResourceLocation> startJigsawName;
   private final int maxDepth;
   private final HeightProvider startHeight;
   private final boolean useExpansionHack;
   private final Optional<Types> projectStartToHeightmap;
   private final int maxDistanceFromCenter;
   private final DimensionPadding dimensionPadding;
   private final LiquidSettings liquidSettings;
   public static int FORWARD_MARKER_DISTANCE_FROM_SPAWNER = 6;
   public static int BACKWARD_MARKER_DISTANCE_FROM_SPAWNER = 11;
   private static final VaultConfig VAULT_CONFIG = new VaultConfig(
      BuiltInLootTables.TRIAL_CHAMBERS_REWARD,
      4.0,
      4.5,
      BossesRiseItems.ANCIENT_TRIAL_KEY.toStack(),
      Optional.empty(),
      PlayerDetector.INCLUDING_CREATIVE_PLAYERS,
      EntitySelector.SELECT_FROM_LEVEL
   );
   private static final TrialSpawnerConfig TRIAL_SPAWNER_CONFIG = new TrialSpawnerConfig(
      TrialSpawnerConfig.DEFAULT.spawnRange(),
      TrialSpawnerConfig.DEFAULT.totalMobs(),
      TrialSpawnerConfig.DEFAULT.simultaneousMobs(),
      TrialSpawnerConfig.DEFAULT.totalMobsAddedPerPlayer(),
      TrialSpawnerConfig.DEFAULT.simultaneousMobsAddedPerPlayer(),
      TrialSpawnerConfig.DEFAULT.ticksBetweenSpawn(),
      SimpleWeightedRandomList.empty(),
      SimpleWeightedRandomList.builder().add(BuiltInLootTables.SPAWNER_TRIAL_CHAMBER_CONSUMABLES).add(BossesRiseLootTables.ANCIENT_TRIAL_KEY).build(),
      TrialSpawnerConfig.DEFAULT.itemsToDropWhenOminous()
   );

   public DragonTowerStructure(
      StructureSettings settings,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawName,
      int maxDepth,
      HeightProvider startHeight,
      boolean useExpansionHack,
      Optional<Types> projectStartToHeightmap,
      int maxDistanceFromCenter,
      DimensionPadding dimensionPadding,
      LiquidSettings liquidSettings
   ) {
      super(settings);
      this.startPool = startPool;
      this.startJigsawName = startJigsawName;
      this.maxDepth = maxDepth;
      this.startHeight = startHeight;
      this.useExpansionHack = useExpansionHack;
      this.projectStartToHeightmap = projectStartToHeightmap;
      this.maxDistanceFromCenter = maxDistanceFromCenter;
      this.dimensionPadding = dimensionPadding;
      this.liquidSettings = liquidSettings;
   }

   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      ChunkPos chunkPos = context.chunkPos();
      int startY = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
      BlockPos startPos = new BlockPos(chunkPos.getMinBlockX(), startY, chunkPos.getMinBlockZ());
      return JigsawPlacement.addPieces(
         context,
         this.startPool,
         this.startJigsawName,
         this.maxDepth,
         startPos,
         this.useExpansionHack,
         this.projectStartToHeightmap,
         this.maxDistanceFromCenter,
         PoolAliasLookup.create(List.of(), startPos, context.seed()),
         this.dimensionPadding,
         this.liquidSettings
      );
   }

   public void afterPlace(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator chunkGenerator,
      RandomSource random,
      BoundingBox boundingBox,
      ChunkPos chunkPos,
      PiecesContainer piecesContainer
   ) {
      MutableBlockPos mutablePos = new MutableBlockPos();
      int minBuildHeight = level.getMinBuildHeight();
      BoundingBox structureBoundingBox = piecesContainer.calculateBoundingBox();
      int structureBaseY = structureBoundingBox.minY();

      for (int x = boundingBox.minX(); x <= boundingBox.maxX(); x++) {
         for (int z = boundingBox.minZ(); z <= boundingBox.maxZ(); z++) {
            mutablePos.set(x, structureBaseY, z);
            if (!level.isEmptyBlock(mutablePos) && structureBoundingBox.isInside(mutablePos) && piecesContainer.isInsidePiece(mutablePos)) {
               for (int y = structureBaseY - 1; y > minBuildHeight; y--) {
                  mutablePos.setY(y);
                  if (level.getBlockState(mutablePos).canOcclude()) {
                     break;
                  }

                  level.setBlock(mutablePos, Blocks.COBBLED_DEEPSLATE.defaultBlockState(), 2);
               }
            }

            for (int y = boundingBox.minY(); y <= boundingBox.maxY(); y++) {
               mutablePos.setY(y);
               BlockState state = level.getBlockState(mutablePos);
               if (state.is(Blocks.BARREL)) {
                  this.assignBarrelLootTable(level, mutablePos);
               } else if (state.is(Blocks.YELLOW_TERRACOTTA)) {
                  this.computeMarker(level, mutablePos, state);
               }
            }
         }
      }
   }

   private void computeMarker(WorldGenLevel level, MutableBlockPos pos, BlockState state) {
      level.setBlock(pos, this.findMarkerReplacement(level, pos), 0);
      pos.setY(pos.getY() + 1);
      if (level.getBlockState(pos).is(Blocks.PURPLE_STAINED_GLASS)) {
         BlockState replacedState = Blocks.SPAWNER.defaultBlockState();
         level.setBlock(pos, replacedState, 0);
         if (level.getBlockEntity(pos) instanceof SpawnerBlockEntity spawner) {
            EntityType var13 = (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_FIREBALL.get();
            double random = Math.random();
            if (random >= 0.33) {
               var13 = (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD.get();
            }

            if (random >= 0.66) {
               var13 = (EntityType)BossesRiseEntities.DRAGON_GUARD_SWORD.get();
            }

            spawner.setEntityId(var13, level.getRandom());
            spawner.setChanged();
         }
      } else if (level.getBlockState(pos).is(Blocks.MAGENTA_STAINED_GLASS)) {
         BlockState replacedState = Blocks.TRIAL_SPAWNER.defaultBlockState();
         level.setBlock(pos, replacedState, 0);
         if (level.getBlockEntity(pos) instanceof TrialSpawnerBlockEntity spawner) {
            spawner.trialSpawner = createTrialSpawner(spawner);
            spawner.markUpdated();
            EntityType var11 = (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_FIREBALL.get();
            double random = Math.random();
            if (random >= 0.33) {
               var11 = (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD.get();
            }

            if (random >= 0.66) {
               var11 = (EntityType)BossesRiseEntities.DRAGON_GUARD_SWORD.get();
            }

            spawner.setEntityId(var11, level.getRandom());
            spawner.setChanged();
         }
      } else if (level.getBlockState(pos).is(Blocks.BLUE_STAINED_GLASS)) {
         if (Math.random() < 0.15F) {
            this.replaceMarkerWithChest(level, pos, state);
         } else {
            level.setBlock(
               pos,
               Math.random() >= 0.5 ? ((Block)BossesRiseBlocks.VASE.get()).defaultBlockState() : ((Block)BossesRiseBlocks.TALL_VASE.get()).defaultBlockState(),
               0
            );
         }
      } else if (level.getBlockState(pos).is(Blocks.LIGHT_BLUE_STAINED_GLASS)) {
         this.replaceMarkerWithVault(level, pos, state);
      }
   }

   private void assignBarrelLootTable(WorldGenLevel level, BlockPos pos) {
      if (level.getBlockEntity(pos) instanceof BarrelBlockEntity barrel) {
         barrel.setLootTable(ResourceKey.create(Registries.LOOT_TABLE, BossesRise.prefix("chests/dragon_tower_common")));
         barrel.setChanged();
      }
   }

   private void replaceMarkerWithChest(WorldGenLevel level, MutableBlockPos pos, BlockState state) {
      BlockState chestState = (BlockState)Blocks.CHEST
         .defaultBlockState()
         .setValue(BlockStateProperties.HORIZONTAL_FACING, Plane.HORIZONTAL.getRandomDirection(level.getRandom()));
      level.setBlock(pos, chestState, 0);
      if (level.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
         chest.setLootTable(ResourceKey.create(Registries.LOOT_TABLE, BossesRise.prefix("chests/dragon_tower")));
         chest.setChanged();
      }
   }

   private void replaceMarkerWithVault(WorldGenLevel level, MutableBlockPos pos, BlockState state) {
      BlockState vaultState = (BlockState)Blocks.VAULT
         .defaultBlockState()
         .setValue(BlockStateProperties.HORIZONTAL_FACING, Plane.HORIZONTAL.getRandomDirection(level.getRandom()));
      level.setBlock(pos, vaultState, 0);
      if (level.getBlockEntity(pos) instanceof VaultBlockEntity vault) {
         vault.setConfig(VAULT_CONFIG);
         vault.setChanged();
      }
   }

   private BlockState findMarkerReplacement(WorldGenLevel level, BlockPos pos) {
      BlockState state = Blocks.DIRT.defaultBlockState();
      BlockState xMinus1 = level.getBlockState(pos.west());
      if (xMinus1.canOcclude()) {
         return xMinus1;
      }

      BlockState xPlus1 = level.getBlockState(pos.east());
      if (xPlus1.canOcclude()) {
         return xPlus1;
      }

      BlockState zMinus1 = level.getBlockState(pos.north());
      if (zMinus1.canOcclude()) {
         return zMinus1;
      }

      BlockState zPlus1 = level.getBlockState(pos.south());
      return zPlus1.canOcclude() ? zPlus1 : state;
   }

   public StructureType<?> type() {
      return (StructureType<?>)BossesRise.DRAGON_TOWER.get();
   }

   private static TrialSpawner createTrialSpawner(TrialSpawnerBlockEntity trialSpawnerBlockEntity) {
      return new TrialSpawner(
         TRIAL_SPAWNER_CONFIG,
         TRIAL_SPAWNER_CONFIG,
         new TrialSpawnerData(),
         36000,
         14,
         trialSpawnerBlockEntity,
         PlayerDetector.NO_CREATIVE_PLAYERS,
         EntitySelector.SELECT_FROM_LEVEL
      );
   }
}
