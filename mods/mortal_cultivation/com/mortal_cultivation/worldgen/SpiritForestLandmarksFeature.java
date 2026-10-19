package com.mortal_cultivation.worldgen;

import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

public final class SpiritForestLandmarksFeature extends Feature<NoneFeatureConfiguration> {
   private static final Direction[] DIRECTIONS = new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH};
   private static final ResourceLocation HIPOKUTE = ResourceLocation.fromNamespaceAndPath("tensura", "hipokute_grass");

   public SpiritForestLandmarksFeature() {
      super(NoneFeatureConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      ChunkPos chunk = new ChunkPos(context.origin());
      int cellX = Math.floorDiv(chunk.getMinBlockX(), 64);
      int cellZ = Math.floorDiv(chunk.getMinBlockZ(), 64);
      SpiritForestLandscapeLayout.Site site = SpiritForestLandscapeLayout.site(level.getSeed(), cellX, cellZ);
      if (Math.floorDiv(site.x(), 16) == chunk.x && Math.floorDiv(site.z(), 16) == chunk.z) {
         BlockPos origin = new BlockPos(site.x(), level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, site.x(), site.z()), site.z());
         if (level.getBiome(origin).is(WorldgenRegistry.SPIRIT_FORESTS)
            && Feature.isDirt(level.getBlockState(origin.below()))
            && level.isEmptyBlock(origin)
            && !AncientCherryStructure.inClearing(level, origin)) {
            int radius = site.kind() == SpiritForestLandscapeLayout.Kind.RUIN ? 6 : 11;
            if (!suitableGround(level, origin, radius, site.kind() == SpiritForestLandscapeLayout.Kind.CHERRY_GLADE ? 3 : 5)) {
               return false;
            }

            return switch (site.kind()) {
               case CHERRY_GLADE -> petalGlade(level, origin, site.seed());
               case SPRING -> spring(level, origin, site);
               case RUIN -> ruin(level, origin, site);
            };
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean petalGlade(WorldGenLevel level, BlockPos origin, long seed) {
      RandomSource random = RandomSource.create(seed);
      boolean placed = false;

      for (int i = 0; i < 75; i++) {
         BlockPos pos = surface(level, origin.offset(random.nextInt(21) - 10, 0, random.nextInt(21) - 10));
         BlockState petals = Blocks.PINK_PETALS.defaultBlockState();
         if (Math.abs(pos.getY() - origin.getY()) <= 3 && level.isEmptyBlock(pos) && petals.canSurvive(level, pos)) {
            placed |= level.setBlock(pos, petals, 2);
         }
      }

      return placed;
   }

   private static boolean suitableGround(WorldGenLevel level, BlockPos origin, int radius, int tolerance) {
      for (int x = -radius; x <= radius; x += radius) {
         for (int z = -radius; z <= radius; z += radius) {
            BlockPos surface = surface(level, origin.offset(x, 0, z));
            if (Math.abs(surface.getY() - origin.getY()) > tolerance
               || !natural(level.getBlockState(surface.below()))
               || !level.getFluidState(surface.below()).isEmpty()
               || !level.getBiome(surface).is(WorldgenRegistry.SPIRIT_FORESTS)) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean spring(WorldGenLevel level, BlockPos origin, SpiritForestLandscapeLayout.Site site) {
      Map<SpiritSpringLayout.Cell, Integer> water = SpiritSpringLayout.water();
      Map<SpiritSpringLayout.Cell, Integer> banks = SpiritSpringLayout.banks(water);

      for (Entry<SpiritSpringLayout.Cell, Integer> entry : water.entrySet()) {
         BlockPos pos = at(origin, site, entry.getKey().along(), entry.getValue(), entry.getKey().across());
         int top = surface(level, pos).getY();
         if (Math.abs(top - origin.getY()) > 5) {
            return false;
         }

         for (int y = pos.getY() - 1; y <= Math.max(top, pos.getY() + 1); y++) {
            if (!natural(level.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())))) {
               return false;
            }
         }
      }

      for (Entry<SpiritSpringLayout.Cell, Integer> entry : banks.entrySet()) {
         BlockPos pos = at(origin, site, entry.getKey().along(), entry.getValue(), entry.getKey().across());
         if (!natural(level.getBlockState(pos)) || !natural(level.getBlockState(pos.below()))) {
            return false;
         }
      }

      RandomSource random = RandomSource.create(site.seed());

      for (Entry<SpiritSpringLayout.Cell, Integer> entry : banks.entrySet()) {
         BlockPos bank = at(origin, site, entry.getKey().along(), entry.getValue(), entry.getKey().across());

         for (int y = bank.getY() - 1; y <= bank.getY(); y++) {
            BlockPos pos = new BlockPos(bank.getX(), y, bank.getZ());
            if (level.isEmptyBlock(pos) || level.getBlockState(pos).is(BlockTags.DIRT)) {
               level.setBlock(pos, y == bank.getY() ? Blocks.GRASS_BLOCK.defaultBlockState() : Blocks.STONE.defaultBlockState(), 2);
            }
         }
      }

      for (Entry<SpiritSpringLayout.Cell, Integer> entry : water.entrySet()) {
         BlockPos pos = at(origin, site, entry.getKey().along(), entry.getValue(), entry.getKey().across());
         int top = surface(level, pos).getY();

         for (int y = pos.getY() + 1; y <= Math.max(top, pos.getY() + 1); y++) {
            level.setBlock(new BlockPos(pos.getX(), y, pos.getZ()), Blocks.AIR.defaultBlockState(), 2);
         }

         level.setBlock(pos.below(), Blocks.STONE.defaultBlockState(), 2);
         level.setBlock(pos, Blocks.WATER.defaultBlockState(), 2);
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      springRocks(level, origin, site, random);
      bambooPath(level, origin, site, random);
      int herbs = 0;

      for (SpiritSpringLayout.Cell cell : banks.keySet()
         .stream()
         .sorted(Comparator.comparingInt(SpiritSpringLayout.Cell::along).thenComparingInt(SpiritSpringLayout.Cell::across))
         .toList()) {
         if (herbs < 10 && random.nextFloat() < 0.25F) {
            BlockPos pos = surface(level, at(origin, site, cell.along(), 0, cell.across()));
            if (plantHipokute(level, pos, random)) {
               herbs++;
            }
         }
      }

      return true;
   }

   private static void springRocks(WorldGenLevel level, BlockPos origin, SpiritForestLandscapeLayout.Site site, RandomSource random) {
      for (int side : new int[]{-1, 1}) {
         for (int along = -12; along <= -7; along++) {
            for (int across = 3; across <= 6; across++) {
               double shape = (along + 9.5) * (along + 9.5) / 9.0 + (across - 4.0) * (across - 4.0) / 5.0;
               int height = (int)Math.round((1.25 - shape) * (side < 0 ? 4 : 5));

               for (int y = -2; y <= height && height >= 0; y++) {
                  BlockPos pos = at(origin, site, along, y, across * side);
                  setNatural(level, pos, y == height && random.nextFloat() < 0.35F ? Blocks.MOSS_BLOCK.defaultBlockState() : rock(random));
               }
            }
         }
      }

      for (int y = -1; y <= 3; y++) {
         for (int across = -1; across <= 2; across++) {
            setNatural(level, at(origin, site, -12, y, across), rock(random));
         }
      }

      BlockPos spout = at(origin, site, -11, 2, 0);
      setNatural(level, spout.below(), Blocks.STONE.defaultBlockState());

      for (int across : new int[]{-1, 1}) {
         setNatural(level, at(origin, site, -11, 2, across), Blocks.MOSSY_COBBLESTONE.defaultBlockState());
      }

      if (level.isEmptyBlock(spout)) {
         level.setBlock(spout, Blocks.WATER.defaultBlockState(), 2);
         level.scheduleTick(spout, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }
   }

   private static void bambooPath(WorldGenLevel level, BlockPos origin, SpiritForestLandscapeLayout.Site site, RandomSource random) {
      for (int along = -6; along <= 10; along++) {
         int edge = Math.max(4, SpiritSpringLayout.bend(along) + 3);

         for (int width = 0; width < 2; width++) {
            BlockPos pos = surface(level, at(origin, site, along, 0, edge + width));
            if (Math.abs(pos.getY() - origin.getY()) <= 5 && level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
               level.setBlock(pos.below(), random.nextInt(5) == 0 ? Blocks.GRAVEL.defaultBlockState() : Blocks.COARSE_DIRT.defaultBlockState(), 2);
            }
         }

         if (along % 2 == 0) {
            for (int side : new int[]{-1, 1}) {
               int across = side < 0 ? -5 - random.nextInt(2) : edge + 2 + random.nextInt(2);
               BlockPos pos = surface(level, at(origin, site, along, 0, across));
               bamboo(level, pos, 5 + random.nextInt(5));
            }
         }
      }
   }

   private static void bamboo(WorldGenLevel level, BlockPos origin, int height) {
      if (origin.getY() + height < level.getMaxBuildHeight() && level.getBlockState(origin.below()).is(BlockTags.BAMBOO_PLANTABLE_ON)) {
         for (int y = 0; y < height; y++) {
            if (!level.isEmptyBlock(origin.above(y))) {
               return;
            }
         }

         for (int y = 0; y < height; y++) {
            BambooLeaves leaves = y == height - 1 ? BambooLeaves.LARGE : (y >= height - 3 ? BambooLeaves.SMALL : BambooLeaves.NONE);
            level.setBlock(
               origin.above(y),
               (BlockState)((BlockState)((BlockState)Blocks.BAMBOO.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1))
                     .setValue(BlockStateProperties.BAMBOO_LEAVES, leaves))
                  .setValue(BlockStateProperties.STAGE, y == height - 1 ? 1 : 0),
               2
            );
         }
      }
   }

   private static boolean plantHipokute(WorldGenLevel level, BlockPos pos, RandomSource random) {
      BlockState plant = ((Block)BuiltInRegistries.BLOCK.get(HIPOKUTE)).defaultBlockState();
      if (!plant.hasProperty(BlockStateProperties.AGE_3)) {
         return false;
      }

      plant = (BlockState)plant.setValue(BlockStateProperties.AGE_3, random.nextInt(4) == 0 ? 3 : 2);
      return level.isEmptyBlock(pos) && plant.canSurvive(level, pos) ? level.setBlock(pos, plant, 2) : false;
   }

   private static boolean ruin(WorldGenLevel level, BlockPos origin, SpiritForestLandscapeLayout.Site site) {
      RandomSource random = RandomSource.create(site.seed());
      int variant = random.nextInt(3);
      if (variant == 2) {
         grotto(level, origin, site, random);
      } else {
         for (int along = -3; along <= 4; along++) {
            for (int across = -3; across <= 3; across++) {
               if ((along - 1) * (along - 1) + across * across <= 18) {
                  for (int y = -3; y <= 0; y++) {
                     BlockPos pos = at(origin, site, along, y, across);
                     setNatural(level, pos, y == 0 ? masonry(random) : rock(random));
                  }
               }
            }
         }

         if (variant == 0) {
            BlockPos stele = at(origin, site, 1, 1, 0);
            if (level.isEmptyBlock(stele) && level.isEmptyBlock(stele.above())) {
               level.setBlock(
                  stele,
                  (BlockState)((Block)BlockRegistry.ANCIENT_STELE.get())
                     .defaultBlockState()
                     .setValue(HorizontalDirectionalBlock.FACING, DIRECTIONS[site.direction()].getOpposite()),
                  2
               );
            }
         } else {
            setNatural(level, at(origin, site, 1, 1, 0), Blocks.SMOOTH_STONE_SLAB.defaultBlockState());

            for (int across : new int[]{-2, 2}) {
               setNatural(level, at(origin, site, 3, 1, across), Blocks.MOSSY_COBBLESTONE_WALL.defaultBlockState());
            }
         }
      }

      for (int along = -9; along <= -3; along++) {
         BlockPos pos = surface(level, at(origin, site, along, 0, 0)).below();

         for (int across = -1; across <= 1; across++) {
            BlockPos step = new BlockPos(site.worldX(along, across), Math.min(origin.getY(), pos.getY() + 1), site.worldZ(along, across));
            if (random.nextInt(6) != 0) {
               setNatural(level, step.below(), Blocks.COBBLESTONE.defaultBlockState());
               setNatural(
                  level, step, (BlockState)Blocks.MOSSY_STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, DIRECTIONS[site.direction()])
               );
            }
         }
      }

      return true;
   }

   private static void grotto(WorldGenLevel level, BlockPos origin, SpiritForestLandscapeLayout.Site site, RandomSource random) {
      for (int along = -1; along <= 7; along++) {
         for (int across = -4; across <= 4; across++) {
            for (int y = -2; y <= 6; y++) {
               double shell = (along - 3.0) * (along - 3.0) / 25.0 + across * across / 20.0 + y * y / 49.0;
               if (!(shell > 1.2)) {
                  BlockPos pos = at(origin, site, along, y, across);
                  boolean inside = along <= 5 && Math.abs(across) <= 1 && y >= 1 && y <= 3;
                  setNatural(level, pos, inside ? Blocks.AIR.defaultBlockState() : (y == 0 ? masonry(random) : rock(random)));
               }
            }
         }
      }
   }

   private static BlockState rock(RandomSource random) {
      return switch (random.nextInt(10)) {
         case 0 -> Blocks.CALCITE.defaultBlockState();
         case 1, 2 -> Blocks.MOSSY_COBBLESTONE.defaultBlockState();
         case 3, 4, 5 -> Blocks.ANDESITE.defaultBlockState();
         default -> Blocks.STONE.defaultBlockState();
      };
   }

   private static BlockState masonry(RandomSource random) {
      return switch (random.nextInt(4)) {
         case 0 -> Blocks.CRACKED_STONE_BRICKS.defaultBlockState();
         case 1 -> Blocks.STONE_BRICKS.defaultBlockState();
         default -> Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
      };
   }

   private static boolean natural(BlockState state) {
      return !state.hasBlockEntity()
         && state.getFluidState().isEmpty()
         && (
            state.isAir()
               || state.is(BlockTags.DIRT)
               || state.is(BlockTags.BASE_STONE_OVERWORLD)
               || state.is(BlockTags.REPLACEABLE_BY_TREES)
               || state.is(BlockTags.LEAVES)
               || state.is(Blocks.COBBLESTONE)
               || state.is(Blocks.MOSSY_COBBLESTONE)
         );
   }

   private static void setNatural(WorldGenLevel level, BlockPos pos, BlockState state) {
      if (!level.isOutsideBuildHeight(pos) && natural(level.getBlockState(pos))) {
         level.setBlock(pos, state, 2);
      }
   }

   private static BlockPos surface(WorldGenLevel level, BlockPos pos) {
      return level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, pos);
   }

   private static BlockPos at(BlockPos origin, SpiritForestLandscapeLayout.Site site, int along, int y, int across) {
      return new BlockPos(site.worldX(along, across), origin.getY() + y, site.worldZ(along, across));
   }
}
