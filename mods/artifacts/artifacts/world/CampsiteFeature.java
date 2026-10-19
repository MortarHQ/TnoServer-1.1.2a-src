package artifacts.world;

import artifacts.Artifacts;
import artifacts.entity.MimicEntity;
import artifacts.registry.ModEntityTypes;
import artifacts.registry.ModTags;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.storage.loot.LootTable;

public class CampsiteFeature extends Feature<CampsiteFeatureConfiguration> {
   public static final ResourceKey<LootTable> CHEST_LOOT = Artifacts.key(Registries.LOOT_TABLE, "chests/campsite_chest");
   public static final ResourceKey<LootTable> BARREL_LOOT = Artifacts.key(Registries.LOOT_TABLE, "chests/campsite_barrel");

   public CampsiteFeature() {
      super(CampsiteFeatureConfiguration.CODEC);
   }

   public boolean place(FeaturePlaceContext<CampsiteFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos origin = context.origin();
      RandomSource random = context.random();
      CampsiteFeatureConfiguration config = (CampsiteFeatureConfiguration)context.config();
      if (!this.isSufficientlyFlat(level, origin)) {
         return false;
      }

      BlockPos.betweenClosedStream(origin.offset(-2, 0, -2), origin.offset(2, 2, 2))
         .filter(posx -> Math.abs(posx.getX() - origin.getX()) < 2 || Math.abs(posx.getZ() - origin.getZ()) < 2)
         .filter(posx -> !level.getBlockState(posx).isAir())
         .forEach(posx -> this.setBlock(level, posx, Blocks.CAVE_AIR.defaultBlockState()));
      this.placeFloor(config, level, origin, random);
      this.placeCampfire(config, level, origin, random);
      Direction direction = Plane.HORIZONTAL.getRandomDirection(random);
      BlockPos pos = origin.relative(direction, 2);
      if (random.nextInt(3) == 0) {
         BlockPos.betweenClosedStream(pos.relative(direction.getClockWise()), pos.relative(direction.getCounterClockWise())).forEach(barrelPos -> {
            this.placeBarrel(level, barrelPos, random);
            if (random.nextInt(3) == 0) {
               this.placeBarrel(level, barrelPos.above(), random);
            }
         });
      } else {
         Direction bedDirection = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
         BlockState bedBlock = (BlockState)config.beds().getState(random, pos).setValue(BedBlock.FACING, bedDirection);
         this.setBlock(level, pos, (BlockState)bedBlock.setValue(BedBlock.PART, BedPart.HEAD));
         this.setBlock(level, pos.relative(bedDirection.getOpposite()), (BlockState)bedBlock.setValue(BedBlock.PART, BedPart.FOOT));
         this.placeBarrel(level, pos.relative(bedDirection), random);
         this.placeLightSource(config, level, pos.relative(bedDirection).above(), random);
      }

      direction = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
      pos = origin.relative(direction, 2);
      List<BlockPos> positions = BlockPos.betweenClosedStream(pos.relative(direction.getClockWise()), pos.relative(direction.getCounterClockWise()))
         .<BlockPos>map(BlockPos::immutable)
         .collect(Collectors.toCollection(ArrayList::new));
      Collections.shuffle(positions);
      this.placeCraftingStation(config, level, positions.removeFirst(), random, direction.getOpposite());
      this.placeFurnace(config, level, positions.removeFirst(), random, direction.getOpposite());
      this.placeChest(level, positions.removeFirst(), random, direction.getOpposite());
      return true;
   }

   private boolean isSufficientlyFlat(WorldGenLevel level, BlockPos origin) {
      return BlockPos.betweenClosedStream(origin.offset(-2, 0, -2), origin.offset(2, 0, 2))
            .filter(pos -> level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP))
            .filter(pos -> level.getBlockState(pos).isAir())
            .count()
         >= 6L;
   }

   private void placeFloor(CampsiteFeatureConfiguration config, WorldGenLevel level, BlockPos origin, RandomSource random) {
      BlockPos.betweenClosedStream(origin.offset(-2, -1, -2), origin.offset(2, -1, 2))
         .filter(pos -> Math.abs(pos.getX() - origin.getX()) < 2 || Math.abs(pos.getZ() - origin.getZ()) < 2)
         .forEach(pos -> {
            if (!level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) {
               this.setBlock(level, pos, config.floor().getState(random, pos));
            } else if (random.nextBoolean()) {
               if (level.getBlockState(pos).is(Blocks.DEEPSLATE)) {
                  this.setBlock(level, pos, Blocks.COBBLED_DEEPSLATE.defaultBlockState());
               } else if (level.getBlockState(pos).is(Blocks.STONE)) {
                  this.setBlock(level, pos, Blocks.COBBLESTONE.defaultBlockState());
               }
            }
         });
   }

   private void placeCampfire(CampsiteFeatureConfiguration config, WorldGenLevel level, BlockPos origin, RandomSource random) {
      BlockState campfire = config.unlitCampfires().getState(random, origin);
      if (Artifacts.CONFIG.general.campsite.allowLightSources.get() && random.nextFloat() < 0.1) {
         campfire = config.litCampfires().getState(random, origin);
      }

      this.setBlock(level, origin, campfire);
   }

   private void placeLightSource(CampsiteFeatureConfiguration config, WorldGenLevel level, BlockPos pos, RandomSource random) {
      if (random.nextFloat() < 0.5) {
         BlockState lightSource = config.unlitLightSources().getState(random, pos);
         if (Artifacts.CONFIG.general.campsite.allowLightSources.get() && random.nextFloat() < 0.3) {
            lightSource = config.lightSources().getState(random, pos);
         }

         this.setBlock(level, pos, lightSource);
      }
   }

   private void placeCraftingStation(CampsiteFeatureConfiguration config, WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing) {
      BlockState craftingStation = config.craftingStations().getState(random, pos);
      if (craftingStation.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
         craftingStation = (BlockState)craftingStation.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
      }

      this.setBlock(level, pos, craftingStation);
      if (random.nextInt(3) == 0) {
         this.setBlock(level, pos.above(), config.decorations().getState(random, pos));
      }
   }

   private void placeFurnace(CampsiteFeatureConfiguration config, WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing) {
      BlockState furnace = config.furnaces().getState(random, pos);
      furnace = (BlockState)furnace.setValue(FurnaceBlock.FACING, facing);
      this.setBlock(level, pos, furnace);
      if (random.nextBoolean()) {
         this.setBlock(level, pos.above(), config.furnaceChimneys().getState(random, pos));
      }
   }

   private void placeBarrel(WorldGenLevel level, BlockPos pos, RandomSource random) {
      BlockState barrel = Blocks.BARREL.defaultBlockState();
      if (random.nextBoolean()) {
         barrel = (BlockState)barrel.setValue(BarrelBlock.FACING, Direction.UP);
      } else {
         barrel = (BlockState)barrel.setValue(BarrelBlock.FACING, Plane.HORIZONTAL.getRandomDirection(random));
      }

      this.setBlock(level, pos, barrel);
      RandomizableContainer.setBlockEntityLootTable(level, random, pos, BARREL_LOOT);
   }

   public void placeChest(WorldGenLevel level, BlockPos pos, RandomSource random, Direction facing) {
      if (random.nextFloat() < Artifacts.CONFIG.general.campsite.mimicChance.get()) {
         MimicEntity mimic = (MimicEntity)ModEntityTypes.MIMIC.get().create(level.getLevel());
         if (mimic != null) {
            mimic.setDormant(true);
            mimic.setFacing(facing);
            mimic.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            level.addFreshEntity(mimic);
         }
      } else {
         BlockState chest;
         if (random.nextInt(8) == 0) {
            this.setBlock(level, pos.below(), Blocks.TNT.defaultBlockState());
            chest = Blocks.TRAPPED_CHEST.defaultBlockState();
            this.setBlock(
               level, pos, (BlockState)Blocks.TRAPPED_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Plane.HORIZONTAL.getRandomDirection(random))
            );
         } else if (Artifacts.CONFIG.general.campsite.useModdedChests.get()) {
            chest = ModTags.getTag(ModTags.CAMPSITE_CHESTS).getRandomElement(random).<Block>map(Holder::value).orElse(Blocks.CHEST).defaultBlockState();
         } else {
            chest = Blocks.CHEST.defaultBlockState();
         }

         if (chest.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            chest = (BlockState)chest.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
         }

         this.setBlock(level, pos, chest);
         RandomizableContainer.setBlockEntityLootTable(level, random, pos, CHEST_LOOT);
      }
   }
}
