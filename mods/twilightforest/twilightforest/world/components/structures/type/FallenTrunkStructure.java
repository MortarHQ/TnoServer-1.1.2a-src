package twilightforest.world.components.structures.type;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Beardifier.Rigid;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
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
import net.minecraft.world.level.storage.loot.LootTable;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructureTypes;
import twilightforest.loot.TFLootTables;
import twilightforest.util.WorldUtil;
import twilightforest.world.components.structures.CustomDensitySource;
import twilightforest.world.components.structures.fallentrunk.FallenTrunkPiece;
import twilightforest.world.components.structures.fallentrunk.TrunkUnderDensityFunction;
import twilightforest.world.components.structures.util.DecorationClearance;

public class FallenTrunkStructure extends Structure implements CustomDensitySource, DecorationClearance {
   public static final MapCodec<FallenTrunkStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Structure.settingsCodec(instance),
            IntProvider.codec(16, 32).fieldOf("length").forGetter(s -> s.length),
            IntProvider.codec(20, 32).fieldOf("big_trunk_length").forGetter(s -> s.bigTrunkLength),
            BlockStateProvider.CODEC.fieldOf("log").forGetter(s -> s.log),
            ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("chest_loot_table").forGetter(s -> s.chestLootTable)
         )
         .apply(instance, FallenTrunkStructure::new)
   );
   public static final List<Integer> radiuses = List.of(1, 2, 4);
   private final IntProvider length;
   private final IntProvider bigTrunkLength;
   private final BlockStateProvider log;
   private final ResourceKey<LootTable> chestLootTable;

   protected FallenTrunkStructure(
      StructureSettings settings, IntProvider length, IntProvider bigTrunkLength, BlockStateProvider log, ResourceKey<LootTable> chestLootTable
   ) {
      super(settings);
      this.length = length;
      this.bigTrunkLength = bigTrunkLength;
      this.log = log;
      this.chestLootTable = chestLootTable;
   }

   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      ChunkPos chunkPos = context.chunkPos();
      RandomSource random = RandomSource.create(context.seed() + chunkPos.x * 14413411L + chunkPos.z * 43387781L);
      int x = SectionPos.sectionToBlockCoord(chunkPos.x, random.nextInt(16));
      int z = SectionPos.sectionToBlockCoord(chunkPos.z, random.nextInt(16));
      int worldY = context.chunkGenerator().getFirstOccupiedHeight(x, z, Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
      int radius = (Integer)Util.getRandom(radiuses, random);
      int length = radius == radiuses.getLast() ? this.bigTrunkLength.sample(random) : this.length.sample(random);
      if (!this.isValidNoiseBiome(context, x, worldY, z)) {
         return Optional.empty();
      }

      if (this.hasInvalidNearbyBiome(context, x, worldY, z, random)) {
         return Optional.empty();
      }

      Direction orientation = Plane.HORIZONTAL.getRandomDirection(random);
      int xySize = radius > 1 ? radius * 2 + 1 : 4;
      int zSize = length - 1;
      BoundingBox baseBox = BoundingBox.orientBox(x, worldY, z, 0, 0, 0, xySize, xySize, zSize, orientation);
      int targetY = this.computeTargetY(context, baseBox, worldY, radius);
      BoundingBox adjustedBox = BoundingBox.orientBox(x, targetY, z, 0, 0, 0, xySize, xySize, zSize, orientation);
      long holeSeed = random.nextLong();
      return Optional.of(new GenerationStub(new BlockPos(x, adjustedBox.minY(), z), structurePiecesBuilder -> {
         StructurePiece piece = new FallenTrunkPiece(length, radius, this.log, this.chestLootTable, orientation, adjustedBox, holeSeed);
         structurePiecesBuilder.addPiece(piece);
         piece.addChildren(piece, structurePiecesBuilder, random);
      }));
   }

   private boolean isValidNoiseBiome(GenerationContext context, int x, int worldY, int z) {
      Holder<Biome> noiseBiome = context.chunkGenerator().getBiomeSource().getNoiseBiome(x >> 2, worldY >> 2, z >> 2, context.randomState().sampler());
      return this.getModifiedStructureSettings().biomes().contains(noiseBiome);
   }

   private boolean hasInvalidNearbyBiome(GenerationContext context, int x, int worldY, int z, RandomSource random) {
      Pair<BlockPos, Holder<Biome>> invalidBiome = context.biomeSource()
         .findBiomeHorizontal(
            x, worldY, z, this.length.getMaxValue(), 1, biomeHolder -> !context.validBiome().test(biomeHolder), random, false, context.randomState().sampler()
         );
      return invalidBiome != null;
   }

   private int computeTargetY(GenerationContext context, BoundingBox box, int defaultY, int radius) {
      return radius == radiuses.getFirst()
         ? defaultY
         : WorldUtil.adjustForTerrain(context, box.minX(), box.minZ(), box.maxX(), box.maxZ(), 4, Types.WORLD_SURFACE_WG);
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.FALLEN_TRUNK.get();
   }

   public static FallenTrunkStructure buildStructureConfig(HolderSet<Biome> biomes) {
      return new FallenTrunkStructure(
         new StructureSettings(
            biomes,
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.NONE
         ),
         UniformInt.of(17, 24),
         UniformInt.of(22, 28),
         BlockStateProvider.simple((Block)TFBlocks.TWILIGHT_OAK_LOG.get()),
         TFLootTables.FALLEN_TRUNK_LOOT
      );
   }

   @Override
   public DensityFunction getStructureTerraformer(ChunkPos chunkPosAt, StructureStart structurePieceSource) {
      FallenTrunkPiece piece = (FallenTrunkPiece)structurePieceSource.getPieces().getFirst();
      ObjectList<Rigid> objectlist = ObjectArrayList.of(new Rigid[]{new Rigid(piece.getBoundingBox(), TerrainAdjustment.BEARD_THIN, 0)});
      boolean isBigTree = piece.radius == radiuses.get(2);
      int minMounds = 1;
      int maxMounds = 2;
      return new TrunkUnderDensityFunction(objectlist.iterator(), piece, isBigTree, minMounds, maxMounds);
   }

   @Override
   public float chunkClearanceRadius() {
      return 0.0F;
   }

   @Override
   public boolean isSurfaceDecorationsAllowed() {
      return false;
   }

   @Override
   public boolean isUndergroundDecoAllowed() {
      return true;
   }

   @Override
   public boolean isGrassDecoAllowed() {
      return true;
   }

   @Override
   public boolean shouldAdjustToTerrain() {
      return false;
   }
}
