package twilightforest.init;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.TrappedChestBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;
import twilightforest.block.AbstractSkullCandleBlock;
import twilightforest.block.AntibuilderBlock;
import twilightforest.block.ArcticFurBlock;
import twilightforest.block.AuroraBrickBlock;
import twilightforest.block.AuroralizedGlassBlock;
import twilightforest.block.BanisterBlock;
import twilightforest.block.BerryBushBlock;
import twilightforest.block.BinaryRotatedBlock;
import twilightforest.block.BossSpawnerBlock;
import twilightforest.block.BrazierBlock;
import twilightforest.block.BuilderBlock;
import twilightforest.block.BurntThornsBlock;
import twilightforest.block.CandelabraBlock;
import twilightforest.block.CarminiteReactorBlock;
import twilightforest.block.CastleDoorBlock;
import twilightforest.block.ChiseledCanopyShelfBlock;
import twilightforest.block.CicadaBlock;
import twilightforest.block.CicadaJarBlock;
import twilightforest.block.CinderFurnaceBlock;
import twilightforest.block.ClimbableHollowLogBlock;
import twilightforest.block.CloudBlock;
import twilightforest.block.DarkLeavesBlock;
import twilightforest.block.DarkTowerBerryBushBlock;
import twilightforest.block.DirectionalRotatedPillarBlock;
import twilightforest.block.DryingRackBlock;
import twilightforest.block.EncasedFireJetBlock;
import twilightforest.block.EncasedSmokerBlock;
import twilightforest.block.EtchedNagastoneBlock;
import twilightforest.block.Experiment115Block;
import twilightforest.block.FallenLeavesBlock;
import twilightforest.block.FiddleheadBlock;
import twilightforest.block.FieryBlock;
import twilightforest.block.FireJetBlock;
import twilightforest.block.FireflyBlock;
import twilightforest.block.FireflyJarBlock;
import twilightforest.block.FireflySpawnerBlock;
import twilightforest.block.ForceFieldBlock;
import twilightforest.block.GhastTrapBlock;
import twilightforest.block.GiantBlock;
import twilightforest.block.GiantLeavesBlock;
import twilightforest.block.GrowingBeanstalkBlock;
import twilightforest.block.HardenedDarkLeavesBlock;
import twilightforest.block.HedgeBlock;
import twilightforest.block.HorizontalHollowLogBlock;
import twilightforest.block.HugeLilyPadBlock;
import twilightforest.block.HugeWaterLilyBlock;
import twilightforest.block.InfestedTowerwoodBlock;
import twilightforest.block.IronLadderBlock;
import twilightforest.block.JarBlock;
import twilightforest.block.KeepsakeCasketBlock;
import twilightforest.block.KnightmetalBlock;
import twilightforest.block.LiverootBlock;
import twilightforest.block.LockedVanishingBlock;
import twilightforest.block.MangroveSaplingBlock;
import twilightforest.block.MasonJarBlock;
import twilightforest.block.MayappleBlock;
import twilightforest.block.MazeSlimeBlock;
import twilightforest.block.MineLogCoreBlock;
import twilightforest.block.MiniatureStructureBlock;
import twilightforest.block.MoonwormBlock;
import twilightforest.block.MossPatchBlock;
import twilightforest.block.MushgloomBlock;
import twilightforest.block.NagastoneBlock;
import twilightforest.block.OminousCandleBlock;
import twilightforest.block.OminousFireBlock;
import twilightforest.block.OreBerryBlock;
import twilightforest.block.PatchBlock;
import twilightforest.block.ReactorDebrisBlock;
import twilightforest.block.ReappearingBlock;
import twilightforest.block.RedThreadBlock;
import twilightforest.block.RootStrandBlock;
import twilightforest.block.RopeBlock;
import twilightforest.block.SinisterSpawnerBlock;
import twilightforest.block.SkullCandleBlock;
import twilightforest.block.SkullChestBlock;
import twilightforest.block.SliderBlock;
import twilightforest.block.SortLogCoreBlock;
import twilightforest.block.SpecialFlowerPotBlock;
import twilightforest.block.SpecialStemLeavesBlock;
import twilightforest.block.SpiralBrickBlock;
import twilightforest.block.StrongholdShieldBlock;
import twilightforest.block.TFHorizontalBlock;
import twilightforest.block.TFPortalBlock;
import twilightforest.block.TFSmokerBlock;
import twilightforest.block.ThornRoseBlock;
import twilightforest.block.ThornsBlock;
import twilightforest.block.TimeLogCoreBlock;
import twilightforest.block.TorchberryPlantBlock;
import twilightforest.block.TransLogCoreBlock;
import twilightforest.block.TransformationLeavesBlock;
import twilightforest.block.TranslucentBuiltBlock;
import twilightforest.block.TrollRootBlock;
import twilightforest.block.TrollsteinnBlock;
import twilightforest.block.TrophyBlock;
import twilightforest.block.TrophyPedestalBlock;
import twilightforest.block.TrophyWallBlock;
import twilightforest.block.UberousSoilBlock;
import twilightforest.block.UncraftingTableBlock;
import twilightforest.block.UnripeTorchClusterBlock;
import twilightforest.block.VanishingBlock;
import twilightforest.block.VerticalHollowLogBlock;
import twilightforest.block.WallPillarBlock;
import twilightforest.block.WallSkullCandleBlock;
import twilightforest.block.WispyCloudBlock;
import twilightforest.block.WroughtIronFenceBlock;
import twilightforest.enums.BlockLoggingEnum;
import twilightforest.enums.BossVariant;
import twilightforest.enums.BrazierLight;
import twilightforest.enums.FireJetVariant;
import twilightforest.enums.TowerDeviceVariant;
import twilightforest.item.WroughtIronFenceItem;
import twilightforest.loot.TFLootTables;
import twilightforest.util.woods.TFWoodTypes;
import twilightforest.world.components.feature.trees.growers.TFTreeGrowers;

public class TFBlocks {
   public static final Blocks BLOCKS = DeferredRegister.createBlocks("twilightforest");
   public static final DeferredBlock<TFPortalBlock> TWILIGHT_PORTAL = BLOCKS.register(
      "twilight_portal",
      () -> new TFPortalBlock(
         Properties.of()
            .pushReaction(PushReaction.BLOCK)
            .strength(-1.0F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> 11)
            .noCollission()
            .noOcclusion()
            .noLootTable()
      )
   );
   public static final DeferredBlock<Block> HEDGE = register(
      "hedge", () -> new HedgeBlock(Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).strength(2.0F, 6.0F))
   );
   public static final DeferredBlock<MasonJarBlock> MASON_JAR = BLOCKS.register(
      "mason_jar", () -> new MasonJarBlock(Properties.of().noOcclusion().noTerrainParticles().randomTicks().sound(TFSoundTypes.JAR).strength(0.3F, 3.0F))
   );
   public static final DeferredBlock<JarBlock> FIREFLY_JAR = BLOCKS.register(
      "firefly_jar",
      () -> new FireflyJarBlock(Properties.of().lightLevel(state -> 15).noOcclusion().noTerrainParticles().sound(TFSoundTypes.JAR).strength(0.3F, 3.0F))
   );
   public static final DeferredBlock<Block> FIREFLY_SPAWNER = register(
      "firefly_particle_spawner",
      () -> new FireflySpawnerBlock(Properties.of().lightLevel(state -> 15).noOcclusion().noTerrainParticles().sound(TFSoundTypes.JAR).strength(0.3F, 3.0F))
   );
   public static final DeferredBlock<JarBlock> CICADA_JAR = BLOCKS.register(
      "cicada_jar", () -> new CicadaJarBlock(Properties.of().noOcclusion().noTerrainParticles().randomTicks().sound(TFSoundTypes.JAR).strength(0.3F, 3.0F))
   );
   public static final DeferredBlock<Block> MOSS_PATCH = register(
      "moss_patch",
      () -> new MossPatchBlock(
         Properties.of()
            .ignitedByLava()
            .instabreak()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.MOSS)
      )
   );
   public static final DeferredBlock<Block> MAYAPPLE = register(
      "mayapple",
      () -> new MayappleBlock(
         Properties.of()
            .ignitedByLava()
            .instabreak()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.GRASS)
      )
   );
   public static final DeferredBlock<Block> CLOVER_PATCH = register(
      "clover_patch",
      () -> new PatchBlock(
         Properties.of()
            .ignitedByLava()
            .noCollission()
            .noOcclusion()
            .instabreak()
            .mapColor(MapColor.PLANT)
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.GRASS)
      )
   );
   public static final DeferredBlock<Block> FIDDLEHEAD = register(
      "fiddlehead",
      () -> new FiddleheadBlock(
         Properties.of()
            .ignitedByLava()
            .instabreak()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .replaceable()
            .sound(SoundType.GRASS)
      )
   );
   public static final DeferredBlock<Block> MUSHGLOOM = register(
      "mushgloom",
      () -> new MushgloomBlock(
         Properties.of()
            .instabreak()
            .lightLevel(state -> 3)
            .noCollission()
            .noOcclusion()
            .mapColor(MapColor.PLANT)
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.FUNGUS)
      )
   );
   public static final DeferredBlock<Block> TORCHBERRY_PLANT = register(
      "torchberry_plant",
      () -> new TorchberryPlantBlock(
         Properties.of()
            .ignitedByLava()
            .instabreak()
            .lightLevel(value -> value.getValue(TorchberryPlantBlock.HAS_BERRIES) ? 7 : 1)
            .noCollission()
            .mapColor(MapColor.PLANT)
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.HANGING_ROOTS)
      )
   );
   public static final DeferredBlock<Block> ROOT_STRAND = register(
      "root_strand",
      () -> new RootStrandBlock(
         Properties.of()
            .ignitedByLava()
            .instabreak()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.HANGING_ROOTS)
      )
   );
   public static final DeferredBlock<Block> FALLEN_LEAVES = BLOCKS.register(
      "fallen_leaves",
      () -> new FallenLeavesBlock(
         Properties.of()
            .ignitedByLava()
            .instabreak()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .noOcclusion()
            .replaceable()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.AZALEA_LEAVES)
      )
   );
   public static final DeferredBlock<Block> ROOT_BLOCK = register(
      "root", () -> new Block(Properties.of().ignitedByLava().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F))
   );
   public static final DeferredBlock<Block> LIVEROOT_BLOCK = register(
      "liveroot_block",
      () -> new LiverootBlock(Properties.of().ignitedByLava().mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.WOOD).strength(2.0F, 3.0F))
   );
   public static final DeferredBlock<Block> UNCRAFTING_TABLE = register(
      "uncrafting_table", () -> new UncraftingTableBlock(Properties.of().ignitedByLava().mapColor(MapColor.FIRE).sound(SoundType.WOOD).strength(2.5F))
   );
   public static final DeferredBlock<Block> SMOKER = register(
      "smoker", () -> new TFSmokerBlock(Properties.of().mapColor(MapColor.GRASS).sound(SoundType.GRASS).strength(1.5F, 6.0F))
   );
   public static final DeferredBlock<Block> ENCASED_SMOKER = register(
      "encased_smoker",
      () -> new EncasedSmokerBlock(
         Properties.of().ignitedByLava().mapColor(MapColor.SAND).requiresCorrectToolForDrops().sound(SoundType.WOOD).strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> FIRE_JET = register(
      "fire_jet",
      () -> new FireJetBlock(
         Properties.of()
            .lightLevel(state -> state.getValue(FireJetBlock.STATE) != FireJetVariant.FLAME ? 0 : 15)
            .mapColor(MapColor.GRASS)
            .randomTicks()
            .sound(SoundType.GRASS)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> ENCASED_FIRE_JET = register(
      "encased_fire_jet",
      () -> new EncasedFireJetBlock(
         Properties.of()
            .ignitedByLava()
            .lightLevel(state -> state.getValue(FireJetBlock.STATE) != FireJetVariant.FLAME ? 0 : 15)
            .mapColor(MapColor.SAND)
            .requiresCorrectToolForDrops()
            .sound(SoundType.WOOD)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> FIREFLY = register(
      "firefly",
      () -> new FireflyBlock(
         Properties.of()
            .instabreak()
            .lightLevel(state -> 15)
            .noCollission()
            .noTerrainParticles()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.SLIME_BLOCK)
      )
   );
   public static final DeferredBlock<Block> CICADA = register(
      "cicada",
      () -> new CicadaBlock(Properties.of().instabreak().noCollission().noTerrainParticles().pushReaction(PushReaction.DESTROY).sound(SoundType.SLIME_BLOCK))
   );
   public static final DeferredBlock<Block> MOONWORM = register(
      "moonworm",
      () -> new MoonwormBlock(
         Properties.of()
            .forceSolidOff()
            .instabreak()
            .lightLevel(state -> 14)
            .noCollission()
            .noTerrainParticles()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.SLIME_BLOCK)
      )
   );
   public static final DeferredBlock<HugeLilyPadBlock> HUGE_LILY_PAD = BLOCKS.register(
      "huge_lily_pad",
      () -> new HugeLilyPadBlock(Properties.of().instabreak().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).sound(SoundType.LILY_PAD))
   );
   public static final DeferredBlock<Block> HUGE_WATER_LILY = BLOCKS.register(
      "huge_water_lily",
      () -> new HugeWaterLilyBlock(
         Properties.of().instabreak().mapColor(MapColor.PLANT).noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.LILY_PAD)
      )
   );
   public static final DeferredBlock<Block> SLIDER = register(
      "slider", () -> new SliderBlock(Properties.of().mapColor(MapColor.DIRT).noLootTable().noOcclusion().randomTicks().strength(2.0F, 10.0F))
   );
   public static final DeferredBlock<Block> IRON_LADDER = register(
      "iron_ladder",
      () -> new IronLadderBlock(
         Properties.of()
            .forceSolidOff()
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL)
            .strength(5.0F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> ROPE = BLOCKS.register(
      "rope", () -> new RopeBlock(Properties.of().forceSolidOff().noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.WOOL).strength(0.3F, 3.0F))
   );
   public static final DeferredBlock<TransparentBlock> CANOPY_WINDOW = register(
      "canopy_window",
      () -> new TransparentBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.HAT)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .noOcclusion()
            .isValidSpawn((pState, pLevel, pPos, pValue) -> false)
            .isRedstoneConductor((pState, pLevel, pPos) -> false)
            .isSuffocating((pState, pLevel, pPos) -> false)
            .isViewBlocking((pState, pLevel, pPos) -> false)
      )
   );
   public static final DeferredBlock<IronBarsBlock> CANOPY_WINDOW_PANE = register(
      "canopy_window_pane", () -> new IronBarsBlock(Properties.of().instrument(NoteBlockInstrument.HAT).strength(0.3F).sound(SoundType.GLASS).noOcclusion())
   );
   public static final DeferredBlock<Block> SINISTER_SPAWNER = register(
      "sinister_spawner", () -> new SinisterSpawnerBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SPAWNER).noLootTable())
   );
   public static final DeferredBlock<Block> BRAZIER = register(
      "brazier",
      () -> new BrazierBlock(
         Properties.of()
            .sound(SoundType.WOOD)
            .lightLevel(state -> state.getValue(BrazierBlock.HALF) == DoubleBlockHalf.UPPER ? ((BrazierLight)state.getValue(BrazierBlock.LIGHT)).getLight() : 0)
            .pushReaction(PushReaction.DESTROY)
      )
   );
   public static final DeferredBlock<Block> IRON_OREBERRY = register(
      "iron_oreberry",
      () -> new OreBerryBlock(
         false, TFLootTables.IRON_OREBERRY_BUSH_DROPS, Properties.of().sound(SoundType.METAL).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> GOLD_OREBERRY = register(
      "gold_oreberry",
      () -> new OreBerryBlock(
         false, TFLootTables.GOLD_OREBERRY_BUSH_DROPS, Properties.of().sound(SoundType.METAL).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> COPPER_OREBERRY = register(
      "copper_oreberry",
      () -> new OreBerryBlock(
         false, TFLootTables.COPPER_OREBERRY_BUSH_DROPS, Properties.of().sound(SoundType.METAL).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> ESSENCE_OREBERRY = register(
      "essence_oreberry",
      () -> new OreBerryBlock(
         true, TFLootTables.ESSENCE_BERRY_BUSH_DROPS, Properties.of().sound(SoundType.METAL).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> RASPBERRY_BUSH = register(
      "raspberry_bush",
      () -> new BerryBushBlock(
         TFLootTables.RASPBERRY_BUSH_DROPS, Properties.of().sound(SoundType.GRASS).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> BLUEBERRY_BUSH = register(
      "blueberry_bush",
      () -> new BerryBushBlock(
         TFLootTables.BLUEBERRY_BUSH_DROPS, Properties.of().sound(SoundType.GRASS).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> BLACKBERRY_BUSH = register(
      "blackberry_bush",
      () -> new BerryBushBlock(
         TFLootTables.BLACKBERRY_BUSH_DROPS, Properties.of().sound(SoundType.GRASS).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> MALOBERRY_BUSH = register(
      "maloberry_bush",
      () -> new BerryBushBlock(
         TFLootTables.MALOBERRY_BUSH_DROPS, Properties.of().sound(SoundType.GRASS).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> BLIGHTBERRY_BUSH = register(
      "blightberry_bush",
      () -> new DarkTowerBerryBushBlock(
         TFLootTables.BLIGHTBERRY_BUSH_DROPS, Properties.of().sound(SoundType.GRASS).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> DUSKBERRY_BUSH = register(
      "duskberry_bush",
      () -> new DarkTowerBerryBushBlock(
         TFLootTables.DUSKBERRY_BUSH_DROPS, Properties.of().sound(SoundType.GRASS).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> SKYBERRY_BUSH = register(
      "skyberry_bush",
      () -> new DarkTowerBerryBushBlock(
         TFLootTables.SKYBERRY_BUSH_DROPS, Properties.of().sound(SoundType.GRASS).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> STINGBERRY_BUSH = register(
      "stingberry_bush",
      () -> new DarkTowerBerryBushBlock(
         TFLootTables.STINGBERRY_BUSH_DROPS, Properties.of().sound(SoundType.GRASS).destroyTime(0.4F).randomTicks().dynamicShape().noOcclusion()
      )
   );
   public static final DeferredBlock<Block> NAGASTONE_HEAD = register(
      "nagastone_head",
      () -> new TFHorizontalBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> NAGASTONE = register(
      "nagastone",
      () -> new NagastoneBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> SPIRAL_BRICKS = register(
      "spiral_bricks",
      () -> new SpiralBrickBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .noOcclusion()
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> ETCHED_NAGASTONE = register(
      "etched_nagastone",
      () -> new EtchedNagastoneBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> NAGASTONE_PILLAR = register(
      "nagastone_pillar",
      () -> new DirectionalRotatedPillarBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<StairBlock> NAGASTONE_STAIRS_LEFT = register(
      "nagastone_stairs_left",
      () -> new StairBlock(((Block)ETCHED_NAGASTONE.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)ETCHED_NAGASTONE.get()))
   );
   public static final DeferredBlock<StairBlock> NAGASTONE_STAIRS_RIGHT = register(
      "nagastone_stairs_right",
      () -> new StairBlock(((Block)ETCHED_NAGASTONE.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)ETCHED_NAGASTONE.get()))
   );
   public static final DeferredBlock<Block> MOSSY_ETCHED_NAGASTONE = register(
      "mossy_etched_nagastone",
      () -> new EtchedNagastoneBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> MOSSY_NAGASTONE_PILLAR = register(
      "mossy_nagastone_pillar",
      () -> new DirectionalRotatedPillarBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<StairBlock> MOSSY_NAGASTONE_STAIRS_LEFT = register(
      "mossy_nagastone_stairs_left",
      () -> new StairBlock(((Block)MOSSY_ETCHED_NAGASTONE.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)MOSSY_ETCHED_NAGASTONE.get()))
   );
   public static final DeferredBlock<StairBlock> MOSSY_NAGASTONE_STAIRS_RIGHT = register(
      "mossy_nagastone_stairs_right",
      () -> new StairBlock(((Block)MOSSY_ETCHED_NAGASTONE.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)MOSSY_ETCHED_NAGASTONE.get()))
   );
   public static final DeferredBlock<Block> CRACKED_ETCHED_NAGASTONE = register(
      "cracked_etched_nagastone",
      () -> new EtchedNagastoneBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> CRACKED_NAGASTONE_PILLAR = register(
      "cracked_nagastone_pillar",
      () -> new DirectionalRotatedPillarBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<StairBlock> CRACKED_NAGASTONE_STAIRS_LEFT = register(
      "cracked_nagastone_stairs_left",
      () -> new StairBlock(((Block)CRACKED_ETCHED_NAGASTONE.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)CRACKED_ETCHED_NAGASTONE.get()))
   );
   public static final DeferredBlock<StairBlock> CRACKED_NAGASTONE_STAIRS_RIGHT = register(
      "cracked_nagastone_stairs_right",
      () -> new StairBlock(((Block)CRACKED_ETCHED_NAGASTONE.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)CRACKED_ETCHED_NAGASTONE.get()))
   );
   public static final DeferredBlock<RotatedPillarBlock> TWISTED_STONE = register(
      "twisted_stone",
      () -> new RotatedPillarBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> TWISTED_STONE_PILLAR = register(
      "twisted_stone_pillar",
      () -> new WallPillarBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .noOcclusion()
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F),
         12.0,
         16.0
      )
   );
   public static final DeferredBlock<Block> SKULL_CHEST = register(
      "skull_chest",
      () -> new SkullChestBlock(
         Properties.of()
            .lightLevel(state -> state.getValue(BlockLoggingEnum.MULTILOGGED) == BlockLoggingEnum.LAVA ? 15 : 0)
            .mapColor(MapColor.COLOR_LIGHT_GRAY)
            .noOcclusion()
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.NETHERITE_BLOCK)
            .strength(3.0F, 100.0F)
      )
   );
   public static final DeferredBlock<Block> KEEPSAKE_CASKET = BLOCKS.register(
      "keepsake_casket",
      () -> new KeepsakeCasketBlock(
         Properties.of()
            .lightLevel(state -> state.getValue(BlockLoggingEnum.MULTILOGGED) == BlockLoggingEnum.LAVA ? 15 : 0)
            .mapColor(MapColor.COLOR_BLACK)
            .noOcclusion()
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.NETHERITE_BLOCK)
            .strength(5.0F, 1200.0F)
      )
   );
   public static final DeferredBlock<RotatedPillarBlock> BOLD_STONE_PILLAR = register(
      "bold_stone_pillar",
      () -> new RotatedPillarBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> CHISELED_CANOPY_BOOKSHELF = register(
      "chiseled_canopy_bookshelf",
      () -> new ChiseledCanopyShelfBlock(
         Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_BROWN).sound(SoundType.CHISELED_BOOKSHELF).strength(2.5F)
      )
   );
   public static final DeferredBlock<Block> CANDELABRA = register(
      "candelabra", () -> new CandelabraBlock(Properties.of().mapColor(MapColor.STONE).strength(1.5F))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> ZOMBIE_SKULL_CANDLE = BLOCKS.register(
      "zombie_skull_candle", () -> new SkullCandleBlock(Types.ZOMBIE, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.ZOMBIE_HEAD))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> ZOMBIE_WALL_SKULL_CANDLE = BLOCKS.register(
      "zombie_wall_skull_candle", () -> new WallSkullCandleBlock(Types.ZOMBIE, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.ZOMBIE_WALL_HEAD))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> SKELETON_SKULL_CANDLE = BLOCKS.register(
      "skeleton_skull_candle", () -> new SkullCandleBlock(Types.SKELETON, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SKELETON_SKULL))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> SKELETON_WALL_SKULL_CANDLE = BLOCKS.register(
      "skeleton_wall_skull_candle",
      () -> new WallSkullCandleBlock(Types.SKELETON, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SKELETON_WALL_SKULL))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> WITHER_SKELE_SKULL_CANDLE = BLOCKS.register(
      "wither_skeleton_skull_candle",
      () -> new SkullCandleBlock(Types.WITHER_SKELETON, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WITHER_SKELETON_SKULL))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> WITHER_SKELE_WALL_SKULL_CANDLE = BLOCKS.register(
      "wither_skeleton_wall_skull_candle",
      () -> new WallSkullCandleBlock(Types.WITHER_SKELETON, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WITHER_SKELETON_WALL_SKULL))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> CREEPER_SKULL_CANDLE = BLOCKS.register(
      "creeper_skull_candle", () -> new SkullCandleBlock(Types.CREEPER, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.CREEPER_HEAD))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> CREEPER_WALL_SKULL_CANDLE = BLOCKS.register(
      "creeper_wall_skull_candle",
      () -> new WallSkullCandleBlock(Types.CREEPER, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.CREEPER_WALL_HEAD))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> PLAYER_SKULL_CANDLE = BLOCKS.register(
      "player_skull_candle", () -> new SkullCandleBlock(Types.PLAYER, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.PLAYER_HEAD))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> PLAYER_WALL_SKULL_CANDLE = BLOCKS.register(
      "player_wall_skull_candle", () -> new WallSkullCandleBlock(Types.PLAYER, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.PLAYER_WALL_HEAD))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> PIGLIN_SKULL_CANDLE = BLOCKS.register(
      "piglin_skull_candle", () -> new SkullCandleBlock(Types.PIGLIN, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.PIGLIN_HEAD))
   );
   public static final DeferredBlock<AbstractSkullCandleBlock> PIGLIN_WALL_SKULL_CANDLE = BLOCKS.register(
      "piglin_wall_skull_candle", () -> new WallSkullCandleBlock(Types.PIGLIN, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.PIGLIN_WALL_HEAD))
   );
   public static final DeferredBlock<WroughtIronFenceBlock> WROUGHT_IRON_FENCE = registerWroughtFence(
      "wrought_iron_fence",
      () -> new WroughtIronFenceBlock(Properties.of().strength(8.0F, 20.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion())
   );
   public static final DeferredBlock<RotatedPillarBlock> TERRORCOTTA_ARCS = register(
      "terrorcotta_arcs",
      () -> new RotatedPillarBlock(
         Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<GlazedTerracottaBlock> TERRORCOTTA_CURVES = register(
      "terrorcotta_curves",
      () -> new GlazedTerracottaBlock(
         Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<BinaryRotatedBlock> TERRORCOTTA_LINES = register(
      "terrorcotta_lines",
      () -> new BinaryRotatedBlock(
         Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)
      )
   );
   public static final DeferredBlock<CarpetBlock> CORONATION_CARPET = register(
      "coronation_carpet",
      () -> new WoolCarpetBlock(
         DyeColor.RED, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.RED_CARPET).isValidSpawn(net.minecraft.world.level.block.Blocks::always)
      )
   );
   public static final DeferredBlock<OminousFireBlock> OMINOUS_FIRE = BLOCKS.register(
      "ominous_fire",
      () -> new OminousFireBlock(
         Properties.of()
            .mapColor(MapColor.COLOR_PURPLE)
            .replaceable()
            .noCollission()
            .instabreak()
            .lightLevel(state -> 15)
            .sound(SoundType.WOOL)
            .pushReaction(PushReaction.DESTROY)
      )
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_CANDLE = ominousCandle(
      "ominous_candle", MapColor.SAND, net.minecraft.world.level.block.Blocks.CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_WHITE_CANDLE = ominousCandle(
      "ominous_white_candle", MapColor.WOOL, net.minecraft.world.level.block.Blocks.WHITE_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_ORANGE_CANDLE = ominousCandle(
      "ominous_orange_candle", MapColor.COLOR_ORANGE, net.minecraft.world.level.block.Blocks.ORANGE_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_MAGENTA_CANDLE = ominousCandle(
      "ominous_magenta_candle", MapColor.COLOR_MAGENTA, net.minecraft.world.level.block.Blocks.MAGENTA_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_LIGHT_BLUE_CANDLE = ominousCandle(
      "ominous_light_blue_candle", MapColor.COLOR_LIGHT_BLUE, net.minecraft.world.level.block.Blocks.LIGHT_BLUE_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_YELLOW_CANDLE = ominousCandle(
      "ominous_yellow_candle", MapColor.COLOR_YELLOW, net.minecraft.world.level.block.Blocks.YELLOW_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_LIME_CANDLE = ominousCandle(
      "ominous_lime_candle", MapColor.COLOR_LIGHT_GREEN, net.minecraft.world.level.block.Blocks.LIME_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_PINK_CANDLE = ominousCandle(
      "ominous_pink_candle", MapColor.COLOR_PINK, net.minecraft.world.level.block.Blocks.PINK_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_GRAY_CANDLE = ominousCandle(
      "ominous_gray_candle", MapColor.COLOR_GRAY, net.minecraft.world.level.block.Blocks.GRAY_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_LIGHT_GRAY_CANDLE = ominousCandle(
      "ominous_light_gray_candle", MapColor.COLOR_LIGHT_GRAY, net.minecraft.world.level.block.Blocks.LIGHT_GRAY_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_CYAN_CANDLE = ominousCandle(
      "ominous_cyan_candle", MapColor.COLOR_CYAN, net.minecraft.world.level.block.Blocks.CYAN_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_PURPLE_CANDLE = ominousCandle(
      "ominous_purple_candle", MapColor.COLOR_PURPLE, net.minecraft.world.level.block.Blocks.PURPLE_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_BLUE_CANDLE = ominousCandle(
      "ominous_blue_candle", MapColor.COLOR_BLUE, net.minecraft.world.level.block.Blocks.BLUE_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_BROWN_CANDLE = ominousCandle(
      "ominous_brown_candle", MapColor.COLOR_BROWN, net.minecraft.world.level.block.Blocks.BROWN_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_GREEN_CANDLE = ominousCandle(
      "ominous_green_candle", MapColor.COLOR_GREEN, net.minecraft.world.level.block.Blocks.GREEN_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_RED_CANDLE = ominousCandle(
      "ominous_red_candle", MapColor.COLOR_RED, net.minecraft.world.level.block.Blocks.RED_CANDLE
   );
   public static final DeferredBlock<OminousCandleBlock> OMINOUS_BLACK_CANDLE = ominousCandle(
      "ominous_black_candle", MapColor.COLOR_BLACK, net.minecraft.world.level.block.Blocks.BLACK_CANDLE
   );
   public static final DeferredBlock<Block> MAZESTONE = register(
      "mazestone",
      () -> new Block(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(100.0F, 5.0F)
      )
   );
   public static final DeferredBlock<Block> MAZESTONE_BRICK = register(
      "mazestone_brick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)MAZESTONE.get()))
   );
   public static final DeferredBlock<Block> CUT_MAZESTONE = register("cut_mazestone", () -> new Block(Properties.ofFullCopy((BlockBehaviour)MAZESTONE.get())));
   public static final DeferredBlock<Block> DECORATIVE_MAZESTONE = register(
      "decorative_mazestone", () -> new Block(Properties.ofFullCopy((BlockBehaviour)MAZESTONE.get()))
   );
   public static final DeferredBlock<Block> CRACKED_MAZESTONE = register(
      "cracked_mazestone", () -> new Block(Properties.ofFullCopy((BlockBehaviour)MAZESTONE.get()))
   );
   public static final DeferredBlock<Block> MOSSY_MAZESTONE = register(
      "mossy_mazestone", () -> new Block(Properties.ofFullCopy((BlockBehaviour)MAZESTONE.get()))
   );
   public static final DeferredBlock<Block> MAZESTONE_MOSAIC = register(
      "mazestone_mosaic", () -> new Block(Properties.ofFullCopy((BlockBehaviour)MAZESTONE.get()))
   );
   public static final DeferredBlock<Block> MAZESTONE_BORDER = register(
      "mazestone_border", () -> new Block(Properties.ofFullCopy((BlockBehaviour)MAZESTONE.get()))
   );
   public static final DeferredBlock<Block> RED_THREAD = register(
      "red_thread",
      () -> new RedThreadBlock(
         Properties.of()
            .instabreak()
            .mapColor(MapColor.FIRE)
            .isValidSpawn(TFBlocks::noSpawning)
            .noCollission()
            .noOcclusion()
            .noTerrainParticles()
            .pushReaction(PushReaction.DESTROY)
      )
   );
   public static final DeferredBlock<Block> MAZE_SLIME_BLOCK = register(
      "maze_slime_block", () -> new MazeSlimeBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SLIME_BLOCK).mapColor(MapColor.STONE))
   );
   public static final DeferredBlock<Block> STRONGHOLD_SHIELD = register(
      "stronghold_shield",
      () -> new StrongholdShieldBlock(
         Properties.of()
            .noLootTable()
            .mapColor(MapColor.STONE)
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL)
            .strength(-1.0F, 6000000.0F)
      )
   );
   public static final DeferredBlock<Block> TROPHY_PEDESTAL = register(
      "trophy_pedestal",
      () -> new TrophyPedestalBlock(
         Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.BLOCK).requiresCorrectToolForDrops().sound(SoundType.STONE).strength(2.0F, 2000.0F)
      )
   );
   public static final DeferredBlock<Block> UNDERBRICK = register(
      "underbrick",
      () -> new Block(Properties.of().mapColor(MapColor.WOOD).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS).strength(1.5F, 6.0F))
   );
   public static final DeferredBlock<Block> MOSSY_UNDERBRICK = register(
      "mossy_underbrick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)UNDERBRICK.get()))
   );
   public static final DeferredBlock<Block> CRACKED_UNDERBRICK = register(
      "cracked_underbrick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)UNDERBRICK.get()))
   );
   public static final DeferredBlock<Block> UNDERBRICK_FLOOR = register(
      "underbrick_floor", () -> new Block(Properties.ofFullCopy((BlockBehaviour)UNDERBRICK.get()))
   );
   public static final DeferredBlock<Block> TOWERWOOD = register(
      "towerwood",
      () -> new Block(
         Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_ORANGE).strength(40.0F, 6.0F).sound(SoundType.WOOD)
      )
   );
   public static final DeferredBlock<Block> ENCASED_TOWERWOOD = register(
      "encased_towerwood", () -> new Block(Properties.ofFullCopy((BlockBehaviour)TOWERWOOD.get()).mapColor(MapColor.SAND))
   );
   public static final DeferredBlock<Block> CRACKED_TOWERWOOD = register(
      "cracked_towerwood", () -> new Block(Properties.ofFullCopy((BlockBehaviour)TOWERWOOD.get()))
   );
   public static final DeferredBlock<Block> MOSSY_TOWERWOOD = register(
      "mossy_towerwood", () -> new Block(Properties.ofFullCopy((BlockBehaviour)TOWERWOOD.get()))
   );
   public static final DeferredBlock<Block> INFESTED_TOWERWOOD = register(
      "infested_towerwood",
      () -> new InfestedTowerwoodBlock(
         Properties.ofFullCopy((BlockBehaviour)TOWERWOOD.get()).instrument(NoteBlockInstrument.FLUTE).noLootTable().strength(2.0F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> REAPPEARING_BLOCK = register(
      "reappearing_block",
      () -> new ReappearingBlock(
         Properties.of()
            .forceSolidOn()
            .lightLevel(state -> 4)
            .mapColor(MapColor.SAND)
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.WOOD)
            .strength(10.0F, 35.0F)
      )
   );
   public static final DeferredBlock<Block> VANISHING_BLOCK = register(
      "vanishing_block",
      () -> new VanishingBlock(
         Properties.of()
            .lightLevel(state -> state.getValue(VanishingBlock.ACTIVE) ? 4 : 0)
            .mapColor(MapColor.SAND)
            .pushReaction(PushReaction.BLOCK)
            .sound(SoundType.WOOD)
            .strength(10.0F, 35.0F)
      )
   );
   public static final DeferredBlock<Block> UNBREAKABLE_VANISHING_BLOCK = BLOCKS.register(
      "unbreakable_vanishing_block",
      () -> new VanishingBlock(Properties.ofFullCopy((BlockBehaviour)VANISHING_BLOCK.get()).noLootTable().strength(-1.0F, 6000000.0F))
   );
   public static final DeferredBlock<Block> LOCKED_VANISHING_BLOCK = register(
      "locked_vanishing_block",
      () -> new LockedVanishingBlock(Properties.of().pushReaction(PushReaction.BLOCK).mapColor(MapColor.SAND).sound(SoundType.WOOD).strength(-1.0F, 2000.0F))
   );
   public static final DeferredBlock<Block> CARMINITE_BUILDER = register(
      "carminite_builder",
      () -> new BuilderBlock(
         Properties.of()
            .lightLevel(state -> state.getValue(BuilderBlock.STATE) == TowerDeviceVariant.BUILDER_ACTIVE ? 4 : 0)
            .mapColor(MapColor.SAND)
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.WOOD)
            .strength(10.0F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> BUILT_BLOCK = BLOCKS.register(
      "built_block",
      () -> new TranslucentBuiltBlock(
         Properties.of().noLootTable().noOcclusion().pushReaction(PushReaction.BLOCK).sound(SoundType.WOOD).strength(50.0F, 2000.0F)
      )
   );
   public static final DeferredBlock<Block> ANTIBUILDER = register(
      "antibuilder",
      () -> new AntibuilderBlock(
         Properties.of()
            .lightLevel(state -> 10)
            .noLootTable()
            .pushReaction(PushReaction.BLOCK)
            .mapColor(MapColor.SAND)
            .requiresCorrectToolForDrops()
            .sound(SoundType.WOOD)
            .strength(10.0F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> ANTIBUILT_BLOCK = BLOCKS.register(
      "antibuilt_block",
      () -> new Block(Properties.of().noLootTable().noOcclusion().pushReaction(PushReaction.BLOCK).sound(SoundType.WOOD).strength(0.3F, 2000.0F))
   );
   public static final DeferredBlock<GhastTrapBlock> GHAST_TRAP = register(
      "ghast_trap",
      () -> new GhastTrapBlock(
         Properties.of()
            .lightLevel(state -> state.getValue(GhastTrapBlock.ACTIVE) ? 15 : 0)
            .mapColor(MapColor.SAND)
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.WOOD)
            .strength(10.0F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> CARMINITE_REACTOR = register(
      "carminite_reactor",
      () -> new CarminiteReactorBlock(
         Properties.of()
            .lightLevel(state -> state.getValue(CarminiteReactorBlock.ACTIVE) ? 15 : 0)
            .mapColor(MapColor.SAND)
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.WOOD)
            .strength(10.0F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> REACTOR_DEBRIS = BLOCKS.register(
      "reactor_debris",
      () -> new ReactorDebrisBlock(
         Properties.of().noLootTable().noOcclusion().pushReaction(PushReaction.BLOCK).sound(SoundType.ANCIENT_DEBRIS).strength(0.3F, 2000.0F)
      )
   );
   public static final DeferredBlock<Block> FAKE_GOLD = BLOCKS.register(
      "fake_gold", () -> new Block(Properties.of().noLootTable().pushReaction(PushReaction.BLOCK).sound(SoundType.METAL).strength(50.0F, 2000.0F))
   );
   public static final DeferredBlock<Block> FAKE_DIAMOND = BLOCKS.register(
      "fake_diamond", () -> new Block(Properties.of().noLootTable().pushReaction(PushReaction.BLOCK).sound(SoundType.METAL).strength(50.0F, 2000.0F))
   );
   public static final DeferredBlock<Block> EXPERIMENT_115 = BLOCKS.register(
      "experiment_115",
      () -> new Experiment115Block(Properties.of().noLootTable().pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.WOOL).strength(0.5F))
   );
   public static final DeferredBlock<Block> AURORA_BLOCK = register(
      "aurora_block", () -> new AuroraBrickBlock(Properties.of().instrument(NoteBlockInstrument.CHIME).mapColor(MapColor.ICE).strength(10.0F, 6.0F))
   );
   public static final DeferredBlock<RotatedPillarBlock> AURORA_PILLAR = register(
      "aurora_pillar",
      () -> new RotatedPillarBlock(
         Properties.of().instrument(NoteBlockInstrument.CHIME).mapColor(MapColor.ICE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> AURORA_SLAB = register(
      "aurora_slab",
      () -> new SlabBlock(Properties.of().instrument(NoteBlockInstrument.CHIME).mapColor(MapColor.ICE).requiresCorrectToolForDrops().strength(2.0F, 6.0F))
   );
   public static final DeferredBlock<Block> AURORALIZED_GLASS = register(
      "auroralized_glass", () -> new AuroralizedGlassBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GLASS))
   );
   public static final DeferredBlock<Block> BROWN_THORNS = register(
      "brown_thorns",
      () -> new ThornsBlock(
         Properties.of().noLootTable().mapColor(MapColor.PODZOL).pushReaction(PushReaction.BLOCK).sound(SoundType.WOOD).strength(50.0F, 2000.0F)
      )
   );
   public static final DeferredBlock<Block> GREEN_THORNS = register(
      "green_thorns",
      () -> new ThornsBlock(
         Properties.of().noLootTable().mapColor(MapColor.PLANT).pushReaction(PushReaction.BLOCK).sound(SoundType.WOOD).strength(50.0F, 2000.0F)
      )
   );
   public static final DeferredBlock<Block> BURNT_THORNS = register(
      "burnt_thorns",
      () -> new BurntThornsBlock(Properties.of().instabreak().noLootTable().mapColor(MapColor.STONE).pushReaction(PushReaction.DESTROY).sound(SoundType.SAND))
   );
   public static final DeferredBlock<Block> THORN_ROSE = register(
      "thorn_rose",
      () -> new ThornRoseBlock(
         Properties.of().mapColor(MapColor.PLANT).noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).strength(10.0F, 0.0F)
      )
   );
   public static final DeferredBlock<Block> THORN_LEAVES = register(
      "thorn_leaves",
      () -> new SpecialStemLeavesBlock(
         Properties.of()
            .ignitedByLava()
            .mapColor(MapColor.PLANT)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .randomTicks()
            .sound(SoundType.AZALEA_LEAVES)
            .strength(0.2F)
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false),
         state -> state.is(BROWN_THORNS) || state.is(GREEN_THORNS)
      )
   );
   public static final DeferredBlock<Block> BEANSTALK_LEAVES = register(
      "beanstalk_leaves",
      () -> new SpecialStemLeavesBlock(
         Properties.of()
            .ignitedByLava()
            .mapColor(MapColor.PLANT)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .randomTicks()
            .sound(SoundType.AZALEA_LEAVES)
            .strength(0.2F)
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false),
         state -> state.is(TFBlocks.HUGE_STALK)
      )
   );
   public static final DeferredBlock<Block> DEADROCK = register(
      "deadrock",
      () -> new Block(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(100.0F, 6000000.0F)
      )
   );
   public static final DeferredBlock<Block> CRACKED_DEADROCK = register(
      "cracked_deadrock", () -> new Block(Properties.ofFullCopy((BlockBehaviour)DEADROCK.get()))
   );
   public static final DeferredBlock<Block> WEATHERED_DEADROCK = register(
      "weathered_deadrock", () -> new Block(Properties.ofFullCopy((BlockBehaviour)DEADROCK.get()))
   );
   public static final DeferredBlock<Block> TROLLSTEINN = register(
      "trollsteinn",
      () -> new TrollsteinnBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.STONE)
            .randomTicks()
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .strength(2.0F, 6.0F)
      )
   );
   public static final DeferredBlock<Block> WISPY_CLOUD = register(
      "wispy_cloud",
      () -> new WispyCloudBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.HAT)
            .mapColor(MapColor.SNOW)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .replaceable()
            .sound(SoundType.WOOL)
            .strength(0.3F, 0.0F)
            .forceSolidOff(),
         Precipitation.NONE
      )
   );
   public static final DeferredBlock<Block> FLUFFY_CLOUD = register(
      "fluffy_cloud",
      () -> new CloudBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.HAT)
            .mapColor(MapColor.ICE)
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.WOOL)
            .strength(0.8F, 0.0F)
            .randomTicks(),
         null
      )
   );
   public static final DeferredBlock<Block> RAINY_CLOUD = register(
      "rainy_cloud",
      () -> new CloudBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.HAT)
            .mapColor(MapColor.ICE)
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.WOOL)
            .strength(0.8F, 0.0F)
            .randomTicks(),
         Precipitation.RAIN
      )
   );
   public static final DeferredBlock<Block> SNOWY_CLOUD = register(
      "snowy_cloud",
      () -> new CloudBlock(
         Properties.of()
            .instrument(NoteBlockInstrument.HAT)
            .mapColor(MapColor.ICE)
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.WOOL)
            .strength(0.8F, 0.0F)
            .randomTicks(),
         Precipitation.SNOW
      )
   );
   public static final DeferredBlock<Block> GIANT_COBBLESTONE = register(
      "giant_cobblestone",
      () -> new GiantBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.COBBLESTONE)
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .strength(128.0F, 50.0F)
      )
   );
   public static final DeferredBlock<Block> GIANT_LOG = register(
      "giant_log",
      () -> new GiantBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS)
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .strength(128.0F, 30.0F)
      )
   );
   public static final DeferredBlock<Block> GIANT_LEAVES = register(
      "giant_leaves",
      () -> new GiantLeavesBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_LEAVES)
            .noOcclusion()
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.AZALEA_LEAVES)
            .strength(12.8F, 15.0F)
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isValidSpawn(TFBlocks::noSpawning)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<Block> GIANT_OBSIDIAN = register(
      "giant_obsidian",
      () -> new GiantBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OBSIDIAN)
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .strength(204800.0F, 8192000.0F)
            .isValidSpawn(TFBlocks::noSpawning)
      )
   );
   public static final DeferredBlock<Block> UBEROUS_SOIL = register(
      "uberous_soil", () -> new UberousSoilBlock(Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(0.6F))
   );
   public static final DeferredBlock<RotatedPillarBlock> HUGE_STALK = register(
      "huge_stalk",
      () -> new RotatedPillarBlock(
         Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.PLANT).sound(SoundType.STEM).strength(1.5F, 3.0F)
      )
   );
   public static final DeferredBlock<Block> BEANSTALK_GROWER = BLOCKS.register(
      "beanstalk_grower",
      () -> new GrowingBeanstalkBlock(Properties.of().noCollission().noLootTable().noOcclusion().noTerrainParticles().strength(-1.0F, 6000000.0F))
   );
   public static final DeferredBlock<Block> HUGE_MUSHGLOOM = register(
      "huge_mushgloom",
      () -> new HugeMushroomBlock(
         Properties.of().ignitedByLava().lightLevel(state -> 5).mapColor(MapColor.COLOR_ORANGE).sound(SoundType.SHROOMLIGHT).strength(0.2F)
      )
   );
   public static final DeferredBlock<Block> HUGE_MUSHGLOOM_STEM = register(
      "huge_mushgloom_stem",
      () -> new HugeMushroomBlock(Properties.of().ignitedByLava().lightLevel(state -> 5).mapColor(MapColor.COLOR_ORANGE).sound(SoundType.NYLIUM).strength(0.2F))
   );
   public static final DeferredBlock<Block> TROLLVIDR = register(
      "trollvidr",
      () -> new TrollRootBlock(
         Properties.of().instabreak().mapColor(MapColor.PLANT).noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.FLOWERING_AZALEA)
      )
   );
   public static final DeferredBlock<Block> UNRIPE_TROLLBER = register(
      "unripe_trollber",
      () -> new UnripeTorchClusterBlock(
         Properties.of()
            .instabreak()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .pushReaction(PushReaction.DESTROY)
            .randomTicks()
            .sound(SoundType.FLOWERING_AZALEA)
      )
   );
   public static final DeferredBlock<Block> TROLLBER = register(
      "trollber",
      () -> new TrollRootBlock(
         Properties.of()
            .instabreak()
            .lightLevel(state -> 15)
            .mapColor(MapColor.PLANT)
            .noCollission()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.FLOWERING_AZALEA)
      )
   );
   public static final DeferredBlock<Block> CASTLE_BRICK = register(
      "castle_brick",
      () -> new Block(
         Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .mapColor(MapColor.QUARTZ)
            .requiresCorrectToolForDrops()
            .sound(SoundType.DEEPSLATE_TILES)
            .strength(100.0F, 50.0F)
      )
   );
   public static final DeferredBlock<Block> WORN_CASTLE_BRICK = register(
      "worn_castle_brick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<Block> CRACKED_CASTLE_BRICK = register(
      "cracked_castle_brick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<Block> CASTLE_ROOF_TILE = register(
      "castle_roof_tile", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()).mapColor(MapColor.COLOR_GRAY))
   );
   public static final DeferredBlock<Block> MOSSY_CASTLE_BRICK = register(
      "mossy_castle_brick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<Block> THICK_CASTLE_BRICK = register(
      "thick_castle_brick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<Block> ENCASED_CASTLE_BRICK_PILLAR = register(
      "encased_castle_brick_pillar", () -> new RotatedPillarBlock(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<Block> ENCASED_CASTLE_BRICK_TILE = register(
      "encased_castle_brick_tile", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<Block> BOLD_CASTLE_BRICK_PILLAR = register(
      "bold_castle_brick_pillar", () -> new RotatedPillarBlock(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<Block> BOLD_CASTLE_BRICK_TILE = register(
      "bold_castle_brick_tile", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<StairBlock> CASTLE_BRICK_STAIRS = register(
      "castle_brick_stairs", () -> new StairBlock(((Block)CASTLE_BRICK.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<StairBlock> WORN_CASTLE_BRICK_STAIRS = register(
      "worn_castle_brick_stairs",
      () -> new StairBlock(((Block)WORN_CASTLE_BRICK.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)WORN_CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<StairBlock> CRACKED_CASTLE_BRICK_STAIRS = register(
      "cracked_castle_brick_stairs",
      () -> new StairBlock(((Block)CRACKED_CASTLE_BRICK.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)CRACKED_CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<StairBlock> MOSSY_CASTLE_BRICK_STAIRS = register(
      "mossy_castle_brick_stairs",
      () -> new StairBlock(((Block)MOSSY_CASTLE_BRICK.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)MOSSY_CASTLE_BRICK.get()))
   );
   public static final DeferredBlock<StairBlock> ENCASED_CASTLE_BRICK_STAIRS = register(
      "encased_castle_brick_stairs",
      () -> new StairBlock(
         ((Block)ENCASED_CASTLE_BRICK_PILLAR.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)ENCASED_CASTLE_BRICK_PILLAR.get())
      )
   );
   public static final DeferredBlock<StairBlock> BOLD_CASTLE_BRICK_STAIRS = register(
      "bold_castle_brick_stairs",
      () -> new StairBlock(((Block)BOLD_CASTLE_BRICK_PILLAR.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)BOLD_CASTLE_BRICK_PILLAR.get()))
   );
   public static final DeferredBlock<Block> PINK_CASTLE_RUNE_BRICK = register(
      "pink_castle_rune_brick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()).mapColor(DyeColor.MAGENTA))
   );
   public static final DeferredBlock<Block> BLUE_CASTLE_RUNE_BRICK = register(
      "blue_castle_rune_brick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()).mapColor(DyeColor.LIGHT_BLUE))
   );
   public static final DeferredBlock<Block> YELLOW_CASTLE_RUNE_BRICK = register(
      "yellow_castle_rune_brick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()).mapColor(DyeColor.YELLOW))
   );
   public static final DeferredBlock<Block> VIOLET_CASTLE_RUNE_BRICK = register(
      "violet_castle_rune_brick", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CASTLE_BRICK.get()).mapColor(DyeColor.PURPLE))
   );
   public static final DeferredBlock<Block> VIOLET_FORCE_FIELD = register(
      "violet_force_field",
      () -> new ForceFieldBlock(
         Properties.of()
            .lightLevel(state -> 2)
            .mapColor(DyeColor.PURPLE)
            .noLootTable()
            .noOcclusion()
            .pushReaction(PushReaction.BLOCK)
            .strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> PINK_FORCE_FIELD = register(
      "pink_force_field",
      () -> new ForceFieldBlock(
         Properties.of()
            .lightLevel(state -> 2)
            .mapColor(DyeColor.MAGENTA)
            .noLootTable()
            .noOcclusion()
            .pushReaction(PushReaction.BLOCK)
            .strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> ORANGE_FORCE_FIELD = register(
      "orange_force_field",
      () -> new ForceFieldBlock(
         Properties.of()
            .lightLevel(state -> 2)
            .mapColor(DyeColor.ORANGE)
            .noLootTable()
            .noOcclusion()
            .pushReaction(PushReaction.BLOCK)
            .strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> GREEN_FORCE_FIELD = register(
      "green_force_field",
      () -> new ForceFieldBlock(
         Properties.of()
            .lightLevel(state -> 2)
            .mapColor(DyeColor.GREEN)
            .noLootTable()
            .noOcclusion()
            .pushReaction(PushReaction.BLOCK)
            .strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> BLUE_FORCE_FIELD = register(
      "blue_force_field",
      () -> new ForceFieldBlock(
         Properties.of()
            .lightLevel(state -> 2)
            .mapColor(DyeColor.LIGHT_BLUE)
            .noLootTable()
            .noOcclusion()
            .pushReaction(PushReaction.BLOCK)
            .strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> CINDER_FURNACE = register(
      "cinder_furnace",
      () -> new CinderFurnaceBlock(Properties.of().mapColor(MapColor.WOOD).requiresCorrectToolForDrops().strength(7.0F).lightLevel(state -> 15))
   );
   public static final DeferredBlock<RotatedPillarBlock> CINDER_LOG = register(
      "cinder_log",
      () -> new RotatedPillarBlock(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_GRAY).strength(1.0F))
   );
   public static final DeferredBlock<Block> CINDER_WOOD = register(
      "cinder_wood", () -> new Block(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_GRAY).strength(1.0F))
   );
   public static final DeferredBlock<Block> YELLOW_CASTLE_DOOR = register(
      "yellow_castle_door",
      () -> new CastleDoorBlock(
         Properties.of()
            .forceSolidOn()
            .mapColor(state -> state.getValue(CastleDoorBlock.VANISHED) ? MapColor.NONE : DyeColor.YELLOW.getMapColor())
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.DEEPSLATE_TILES)
            .strength(100.0F, 100.0F)
      )
   );
   public static final DeferredBlock<Block> VIOLET_CASTLE_DOOR = register(
      "violet_castle_door",
      () -> new CastleDoorBlock(
         Properties.of()
            .forceSolidOn()
            .mapColor(state -> state.getValue(CastleDoorBlock.VANISHED) ? MapColor.NONE : DyeColor.LIGHT_BLUE.getMapColor())
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.DEEPSLATE_TILES)
            .strength(100.0F, 100.0F)
      )
   );
   public static final DeferredBlock<Block> PINK_CASTLE_DOOR = register(
      "pink_castle_door",
      () -> new CastleDoorBlock(
         Properties.of()
            .forceSolidOn()
            .mapColor(state -> state.getValue(CastleDoorBlock.VANISHED) ? MapColor.NONE : DyeColor.MAGENTA.getMapColor())
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.DEEPSLATE_TILES)
            .strength(100.0F, 100.0F)
      )
   );
   public static final DeferredBlock<Block> BLUE_CASTLE_DOOR = register(
      "blue_castle_door",
      () -> new CastleDoorBlock(
         Properties.of()
            .forceSolidOn()
            .mapColor(state -> state.getValue(CastleDoorBlock.VANISHED) ? MapColor.NONE : DyeColor.PURPLE.getMapColor())
            .pushReaction(PushReaction.BLOCK)
            .requiresCorrectToolForDrops()
            .sound(SoundType.DEEPSLATE_TILES)
            .strength(100.0F, 100.0F)
      )
   );
   public static final DeferredBlock<Block> TWILIGHT_PORTAL_MINIATURE_STRUCTURE = register(
      "twilight_portal_miniature_structure",
      () -> new MiniatureStructureBlock(Properties.of().noCollission().noOcclusion().requiresCorrectToolForDrops().strength(0.75F))
   );
   public static final DeferredBlock<Block> NAGA_COURTYARD_MINIATURE_STRUCTURE = register(
      "naga_courtyard_miniature_structure", () -> new MiniatureStructureBlock(Properties.ofFullCopy((BlockBehaviour)TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get()))
   );
   public static final DeferredBlock<Block> LICH_TOWER_MINIATURE_STRUCTURE = register(
      "lich_tower_miniature_structure", () -> new MiniatureStructureBlock(Properties.ofFullCopy((BlockBehaviour)TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get()))
   );
   public static final DeferredBlock<Block> MINOTAUR_LABYRINTH_MINIATURE_STRUCTURE = register(
      "minotaur_labyrinth_miniature_structure",
      () -> new MiniatureStructureBlock(Properties.ofFullCopy((BlockBehaviour)TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get()))
   );
   public static final DeferredBlock<Block> DARK_TOWER_MINIATURE_STRUCTURE = register(
      "dark_tower_miniature_structure", () -> new MiniatureStructureBlock(Properties.ofFullCopy((BlockBehaviour)TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get()))
   );
   public static final DeferredBlock<Block> KNIGHTMETAL_BLOCK = register(
      "knightmetal_block",
      () -> new KnightmetalBlock(Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK).strength(5.0F, 40.0F))
   );
   public static final DeferredBlock<Block> IRONWOOD_BLOCK = register(
      "ironwood_block", () -> new Block(Properties.of().ignitedByLava().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(5.0F, 6.0F))
   );
   public static final DeferredBlock<Block> FIERY_BLOCK = registerFireResistantItem(
      "fiery_block",
      () -> new FieryBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_BLACK)
            .noOcclusion()
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL)
            .strength(5.0F, 6.0F)
            .emissiveRendering((state, world, pos) -> true)
      )
   );
   public static final DeferredBlock<Block> STEELEAF_BLOCK = register(
      "steeleaf_block", () -> new Block(Properties.of().mapColor(MapColor.PLANT).sound(SoundType.NETHERITE_BLOCK).strength(5.0F, 6.0F))
   );
   public static final DeferredBlock<Block> ARCTIC_FUR_BLOCK = register(
      "arctic_fur_block", () -> new ArcticFurBlock(Properties.of().ignitedByLava().mapColor(MapColor.WOOL).sound(SoundType.WOOL).strength(0.8F))
   );
   public static final DeferredBlock<Block> CARMINITE_BLOCK = register(
      "carminite_block",
      () -> new Block(Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F, 10.0F).requiresCorrectToolForDrops().sound(SoundType.METAL))
   );
   public static final DeferredBlock<Block> NAGA_BOSS_SPAWNER = register(
      "naga_boss_spawner",
      () -> new BossSpawnerBlock(
         BossVariant.NAGA, Properties.of().mapColor(MapColor.STONE).noLootTable().sound(SoundType.METAL).noOcclusion().strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> LICH_BOSS_SPAWNER = register(
      "lich_boss_spawner",
      () -> new BossSpawnerBlock(
         BossVariant.LICH, Properties.of().mapColor(MapColor.STONE).noLootTable().sound(SoundType.METAL).noOcclusion().strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> HYDRA_BOSS_SPAWNER = register(
      "hydra_boss_spawner",
      () -> new BossSpawnerBlock(
         BossVariant.HYDRA, Properties.of().mapColor(MapColor.STONE).noLootTable().sound(SoundType.METAL).noOcclusion().strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> UR_GHAST_BOSS_SPAWNER = register(
      "ur_ghast_boss_spawner",
      () -> new BossSpawnerBlock(
         BossVariant.UR_GHAST, Properties.of().mapColor(MapColor.STONE).noLootTable().sound(SoundType.METAL).noOcclusion().strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> KNIGHT_PHANTOM_BOSS_SPAWNER = register(
      "knight_phantom_boss_spawner",
      () -> new BossSpawnerBlock(
         BossVariant.KNIGHT_PHANTOM, Properties.of().mapColor(MapColor.STONE).noLootTable().sound(SoundType.METAL).noOcclusion().strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> SNOW_QUEEN_BOSS_SPAWNER = register(
      "snow_queen_boss_spawner",
      () -> new BossSpawnerBlock(
         BossVariant.SNOW_QUEEN, Properties.of().mapColor(MapColor.STONE).noLootTable().sound(SoundType.METAL).noOcclusion().strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> MINOSHROOM_BOSS_SPAWNER = register(
      "minoshroom_boss_spawner",
      () -> new BossSpawnerBlock(
         BossVariant.MINOSHROOM, Properties.of().mapColor(MapColor.STONE).noLootTable().sound(SoundType.METAL).noOcclusion().strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> ALPHA_YETI_BOSS_SPAWNER = register(
      "alpha_yeti_boss_spawner",
      () -> new BossSpawnerBlock(
         BossVariant.ALPHA_YETI, Properties.of().mapColor(MapColor.STONE).noLootTable().sound(SoundType.METAL).noOcclusion().strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<Block> FINAL_BOSS_BOSS_SPAWNER = register(
      "final_boss_boss_spawner",
      () -> new BossSpawnerBlock(
         BossVariant.FINAL_BOSS, Properties.of().mapColor(MapColor.STONE).noLootTable().sound(SoundType.METAL).noOcclusion().strength(-1.0F, 3600000.8F)
      )
   );
   public static final DeferredBlock<TrophyBlock> NAGA_TROPHY = BLOCKS.register(
      "naga_trophy", () -> new TrophyBlock(BossVariant.NAGA, 5, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyBlock> LICH_TROPHY = BLOCKS.register(
      "lich_trophy", () -> new TrophyBlock(BossVariant.LICH, 6, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyBlock> HYDRA_TROPHY = BLOCKS.register(
      "hydra_trophy", () -> new TrophyBlock(BossVariant.HYDRA, 12, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyBlock> UR_GHAST_TROPHY = BLOCKS.register(
      "ur_ghast_trophy", () -> new TrophyBlock(BossVariant.UR_GHAST, 13, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyBlock> KNIGHT_PHANTOM_TROPHY = BLOCKS.register(
      "knight_phantom_trophy", () -> new TrophyBlock(BossVariant.KNIGHT_PHANTOM, 8, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyBlock> SNOW_QUEEN_TROPHY = BLOCKS.register(
      "snow_queen_trophy", () -> new TrophyBlock(BossVariant.SNOW_QUEEN, 14, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyBlock> MINOSHROOM_TROPHY = BLOCKS.register(
      "minoshroom_trophy", () -> new TrophyBlock(BossVariant.MINOSHROOM, 7, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyBlock> ALPHA_YETI_TROPHY = BLOCKS.register(
      "alpha_yeti_trophy", () -> new TrophyBlock(BossVariant.ALPHA_YETI, 9, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyBlock> QUEST_RAM_TROPHY = BLOCKS.register(
      "quest_ram_trophy", () -> new TrophyBlock(BossVariant.QUEST_RAM, 1, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyWallBlock> NAGA_WALL_TROPHY = BLOCKS.register(
      "naga_wall_trophy", () -> new TrophyWallBlock(BossVariant.NAGA, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyWallBlock> LICH_WALL_TROPHY = BLOCKS.register(
      "lich_wall_trophy", () -> new TrophyWallBlock(BossVariant.LICH, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyWallBlock> HYDRA_WALL_TROPHY = BLOCKS.register(
      "hydra_wall_trophy", () -> new TrophyWallBlock(BossVariant.HYDRA, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyWallBlock> UR_GHAST_WALL_TROPHY = BLOCKS.register(
      "ur_ghast_wall_trophy", () -> new TrophyWallBlock(BossVariant.UR_GHAST, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyWallBlock> KNIGHT_PHANTOM_WALL_TROPHY = BLOCKS.register(
      "knight_phantom_wall_trophy", () -> new TrophyWallBlock(BossVariant.KNIGHT_PHANTOM, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyWallBlock> SNOW_QUEEN_WALL_TROPHY = BLOCKS.register(
      "snow_queen_wall_trophy", () -> new TrophyWallBlock(BossVariant.SNOW_QUEEN, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyWallBlock> MINOSHROOM_WALL_TROPHY = BLOCKS.register(
      "minoshroom_wall_trophy", () -> new TrophyWallBlock(BossVariant.MINOSHROOM, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyWallBlock> ALPHA_YETI_WALL_TROPHY = BLOCKS.register(
      "alpha_yeti_wall_trophy", () -> new TrophyWallBlock(BossVariant.ALPHA_YETI, Properties.of().instabreak())
   );
   public static final DeferredBlock<TrophyWallBlock> QUEST_RAM_WALL_TROPHY = BLOCKS.register(
      "quest_ram_wall_trophy", () -> new TrophyWallBlock(BossVariant.QUEST_RAM, Properties.of().instabreak())
   );
   public static final DeferredBlock<BanisterBlock> OAK_BANISTER = register(
      "oak_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS))
   );
   public static final DeferredBlock<BanisterBlock> SPRUCE_BANISTER = register(
      "spruce_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SPRUCE_PLANKS))
   );
   public static final DeferredBlock<BanisterBlock> BIRCH_BANISTER = register(
      "birch_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BIRCH_PLANKS))
   );
   public static final DeferredBlock<BanisterBlock> JUNGLE_BANISTER = register(
      "jungle_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.JUNGLE_PLANKS))
   );
   public static final DeferredBlock<BanisterBlock> ACACIA_BANISTER = register(
      "acacia_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.ACACIA_PLANKS))
   );
   public static final DeferredBlock<BanisterBlock> DARK_OAK_BANISTER = register(
      "dark_oak_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DARK_OAK_PLANKS))
   );
   public static final DeferredBlock<BanisterBlock> CRIMSON_BANISTER = register(
      "crimson_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.CRIMSON_PLANKS))
   );
   public static final DeferredBlock<BanisterBlock> WARPED_BANISTER = register(
      "warped_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WARPED_PLANKS))
   );
   public static final DeferredBlock<BanisterBlock> VANGROVE_BANISTER = register(
      "vangrove_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.MANGROVE_PLANKS))
   );
   public static final DeferredBlock<BanisterBlock> BAMBOO_BANISTER = register(
      "bamboo_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BAMBOO_PLANKS))
   );
   public static final DeferredBlock<BanisterBlock> CHERRY_BANISTER = register(
      "cherry_banister", () -> new BanisterBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.CHERRY_PLANKS))
   );
   public static final DeferredBlock<DryingRackBlock> OAK_DRYING_RACK = register(
      "oak_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.OAK_SLAB, 0.5F))
   );
   public static final DeferredBlock<DryingRackBlock> SPRUCE_DRYING_RACK = register(
      "spruce_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.SPRUCE_SLAB, 0.5F))
   );
   public static final DeferredBlock<DryingRackBlock> BIRCH_DRYING_RACK = register(
      "birch_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.BIRCH_SLAB, 0.5F))
   );
   public static final DeferredBlock<DryingRackBlock> JUNGLE_DRYING_RACK = register(
      "jungle_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.JUNGLE_SLAB, 0.5F))
   );
   public static final DeferredBlock<DryingRackBlock> ACACIA_DRYING_RACK = register(
      "acacia_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.ACACIA_SLAB, 0.5F))
   );
   public static final DeferredBlock<DryingRackBlock> DARK_OAK_DRYING_RACK = register(
      "dark_oak_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.DARK_OAK_SLAB, 0.5F))
   );
   public static final DeferredBlock<DryingRackBlock> CRIMSON_DRYING_RACK = register(
      "crimson_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.CRIMSON_SLAB, 0.5F))
   );
   public static final DeferredBlock<DryingRackBlock> WARPED_DRYING_RACK = register(
      "warped_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.WARPED_SLAB, 0.5F))
   );
   public static final DeferredBlock<DryingRackBlock> VANGROVE_DRYING_RACK = register(
      "vangrove_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.MANGROVE_SLAB, 0.5F))
   );
   public static final DeferredBlock<DryingRackBlock> BAMBOO_DRYING_RACK = register(
      "bamboo_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.BAMBOO_SLAB, 0.5F))
   );
   public static final DeferredBlock<DryingRackBlock> CHERRY_DRYING_RACK = register(
      "cherry_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties(net.minecraft.world.level.block.Blocks.CHERRY_SLAB, 0.5F))
   );
   public static final Properties TWILIGHT_OAK_LOG_PROPS = logProperties(MapColor.WOOD, MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties CANOPY_LOG_PROPS = logProperties(MapColor.PODZOL, MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties MANGROVE_LOG_PROPS = logProperties(MapColor.DIRT, MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties DARK_LOG_PROPS = logProperties(MapColor.COLOR_BROWN, MapColor.STONE).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties TIME_LOG_PROPS = logProperties(MapColor.DIRT, MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties TRANSFORMATION_LOG_PROPS = logProperties(MapColor.WOOD, MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties MINING_LOG_PROPS = logProperties(MapColor.SAND, MapColor.QUARTZ).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties SORTING_LOG_PROPS = logProperties(MapColor.PODZOL, MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties TWILIGHT_OAK_BARK_PROPS = logProperties(MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties CANOPY_BARK_PROPS = logProperties(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties MANGROVE_BARK_PROPS = logProperties(MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties DARK_BARK_PROPS = logProperties(MapColor.STONE).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties TIME_BARK_PROPS = logProperties(MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties TRANSFORMATION_BARK_PROPS = logProperties(MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties MINING_BARK_PROPS = logProperties(MapColor.QUARTZ).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties SORTING_BARK_PROPS = logProperties(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties TWILIGHT_OAK_STRIPPED_PROPS = logProperties(MapColor.WOOD).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties CANOPY_STRIPPED_PROPS = logProperties(MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties MANGROVE_STRIPPED_PROPS = logProperties(MapColor.DIRT).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties DARK_STRIPPED_PROPS = logProperties(MapColor.COLOR_BROWN).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties TIME_STRIPPED_PROPS = logProperties(MapColor.DIRT).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties TRANSFORMATION_STRIPPED_PROPS = logProperties(MapColor.WOOD).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties MINING_STRIPPED_PROPS = logProperties(MapColor.SAND).strength(2.0F).sound(SoundType.WOOD);
   public static final Properties SORTING_STRIPPED_PROPS = logProperties(MapColor.PODZOL).strength(2.0F).sound(SoundType.WOOD);
   public static final DeferredBlock<RotatedPillarBlock> TWILIGHT_OAK_LOG = register("twilight_oak_log", () -> new RotatedPillarBlock(TWILIGHT_OAK_LOG_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> CANOPY_LOG = register("canopy_log", () -> new RotatedPillarBlock(CANOPY_LOG_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> MANGROVE_LOG = register("mangrove_log", () -> new RotatedPillarBlock(MANGROVE_LOG_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> DARK_LOG = register("dark_log", () -> new RotatedPillarBlock(DARK_LOG_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> TIME_LOG = register("time_log", () -> new RotatedPillarBlock(TIME_LOG_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> TRANSFORMATION_LOG = register(
      "transformation_log", () -> new RotatedPillarBlock(TRANSFORMATION_LOG_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> MINING_LOG = register("mining_log", () -> new RotatedPillarBlock(MINING_LOG_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> SORTING_LOG = register("sorting_log", () -> new RotatedPillarBlock(SORTING_LOG_PROPS));
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_twilight_oak_log_horizontal", () -> new HorizontalHollowLogBlock(TWILIGHT_OAK_BARK_PROPS)
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_CANOPY_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_canopy_log_horizontal", () -> new HorizontalHollowLogBlock(CANOPY_BARK_PROPS)
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_MANGROVE_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_mangrove_log_horizontal", () -> new HorizontalHollowLogBlock(MANGROVE_BARK_PROPS)
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_DARK_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_dark_log_horizontal", () -> new HorizontalHollowLogBlock(DARK_BARK_PROPS)
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_TIME_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_time_log_horizontal", () -> new HorizontalHollowLogBlock(TIME_BARK_PROPS)
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_TRANSFORMATION_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_transformation_log_horizontal", () -> new HorizontalHollowLogBlock(TRANSFORMATION_BARK_PROPS)
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_MINING_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_mining_log_horizontal", () -> new HorizontalHollowLogBlock(MINING_BARK_PROPS)
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_SORTING_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_sorting_log_horizontal", () -> new HorizontalHollowLogBlock(SORTING_BARK_PROPS)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_TWILIGHT_OAK_LOG_VERTICAL = BLOCKS.register(
      "hollow_twilight_oak_log_vertical", () -> new VerticalHollowLogBlock(TWILIGHT_OAK_STRIPPED_PROPS, TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_CANOPY_LOG_VERTICAL = BLOCKS.register(
      "hollow_canopy_log_vertical", () -> new VerticalHollowLogBlock(CANOPY_STRIPPED_PROPS, TFBlocks.HOLLOW_CANOPY_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_MANGROVE_LOG_VERTICAL = BLOCKS.register(
      "hollow_mangrove_log_vertical", () -> new VerticalHollowLogBlock(MANGROVE_STRIPPED_PROPS, TFBlocks.HOLLOW_MANGROVE_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_DARK_LOG_VERTICAL = BLOCKS.register(
      "hollow_dark_log_vertical", () -> new VerticalHollowLogBlock(DARK_STRIPPED_PROPS, TFBlocks.HOLLOW_DARK_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_TIME_LOG_VERTICAL = BLOCKS.register(
      "hollow_time_log_vertical", () -> new VerticalHollowLogBlock(TIME_STRIPPED_PROPS, TFBlocks.HOLLOW_TIME_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_TRANSFORMATION_LOG_VERTICAL = BLOCKS.register(
      "hollow_transformation_log_vertical", () -> new VerticalHollowLogBlock(TRANSFORMATION_STRIPPED_PROPS, TFBlocks.HOLLOW_TRANSFORMATION_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_MINING_LOG_VERTICAL = BLOCKS.register(
      "hollow_mining_log_vertical", () -> new VerticalHollowLogBlock(MINING_STRIPPED_PROPS, TFBlocks.HOLLOW_MINING_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_SORTING_LOG_VERTICAL = BLOCKS.register(
      "hollow_sorting_log_vertical", () -> new VerticalHollowLogBlock(SORTING_STRIPPED_PROPS, TFBlocks.HOLLOW_SORTING_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_twilight_oak_log_climbable", () -> new ClimbableHollowLogBlock(HOLLOW_TWILIGHT_OAK_LOG_VERTICAL, TWILIGHT_OAK_STRIPPED_PROPS)
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_CANOPY_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_canopy_log_climbable", () -> new ClimbableHollowLogBlock(HOLLOW_CANOPY_LOG_VERTICAL, CANOPY_STRIPPED_PROPS)
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_MANGROVE_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_mangrove_log_climbable", () -> new ClimbableHollowLogBlock(HOLLOW_MANGROVE_LOG_VERTICAL, MANGROVE_STRIPPED_PROPS)
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_DARK_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_dark_log_climbable", () -> new ClimbableHollowLogBlock(HOLLOW_DARK_LOG_VERTICAL, DARK_STRIPPED_PROPS)
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_TIME_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_time_log_climbable", () -> new ClimbableHollowLogBlock(HOLLOW_TIME_LOG_VERTICAL, TIME_STRIPPED_PROPS)
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_TRANSFORMATION_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_transformation_log_climbable", () -> new ClimbableHollowLogBlock(HOLLOW_TRANSFORMATION_LOG_VERTICAL, TRANSFORMATION_STRIPPED_PROPS)
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_MINING_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_mining_log_climbable", () -> new ClimbableHollowLogBlock(HOLLOW_MINING_LOG_VERTICAL, MINING_STRIPPED_PROPS)
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_SORTING_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_sorting_log_climbable", () -> new ClimbableHollowLogBlock(HOLLOW_SORTING_LOG_VERTICAL, SORTING_STRIPPED_PROPS)
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_OAK_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_oak_log_horizontal", () -> new HorizontalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_WOOD))
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_SPRUCE_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_spruce_log_horizontal", () -> new HorizontalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SPRUCE_WOOD))
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_BIRCH_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_birch_log_horizontal", () -> new HorizontalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.BIRCH_WOOD))
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_JUNGLE_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_jungle_log_horizontal", () -> new HorizontalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.JUNGLE_WOOD))
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_ACACIA_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_acacia_log_horizontal", () -> new HorizontalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.ACACIA_WOOD))
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_DARK_OAK_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_dark_oak_log_horizontal", () -> new HorizontalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DARK_OAK_WOOD))
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_CRIMSON_STEM_HORIZONTAL = BLOCKS.register(
      "hollow_crimson_stem_horizontal", () -> new HorizontalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.CRIMSON_HYPHAE))
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_WARPED_STEM_HORIZONTAL = BLOCKS.register(
      "hollow_warped_stem_horizontal", () -> new HorizontalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WARPED_HYPHAE))
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_VANGROVE_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_vangrove_log_horizontal", () -> new HorizontalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.MANGROVE_WOOD))
   );
   public static final DeferredBlock<HorizontalHollowLogBlock> HOLLOW_CHERRY_LOG_HORIZONTAL = BLOCKS.register(
      "hollow_cherry_log_horizontal", () -> new HorizontalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.CHERRY_WOOD))
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_OAK_LOG_VERTICAL = BLOCKS.register(
      "hollow_oak_log_vertical",
      () -> new VerticalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_OAK_WOOD), TFBlocks.HOLLOW_OAK_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_SPRUCE_LOG_VERTICAL = BLOCKS.register(
      "hollow_spruce_log_vertical",
      () -> new VerticalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_SPRUCE_WOOD), TFBlocks.HOLLOW_SPRUCE_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_BIRCH_LOG_VERTICAL = BLOCKS.register(
      "hollow_birch_log_vertical",
      () -> new VerticalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_BIRCH_WOOD), TFBlocks.HOLLOW_BIRCH_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_JUNGLE_LOG_VERTICAL = BLOCKS.register(
      "hollow_jungle_log_vertical",
      () -> new VerticalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_JUNGLE_WOOD), TFBlocks.HOLLOW_JUNGLE_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_ACACIA_LOG_VERTICAL = BLOCKS.register(
      "hollow_acacia_log_vertical",
      () -> new VerticalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_ACACIA_WOOD), TFBlocks.HOLLOW_ACACIA_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_DARK_OAK_LOG_VERTICAL = BLOCKS.register(
      "hollow_dark_oak_log_vertical",
      () -> new VerticalHollowLogBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_DARK_OAK_WOOD), TFBlocks.HOLLOW_DARK_OAK_LOG_CLIMBABLE
      )
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_CRIMSON_STEM_VERTICAL = BLOCKS.register(
      "hollow_crimson_stem_vertical",
      () -> new VerticalHollowLogBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_CRIMSON_HYPHAE), TFBlocks.HOLLOW_CRIMSON_STEM_CLIMBABLE
      )
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_WARPED_STEM_VERTICAL = BLOCKS.register(
      "hollow_warped_stem_vertical",
      () -> new VerticalHollowLogBlock(
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_WARPED_HYPHAE), TFBlocks.HOLLOW_WARPED_STEM_CLIMBABLE
      )
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_VANGROVE_LOG_VERTICAL = BLOCKS.register(
      "hollow_vangrove_log_vertical",
      () -> new VerticalHollowLogBlock(
         Properties.of().ignitedByLava().mapColor(MapColor.COLOR_RED).strength(2.0F).sound(SoundType.WOOD), TFBlocks.HOLLOW_VANGROVE_LOG_CLIMBABLE
      )
   );
   public static final DeferredBlock<VerticalHollowLogBlock> HOLLOW_CHERRY_LOG_VERTICAL = BLOCKS.register(
      "hollow_cherry_log_vertical",
      () -> new VerticalHollowLogBlock(Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_CHERRY_WOOD), TFBlocks.HOLLOW_CHERRY_LOG_CLIMBABLE)
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_OAK_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_oak_log_climbable",
      () -> new ClimbableHollowLogBlock(HOLLOW_OAK_LOG_VERTICAL, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_OAK_WOOD))
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_SPRUCE_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_spruce_log_climbable",
      () -> new ClimbableHollowLogBlock(HOLLOW_SPRUCE_LOG_VERTICAL, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_SPRUCE_WOOD))
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_BIRCH_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_birch_log_climbable",
      () -> new ClimbableHollowLogBlock(HOLLOW_BIRCH_LOG_VERTICAL, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_BIRCH_WOOD))
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_JUNGLE_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_jungle_log_climbable",
      () -> new ClimbableHollowLogBlock(HOLLOW_JUNGLE_LOG_VERTICAL, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_JUNGLE_WOOD))
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_ACACIA_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_acacia_log_climbable",
      () -> new ClimbableHollowLogBlock(HOLLOW_ACACIA_LOG_VERTICAL, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_ACACIA_WOOD))
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_DARK_OAK_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_dark_oak_log_climbable",
      () -> new ClimbableHollowLogBlock(HOLLOW_DARK_OAK_LOG_VERTICAL, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_DARK_OAK_WOOD))
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_CRIMSON_STEM_CLIMBABLE = BLOCKS.register(
      "hollow_crimson_stem_climbable",
      () -> new ClimbableHollowLogBlock(HOLLOW_CRIMSON_STEM_VERTICAL, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_CRIMSON_HYPHAE))
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_WARPED_STEM_CLIMBABLE = BLOCKS.register(
      "hollow_warped_stem_climbable",
      () -> new ClimbableHollowLogBlock(HOLLOW_WARPED_STEM_VERTICAL, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_WARPED_HYPHAE))
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_VANGROVE_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_vangrove_log_climbable",
      () -> new ClimbableHollowLogBlock(
         HOLLOW_VANGROVE_LOG_VERTICAL, Properties.of().ignitedByLava().mapColor(MapColor.COLOR_RED).strength(2.0F).sound(SoundType.WOOD)
      )
   );
   public static final DeferredBlock<ClimbableHollowLogBlock> HOLLOW_CHERRY_LOG_CLIMBABLE = BLOCKS.register(
      "hollow_cherry_log_climbable",
      () -> new ClimbableHollowLogBlock(HOLLOW_CHERRY_LOG_VERTICAL, Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.STRIPPED_CHERRY_WOOD))
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_TWILIGHT_OAK_LOG = register(
      "stripped_twilight_oak_log", () -> new RotatedPillarBlock(TWILIGHT_OAK_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_CANOPY_LOG = register(
      "stripped_canopy_log", () -> new RotatedPillarBlock(CANOPY_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MANGROVE_LOG = register(
      "stripped_mangrove_log", () -> new RotatedPillarBlock(MANGROVE_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_DARK_LOG = register("stripped_dark_log", () -> new RotatedPillarBlock(DARK_STRIPPED_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_TIME_LOG = register("stripped_time_log", () -> new RotatedPillarBlock(TIME_STRIPPED_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_TRANSFORMATION_LOG = register(
      "stripped_transformation_log", () -> new RotatedPillarBlock(TRANSFORMATION_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MINING_LOG = register(
      "stripped_mining_log", () -> new RotatedPillarBlock(MINING_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SORTING_LOG = register(
      "stripped_sorting_log", () -> new RotatedPillarBlock(SORTING_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> TWILIGHT_OAK_WOOD = register(
      "twilight_oak_wood", () -> new RotatedPillarBlock(TWILIGHT_OAK_BARK_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> CANOPY_WOOD = register("canopy_wood", () -> new RotatedPillarBlock(CANOPY_BARK_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> MANGROVE_WOOD = register("mangrove_wood", () -> new RotatedPillarBlock(MANGROVE_BARK_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> DARK_WOOD = register("dark_wood", () -> new RotatedPillarBlock(DARK_BARK_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> TIME_WOOD = register("time_wood", () -> new RotatedPillarBlock(TIME_BARK_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> TRANSFORMATION_WOOD = register(
      "transformation_wood", () -> new RotatedPillarBlock(TRANSFORMATION_BARK_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> MINING_WOOD = register("mining_wood", () -> new RotatedPillarBlock(MINING_BARK_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> SORTING_WOOD = register("sorting_wood", () -> new RotatedPillarBlock(SORTING_BARK_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_TWILIGHT_OAK_WOOD = register(
      "stripped_twilight_oak_wood", () -> new RotatedPillarBlock(TWILIGHT_OAK_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_CANOPY_WOOD = register(
      "stripped_canopy_wood", () -> new RotatedPillarBlock(CANOPY_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MANGROVE_WOOD = register(
      "stripped_mangrove_wood", () -> new RotatedPillarBlock(MANGROVE_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_DARK_WOOD = register("stripped_dark_wood", () -> new RotatedPillarBlock(DARK_STRIPPED_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_TIME_WOOD = register("stripped_time_wood", () -> new RotatedPillarBlock(TIME_STRIPPED_PROPS));
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_TRANSFORMATION_WOOD = register(
      "stripped_transformation_wood", () -> new RotatedPillarBlock(TRANSFORMATION_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MINING_WOOD = register(
      "stripped_mining_wood", () -> new RotatedPillarBlock(MINING_STRIPPED_PROPS)
   );
   public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SORTING_WOOD = register(
      "stripped_sorting_wood", () -> new RotatedPillarBlock(SORTING_STRIPPED_PROPS)
   );
   public static final DeferredBlock<Block> TIME_LOG_CORE = register("time_log_core", () -> new TimeLogCoreBlock(TIME_LOG_PROPS));
   public static final DeferredBlock<Block> TRANSFORMATION_LOG_CORE = register("transformation_log_core", () -> new TransLogCoreBlock(TRANSFORMATION_LOG_PROPS));
   public static final DeferredBlock<Block> MINING_LOG_CORE = register("mining_log_core", () -> new MineLogCoreBlock(MINING_LOG_PROPS));
   public static final DeferredBlock<Block> SORTING_LOG_CORE = register("sorting_log_core", () -> new SortLogCoreBlock(SORTING_LOG_PROPS));
   public static final DeferredBlock<Block> MANGROVE_ROOT = register(
      "mangrove_root",
      () -> new Block(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.STONE).sound(SoundType.WOOD).strength(2.0F))
   );
   public static final DeferredBlock<Block> TWILIGHT_OAK_LEAVES = register(
      "twilight_oak_leaves",
      () -> new LeavesBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .strength(0.2F)
            .randomTicks()
            .noOcclusion()
            .sound(SoundType.AZALEA_LEAVES)
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<Block> CANOPY_LEAVES = register(
      "canopy_leaves",
      () -> new LeavesBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .strength(0.2F)
            .randomTicks()
            .noOcclusion()
            .sound(SoundType.AZALEA_LEAVES)
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<Block> MANGROVE_LEAVES = register(
      "mangrove_leaves",
      () -> new LeavesBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .strength(0.2F)
            .randomTicks()
            .noOcclusion()
            .sound(SoundType.AZALEA_LEAVES)
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<Block> DARK_LEAVES = register(
      "dark_leaves",
      () -> new DarkLeavesBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .strength(2.0F, 10.0F)
            .sound(SoundType.AZALEA_LEAVES)
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<Block> HARDENED_DARK_LEAVES = BLOCKS.register(
      "hardened_dark_leaves",
      () -> new HardenedDarkLeavesBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .strength(2.0F, 10.0F)
            .sound(SoundType.AZALEA_LEAVES)
            .isValidSpawn(TFBlocks::noSpawning)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<Block> RAINBOW_OAK_LEAVES = register(
      "rainbow_oak_leaves",
      () -> new LeavesBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .strength(0.2F)
            .randomTicks()
            .noOcclusion()
            .sound(SoundType.AZALEA_LEAVES)
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<Block> TIME_LEAVES = register(
      "time_leaves",
      () -> new LeavesBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .strength(0.2F)
            .sound(SoundType.AZALEA_LEAVES)
            .randomTicks()
            .noOcclusion()
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<Block> TRANSFORMATION_LEAVES = register(
      "transformation_leaves",
      () -> new TransformationLeavesBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .strength(0.2F)
            .sound(SoundType.AZALEA_LEAVES)
            .randomTicks()
            .noOcclusion()
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<Block> MINING_LEAVES = register(
      "mining_leaves",
      () -> new LeavesBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .strength(0.2F)
            .sound(SoundType.AZALEA_LEAVES)
            .randomTicks()
            .noOcclusion()
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<Block> SORTING_LEAVES = register(
      "sorting_leaves",
      () -> new LeavesBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .strength(0.2F)
            .sound(SoundType.AZALEA_LEAVES)
            .randomTicks()
            .noOcclusion()
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false)
      )
   );
   public static final DeferredBlock<SaplingBlock> TWILIGHT_OAK_SAPLING = register(
      "twilight_oak_sapling",
      () -> new SaplingBlock(
         TFTreeGrowers.TWILIGHT_OAK,
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()
      )
   );
   public static final DeferredBlock<SaplingBlock> CANOPY_SAPLING = register(
      "canopy_sapling",
      () -> new SaplingBlock(
         TFTreeGrowers.CANOPY,
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()
      )
   );
   public static final DeferredBlock<SaplingBlock> MANGROVE_SAPLING = register(
      "mangrove_sapling",
      () -> new MangroveSaplingBlock(
         TFTreeGrowers.MANGROVE,
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()
      )
   );
   public static final DeferredBlock<SaplingBlock> DARKWOOD_SAPLING = register(
      "darkwood_sapling",
      () -> new SaplingBlock(
         TFTreeGrowers.DARK,
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()
      )
   );
   public static final DeferredBlock<SaplingBlock> HOLLOW_OAK_SAPLING = register(
      "hollow_oak_sapling",
      () -> new SaplingBlock(
         TFTreeGrowers.HOLLOW_OAK,
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()
      )
   );
   public static final DeferredBlock<SaplingBlock> TIME_SAPLING = register(
      "time_sapling",
      () -> new SaplingBlock(
         TFTreeGrowers.TIME,
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()
      )
   );
   public static final DeferredBlock<SaplingBlock> TRANSFORMATION_SAPLING = register(
      "transformation_sapling",
      () -> new SaplingBlock(
         TFTreeGrowers.TRANSFORMATION,
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()
      )
   );
   public static final DeferredBlock<SaplingBlock> MINING_SAPLING = register(
      "mining_sapling",
      () -> new SaplingBlock(
         TFTreeGrowers.MINING,
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()
      )
   );
   public static final DeferredBlock<SaplingBlock> SORTING_SAPLING = register(
      "sorting_sapling",
      () -> new SaplingBlock(
         TFTreeGrowers.SORTING,
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()
      )
   );
   public static final DeferredBlock<SaplingBlock> RAINBOW_OAK_SAPLING = register(
      "rainbow_oak_sapling",
      () -> new SaplingBlock(
         TFTreeGrowers.RAINBOW_OAK,
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()
      )
   );
   public static final DeferredBlock<Block> TWILIGHT_OAK_PLANKS = register(
      "twilight_oak_planks",
      () -> new Block(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD))
   );
   public static final DeferredBlock<StairBlock> TWILIGHT_OAK_STAIRS = register(
      "twilight_oak_stairs",
      () -> new StairBlock(((Block)TWILIGHT_OAK_PLANKS.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()))
   );
   public static final DeferredBlock<Block> TWILIGHT_OAK_SLAB = register(
      "twilight_oak_slab", () -> new SlabBlock(Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()))
   );
   public static final DeferredBlock<Block> TWILIGHT_OAK_BUTTON = register(
      "twilight_oak_button",
      () -> new ButtonBlock(TFWoodTypes.TWILIGHT_OAK_SET, 30, Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).noCollission().strength(0.5F))
   );
   public static final DeferredBlock<Block> TWILIGHT_OAK_FENCE = register(
      "twilight_oak_fence", () -> new FenceBlock(Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()))
   );
   public static final DeferredBlock<Block> TWILIGHT_OAK_GATE = register(
      "twilight_oak_fence_gate",
      () -> new FenceGateBlock(TFWoodTypes.TWILIGHT_OAK_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).forceSolidOn())
   );
   public static final DeferredBlock<Block> TWILIGHT_OAK_PLATE = register(
      "twilight_oak_pressure_plate",
      () -> new PressurePlateBlock(
         TFWoodTypes.TWILIGHT_OAK_SET, Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).forceSolidOn().noCollission().strength(0.5F)
      )
   );
   public static final DeferredBlock<DoorBlock> TWILIGHT_OAK_DOOR = registerDoubleBlockItem(
      "twilight_oak_door",
      () -> new DoorBlock(
         TFWoodTypes.TWILIGHT_OAK_SET, Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).strength(3.0F).sound(SoundType.WOOD).noOcclusion()
      )
   );
   public static final DeferredBlock<TrapDoorBlock> TWILIGHT_OAK_TRAPDOOR = register(
      "twilight_oak_trapdoor",
      () -> new TrapDoorBlock(TFWoodTypes.TWILIGHT_OAK_SET, Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<StandingSignBlock> TWILIGHT_OAK_SIGN = BLOCKS.register(
      "twilight_oak_sign",
      () -> new StandingSignBlock(
         TFWoodTypes.TWILIGHT_OAK_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).strength(3.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<WallSignBlock> TWILIGHT_WALL_SIGN = BLOCKS.register(
      "twilight_wall_sign",
      () -> new WallSignBlock(
         TFWoodTypes.TWILIGHT_OAK_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).strength(3.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<CeilingHangingSignBlock> TWILIGHT_OAK_HANGING_SIGN = BLOCKS.register(
      "twilight_oak_hanging_sign",
      () -> new CeilingHangingSignBlock(
         TFWoodTypes.TWILIGHT_OAK_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).noCollission().strength(1.0F)
      )
   );
   public static final DeferredBlock<WallHangingSignBlock> TWILIGHT_OAK_WALL_HANGING_SIGN = BLOCKS.register(
      "twilight_oak_wall_hanging_sign",
      () -> new WallHangingSignBlock(
         TFWoodTypes.TWILIGHT_OAK_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).noCollission().strength(1.0F)
      )
   );
   public static final DeferredBlock<BanisterBlock> TWILIGHT_OAK_BANISTER = register(
      "twilight_oak_banister", () -> new BanisterBlock(Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()))
   );
   public static final DeferredBlock<DryingRackBlock> TWILIGHT_OAK_DRYING_RACK = register(
      "twilight_oak_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties((BlockBehaviour)TWILIGHT_OAK_SLAB.get(), 0.5F))
   );
   public static final DeferredBlock<Block> CANOPY_PLANKS = register(
      "canopy_planks",
      () -> new Block(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.PODZOL).strength(2.0F, 3.0F).sound(SoundType.WOOD))
   );
   public static final DeferredBlock<StairBlock> CANOPY_STAIRS = register(
      "canopy_stairs", () -> new StairBlock(((Block)CANOPY_PLANKS.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()))
   );
   public static final DeferredBlock<Block> CANOPY_SLAB = register(
      "canopy_slab", () -> new SlabBlock(Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()))
   );
   public static final DeferredBlock<Block> CANOPY_BUTTON = register(
      "canopy_button",
      () -> new ButtonBlock(TFWoodTypes.CANOPY_WOOD_SET, 30, Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).noCollission().strength(0.5F))
   );
   public static final DeferredBlock<Block> CANOPY_FENCE = register(
      "canopy_fence", () -> new FenceBlock(Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()))
   );
   public static final DeferredBlock<Block> CANOPY_GATE = register(
      "canopy_fence_gate", () -> new FenceGateBlock(TFWoodTypes.CANOPY_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).forceSolidOn())
   );
   public static final DeferredBlock<Block> CANOPY_PLATE = register(
      "canopy_pressure_plate",
      () -> new PressurePlateBlock(
         TFWoodTypes.CANOPY_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).forceSolidOn().noCollission().strength(0.5F)
      )
   );
   public static final DeferredBlock<DoorBlock> CANOPY_DOOR = registerDoubleBlockItem(
      "canopy_door", () -> new DoorBlock(TFWoodTypes.CANOPY_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<TrapDoorBlock> CANOPY_TRAPDOOR = register(
      "canopy_trapdoor",
      () -> new TrapDoorBlock(
         TFWoodTypes.CANOPY_WOOD_SET, Properties.of().ignitedByLava().mapColor(MapColor.SAND).strength(3.0F).sound(SoundType.WOOD).noOcclusion()
      )
   );
   public static final DeferredBlock<StandingSignBlock> CANOPY_SIGN = BLOCKS.register(
      "canopy_sign",
      () -> new StandingSignBlock(
         TFWoodTypes.CANOPY_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<WallSignBlock> CANOPY_WALL_SIGN = BLOCKS.register(
      "canopy_wall_sign",
      () -> new WallSignBlock(
         TFWoodTypes.CANOPY_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<Block> CANOPY_BOOKSHELF = register(
      "canopy_bookshelf", () -> new Block(Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).strength(1.5F))
   );
   public static final DeferredBlock<CeilingHangingSignBlock> CANOPY_HANGING_SIGN = BLOCKS.register(
      "canopy_hanging_sign",
      () -> new CeilingHangingSignBlock(TFWoodTypes.CANOPY_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).noCollission().strength(1.0F))
   );
   public static final DeferredBlock<WallHangingSignBlock> CANOPY_WALL_HANGING_SIGN = BLOCKS.register(
      "canopy_wall_hanging_sign",
      () -> new WallHangingSignBlock(TFWoodTypes.CANOPY_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).noCollission().strength(1.0F))
   );
   public static final DeferredBlock<BanisterBlock> CANOPY_BANISTER = register(
      "canopy_banister", () -> new BanisterBlock(Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()))
   );
   public static final DeferredBlock<DryingRackBlock> CANOPY_DRYING_RACK = register(
      "canopy_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties((BlockBehaviour)CANOPY_SLAB.get(), 0.5F))
   );
   public static final DeferredBlock<Block> MANGROVE_PLANKS = register(
      "mangrove_planks",
      () -> new Block(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.DIRT).strength(2.0F, 3.0F).sound(SoundType.WOOD))
   );
   public static final DeferredBlock<StairBlock> MANGROVE_STAIRS = register(
      "mangrove_stairs", () -> new StairBlock(((Block)MANGROVE_PLANKS.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()))
   );
   public static final DeferredBlock<Block> MANGROVE_SLAB = register(
      "mangrove_slab", () -> new SlabBlock(Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()))
   );
   public static final DeferredBlock<Block> MANGROVE_BUTTON = register(
      "mangrove_button",
      () -> new ButtonBlock(TFWoodTypes.MANGROVE_WOOD_SET, 30, Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).noCollission().strength(0.5F))
   );
   public static final DeferredBlock<Block> MANGROVE_FENCE = register(
      "mangrove_fence", () -> new FenceBlock(Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()))
   );
   public static final DeferredBlock<Block> MANGROVE_GATE = register(
      "mangrove_fence_gate",
      () -> new FenceGateBlock(TFWoodTypes.MANGROVE_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).forceSolidOn())
   );
   public static final DeferredBlock<Block> MANGROVE_PLATE = register(
      "mangrove_pressure_plate",
      () -> new PressurePlateBlock(
         TFWoodTypes.MANGROVE_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).forceSolidOn().noCollission().strength(0.5F)
      )
   );
   public static final DeferredBlock<DoorBlock> MANGROVE_DOOR = registerDoubleBlockItem(
      "mangrove_door",
      () -> new DoorBlock(TFWoodTypes.MANGROVE_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<TrapDoorBlock> MANGROVE_TRAPDOOR = register(
      "mangrove_trapdoor",
      () -> new TrapDoorBlock(TFWoodTypes.MANGROVE_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<StandingSignBlock> MANGROVE_SIGN = BLOCKS.register(
      "mangrove_sign",
      () -> new StandingSignBlock(
         TFWoodTypes.MANGROVE_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<WallSignBlock> MANGROVE_WALL_SIGN = BLOCKS.register(
      "mangrove_wall_sign",
      () -> new WallSignBlock(
         TFWoodTypes.MANGROVE_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<CeilingHangingSignBlock> MANGROVE_HANGING_SIGN = BLOCKS.register(
      "mangrove_hanging_sign",
      () -> new CeilingHangingSignBlock(
         TFWoodTypes.MANGROVE_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).noCollission().strength(1.0F)
      )
   );
   public static final DeferredBlock<WallHangingSignBlock> MANGROVE_WALL_HANGING_SIGN = BLOCKS.register(
      "mangrove_wall_hanging_sign",
      () -> new WallHangingSignBlock(TFWoodTypes.MANGROVE_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).noCollission().strength(1.0F))
   );
   public static final DeferredBlock<BanisterBlock> MANGROVE_BANISTER = register(
      "mangrove_banister", () -> new BanisterBlock(Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()))
   );
   public static final DeferredBlock<DryingRackBlock> MANGROVE_DRYING_RACK = register(
      "mangrove_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties((BlockBehaviour)MANGROVE_SLAB.get(), 0.5F))
   );
   public static final DeferredBlock<Block> DARK_PLANKS = register(
      "dark_planks",
      () -> new Block(
         Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_ORANGE).strength(2.0F, 3.0F).sound(SoundType.WOOD)
      )
   );
   public static final DeferredBlock<StairBlock> DARK_STAIRS = register(
      "dark_stairs", () -> new StairBlock(((Block)DARK_PLANKS.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()))
   );
   public static final DeferredBlock<Block> DARK_SLAB = register(
      "dark_slab", () -> new SlabBlock(Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).sound(SoundType.WOOD))
   );
   public static final DeferredBlock<Block> DARK_BUTTON = register(
      "dark_button",
      () -> new ButtonBlock(TFWoodTypes.DARK_WOOD_SET, 30, Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).noCollission().strength(0.5F))
   );
   public static final DeferredBlock<Block> DARK_FENCE = register("dark_fence", () -> new FenceBlock(Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get())));
   public static final DeferredBlock<Block> DARK_GATE = register(
      "dark_fence_gate", () -> new FenceGateBlock(TFWoodTypes.DARK_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).forceSolidOn())
   );
   public static final DeferredBlock<Block> DARK_PLATE = register(
      "dark_pressure_plate",
      () -> new PressurePlateBlock(
         TFWoodTypes.DARK_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).forceSolidOn().noCollission().strength(0.5F)
      )
   );
   public static final DeferredBlock<DoorBlock> DARK_DOOR = registerDoubleBlockItem(
      "dark_door",
      () -> new DoorBlock(
         TFWoodTypes.DARK_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).strength(3.0F).sound(SoundType.WOOD).noOcclusion()
      )
   );
   public static final DeferredBlock<TrapDoorBlock> DARK_TRAPDOOR = register(
      "dark_trapdoor",
      () -> new TrapDoorBlock(TFWoodTypes.DARK_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<StandingSignBlock> DARK_SIGN = BLOCKS.register(
      "dark_sign",
      () -> new StandingSignBlock(
         TFWoodTypes.DARK_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<WallSignBlock> DARK_WALL_SIGN = BLOCKS.register(
      "dark_wall_sign",
      () -> new WallSignBlock(TFWoodTypes.DARK_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).strength(1.0F).noOcclusion().noCollission())
   );
   public static final DeferredBlock<CeilingHangingSignBlock> DARK_HANGING_SIGN = BLOCKS.register(
      "dark_hanging_sign",
      () -> new CeilingHangingSignBlock(TFWoodTypes.DARK_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).noCollission().strength(1.0F))
   );
   public static final DeferredBlock<WallHangingSignBlock> DARK_WALL_HANGING_SIGN = BLOCKS.register(
      "dark_wall_hanging_sign",
      () -> new WallHangingSignBlock(TFWoodTypes.DARK_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).noCollission().strength(1.0F))
   );
   public static final DeferredBlock<BanisterBlock> DARK_BANISTER = register(
      "dark_banister", () -> new BanisterBlock(Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()))
   );
   public static final DeferredBlock<DryingRackBlock> DARK_DRYING_RACK = register(
      "dark_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties((BlockBehaviour)DARK_SLAB.get(), 0.5F))
   );
   public static final DeferredBlock<Block> TIME_PLANKS = register(
      "time_planks",
      () -> new Block(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.DIRT).strength(2.0F, 3.0F).sound(SoundType.WOOD))
   );
   public static final DeferredBlock<StairBlock> TIME_STAIRS = register(
      "time_stairs", () -> new StairBlock(((Block)TIME_PLANKS.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()))
   );
   public static final DeferredBlock<Block> TIME_SLAB = register(
      "time_slab", () -> new SlabBlock(Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).sound(SoundType.WOOD))
   );
   public static final DeferredBlock<Block> TIME_BUTTON = register(
      "time_button",
      () -> new ButtonBlock(TFWoodTypes.TIME_WOOD_SET, 30, Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).noCollission().strength(0.5F))
   );
   public static final DeferredBlock<Block> TIME_FENCE = register("time_fence", () -> new FenceBlock(Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get())));
   public static final DeferredBlock<Block> TIME_GATE = register(
      "time_fence_gate", () -> new FenceGateBlock(TFWoodTypes.TIME_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).forceSolidOn())
   );
   public static final DeferredBlock<Block> TIME_PLATE = register(
      "time_pressure_plate",
      () -> new PressurePlateBlock(
         TFWoodTypes.TIME_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).forceSolidOn().noCollission().strength(0.5F)
      )
   );
   public static final DeferredBlock<DoorBlock> TIME_DOOR = registerDoubleBlockItem(
      "time_door",
      () -> new DoorBlock(
         TFWoodTypes.TIME_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).strength(3.0F).sound(SoundType.WOOD).noOcclusion()
      )
   );
   public static final DeferredBlock<TrapDoorBlock> TIME_TRAPDOOR = register(
      "time_trapdoor",
      () -> new TrapDoorBlock(TFWoodTypes.TIME_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<StandingSignBlock> TIME_SIGN = BLOCKS.register(
      "time_sign",
      () -> new StandingSignBlock(
         TFWoodTypes.TIME_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<WallSignBlock> TIME_WALL_SIGN = BLOCKS.register(
      "time_wall_sign",
      () -> new WallSignBlock(TFWoodTypes.TIME_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).strength(1.0F).noOcclusion().noCollission())
   );
   public static final DeferredBlock<CeilingHangingSignBlock> TIME_HANGING_SIGN = BLOCKS.register(
      "time_hanging_sign",
      () -> new CeilingHangingSignBlock(TFWoodTypes.TIME_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).noCollission().strength(1.0F))
   );
   public static final DeferredBlock<WallHangingSignBlock> TIME_WALL_HANGING_SIGN = BLOCKS.register(
      "time_wall_hanging_sign",
      () -> new WallHangingSignBlock(TFWoodTypes.TIME_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).noCollission().strength(1.0F))
   );
   public static final DeferredBlock<BanisterBlock> TIME_BANISTER = register(
      "time_banister", () -> new BanisterBlock(Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()))
   );
   public static final DeferredBlock<DryingRackBlock> TIME_DRYING_RACK = register(
      "time_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties((BlockBehaviour)TIME_SLAB.get(), 0.5F))
   );
   public static final DeferredBlock<Block> TRANSFORMATION_PLANKS = register(
      "transformation_planks",
      () -> new Block(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD))
   );
   public static final DeferredBlock<StairBlock> TRANSFORMATION_STAIRS = register(
      "transformation_stairs",
      () -> new StairBlock(((Block)TRANSFORMATION_PLANKS.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()))
   );
   public static final DeferredBlock<Block> TRANSFORMATION_SLAB = register(
      "transformation_slab", () -> new SlabBlock(Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()))
   );
   public static final DeferredBlock<Block> TRANSFORMATION_BUTTON = register(
      "transformation_button",
      () -> new ButtonBlock(
         TFWoodTypes.TRANSFORMATION_WOOD_SET, 30, Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).noCollission().strength(0.5F)
      )
   );
   public static final DeferredBlock<Block> TRANSFORMATION_FENCE = register(
      "transformation_fence", () -> new FenceBlock(Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()))
   );
   public static final DeferredBlock<Block> TRANSFORMATION_GATE = register(
      "transformation_fence_gate",
      () -> new FenceGateBlock(TFWoodTypes.TRANSFORMATION_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).forceSolidOn())
   );
   public static final DeferredBlock<Block> TRANSFORMATION_PLATE = register(
      "transformation_pressure_plate",
      () -> new PressurePlateBlock(
         TFWoodTypes.TRANSFORMATION_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).forceSolidOn().noCollission().strength(0.5F)
      )
   );
   public static final DeferredBlock<DoorBlock> TRANSFORMATION_DOOR = registerDoubleBlockItem(
      "transformation_door",
      () -> new DoorBlock(TFWoodTypes.TRANSFORMATION_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<TrapDoorBlock> TRANSFORMATION_TRAPDOOR = register(
      "transformation_trapdoor",
      () -> new TrapDoorBlock(
         TFWoodTypes.TRANSFORMATION_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).strength(3.0F).noOcclusion()
      )
   );
   public static final DeferredBlock<StandingSignBlock> TRANSFORMATION_SIGN = BLOCKS.register(
      "transformation_sign",
      () -> new StandingSignBlock(
         TFWoodTypes.TRANSFORMATION_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<WallSignBlock> TRANSFORMATION_WALL_SIGN = BLOCKS.register(
      "transformation_wall_sign",
      () -> new WallSignBlock(
         TFWoodTypes.TRANSFORMATION_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<CeilingHangingSignBlock> TRANSFORMATION_HANGING_SIGN = BLOCKS.register(
      "transformation_hanging_sign",
      () -> new CeilingHangingSignBlock(
         TFWoodTypes.TRANSFORMATION_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).noCollission().strength(1.0F)
      )
   );
   public static final DeferredBlock<WallHangingSignBlock> TRANSFORMATION_WALL_HANGING_SIGN = BLOCKS.register(
      "transformation_wall_hanging_sign",
      () -> new WallHangingSignBlock(
         TFWoodTypes.TRANSFORMATION_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).noCollission().strength(1.0F)
      )
   );
   public static final DeferredBlock<BanisterBlock> TRANSFORMATION_BANISTER = register(
      "transformation_banister", () -> new BanisterBlock(Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()))
   );
   public static final DeferredBlock<DryingRackBlock> TRANSFORMATION_DRYING_RACK = register(
      "transformation_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties((BlockBehaviour)TRANSFORMATION_SLAB.get(), 0.5F))
   );
   public static final DeferredBlock<Block> MINING_PLANKS = register(
      "mining_planks",
      () -> new Block(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.SAND).strength(2.0F, 3.0F).sound(SoundType.WOOD))
   );
   public static final DeferredBlock<StairBlock> MINING_STAIRS = register(
      "mining_stairs", () -> new StairBlock(((Block)MINING_PLANKS.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()))
   );
   public static final DeferredBlock<Block> MINING_SLAB = register(
      "mining_slab", () -> new SlabBlock(Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()))
   );
   public static final DeferredBlock<Block> MINING_BUTTON = register(
      "mining_button",
      () -> new ButtonBlock(TFWoodTypes.MINING_WOOD_SET, 30, Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).noCollission().strength(0.5F))
   );
   public static final DeferredBlock<Block> MINING_FENCE = register(
      "mining_fence", () -> new FenceBlock(Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()))
   );
   public static final DeferredBlock<Block> MINING_GATE = register(
      "mining_fence_gate", () -> new FenceGateBlock(TFWoodTypes.MINING_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).forceSolidOn())
   );
   public static final DeferredBlock<Block> MINING_PLATE = register(
      "mining_pressure_plate",
      () -> new PressurePlateBlock(
         TFWoodTypes.MINING_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).forceSolidOn().noCollission().strength(0.5F)
      )
   );
   public static final DeferredBlock<DoorBlock> MINING_DOOR = registerDoubleBlockItem(
      "mining_door", () -> new DoorBlock(TFWoodTypes.MINING_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<TrapDoorBlock> MINING_TRAPDOOR = register(
      "mining_trapdoor",
      () -> new TrapDoorBlock(TFWoodTypes.MINING_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<StandingSignBlock> MINING_SIGN = BLOCKS.register(
      "mining_sign",
      () -> new StandingSignBlock(
         TFWoodTypes.MINING_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<WallSignBlock> MINING_WALL_SIGN = BLOCKS.register(
      "mining_wall_sign",
      () -> new WallSignBlock(
         TFWoodTypes.MINING_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<CeilingHangingSignBlock> MINING_HANGING_SIGN = BLOCKS.register(
      "mining_hanging_sign",
      () -> new CeilingHangingSignBlock(TFWoodTypes.MINING_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).noCollission().strength(1.0F))
   );
   public static final DeferredBlock<WallHangingSignBlock> MINING_WALL_HANGING_SIGN = BLOCKS.register(
      "mining_wall_hanging_sign",
      () -> new WallHangingSignBlock(TFWoodTypes.MINING_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).noCollission().strength(1.0F))
   );
   public static final DeferredBlock<BanisterBlock> MINING_BANISTER = register(
      "mining_banister", () -> new BanisterBlock(Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()))
   );
   public static final DeferredBlock<DryingRackBlock> MINING_DRYING_RACK = register(
      "mining_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties((BlockBehaviour)MINING_SLAB.get(), 0.5F))
   );
   public static final DeferredBlock<Block> SORTING_PLANKS = register(
      "sorting_planks",
      () -> new Block(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.PODZOL).strength(2.0F, 3.0F).sound(SoundType.WOOD))
   );
   public static final DeferredBlock<StairBlock> SORTING_STAIRS = register(
      "sorting_stairs", () -> new StairBlock(((Block)SORTING_PLANKS.get()).defaultBlockState(), Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()))
   );
   public static final DeferredBlock<Block> SORTING_SLAB = register(
      "sorting_slab", () -> new SlabBlock(Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()))
   );
   public static final DeferredBlock<Block> SORTING_BUTTON = register(
      "sorting_button",
      () -> new ButtonBlock(TFWoodTypes.SORTING_WOOD_SET, 30, Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).noCollission().strength(0.5F))
   );
   public static final DeferredBlock<Block> SORTING_FENCE = register(
      "sorting_fence", () -> new FenceBlock(Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()))
   );
   public static final DeferredBlock<Block> SORTING_GATE = register(
      "sorting_fence_gate", () -> new FenceGateBlock(TFWoodTypes.SORTING_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).forceSolidOn())
   );
   public static final DeferredBlock<Block> SORTING_PLATE = register(
      "sorting_pressure_plate",
      () -> new PressurePlateBlock(
         TFWoodTypes.SORTING_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).forceSolidOn().noCollission().strength(0.5F)
      )
   );
   public static final DeferredBlock<DoorBlock> SORTING_DOOR = registerDoubleBlockItem(
      "sorting_door",
      () -> new DoorBlock(TFWoodTypes.SORTING_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<TrapDoorBlock> SORTING_TRAPDOOR = register(
      "sorting_trapdoor",
      () -> new TrapDoorBlock(TFWoodTypes.SORTING_WOOD_SET, Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).strength(3.0F).noOcclusion())
   );
   public static final DeferredBlock<StandingSignBlock> SORTING_SIGN = BLOCKS.register(
      "sorting_sign",
      () -> new StandingSignBlock(
         TFWoodTypes.SORTING_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<WallSignBlock> SORTING_WALL_SIGN = BLOCKS.register(
      "sorting_wall_sign",
      () -> new WallSignBlock(
         TFWoodTypes.SORTING_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).strength(1.0F).noOcclusion().noCollission()
      )
   );
   public static final DeferredBlock<CeilingHangingSignBlock> SORTING_HANGING_SIGN = BLOCKS.register(
      "sorting_hanging_sign",
      () -> new CeilingHangingSignBlock(
         TFWoodTypes.SORTING_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).noCollission().strength(1.0F)
      )
   );
   public static final DeferredBlock<WallHangingSignBlock> SORTING_WALL_HANGING_SIGN = BLOCKS.register(
      "sorting_wall_hanging_sign",
      () -> new WallHangingSignBlock(TFWoodTypes.SORTING_WOOD_TYPE, Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).noCollission().strength(1.0F))
   );
   public static final DeferredBlock<BanisterBlock> SORTING_BANISTER = register(
      "sorting_banister", () -> new BanisterBlock(Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()))
   );
   public static final DeferredBlock<DryingRackBlock> SORTING_DRYING_RACK = register(
      "sorting_drying_rack", () -> new DryingRackBlock(copyAndScaleProperties((BlockBehaviour)SORTING_SLAB.get(), 0.5F))
   );
   public static final DeferredBlock<ChestBlock> TWILIGHT_OAK_CHEST = register(
      "twilight_oak_chest", () -> new ChestBlock(Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).strength(2.5F), () -> BlockEntityType.CHEST)
   );
   public static final DeferredBlock<ChestBlock> CANOPY_CHEST = register(
      "canopy_chest", () -> new ChestBlock(Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).strength(2.5F), () -> BlockEntityType.CHEST)
   );
   public static final DeferredBlock<ChestBlock> MANGROVE_CHEST = register(
      "mangrove_chest", () -> new ChestBlock(Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).strength(2.5F), () -> BlockEntityType.CHEST)
   );
   public static final DeferredBlock<ChestBlock> DARK_CHEST = register(
      "dark_chest", () -> new ChestBlock(Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).strength(2.5F), () -> BlockEntityType.CHEST)
   );
   public static final DeferredBlock<ChestBlock> TIME_CHEST = register(
      "time_chest", () -> new ChestBlock(Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).strength(2.5F), () -> BlockEntityType.CHEST)
   );
   public static final DeferredBlock<ChestBlock> TRANSFORMATION_CHEST = register(
      "transformation_chest",
      () -> new ChestBlock(Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).strength(2.5F), () -> BlockEntityType.CHEST)
   );
   public static final DeferredBlock<ChestBlock> MINING_CHEST = register(
      "mining_chest", () -> new ChestBlock(Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).strength(2.5F), () -> BlockEntityType.CHEST)
   );
   public static final DeferredBlock<ChestBlock> SORTING_CHEST = register(
      "sorting_chest", () -> new ChestBlock(Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).strength(2.5F), () -> BlockEntityType.CHEST)
   );
   public static final DeferredBlock<TrappedChestBlock> TWILIGHT_OAK_TRAPPED_CHEST = register(
      "twilight_oak_trapped_chest", () -> new TrappedChestBlock(Properties.ofFullCopy((BlockBehaviour)TWILIGHT_OAK_PLANKS.get()).strength(2.5F))
   );
   public static final DeferredBlock<TrappedChestBlock> CANOPY_TRAPPED_CHEST = register(
      "canopy_trapped_chest", () -> new TrappedChestBlock(Properties.ofFullCopy((BlockBehaviour)CANOPY_PLANKS.get()).strength(2.5F))
   );
   public static final DeferredBlock<TrappedChestBlock> MANGROVE_TRAPPED_CHEST = register(
      "mangrove_trapped_chest", () -> new TrappedChestBlock(Properties.ofFullCopy((BlockBehaviour)MANGROVE_PLANKS.get()).strength(2.5F))
   );
   public static final DeferredBlock<TrappedChestBlock> DARK_TRAPPED_CHEST = register(
      "dark_trapped_chest", () -> new TrappedChestBlock(Properties.ofFullCopy((BlockBehaviour)DARK_PLANKS.get()).strength(2.5F))
   );
   public static final DeferredBlock<TrappedChestBlock> TIME_TRAPPED_CHEST = register(
      "time_trapped_chest", () -> new TrappedChestBlock(Properties.ofFullCopy((BlockBehaviour)TIME_PLANKS.get()).strength(2.5F))
   );
   public static final DeferredBlock<TrappedChestBlock> TRANSFORMATION_TRAPPED_CHEST = register(
      "transformation_trapped_chest", () -> new TrappedChestBlock(Properties.ofFullCopy((BlockBehaviour)TRANSFORMATION_PLANKS.get()).strength(2.5F))
   );
   public static final DeferredBlock<TrappedChestBlock> MINING_TRAPPED_CHEST = register(
      "mining_trapped_chest", () -> new TrappedChestBlock(Properties.ofFullCopy((BlockBehaviour)MINING_PLANKS.get()).strength(2.5F))
   );
   public static final DeferredBlock<TrappedChestBlock> SORTING_TRAPPED_CHEST = register(
      "sorting_trapped_chest", () -> new TrappedChestBlock(Properties.ofFullCopy((BlockBehaviour)SORTING_PLANKS.get()).strength(2.5F))
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_TWILIGHT_OAK_SAPLING = BLOCKS.register(
      "potted_twilight_oak_sapling",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         TWILIGHT_OAK_SAPLING,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_CANOPY_SAPLING = BLOCKS.register(
      "potted_canopy_sapling",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         CANOPY_SAPLING,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_MANGROVE_SAPLING = BLOCKS.register(
      "potted_mangrove_sapling",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         MANGROVE_SAPLING,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_DARKWOOD_SAPLING = BLOCKS.register(
      "potted_darkwood_sapling",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         DARKWOOD_SAPLING,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_HOLLOW_OAK_SAPLING = BLOCKS.register(
      "potted_hollow_oak_sapling",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         HOLLOW_OAK_SAPLING,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_RAINBOW_OAK_SAPLING = BLOCKS.register(
      "potted_rainbow_oak_sapling",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         RAINBOW_OAK_SAPLING,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_TIME_SAPLING = BLOCKS.register(
      "potted_time_sapling",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         TIME_SAPLING,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_TRANSFORMATION_SAPLING = BLOCKS.register(
      "potted_transformation_sapling",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         TRANSFORMATION_SAPLING,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_MINING_SAPLING = BLOCKS.register(
      "potted_mining_sapling",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         MINING_SAPLING,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_SORTING_SAPLING = BLOCKS.register(
      "potted_sorting_sapling",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         SORTING_SAPLING,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_MAYAPPLE = BLOCKS.register(
      "potted_mayapple",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         MAYAPPLE,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_FIDDLEHEAD = BLOCKS.register(
      "potted_fiddlehead",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         FIDDLEHEAD,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_MUSHGLOOM = BLOCKS.register(
      "potted_mushgloom",
      () -> new FlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         MUSHGLOOM,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_THORN = BLOCKS.register(
      "potted_thorn",
      () -> new SpecialFlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         BROWN_THORNS,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_GREEN_THORN = BLOCKS.register(
      "potted_green_thorn",
      () -> new SpecialFlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         GREEN_THORNS,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );
   public static final DeferredBlock<FlowerPotBlock> POTTED_DEAD_THORN = BLOCKS.register(
      "potted_dead_thorn",
      () -> new SpecialFlowerPotBlock(
         () -> (FlowerPotBlock)net.minecraft.world.level.block.Blocks.FLOWER_POT,
         BURNT_THORNS,
         Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
      )
   );

   public static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block) {
      DeferredBlock<T> ret = BLOCKS.register(name, block);
      TFItems.ITEMS.register(name, () -> new BlockItem((Block)ret.get(), new net.minecraft.world.item.Item.Properties()));
      return ret;
   }

   public static <T extends Block> DeferredBlock<T> registerFireResistantItem(String name, Supplier<T> block) {
      DeferredBlock<T> ret = BLOCKS.register(name, block);
      TFItems.ITEMS
         .register(name, () -> new BlockItem((Block)ret.get(), new net.minecraft.world.item.Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)));
      return ret;
   }

   public static <T extends Block> DeferredBlock<T> registerDoubleBlockItem(String name, Supplier<T> block) {
      DeferredBlock<T> ret = BLOCKS.register(name, block);
      TFItems.ITEMS.register(name, () -> new DoubleHighBlockItem((Block)ret.get(), new net.minecraft.world.item.Item.Properties()));
      return ret;
   }

   public static <T extends Block> DeferredBlock<T> registerWroughtFence(String name, Supplier<T> block) {
      DeferredBlock<T> ret = BLOCKS.register(name, block);
      TFItems.ITEMS.register(name, () -> new WroughtIronFenceItem((Block)ret.get(), new net.minecraft.world.item.Item.Properties()));
      return ret;
   }

   public static DeferredBlock<OminousCandleBlock> ominousCandle(String name, MapColor mapColor, Block candle) {
      return BLOCKS.register(
         name,
         () -> new OminousCandleBlock(
            candle,
            Properties.of()
               .mapColor(mapColor)
               .noOcclusion()
               .strength(0.1F)
               .sound(SoundType.CANDLE)
               .lightLevel(state -> 2 * (Integer)state.getValue(OminousCandleBlock.CANDLES))
               .pushReaction(PushReaction.DESTROY)
         )
      );
   }

   private static Properties logProperties(MapColor color) {
      return Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(color);
   }

   private static Properties logProperties(MapColor top, MapColor side) {
      return Properties.of()
         .ignitedByLava()
         .instrument(NoteBlockInstrument.BASS)
         .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Axis.Y ? top : side);
   }

   public static Properties copyAndScaleProperties(BlockBehaviour blockBehaviour, float scale) {
      Properties properties = Properties.ofFullCopy(blockBehaviour);
      return properties.destroyTime(properties.destroyTime * scale).explosionResistance(properties.explosionResistance * scale);
   }

   private static boolean noSpawning(BlockState pState, BlockGetter pLevel, BlockPos pPos, EntityType<?> pValue) {
      return false;
   }
}
