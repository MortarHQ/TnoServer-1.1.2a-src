package cn.leolezury.eternalstarlight.neoforge.datagen.provider.model;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.CaveMossCarpetBlock;
import cn.leolezury.eternalstarlight.common.block.CaveMossFullBlock;
import cn.leolezury.eternalstarlight.common.block.CrateBlock;
import cn.leolezury.eternalstarlight.common.block.DryingRackBlock;
import cn.leolezury.eternalstarlight.common.block.ESPortalBlock;
import cn.leolezury.eternalstarlight.common.block.EnergyTransmitterBlock;
import cn.leolezury.eternalstarlight.common.block.FlareSpawnerBlock;
import cn.leolezury.eternalstarlight.common.block.IcicleBlock;
import cn.leolezury.eternalstarlight.common.block.LumenstemBlock;
import cn.leolezury.eternalstarlight.common.block.LumenstemPlantBlock;
import cn.leolezury.eternalstarlight.common.block.LunarisCactusBlock;
import cn.leolezury.eternalstarlight.common.block.MoonlightBushBlock;
import cn.leolezury.eternalstarlight.common.block.NocturnalMilletTopBlock;
import cn.leolezury.eternalstarlight.common.block.OrbfloraBlock;
import cn.leolezury.eternalstarlight.common.block.PolishedToxiteBlock;
import cn.leolezury.eternalstarlight.common.block.RawAmaramberBlock;
import cn.leolezury.eternalstarlight.common.block.ShadegrieveBlock;
import cn.leolezury.eternalstarlight.common.block.StarfireBirdAviaryBlock;
import cn.leolezury.eternalstarlight.common.block.StarfireBirdNestBlock;
import cn.leolezury.eternalstarlight.common.block.ThioquartzBlock;
import cn.leolezury.eternalstarlight.common.block.TorreyaCampfireBlock;
import cn.leolezury.eternalstarlight.common.block.TorreyaVinesPlantBlock;
import cn.leolezury.eternalstarlight.common.block.WaterlilyWithFlowerBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import java.util.function.IntUnaryOperator;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder.PartBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ESBlockStateProvider extends BlockStateProvider {
   private static final ResourceLocation SOLID = ResourceLocation.withDefaultNamespace("solid");
   private static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace("cutout");
   private static final ResourceLocation CUTOUT_MIPPED = ResourceLocation.withDefaultNamespace("cutout_mipped");
   private static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent");

   public ESBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
      super(output, "eternal_starlight", exFileHelper);
   }

   protected void registerStatesAndModels() {
      this.leaves((Block)ESBlocks.LUNAR_LEAVES.get());
      this.woodSet(
         (RotatedPillarBlock)ESBlocks.LUNAR_LOG.get(),
         (RotatedPillarBlock)ESBlocks.LUNAR_WOOD.get(),
         ESBlocks.LUNAR_PLANKS.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_LUNAR_LOG.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_LUNAR_WOOD.get(),
         (DoorBlock)ESBlocks.LUNAR_DOOR.get(),
         false,
         (TrapDoorBlock)ESBlocks.LUNAR_TRAPDOOR.get(),
         false,
         (PressurePlateBlock)ESBlocks.LUNAR_PRESSURE_PLATE.get(),
         (ButtonBlock)ESBlocks.LUNAR_BUTTON.get(),
         (FenceBlock)ESBlocks.LUNAR_FENCE.get(),
         (FenceGateBlock)ESBlocks.LUNAR_FENCE_GATE.get(),
         (SlabBlock)ESBlocks.LUNAR_SLAB.get(),
         (StairBlock)ESBlocks.LUNAR_STAIRS.get(),
         (Block)ESBlocks.LUNAR_SIGN.get(),
         (Block)ESBlocks.LUNAR_WALL_SIGN.get(),
         (Block)ESBlocks.LUNAR_HANGING_SIGN.get(),
         (Block)ESBlocks.LUNAR_WALL_HANGING_SIGN.get()
      );
      this.cross((Block)ESBlocks.LUNAR_SAPLING.get());
      this.pottedPlant((Block)ESBlocks.POTTED_LUNAR_SAPLING.get(), this.blockTexture((Block)ESBlocks.LUNAR_SAPLING.get()));
      this.logBlock((RotatedPillarBlock)ESBlocks.DEAD_LUNAR_LOG.get());
      this.axisBlock(
         (RotatedPillarBlock)ESBlocks.RED_CRYSTALLIZED_LUNAR_LOG.get(),
         this.blockTexture((Block)ESBlocks.RED_CRYSTALLIZED_LUNAR_LOG.get()),
         this.blockTexture((Block)ESBlocks.DEAD_LUNAR_LOG.get()).withSuffix("_top")
      );
      this.axisBlock(
         (RotatedPillarBlock)ESBlocks.BLUE_CRYSTALLIZED_LUNAR_LOG.get(),
         this.blockTexture((Block)ESBlocks.BLUE_CRYSTALLIZED_LUNAR_LOG.get()),
         this.blockTexture((Block)ESBlocks.DEAD_LUNAR_LOG.get()).withSuffix("_top")
      );
      this.snowyLeaves((Block)ESBlocks.NORTHLAND_LEAVES.get());
      this.woodSet(
         (RotatedPillarBlock)ESBlocks.NORTHLAND_LOG.get(),
         (RotatedPillarBlock)ESBlocks.NORTHLAND_WOOD.get(),
         ESBlocks.NORTHLAND_PLANKS.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_NORTHLAND_LOG.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_NORTHLAND_WOOD.get(),
         (DoorBlock)ESBlocks.NORTHLAND_DOOR.get(),
         false,
         (TrapDoorBlock)ESBlocks.NORTHLAND_TRAPDOOR.get(),
         false,
         (PressurePlateBlock)ESBlocks.NORTHLAND_PRESSURE_PLATE.get(),
         (ButtonBlock)ESBlocks.NORTHLAND_BUTTON.get(),
         (FenceBlock)ESBlocks.NORTHLAND_FENCE.get(),
         (FenceGateBlock)ESBlocks.NORTHLAND_FENCE_GATE.get(),
         (SlabBlock)ESBlocks.NORTHLAND_SLAB.get(),
         (StairBlock)ESBlocks.NORTHLAND_STAIRS.get(),
         (Block)ESBlocks.NORTHLAND_SIGN.get(),
         (Block)ESBlocks.NORTHLAND_WALL_SIGN.get(),
         (Block)ESBlocks.NORTHLAND_HANGING_SIGN.get(),
         (Block)ESBlocks.NORTHLAND_WALL_HANGING_SIGN.get()
      );
      this.cross((Block)ESBlocks.NORTHLAND_SAPLING.get());
      this.pottedPlant((Block)ESBlocks.POTTED_NORTHLAND_SAPLING.get(), this.blockTexture((Block)ESBlocks.NORTHLAND_SAPLING.get()));
      this.leaves((Block)ESBlocks.BANYIN_LEAVES.get());
      this.woodSet(
         (RotatedPillarBlock)ESBlocks.BANYIN_LOG.get(),
         (RotatedPillarBlock)ESBlocks.BANYIN_WOOD.get(),
         ESBlocks.BANYIN_PLANKS.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_BANYIN_LOG.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_BANYIN_WOOD.get(),
         (DoorBlock)ESBlocks.BANYIN_DOOR.get(),
         true,
         (TrapDoorBlock)ESBlocks.BANYIN_TRAPDOOR.get(),
         true,
         (PressurePlateBlock)ESBlocks.BANYIN_PRESSURE_PLATE.get(),
         (ButtonBlock)ESBlocks.BANYIN_BUTTON.get(),
         (FenceBlock)ESBlocks.BANYIN_FENCE.get(),
         (FenceGateBlock)ESBlocks.BANYIN_FENCE_GATE.get(),
         (SlabBlock)ESBlocks.BANYIN_SLAB.get(),
         (StairBlock)ESBlocks.BANYIN_STAIRS.get(),
         (Block)ESBlocks.BANYIN_SIGN.get(),
         (Block)ESBlocks.BANYIN_WALL_SIGN.get(),
         (Block)ESBlocks.BANYIN_HANGING_SIGN.get(),
         (Block)ESBlocks.BANYIN_WALL_HANGING_SIGN.get()
      );
      this.cross((Block)ESBlocks.BANYIN_SAPLING.get());
      this.pottedPlant((Block)ESBlocks.POTTED_BANYIN_SAPLING.get(), this.blockTexture((Block)ESBlocks.BANYIN_SAPLING.get()));
      this.mangroveRoots((Block)ESBlocks.BANYIN_ROOTS.get());
      this.muddyMangroveRoots((RotatedPillarBlock)ESBlocks.MUDDY_BANYIN_ROOTS.get());
      this.leaves((Block)ESBlocks.SCARLET_LEAVES.get());
      this.layered(ESBlocks.SCARLET_LEAVES_PILE.get(), this.blockTexture((Block)ESBlocks.SCARLET_LEAVES.get()));
      this.woodSet(
         (RotatedPillarBlock)ESBlocks.SCARLET_LOG.get(),
         (RotatedPillarBlock)ESBlocks.SCARLET_WOOD.get(),
         ESBlocks.SCARLET_PLANKS.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_SCARLET_LOG.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_SCARLET_WOOD.get(),
         (DoorBlock)ESBlocks.SCARLET_DOOR.get(),
         false,
         (TrapDoorBlock)ESBlocks.SCARLET_TRAPDOOR.get(),
         false,
         (PressurePlateBlock)ESBlocks.SCARLET_PRESSURE_PLATE.get(),
         (ButtonBlock)ESBlocks.SCARLET_BUTTON.get(),
         (FenceBlock)ESBlocks.SCARLET_FENCE.get(),
         (FenceGateBlock)ESBlocks.SCARLET_FENCE_GATE.get(),
         (SlabBlock)ESBlocks.SCARLET_SLAB.get(),
         (StairBlock)ESBlocks.SCARLET_STAIRS.get(),
         (Block)ESBlocks.SCARLET_SIGN.get(),
         (Block)ESBlocks.SCARLET_WALL_SIGN.get(),
         (Block)ESBlocks.SCARLET_HANGING_SIGN.get(),
         (Block)ESBlocks.SCARLET_WALL_HANGING_SIGN.get()
      );
      this.cross((Block)ESBlocks.SCARLET_SAPLING.get());
      this.pottedPlant((Block)ESBlocks.POTTED_SCARLET_SAPLING.get(), this.blockTexture((Block)ESBlocks.SCARLET_SAPLING.get()));
      this.leaves((Block)ESBlocks.TORREYA_LEAVES.get());
      this.woodSet(
         (RotatedPillarBlock)ESBlocks.TORREYA_LOG.get(),
         (RotatedPillarBlock)ESBlocks.TORREYA_WOOD.get(),
         ESBlocks.TORREYA_PLANKS.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_TORREYA_LOG.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_TORREYA_WOOD.get(),
         (DoorBlock)ESBlocks.TORREYA_DOOR.get(),
         true,
         (TrapDoorBlock)ESBlocks.TORREYA_TRAPDOOR.get(),
         true,
         (PressurePlateBlock)ESBlocks.TORREYA_PRESSURE_PLATE.get(),
         (ButtonBlock)ESBlocks.TORREYA_BUTTON.get(),
         (FenceBlock)ESBlocks.TORREYA_FENCE.get(),
         (FenceGateBlock)ESBlocks.TORREYA_FENCE_GATE.get(),
         (SlabBlock)ESBlocks.TORREYA_SLAB.get(),
         (StairBlock)ESBlocks.TORREYA_STAIRS.get(),
         (Block)ESBlocks.TORREYA_SIGN.get(),
         (Block)ESBlocks.TORREYA_WALL_SIGN.get(),
         (Block)ESBlocks.TORREYA_HANGING_SIGN.get(),
         (Block)ESBlocks.TORREYA_WALL_HANGING_SIGN.get()
      );
      this.cross((Block)ESBlocks.TORREYA_SAPLING.get());
      this.pottedPlant((Block)ESBlocks.POTTED_TORREYA_SAPLING.get(), this.blockTexture((Block)ESBlocks.TORREYA_SAPLING.get()));
      this.cross((Block)ESBlocks.TORREYA_VINES.get());
      this.torreyaVines((Block)ESBlocks.TORREYA_VINES_PLANT.get());
      this.torreyaCampfire((Block)ESBlocks.TORREYA_CAMPFIRE.get());
      this.cross((Block)ESBlocks.HANGING_ALGALEAVES.get());
      this.cross((Block)ESBlocks.HANGING_ALGALEAVES_PLANT.get());
      this.multifaceBlock((Block)ESBlocks.ALGALEAVES.get());
      this.woodSet(
         (RotatedPillarBlock)ESBlocks.JINGLESTEM_LOG.get(),
         (RotatedPillarBlock)ESBlocks.JINGLESTEM_WOOD.get(),
         ESBlocks.JINGLESTEM_PLANKS.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_JINGLESTEM_LOG.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_JINGLESTEM_WOOD.get(),
         (DoorBlock)ESBlocks.JINGLESTEM_DOOR.get(),
         true,
         (TrapDoorBlock)ESBlocks.JINGLESTEM_TRAPDOOR.get(),
         true,
         (PressurePlateBlock)ESBlocks.JINGLESTEM_PRESSURE_PLATE.get(),
         (ButtonBlock)ESBlocks.JINGLESTEM_BUTTON.get(),
         (FenceBlock)ESBlocks.JINGLESTEM_FENCE.get(),
         (FenceGateBlock)ESBlocks.JINGLESTEM_FENCE_GATE.get(),
         (SlabBlock)ESBlocks.JINGLESTEM_SLAB.get(),
         (StairBlock)ESBlocks.JINGLESTEM_STAIRS.get(),
         (Block)ESBlocks.JINGLESTEM_SIGN.get(),
         (Block)ESBlocks.JINGLESTEM_WALL_SIGN.get(),
         (Block)ESBlocks.JINGLESTEM_HANGING_SIGN.get(),
         (Block)ESBlocks.JINGLESTEM_WALL_HANGING_SIGN.get()
      );
      this.cross((Block)ESBlocks.JINGLESTEM_SAPLING.get());
      this.pottedPlant((Block)ESBlocks.POTTED_JINGLESTEM_SAPLING.get(), this.blockTexture((Block)ESBlocks.JINGLESTEM_SAPLING.get()));
      this.leaves((Block)ESBlocks.CRADLEWOOD_LEAVES.get());
      this.woodSet(
         (RotatedPillarBlock)ESBlocks.CRADLEWOOD_LOG.get(),
         (RotatedPillarBlock)ESBlocks.CRADLEWOOD_WOOD.get(),
         ESBlocks.CRADLEWOOD_PLANKS.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_CRADLEWOOD_LOG.get(),
         (RotatedPillarBlock)ESBlocks.STRIPPED_CRADLEWOOD_WOOD.get(),
         (DoorBlock)ESBlocks.CRADLEWOOD_DOOR.get(),
         true,
         (TrapDoorBlock)ESBlocks.CRADLEWOOD_TRAPDOOR.get(),
         true,
         (PressurePlateBlock)ESBlocks.CRADLEWOOD_PRESSURE_PLATE.get(),
         (ButtonBlock)ESBlocks.CRADLEWOOD_BUTTON.get(),
         (FenceBlock)ESBlocks.CRADLEWOOD_FENCE.get(),
         (FenceGateBlock)ESBlocks.CRADLEWOOD_FENCE_GATE.get(),
         (SlabBlock)ESBlocks.CRADLEWOOD_SLAB.get(),
         (StairBlock)ESBlocks.CRADLEWOOD_STAIRS.get(),
         (Block)ESBlocks.CRADLEWOOD_SIGN.get(),
         (Block)ESBlocks.CRADLEWOOD_WALL_SIGN.get(),
         (Block)ESBlocks.CRADLEWOOD_HANGING_SIGN.get(),
         (Block)ESBlocks.CRADLEWOOD_WALL_HANGING_SIGN.get()
      );
      this.cross(ESBlocks.CRADLEWOOD_SAPLING.get());
      this.pottedPlant((Block)ESBlocks.POTTED_CRADLEWOOD_SAPLING.get(), this.blockTexture(ESBlocks.CRADLEWOOD_SAPLING.get()));
      this.randomlyMirroredBlock(ESBlocks.GRIMSTONE.get());
      this.slabBlock((SlabBlock)ESBlocks.GRIMSTONE_SLAB.get(), this.blockTexture(ESBlocks.GRIMSTONE.get()), this.blockTexture(ESBlocks.GRIMSTONE.get()));
      this.stairsBlock((StairBlock)ESBlocks.GRIMSTONE_STAIRS.get(), this.blockTexture(ESBlocks.GRIMSTONE.get()));
      this.wallBlock((WallBlock)ESBlocks.GRIMSTONE_WALL.get(), this.blockTexture(ESBlocks.GRIMSTONE.get()));
      this.simpleBlock(ESBlocks.CHISELED_GRIMSTONE.get());
      this.stoneSet(
         ESBlocks.COBBLED_GRIMSTONE.get(),
         (SlabBlock)ESBlocks.COBBLED_GRIMSTONE_SLAB.get(),
         (StairBlock)ESBlocks.COBBLED_GRIMSTONE_STAIRS.get(),
         (WallBlock)ESBlocks.COBBLED_GRIMSTONE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.GRIMSTONE_BRICKS.get(),
         (SlabBlock)ESBlocks.GRIMSTONE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.GRIMSTONE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.GRIMSTONE_BRICK_WALL.get()
      );
      this.simpleBlock(ESBlocks.CRACKED_GRIMSTONE_BRICKS.get());
      this.stoneSet(
         ESBlocks.POLISHED_GRIMSTONE.get(),
         (SlabBlock)ESBlocks.POLISHED_GRIMSTONE_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_GRIMSTONE_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_GRIMSTONE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.GRIMSTONE_TILES.get(),
         (SlabBlock)ESBlocks.GRIMSTONE_TILE_SLAB.get(),
         (StairBlock)ESBlocks.GRIMSTONE_TILE_STAIRS.get(),
         (WallBlock)ESBlocks.GRIMSTONE_TILE_WALL.get()
      );
      this.simpleBlock(ESBlocks.CRACKED_GRIMSTONE_TILES.get());
      this.stoneSet(
         ESBlocks.POLISHED_GRIMSTONE_TILES.get(),
         (SlabBlock)ESBlocks.POLISHED_GRIMSTONE_TILE_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_GRIMSTONE_TILE_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_GRIMSTONE_TILE_WALL.get()
      );
      this.simpleBlock(ESBlocks.GLOWING_GRIMSTONE.get());
      this.randomlyMirroredAndRotatedBlock(ESBlocks.VOIDSTONE.get());
      this.slabBlock((SlabBlock)ESBlocks.VOIDSTONE_SLAB.get(), this.blockTexture(ESBlocks.VOIDSTONE.get()), this.blockTexture(ESBlocks.VOIDSTONE.get()));
      this.stairsBlock((StairBlock)ESBlocks.VOIDSTONE_STAIRS.get(), this.blockTexture(ESBlocks.VOIDSTONE.get()));
      this.wallBlock((WallBlock)ESBlocks.VOIDSTONE_WALL.get(), this.blockTexture(ESBlocks.VOIDSTONE.get()));
      this.simpleBlock(ESBlocks.CHISELED_VOIDSTONE.get());
      this.stoneSet(
         ESBlocks.COBBLED_VOIDSTONE.get(),
         (SlabBlock)ESBlocks.COBBLED_VOIDSTONE_SLAB.get(),
         (StairBlock)ESBlocks.COBBLED_VOIDSTONE_STAIRS.get(),
         (WallBlock)ESBlocks.COBBLED_VOIDSTONE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.VOIDSTONE_BRICKS.get(),
         (SlabBlock)ESBlocks.VOIDSTONE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.VOIDSTONE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.VOIDSTONE_BRICK_WALL.get()
      );
      this.simpleBlock(ESBlocks.CRACKED_VOIDSTONE_BRICKS.get());
      this.stoneSet(
         ESBlocks.POLISHED_VOIDSTONE.get(),
         (SlabBlock)ESBlocks.POLISHED_VOIDSTONE_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_VOIDSTONE_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_VOIDSTONE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.VOIDSTONE_TILES.get(),
         (SlabBlock)ESBlocks.VOIDSTONE_TILE_SLAB.get(),
         (StairBlock)ESBlocks.VOIDSTONE_TILE_STAIRS.get(),
         (WallBlock)ESBlocks.VOIDSTONE_TILE_WALL.get()
      );
      this.simpleBlock(ESBlocks.CRACKED_VOIDSTONE_TILES.get());
      this.simpleBlock(ESBlocks.GLOWING_VOIDSTONE.get());
      this.simpleBlock(ESBlocks.ETERNAL_ICE.get());
      this.simpleBlock(
         (Block)ESBlocks.THIN_ETERNAL_ICE.get(),
         ((BlockModelBuilder)this.models()
               .cubeAll(this.name((Block)ESBlocks.THIN_ETERNAL_ICE.get()), this.blockTexture((Block)ESBlocks.THIN_ETERNAL_ICE.get())))
            .renderType(TRANSLUCENT)
      );
      this.stoneSet(
         ESBlocks.ETERNAL_ICE_BRICKS.get(),
         (SlabBlock)ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.ETERNAL_ICE_BRICK_WALL.get()
      );
      this.onOffBlock(
         (Block)ESBlocks.ETERNAL_ICE_LANTERN.get(),
         BlockStateProperties.HANGING,
         this.models().getExistingFile(EternalStarlight.id("eternal_ice_lantern_hanging")),
         this.models().getExistingFile(EternalStarlight.id("eternal_ice_lantern"))
      );
      this.simpleBlock(ESBlocks.HAZE_ICE.get());
      this.stoneSet(
         ESBlocks.HAZE_ICE_BRICKS.get(),
         (SlabBlock)ESBlocks.HAZE_ICE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.HAZE_ICE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.HAZE_ICE_BRICK_WALL.get()
      );
      this.onOffBlock(
         (Block)ESBlocks.HAZE_ICE_LANTERN.get(),
         BlockStateProperties.HANGING,
         this.models().getExistingFile(EternalStarlight.id("haze_ice_lantern_hanging")),
         this.models().getExistingFile(EternalStarlight.id("haze_ice_lantern"))
      );
      this.simpleBlock(
         (Block)ESBlocks.REINFORCED_ICE.get(),
         ((BlockModelBuilder)this.models().cubeAll(this.name((Block)ESBlocks.REINFORCED_ICE.get()), this.blockTexture((Block)ESBlocks.REINFORCED_ICE.get())))
            .renderType(TRANSLUCENT)
      );
      this.paneBlockWithRenderType(
         (IronBarsBlock)ESBlocks.REINFORCED_ICE_PANE.get(),
         this.blockTexture((Block)ESBlocks.REINFORCED_ICE.get()),
         this.blockTexture((Block)ESBlocks.REINFORCED_ICE_PANE.get()).withSuffix("_top"),
         TRANSLUCENT
      );
      this.icicle(ESBlocks.ICICLE.get());
      this.layered(ESBlocks.ASHEN_SNOW.get(), this.blockTexture(ESBlocks.ASHEN_SNOW.get()));
      this.simpleBlock(ESBlocks.NEBULAITE.get());
      this.stoneSet(
         ESBlocks.NEBULAITE_BRICKS.get(),
         (SlabBlock)ESBlocks.NEBULAITE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.NEBULAITE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.NEBULAITE_BRICK_WALL.get()
      );
      this.simpleBlock(ESBlocks.CHISELED_NEBULAITE_BRICKS.get());
      this.simpleBlock(ESBlocks.STARCORE_BLOCK.get());
      this.simpleBlock(ESBlocks.BLAZING_STARCORE_BLOCK.get());
      this.simpleBlock(ESBlocks.STARCORE_LIGHT.get());
      this.simpleBlock((Block)ESBlocks.GRIMSTONE_STARCORE_ORE.get());
      this.simpleBlock((Block)ESBlocks.VOIDSTONE_STARCORE_ORE.get());
      this.simpleBlock((Block)ESBlocks.ETERNAL_ICE_STARCORE_ORE.get());
      this.simpleBlock((Block)ESBlocks.HAZE_ICE_STARCORE_ORE.get());
      this.stoneSet(
         ESBlocks.RADIANITE.get(),
         (SlabBlock)ESBlocks.RADIANITE_SLAB.get(),
         (StairBlock)ESBlocks.RADIANITE_STAIRS.get(),
         (WallBlock)ESBlocks.RADIANITE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.COBBLED_RADIANITE.get(),
         (SlabBlock)ESBlocks.COBBLED_RADIANITE_SLAB.get(),
         (StairBlock)ESBlocks.COBBLED_RADIANITE_STAIRS.get(),
         (WallBlock)ESBlocks.COBBLED_RADIANITE_WALL.get()
      );
      this.axisBlock((RotatedPillarBlock)ESBlocks.RADIANITE_PILLAR.get());
      this.stoneSet(
         ESBlocks.RADIANITE_BRICKS.get(),
         (SlabBlock)ESBlocks.RADIANITE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.RADIANITE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.RADIANITE_BRICK_WALL.get()
      );
      this.stoneSet(
         ESBlocks.POLISHED_RADIANITE.get(),
         (SlabBlock)ESBlocks.POLISHED_RADIANITE_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_RADIANITE_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_RADIANITE_WALL.get()
      );
      this.simpleBlock(ESBlocks.CHISELED_RADIANITE.get());
      this.stoneSet(
         ESBlocks.FLARE_BRICKS.get(),
         (SlabBlock)ESBlocks.FLARE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.FLARE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.FLARE_BRICK_WALL.get()
      );
      this.stoneSet(
         ESBlocks.CUT_FLARE_BRICKS.get(),
         (SlabBlock)ESBlocks.CUT_FLARE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.CUT_FLARE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.CUT_FLARE_BRICK_WALL.get()
      );
      this.stoneSet(
         ESBlocks.FLARE_TILES.get(),
         (SlabBlock)ESBlocks.FLARE_TILE_SLAB.get(),
         (StairBlock)ESBlocks.FLARE_TILE_STAIRS.get(),
         (WallBlock)ESBlocks.FLARE_TILE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.CUT_FLARE_TILES.get(),
         (SlabBlock)ESBlocks.CUT_FLARE_TILE_SLAB.get(),
         (StairBlock)ESBlocks.CUT_FLARE_TILE_STAIRS.get(),
         (WallBlock)ESBlocks.CUT_FLARE_TILE_WALL.get()
      );
      this.axisBlock((RotatedPillarBlock)ESBlocks.CHISELED_FLARE_PILLAR.get());
      this.stoneSet(
         ESBlocks.STELLAGMITE.get(),
         (SlabBlock)ESBlocks.STELLAGMITE_SLAB.get(),
         (StairBlock)ESBlocks.STELLAGMITE_STAIRS.get(),
         (WallBlock)ESBlocks.STELLAGMITE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.MOLTEN_STELLAGMITE.get(),
         (SlabBlock)ESBlocks.MOLTEN_STELLAGMITE_SLAB.get(),
         (StairBlock)ESBlocks.MOLTEN_STELLAGMITE_STAIRS.get(),
         (WallBlock)ESBlocks.MOLTEN_STELLAGMITE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.POLISHED_STELLAGMITE.get(),
         (SlabBlock)ESBlocks.POLISHED_STELLAGMITE_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_STELLAGMITE_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_STELLAGMITE_WALL.get()
      );
      this.simpleBlock(ESBlocks.ABYSSLATE.get());
      this.simpleBlock(ESBlocks.CHISELED_POLISHED_ABYSSLATE.get());
      this.stoneSet(
         ESBlocks.POLISHED_ABYSSLATE.get(),
         (SlabBlock)ESBlocks.POLISHED_ABYSSLATE_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_ABYSSLATE_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_ABYSSLATE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.POLISHED_ABYSSLATE_BRICKS.get(),
         (SlabBlock)ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get()
      );
      this.simpleBlock((Block)ESBlocks.ABYSSAL_MAGMA_BLOCK.get());
      this.geyser((Block)ESBlocks.ABYSSAL_GEYSER.get(), ESBlocks.ABYSSLATE.get());
      this.simpleBlock(ESBlocks.THERMABYSSLATE.get());
      this.simpleBlock(ESBlocks.CHISELED_POLISHED_THERMABYSSLATE.get());
      this.stoneSet(
         ESBlocks.POLISHED_THERMABYSSLATE.get(),
         (SlabBlock)ESBlocks.POLISHED_THERMABYSSLATE_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_THERMABYSSLATE_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_THERMABYSSLATE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get(),
         (SlabBlock)ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get()
      );
      this.simpleBlock((Block)ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get());
      this.geyser((Block)ESBlocks.THERMABYSSAL_GEYSER.get(), ESBlocks.THERMABYSSLATE.get());
      this.simpleBlock(ESBlocks.CRYOBYSSLATE.get());
      this.simpleBlock(ESBlocks.CHISELED_POLISHED_CRYOBYSSLATE.get());
      this.stoneSet(
         ESBlocks.POLISHED_CRYOBYSSLATE.get(),
         (SlabBlock)ESBlocks.POLISHED_CRYOBYSSLATE_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_CRYOBYSSLATE_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_CRYOBYSSLATE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get(),
         (SlabBlock)ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get()
      );
      this.simpleBlock((Block)ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get());
      this.geyser((Block)ESBlocks.CRYOBYSSAL_GEYSER.get(), ESBlocks.CRYOBYSSLATE.get());
      this.simpleBlock((Block)ESBlocks.NIGHTFALL_MUD.get());
      this.simpleBlock((Block)ESBlocks.GLOWING_NIGHTFALL_MUD.get());
      this.simpleBlock(ESBlocks.PACKED_NIGHTFALL_MUD.get());
      this.stoneSet(
         ESBlocks.NIGHTFALL_MUD_BRICKS.get(),
         (SlabBlock)ESBlocks.NIGHTFALL_MUD_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.NIGHTFALL_MUD_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.NIGHTFALL_MUD_BRICK_WALL.get()
      );
      this.sand((Block)ESBlocks.TWILIGHT_SAND.get());
      this.sandstoneAndCut(ESBlocks.TWILIGHT_SANDSTONE.get(), ESBlocks.CUT_TWILIGHT_SANDSTONE.get());
      this.slabBlock(
         (SlabBlock)ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top")
      );
      this.stairsBlock(
         (StairBlock)ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get(),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top")
      );
      this.wallBlock((WallBlock)ESBlocks.TWILIGHT_SANDSTONE_WALL.get(), this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_bottom"));
      this.slabBlock(
         (SlabBlock)ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get(),
         this.blockTexture(ESBlocks.CUT_TWILIGHT_SANDSTONE.get()),
         this.blockTexture(ESBlocks.CUT_TWILIGHT_SANDSTONE.get()),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top")
      );
      this.stairsBlock(
         (StairBlock)ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get(),
         this.blockTexture(ESBlocks.CUT_TWILIGHT_SANDSTONE.get()),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"),
         this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top")
      );
      this.wallBlock((WallBlock)ESBlocks.CUT_TWILIGHT_SANDSTONE_WALL.get(), this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top"));
      this.simpleBlock(
         ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get(),
         this.models()
            .cubeColumn(
               this.name(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get()),
               this.blockTexture(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get()),
               this.blockTexture(ESBlocks.TWILIGHT_SANDSTONE.get()).withSuffix("_top")
            )
      );
      this.sand((Block)ESBlocks.DUSTED_GRAVEL.get());
      this.stoneSet(
         ESBlocks.DUSTED_BRICKS.get(),
         (SlabBlock)ESBlocks.DUSTED_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.DUSTED_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.DUSTED_BRICK_WALL.get()
      );
      this.simpleGrassBlock(ESBlocks.MOSSY_DUSTED_GRAVEL.get(), this.blockTexture((Block)ESBlocks.DUSTED_GRAVEL.get()));
      this.simpleGrassBlock(
         ESBlocks.GLOWING_MOSSY_DUSTED_GRAVEL.get(),
         this.blockTexture(ESBlocks.MOSSY_DUSTED_GRAVEL.get()).withSuffix("_side"),
         this.blockTexture(ESBlocks.GLOWING_MOSSY_DUSTED_GRAVEL.get()).withSuffix("_top"),
         this.blockTexture((Block)ESBlocks.DUSTED_GRAVEL.get())
      );
      this.suspiciousBlock((Block)ESBlocks.SUSPICIOUS_DUSTED_GRAVEL.get());
      this.sand((Block)ESBlocks.DIMSLAG.get());
      this.suspiciousBlock((Block)ESBlocks.SUSPICIOUS_DIMSLAG.get());
      this.cross((Block)ESBlocks.STARLIGHT_FLOWER.get());
      this.pottedPlant((Block)ESBlocks.POTTED_STARLIGHT_FLOWER.get(), this.blockTexture((Block)ESBlocks.STARLIGHT_FLOWER.get()));
      this.cross((Block)ESBlocks.AUREATE_FLOWER.get());
      this.pottedPlant((Block)ESBlocks.POTTED_AUREATE_FLOWER.get(), this.blockTexture((Block)ESBlocks.AUREATE_FLOWER.get()));
      this.cross((Block)ESBlocks.CONEBLOOM.get());
      this.pottedPlant((Block)ESBlocks.POTTED_CONEBLOOM.get(), this.blockTexture((Block)ESBlocks.CONEBLOOM.get()));
      this.cross((Block)ESBlocks.NIGHTFAN.get());
      this.pottedPlant((Block)ESBlocks.POTTED_NIGHTFAN.get(), this.blockTexture((Block)ESBlocks.NIGHTFAN.get()));
      this.cross((Block)ESBlocks.PINK_ROSE.get());
      this.pottedPlant((Block)ESBlocks.POTTED_PINK_ROSE.get(), this.blockTexture((Block)ESBlocks.PINK_ROSE.get()));
      this.cross((Block)ESBlocks.STARLIGHT_TORCHFLOWER.get());
      this.pottedPlant((Block)ESBlocks.POTTED_STARLIGHT_TORCHFLOWER.get(), this.blockTexture((Block)ESBlocks.STARLIGHT_TORCHFLOWER.get()));
      this.doublePlant((Block)ESBlocks.NIGHTFAN_BUSH.get());
      this.doublePlant((Block)ESBlocks.PINK_ROSE_BUSH.get());
      this.cross((Block)ESBlocks.NIGHT_SPROUTS.get());
      this.cross((Block)ESBlocks.SMALL_NIGHT_SPROUTS.get());
      this.cross((Block)ESBlocks.GLOWING_NIGHT_SPROUTS.get());
      this.cross((Block)ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get());
      this.cross((Block)ESBlocks.LUNAR_GRASS.get());
      this.cross((Block)ESBlocks.GLOWING_LUNAR_GRASS.get());
      this.cross((Block)ESBlocks.CRESCENT_GRASS.get());
      this.pottedPlant((Block)ESBlocks.POTTED_CRESCENT_GRASS.get(), this.blockTexture((Block)ESBlocks.CRESCENT_GRASS.get()));
      this.cross((Block)ESBlocks.GLOWING_CRESCENT_GRASS.get());
      this.pottedPlant((Block)ESBlocks.POTTED_GLOWING_CRESCENT_GRASS.get(), this.blockTexture((Block)ESBlocks.GLOWING_CRESCENT_GRASS.get()));
      this.cross((Block)ESBlocks.PARASOL_GRASS.get());
      this.pottedPlant((Block)ESBlocks.POTTED_PARASOL_GRASS.get(), this.blockTexture((Block)ESBlocks.PARASOL_GRASS.get()));
      this.cross((Block)ESBlocks.GLOWING_PARASOL_GRASS.get());
      this.pottedPlant((Block)ESBlocks.POTTED_GLOWING_PARASOL_GRASS.get(), this.blockTexture((Block)ESBlocks.GLOWING_PARASOL_GRASS.get()));
      this.cross((Block)ESBlocks.LUNAR_BUSH.get());
      this.cross((Block)ESBlocks.GLOWING_LUNAR_BUSH.get());
      this.doublePlant((Block)ESBlocks.TALL_CRESCENT_GRASS.get());
      this.doublePlant((Block)ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get());
      this.doublePlant((Block)ESBlocks.LUNAR_REED.get());
      this.cross((Block)ESBlocks.WHISPERBLOOM.get());
      this.pottedPlant((Block)ESBlocks.POTTED_WHISPERBLOOM.get(), this.blockTexture((Block)ESBlocks.WHISPERBLOOM.get()));
      this.cross((Block)ESBlocks.GLADESPIKE.get());
      this.pottedPlant((Block)ESBlocks.POTTED_GLADESPIKE.get(), this.blockTexture((Block)ESBlocks.GLADESPIKE.get()));
      this.cross((Block)ESBlocks.VIVIDSTALK.get());
      this.pottedPlant((Block)ESBlocks.POTTED_VIVIDSTALK.get(), this.blockTexture((Block)ESBlocks.VIVIDSTALK.get()));
      this.doublePlant((Block)ESBlocks.TALL_GLADESPIKE.get());
      this.onOffBlock(
         (Block)ESBlocks.MOONLIGHT_BUSH.get(),
         MoonlightBushBlock.BERRIES,
         ((BlockModelBuilder)this.models()
               .cross(
                  this.name((Block)ESBlocks.MOONLIGHT_BUSH.get()) + "_berries", this.blockTexture((Block)ESBlocks.MOONLIGHT_BUSH.get()).withSuffix("_berries")
               ))
            .renderType(CUTOUT),
         ((BlockModelBuilder)this.models().cross(this.name((Block)ESBlocks.MOONLIGHT_BUSH.get()), this.blockTexture((Block)ESBlocks.MOONLIGHT_BUSH.get())))
            .renderType(CUTOUT)
      );
      this.doublePlant((Block)ESBlocks.GLINTGRASS.get());
      this.cross((Block)ESBlocks.GLOWING_MUSHROOM.get());
      this.pottedPlant((Block)ESBlocks.POTTED_GLOWING_MUSHROOM.get(), this.blockTexture((Block)ESBlocks.GLOWING_MUSHROOM.get()));
      this.mushroomLikeBlock((Block)ESBlocks.GLOWING_MUSHROOM_BLOCK.get());
      this.mushroomLikeBlock(
         (Block)ESBlocks.GLOWING_MUSHROOM_STEM.get(),
         this.name((Block)ESBlocks.GLOWING_MUSHROOM_STEM.get()),
         this.blockTexture((Block)ESBlocks.GLOWING_MUSHROOM_STEM.get()),
         this.name((Block)ESBlocks.GLOWING_MUSHROOM_STEM.get()) + "_inside",
         this.blockTexture((Block)ESBlocks.GLOWING_MUSHROOM_BLOCK.get()).withSuffix("_inside")
      );
      this.cross((Block)ESBlocks.SHINING_MUSHROOM.get());
      this.pottedPlant((Block)ESBlocks.POTTED_SHINING_MUSHROOM.get(), this.blockTexture((Block)ESBlocks.SHINING_MUSHROOM.get()));
      this.mushroomLikeBlock((Block)ESBlocks.SHINING_MUSHROOM_BLOCK.get());
      this.mushroomLikeBlock(
         (Block)ESBlocks.SHINING_MUSHROOM_STEM.get(),
         this.name((Block)ESBlocks.SHINING_MUSHROOM_STEM.get()),
         this.blockTexture((Block)ESBlocks.SHINING_MUSHROOM_STEM.get()),
         this.name((Block)ESBlocks.SHINING_MUSHROOM_STEM.get()) + "_inside",
         this.blockTexture((Block)ESBlocks.SHINING_MUSHROOM_BLOCK.get()).withSuffix("_inside")
      );
      this.vinesWithFruit((Block)ESBlocks.BERRIES_VINES.get());
      this.vinesWithFruit((Block)ESBlocks.BERRIES_VINES_PLANT.get());
      this.tintedCross((Block)ESBlocks.CAVE_MOSS.get());
      this.tintedCross((Block)ESBlocks.CAVE_MOSS_PLANT.get());
      this.multifaceBlock((Block)ESBlocks.CAVE_MOSS_VEIN.get());
      this.caveMossFull(ESBlocks.CAVE_MOSS_BLOCK.get());
      this.onOffBlock(
         (Block)ESBlocks.CAVE_MOSS_CARPET.get(),
         CaveMossCarpetBlock.BOTTOM,
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.CAVE_MOSS_CARPET.get()).withSuffix("_bottom")),
         this.models()
            .singleTexture(
               this.name((Block)ESBlocks.CAVE_MOSS_CARPET.get()),
               EternalStarlight.id("block/tinted_carpet"),
               "wool",
               this.blockTexture(ESBlocks.CAVE_MOSS_BLOCK.get())
            )
      );
      this.directionalBud(ESBlocks.BOULDERSHROOM.get());
      this.pottedPlant((Block)ESBlocks.POTTED_BOULDERSHROOM.get(), this.blockTexture(ESBlocks.BOULDERSHROOM.get()));
      this.mushroomLikeBlock((Block)ESBlocks.BOULDERSHROOM_BLOCK.get());
      this.mushroomLikeBlock(
         (Block)ESBlocks.BOULDERSHROOM_STEM.get(),
         this.name((Block)ESBlocks.BOULDERSHROOM_STEM.get()),
         this.blockTexture((Block)ESBlocks.BOULDERSHROOM_STEM.get()),
         this.name((Block)ESBlocks.BOULDERSHROOM_STEM.get()) + "_inside",
         this.blockTexture((Block)ESBlocks.BOULDERSHROOM_BLOCK.get()).withSuffix("_inside")
      );
      this.cross((Block)ESBlocks.BOULDERSHROOM_ROOTS.get());
      this.cross((Block)ESBlocks.BOULDERSHROOM_ROOTS_PLANT.get());
      this.cross((Block)ESBlocks.SWAMP_ROSE.get());
      this.pottedPlant((Block)ESBlocks.POTTED_SWAMP_ROSE.get(), this.blockTexture((Block)ESBlocks.SWAMP_ROSE.get()));
      this.cross((Block)ESBlocks.FANTABUD.get());
      this.cross((Block)ESBlocks.GREEN_FANTABUD.get());
      this.cross((Block)ESBlocks.FANTAFERN.get());
      this.pottedPlant((Block)ESBlocks.POTTED_FANTAFERN.get(), this.blockTexture((Block)ESBlocks.FANTAFERN.get()));
      this.cross((Block)ESBlocks.GREEN_FANTAFERN.get());
      this.pottedPlant((Block)ESBlocks.POTTED_GREEN_FANTAFERN.get(), this.blockTexture((Block)ESBlocks.GREEN_FANTAFERN.get()));
      this.cross((Block)ESBlocks.FANTAGRASS.get());
      this.cross((Block)ESBlocks.GREEN_FANTAGRASS.get());
      this.cross((Block)ESBlocks.HANGING_FANTAGRASS.get());
      this.cross((Block)ESBlocks.HANGING_FANTAGRASS_PLANT.get());
      this.cross((Block)ESBlocks.ORANGE_SCARLET_BUD.get());
      this.cross((Block)ESBlocks.PURPLE_SCARLET_BUD.get());
      this.cross((Block)ESBlocks.RED_SCARLET_BUD.get());
      this.cross((Block)ESBlocks.SCARLET_GRASS.get());
      this.cross((Block)ESBlocks.MAUVE_FERN.get());
      this.cross((Block)ESBlocks.WITHERED_STARLIGHT_FLOWER.get());
      this.pottedPlant((Block)ESBlocks.POTTED_WITHERED_STARLIGHT_FLOWER.get(), this.blockTexture((Block)ESBlocks.WITHERED_STARLIGHT_FLOWER.get()));
      this.cross((Block)ESBlocks.AMARAMBER_GRASS.get());
      this.pottedPlant((Block)ESBlocks.POTTED_AMARAMBER_GRASS.get(), this.blockTexture((Block)ESBlocks.AMARAMBER_GRASS.get()));
      this.cross((Block)ESBlocks.AMARAMBER_GRASS_BUSH.get());
      this.cross((Block)ESBlocks.GLOOMCANDLE_ROOT.get());
      this.simpleBlock(ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get());
      this.simpleBlock(ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
      this.directionalBud(ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get());
      this.directionalBud(ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
      this.simpleBlock(ESBlocks.RED_STARLIGHT_CRYSTAL_LANTERN.get());
      this.simpleBlock(ESBlocks.BLUE_STARLIGHT_CRYSTAL_LANTERN.get());
      this.cross((Block)ESBlocks.DEAD_LUNAR_BUSH.get());
      this.pottedPlant((Block)ESBlocks.POTTED_DEAD_LUNAR_BUSH.get(), this.blockTexture((Block)ESBlocks.DEAD_LUNAR_BUSH.get()));
      this.cross((Block)ESBlocks.DESERT_AMETHYSIA.get());
      this.pottedPlant((Block)ESBlocks.POTTED_DESERT_AMETHYSIA.get(), this.blockTexture((Block)ESBlocks.DESERT_AMETHYSIA.get()));
      this.cross((Block)ESBlocks.WITHERED_DESERT_AMETHYSIA.get());
      this.pottedPlant((Block)ESBlocks.POTTED_WITHERED_DESERT_AMETHYSIA.get(), this.blockTexture((Block)ESBlocks.WITHERED_DESERT_AMETHYSIA.get()));
      this.cross((Block)ESBlocks.SUNSET_THORNBLOOM.get());
      this.pottedPlant((Block)ESBlocks.POTTED_SUNSET_THORNBLOOM.get(), this.blockTexture((Block)ESBlocks.SUNSET_THORNBLOOM.get()));
      this.cross((Block)ESBlocks.AMETHYSIA_GRASS.get());
      this.lunarisCactus(ESBlocks.LUNARIS_CACTUS.get());
      this.simpleExisting((Block)ESBlocks.LUNARIS_CACTUS_GEL_BLOCK.get());
      this.horizontalBlock(
         (Block)ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get(),
         this.models()
            .orientableWithBottom(
               this.name((Block)ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get()),
               this.blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit"),
               this.blockTexture((Block)ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get()),
               this.blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit_bottom"),
               this.blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit_top")
            )
      );
      this.horizontalBlock(
         (Block)ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get(),
         this.models()
            .orientableWithBottom(
               this.name((Block)ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get()),
               this.blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit"),
               this.blockTexture((Block)ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get()),
               this.blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit_bottom"),
               this.blockTexture(ESBlocks.LUNARIS_CACTUS.get()).withSuffix("_fruit_top")
            )
      );
      this.directionalBud(ESBlocks.BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER.get());
      this.directionalBud(ESBlocks.BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
      this.cross((Block)ESBlocks.RED_CRYSTALFLEUR.get());
      this.pottedPlant((Block)ESBlocks.POTTED_RED_CRYSTALFLEUR.get(), this.blockTexture((Block)ESBlocks.RED_CRYSTALFLEUR.get()));
      this.cross((Block)ESBlocks.BLUE_CRYSTALFLEUR.get());
      this.pottedPlant((Block)ESBlocks.POTTED_BLUE_CRYSTALFLEUR.get(), this.blockTexture((Block)ESBlocks.BLUE_CRYSTALFLEUR.get()));
      this.multifaceBlock((Block)ESBlocks.RED_CRYSTALFLEUR_VINE.get());
      this.multifaceBlock((Block)ESBlocks.BLUE_CRYSTALFLEUR_VINE.get());
      this.randomlyMirroredAndRotatedBlock(ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get());
      this.randomlyMirroredAndRotatedBlock(ESBlocks.BLUE_CRYSTAL_MOSS_BLOCK.get());
      this.carpet((Block)ESBlocks.RED_CRYSTAL_MOSS_CARPET.get(), this.blockTexture(ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get()));
      this.carpet((Block)ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get(), this.blockTexture(ESBlocks.BLUE_CRYSTAL_MOSS_BLOCK.get()));
      this.cross((Block)ESBlocks.FIRE_ORCHID.get());
      this.pottedPlant((Block)ESBlocks.POTTED_FIRE_ORCHID.get(), this.blockTexture((Block)ESBlocks.FIRE_ORCHID.get()));
      this.cross((Block)ESBlocks.BLAZEBANK_GRASS.get());
      this.waterlily((Block)ESBlocks.MOONLIGHT_LILY_PAD.get());
      this.waterlilyWithFlower((Block)ESBlocks.STARLIT_LILY_PAD.get());
      this.waterlily((Block)ESBlocks.MOONLIGHT_DUCKWEED.get());
      this.vinesWithFruit((Block)ESBlocks.ABYSSAL_KELP.get());
      this.vinesWithFruit((Block)ESBlocks.ABYSSAL_KELP_PLANT.get());
      this.orbflora((Block)ESBlocks.ORBFLORA.get());
      this.cross((Block)ESBlocks.ORBFLORA_PLANT.get());
      this.simpleBlock(ESBlocks.ORBFLORA_LIGHT.get());
      this.simpleExisting((Block)ESBlocks.SPIRAL_KELP.get());
      this.simpleExisting((Block)ESBlocks.SPIRAL_KELP_PLANT.get());
      this.multifaceBlock((Block)ESBlocks.SEA_ROSA.get());
      this.doublePlant((Block)ESBlocks.WICK_GRASS.get());
      this.lumenstem((Block)ESBlocks.LUMENSTEM.get());
      this.lumenstemPlant((Block)ESBlocks.LUMENSTEM_PLANT.get());
      this.cross((Block)ESBlocks.MARIMOLD.get());
      this.simpleBlock(
         (Block)ESBlocks.MARIMOLD_BLOCK.get(),
         ((BlockModelBuilder)this.models().cubeAll(this.name((Block)ESBlocks.MARIMOLD_BLOCK.get()), this.blockTexture((Block)ESBlocks.MARIMOLD_BLOCK.get())))
            .renderType(TRANSLUCENT)
      );
      this.mushroomLikeBlock((Block)ESBlocks.MARIMOLD_STEM.get());
      this.cross((Block)ESBlocks.CIRCULUSH.get());
      this.cross((Block)ESBlocks.STONETT.get());
      this.cross((Block)ESBlocks.LUMINIS.get());
      this.cross((Block)ESBlocks.GLOWLIS.get());
      this.cross((Block)ESBlocks.GLOREED.get());
      this.cross((Block)ESBlocks.STARLIGHT_SEAGRASS.get());
      this.cross((Block)ESBlocks.JINGLING_PICKLE.get());
      this.cross((Block)ESBlocks.DEAD_TENTACLES_CORAL.get());
      this.cross((Block)ESBlocks.TENTACLES_CORAL.get());
      this.coralFan((Block)ESBlocks.DEAD_TENTACLES_CORAL_FAN.get());
      this.coralFan((Block)ESBlocks.TENTACLES_CORAL_FAN.get());
      this.coralWallFan((Block)ESBlocks.DEAD_TENTACLES_CORAL_WALL_FAN.get(), (Block)ESBlocks.DEAD_TENTACLES_CORAL_FAN.get());
      this.coralWallFan((Block)ESBlocks.TENTACLES_CORAL_WALL_FAN.get(), (Block)ESBlocks.TENTACLES_CORAL_FAN.get());
      this.simpleBlock(ESBlocks.DEAD_TENTACLES_CORAL_BLOCK.get());
      this.simpleBlock((Block)ESBlocks.TENTACLES_CORAL_BLOCK.get());
      this.cross((Block)ESBlocks.DEAD_GOLDEN_CORAL.get());
      this.cross((Block)ESBlocks.GOLDEN_CORAL.get());
      this.coralFan((Block)ESBlocks.DEAD_GOLDEN_CORAL_FAN.get());
      this.coralFan((Block)ESBlocks.GOLDEN_CORAL_FAN.get());
      this.coralWallFan((Block)ESBlocks.DEAD_GOLDEN_CORAL_WALL_FAN.get(), (Block)ESBlocks.DEAD_GOLDEN_CORAL_FAN.get());
      this.coralWallFan((Block)ESBlocks.GOLDEN_CORAL_WALL_FAN.get(), (Block)ESBlocks.GOLDEN_CORAL_FAN.get());
      this.simpleBlock(ESBlocks.DEAD_GOLDEN_CORAL_BLOCK.get());
      this.simpleBlock((Block)ESBlocks.GOLDEN_CORAL_BLOCK.get());
      this.cross((Block)ESBlocks.DEAD_CRYSTALLUM_CORAL.get());
      this.cross((Block)ESBlocks.CRYSTALLUM_CORAL.get());
      this.coralFan((Block)ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN.get());
      this.coralFan((Block)ESBlocks.CRYSTALLUM_CORAL_FAN.get());
      this.coralWallFan((Block)ESBlocks.DEAD_CRYSTALLUM_CORAL_WALL_FAN.get(), (Block)ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN.get());
      this.coralWallFan((Block)ESBlocks.CRYSTALLUM_CORAL_WALL_FAN.get(), (Block)ESBlocks.CRYSTALLUM_CORAL_FAN.get());
      this.simpleBlock(ESBlocks.DEAD_CRYSTALLUM_CORAL_BLOCK.get());
      this.simpleBlock((Block)ESBlocks.CRYSTALLUM_CORAL_BLOCK.get());
      this.mushroomLikeBlock(ESBlocks.VELVETUMOSS.get());
      this.directionalDenseCrossBud(ESBlocks.VELVETUMOSS_VILLI.get());
      this.simpleBlock(ESBlocks.RED_VELVETUMOSS.get());
      this.directionalDenseCrossBud(ESBlocks.RED_VELVETUMOSS_VILLI.get());
      this.cross((Block)ESBlocks.RED_VELVETUMOSS_FLOWER.get());
      this.pottedPlant((Block)ESBlocks.POTTED_RED_VELVETUMOSS_FLOWER.get(), this.blockTexture((Block)ESBlocks.RED_VELVETUMOSS_FLOWER.get()));
      this.cross((Block)ESBlocks.CRYSTALLIZED_LUNAR_GRASS.get());
      this.cross((Block)ESBlocks.RED_CRYSTAL_ROOTS.get());
      this.cross((Block)ESBlocks.BLUE_CRYSTAL_ROOTS.get());
      this.doublePlant((Block)ESBlocks.TWILVEWRYM_HERB.get());
      this.doublePlant((Block)ESBlocks.STELLAFLY_BUSH.get());
      this.doublePlant((Block)ESBlocks.GLIMMERFLY_BUSH.get());
      this.cross((Block)ESBlocks.GOLDEN_GRASS.get());
      this.doublePlant((Block)ESBlocks.TALL_GOLDEN_GRASS.get());
      this.simpleBlock(ESBlocks.NIGHTFALL_DIRT.get());
      this.farmland((Block)ESBlocks.NIGHTFALL_FARMLAND.get(), ESBlocks.NIGHTFALL_DIRT.get());
      this.dirtPath((Block)ESBlocks.NIGHTFALL_DIRT_PATH.get(), ESBlocks.NIGHTFALL_DIRT.get());
      this.grassBlock((Block)ESBlocks.NIGHTFALL_GRASS_BLOCK.get(), this.blockTexture(ESBlocks.NIGHTFALL_DIRT.get()));
      this.snowyDirtBlock(
         (Block)ESBlocks.NIGHTFALL_PODZOL.get(),
         this.blockTexture(ESBlocks.NIGHTFALL_DIRT.get()),
         this.blockTexture((Block)ESBlocks.NIGHTFALL_GRASS_BLOCK.get()).withSuffix("_snow")
      );
      this.simpleGrassBlock((Block)ESBlocks.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get(), this.blockTexture(ESBlocks.NIGHTFALL_DIRT.get()));
      this.simpleGrassBlock((Block)ESBlocks.FANTASY_GRASS_BLOCK.get(), this.blockTexture((Block)ESBlocks.NIGHTFALL_MUD.get()));
      this.carpet((Block)ESBlocks.FANTASY_GRASS_CARPET.get(), this.blockTexture((Block)ESBlocks.FANTASY_GRASS_BLOCK.get()).withSuffix("_top"));
      this.simpleGrassBlock((Block)ESBlocks.GOLDEN_GRASS_BLOCK.get(), this.blockTexture(ESBlocks.NIGHTFALL_DIRT.get()));
      this.crinoa((Block)ESBlocks.CRINOA.get());
      this.directionalCubeBottomTop(ESBlocks.CRINOA_BALE.get());
      this.nocturnalMilletTop((Block)ESBlocks.NOCTURNAL_MILLET_PANICLE.get());
      this.nocturnalMilletBottom((Block)ESBlocks.NOCTURNAL_MILLET_STALK.get());
      this.simpleBlock(ESBlocks.RAW_AETHERSENT_BLOCK.get());
      this.simpleBlock(ESBlocks.AETHERSENT_BLOCK.get());
      this.stoneSet(
         ESBlocks.SPRINGSTONE.get(),
         (SlabBlock)ESBlocks.SPRINGSTONE_SLAB.get(),
         (StairBlock)ESBlocks.SPRINGSTONE_STAIRS.get(),
         (WallBlock)ESBlocks.SPRINGSTONE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.SPRINGSTONE_BRICKS.get(),
         (SlabBlock)ESBlocks.SPRINGSTONE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.SPRINGSTONE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.SPRINGSTONE_BRICK_WALL.get()
      );
      this.stoneSet(
         ESBlocks.POLISHED_SPRINGSTONE.get(),
         (SlabBlock)ESBlocks.POLISHED_SPRINGSTONE_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_SPRINGSTONE_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_SPRINGSTONE_WALL.get()
      );
      this.simpleBlock(ESBlocks.CHISELED_SPRINGSTONE.get());
      this.stoneSet(
         ESBlocks.THERMAL_SPRINGSTONE.get(),
         (SlabBlock)ESBlocks.THERMAL_SPRINGSTONE_SLAB.get(),
         (StairBlock)ESBlocks.THERMAL_SPRINGSTONE_STAIRS.get(),
         (WallBlock)ESBlocks.THERMAL_SPRINGSTONE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.THERMAL_SPRINGSTONE_BRICKS.get(),
         (SlabBlock)ESBlocks.THERMAL_SPRINGSTONE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.THERMAL_SPRINGSTONE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.THERMAL_SPRINGSTONE_BRICK_WALL.get()
      );
      this.simpleBlock((Block)ESBlocks.GLACITE.get());
      this.simpleBlock(ESBlocks.GLACITE_BLOCK.get());
      this.simpleBlock((Block)ESBlocks.GRIMSTONE_STARLIT_DIAMOND_ORE.get());
      this.simpleBlock((Block)ESBlocks.VOIDSTONE_STARLIT_DIAMOND_ORE.get());
      this.simpleBlock((Block)ESBlocks.ETERNAL_ICE_STARLIT_DIAMOND_ORE.get());
      this.simpleBlock((Block)ESBlocks.HAZE_ICE_STARLIT_DIAMOND_ORE.get());
      this.simpleBlock(ESBlocks.STARLIT_DIAMOND_BLOCK.get());
      this.simpleBlock(ESBlocks.GRIMSTONE_DEEPSILVER_ORE.get());
      this.simpleBlock(ESBlocks.VOIDSTONE_DEEPSILVER_ORE.get());
      this.simpleBlock(ESBlocks.ETERNAL_ICE_DEEPSILVER_ORE.get());
      this.simpleBlock(ESBlocks.HAZE_ICE_DEEPSILVER_ORE.get());
      this.simpleBlock(ESBlocks.NIGHTFALL_MUD_DEEPSILVER_ORE.get());
      this.simpleBlock(ESBlocks.PACKED_NIGHTFALL_MUD_DEEPSILVER_ORE.get());
      this.simpleBlock(ESBlocks.RAW_DEEPSILVER_BLOCK.get());
      this.simpleBlock(ESBlocks.DEEPSILVER_BLOCK.get());
      this.simpleBlock(
         (Block)ESBlocks.DEEPSILVER_GRATE.get(),
         ((BlockModelBuilder)this.models()
               .cubeAll(this.name((Block)ESBlocks.DEEPSILVER_GRATE.get()), this.blockTexture((Block)ESBlocks.DEEPSILVER_GRATE.get())))
            .renderType(CUTOUT)
      );
      this.simpleExisting(ESBlocks.UNREALIUM_BLOCK.get());
      this.simpleBlock((Block)ESBlocks.GRIMSTONE_MALARITE_ORE.get());
      this.simpleBlock((Block)ESBlocks.VOIDSTONE_MALARITE_ORE.get());
      this.simpleBlock((Block)ESBlocks.NIGHTFALL_MUD_MALARITE_ORE.get());
      this.simpleBlock((Block)ESBlocks.PACKED_NIGHTFALL_MUD_MALARITE_ORE.get());
      this.simpleBlock(ESBlocks.MALARITE_BLOCK.get());
      this.pungencyFruit((Block)ESBlocks.PUNGENCY_FRUIT_VINES.get());
      this.cubeBottomTop((Block)ESBlocks.TEAR_BOMB.get());
      this.dryingRack((Block)ESBlocks.DRYING_RACK.get());
      this.starfireBirdNest((Block)ESBlocks.STARFIRE_BIRD_NEST.get());
      this.starfireBirdAviary((Block)ESBlocks.OAK_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("oak"));
      this.starfireBirdAviary((Block)ESBlocks.SPRUCE_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("spruce"));
      this.starfireBirdAviary((Block)ESBlocks.BIRCH_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("birch"));
      this.starfireBirdAviary((Block)ESBlocks.ACACIA_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("acacia"));
      this.starfireBirdAviary((Block)ESBlocks.CHERRY_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("cherry"));
      this.starfireBirdAviary((Block)ESBlocks.JUNGLE_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("jungle"));
      this.starfireBirdAviary((Block)ESBlocks.DARK_OAK_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("dark_oak"));
      this.starfireBirdAviary((Block)ESBlocks.CRIMSON_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("crimson"), "stem");
      this.starfireBirdAviary((Block)ESBlocks.WARPED_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("warped"), "stem");
      this.starfireBirdAviary((Block)ESBlocks.MANGROVE_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("mangrove"));
      this.starfireBirdAviary((Block)ESBlocks.BAMBOO_STARFIRE_BIRD_AVIARY.get(), ResourceLocation.withDefaultNamespace("bamboo"), "block");
      this.starfireBirdAviary((Block)ESBlocks.LUNAR_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("lunar"));
      this.starfireBirdAviary((Block)ESBlocks.NORTHLAND_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("northland"));
      this.starfireBirdAviary((Block)ESBlocks.BANYIN_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("banyin"));
      this.starfireBirdAviary((Block)ESBlocks.SCARLET_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("scarlet"));
      this.starfireBirdAviary((Block)ESBlocks.TORREYA_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("torreya"));
      this.starfireBirdAviary((Block)ESBlocks.JINGLESTEM_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("jinglestem"));
      this.starfireBirdAviary((Block)ESBlocks.CRADLEWOOD_STARFIRE_BIRD_AVIARY.get(), EternalStarlight.id("cradlewood"));
      this.simpleBlock(ESBlocks.RAW_FLOWGLAZE.get());
      this.simpleBlock(
         (Block)ESBlocks.FLOWGLAZE.get(),
         ((BlockModelBuilder)this.models().cubeAll(this.name((Block)ESBlocks.FLOWGLAZE.get()), this.blockTexture((Block)ESBlocks.FLOWGLAZE.get())))
            .renderType(TRANSLUCENT)
      );
      this.paneBlockWithRenderType(
         (IronBarsBlock)ESBlocks.FLOWGLAZE_PANE.get(),
         this.blockTexture((Block)ESBlocks.FLOWGLAZE.get()),
         this.blockTexture((Block)ESBlocks.FLOWGLAZE_PANE.get()).withSuffix("_top"),
         TRANSLUCENT
      );
      this.stoneSet(
         ESBlocks.FLOWGLAZE_BRICKS.get(),
         (SlabBlock)ESBlocks.FLOWGLAZE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.FLOWGLAZE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.FLOWGLAZE_BRICK_WALL.get()
      );
      this.simpleBlock((Block)ESBlocks.GRIMSTONE_SALTPETER_ORE.get());
      this.simpleBlock((Block)ESBlocks.VOIDSTONE_SALTPETER_ORE.get());
      this.simpleBlock((Block)ESBlocks.ETERNAL_ICE_SALTPETER_ORE.get());
      this.simpleBlock((Block)ESBlocks.HAZE_ICE_SALTPETER_ORE.get());
      this.simpleBlock(ESBlocks.SALTPETER_BLOCK.get());
      this.rawAmaramberBlock(ESBlocks.RAW_AMARAMBER_BLOCK.get());
      this.lantern((Block)ESBlocks.AMARAMBER_LANTERN.get());
      this.candle((Block)ESBlocks.AMARAMBER_CANDLE.get());
      this.candleCake((Block)ESBlocks.AMARAMBER_CANDLE_CAKE.get(), Blocks.CAKE, (Block)ESBlocks.AMARAMBER_CANDLE.get());
      this.stoneSet(
         ESBlocks.AMARAMBER_BRICKS.get(),
         (SlabBlock)ESBlocks.AMARAMBER_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.AMARAMBER_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.AMARAMBER_BRICK_WALL.get()
      );
      this.stoneSet(
         ESBlocks.TORREYA_TILES.get(),
         (SlabBlock)ESBlocks.TORREYA_TILE_SLAB.get(),
         (StairBlock)ESBlocks.TORREYA_TILE_STAIRS.get(),
         (WallBlock)ESBlocks.TORREYA_TILE_WALL.get()
      );
      this.particleOnly((Block)ESBlocks.ETHER.get());
      this.thioquartzBlock(ESBlocks.THIOQUARTZ_BLOCK.get());
      this.simpleBlock(ESBlocks.BUDDING_THIOQUARTZ.get());
      this.directionalBud(ESBlocks.THIOQUARTZ_CLUSTER.get());
      this.stoneSet(
         ESBlocks.TOXITE.get(), (SlabBlock)ESBlocks.TOXITE_SLAB.get(), (StairBlock)ESBlocks.TOXITE_STAIRS.get(), (WallBlock)ESBlocks.TOXITE_WALL.get()
      );
      this.stoneSet(
         ESBlocks.TOXITE_BRICKS.get(),
         (SlabBlock)ESBlocks.TOXITE_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.TOXITE_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.TOXITE_BRICK_WALL.get()
      );
      this.polishedToxite(ESBlocks.POLISHED_TOXITE.get());
      this.slabBlock(
         (SlabBlock)ESBlocks.POLISHED_TOXITE_SLAB.get(), this.blockTexture(ESBlocks.POLISHED_TOXITE.get()), this.blockTexture(ESBlocks.POLISHED_TOXITE.get())
      );
      this.stairsBlock((StairBlock)ESBlocks.POLISHED_TOXITE_STAIRS.get(), this.blockTexture(ESBlocks.POLISHED_TOXITE.get()));
      this.wallBlock((WallBlock)ESBlocks.POLISHED_TOXITE_WALL.get(), this.blockTexture(ESBlocks.POLISHED_TOXITE.get()));
      this.simpleBlock(ESBlocks.CHISELED_TOXITE.get());
      this.redstoneOre((Block)ESBlocks.GRIMSTONE_REDSTONE_ORE.get());
      this.redstoneOre((Block)ESBlocks.VOIDSTONE_REDSTONE_ORE.get());
      this.simpleBlock(ESBlocks.ETERNAL_ICE_REDSTONE_ORE.get());
      this.simpleBlock(ESBlocks.HAZE_ICE_REDSTONE_ORE.get());
      this.tintedCubeAll((Block)ESBlocks.WHITE_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.ORANGE_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.MAGENTA_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.LIGHT_BLUE_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.YELLOW_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.LIME_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.PINK_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.GRAY_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.LIGHT_GRAY_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.CYAN_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.PURPLE_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.BLUE_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.BROWN_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.GREEN_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.RED_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCubeAll((Block)ESBlocks.BLACK_YETI_FUR.get(), EternalStarlight.id("block/yeti_fur"), SOLID);
      this.tintedCarpet((Block)ESBlocks.WHITE_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.ORANGE_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.MAGENTA_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.YELLOW_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.LIME_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.PINK_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.GRAY_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.CYAN_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.PURPLE_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.BLUE_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.BROWN_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.GREEN_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.RED_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.tintedCarpet((Block)ESBlocks.BLACK_YETI_FUR_CARPET.get(), EternalStarlight.id("block/yeti_fur"));
      this.particleOnly((Block)ESBlocks.TANGLED_SKULL.get(), this.blockTexture(Blocks.SOUL_SAND));
      this.particleOnly((Block)ESBlocks.TANGLED_WALL_SKULL.get(), this.blockTexture(Blocks.SOUL_SAND));
      this.stoneSet(
         ESBlocks.TOOTH_OF_HUNGER_TILES.get(),
         (SlabBlock)ESBlocks.TOOTH_OF_HUNGER_TILE_SLAB.get(),
         (StairBlock)ESBlocks.TOOTH_OF_HUNGER_TILE_STAIRS.get(),
         (WallBlock)ESBlocks.TOOTH_OF_HUNGER_TILE_WALL.get()
      );
      this.simpleBlock(ESBlocks.CHISELED_TOOTH_OF_HUNGER_TILES.get());
      this.directionalBlock(
         (Block)ESBlocks.CRYSTALBORN_CATALYST.get(),
         this.models()
            .cubeBottomTop(
               this.name((Block)ESBlocks.CRYSTALBORN_CATALYST.get()),
               EternalStarlight.id("block/machine_side"),
               EternalStarlight.id("block/machine_side"),
               this.blockTexture((Block)ESBlocks.CRYSTALBORN_CATALYST.get())
            )
      );
      this.simpleBlock(ESBlocks.CRYSTALLIZED_SAND.get());
      this.particleOnly((Block)ESBlocks.LOOT_CHEST.get(), this.blockTexture(ESBlocks.LUNAR_PLANKS.get()));
      this.spawner((Block)ESBlocks.THE_GATEKEEPER_SPAWNER.get());
      this.spawner((Block)ESBlocks.STARLIGHT_GOLEM_SPAWNER.get());
      this.spawner((Block)ESBlocks.PERMAFROST_SPAWNER.get());
      this.spawner((Block)ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get());
      this.particleOnly((Block)ESBlocks.SOLAR_EGG.get(), this.itemTextureFromBlock((Block)ESBlocks.SOLAR_EGG.get()));
      this.simpleBlock(ESBlocks.GOLEM_STEEL_BLOCK.get());
      this.simpleBlock(ESBlocks.WAXED_GOLEM_STEEL_BLOCK.get(), this.models().getExistingFile(this.blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get())));
      this.simpleBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get());
      this.slabBlock(
         (SlabBlock)ESBlocks.GOLEM_STEEL_SLAB.get(), this.blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get()), this.blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get())
      );
      this.slabBlock(
         (SlabBlock)ESBlocks.WAXED_GOLEM_STEEL_SLAB.get(),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_SLAB.get())),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_SLAB.get()).withSuffix("_top")),
         this.models().getExistingFile(this.blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get()))
      );
      this.slabBlock(
         (SlabBlock)ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(),
         this.blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get()),
         this.blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get())
      );
      this.stairsBlock((StairBlock)ESBlocks.GOLEM_STEEL_STAIRS.get(), this.blockTexture(ESBlocks.GOLEM_STEEL_BLOCK.get()));
      this.stairsBlock(
         (StairBlock)ESBlocks.WAXED_GOLEM_STEEL_STAIRS.get(),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_STAIRS.get())),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_STAIRS.get()).withSuffix("_inner")),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_STAIRS.get()).withSuffix("_outer"))
      );
      this.stairsBlock((StairBlock)ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get(), this.blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get()));
      this.simpleBlock(ESBlocks.GOLEM_STEEL_TILES.get());
      this.simpleBlock(ESBlocks.WAXED_GOLEM_STEEL_TILES.get(), this.models().getExistingFile(this.blockTexture(ESBlocks.GOLEM_STEEL_TILES.get())));
      this.simpleBlock(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get());
      this.slabBlock(
         (SlabBlock)ESBlocks.GOLEM_STEEL_TILE_SLAB.get(),
         this.blockTexture(ESBlocks.GOLEM_STEEL_TILES.get()),
         this.blockTexture(ESBlocks.GOLEM_STEEL_TILES.get())
      );
      this.slabBlock(
         (SlabBlock)ESBlocks.WAXED_GOLEM_STEEL_TILE_SLAB.get(),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_TILE_SLAB.get())),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_TILE_SLAB.get()).withSuffix("_top")),
         this.models().getExistingFile(this.blockTexture(ESBlocks.GOLEM_STEEL_TILES.get()))
      );
      this.slabBlock(
         (SlabBlock)ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(),
         this.blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get()),
         this.blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get())
      );
      this.stairsBlock((StairBlock)ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(), this.blockTexture(ESBlocks.GOLEM_STEEL_TILES.get()));
      this.stairsBlock(
         (StairBlock)ESBlocks.WAXED_GOLEM_STEEL_TILE_STAIRS.get(),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_TILE_STAIRS.get())),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_TILE_STAIRS.get()).withSuffix("_inner")),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_TILE_STAIRS.get()).withSuffix("_outer"))
      );
      this.stairsBlock((StairBlock)ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(), this.blockTexture(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get()));
      this.simpleBlock(
         (Block)ESBlocks.GOLEM_STEEL_GRATE.get(),
         ((BlockModelBuilder)this.models()
               .cubeAll(this.name((Block)ESBlocks.GOLEM_STEEL_GRATE.get()), this.blockTexture((Block)ESBlocks.GOLEM_STEEL_GRATE.get())))
            .renderType(CUTOUT)
      );
      this.simpleBlock((Block)ESBlocks.WAXED_GOLEM_STEEL_GRATE.get(), this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_GRATE.get())));
      this.simpleBlock(
         (Block)ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get(),
         ((BlockModelBuilder)this.models()
               .cubeAll(this.name((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get()), this.blockTexture((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get())))
            .renderType(CUTOUT)
      );
      this.axisBlock((RotatedPillarBlock)ESBlocks.GOLEM_STEEL_PILLAR.get());
      this.axisBlock(
         (RotatedPillarBlock)ESBlocks.WAXED_GOLEM_STEEL_PILLAR.get(),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_PILLAR.get())),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_PILLAR.get()).withSuffix("_horizontal"))
      );
      this.axisBlock((RotatedPillarBlock)ESBlocks.OXIDIZED_GOLEM_STEEL_PILLAR.get());
      this.simpleBlock(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get());
      this.simpleBlock(
         ESBlocks.WAXED_CHISELED_GOLEM_STEEL_BLOCK.get(), this.models().getExistingFile(this.blockTexture(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get()))
      );
      this.simpleBlock(ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get());
      this.jetBlock((Block)ESBlocks.GOLEM_STEEL_JET.get());
      this.directionalBlock(
         (Block)ESBlocks.WAXED_GOLEM_STEEL_JET.get(), this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_JET.get()))
      );
      this.jetBlock((Block)ESBlocks.OXIDIZED_GOLEM_STEEL_JET.get());
      this.directionalOnOffBlock(
         (Block)ESBlocks.GOLEM_STEEL_CRATE.get(),
         CrateBlock.OPEN,
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_CRATE.get()).withSuffix("_open")),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.GOLEM_STEEL_CRATE.get()))
      );
      this.directionalOnOffBlock(
         (Block)ESBlocks.ENERGY_TRANSMITTER.get(),
         EnergyTransmitterBlock.POWERED,
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.ENERGY_TRANSMITTER.get()).withSuffix("_on")),
         this.models().getExistingFile(this.blockTexture((Block)ESBlocks.ENERGY_TRANSMITTER.get())),
         EnergyTransmitterBlock.OFFSET_TRANSFORMATION,
         EnergyTransmitterBlock.POWER,
         EnergyTransmitterBlock.DIRECT_POWER
      );
      this.accumulator((Block)ESBlocks.ACCUMULATOR.get());
      this.particleOnly((Block)ESBlocks.MECHANICAL_SPAWNER.get(), this.blockTexture(Blocks.SPAWNER));
      this.particleOnly((Block)ESBlocks.ALLOY_FURNACE.get(), this.itemTextureFromBlock((Block)ESBlocks.ALLOY_FURNACE.get()));
      this.particleOnly((Block)ESBlocks.WAXED_ALLOY_FURNACE.get(), this.itemTextureFromBlock((Block)ESBlocks.ALLOY_FURNACE.get()));
      this.particleOnly((Block)ESBlocks.OXIDIZED_ALLOY_FURNACE.get(), this.itemTextureFromBlock((Block)ESBlocks.OXIDIZED_ALLOY_FURNACE.get()));
      this.onOffBlock(ESBlocks.ENERGY_BLOCK.get());
      this.shadegrieve(ESBlocks.SHADEGRIEVE.get());
      this.shadegrieve(ESBlocks.BLOOMING_SHADEGRIEVE.get());
      this.particleOnly((Block)ESBlocks.LUNAR_VINE.get(), this.itemTextureFromBlock((Block)ESBlocks.LUNAR_VINE.get()));
      this.simpleBlock(ESBlocks.LUNAR_MOSAIC.get());
      this.slabBlock(
         (SlabBlock)ESBlocks.LUNAR_MOSAIC_SLAB.get(), this.blockTexture(ESBlocks.LUNAR_MOSAIC.get()), this.blockTexture(ESBlocks.LUNAR_MOSAIC.get())
      );
      this.stairsBlock((StairBlock)ESBlocks.LUNAR_MOSAIC_STAIRS.get(), this.blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
      this.fenceBlock((FenceBlock)ESBlocks.LUNAR_MOSAIC_FENCE.get(), this.blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
      this.fenceGateBlock((FenceGateBlock)ESBlocks.LUNAR_MOSAIC_FENCE_GATE.get(), this.blockTexture(ESBlocks.LUNAR_MOSAIC.get()));
      this.carpet((Block)ESBlocks.LUNAR_MAT.get(), this.blockTexture((Block)ESBlocks.LUNAR_MAT.get()));
      this.tintedCubeAll((Block)ESBlocks.DUSK_GLASS.get(), this.blockTexture((Block)ESBlocks.DUSK_GLASS.get()), TRANSLUCENT);
      this.simpleBlock((Block)ESBlocks.DUSK_LIGHT.get());
      this.simpleBlock((Block)ESBlocks.REINFORCED_DUSK_LIGHT.get());
      this.duskEmitter(
         (Block)ESBlocks.DUSK_EMITTER.get(),
         this.name((Block)ESBlocks.DUSK_EMITTER.get()),
         this.blockTexture((Block)ESBlocks.DUSK_EMITTER.get()),
         this.name((Block)ESBlocks.DUSK_EMITTER.get()) + "_off",
         this.blockTexture((Block)ESBlocks.DUSK_EMITTER.get()).withSuffix("_off")
      );
      this.simpleBlock((Block)ESBlocks.DUSK_LOCKBOX.get());
      this.onOffBlock(
         (Block)ESBlocks.FLARE_SPAWNER.get(),
         FlareSpawnerBlock.LIT,
         ((BlockModelBuilder)this.models().cubeAll(this.name((Block)ESBlocks.FLARE_SPAWNER.get()), this.blockTexture((Block)ESBlocks.FLARE_SPAWNER.get())))
            .renderType(CUTOUT),
         ((BlockModelBuilder)this.models()
               .cubeAll(this.name((Block)ESBlocks.FLARE_SPAWNER.get()) + "_off", this.blockTexture((Block)ESBlocks.FLARE_SPAWNER.get()).withSuffix("_off")))
            .renderType(CUTOUT)
      );
      this.simpleBlock((Block)ESBlocks.ECLIPSE_CORE.get());
      this.stoneSet(
         ESBlocks.DOOMEDEN_TILES.get(),
         (SlabBlock)ESBlocks.DOOMEDEN_TILE_SLAB.get(),
         (StairBlock)ESBlocks.DOOMEDEN_TILE_STAIRS.get(),
         (WallBlock)ESBlocks.DOOMEDEN_TILE_WALL.get()
      );
      this.simpleBlock(ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
      this.simpleBlock(ESBlocks.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
      this.doomedenTorch((Block)ESBlocks.DOOMED_TORCH.get(), (Block)ESBlocks.WALL_DOOMED_TORCH.get());
      this.doomedenRedstoneTorch((Block)ESBlocks.DOOMED_REDSTONE_TORCH.get(), (Block)ESBlocks.WALL_DOOMED_REDSTONE_TORCH.get());
      this.stoneSet(
         ESBlocks.DOOMEDEN_BRICKS.get(),
         (SlabBlock)ESBlocks.DOOMEDEN_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.DOOMEDEN_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.DOOMEDEN_BRICK_WALL.get()
      );
      this.stoneSet(
         ESBlocks.POLISHED_DOOMEDEN_BRICKS.get(),
         (SlabBlock)ESBlocks.POLISHED_DOOMEDEN_BRICK_SLAB.get(),
         (StairBlock)ESBlocks.POLISHED_DOOMEDEN_BRICK_STAIRS.get(),
         (WallBlock)ESBlocks.POLISHED_DOOMEDEN_BRICK_WALL.get()
      );
      this.onOffBlock((Block)ESBlocks.DOOMEDEN_LIGHT.get());
      this.doomedenKeyhole(ESBlocks.DOOMEDEN_KEYHOLE.get(), ESBlocks.REDSTONE_DOOMEDEN_KEYHOLE.get());
      this.stellarRack((Block)ESBlocks.STELLAR_RACK.get());
      this.horizontalBlock(
         (Block)ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get(),
         this.blockTexture(ESBlocks.GRIMSTONE_BRICKS.get()),
         this.blockTexture((Block)ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get()),
         this.blockTexture(ESBlocks.POLISHED_GRIMSTONE.get())
      );
      this.portal((Block)ESBlocks.STARLIGHT_PORTAL.get());
   }

   private void simpleExisting(Block block) {
      ModelFile modelFile = this.models().getExistingFile(this.key(block));
      this.simpleBlock(block, modelFile);
   }

   private void woodSet(
      RotatedPillarBlock log,
      RotatedPillarBlock wood,
      Block planks,
      RotatedPillarBlock strippedLog,
      RotatedPillarBlock strippedWood,
      DoorBlock door,
      boolean cutoutDoor,
      TrapDoorBlock trapdoor,
      boolean cutoutTrapdoor,
      PressurePlateBlock pressurePlate,
      ButtonBlock button,
      FenceBlock fence,
      FenceGateBlock fenceGate,
      SlabBlock slab,
      StairBlock stairs,
      Block sign,
      Block wallSign,
      Block hangingSign,
      Block wallHangingSign
   ) {
      this.logBlock(log);
      this.axisBlock(wood, this.blockTexture(log), this.blockTexture(log));
      this.simpleBlock(planks);
      this.logBlock(strippedLog);
      this.axisBlock(strippedWood, this.blockTexture(strippedLog), this.blockTexture(strippedLog));
      if (cutoutDoor) {
         this.doorBlockWithRenderType(door, this.blockTexture(door).withSuffix("_bottom"), this.blockTexture(door).withSuffix("_top"), CUTOUT);
      } else {
         this.doorBlock(door, this.blockTexture(door).withSuffix("_bottom"), this.blockTexture(door).withSuffix("_top"));
      }

      if (cutoutTrapdoor) {
         this.trapdoorBlockWithRenderType(trapdoor, this.blockTexture(trapdoor), true, CUTOUT);
      } else {
         this.trapdoorBlock(trapdoor, this.blockTexture(trapdoor), true);
      }

      this.pressurePlateBlock(pressurePlate, this.blockTexture(planks));
      this.buttonBlock(button, this.blockTexture(planks));
      this.fenceBlock(fence, this.blockTexture(planks));
      this.fenceGateBlock(fenceGate, this.blockTexture(planks));
      this.slabBlock(slab, this.blockTexture(planks), this.blockTexture(planks));
      this.stairsBlock(stairs, this.blockTexture(planks));
      this.simpleSign(sign, wallSign, this.blockTexture(planks));
      this.simpleSign(hangingSign, wallHangingSign, this.blockTexture(planks));
   }

   private void stoneSet(Block stone, SlabBlock slab, StairBlock stairs, WallBlock wall) {
      this.simpleBlock(stone);
      this.slabBlock(slab, this.blockTexture(stone), this.blockTexture(stone));
      this.stairsBlock(stairs, this.blockTexture(stone));
      this.wallBlock(wall, this.blockTexture(stone));
   }

   private void stellarRack(Block block) {
      ModelFile modelFile = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(this.name(block), ResourceLocation.withDefaultNamespace("block/block")))
                     .texture("particle", this.blockTexture(block).withSuffix("_bottom")))
                  .texture("bottom", this.blockTexture(block).withSuffix("_bottom")))
               .texture("top", this.blockTexture(block).withSuffix("_top")))
            .texture("side", this.blockTexture(block).withSuffix("_side")))
         .element()
         .from(0.0F, 0.0F, 0.0F)
         .to(16.0F, 12.0F, 16.0F)
         .face(Direction.DOWN)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#bottom")
         .cullface(Direction.DOWN)
         .end()
         .face(Direction.UP)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#top")
         .end()
         .face(Direction.NORTH)
         .uvs(0.0F, 4.0F, 16.0F, 16.0F)
         .texture("#side")
         .cullface(Direction.NORTH)
         .end()
         .face(Direction.SOUTH)
         .uvs(0.0F, 4.0F, 16.0F, 16.0F)
         .texture("#side")
         .cullface(Direction.SOUTH)
         .end()
         .face(Direction.WEST)
         .uvs(0.0F, 4.0F, 16.0F, 16.0F)
         .texture("#side")
         .cullface(Direction.WEST)
         .end()
         .face(Direction.EAST)
         .uvs(0.0F, 4.0F, 16.0F, 16.0F)
         .texture("#side")
         .cullface(Direction.EAST)
         .end()
         .end();
      this.simpleBlock(block, modelFile);
   }

   private void portal(Block block) {
      ModelFile modelEw = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder(this.name(block) + "_ew"))
                  .texture("particle", this.blockTexture(block)))
               .texture("portal", this.blockTexture(block)))
            .renderType(TRANSLUCENT))
         .element()
         .from(6.0F, 0.0F, 0.0F)
         .to(10.0F, 16.0F, 16.0F)
         .face(Direction.EAST)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#portal")
         .end()
         .face(Direction.WEST)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#portal")
         .end()
         .end();
      ModelFile modelNs = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models().getBuilder(this.name(block) + "_ns"))
                  .texture("particle", this.blockTexture(block)))
               .texture("portal", this.blockTexture(block)))
            .renderType(TRANSLUCENT))
         .element()
         .from(0.0F, 0.0F, 6.0F)
         .to(16.0F, 16.0F, 10.0F)
         .face(Direction.NORTH)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#portal")
         .end()
         .face(Direction.SOUTH)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#portal")
         .end()
         .end();
      this.getVariantBuilder(block)
         .forAllStatesExcept(
            state -> ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Axis.X ? modelNs : modelEw).build(),
            new Property[]{ESPortalBlock.CENTER}
         );
   }

   private void crinoa(Block block) {
      this.getVariantBuilder(block)
         .forAllStates(
            state -> {
               int stage = Math.max((Integer)state.getValue(BlockStateProperties.AGE_7) - 1, 0);
               return ConfiguredModel.builder()
                  .modelFile(
                     ((BlockModelBuilder)this.models().crop(this.name(block) + "_stage" + stage, this.blockTexture(block).withSuffix("_stage" + stage)))
                        .renderType(CUTOUT)
                  )
                  .build();
            }
         );
   }

   private void pungencyFruit(Block block) {
      ModelFile stage0 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage0", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage0")))
         .renderType(CUTOUT);
      ModelFile stage1 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage1", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage1")))
         .renderType(CUTOUT);
      ModelFile stage2 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage2", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage2")))
         .renderType(CUTOUT);
      ModelFile stage3 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage3", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage3")))
         .renderType(CUTOUT);
      ((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)this.getVariantBuilder(
                                 block
                              )
                              .partialState()
                              .with(BlockStateProperties.AGE_7, 0)
                              .modelForState()
                              .modelFile(stage0)
                              .addModel())
                           .partialState()
                           .with(BlockStateProperties.AGE_7, 1)
                           .modelForState()
                           .modelFile(stage0)
                           .addModel())
                        .partialState()
                        .with(BlockStateProperties.AGE_7, 2)
                        .modelForState()
                        .modelFile(stage1)
                        .addModel())
                     .partialState()
                     .with(BlockStateProperties.AGE_7, 3)
                     .modelForState()
                     .modelFile(stage1)
                     .addModel())
                  .partialState()
                  .with(BlockStateProperties.AGE_7, 4)
                  .modelForState()
                  .modelFile(stage2)
                  .addModel())
               .partialState()
               .with(BlockStateProperties.AGE_7, 5)
               .modelForState()
               .modelFile(stage2)
               .addModel())
            .partialState()
            .with(BlockStateProperties.AGE_7, 6)
            .modelForState()
            .modelFile(stage2)
            .addModel())
         .partialState()
         .with(BlockStateProperties.AGE_7, 7)
         .modelForState()
         .modelFile(stage3)
         .addModel();
   }

   private void nocturnalMilletTop(Block block) {
      ModelFile stage0 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage0", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage0")))
         .renderType(CUTOUT);
      ModelFile stage1 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage1", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage1")))
         .renderType(CUTOUT);
      ModelFile stage2 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage2", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage2")))
         .renderType(CUTOUT);
      ModelFile stageWithered = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_withered", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_withered")))
         .renderType(CUTOUT);
      ModelFile stage0Forgotten = ((BlockModelBuilder)this.models()
            .singleTexture(
               this.name(block) + "_stage0_forgotten",
               EternalStarlight.id("block/cross_crop"),
               "cross",
               this.blockTexture(block).withSuffix("_stage0_forgotten")
            ))
         .renderType(CUTOUT);
      ModelFile stage1Forgotten = ((BlockModelBuilder)this.models()
            .singleTexture(
               this.name(block) + "_stage1_forgotten",
               EternalStarlight.id("block/cross_crop"),
               "cross",
               this.blockTexture(block).withSuffix("_stage1_forgotten")
            ))
         .renderType(CUTOUT);
      ModelFile stage2Forgotten = ((BlockModelBuilder)this.models()
            .singleTexture(
               this.name(block) + "_stage2_forgotten",
               EternalStarlight.id("block/cross_crop"),
               "cross",
               this.blockTexture(block).withSuffix("_stage2_forgotten")
            ))
         .renderType(CUTOUT);
      this.getVariantBuilder(block).forAllStates(state -> {
         int age = (Integer)state.getValue(BlockStateProperties.AGE_2);
         boolean forgotten = (Boolean)state.getValue(NocturnalMilletTopBlock.FORGOTTEN);
         boolean withered = (Boolean)state.getValue(NocturnalMilletTopBlock.WITHERED);
         ModelFile model;
         if (withered) {
            model = forgotten ? stage2Forgotten : stageWithered;
         } else if (forgotten) {
            model = switch (age) {
               case 0 -> stage0Forgotten;
               case 1 -> stage1Forgotten;
               default -> stage2Forgotten;
            };
         } else {
            model = switch (age) {
               case 0 -> stage0;
               case 1 -> stage1;
               default -> stage2;
            };
         }

         return ConfiguredModel.builder().modelFile(model).build();
      });
   }

   private void nocturnalMilletBottom(Block block) {
      ModelFile stage0 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage0", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage0")))
         .renderType(CUTOUT);
      ModelFile stage1 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage1", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage1")))
         .renderType(CUTOUT);
      ModelFile stage2 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage2", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage2")))
         .renderType(CUTOUT);
      ModelFile stage3 = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_stage3", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_stage3")))
         .renderType(CUTOUT);
      ModelFile stageWithered = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block) + "_withered", EternalStarlight.id("block/cross_crop"), "cross", this.blockTexture(block).withSuffix("_withered")))
         .renderType(CUTOUT);
      ModelFile stage0Forgotten = ((BlockModelBuilder)this.models()
            .singleTexture(
               this.name(block) + "_stage0_forgotten",
               EternalStarlight.id("block/cross_crop"),
               "cross",
               this.blockTexture(block).withSuffix("_stage0_forgotten")
            ))
         .renderType(CUTOUT);
      ModelFile stage1Forgotten = ((BlockModelBuilder)this.models()
            .singleTexture(
               this.name(block) + "_stage1_forgotten",
               EternalStarlight.id("block/cross_crop"),
               "cross",
               this.blockTexture(block).withSuffix("_stage1_forgotten")
            ))
         .renderType(CUTOUT);
      ModelFile stage2Forgotten = ((BlockModelBuilder)this.models()
            .singleTexture(
               this.name(block) + "_stage2_forgotten",
               EternalStarlight.id("block/cross_crop"),
               "cross",
               this.blockTexture(block).withSuffix("_stage2_forgotten")
            ))
         .renderType(CUTOUT);
      ModelFile stage3Forgotten = ((BlockModelBuilder)this.models()
            .singleTexture(
               this.name(block) + "_stage3_forgotten",
               EternalStarlight.id("block/cross_crop"),
               "cross",
               this.blockTexture(block).withSuffix("_stage3_forgotten")
            ))
         .renderType(CUTOUT);
      IntUnaryOperator ageToIndex = age -> {
         if (age <= 1) {
            return 0;
         } else if (age <= 3) {
            return 1;
         } else {
            return age <= 6 ? 2 : 3;
         }
      };
      this.getVariantBuilder(block).forAllStates(state -> {
         int age = (Integer)state.getValue(BlockStateProperties.AGE_7);
         boolean forgotten = (Boolean)state.getValue(NocturnalMilletTopBlock.FORGOTTEN);
         boolean withered = (Boolean)state.getValue(NocturnalMilletTopBlock.WITHERED);
         ModelFile model;
         if (withered) {
            model = forgotten ? stage3Forgotten : stageWithered;
         } else {
            int idx = ageToIndex.applyAsInt(age);
            if (forgotten) {
               model = switch (idx) {
                  case 0 -> stage0Forgotten;
                  case 1 -> stage1Forgotten;
                  case 2 -> stage2Forgotten;
                  default -> stage3Forgotten;
               };
            } else {
               model = switch (idx) {
                  case 0 -> stage0;
                  case 1 -> stage1;
                  case 2 -> stage2;
                  default -> stage3;
               };
            }
         }

         return ConfiguredModel.builder().modelFile(model).build();
      });
   }

   private void dryingRack(Block block) {
      ModelFile modelFile = this.models().getExistingFile(EternalStarlight.id("drying_rack"));
      ModelFile modelCampfire = this.models().getExistingFile(EternalStarlight.id("drying_rack_campfire"));
      this.getVariantBuilder(block)
         .forAllStatesExcept(
            state -> ConfiguredModel.builder()
               .modelFile(state.getValue(DryingRackBlock.CAMPFIRE) ? modelCampfire : modelFile)
               .rotationY((int)((Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING)).toYRot())
               .build(),
            new Property[]{BlockStateProperties.LIT}
         );
   }

   private void starfireBirdNest(Block block) {
      ModelFile modelFile = this.models().getExistingFile(EternalStarlight.id("starfire_bird_nest"));
      ModelFile modelEggs1 = this.models().getExistingFile(EternalStarlight.id("starfire_bird_nest_eggs1"));
      ModelFile modelEggs2 = this.models().getExistingFile(EternalStarlight.id("starfire_bird_nest_eggs2"));
      ModelFile modelEggs3 = this.models().getExistingFile(EternalStarlight.id("starfire_bird_nest_eggs3"));
      this.getVariantBuilder(block).forAllStates(state -> {
         return ConfiguredModel.builder().modelFile(switch (state.getValue(StarfireBirdNestBlock.EGGS)) {
            case 1 -> modelEggs1;
            case 2 -> modelEggs2;
            case 3 -> modelEggs3;
            default -> modelFile;
         }).rotationY(((int)((Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING)).toYRot() + 180) % 360).build();
      });
   }

   private void starfireBirdAviary(Block block, ResourceLocation type) {
      this.starfireBirdAviary(block, type, "log");
   }

   private void starfireBirdAviary(Block block, ResourceLocation type, String logSuffix) {
      ModelFile modelFile = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .withExistingParent(this.name(block), EternalStarlight.id("template_starfire_bird_aviary")))
                  .texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
               .texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
            .texture("trapdoor", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_trapdoor")))
         .texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
      ModelFile modelOpen = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(this.name(block) + "_open", EternalStarlight.id("template_starfire_bird_aviary_open")))
               .texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
            .texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
         .texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
      ModelFile modelEggs1 = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .withExistingParent(this.name(block) + "_eggs1", EternalStarlight.id("template_starfire_bird_aviary_eggs1")))
                  .texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
               .texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
            .texture("trapdoor", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_trapdoor")))
         .texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
      ModelFile modelEggs1Open = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(this.name(block) + "_eggs1_open", EternalStarlight.id("template_starfire_bird_aviary_eggs1_open")))
               .texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
            .texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
         .texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
      ModelFile modelEggs2 = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .withExistingParent(this.name(block) + "_eggs2", EternalStarlight.id("template_starfire_bird_aviary_eggs2")))
                  .texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
               .texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
            .texture("trapdoor", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_trapdoor")))
         .texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
      ModelFile modelEggs2Open = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(this.name(block) + "_eggs2_open", EternalStarlight.id("template_starfire_bird_aviary_eggs2_open")))
               .texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
            .texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
         .texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
      ModelFile modelEggs3 = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                     .withExistingParent(this.name(block) + "_eggs3", EternalStarlight.id("template_starfire_bird_aviary_eggs3")))
                  .texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
               .texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
            .texture("trapdoor", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_trapdoor")))
         .texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
      ModelFile modelEggs3Open = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(this.name(block) + "_eggs3_open", EternalStarlight.id("template_starfire_bird_aviary_eggs3_open")))
               .texture("particle", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
            .texture("planks", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/" + type.getPath() + "_planks")))
         .texture("stripped_log", ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "block/stripped_" + type.getPath() + "_" + logSuffix));
      this.getVariantBuilder(block).forAllStates(state -> {
         return ConfiguredModel.builder().modelFile(switch (state.getValue(StarfireBirdNestBlock.EGGS)) {
            case 1 -> state.getValue(StarfireBirdAviaryBlock.OPEN) ? modelEggs1Open : modelEggs1;
            case 2 -> state.getValue(StarfireBirdAviaryBlock.OPEN) ? modelEggs2Open : modelEggs2;
            case 3 -> state.getValue(StarfireBirdAviaryBlock.OPEN) ? modelEggs3Open : modelEggs3;
            default -> state.getValue(StarfireBirdAviaryBlock.OPEN) ? modelOpen : modelFile;
         }).rotationY(((int)((Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING)).toYRot() + 180) % 360).build();
      });
   }

   private void rawAmaramberBlock(Block block) {
      ModelFile modelTop = this.models()
         .cubeBottomTop(
            this.name(block) + "top", this.blockTexture(block).withSuffix("_side"), this.blockTexture(block), this.blockTexture(block).withSuffix("_top")
         );
      ModelFile modelNormal = this.models().cubeAll(this.name(block), this.blockTexture(block));
      ((VariantBlockStateBuilder)this.getVariantBuilder(block).partialState().with(RawAmaramberBlock.TOP, true).modelForState().modelFile(modelTop).addModel())
         .partialState()
         .with(RawAmaramberBlock.TOP, false)
         .modelForState()
         .modelFile(modelNormal)
         .addModel();
   }

   private void thioquartzBlock(Block block) {
      ModelFile modelNormal = this.models().cubeAll(this.name(block), this.blockTexture(block));
      ModelFile modelEmbedded = this.models().cubeAll(this.name(block) + "_seed", this.blockTexture(block).withSuffix("_seed"));
      ((VariantBlockStateBuilder)this.getVariantBuilder(block)
            .partialState()
            .with(ThioquartzBlock.SEED, false)
            .modelForState()
            .modelFile(modelNormal)
            .addModel())
         .partialState()
         .with(ThioquartzBlock.SEED, true)
         .modelForState()
         .modelFile(modelEmbedded)
         .addModel();
   }

   private void polishedToxite(Block block) {
      this.getVariantBuilder(block)
         .forAllStates(
            state -> ConfiguredModel.builder()
               .modelFile(
                  state.getValue(PolishedToxiteBlock.PART) == PolishedToxiteBlock.Part.FULL
                     ? this.models().cubeAll(this.name(block), this.blockTexture(block))
                     : (
                        state.getValue(PolishedToxiteBlock.PART) == PolishedToxiteBlock.Part.MIDDLE
                           ? this.models().cubeAll(this.name(block) + "_middle", this.blockTexture(block).withSuffix("_middle"))
                           : (
                              state.getValue(PolishedToxiteBlock.PART) == PolishedToxiteBlock.Part.UPPER
                                 ? this.models()
                                    .cubeBottomTop(
                                       this.name(block) + "_upper",
                                       this.blockTexture(block).withSuffix("_upper"),
                                       this.blockTexture(block).withSuffix("_middle"),
                                       this.blockTexture(block)
                                    )
                                 : this.models()
                                    .cubeBottomTop(
                                       this.name(block) + "_lower",
                                       this.blockTexture(block).withSuffix("_lower"),
                                       this.blockTexture(block),
                                       this.blockTexture(block).withSuffix("_middle")
                                    )
                           )
                     )
               )
               .build()
         );
   }

   private void orbflora(Block block) {
      ModelFile modelAge0 = this.models().getExistingFile(EternalStarlight.id("orbflora_age_0"));
      ModelFile modelAge1 = this.models().getExistingFile(EternalStarlight.id("orbflora_age_1"));
      ModelFile modelAge2 = this.models().getExistingFile(EternalStarlight.id("orbflora"));
      ((VariantBlockStateBuilder)((VariantBlockStateBuilder)this.getVariantBuilder(block)
               .partialState()
               .with(OrbfloraBlock.ORBFLORA_AGE, 0)
               .modelForState()
               .modelFile(modelAge0)
               .addModel())
            .partialState()
            .with(OrbfloraBlock.ORBFLORA_AGE, 1)
            .modelForState()
            .modelFile(modelAge1)
            .addModel())
         .partialState()
         .with(OrbfloraBlock.ORBFLORA_AGE, 2)
         .modelForState()
         .modelFile(modelAge2)
         .addModel();
   }

   private void lunarisCactus(Block block) {
      ModelFile modelNormal = this.models().getExistingFile(EternalStarlight.id("lunaris_cactus"));
      ModelFile modelFruit = this.models().getExistingFile(EternalStarlight.id("lunaris_cactus_fruit"));
      ((VariantBlockStateBuilder)this.getVariantBuilder(block)
            .partialState()
            .with(LunarisCactusBlock.FRUIT, false)
            .modelForState()
            .modelFile(modelNormal)
            .addModel())
         .partialState()
         .with(LunarisCactusBlock.FRUIT, true)
         .modelForState()
         .modelFile(modelFruit)
         .addModel();
   }

   private void torreyaVines(Block block) {
      ModelFile modelNormal = ((BlockModelBuilder)this.models().cross(this.name(block), this.blockTexture(block))).renderType(CUTOUT);
      ModelFile modelTop = ((BlockModelBuilder)this.models().cross(this.name(block) + "_top", this.blockTexture(block).withSuffix("_top"))).renderType(CUTOUT);
      this.onOffBlock(block, TorreyaVinesPlantBlock.TOP, modelTop, modelNormal);
   }

   private void vinesWithFruit(Block block) {
      ModelFile modelNormal = ((BlockModelBuilder)this.models().cross(this.name(block), this.blockTexture(block))).renderType(CUTOUT);
      ModelFile modelLit = ((BlockModelBuilder)this.models().cross(this.name(block) + "_lit", this.blockTexture(block).withSuffix("_lit"))).renderType(CUTOUT);
      this.onOffBlock(block, BlockStateProperties.BERRIES, modelLit, modelNormal);
   }

   private void lumenstem(Block block) {
      ModelFile modelNormal = ((BlockModelBuilder)this.models().cross(this.name(block), this.blockTexture(block))).renderType(CUTOUT);
      ModelFile modelBottom = ((BlockModelBuilder)this.models().cross(this.name(block) + "_bottom", this.blockTexture(block).withSuffix("_bottom")))
         .renderType(CUTOUT);
      this.onOffBlock(block, LumenstemBlock.BOTTOM, modelBottom, modelNormal);
   }

   private void lumenstemPlant(Block block) {
      this.getVariantBuilder(block)
         .forAllStates(
            state -> {
               LumenstemPlantBlock.LumenstemState lumenstemState = (LumenstemPlantBlock.LumenstemState)state.getValue(LumenstemPlantBlock.LUMENSTEM_STATE);
               ModelFile modelFile = ((BlockModelBuilder)this.models()
                     .cross(
                        this.name(block) + "_" + lumenstemState.getSerializedName(),
                        this.blockTexture(block).withSuffix("_" + lumenstemState.getSerializedName())
                     ))
                  .renderType(CUTOUT);
               return ConfiguredModel.builder().modelFile(modelFile).build();
            }
         );
   }

   private void mangroveRoots(Block block) {
      this.cubeColumn(block, this.blockTexture(block).withSuffix("_top"), this.blockTexture(block).withSuffix("_side"), CUTOUT_MIPPED);
   }

   private void muddyMangroveRoots(RotatedPillarBlock block) {
      this.axisBlock(
         block,
         ((BlockModelBuilder)this.models()
               .cubeColumn(this.name(block), this.blockTexture(block).withSuffix("_side"), this.blockTexture(block).withSuffix("_top")))
            .renderType(CUTOUT_MIPPED),
         ((BlockModelBuilder)this.models()
               .cubeColumnHorizontal(
                  this.name(block) + "_horizontal", this.blockTexture(block).withSuffix("_side"), this.blockTexture(block).withSuffix("_top")
               ))
            .renderType(CUTOUT_MIPPED)
      );
   }

   private void doomedenKeyhole(Block block, Block redstone) {
      ResourceLocation tiles = this.blockTexture(ESBlocks.DOOMEDEN_TILES.get());
      ModelFile modelOn = ((BlockModelBuilder)this.models()
            .cube(
               this.name(block) + "_lit",
               tiles,
               tiles,
               this.blockTexture(block).withSuffix("_on_front"),
               this.blockTexture(block).withSuffix("_on_front"),
               this.blockTexture(block).withSuffix("_on_side"),
               this.blockTexture(block).withSuffix("_on_side")
            ))
         .texture("particle", tiles);
      ModelFile modelOff = ((BlockModelBuilder)this.models()
            .cube(
               this.name(block),
               tiles,
               tiles,
               this.blockTexture(block).withSuffix("_off_front"),
               this.blockTexture(block).withSuffix("_off_front"),
               this.blockTexture(block).withSuffix("_off_side"),
               this.blockTexture(block).withSuffix("_off_side")
            ))
         .texture("particle", tiles);
      this.getVariantBuilder(block)
         .forAllStates(
            state -> state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Axis.X
               ? ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? modelOn : modelOff).rotationY(90).build()
               : ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? modelOn : modelOff).build()
         );
      ModelFile modelOnRedstone = ((BlockModelBuilder)this.models()
            .cube(
               this.name(redstone) + "_lit",
               tiles,
               tiles,
               this.blockTexture(redstone).withSuffix("_on"),
               this.blockTexture(redstone).withSuffix("_on"),
               this.blockTexture(block).withSuffix("_on_side"),
               this.blockTexture(block).withSuffix("_on_side")
            ))
         .texture("particle", tiles);
      ModelFile modelOffRedstone = ((BlockModelBuilder)this.models()
            .cube(
               this.name(redstone),
               tiles,
               tiles,
               this.blockTexture(redstone).withSuffix("_off"),
               this.blockTexture(redstone).withSuffix("_off"),
               this.blockTexture(block).withSuffix("_off_side"),
               this.blockTexture(block).withSuffix("_off_side")
            ))
         .texture("particle", tiles);
      this.getVariantBuilder(redstone)
         .forAllStates(
            state -> state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Axis.X
               ? ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? modelOnRedstone : modelOffRedstone).rotationY(90).build()
               : ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? modelOnRedstone : modelOffRedstone).build()
         );
   }

   private void doomedenTorch(Block normal, Block wall) {
      ModelFile modelNormal = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(normal), EternalStarlight.id("block/template_doomeden_torch"), "torch", this.blockTexture(normal)))
         .renderType(CUTOUT);
      ModelFile modelWall = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(wall), EternalStarlight.id("block/template_doomeden_torch_wall"), "torch", this.blockTexture(normal)))
         .renderType(CUTOUT);
      this.simpleBlock(normal, modelNormal);
      this.horizontalBlock(wall, modelWall, 90);
   }

   private void doomedenRedstoneTorch(Block normal, Block wall) {
      ModelFile modelNormal = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(normal) + "_lit", EternalStarlight.id("block/template_doomeden_torch"), "torch", this.blockTexture(normal)))
         .renderType(CUTOUT);
      ModelFile modelNormalOff = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(normal), EternalStarlight.id("block/template_doomeden_torch"), "torch", this.blockTexture(normal).withSuffix("_off")))
         .renderType(CUTOUT);
      ModelFile modelWall = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(wall) + "_lit", EternalStarlight.id("block/template_doomeden_torch_wall"), "torch", this.blockTexture(normal)))
         .renderType(CUTOUT);
      ModelFile modelWallOff = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(wall), EternalStarlight.id("block/template_doomeden_torch_wall"), "torch", this.blockTexture(normal).withSuffix("_off")))
         .renderType(CUTOUT);
      this.onOffBlock(normal, BlockStateProperties.LIT, modelNormal, modelNormalOff);
      this.horizontalBlock(wall, state -> state.getValue(BlockStateProperties.LIT) ? modelWall : modelWallOff, 90);
   }

   private void directionalBud(Block block) {
      ModelFile modelFile = ((BlockModelBuilder)this.models().cross(this.name(block), this.blockTexture(block))).renderType(CUTOUT);
      this.getVariantBuilder(block).forAllStates(state -> {
         Direction direction = (Direction)state.getValue(BlockStateProperties.FACING);
         int rotX = direction == Direction.DOWN ? 180 : (direction == Direction.UP ? 0 : 90);
         return ConfiguredModel.builder().modelFile(modelFile).rotationY(((int)direction.toYRot() + 180) % 360).rotationX(rotX).build();
      });
   }

   private void directionalDenseCrossBud(Block block) {
      ModelFile modelFile = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(block), EternalStarlight.id("block/dense_cross"), "cross", this.blockTexture(block)))
         .renderType(CUTOUT);
      this.getVariantBuilder(block).forAllStates(state -> {
         Direction direction = (Direction)state.getValue(BlockStateProperties.FACING);
         int rotX = direction == Direction.DOWN ? 180 : (direction == Direction.UP ? 0 : 90);
         return ConfiguredModel.builder().modelFile(modelFile).rotationY(((int)direction.toYRot() + 180) % 360).rotationX(rotX).build();
      });
   }

   private void directionalCubeBottomTop(Block block) {
      ModelFile modelFile = this.models()
         .cubeBottomTop(this.name(block), this.blockTexture(block), this.blockTexture(block).withSuffix("_bottom"), this.blockTexture(block).withSuffix("_top"));
      this.getVariantBuilder(block).forAllStates(state -> {
         Direction direction = (Direction)state.getValue(BlockStateProperties.FACING);
         int rotX = direction == Direction.DOWN ? 180 : (direction == Direction.UP ? 0 : 90);
         return ConfiguredModel.builder().modelFile(modelFile).rotationY(((int)direction.toYRot() + 180) % 360).rotationX(rotX).build();
      });
   }

   private void spawner(Block block) {
      this.simpleBlock(block, ((BlockModelBuilder)this.models().cubeAll(this.name(block), this.blockTexture(Blocks.SPAWNER))).renderType(CUTOUT));
   }

   private void geyser(Block geyser, Block stone) {
      ModelFile modelFile = this.models().cubeBottomTop(this.name(geyser), this.blockTexture(stone), this.blockTexture(stone), this.blockTexture(geyser));
      this.simpleBlock(geyser, modelFile);
   }

   private void jetBlock(Block block) {
      ModelFile modelFile = this.models()
         .cubeBottomTop(
            this.name(block),
            this.blockTexture(block).withSuffix("_side"),
            this.blockTexture(block).withSuffix("_side"),
            this.blockTexture(block).withSuffix("_top")
         );
      this.directionalBlock(block, modelFile);
   }

   private void cubeBottomTop(Block block) {
      ModelFile modelFile = this.models()
         .cubeBottomTop(
            this.name(block),
            this.blockTexture(block).withSuffix("_side"),
            this.blockTexture(block).withSuffix("_bottom"),
            this.blockTexture(block).withSuffix("_top")
         );
      this.simpleBlock(block, modelFile);
   }

   private void waterlilyWithFlower(Block lily) {
      ModelFile model = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .getBuilder(this.name(lily)))
                     .ao(false))
                  .texture("particle", this.blockTexture(lily)))
               .texture("texture", this.blockTexture(lily)))
            .renderType(TRANSLUCENT))
         .element()
         .from(0.0F, 0.25F, 0.0F)
         .to(16.0F, 0.25F, 16.0F)
         .face(Direction.DOWN)
         .uvs(0.0F, 16.0F, 16.0F, 0.0F)
         .texture("#texture")
         .tintindex(0)
         .end()
         .face(Direction.UP)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#texture")
         .tintindex(0)
         .end()
         .end();
      ((PartBuilder)((PartBuilder)this.getMultipartBuilder(lily)
               .part()
               .modelFile(
                  ((BlockModelBuilder)this.models().cross(this.name(lily) + "_flower", this.blockTexture(lily).withSuffix("_flower"))).renderType(CUTOUT)
               )
               .addModel())
            .condition(WaterlilyWithFlowerBlock.LIT, new Boolean[]{true})
            .end()
            .part()
            .modelFile(model)
            .nextModel()
            .rotationY(270)
            .modelFile(model)
            .nextModel()
            .rotationY(180)
            .modelFile(model)
            .nextModel()
            .rotationY(90)
            .modelFile(model)
            .addModel())
         .end();
   }

   private void waterlily(Block lily) {
      ModelFile model = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .getBuilder(this.name(lily)))
                     .ao(false))
                  .texture("particle", this.blockTexture(lily)))
               .texture("texture", this.blockTexture(lily)))
            .renderType(TRANSLUCENT))
         .element()
         .from(0.0F, 0.25F, 0.0F)
         .to(16.0F, 0.25F, 16.0F)
         .face(Direction.DOWN)
         .uvs(0.0F, 16.0F, 16.0F, 0.0F)
         .texture("#texture")
         .tintindex(0)
         .end()
         .face(Direction.UP)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#texture")
         .tintindex(0)
         .end()
         .end();
      this.getVariantBuilder(lily)
         .forAllStates(
            state -> ConfiguredModel.builder()
               .modelFile(model)
               .nextModel()
               .rotationY(270)
               .modelFile(model)
               .nextModel()
               .rotationY(180)
               .modelFile(model)
               .nextModel()
               .rotationY(90)
               .modelFile(model)
               .build()
         );
   }

   private void multifaceBlock(Block block) {
      ModelFile model = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .getBuilder(this.name(block)))
                     .ao(false))
                  .texture("particle", this.blockTexture(block)))
               .texture("texture", this.blockTexture(block)))
            .renderType(TRANSLUCENT))
         .element()
         .from(0.0F, 0.0F, 0.1F)
         .to(16.0F, 16.0F, 0.1F)
         .face(Direction.NORTH)
         .uvs(16.0F, 0.0F, 0.0F, 16.0F)
         .texture("#texture")
         .tintindex(0)
         .end()
         .face(Direction.SOUTH)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#texture")
         .tintindex(0)
         .end()
         .end();
      ((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)this.getMultipartBuilder(
                                                block
                                             )
                                             .part()
                                             .modelFile(model)
                                             .addModel())
                                          .condition(BlockStateProperties.NORTH, new Boolean[]{true})
                                          .end()
                                          .part()
                                          .modelFile(model)
                                          .addModel())
                                       .condition(BlockStateProperties.DOWN, new Boolean[]{false})
                                       .condition(BlockStateProperties.EAST, new Boolean[]{false})
                                       .condition(BlockStateProperties.NORTH, new Boolean[]{false})
                                       .condition(BlockStateProperties.SOUTH, new Boolean[]{false})
                                       .condition(BlockStateProperties.UP, new Boolean[]{false})
                                       .condition(BlockStateProperties.WEST, new Boolean[]{false})
                                       .end()
                                       .part()
                                       .modelFile(model)
                                       .uvLock(true)
                                       .rotationY(90)
                                       .addModel())
                                    .condition(BlockStateProperties.EAST, new Boolean[]{true})
                                    .end()
                                    .part()
                                    .modelFile(model)
                                    .uvLock(true)
                                    .rotationY(90)
                                    .addModel())
                                 .condition(BlockStateProperties.DOWN, new Boolean[]{false})
                                 .condition(BlockStateProperties.EAST, new Boolean[]{false})
                                 .condition(BlockStateProperties.NORTH, new Boolean[]{false})
                                 .condition(BlockStateProperties.SOUTH, new Boolean[]{false})
                                 .condition(BlockStateProperties.UP, new Boolean[]{false})
                                 .condition(BlockStateProperties.WEST, new Boolean[]{false})
                                 .end()
                                 .part()
                                 .modelFile(model)
                                 .uvLock(true)
                                 .rotationY(180)
                                 .addModel())
                              .condition(BlockStateProperties.SOUTH, new Boolean[]{true})
                              .end()
                              .part()
                              .modelFile(model)
                              .uvLock(true)
                              .rotationY(180)
                              .addModel())
                           .condition(BlockStateProperties.DOWN, new Boolean[]{false})
                           .condition(BlockStateProperties.EAST, new Boolean[]{false})
                           .condition(BlockStateProperties.NORTH, new Boolean[]{false})
                           .condition(BlockStateProperties.SOUTH, new Boolean[]{false})
                           .condition(BlockStateProperties.UP, new Boolean[]{false})
                           .condition(BlockStateProperties.WEST, new Boolean[]{false})
                           .end()
                           .part()
                           .modelFile(model)
                           .uvLock(true)
                           .rotationY(270)
                           .addModel())
                        .condition(BlockStateProperties.WEST, new Boolean[]{true})
                        .end()
                        .part()
                        .modelFile(model)
                        .uvLock(true)
                        .rotationY(270)
                        .addModel())
                     .condition(BlockStateProperties.DOWN, new Boolean[]{false})
                     .condition(BlockStateProperties.EAST, new Boolean[]{false})
                     .condition(BlockStateProperties.NORTH, new Boolean[]{false})
                     .condition(BlockStateProperties.SOUTH, new Boolean[]{false})
                     .condition(BlockStateProperties.UP, new Boolean[]{false})
                     .condition(BlockStateProperties.WEST, new Boolean[]{false})
                     .end()
                     .part()
                     .modelFile(model)
                     .uvLock(true)
                     .rotationX(270)
                     .addModel())
                  .condition(BlockStateProperties.UP, new Boolean[]{true})
                  .end()
                  .part()
                  .modelFile(model)
                  .uvLock(true)
                  .rotationX(270)
                  .addModel())
               .condition(BlockStateProperties.DOWN, new Boolean[]{false})
               .condition(BlockStateProperties.EAST, new Boolean[]{false})
               .condition(BlockStateProperties.NORTH, new Boolean[]{false})
               .condition(BlockStateProperties.SOUTH, new Boolean[]{false})
               .condition(BlockStateProperties.UP, new Boolean[]{false})
               .condition(BlockStateProperties.WEST, new Boolean[]{false})
               .end()
               .part()
               .modelFile(model)
               .uvLock(true)
               .rotationX(90)
               .addModel())
            .condition(BlockStateProperties.DOWN, new Boolean[]{true})
            .end()
            .part()
            .modelFile(model)
            .uvLock(true)
            .rotationX(90)
            .addModel())
         .condition(BlockStateProperties.DOWN, new Boolean[]{false})
         .condition(BlockStateProperties.EAST, new Boolean[]{false})
         .condition(BlockStateProperties.NORTH, new Boolean[]{false})
         .condition(BlockStateProperties.SOUTH, new Boolean[]{false})
         .condition(BlockStateProperties.UP, new Boolean[]{false})
         .condition(BlockStateProperties.WEST, new Boolean[]{false})
         .end();
   }

   private void mushroomLikeBlock(Block block) {
      this.mushroomLikeBlock(block, this.name(block), this.blockTexture(block), this.name(block) + "_inside", this.blockTexture(block).withSuffix("_inside"));
   }

   private void mushroomLikeBlock(Block block, String outerName, ResourceLocation outerTexture, String innerName, ResourceLocation innerTexture) {
      ModelFile modelOutside = this.models().singleTexture(outerName, ResourceLocation.withDefaultNamespace("block/template_single_face"), outerTexture);
      ModelFile modelInside = this.models().singleTexture(innerName, ResourceLocation.withDefaultNamespace("block/template_single_face"), innerTexture);
      ((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)this.getMultipartBuilder(
                                                block
                                             )
                                             .part()
                                             .modelFile(modelOutside)
                                             .addModel())
                                          .condition(BlockStateProperties.NORTH, new Boolean[]{true})
                                          .end()
                                          .part()
                                          .modelFile(modelOutside)
                                          .uvLock(true)
                                          .rotationY(90)
                                          .addModel())
                                       .condition(BlockStateProperties.EAST, new Boolean[]{true})
                                       .end()
                                       .part()
                                       .modelFile(modelOutside)
                                       .uvLock(true)
                                       .rotationY(180)
                                       .addModel())
                                    .condition(BlockStateProperties.SOUTH, new Boolean[]{true})
                                    .end()
                                    .part()
                                    .modelFile(modelOutside)
                                    .uvLock(true)
                                    .rotationY(270)
                                    .addModel())
                                 .condition(BlockStateProperties.WEST, new Boolean[]{true})
                                 .end()
                                 .part()
                                 .modelFile(modelOutside)
                                 .uvLock(true)
                                 .rotationX(270)
                                 .addModel())
                              .condition(BlockStateProperties.UP, new Boolean[]{true})
                              .end()
                              .part()
                              .modelFile(modelOutside)
                              .uvLock(true)
                              .rotationX(90)
                              .addModel())
                           .condition(BlockStateProperties.DOWN, new Boolean[]{true})
                           .end()
                           .part()
                           .modelFile(modelInside)
                           .addModel())
                        .condition(BlockStateProperties.NORTH, new Boolean[]{false})
                        .end()
                        .part()
                        .modelFile(modelInside)
                        .uvLock(false)
                        .rotationY(90)
                        .addModel())
                     .condition(BlockStateProperties.EAST, new Boolean[]{false})
                     .end()
                     .part()
                     .modelFile(modelInside)
                     .uvLock(false)
                     .rotationY(180)
                     .addModel())
                  .condition(BlockStateProperties.SOUTH, new Boolean[]{false})
                  .end()
                  .part()
                  .modelFile(modelInside)
                  .uvLock(false)
                  .rotationY(270)
                  .addModel())
               .condition(BlockStateProperties.WEST, new Boolean[]{false})
               .end()
               .part()
               .modelFile(modelInside)
               .uvLock(false)
               .rotationX(270)
               .addModel())
            .condition(BlockStateProperties.UP, new Boolean[]{false})
            .end()
            .part()
            .modelFile(modelInside)
            .uvLock(false)
            .rotationX(90)
            .addModel())
         .condition(BlockStateProperties.DOWN, new Boolean[]{false})
         .end();
   }

   private void duskEmitter(Block block, String outerName, ResourceLocation outerTexture, String innerName, ResourceLocation innerTexture) {
      ModelFile modelOutside = this.models().singleTexture(outerName, ResourceLocation.withDefaultNamespace("block/template_single_face"), outerTexture);
      ModelFile modelInside = this.models().singleTexture(innerName, ResourceLocation.withDefaultNamespace("block/template_single_face"), innerTexture);
      ((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)((PartBuilder)this.getMultipartBuilder(
                                                block
                                             )
                                             .part()
                                             .modelFile(modelOutside)
                                             .addModel())
                                          .condition(BlockStateProperties.NORTH, new Boolean[]{true})
                                          .end()
                                          .part()
                                          .modelFile(modelOutside)
                                          .uvLock(true)
                                          .rotationY(90)
                                          .addModel())
                                       .condition(BlockStateProperties.EAST, new Boolean[]{true})
                                       .end()
                                       .part()
                                       .modelFile(modelOutside)
                                       .uvLock(true)
                                       .rotationY(180)
                                       .addModel())
                                    .condition(BlockStateProperties.SOUTH, new Boolean[]{true})
                                    .end()
                                    .part()
                                    .modelFile(modelOutside)
                                    .uvLock(true)
                                    .rotationY(270)
                                    .addModel())
                                 .condition(BlockStateProperties.WEST, new Boolean[]{true})
                                 .end()
                                 .part()
                                 .modelFile(modelOutside)
                                 .uvLock(true)
                                 .rotationX(270)
                                 .rotationY(180)
                                 .addModel())
                              .condition(BlockStateProperties.UP, new Boolean[]{true})
                              .end()
                              .part()
                              .modelFile(modelOutside)
                              .uvLock(true)
                              .rotationX(90)
                              .rotationY(180)
                              .addModel())
                           .condition(BlockStateProperties.DOWN, new Boolean[]{true})
                           .end()
                           .part()
                           .modelFile(modelInside)
                           .addModel())
                        .condition(BlockStateProperties.NORTH, new Boolean[]{false})
                        .end()
                        .part()
                        .modelFile(modelInside)
                        .uvLock(false)
                        .rotationY(90)
                        .addModel())
                     .condition(BlockStateProperties.EAST, new Boolean[]{false})
                     .end()
                     .part()
                     .modelFile(modelInside)
                     .uvLock(false)
                     .rotationY(180)
                     .addModel())
                  .condition(BlockStateProperties.SOUTH, new Boolean[]{false})
                  .end()
                  .part()
                  .modelFile(modelInside)
                  .uvLock(false)
                  .rotationY(270)
                  .addModel())
               .condition(BlockStateProperties.WEST, new Boolean[]{false})
               .end()
               .part()
               .modelFile(modelInside)
               .uvLock(false)
               .rotationX(270)
               .rotationY(180)
               .addModel())
            .condition(BlockStateProperties.UP, new Boolean[]{false})
            .end()
            .part()
            .modelFile(modelInside)
            .uvLock(false)
            .rotationX(90)
            .rotationY(180)
            .addModel())
         .condition(BlockStateProperties.DOWN, new Boolean[]{false})
         .end();
   }

   private void torreyaCampfire(Block block) {
      ResourceLocation log = this.blockTexture(block).withSuffix("_log");
      ResourceLocation litLog = this.blockTexture(block).withSuffix("_log_lit");
      ResourceLocation fire = this.blockTexture(block).withSuffix("_fire");
      ResourceLocation starfire = this.blockTexture(block).withSuffix("_starfire");
      ModelFile modelNormal = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(this.name(block) + "_off", ResourceLocation.withDefaultNamespace("campfire_off")))
               .texture("log", log))
            .texture("particle", log))
         .renderType(CUTOUT);
      ModelFile modelLit = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(this.name(block), ResourceLocation.withDefaultNamespace("template_campfire")))
                     .texture("log", log))
                  .texture("particle", log))
               .texture("lit_log", litLog))
            .texture("fire", fire))
         .renderType(CUTOUT);
      ModelFile modelStarfire = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(this.name(block) + "_starfire", ResourceLocation.withDefaultNamespace("template_campfire")))
                     .texture("log", log))
                  .texture("particle", log))
               .texture("lit_log", litLog))
            .texture("fire", starfire))
         .renderType(CUTOUT);
      this.getVariantBuilder(block)
         .forAllStatesExcept(
            state -> ConfiguredModel.builder()
               .modelFile(state.getValue(BlockStateProperties.LIT) ? (state.getValue(TorreyaCampfireBlock.STARFIRE) ? modelStarfire : modelLit) : modelNormal)
               .rotationY((int)((Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING)).toYRot())
               .build(),
            new Property[]{BlockStateProperties.WATERLOGGED, BlockStateProperties.SIGNAL_FIRE}
         );
   }

   private void simpleGrassBlock(Block grassBlock, ResourceLocation dirt) {
      this.simpleGrassBlock(grassBlock, this.blockTexture(grassBlock).withSuffix("_side"), this.blockTexture(grassBlock).withSuffix("_top"), dirt);
   }

   private void simpleGrassBlock(Block grassBlock, ResourceLocation side, ResourceLocation top, ResourceLocation dirt) {
      ModelFile modelFile = this.models().cubeBottomTop(this.name(grassBlock), side, dirt, top);
      this.getVariantBuilder(grassBlock)
         .forAllStates(
            state -> ConfiguredModel.builder()
               .modelFile(modelFile)
               .nextModel()
               .rotationY(270)
               .modelFile(modelFile)
               .nextModel()
               .rotationY(180)
               .modelFile(modelFile)
               .nextModel()
               .rotationY(90)
               .modelFile(modelFile)
               .build()
         );
   }

   private void snowyDirtBlock(Block block, ResourceLocation dirt, ResourceLocation snowy) {
      ModelFile modelFile = this.models()
         .cubeBottomTop(this.name(block), this.blockTexture(block).withSuffix("_side"), dirt, this.blockTexture(block).withSuffix("_top"));
      ModelFile snowyModel = this.models().getExistingFile(snowy);
      this.getVariantBuilder(block)
         .forAllStates(
            state -> ConfiguredModel.builder()
               .modelFile(state.getValue(BlockStateProperties.SNOWY) ? snowyModel : modelFile)
               .nextModel()
               .rotationY(270)
               .modelFile(state.getValue(BlockStateProperties.SNOWY) ? snowyModel : modelFile)
               .nextModel()
               .rotationY(180)
               .modelFile(state.getValue(BlockStateProperties.SNOWY) ? snowyModel : modelFile)
               .nextModel()
               .rotationY(90)
               .modelFile(state.getValue(BlockStateProperties.SNOWY) ? snowyModel : modelFile)
               .build()
         );
   }

   private void grassBlock(Block grassBlock, ResourceLocation dirt) {
      ModelFile modelNormal = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                                 .withExistingParent(this.name(grassBlock), ResourceLocation.withDefaultNamespace("block/block")))
                              .renderType(CUTOUT_MIPPED))
                           .texture("particle", dirt))
                        .texture("bottom", dirt))
                     .texture("top", this.blockTexture(grassBlock).withSuffix("_top")))
                  .texture("side", this.blockTexture(grassBlock).withSuffix("_side")))
               .texture("overlay", this.blockTexture(grassBlock).withSuffix("_side_overlay")))
            .element()
            .from(0.0F, 0.0F, 0.0F)
            .to(16.0F, 16.0F, 16.0F)
            .allFaces(
               (dir, builder) -> {
                  ModelBuilder<BlockModelBuilder>.ElementBuilder.FaceBuilder faceBuilder = builder.uvs(0.0F, 0.0F, 16.0F, 16.0F)
                     .texture("#" + (dir == Direction.UP ? "top" : (dir == Direction.DOWN ? "bottom" : "side")))
                     .cullface(dir);
                  if (dir == Direction.UP) {
                     faceBuilder.tintindex(0).end();
                  } else {
                     faceBuilder.end();
                  }
               }
            )
            .end())
         .element()
         .from(0.0F, 0.0F, 0.0F)
         .to(16.0F, 16.0F, 16.0F)
         .face(Direction.NORTH)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#overlay")
         .cullface(Direction.NORTH)
         .tintindex(0)
         .end()
         .face(Direction.SOUTH)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#overlay")
         .cullface(Direction.SOUTH)
         .tintindex(0)
         .end()
         .face(Direction.WEST)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#overlay")
         .cullface(Direction.WEST)
         .tintindex(0)
         .end()
         .face(Direction.EAST)
         .uvs(0.0F, 0.0F, 16.0F, 16.0F)
         .texture("#overlay")
         .cullface(Direction.EAST)
         .tintindex(0)
         .end()
         .end();
      ModelFile modelSnow = this.models()
         .cubeBottomTop(
            this.name(grassBlock) + "_snow", this.blockTexture(grassBlock).withSuffix("_snow"), dirt, this.blockTexture(grassBlock).withSuffix("_top")
         );
      this.getVariantBuilder(grassBlock)
         .forAllStates(
            state -> state.getValue(BlockStateProperties.SNOWY)
               ? ConfiguredModel.builder().modelFile(modelSnow).build()
               : ConfiguredModel.builder()
                  .modelFile(modelNormal)
                  .nextModel()
                  .rotationY(270)
                  .modelFile(modelNormal)
                  .nextModel()
                  .rotationY(180)
                  .modelFile(modelNormal)
                  .nextModel()
                  .rotationY(90)
                  .modelFile(modelNormal)
                  .build()
         );
   }

   private void sand(Block sand) {
      ModelFile modelFile = this.models().cubeAll(this.name(sand), this.blockTexture(sand));
      this.getVariantBuilder(sand)
         .forAllStates(
            state -> ConfiguredModel.builder()
               .modelFile(modelFile)
               .nextModel()
               .rotationY(270)
               .modelFile(modelFile)
               .nextModel()
               .rotationY(180)
               .modelFile(modelFile)
               .nextModel()
               .rotationY(90)
               .modelFile(modelFile)
               .build()
         );
   }

   private void suspiciousBlock(Block block) {
      this.getVariantBuilder(block)
         .forAllStates(
            state -> ConfiguredModel.builder()
               .modelFile(
                  this.models()
                     .cubeAll(
                        this.name(block) + "_" + state.getValue(BlockStateProperties.DUSTED),
                        this.blockTexture(block).withSuffix("_" + state.getValue(BlockStateProperties.DUSTED))
                     )
               )
               .build()
         );
   }

   private void randomlyMirroredAndRotatedBlock(Block stone) {
      ModelFile normal = this.models().cubeAll(this.name(stone), this.blockTexture(stone));
      ModelFile mirrored = this.models()
         .singleTexture(this.name(stone) + "_mirrored", ResourceLocation.withDefaultNamespace("block/cube_mirrored_all"), "all", this.blockTexture(stone));
      this.getVariantBuilder(stone)
         .forAllStates(
            state -> ConfiguredModel.builder()
               .modelFile(normal)
               .nextModel()
               .modelFile(mirrored)
               .nextModel()
               .rotationY(180)
               .modelFile(normal)
               .nextModel()
               .rotationY(180)
               .modelFile(mirrored)
               .build()
         );
   }

   private void randomlyMirroredBlock(Block stone) {
      ModelFile normal = this.models().cubeAll(this.name(stone), this.blockTexture(stone));
      ModelFile mirrored = this.models()
         .singleTexture(this.name(stone) + "_mirrored", ResourceLocation.withDefaultNamespace("block/cube_mirrored_all"), "all", this.blockTexture(stone));
      this.getVariantBuilder(stone).forAllStates(state -> ConfiguredModel.builder().modelFile(normal).nextModel().modelFile(mirrored).build());
   }

   private void farmland(Block farmland, Block dirt) {
      ModelFile normal = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(farmland), "template_farmland"))
            .texture("dirt", this.blockTexture(dirt)))
         .texture("top", this.blockTexture(farmland));
      ModelFile moist = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(farmland) + "_moist", "template_farmland"))
            .texture("dirt", this.blockTexture(dirt)))
         .texture("top", this.blockTexture(farmland).withSuffix("_moist"));
      this.getVariantBuilder(farmland)
         .forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.MOISTURE) < 7 ? normal : moist).build());
   }

   private void dirtPath(Block dirtPath, Block dirt) {
      this.getVariantBuilder(dirtPath)
         .forAllStates(
            state -> {
               ModelFile modelFile = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                                 .withExistingParent(this.name(dirtPath), ResourceLocation.withDefaultNamespace("block/block")))
                              .texture("top", this.blockTexture(dirtPath).withSuffix("_top")))
                           .texture("side", this.blockTexture(dirtPath).withSuffix("_side")))
                        .texture("bottom", this.blockTexture(dirt)))
                     .texture("particle", this.blockTexture(dirt)))
                  .element()
                  .from(0.0F, 0.0F, 0.0F)
                  .to(16.0F, 15.0F, 16.0F)
                  .face(Direction.DOWN)
                  .uvs(0.0F, 0.0F, 16.0F, 16.0F)
                  .texture("#bottom")
                  .cullface(Direction.DOWN)
                  .end()
                  .face(Direction.UP)
                  .uvs(0.0F, 0.0F, 16.0F, 16.0F)
                  .texture("#top")
                  .end()
                  .face(Direction.NORTH)
                  .uvs(0.0F, 1.0F, 16.0F, 16.0F)
                  .texture("#side")
                  .cullface(Direction.NORTH)
                  .end()
                  .face(Direction.SOUTH)
                  .uvs(0.0F, 1.0F, 16.0F, 16.0F)
                  .texture("#side")
                  .cullface(Direction.SOUTH)
                  .end()
                  .face(Direction.WEST)
                  .uvs(0.0F, 1.0F, 16.0F, 16.0F)
                  .texture("#side")
                  .cullface(Direction.WEST)
                  .end()
                  .face(Direction.EAST)
                  .uvs(0.0F, 1.0F, 16.0F, 16.0F)
                  .texture("#side")
                  .cullface(Direction.EAST)
                  .end()
                  .end();
               return ConfiguredModel.builder()
                  .modelFile(modelFile)
                  .nextModel()
                  .rotationY(270)
                  .modelFile(modelFile)
                  .nextModel()
                  .rotationY(180)
                  .modelFile(modelFile)
                  .nextModel()
                  .rotationY(90)
                  .modelFile(modelFile)
                  .build();
            }
         );
   }

   private void lantern(Block lantern) {
      ModelFile normal = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(lantern), ResourceLocation.withDefaultNamespace("template_lantern"), "lantern", this.blockTexture(lantern)))
         .renderType(CUTOUT);
      ModelFile hanging = ((BlockModelBuilder)this.models()
            .singleTexture(
               this.name(lantern) + "_hanging", ResourceLocation.withDefaultNamespace("template_hanging_lantern"), "lantern", this.blockTexture(lantern)
            ))
         .renderType(CUTOUT);
      ((VariantBlockStateBuilder)this.getVariantBuilder(lantern)
            .partialState()
            .with(BlockStateProperties.HANGING, false)
            .modelForState()
            .modelFile(normal)
            .addModel())
         .partialState()
         .with(BlockStateProperties.HANGING, true)
         .modelForState()
         .modelFile(hanging)
         .addModel();
   }

   private void candle(Block candle) {
      ModelFile one = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(candle), "template_candle"))
            .texture("all", this.blockTexture(candle)))
         .texture("particle", this.blockTexture(candle));
      ModelFile oneLit = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(candle) + "_lit", "template_candle"))
            .texture("all", this.blockTexture(candle).withSuffix("_lit")))
         .texture("particle", this.blockTexture(candle).withSuffix("_lit"));
      ModelFile two = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(candle) + "_two", "template_two_candles"))
            .texture("all", this.blockTexture(candle)))
         .texture("particle", this.blockTexture(candle));
      ModelFile twoLit = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(candle) + "_two_lit", "template_two_candles"))
            .texture("all", this.blockTexture(candle).withSuffix("_lit")))
         .texture("particle", this.blockTexture(candle).withSuffix("_lit"));
      ModelFile three = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(candle) + "_three", "template_three_candles"))
            .texture("all", this.blockTexture(candle)))
         .texture("particle", this.blockTexture(candle));
      ModelFile threeLit = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(candle) + "_three_lit", "template_three_candles"))
            .texture("all", this.blockTexture(candle).withSuffix("_lit")))
         .texture("particle", this.blockTexture(candle).withSuffix("_lit"));
      ModelFile four = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(candle) + "_four", "template_four_candles"))
            .texture("all", this.blockTexture(candle)))
         .texture("particle", this.blockTexture(candle));
      ModelFile fourLit = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(candle) + "_four_lit", "template_four_candles"))
            .texture("all", this.blockTexture(candle).withSuffix("_lit")))
         .texture("particle", this.blockTexture(candle).withSuffix("_lit"));
      ((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)this.getVariantBuilder(
                                 candle
                              )
                              .partialState()
                              .with(BlockStateProperties.CANDLES, 1)
                              .with(BlockStateProperties.LIT, false)
                              .modelForState()
                              .modelFile(one)
                              .addModel())
                           .partialState()
                           .with(BlockStateProperties.CANDLES, 1)
                           .with(BlockStateProperties.LIT, true)
                           .modelForState()
                           .modelFile(oneLit)
                           .addModel())
                        .partialState()
                        .with(BlockStateProperties.CANDLES, 2)
                        .with(BlockStateProperties.LIT, false)
                        .modelForState()
                        .modelFile(two)
                        .addModel())
                     .partialState()
                     .with(BlockStateProperties.CANDLES, 2)
                     .with(BlockStateProperties.LIT, true)
                     .modelForState()
                     .modelFile(twoLit)
                     .addModel())
                  .partialState()
                  .with(BlockStateProperties.CANDLES, 3)
                  .with(BlockStateProperties.LIT, false)
                  .modelForState()
                  .modelFile(three)
                  .addModel())
               .partialState()
               .with(BlockStateProperties.CANDLES, 3)
               .with(BlockStateProperties.LIT, true)
               .modelForState()
               .modelFile(threeLit)
               .addModel())
            .partialState()
            .with(BlockStateProperties.CANDLES, 4)
            .with(BlockStateProperties.LIT, false)
            .modelForState()
            .modelFile(four)
            .addModel())
         .partialState()
         .with(BlockStateProperties.CANDLES, 4)
         .with(BlockStateProperties.LIT, true)
         .modelForState()
         .modelFile(fourLit)
         .addModel();
   }

   private void candleCake(Block candleCake, Block cake, Block candle) {
      ModelFile normal = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(this.name(candleCake), "template_cake_with_candle"))
                     .texture("bottom", this.blockTexture(cake).withSuffix("_bottom")))
                  .texture("side", this.blockTexture(cake).withSuffix("_side")))
               .texture("top", this.blockTexture(cake).withSuffix("_top")))
            .texture("candle", this.blockTexture(candle)))
         .texture("particle", this.blockTexture(cake).withSuffix("_side"));
      ModelFile lit = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                        .withExistingParent(this.name(candleCake) + "_lit", "template_cake_with_candle"))
                     .texture("bottom", this.blockTexture(cake).withSuffix("_bottom")))
                  .texture("side", this.blockTexture(cake).withSuffix("_side")))
               .texture("top", this.blockTexture(cake).withSuffix("_top")))
            .texture("candle", this.blockTexture(candle).withSuffix("_lit")))
         .texture("particle", this.blockTexture(cake).withSuffix("_side"));
      ((VariantBlockStateBuilder)this.getVariantBuilder(candleCake)
            .partialState()
            .with(BlockStateProperties.LIT, false)
            .modelForState()
            .modelFile(normal)
            .addModel())
         .partialState()
         .with(BlockStateProperties.LIT, true)
         .modelForState()
         .modelFile(lit)
         .addModel();
   }

   private void sandstoneAndCut(Block sandstone, Block cut) {
      ModelFile modelFile = this.models()
         .cubeBottomTop(
            this.name(sandstone),
            this.blockTexture(sandstone),
            this.blockTexture(sandstone).withSuffix("_bottom"),
            this.blockTexture(sandstone).withSuffix("_top")
         );
      ModelFile modelCut = this.models().cubeColumn(this.name(cut), this.blockTexture(cut), this.blockTexture(sandstone).withSuffix("_top"));
      this.simpleBlock(sandstone, modelFile);
      this.simpleBlock(cut, modelCut);
   }

   private void coralFan(Block fan) {
      ModelFile modelFile = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(fan), ResourceLocation.withDefaultNamespace("block/coral_fan"), "fan", this.blockTexture(fan)))
         .renderType(CUTOUT);
      this.simpleBlock(fan, modelFile);
   }

   private void coralWallFan(Block wall, Block fan) {
      ModelFile modelFile = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(wall), ResourceLocation.withDefaultNamespace("block/coral_wall_fan"), "fan", this.blockTexture(fan)))
         .renderType(CUTOUT);
      this.getVariantBuilder(wall)
         .forAllStates(
            state -> ConfiguredModel.builder()
               .modelFile(modelFile)
               .rotationY(((int)((Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING)).toYRot() + 180) % 360)
               .build()
         );
   }

   private void torch(Block normal, Block wall) {
      ModelFile modelNormal = ((BlockModelBuilder)this.models().torch(this.name(normal), this.blockTexture(normal))).renderType(CUTOUT);
      ModelFile modelWall = ((BlockModelBuilder)this.models().torchWall(this.name(wall), this.blockTexture(normal))).renderType(CUTOUT);
      this.simpleBlock(normal, modelNormal);
      this.horizontalBlock(wall, modelWall, 90);
   }

   private void redstoneTorch(Block normal, Block wall) {
      ModelFile modelNormal = ((BlockModelBuilder)this.models().torch(this.name(normal) + "_lit", this.blockTexture(normal))).renderType(CUTOUT);
      ModelFile modelNormalOff = ((BlockModelBuilder)this.models().torch(this.name(normal), this.blockTexture(normal).withSuffix("_off"))).renderType(CUTOUT);
      ModelFile modelWall = ((BlockModelBuilder)this.models().torchWall(this.name(wall) + "_lit", this.blockTexture(normal))).renderType(CUTOUT);
      ModelFile modelWallOff = ((BlockModelBuilder)this.models().torchWall(this.name(wall), this.blockTexture(normal).withSuffix("_off"))).renderType(CUTOUT);
      this.onOffBlock(normal, BlockStateProperties.LIT, modelNormal, modelNormalOff);
      this.horizontalBlock(wall, state -> state.getValue(BlockStateProperties.LIT) ? modelWall : modelWallOff, 90);
   }

   private void onOffBlock(Block block) {
      ModelFile on = this.models().cubeAll(this.name(block) + "_lit", this.blockTexture(block).withSuffix("_lit"));
      ModelFile off = this.models().cubeAll(this.name(block), this.blockTexture(block));
      this.onOffBlock(block, BlockStateProperties.LIT, on, off);
   }

   private void redstoneOre(Block block) {
      ModelFile on = this.models().cubeAll(this.name(block), this.blockTexture(block));
      ModelFile off = this.models().cubeAll(this.name(block) + "_off", this.blockTexture(block).withSuffix("_off"));
      this.onOffBlock(block, BlockStateProperties.LIT, on, off);
   }

   private void snowyLeaves(Block leaves) {
      ModelFile modelNormal = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(leaves), ResourceLocation.withDefaultNamespace("block/leaves"), "all", this.blockTexture(leaves)))
         .renderType(CUTOUT_MIPPED);
      ModelFile modelSnowy = ((BlockModelBuilder)this.models()
            .cubeBottomTop(this.name(leaves) + "_snowy", this.blockTexture(leaves).withSuffix("_snowy"), this.blockTexture(leaves), this.blockTexture(leaves)))
         .renderType(CUTOUT_MIPPED);
      this.onOffBlock(leaves, BlockStateProperties.SNOWY, modelSnowy, modelNormal);
   }

   private void shadegrieve(Block block) {
      ModelFile modelNormal = this.cubeAll(block);
      ModelFile modelTop = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(this.name(block) + "_top", EternalStarlight.id("template_up_spreading_plant")))
               .renderType(CUTOUT))
            .texture("cube", this.blockTexture(block)))
         .texture("upper", this.blockTexture(block).withSuffix("_upper"));
      this.onOffBlock(block, ShadegrieveBlock.TOP, modelTop, modelNormal);
   }

   private void caveMossFull(Block block) {
      ModelFile modelNormal = this.models().singleTexture(this.name(block), EternalStarlight.id("block/tinted_cube_all"), "all", this.blockTexture(block));
      ModelFile modelBottom = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                  .withExistingParent(this.name(block) + "_bottom", EternalStarlight.id("template_down_spreading_plant")))
               .renderType(CUTOUT))
            .texture("cube", this.blockTexture(block)))
         .texture("lower", this.blockTexture(block).withSuffix("_lower"));
      this.onOffBlock(block, CaveMossFullBlock.BOTTOM, modelBottom, modelNormal);
   }

   private void onOffBlock(Block block, BooleanProperty property, ModelFile on, ModelFile off) {
      ((VariantBlockStateBuilder)this.getVariantBuilder(block).partialState().with(property, false).modelForState().modelFile(off).addModel())
         .partialState()
         .with(property, true)
         .modelForState()
         .modelFile(on)
         .addModel();
   }

   private void directionalOnOffBlock(Block block, BooleanProperty property, ModelFile on, ModelFile off, Property<?>... ignored) {
      this.getVariantBuilder(block)
         .forAllStatesExcept(
            state -> {
               Direction direction = (Direction)state.getValue(BlockStateProperties.FACING);
               int rotX = direction == Direction.DOWN ? 180 : (direction == Direction.UP ? 0 : 90);
               return ConfiguredModel.builder()
                  .modelFile(state.getValue(property) ? on : off)
                  .rotationX(rotX)
                  .rotationY(((int)direction.toYRot() + 180) % 360)
                  .build();
            },
            ignored
         );
   }

   private void accumulator(Block block) {
      ModelFile modelFile = ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                                 .withExistingParent(this.name(block), ResourceLocation.withDefaultNamespace("block/block")))
                              .renderType(CUTOUT_MIPPED))
                           .texture("particle", this.blockTexture(block)))
                        .texture("top", this.blockTexture(block).withSuffix("_top")))
                     .texture("top_overlay", this.blockTexture(block).withSuffix("_top_overlay")))
                  .texture("side", this.blockTexture(block)))
               .texture("side_overlay", this.blockTexture(block).withSuffix("_overlay")))
            .element()
            .from(0.0F, 0.0F, 0.0F)
            .to(16.0F, 16.0F, 16.0F)
            .allFaces((dir, builder) -> builder.uvs(0.0F, 0.0F, 16.0F, 16.0F).texture("#" + (dir == Direction.UP ? "top" : "side")).cullface(dir).end())
            .end())
         .element()
         .from(0.0F, 0.0F, 0.0F)
         .to(16.0F, 16.0F, 16.0F)
         .allFaces(
            (dir, builder) -> builder.uvs(0.0F, 0.0F, 16.0F, 16.0F)
               .texture("#" + (dir == Direction.UP ? "top_overlay" : "side_overlay"))
               .cullface(dir)
               .tintindex(0)
               .end()
         )
         .end();
      this.directionalBlock(block, modelFile);
   }

   private void simpleSign(Block normal, Block wall, ResourceLocation location) {
      this.particleOnly(normal, location);
      this.particleOnly(wall, location);
   }

   private void particleOnly(Block block) {
      this.particleOnly(block, this.blockTexture(block));
   }

   private void particleOnly(Block block, ResourceLocation location) {
      this.simpleBlock(block, ((BlockModelBuilder)this.models().getBuilder(this.name(block))).texture("particle", location));
   }

   private void leaves(Block leaves) {
      ModelFile modelFile = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(leaves), ResourceLocation.withDefaultNamespace("block/leaves"), "all", this.blockTexture(leaves)))
         .renderType(CUTOUT_MIPPED);
      this.simpleBlock(leaves, modelFile);
   }

   private void icicle(Block block) {
      this.getVariantBuilder(block)
         .forAllStates(
            state -> {
               IcicleBlock.IcicleThickness thickness = (IcicleBlock.IcicleThickness)state.getValue(IcicleBlock.THICKNESS);
               ModelFile modelFile = ((BlockModelBuilder)this.models()
                     .cross(this.name(block) + "_" + thickness.getSerializedName(), this.blockTexture(block).withSuffix("_" + thickness.getSerializedName())))
                  .renderType(CUTOUT);
               return ConfiguredModel.builder().modelFile(modelFile).rotationX(state.getValue(IcicleBlock.TIP_DIRECTION) == Direction.UP ? 0 : 180).build();
            }
         );
   }

   private void layered(Block layered, ResourceLocation texture) {
      this.getVariantBuilder(layered)
         .forAllStates(
            state -> {
               int height = (Integer)state.getValue(BlockStateProperties.LAYERS) * 2;
               ModelFile modelFile = height < 16
                  ? ((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)((BlockModelBuilder)this.models()
                                 .withExistingParent(this.name(layered) + "_height" + height, ResourceLocation.withDefaultNamespace("block/thin_block")))
                              .texture("particle", texture))
                           .texture("texture", texture))
                        .renderType(CUTOUT_MIPPED))
                     .element()
                     .from(0.0F, 0.0F, 0.0F)
                     .to(16.0F, height, 16.0F)
                     .face(Direction.DOWN)
                     .uvs(0.0F, 0.0F, 16.0F, 16.0F)
                     .texture("#texture")
                     .cullface(Direction.DOWN)
                     .end()
                     .face(Direction.UP)
                     .uvs(0.0F, 0.0F, 16.0F, 16.0F)
                     .texture("#texture")
                     .end()
                     .face(Direction.NORTH)
                     .uvs(0.0F, 16 - height, 16.0F, 16.0F)
                     .texture("#texture")
                     .cullface(Direction.NORTH)
                     .end()
                     .face(Direction.SOUTH)
                     .uvs(0.0F, 16 - height, 16.0F, 16.0F)
                     .texture("#texture")
                     .cullface(Direction.SOUTH)
                     .end()
                     .face(Direction.WEST)
                     .uvs(0.0F, 16 - height, 16.0F, 16.0F)
                     .texture("#texture")
                     .cullface(Direction.WEST)
                     .end()
                     .face(Direction.EAST)
                     .uvs(0.0F, 16 - height, 16.0F, 16.0F)
                     .texture("#texture")
                     .cullface(Direction.EAST)
                     .end()
                     .end()
                  : this.models().cubeAll(this.name(layered), texture);
               return ConfiguredModel.builder().modelFile(modelFile).build();
            }
         );
   }

   private void pottedPlant(Block potted, ResourceLocation location) {
      ModelFile modelFile = ((BlockModelBuilder)this.models()
            .singleTexture(this.name(potted), ResourceLocation.withDefaultNamespace("block/flower_pot_cross"), "plant", location))
         .renderType(CUTOUT);
      this.simpleBlock(potted, modelFile);
   }

   private void doublePlant(Block block) {
      ModelFile upper = ((BlockModelBuilder)this.models().cross(this.name(block) + "_top", this.blockTexture(block).withSuffix("_top"))).renderType(CUTOUT);
      ModelFile lower = ((BlockModelBuilder)this.models().cross(this.name(block) + "_bottom", this.blockTexture(block).withSuffix("_bottom")))
         .renderType(CUTOUT);
      ((VariantBlockStateBuilder)this.getVariantBuilder(block)
            .partialState()
            .with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER)
            .modelForState()
            .modelFile(upper)
            .addModel())
         .partialState()
         .with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
         .modelForState()
         .modelFile(lower)
         .addModel();
   }

   private void cubeColumn(Block block, ResourceLocation end, ResourceLocation side, ResourceLocation renderType) {
      ModelFile modelFile = ((BlockModelBuilder)this.models().cubeColumn(this.name(block), side, end)).renderType(renderType);
      this.simpleBlock(block, modelFile);
   }

   private void tintedCubeAll(Block block, ResourceLocation texture, ResourceLocation renderType) {
      this.simpleBlock(
         block,
         ((BlockModelBuilder)this.models().singleTexture(this.name(block), EternalStarlight.id("block/tinted_cube_all"), "all", texture))
            .renderType(renderType)
      );
   }

   private void cross(Block block) {
      this.cross(block, this.blockTexture(block), CUTOUT);
   }

   private void cross(Block block, ResourceLocation texture, ResourceLocation renderType) {
      ModelFile modelFile = ((BlockModelBuilder)this.models().cross(this.name(block), texture)).renderType(renderType);
      this.simpleBlock(block, modelFile);
   }

   private void tintedCross(Block block) {
      this.tintedCross(block, this.blockTexture(block), CUTOUT);
   }

   private void tintedCross(Block block, ResourceLocation texture, ResourceLocation renderType) {
      ModelFile modelFile = ((BlockModelBuilder)((BlockModelBuilder)this.models().withExistingParent(this.name(block), "tinted_cross"))
            .texture("cross", texture))
         .renderType(renderType);
      this.simpleBlock(block, modelFile);
   }

   private void tintedCarpet(Block block, ResourceLocation wool) {
      this.simpleBlock(block, this.models().singleTexture(this.name(block), EternalStarlight.id("block/tinted_carpet"), "wool", wool));
   }

   private void carpet(Block block, ResourceLocation wool) {
      ModelFile modelFile = this.models().carpet(this.name(block), wool);
      this.simpleBlock(block, modelFile);
   }

   public ResourceLocation itemTextureFromBlock(Block block) {
      ResourceLocation name = this.key(block);
      return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "item/" + name.getPath());
   }

   private ResourceLocation key(Block block) {
      return BuiltInRegistries.BLOCK.getKey(block);
   }

   private String name(Block block) {
      return this.key(block).getPath();
   }
}
