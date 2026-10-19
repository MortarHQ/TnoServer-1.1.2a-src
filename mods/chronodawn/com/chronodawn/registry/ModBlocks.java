package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.blocks.AncientTimeWoodButton;
import com.chronodawn.blocks.AncientTimeWoodDoor;
import com.chronodawn.blocks.AncientTimeWoodFence;
import com.chronodawn.blocks.AncientTimeWoodFenceGate;
import com.chronodawn.blocks.AncientTimeWoodLeaves;
import com.chronodawn.blocks.AncientTimeWoodLog;
import com.chronodawn.blocks.AncientTimeWoodPlanks;
import com.chronodawn.blocks.AncientTimeWoodPressurePlate;
import com.chronodawn.blocks.AncientTimeWoodSapling;
import com.chronodawn.blocks.AncientTimeWoodSlab;
import com.chronodawn.blocks.AncientTimeWoodStairs;
import com.chronodawn.blocks.AncientTimeWoodTrapdoor;
import com.chronodawn.blocks.AttachedChronoMelonStemBlock;
import com.chronodawn.blocks.BossRoomBoundaryMarkerBlock;
import com.chronodawn.blocks.BossRoomDoorBlock;
import com.chronodawn.blocks.ChronoCoralBlock;
import com.chronodawn.blocks.ChronoDawnPortalBlock;
import com.chronodawn.blocks.ChronoMelonBlock;
import com.chronodawn.blocks.ChronoMelonStemBlock;
import com.chronodawn.blocks.ClockTowerTeleporterBlock;
import com.chronodawn.blocks.ClockstoneBlock;
import com.chronodawn.blocks.ClockstoneOre;
import com.chronodawn.blocks.ClockstoneSlab;
import com.chronodawn.blocks.ClockstoneStairs;
import com.chronodawn.blocks.ClockstoneWall;
import com.chronodawn.blocks.ClockworkBlock;
import com.chronodawn.blocks.ClockworkDialBlock;
import com.chronodawn.blocks.CoarseTemporalDirtBlock;
import com.chronodawn.blocks.CobbledDeepslateTemporalStoneBlock;
import com.chronodawn.blocks.CobbledDeepslateTemporalStoneSlab;
import com.chronodawn.blocks.CobbledDeepslateTemporalStoneStairs;
import com.chronodawn.blocks.CobbledDeepslateTemporalStoneWall;
import com.chronodawn.blocks.DarkTimeWoodButton;
import com.chronodawn.blocks.DarkTimeWoodDoor;
import com.chronodawn.blocks.DarkTimeWoodFence;
import com.chronodawn.blocks.DarkTimeWoodFenceGate;
import com.chronodawn.blocks.DarkTimeWoodLeaves;
import com.chronodawn.blocks.DarkTimeWoodLog;
import com.chronodawn.blocks.DarkTimeWoodPlanks;
import com.chronodawn.blocks.DarkTimeWoodPressurePlate;
import com.chronodawn.blocks.DarkTimeWoodSapling;
import com.chronodawn.blocks.DarkTimeWoodSlab;
import com.chronodawn.blocks.DarkTimeWoodStairs;
import com.chronodawn.blocks.DarkTimeWoodTrapdoor;
import com.chronodawn.blocks.DawnBellBlock;
import com.chronodawn.blocks.DawnLanternBlock;
import com.chronodawn.blocks.DecorativeWaterBlock;
import com.chronodawn.blocks.DeepslateClockstoneOre;
import com.chronodawn.blocks.DeepslateTemporalAmberOre;
import com.chronodawn.blocks.DeepslateTemporalGoldOre;
import com.chronodawn.blocks.DeepslateTemporalRedstoneOre;
import com.chronodawn.blocks.DeepslateTemporalStoneBlock;
import com.chronodawn.blocks.DuskBellBlock;
import com.chronodawn.blocks.DuskLanternBlock;
import com.chronodawn.blocks.EntropyCryptTrapdoorBlock;
import com.chronodawn.blocks.EntropyCrystalOre;
import com.chronodawn.blocks.FadedTemporalGrassBlock;
import com.chronodawn.blocks.FrozenTimeIceBlock;
import com.chronodawn.blocks.FruitOfTimeBlock;
import com.chronodawn.blocks.LumenPolypBlock;
import com.chronodawn.blocks.MossyTemporalCobblestoneBlock;
import com.chronodawn.blocks.MossyTemporalCobblestoneSlab;
import com.chronodawn.blocks.MossyTemporalCobblestoneStairs;
import com.chronodawn.blocks.MossyTemporalCobblestoneWall;
import com.chronodawn.blocks.MossyTemporalStoneBricksBlock;
import com.chronodawn.blocks.MossyTemporalStoneBricksSlab;
import com.chronodawn.blocks.MossyTemporalStoneBricksStairs;
import com.chronodawn.blocks.MossyTemporalStoneBricksWall;
import com.chronodawn.blocks.PolishedDeepslateTemporalStoneBlock;
import com.chronodawn.blocks.PolishedDeepslateTemporalStoneSlab;
import com.chronodawn.blocks.PolishedDeepslateTemporalStoneStairs;
import com.chronodawn.blocks.PolishedDeepslateTemporalStoneWall;
import com.chronodawn.blocks.ReversingTimeSandstone;
import com.chronodawn.blocks.StrippedAncientTimeWoodLog;
import com.chronodawn.blocks.StrippedDarkTimeWoodLog;
import com.chronodawn.blocks.StrippedTimeWoodLog;
import com.chronodawn.blocks.TallTemporalSeagrassBlock;
import com.chronodawn.blocks.TemporalAmberOre;
import com.chronodawn.blocks.TemporalBricksBlock;
import com.chronodawn.blocks.TemporalBricksSlab;
import com.chronodawn.blocks.TemporalBricksStairs;
import com.chronodawn.blocks.TemporalBricksWall;
import com.chronodawn.blocks.TemporalCoalOre;
import com.chronodawn.blocks.TemporalCobblestoneBlock;
import com.chronodawn.blocks.TemporalCobblestoneSlab;
import com.chronodawn.blocks.TemporalCobblestoneStairs;
import com.chronodawn.blocks.TemporalCobblestoneWall;
import com.chronodawn.blocks.TemporalDeadBushBlock;
import com.chronodawn.blocks.TemporalDirtBlock;
import com.chronodawn.blocks.TemporalFarmlandBlock;
import com.chronodawn.blocks.TemporalFernBlock;
import com.chronodawn.blocks.TemporalGoldOre;
import com.chronodawn.blocks.TemporalGrassBlock;
import com.chronodawn.blocks.TemporalGravel;
import com.chronodawn.blocks.TemporalIronOre;
import com.chronodawn.blocks.TemporalKelpBlock;
import com.chronodawn.blocks.TemporalKelpPlantBlock;
import com.chronodawn.blocks.TemporalLanternBlock;
import com.chronodawn.blocks.TemporalMossBlock;
import com.chronodawn.blocks.TemporalParticleEmitterBlock;
import com.chronodawn.blocks.TemporalPointedDripstoneBlock;
import com.chronodawn.blocks.TemporalRedstoneOre;
import com.chronodawn.blocks.TemporalRootBlock;
import com.chronodawn.blocks.TemporalSand;
import com.chronodawn.blocks.TemporalSandstone;
import com.chronodawn.blocks.TemporalSandstoneSlab;
import com.chronodawn.blocks.TemporalSandstoneStairs;
import com.chronodawn.blocks.TemporalSandstoneWall;
import com.chronodawn.blocks.TemporalSeagrassBlock;
import com.chronodawn.blocks.TemporalShortGrassBlock;
import com.chronodawn.blocks.TemporalStoneBlock;
import com.chronodawn.blocks.TemporalStoneBricksBlock;
import com.chronodawn.blocks.TemporalStoneBricksSlab;
import com.chronodawn.blocks.TemporalStoneBricksStairs;
import com.chronodawn.blocks.TemporalStoneBricksWall;
import com.chronodawn.blocks.TemporalStoneButton;
import com.chronodawn.blocks.TemporalStonePressurePlate;
import com.chronodawn.blocks.TemporalStoneSlab;
import com.chronodawn.blocks.TemporalStoneStairs;
import com.chronodawn.blocks.TemporalStoneWall;
import com.chronodawn.blocks.TemporalTallGrassBlock;
import com.chronodawn.blocks.TimeBlossomBlock;
import com.chronodawn.blocks.TimeCrystalBlock;
import com.chronodawn.blocks.TimeCrystalOre;
import com.chronodawn.blocks.TimeTorchBlock;
import com.chronodawn.blocks.TimeWheatBaleBlock;
import com.chronodawn.blocks.TimeWheatBlock;
import com.chronodawn.blocks.TimeWoodButton;
import com.chronodawn.blocks.TimeWoodDoor;
import com.chronodawn.blocks.TimeWoodFence;
import com.chronodawn.blocks.TimeWoodFenceGate;
import com.chronodawn.blocks.TimeWoodLeaves;
import com.chronodawn.blocks.TimeWoodLog;
import com.chronodawn.blocks.TimeWoodPlanks;
import com.chronodawn.blocks.TimeWoodPressurePlate;
import com.chronodawn.blocks.TimeWoodSapling;
import com.chronodawn.blocks.TimeWoodSlab;
import com.chronodawn.blocks.TimeWoodStairs;
import com.chronodawn.blocks.TimeWoodTrapdoor;
import com.chronodawn.blocks.TimelessMushroomBlock;
import com.chronodawn.blocks.UnstableFungus;
import com.chronodawn.blocks.WallTimeTorchBlock;
import com.chronodawn.compat.CompatBlockProperties;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create("chronodawn", Registries.BLOCK);
   public static final RegistrySupplier<Block> CLOCKSTONE_ORE = BLOCKS.register(
      ModBlockId.CLOCKSTONE_ORE.id(), () -> new ClockstoneOre(ClockstoneOre.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_CRYSTAL_ORE = BLOCKS.register(
      ModBlockId.TIME_CRYSTAL_ORE.id(), () -> new TimeCrystalOre(TimeCrystalOre.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_COAL_ORE = BLOCKS.register(
      ModBlockId.TEMPORAL_COAL_ORE.id(), () -> new TemporalCoalOre(TemporalCoalOre.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_GOLD_ORE = BLOCKS.register(
      ModBlockId.TEMPORAL_GOLD_ORE.id(), () -> new TemporalGoldOre(TemporalGoldOre.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_IRON_ORE = BLOCKS.register(
      ModBlockId.TEMPORAL_IRON_ORE.id(), () -> new TemporalIronOre(TemporalIronOre.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_REDSTONE_ORE = BLOCKS.register(
      ModBlockId.TEMPORAL_REDSTONE_ORE.id(), () -> new TemporalRedstoneOre(TemporalRedstoneOre.createProperties())
   );
   public static final RegistrySupplier<Block> ENTROPY_CRYSTAL_ORE = BLOCKS.register(
      ModBlockId.ENTROPY_CRYSTAL_ORE.id(), () -> new EntropyCrystalOre(EntropyCrystalOre.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_AMBER_ORE = BLOCKS.register(
      ModBlockId.TEMPORAL_AMBER_ORE.id(), () -> new TemporalAmberOre(TemporalAmberOre.createProperties())
   );
   public static final RegistrySupplier<Block> DEEPSLATE_TEMPORAL_AMBER_ORE = BLOCKS.register(
      ModBlockId.DEEPSLATE_TEMPORAL_AMBER_ORE.id(), () -> new DeepslateTemporalAmberOre(DeepslateTemporalAmberOre.createProperties())
   );
   public static final RegistrySupplier<Block> DEEPSLATE_TEMPORAL_GOLD_ORE = BLOCKS.register(
      ModBlockId.DEEPSLATE_TEMPORAL_GOLD_ORE.id(), () -> new DeepslateTemporalGoldOre(DeepslateTemporalGoldOre.createProperties())
   );
   public static final RegistrySupplier<Block> DEEPSLATE_TEMPORAL_REDSTONE_ORE = BLOCKS.register(
      ModBlockId.DEEPSLATE_TEMPORAL_REDSTONE_ORE.id(), () -> new DeepslateTemporalRedstoneOre(DeepslateTemporalRedstoneOre.createProperties())
   );
   public static final RegistrySupplier<Block> DEEPSLATE_CLOCKSTONE_ORE = BLOCKS.register(
      ModBlockId.DEEPSLATE_CLOCKSTONE_ORE.id(), () -> new DeepslateClockstoneOre(DeepslateClockstoneOre.createProperties())
   );
   public static final RegistrySupplier<Block> CLOCKSTONE_BLOCK = BLOCKS.register(
      ModBlockId.CLOCKSTONE_BLOCK.id(), () -> new ClockstoneBlock(ClockstoneBlock.createProperties())
   );
   public static final RegistrySupplier<Block> CLOCKSTONE_STAIRS = BLOCKS.register(
      ModBlockId.CLOCKSTONE_STAIRS.id(), () -> new ClockstoneStairs(ClockstoneStairs.createProperties())
   );
   public static final RegistrySupplier<Block> CLOCKSTONE_SLAB = BLOCKS.register(
      ModBlockId.CLOCKSTONE_SLAB.id(), () -> new ClockstoneSlab(ClockstoneSlab.createProperties())
   );
   public static final RegistrySupplier<Block> CLOCKSTONE_WALL = BLOCKS.register(
      ModBlockId.CLOCKSTONE_WALL.id(), () -> new ClockstoneWall(ClockstoneWall.createProperties())
   );
   public static final RegistrySupplier<Block> CHRONO_DAWN_PORTAL = BLOCKS.register(
      ModBlockId.CHRONO_DAWN_PORTAL.id(), () -> new ChronoDawnPortalBlock(ChronoDawnPortalBlock.createProperties())
   );
   public static final RegistrySupplier<Block> REVERSING_TIME_SANDSTONE = BLOCKS.register(
      ModBlockId.REVERSING_TIME_SANDSTONE.id(), () -> new ReversingTimeSandstone(ReversingTimeSandstone.createProperties())
   );
   public static final RegistrySupplier<Block> UNSTABLE_FUNGUS = BLOCKS.register(
      ModBlockId.UNSTABLE_FUNGUS.id(), () -> new UnstableFungus(UnstableFungus.createProperties())
   );
   public static final RegistrySupplier<Block> POTTED_UNSTABLE_FUNGUS = BLOCKS.register(
      ModBlockId.POTTED_UNSTABLE_FUNGUS.id(), () -> new FlowerPotBlock((Block)UNSTABLE_FUNGUS.get(), CompatBlockProperties.ofFullCopy(Blocks.FLOWER_POT))
   );
   public static final RegistrySupplier<Block> TIME_WOOD_LOG = BLOCKS.register(
      ModBlockId.TIME_WOOD_LOG.id(), () -> new TimeWoodLog(TimeWoodLog.createProperties())
   );
   public static final RegistrySupplier<Block> STRIPPED_TIME_WOOD_LOG = BLOCKS.register(
      ModBlockId.STRIPPED_TIME_WOOD_LOG.id(), () -> new StrippedTimeWoodLog(StrippedTimeWoodLog.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD = BLOCKS.register(
      ModBlockId.TIME_WOOD.id(), () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 2.0F).sound(SoundType.WOOD))
   );
   public static final RegistrySupplier<Block> STRIPPED_TIME_WOOD = BLOCKS.register(
      ModBlockId.STRIPPED_TIME_WOOD.id(), () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 2.0F).sound(SoundType.WOOD))
   );
   public static final RegistrySupplier<Block> TIME_WOOD_LEAVES = BLOCKS.register(
      ModBlockId.TIME_WOOD_LEAVES.id(), () -> new TimeWoodLeaves(TimeWoodLeaves.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD_PLANKS = BLOCKS.register(
      ModBlockId.TIME_WOOD_PLANKS.id(), () -> new TimeWoodPlanks(TimeWoodPlanks.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD_SAPLING = BLOCKS.register(
      ModBlockId.TIME_WOOD_SAPLING.id(), () -> new TimeWoodSapling(TimeWoodSapling.createProperties())
   );
   public static final RegistrySupplier<Block> POTTED_TIME_WOOD_SAPLING = BLOCKS.register(
      ModBlockId.POTTED_TIME_WOOD_SAPLING.id(), () -> new FlowerPotBlock((Block)TIME_WOOD_SAPLING.get(), CompatBlockProperties.ofFullCopy(Blocks.FLOWER_POT))
   );
   public static final RegistrySupplier<Block> FRUIT_OF_TIME_BLOCK = BLOCKS.register(
      ModBlockId.FRUIT_OF_TIME.id(), () -> new FruitOfTimeBlock(FruitOfTimeBlock.createProperties())
   );
   public static final RegistrySupplier<Block> CLOCK_TOWER_TELEPORTER = BLOCKS.register(
      ModBlockId.CLOCK_TOWER_TELEPORTER.id(),
      () -> new ClockTowerTeleporterBlock(CompatBlockProperties.ofFullCopy(Blocks.GLOWSTONE).lightLevel(state -> 15).noOcclusion())
   );
   public static final RegistrySupplier<Block> CLOCKWORK_BLOCK = BLOCKS.register(
      ModBlockId.CLOCKWORK_BLOCK.id(), () -> new ClockworkBlock(ClockworkBlock.createProperties())
   );
   public static final RegistrySupplier<Block> CLOCKWORK_DIAL = BLOCKS.register(
      ModBlockId.CLOCKWORK_DIAL.id(), () -> new ClockworkDialBlock(ClockworkDialBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_CRYSTAL_BLOCK = BLOCKS.register(
      ModBlockId.TIME_CRYSTAL_BLOCK.id(), () -> new TimeCrystalBlock(TimeCrystalBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_BRICKS = BLOCKS.register(
      ModBlockId.TEMPORAL_BRICKS.id(), () -> new TemporalBricksBlock(TemporalBricksBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_BRICKS_STAIRS = BLOCKS.register(
      ModBlockId.TEMPORAL_BRICKS_STAIRS.id(), () -> new TemporalBricksStairs(TemporalBricksStairs.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_BRICKS_SLAB = BLOCKS.register(
      ModBlockId.TEMPORAL_BRICKS_SLAB.id(), () -> new TemporalBricksSlab(TemporalBricksSlab.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_BRICKS_WALL = BLOCKS.register(
      ModBlockId.TEMPORAL_BRICKS_WALL.id(), () -> new TemporalBricksWall(TemporalBricksWall.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD_STAIRS = BLOCKS.register(
      ModBlockId.TIME_WOOD_STAIRS.id(), () -> new TimeWoodStairs(TimeWoodStairs.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD_SLAB = BLOCKS.register(
      ModBlockId.TIME_WOOD_SLAB.id(), () -> new TimeWoodSlab(TimeWoodSlab.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD_FENCE = BLOCKS.register(
      ModBlockId.TIME_WOOD_FENCE.id(), () -> new TimeWoodFence(TimeWoodFence.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD_DOOR = BLOCKS.register(
      ModBlockId.TIME_WOOD_DOOR.id(), () -> new TimeWoodDoor(TimeWoodDoor.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD_TRAPDOOR = BLOCKS.register(
      ModBlockId.TIME_WOOD_TRAPDOOR.id(), () -> new TimeWoodTrapdoor(TimeWoodTrapdoor.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD_FENCE_GATE = BLOCKS.register(
      ModBlockId.TIME_WOOD_FENCE_GATE.id(), () -> new TimeWoodFenceGate(TimeWoodFenceGate.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD_BUTTON = BLOCKS.register(
      ModBlockId.TIME_WOOD_BUTTON.id(), () -> new TimeWoodButton(TimeWoodButton.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WOOD_PRESSURE_PLATE = BLOCKS.register(
      ModBlockId.TIME_WOOD_PRESSURE_PLATE.id(), () -> new TimeWoodPressurePlate(TimeWoodPressurePlate.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_MOSS = BLOCKS.register(
      ModBlockId.TEMPORAL_MOSS.id(), () -> new TemporalMossBlock(TemporalMossBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_DIRT = BLOCKS.register(
      ModBlockId.TEMPORAL_DIRT.id(), () -> new TemporalDirtBlock(TemporalDirtBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_GRASS_BLOCK = BLOCKS.register(
      ModBlockId.TEMPORAL_GRASS_BLOCK.id(), () -> new TemporalGrassBlock(TemporalGrassBlock.createProperties())
   );
   public static final RegistrySupplier<Block> COARSE_TEMPORAL_DIRT = BLOCKS.register(
      ModBlockId.COARSE_TEMPORAL_DIRT.id(), () -> new CoarseTemporalDirtBlock(CoarseTemporalDirtBlock.createProperties())
   );
   public static final RegistrySupplier<Block> PARCHED_TEMPORAL_DIRT = BLOCKS.register(
      ModBlockId.PARCHED_TEMPORAL_DIRT.id(), () -> new Block(CompatBlockProperties.ofFullCopy(Blocks.COARSE_DIRT).strength(0.5F).sound(SoundType.GRAVEL))
   );
   public static final RegistrySupplier<Block> TEMPORAL_FARMLAND = BLOCKS.register(
      ModBlockId.TEMPORAL_FARMLAND.id(), () -> new TemporalFarmlandBlock(TemporalFarmlandBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_STONE = BLOCKS.register(
      ModBlockId.TEMPORAL_STONE.id(), () -> new TemporalStoneBlock(TemporalStoneBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_COBBLESTONE = BLOCKS.register(
      ModBlockId.TEMPORAL_COBBLESTONE.id(), () -> new TemporalCobblestoneBlock(TemporalCobblestoneBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_STONE_STAIRS = BLOCKS.register(
      ModBlockId.TEMPORAL_STONE_STAIRS.id(), () -> new TemporalStoneStairs(TemporalStoneStairs.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_STONE_SLAB = BLOCKS.register(
      ModBlockId.TEMPORAL_STONE_SLAB.id(), () -> new TemporalStoneSlab(TemporalStoneSlab.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_STONE_WALL = BLOCKS.register(
      ModBlockId.TEMPORAL_STONE_WALL.id(), () -> new TemporalStoneWall(TemporalStoneWall.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_SAND = BLOCKS.register(
      ModBlockId.TEMPORAL_SAND.id(), () -> new TemporalSand(TemporalSand.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_GRAVEL = BLOCKS.register(
      ModBlockId.TEMPORAL_GRAVEL.id(), () -> new TemporalGravel(TemporalGravel.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_SANDSTONE = BLOCKS.register(
      ModBlockId.TEMPORAL_SANDSTONE.id(), () -> new TemporalSandstone(TemporalSandstone.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_SANDSTONE_STAIRS = BLOCKS.register(
      ModBlockId.TEMPORAL_SANDSTONE_STAIRS.id(), () -> new TemporalSandstoneStairs(TemporalSandstoneStairs.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_SANDSTONE_SLAB = BLOCKS.register(
      ModBlockId.TEMPORAL_SANDSTONE_SLAB.id(), () -> new TemporalSandstoneSlab(TemporalSandstoneSlab.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_SANDSTONE_WALL = BLOCKS.register(
      ModBlockId.TEMPORAL_SANDSTONE_WALL.id(), () -> new TemporalSandstoneWall(TemporalSandstoneWall.createProperties())
   );
   public static final RegistrySupplier<Block> DEEPSLATE_TEMPORAL_STONE = BLOCKS.register(
      ModBlockId.DEEPSLATE_TEMPORAL_STONE.id(), () -> new DeepslateTemporalStoneBlock(DeepslateTemporalStoneBlock.createProperties())
   );
   public static final RegistrySupplier<Block> COBBLED_DEEPSLATE_TEMPORAL_STONE = BLOCKS.register(
      ModBlockId.COBBLED_DEEPSLATE_TEMPORAL_STONE.id(), () -> new CobbledDeepslateTemporalStoneBlock(CobbledDeepslateTemporalStoneBlock.createProperties())
   );
   public static final RegistrySupplier<Block> COBBLED_DEEPSLATE_TEMPORAL_STONE_STAIRS = BLOCKS.register(
      ModBlockId.COBBLED_DEEPSLATE_TEMPORAL_STONE_STAIRS.id(),
      () -> new CobbledDeepslateTemporalStoneStairs(CobbledDeepslateTemporalStoneStairs.createProperties())
   );
   public static final RegistrySupplier<Block> COBBLED_DEEPSLATE_TEMPORAL_STONE_SLAB = BLOCKS.register(
      ModBlockId.COBBLED_DEEPSLATE_TEMPORAL_STONE_SLAB.id(), () -> new CobbledDeepslateTemporalStoneSlab(CobbledDeepslateTemporalStoneSlab.createProperties())
   );
   public static final RegistrySupplier<Block> COBBLED_DEEPSLATE_TEMPORAL_STONE_WALL = BLOCKS.register(
      ModBlockId.COBBLED_DEEPSLATE_TEMPORAL_STONE_WALL.id(), () -> new CobbledDeepslateTemporalStoneWall(CobbledDeepslateTemporalStoneWall.createProperties())
   );
   public static final RegistrySupplier<Block> POLISHED_DEEPSLATE_TEMPORAL_STONE = BLOCKS.register(
      ModBlockId.POLISHED_DEEPSLATE_TEMPORAL_STONE.id(), () -> new PolishedDeepslateTemporalStoneBlock(PolishedDeepslateTemporalStoneBlock.createProperties())
   );
   public static final RegistrySupplier<Block> POLISHED_DEEPSLATE_TEMPORAL_STONE_STAIRS = BLOCKS.register(
      ModBlockId.POLISHED_DEEPSLATE_TEMPORAL_STONE_STAIRS.id(),
      () -> new PolishedDeepslateTemporalStoneStairs(PolishedDeepslateTemporalStoneStairs.createProperties())
   );
   public static final RegistrySupplier<Block> POLISHED_DEEPSLATE_TEMPORAL_STONE_SLAB = BLOCKS.register(
      ModBlockId.POLISHED_DEEPSLATE_TEMPORAL_STONE_SLAB.id(),
      () -> new PolishedDeepslateTemporalStoneSlab(PolishedDeepslateTemporalStoneSlab.createProperties())
   );
   public static final RegistrySupplier<Block> POLISHED_DEEPSLATE_TEMPORAL_STONE_WALL = BLOCKS.register(
      ModBlockId.POLISHED_DEEPSLATE_TEMPORAL_STONE_WALL.id(),
      () -> new PolishedDeepslateTemporalStoneWall(PolishedDeepslateTemporalStoneWall.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_POINTED_DRIPSTONE = BLOCKS.register(
      ModBlockId.TEMPORAL_POINTED_DRIPSTONE.id(), () -> new TemporalPointedDripstoneBlock(TemporalPointedDripstoneBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_COBBLESTONE_STAIRS = BLOCKS.register(
      ModBlockId.TEMPORAL_COBBLESTONE_STAIRS.id(), () -> new TemporalCobblestoneStairs(TemporalCobblestoneStairs.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_COBBLESTONE_SLAB = BLOCKS.register(
      ModBlockId.TEMPORAL_COBBLESTONE_SLAB.id(), () -> new TemporalCobblestoneSlab(TemporalCobblestoneSlab.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_COBBLESTONE_WALL = BLOCKS.register(
      ModBlockId.TEMPORAL_COBBLESTONE_WALL.id(), () -> new TemporalCobblestoneWall(TemporalCobblestoneWall.createProperties())
   );
   public static final RegistrySupplier<Block> MOSSY_TEMPORAL_COBBLESTONE = BLOCKS.register(
      ModBlockId.MOSSY_TEMPORAL_COBBLESTONE.id(), () -> new MossyTemporalCobblestoneBlock(MossyTemporalCobblestoneBlock.createProperties())
   );
   public static final RegistrySupplier<Block> MOSSY_TEMPORAL_COBBLESTONE_STAIRS = BLOCKS.register(
      ModBlockId.MOSSY_TEMPORAL_COBBLESTONE_STAIRS.id(), () -> new MossyTemporalCobblestoneStairs(MossyTemporalCobblestoneStairs.createProperties())
   );
   public static final RegistrySupplier<Block> MOSSY_TEMPORAL_COBBLESTONE_SLAB = BLOCKS.register(
      ModBlockId.MOSSY_TEMPORAL_COBBLESTONE_SLAB.id(), () -> new MossyTemporalCobblestoneSlab(MossyTemporalCobblestoneSlab.createProperties())
   );
   public static final RegistrySupplier<Block> MOSSY_TEMPORAL_COBBLESTONE_WALL = BLOCKS.register(
      ModBlockId.MOSSY_TEMPORAL_COBBLESTONE_WALL.id(), () -> new MossyTemporalCobblestoneWall(MossyTemporalCobblestoneWall.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_STONE_BRICKS = BLOCKS.register(
      ModBlockId.TEMPORAL_STONE_BRICKS.id(), () -> new TemporalStoneBricksBlock(TemporalStoneBricksBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_STONE_BRICKS_STAIRS = BLOCKS.register(
      ModBlockId.TEMPORAL_STONE_BRICKS_STAIRS.id(), () -> new TemporalStoneBricksStairs(TemporalStoneBricksStairs.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_STONE_BRICKS_SLAB = BLOCKS.register(
      ModBlockId.TEMPORAL_STONE_BRICKS_SLAB.id(), () -> new TemporalStoneBricksSlab(TemporalStoneBricksSlab.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_STONE_BRICKS_WALL = BLOCKS.register(
      ModBlockId.TEMPORAL_STONE_BRICKS_WALL.id(), () -> new TemporalStoneBricksWall(TemporalStoneBricksWall.createProperties())
   );
   public static final RegistrySupplier<Block> MOSSY_TEMPORAL_STONE_BRICKS = BLOCKS.register(
      ModBlockId.MOSSY_TEMPORAL_STONE_BRICKS.id(), () -> new MossyTemporalStoneBricksBlock(MossyTemporalStoneBricksBlock.createProperties())
   );
   public static final RegistrySupplier<Block> MOSSY_TEMPORAL_STONE_BRICKS_STAIRS = BLOCKS.register(
      ModBlockId.MOSSY_TEMPORAL_STONE_BRICKS_STAIRS.id(), () -> new MossyTemporalStoneBricksStairs(MossyTemporalStoneBricksStairs.createProperties())
   );
   public static final RegistrySupplier<Block> MOSSY_TEMPORAL_STONE_BRICKS_SLAB = BLOCKS.register(
      ModBlockId.MOSSY_TEMPORAL_STONE_BRICKS_SLAB.id(), () -> new MossyTemporalStoneBricksSlab(MossyTemporalStoneBricksSlab.createProperties())
   );
   public static final RegistrySupplier<Block> MOSSY_TEMPORAL_STONE_BRICKS_WALL = BLOCKS.register(
      ModBlockId.MOSSY_TEMPORAL_STONE_BRICKS_WALL.id(), () -> new MossyTemporalStoneBricksWall(MossyTemporalStoneBricksWall.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_STONE_BUTTON = BLOCKS.register(
      ModBlockId.TEMPORAL_STONE_BUTTON.id(), () -> new TemporalStoneButton(TemporalStoneButton.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_STONE_PRESSURE_PLATE = BLOCKS.register(
      ModBlockId.TEMPORAL_STONE_PRESSURE_PLATE.id(), () -> new TemporalStonePressurePlate(TemporalStonePressurePlate.createProperties())
   );
   public static final RegistrySupplier<Block> FROZEN_TIME_ICE = BLOCKS.register(
      ModBlockId.FROZEN_TIME_ICE.id(), () -> new FrozenTimeIceBlock(FrozenTimeIceBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TIME_WHEAT = BLOCKS.register(
      ModBlockId.TIME_WHEAT.id(), () -> new TimeWheatBlock(CompatBlockProperties.ofFullCopy(Blocks.WHEAT))
   );
   public static final RegistrySupplier<Block> TIME_WHEAT_BALE = BLOCKS.register(
      ModBlockId.TIME_WHEAT_BALE.id(), () -> new TimeWheatBaleBlock(CompatBlockProperties.ofFullCopy(Blocks.HAY_BLOCK))
   );
   public static final RegistrySupplier<Block> TEMPORAL_ROOT = BLOCKS.register(
      ModBlockId.TEMPORAL_ROOT.id(), () -> new TemporalRootBlock(CompatBlockProperties.ofFullCopy(Blocks.CARROTS))
   );
   public static final RegistrySupplier<Block> CHRONO_MELON = BLOCKS.register(
      ModBlockId.CHRONO_MELON.id(), () -> new ChronoMelonBlock(CompatBlockProperties.ofFullCopy(Blocks.MELON))
   );
   public static final RegistrySupplier<Block> CHRONO_MELON_STEM = BLOCKS.register(
      ModBlockId.CHRONO_MELON_STEM.id(), () -> new ChronoMelonStemBlock(CompatBlockProperties.ofFullCopy(Blocks.MELON_STEM))
   );
   public static final RegistrySupplier<Block> ATTACHED_CHRONO_MELON_STEM = BLOCKS.register(
      ModBlockId.ATTACHED_CHRONO_MELON_STEM.id(), () -> new AttachedChronoMelonStemBlock(CompatBlockProperties.ofFullCopy(Blocks.ATTACHED_MELON_STEM))
   );
   public static final RegistrySupplier<Block> TIMELESS_MUSHROOM = BLOCKS.register(
      ModBlockId.TIMELESS_MUSHROOM.id(), () -> new TimelessMushroomBlock(CompatBlockProperties.ofFullCopy(Blocks.BROWN_MUSHROOM).lightLevel(state -> 6))
   );
   public static final RegistrySupplier<Block> PURPLE_TIME_BLOSSOM = BLOCKS.register(
      ModBlockId.PURPLE_TIME_BLOSSOM.id(), () -> new TimeBlossomBlock(TimeBlossomBlock.createProperties())
   );
   public static final RegistrySupplier<Block> ORANGE_TIME_BLOSSOM = BLOCKS.register(
      ModBlockId.ORANGE_TIME_BLOSSOM.id(), () -> new TimeBlossomBlock(TimeBlossomBlock.createProperties())
   );
   public static final RegistrySupplier<Block> PINK_TIME_BLOSSOM = BLOCKS.register(
      ModBlockId.PINK_TIME_BLOSSOM.id(), () -> new TimeBlossomBlock(TimeBlossomBlock.createProperties())
   );
   public static final RegistrySupplier<Block> POTTED_PURPLE_TIME_BLOSSOM = BLOCKS.register(
      ModBlockId.POTTED_PURPLE_TIME_BLOSSOM.id(),
      () -> new FlowerPotBlock((Block)PURPLE_TIME_BLOSSOM.get(), CompatBlockProperties.ofFullCopy(Blocks.FLOWER_POT))
   );
   public static final RegistrySupplier<Block> POTTED_ORANGE_TIME_BLOSSOM = BLOCKS.register(
      ModBlockId.POTTED_ORANGE_TIME_BLOSSOM.id(),
      () -> new FlowerPotBlock((Block)ORANGE_TIME_BLOSSOM.get(), CompatBlockProperties.ofFullCopy(Blocks.FLOWER_POT))
   );
   public static final RegistrySupplier<Block> POTTED_PINK_TIME_BLOSSOM = BLOCKS.register(
      ModBlockId.POTTED_PINK_TIME_BLOSSOM.id(), () -> new FlowerPotBlock((Block)PINK_TIME_BLOSSOM.get(), CompatBlockProperties.ofFullCopy(Blocks.FLOWER_POT))
   );
   public static final RegistrySupplier<Block> DAWN_BELL = BLOCKS.register(ModBlockId.DAWN_BELL.id(), () -> new DawnBellBlock(DawnBellBlock.createProperties()));
   public static final RegistrySupplier<Block> DUSK_BELL = BLOCKS.register(ModBlockId.DUSK_BELL.id(), () -> new DuskBellBlock(DuskBellBlock.createProperties()));
   public static final RegistrySupplier<Block> CHRONO_COBWEB = BLOCKS.register(
      ModBlockId.CHRONO_COBWEB.id(), () -> new WebBlock(CompatBlockProperties.ofFullCopy(Blocks.COBWEB))
   );
   public static final RegistrySupplier<Block> TEMPORAL_TALL_GRASS = BLOCKS.register(
      ModBlockId.TEMPORAL_TALL_GRASS.id(), () -> new TemporalTallGrassBlock(TemporalTallGrassBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_FERN = BLOCKS.register(
      ModBlockId.TEMPORAL_FERN.id(), () -> new TemporalFernBlock(TemporalFernBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_GRASS = BLOCKS.register(
      ModBlockId.TEMPORAL_GRASS.id(), () -> new TemporalShortGrassBlock(TemporalShortGrassBlock.createProperties())
   );
   public static final RegistrySupplier<Block> FADED_TEMPORAL_GRASS = BLOCKS.register(
      ModBlockId.FADED_TEMPORAL_GRASS.id(), () -> new FadedTemporalGrassBlock(FadedTemporalGrassBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_DEAD_BUSH = BLOCKS.register(
      ModBlockId.TEMPORAL_DEAD_BUSH.id(), () -> new TemporalDeadBushBlock(TemporalDeadBushBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_KELP = BLOCKS.register(
      ModBlockId.TEMPORAL_KELP.id(), () -> new TemporalKelpBlock(TemporalKelpBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_KELP_PLANT = BLOCKS.register(
      ModBlockId.TEMPORAL_KELP_PLANT.id(), () -> new TemporalKelpPlantBlock(TemporalKelpPlantBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TEMPORAL_SEAGRASS = BLOCKS.register(
      ModBlockId.TEMPORAL_SEAGRASS.id(), () -> new TemporalSeagrassBlock(TemporalSeagrassBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TALL_TEMPORAL_SEAGRASS = BLOCKS.register(
      ModBlockId.TALL_TEMPORAL_SEAGRASS.id(), () -> new TallTemporalSeagrassBlock(TallTemporalSeagrassBlock.createProperties())
   );
   public static final RegistrySupplier<Block> LUMEN_POLYP = BLOCKS.register(
      ModBlockId.LUMEN_POLYP.id(), () -> new LumenPolypBlock(LumenPolypBlock.createProperties())
   );
   public static final RegistrySupplier<Block> DAWN_CHRONO_CORAL = BLOCKS.register(
      ModBlockId.DAWN_CHRONO_CORAL.id(), () -> new ChronoCoralBlock(ChronoCoralBlock.createProperties())
   );
   public static final RegistrySupplier<Block> DUSK_CHRONO_CORAL = BLOCKS.register(
      ModBlockId.DUSK_CHRONO_CORAL.id(), () -> new ChronoCoralBlock(ChronoCoralBlock.createProperties())
   );
   public static final RegistrySupplier<Block> TWILIGHT_CHRONO_CORAL = BLOCKS.register(
      ModBlockId.TWILIGHT_CHRONO_CORAL.id(), () -> new ChronoCoralBlock(ChronoCoralBlock.createProperties())
   );
   public static final RegistrySupplier<Block> ETERNAL_CHRONO_CORAL = BLOCKS.register(
      ModBlockId.ETERNAL_CHRONO_CORAL.id(), () -> new ChronoCoralBlock(ChronoCoralBlock.createProperties())
   );
   public static final RegistrySupplier<Block> PURPLE_TIME_TORCH = BLOCKS.register(
      ModBlockId.PURPLE_TIME_TORCH.id(), () -> new TimeTorchBlock(ParticleTypes.FLAME, TimeTorchBlock.createProperties("purple_time_torch"))
   );
   public static final RegistrySupplier<Block> ORANGE_TIME_TORCH = BLOCKS.register(
      ModBlockId.ORANGE_TIME_TORCH.id(), () -> new TimeTorchBlock(ParticleTypes.FLAME, TimeTorchBlock.createProperties("orange_time_torch"))
   );
   public static final RegistrySupplier<Block> PINK_TIME_TORCH = BLOCKS.register(
      ModBlockId.PINK_TIME_TORCH.id(), () -> new TimeTorchBlock(ParticleTypes.FLAME, TimeTorchBlock.createProperties("pink_time_torch"))
   );
   public static final RegistrySupplier<Block> WALL_PURPLE_TIME_TORCH = BLOCKS.register(
      ModBlockId.WALL_PURPLE_TIME_TORCH.id(), () -> new WallTimeTorchBlock(ParticleTypes.FLAME, WallTimeTorchBlock.createProperties("wall_purple_time_torch"))
   );
   public static final RegistrySupplier<Block> WALL_ORANGE_TIME_TORCH = BLOCKS.register(
      ModBlockId.WALL_ORANGE_TIME_TORCH.id(), () -> new WallTimeTorchBlock(ParticleTypes.FLAME, WallTimeTorchBlock.createProperties("wall_orange_time_torch"))
   );
   public static final RegistrySupplier<Block> WALL_PINK_TIME_TORCH = BLOCKS.register(
      ModBlockId.WALL_PINK_TIME_TORCH.id(), () -> new WallTimeTorchBlock(ParticleTypes.FLAME, WallTimeTorchBlock.createProperties("wall_pink_time_torch"))
   );
   public static final RegistrySupplier<Block> TEMPORAL_LANTERN = BLOCKS.register(
      ModBlockId.TEMPORAL_LANTERN.id(), () -> new TemporalLanternBlock(TemporalLanternBlock.createProperties())
   );
   public static final RegistrySupplier<Block> DAWN_LANTERN = BLOCKS.register(
      ModBlockId.DAWN_LANTERN.id(), () -> new DawnLanternBlock(DawnLanternBlock.createProperties())
   );
   public static final RegistrySupplier<Block> DUSK_LANTERN = BLOCKS.register(
      ModBlockId.DUSK_LANTERN.id(), () -> new DuskLanternBlock(DuskLanternBlock.createProperties())
   );
   public static final RegistrySupplier<Block> BOSS_ROOM_DOOR = BLOCKS.register(
      ModBlockId.BOSS_ROOM_DOOR.id(), () -> new BossRoomDoorBlock(CompatBlockProperties.ofFullCopy(Blocks.IRON_DOOR).noOcclusion())
   );
   public static final RegistrySupplier<Block> ENTROPY_CRYPT_TRAPDOOR = BLOCKS.register(
      ModBlockId.ENTROPY_CRYPT_TRAPDOOR.id(), () -> new EntropyCryptTrapdoorBlock(CompatBlockProperties.ofFullCopy(Blocks.IRON_TRAPDOOR).noOcclusion())
   );
   public static final RegistrySupplier<Block> TEMPORAL_PARTICLE_EMITTER = BLOCKS.register(
      ModBlockId.TEMPORAL_PARTICLE_EMITTER.id(), () -> new TemporalParticleEmitterBlock(TemporalParticleEmitterBlock.createProperties())
   );
   public static final RegistrySupplier<Block> DECORATIVE_WATER = BLOCKS.register(
      ModBlockId.DECORATIVE_WATER.id(),
      () -> new DecorativeWaterBlock((FlowingFluid)ModFluids.DECORATIVE_WATER.get(), CompatBlockProperties.ofFullCopy(Blocks.WATER))
   );
   public static final RegistrySupplier<Block> BOSS_ROOM_BOUNDARY_MARKER = BLOCKS.register(
      ModBlockId.BOSS_ROOM_BOUNDARY_MARKER.id(), () -> new BossRoomBoundaryMarkerBlock(Properties.of().strength(-1.0F, 3600000.0F).noLootTable().noOcclusion())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_LOG = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_LOG.id(), () -> new DarkTimeWoodLog(DarkTimeWoodLog.createProperties())
   );
   public static final RegistrySupplier<Block> STRIPPED_DARK_TIME_WOOD_LOG = BLOCKS.register(
      ModBlockId.STRIPPED_DARK_TIME_WOOD_LOG.id(), () -> new StrippedDarkTimeWoodLog(StrippedDarkTimeWoodLog.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD.id(), () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 2.0F).sound(SoundType.WOOD))
   );
   public static final RegistrySupplier<Block> STRIPPED_DARK_TIME_WOOD = BLOCKS.register(
      ModBlockId.STRIPPED_DARK_TIME_WOOD.id(), () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 2.0F).sound(SoundType.WOOD))
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_LEAVES = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_LEAVES.id(), () -> new DarkTimeWoodLeaves(DarkTimeWoodLeaves.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_PLANKS = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_PLANKS.id(), () -> new DarkTimeWoodPlanks(DarkTimeWoodPlanks.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_STAIRS = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_STAIRS.id(), () -> new DarkTimeWoodStairs(DarkTimeWoodStairs.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_SLAB = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_SLAB.id(), () -> new DarkTimeWoodSlab(DarkTimeWoodSlab.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_FENCE = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_FENCE.id(), () -> new DarkTimeWoodFence(DarkTimeWoodFence.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_DOOR = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_DOOR.id(), () -> new DarkTimeWoodDoor(DarkTimeWoodDoor.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_TRAPDOOR = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_TRAPDOOR.id(), () -> new DarkTimeWoodTrapdoor(DarkTimeWoodTrapdoor.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_FENCE_GATE = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_FENCE_GATE.id(), () -> new DarkTimeWoodFenceGate(DarkTimeWoodFenceGate.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_BUTTON = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_BUTTON.id(), () -> new DarkTimeWoodButton(DarkTimeWoodButton.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_PRESSURE_PLATE = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_PRESSURE_PLATE.id(), () -> new DarkTimeWoodPressurePlate(DarkTimeWoodPressurePlate.createProperties())
   );
   public static final RegistrySupplier<Block> DARK_TIME_WOOD_SAPLING = BLOCKS.register(
      ModBlockId.DARK_TIME_WOOD_SAPLING.id(), () -> new DarkTimeWoodSapling(DarkTimeWoodSapling.createProperties())
   );
   public static final RegistrySupplier<Block> POTTED_DARK_TIME_WOOD_SAPLING = BLOCKS.register(
      ModBlockId.POTTED_DARK_TIME_WOOD_SAPLING.id(),
      () -> new FlowerPotBlock((Block)DARK_TIME_WOOD_SAPLING.get(), CompatBlockProperties.ofFullCopy(Blocks.FLOWER_POT))
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_LOG = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_LOG.id(), () -> new AncientTimeWoodLog(AncientTimeWoodLog.createProperties())
   );
   public static final RegistrySupplier<Block> STRIPPED_ANCIENT_TIME_WOOD_LOG = BLOCKS.register(
      ModBlockId.STRIPPED_ANCIENT_TIME_WOOD_LOG.id(), () -> new StrippedAncientTimeWoodLog(StrippedAncientTimeWoodLog.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD.id(), () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 2.0F).sound(SoundType.WOOD))
   );
   public static final RegistrySupplier<Block> STRIPPED_ANCIENT_TIME_WOOD = BLOCKS.register(
      ModBlockId.STRIPPED_ANCIENT_TIME_WOOD.id(),
      () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 2.0F).sound(SoundType.WOOD))
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_LEAVES = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_LEAVES.id(), () -> new AncientTimeWoodLeaves(AncientTimeWoodLeaves.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_PLANKS = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_PLANKS.id(), () -> new AncientTimeWoodPlanks(AncientTimeWoodPlanks.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_STAIRS = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_STAIRS.id(), () -> new AncientTimeWoodStairs(AncientTimeWoodStairs.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_SLAB = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_SLAB.id(), () -> new AncientTimeWoodSlab(AncientTimeWoodSlab.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_FENCE = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_FENCE.id(), () -> new AncientTimeWoodFence(AncientTimeWoodFence.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_DOOR = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_DOOR.id(), () -> new AncientTimeWoodDoor(AncientTimeWoodDoor.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_TRAPDOOR = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_TRAPDOOR.id(), () -> new AncientTimeWoodTrapdoor(AncientTimeWoodTrapdoor.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_FENCE_GATE = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_FENCE_GATE.id(), () -> new AncientTimeWoodFenceGate(AncientTimeWoodFenceGate.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_BUTTON = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_BUTTON.id(), () -> new AncientTimeWoodButton(AncientTimeWoodButton.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_PRESSURE_PLATE = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_PRESSURE_PLATE.id(), () -> new AncientTimeWoodPressurePlate(AncientTimeWoodPressurePlate.createProperties())
   );
   public static final RegistrySupplier<Block> ANCIENT_TIME_WOOD_SAPLING = BLOCKS.register(
      ModBlockId.ANCIENT_TIME_WOOD_SAPLING.id(), () -> new AncientTimeWoodSapling(AncientTimeWoodSapling.createProperties())
   );
   public static final RegistrySupplier<Block> POTTED_ANCIENT_TIME_WOOD_SAPLING = BLOCKS.register(
      ModBlockId.POTTED_ANCIENT_TIME_WOOD_SAPLING.id(),
      () -> new FlowerPotBlock((Block)ANCIENT_TIME_WOOD_SAPLING.get(), CompatBlockProperties.ofFullCopy(Blocks.FLOWER_POT))
   );

   public static void register() {
      BLOCKS.register();
      ChronoDawn.LOGGER.debug("Registered ModBlocks");
   }
}
