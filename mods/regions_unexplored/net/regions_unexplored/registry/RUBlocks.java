package net.regions_unexplored.registry;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.regions_unexplored.RegionsUnexplored;
import net.regions_unexplored.block.RUBlockUtils;
import net.regions_unexplored.block.RuWoodTypes;
import net.regions_unexplored.block.sapling.RUTreeGrowers;
import net.regions_unexplored.block.set.BrimwoodWoodSet;
import net.regions_unexplored.block.set.ColoredSet;
import net.regions_unexplored.block.set.NaturalSet;
import net.regions_unexplored.block.set.WoodSet;
import net.regions_unexplored.block.type.dirt.AshenDirtBlock;
import net.regions_unexplored.block.type.dirt.RUDirtBlock;
import net.regions_unexplored.block.type.dirt.RUDirtPathBlock;
import net.regions_unexplored.block.type.dirt.RUFarmlandBlock;
import net.regions_unexplored.block.type.dirt.RUGrassBlock;
import net.regions_unexplored.block.type.flower.LargeFlowerBlock;
import net.regions_unexplored.block.type.flower.ShortFlowerBlock;
import net.regions_unexplored.block.type.leaves.AppleLeavesBlock;
import net.regions_unexplored.block.type.leaves.BrimwoodLeavesBlock;
import net.regions_unexplored.block.type.leaves.JoshuaLeavesBlock;
import net.regions_unexplored.block.type.leaves.RUTintedParticlesLeavesBlock;
import net.regions_unexplored.block.type.leaves.RUUntintedParticlesLeavesBlock;
import net.regions_unexplored.block.type.misc.PrismaglassBlock;
import net.regions_unexplored.client.color.RuColors;
import net.regions_unexplored.item.RUItemUtils;
import net.regions_unexplored.mixin.VillagerProfessionAccessor;
import net.regions_unexplored.module.version.VersionBlockHelper;
import net.regions_unexplored.registry.data.RUBlockIds;
import net.regions_unexplored.registry.data.RUConfiguredFeatures;
import net.regions_unexplored.registry.data.RUPlacedFeatures;
import net.regions_unexplored.world.level.block.cave.HangingPrismariteBlock;
import net.regions_unexplored.world.level.block.cave.PointedRedstoneBlock;
import net.regions_unexplored.world.level.block.cave.PrismariteBlock;
import net.regions_unexplored.world.level.block.cave.PrismariteDoubleBlock;
import net.regions_unexplored.world.level.block.cave.PrismossBlock;
import net.regions_unexplored.world.level.block.cave.RedstoneBulbBlock;
import net.regions_unexplored.world.level.block.cave.ViridescentNyliumBlock;
import net.regions_unexplored.world.level.block.nether.RuBlackstoneNyliumBlock;
import net.regions_unexplored.world.level.block.nether.RuNyliumBlock;
import net.regions_unexplored.world.level.block.other.AshBlock;
import net.regions_unexplored.world.level.block.other.AshVentBlock;
import net.regions_unexplored.world.level.block.other.CobaltObsidianBlock;
import net.regions_unexplored.world.level.block.other.GlowingBioshroomBlock;
import net.regions_unexplored.world.level.block.other.HyacinthLampBlock;
import net.regions_unexplored.world.level.block.other.IcicleBlock;
import net.regions_unexplored.world.level.block.other.RockPileBlock;
import net.regions_unexplored.world.level.block.plant.aquatic.DuckweedBlock;
import net.regions_unexplored.world.level.block.plant.aquatic.FloweringLilyBlock;
import net.regions_unexplored.world.level.block.plant.aquatic.GiantLilyBlock;
import net.regions_unexplored.world.level.block.plant.aquatic.TallHyacinthStockBlock;
import net.regions_unexplored.world.level.block.plant.dusktrap.DuskTrapBlock;
import net.regions_unexplored.world.level.block.plant.flower.CaveFlowerBlock;
import net.regions_unexplored.world.level.block.plant.flower.GroundCoverBlock;
import net.regions_unexplored.world.level.block.plant.flower.RULeafLitterBlock;
import net.regions_unexplored.world.level.block.plant.flower.RuSnowFlowerBlock;
import net.regions_unexplored.world.level.block.plant.food.DuskmelonBlock;
import net.regions_unexplored.world.level.block.plant.food.SalmonBerryBushBlock;
import net.regions_unexplored.world.level.block.plant.grass.AshenGrassBlock;
import net.regions_unexplored.world.level.block.plant.grass.FrozenGrassBlock;
import net.regions_unexplored.world.level.block.plant.grass.GrassSproutsBlock;
import net.regions_unexplored.world.level.block.plant.grass.PrismossSproutBlock;
import net.regions_unexplored.world.level.block.plant.grass.RuCobaltPlantBlock;
import net.regions_unexplored.world.level.block.plant.grass.RuNetherPlantBlock;
import net.regions_unexplored.world.level.block.plant.grass.RuPlantBlock;
import net.regions_unexplored.world.level.block.plant.grass.RuSandyPlantBlock;
import net.regions_unexplored.world.level.block.plant.nether.BrimPlantBlock;
import net.regions_unexplored.world.level.block.plant.nether.DorcelPlantBlock;
import net.regions_unexplored.world.level.block.plant.nether.GlisteringIvyBlock;
import net.regions_unexplored.world.level.block.plant.nether.GlisteringIvyPlantBlock;
import net.regions_unexplored.world.level.block.plant.nether.HangingEarlightBlock;
import net.regions_unexplored.world.level.block.plant.nether.HangingEarlightPlantBlock;
import net.regions_unexplored.world.level.block.plant.nether.NetherGroundCoverBlock;
import net.regions_unexplored.world.level.block.plant.other.BarrelCactusBlock;
import net.regions_unexplored.world.level.block.plant.other.BioshroomBlock;
import net.regions_unexplored.world.level.block.plant.other.DeadShrubBlock;
import net.regions_unexplored.world.level.block.plant.other.DropleafBlock;
import net.regions_unexplored.world.level.block.plant.other.DropleafPlantBlock;
import net.regions_unexplored.world.level.block.plant.other.KapokVinesBlock;
import net.regions_unexplored.world.level.block.plant.other.KapokVinesPlantBlock;
import net.regions_unexplored.world.level.block.plant.other.RedstoneBudBlock;
import net.regions_unexplored.world.level.block.plant.other.SaguaroCactusBlock;
import net.regions_unexplored.world.level.block.plant.other.SpanishMossBlock;
import net.regions_unexplored.world.level.block.plant.other.SpanishMossPlantBlock;
import net.regions_unexplored.world.level.block.plant.sapling.BaobabSaplingBlock;
import net.regions_unexplored.world.level.block.plant.sapling.RuBrimSaplingBlock;
import net.regions_unexplored.world.level.block.plant.sapling.RuUltraFromSuperSaplingBlock;
import net.regions_unexplored.world.level.block.plant.tall.BrimwoodShrubBlock;
import net.regions_unexplored.world.level.block.plant.tall.CattailBlock;
import net.regions_unexplored.world.level.block.plant.tall.CorpseFlowerBlock;
import net.regions_unexplored.world.level.block.plant.tall.DoubleBioshroomBlock;
import net.regions_unexplored.world.level.block.plant.tall.ElephantEarBlock;
import net.regions_unexplored.world.level.block.plant.tall.MangroveShrubBlock;
import net.regions_unexplored.world.level.block.plant.tall.RuDoubleFlowerBlock;
import net.regions_unexplored.world.level.block.plant.tall.RuDoublePlantBlock;
import net.regions_unexplored.world.level.block.plant.tall.RuNetherDoublePlantBlock;
import net.regions_unexplored.world.level.block.plant.tall.RuSandyDoublePlantBlock;
import net.regions_unexplored.world.level.block.wood.AspenLogBlock;
import net.regions_unexplored.world.level.block.wood.BambooLogBlock;
import net.regions_unexplored.world.level.block.wood.PineLogBlock;
import net.regions_unexplored.world.level.block.wood.SmallOakLogBlock;
import net.regions_unexplored.world.level.block.wood.StrippedBambooLogBlock;

public interface RUBlocks {
   Supplier<Block> PRISMOSS = RUBlockUtils.register(
      "prismoss",
      p -> new PrismossBlock(p.mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.STONE).randomTicks().strength(1.5F, 6.0F).requiresCorrectToolForDrops())
   );
   Supplier<Block> DEEPSLATE_PRISMOSS = RUBlockUtils.register(
      "deepslate_prismoss",
      p -> new PrismossBlock(p.mapColor(MapColor.COLOR_LIGHT_GREEN).sound(SoundType.DEEPSLATE).randomTicks().strength(3.0F, 6.0F).requiresCorrectToolForDrops())
   );
   Supplier<Block> HANGING_PRISMARITE = RUBlockUtils.register(
      "hanging_prismarite",
      p -> new HangingPrismariteBlock(
         VersionBlockHelper.postProcessed(p)
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.AMETHYST)
            .dynamicShape()
            .offsetType(OffsetType.XZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 10)
      )
   );
   Supplier<Block> LARGE_PRISMARITE_CLUSTER = RUBlockUtils.register(
      "large_prismarite_cluster",
      p -> new PrismariteDoubleBlock(
         VersionBlockHelper.postProcessed(p)
            .pushReaction(PushReaction.DESTROY)
            .noCollission()
            .sound(SoundType.AMETHYST)
            .offsetType(OffsetType.XYZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 10)
      )
   );
   Supplier<Block> PRISMAGLASS = RUBlockUtils.register(
      "prismaglass",
      p -> new PrismaglassBlock(
         p.strength(0.3F)
            .sound(SoundType.GLASS)
            .noOcclusion()
            .isValidSpawn(RUBlockUtils::never)
            .isRedstoneConductor(RUBlockUtils::never)
            .isSuffocating(RUBlockUtils::never)
            .isViewBlocking(RUBlockUtils::never)
      )
   );
   Supplier<Block> PRISMARITE_CLUSTER = RUBlockUtils.register(
      "prismarite_cluster",
      p -> new PrismariteBlock(
         VersionBlockHelper.postProcessed(p)
            .noCollission()
            .noOcclusion()
            .instabreak()
            .sound(SoundType.AMETHYST_CLUSTER)
            .pushReaction(PushReaction.DESTROY)
            .offsetType(OffsetType.XZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 10)
      )
   );
   Supplier<Block> PRISMOSS_SPROUT = RUBlockUtils.register(
      "prismoss_sprout",
      p -> new PrismossSproutBlock(p.pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XYZ))
   );
   Supplier<Block> POINTED_REDSTONE = RUBlockUtils.register(
      "pointed_redstone",
      p -> new PointedRedstoneBlock(
         p.mapColor(MapColor.COLOR_RED)
            .noOcclusion()
            .sound(SoundType.POINTED_DRIPSTONE)
            .randomTicks()
            .strength(1.5F, 3.0F)
            .dynamicShape()
            .offsetType(OffsetType.XZ)
            .lightLevel(s -> 1)
            .isRedstoneConductor(RUBlockUtils::always)
      )
   );
   Supplier<Block> RAW_REDSTONE_BLOCK = RUBlockUtils.register(
      "raw_redstone_block",
      p -> new Block(
         p.mapColor(MapColor.COLOR_RED)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .sound(SoundType.TUFF)
            .strength(1.5F)
            .requiresCorrectToolForDrops()
            .isRedstoneConductor(RUBlockUtils::always)
      )
   );
   Supplier<Block> REDSTONE_BUD = RUBlockUtils.register(
      "redstone_bud",
      p -> new RedstoneBudBlock(
         p.pushReaction(PushReaction.DESTROY).replaceable().mapColor(MapColor.COLOR_RED).noCollission().sound(SoundType.TUFF).offsetType(OffsetType.XZ)
      )
   );
   Supplier<Block> REDSTONE_BULB = RUBlockUtils.register(
      "redstone_bulb",
      p -> new RedstoneBulbBlock(
         VersionBlockHelper.postProcessed(p)
            .pushReaction(PushReaction.DESTROY)
            .noCollission()
            .sound(SoundType.AMETHYST)
            .offsetType(OffsetType.XZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 12)
            .isRedstoneConductor(RUBlockUtils::never)
      )
   );
   Supplier<Block> ARGILLITE = RUBlockUtils.register("argillite", p -> new Block(p.mapColor(MapColor.TERRACOTTA_ORANGE).sound(SoundType.CALCITE)), Blocks.STONE);
   Supplier<Block> ARGILLITE_GRASS_BLOCK = RUBlockUtils.register(
      "argillite_grass_block",
      p -> RUGrassBlock.simple(
         ARGILLITE,
         RUPlacedFeatures.BONEMEAL_ARGILLITE_GRASS,
         p.mapColor(MapColor.GRASS).sound(SoundType.STONE).randomTicks().strength(1.5F, 6.0F).requiresCorrectToolForDrops()
      )
   );
   Supplier<Block> STONE_GRASS_BLOCK = RUBlockUtils.register(
      "stone_grass_block",
      p -> RUGrassBlock.simple(
         () -> Blocks.STONE,
         RUPlacedFeatures.BONEMEAL_STONE_GRASS,
         p.mapColor(MapColor.GRASS).sound(SoundType.STONE).randomTicks().strength(1.5F, 6.0F).requiresCorrectToolForDrops()
      )
   );
   Supplier<Block> DEEPSLATE_GRASS_BLOCK = RUBlockUtils.register(
      "deepslate_grass_block",
      p -> RUGrassBlock.simple(
         () -> Blocks.DEEPSLATE,
         RUPlacedFeatures.BONEMEAL_DEEPSLATE_GRASS,
         p.mapColor(MapColor.GRASS).sound(SoundType.DEEPSLATE).randomTicks().strength(3.0F, 6.0F).requiresCorrectToolForDrops()
      )
   );
   Supplier<Block> VIRIDESCENT_NYLIUM = RUBlockUtils.register(
      "viridescent_nylium",
      p -> new ViridescentNyliumBlock(p.mapColor(MapColor.GRASS).sound(SoundType.NYLIUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops())
   );
   Supplier<Block> DEEPSLATE_VIRIDESCENT_NYLIUM = RUBlockUtils.register(
      "deepslate_viridescent_nylium",
      p -> new ViridescentNyliumBlock(p.mapColor(MapColor.GRASS).sound(SoundType.NYLIUM).strength(3.0F, 6.0F).requiresCorrectToolForDrops())
   );
   Supplier<Block> CORPSE_FLOWER = RUBlockUtils.register("corpse_flower", p -> new CorpseFlowerBlock(p.sound(SoundType.FLOWERING_AZALEA)), Blocks.SUNFLOWER);
   Supplier<Block> BLADED_GRASS = RUBlockUtils.register("bladed_grass", p -> new RuPlantBlock(p.sound(SoundType.AZALEA)), Blocks.SHORT_GRASS);
   Supplier<Block> BLADED_TALL_GRASS = RUBlockUtils.register("bladed_tall_grass", p -> new RuDoublePlantBlock(p.sound(SoundType.AZALEA)), Blocks.TALL_GRASS);
   Supplier<Block> DROPLEAF = RUBlockUtils.register(
      "dropleaf",
      p -> new DropleafBlock(
         VersionBlockHelper.postProcessed(p)
            .mapColor(MapColor.COLOR_CYAN)
            .randomTicks()
            .noCollission()
            .instabreak()
            .sound(SoundType.WEEPING_VINES)
            .offsetType(OffsetType.XZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 14)
      )
   );
   Supplier<Block> DROPLEAF_PLANT = RUBlockUtils.registerNoItem(
      "dropleaf_plant",
      p -> new DropleafPlantBlock(p.mapColor(MapColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WEEPING_VINES).offsetType(OffsetType.XZ))
   );
   Supplier<Block> DUSKMELON = RUBlockUtils.registerNoItem("duskmelon", p -> new DuskmelonBlock(p.noCollission().instabreak().sound(SoundType.AZALEA)));
   Supplier<Block> DUSKTRAP = RUBlockUtils.register(
      "dusktrap",
      p -> new DuskTrapBlock(DuskTrapBlock.Sensitivity.EVERYTHING, p.mapColor(MapColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.TWISTING_VINES))
   );
   Supplier<Block> DEAD_STEPPE_SHRUB = RUBlockUtils.register(
      "dead_steppe_shrub",
      p -> new DeadShrubBlock(
         p.pushReaction(PushReaction.DESTROY)
            .ignitedByLava()
            .replaceable()
            .mapColor(MapColor.WOOD)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
      )
   );
   Supplier<Block> FROZEN_GRASS = RUBlockUtils.register("frozen_grass", FrozenGrassBlock::new, Blocks.SHORT_GRASS);
   Supplier<Block> SANDY_GRASS = RUBlockUtils.register("sandy_grass", RuSandyPlantBlock::new, Blocks.SHORT_GRASS);
   Supplier<Block> SMALL_DESERT_SHRUB = RUBlockUtils.register("small_desert_shrub", DeadShrubBlock::new, Blocks.DEAD_BUSH);
   Supplier<Block> STEPPE_GRASS = RUBlockUtils.register(
      "steppe_grass",
      p -> new RuPlantBlock(
         p.pushReaction(PushReaction.DESTROY).ignitedByLava().replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XZ)
      )
   );
   Supplier<Block> STEPPE_SHRUB = RUBlockUtils.register("steppe_shrub", RuPlantBlock::new, STEPPE_GRASS);
   Supplier<Block> GRASS_SPROUTS = RUBlockUtils.register("grass_sprouts", GrassSproutsBlock::new, Blocks.SHORT_GRASS);
   Supplier<Block> ELEPHANT_EAR = RUBlockUtils.register("elephant_ear", ElephantEarBlock::new, Blocks.TALL_GRASS);
   Supplier<Block> SANDY_TALL_GRASS = RUBlockUtils.register("sandy_tall_grass", RuSandyDoublePlantBlock::new, Blocks.TALL_GRASS);
   Supplier<Block> STEPPE_TALL_GRASS = RUBlockUtils.register("steppe_tall_grass", RuDoublePlantBlock::new, Blocks.TALL_GRASS);
   Supplier<Block> WINDSWEPT_GRASS = RUBlockUtils.register("windswept_grass", RuDoublePlantBlock::new, Blocks.TALL_GRASS);
   Supplier<Block> ALPHA_DANDELION = RUBlockUtils.register("alpha_dandelion", p -> new FlowerBlock(MobEffects.JUMP, 5.0F, p), Blocks.DANDELION);
   Supplier<Block> ALPHA_ROSE = RUBlockUtils.register("alpha_rose", p -> new FlowerBlock(MobEffects.JUMP, 5.0F, p), Blocks.DANDELION);
   Supplier<Block> ASTER = RUBlockUtils.register(
      "aster",
      p -> new FlowerBlock(MobEffects.CONFUSION, 10.0F, VersionBlockHelper.postProcessed(p).emissiveRendering((bs, br, bp) -> true).lightLevel(s -> 13)),
      Blocks.DANDELION
   );
   Supplier<Block> BLEEDING_HEART = RUBlockUtils.register("bleeding_heart", p -> new RuSnowFlowerBlock(MobEffects.POISON, 9, p), Blocks.DANDELION);
   Supplier<Block> BLUE_LUPINE = RUBlockUtils.register("blue_lupine", p -> new LargeFlowerBlock(MobEffects.SATURATION, 4.0F, p), Blocks.DANDELION);
   Supplier<Block> DAISY = RUBlockUtils.register("daisy", p -> new ShortFlowerBlock(MobEffects.MOVEMENT_SPEED, 8.0F, p), Blocks.DANDELION);
   Supplier<Block> DORCEL = RUBlockUtils.register("dorcel", p -> new DorcelPlantBlock(MobEffects.WITHER, 20, p.speedFactor(0.5F)), Blocks.DANDELION);
   Supplier<Block> FELICIA_DAISY = RUBlockUtils.register("felicia_daisy", p -> new ShortFlowerBlock(MobEffects.MOVEMENT_SPEED, 8.0F, p), Blocks.DANDELION);
   Supplier<Block> FIREWEED = RUBlockUtils.register("fireweed", p -> new FlowerBlock(MobEffects.GLOWING, 2.0F, p), Blocks.DANDELION);
   Supplier<Block> HIBISCUS = RUBlockUtils.register("hibiscus", p -> new FlowerBlock(MobEffects.JUMP, 6.0F, p), Blocks.DANDELION);
   Supplier<Block> HYSSOP = RUBlockUtils.register("hyssop", p -> new LargeFlowerBlock(MobEffects.LUCK, 10.0F, p), Blocks.DANDELION);
   Supplier<Block> MALLOW = RUBlockUtils.register("mallow", p -> new FlowerBlock(MobEffects.DIG_SLOWDOWN, 4.0F, p), Blocks.DANDELION);
   Supplier<Block> PINK_LUPINE = RUBlockUtils.register("pink_lupine", p -> new LargeFlowerBlock(MobEffects.SATURATION, 4.0F, p), Blocks.DANDELION);
   Supplier<Block> POPPY_BUSH = RUBlockUtils.register(
      "poppy_bush",
      p -> new LargeFlowerBlock(
         MobEffects.WEAKNESS,
         3.0F,
         p.mapColor(MapColor.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XYZ)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
      ),
      Blocks.DANDELION
   );
   Supplier<Block> SALMON_POPPY = RUBlockUtils.register("salmon_poppy", p -> new FlowerBlock(MobEffects.WEAKNESS, 3.0F, p), Blocks.POPPY);
   Supplier<Block> SALMON_POPPY_BUSH = RUBlockUtils.register("salmon_poppy_bush", p -> new LargeFlowerBlock(MobEffects.WEAKNESS, 3.0F, p), POPPY_BUSH);
   Supplier<Block> PURPLE_LUPINE = RUBlockUtils.register("purple_lupine", p -> new LargeFlowerBlock(MobEffects.SATURATION, 4.0F, p), Blocks.DANDELION);
   Supplier<Block> RED_LUPINE = RUBlockUtils.register("red_lupine", p -> new LargeFlowerBlock(MobEffects.SATURATION, 4.0F, p), Blocks.DANDELION);
   Supplier<Block> WARATAH = RUBlockUtils.register("waratah", p -> new FlowerBlock(MobEffects.JUMP, 5.0F, p), Blocks.DANDELION);
   Supplier<Block> TSUBAKI = RUBlockUtils.register("tsubaki", p -> new FlowerBlock(MobEffects.HEAL, 3.0F, p), Blocks.DANDELION);
   Supplier<Block> WHITE_TRILLIUM = RUBlockUtils.register("white_trillium", p -> new FlowerBlock(MobEffects.DIG_SPEED, 7.0F, p), Blocks.DANDELION);
   Supplier<Block> WILTING_TRILLIUM = RUBlockUtils.register("wilting_trillium", p -> new FlowerBlock(MobEffects.DIG_SLOWDOWN, 10.0F, p), Blocks.DANDELION);
   Supplier<Block> YELLOW_LUPINE = RUBlockUtils.register("yellow_lupine", p -> new LargeFlowerBlock(MobEffects.SATURATION, 4.0F, p), Blocks.DANDELION);
   Supplier<Block> ORANGE_CONEFLOWER = RUBlockUtils.register(
      "orange_coneflower", p -> new GroundCoverBlock(p.pushReaction(PushReaction.DESTROY).ignitedByLava().noCollission().sound(SoundType.PINK_PETALS))
   );
   Supplier<Block> PURPLE_CONEFLOWER = RUBlockUtils.register(
      "purple_coneflower", p -> new GroundCoverBlock(p.pushReaction(PushReaction.DESTROY).ignitedByLava().noCollission().sound(SoundType.PINK_PETALS))
   );
   Supplier<Block> CLOVER = RUBlockUtils.register(
      "clover", p -> new GroundCoverBlock(p.pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().sound(SoundType.PINK_PETALS))
   );
   Supplier<MultifaceBlock> BLUE_MAGNOLIA_FLOWERS = RUBlockUtils.register(
      "blue_magnolia_flowers",
      p -> new GlowLichenBlock(p.pushReaction(PushReaction.DESTROY).ignitedByLava().replaceable().noCollission().strength(0.1F).sound(SoundType.GLOW_LICHEN))
   );
   Supplier<MultifaceBlock> PINK_MAGNOLIA_FLOWERS = RUBlockUtils.register("pink_magnolia_flowers", GlowLichenBlock::new, BLUE_MAGNOLIA_FLOWERS);
   Supplier<MultifaceBlock> WHITE_MAGNOLIA_FLOWERS = RUBlockUtils.register("white_magnolia_flowers", GlowLichenBlock::new, BLUE_MAGNOLIA_FLOWERS);
   Supplier<Block> MAPLE_LEAF_LITTER = RUBlockUtils.register(
      "maple_leaf_litter",
      p -> new RULeafLitterBlock(p.pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().sound(RUSoundEvents.LEAF_LITTER))
   );
   Supplier<Block> RED_MAPLE_LEAF_LITTER = RUBlockUtils.register(
      "red_maple_leaf_litter",
      p -> new RULeafLitterBlock(p.pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().sound(RUSoundEvents.LEAF_LITTER))
   );
   Supplier<Block> ORANGE_MAPLE_LEAF_LITTER = RUBlockUtils.register(
      "orange_maple_leaf_litter",
      p -> new RULeafLitterBlock(p.pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().sound(RUSoundEvents.LEAF_LITTER))
   );
   Supplier<Block> SILVER_BIRCH_LEAF_LITTER = RUBlockUtils.register(
      "silver_birch_leaf_litter",
      p -> new RULeafLitterBlock(p.pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().sound(RUSoundEvents.LEAF_LITTER))
   );
   Supplier<Block> ENCHANTED_BIRCH_LEAF_LITTER = RUBlockUtils.register(
      "enchanted_birch_leaf_litter",
      p -> new RULeafLitterBlock(p.pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().sound(RUSoundEvents.LEAF_LITTER))
   );
   Supplier<Block> MEADOW_SAGE = RUBlockUtils.registerNoItem("meadow_sage", RuDoubleFlowerBlock::new, Blocks.ROSE_BUSH);
   Supplier<Block> BARLEY = RUBlockUtils.register("barley", RuDoublePlantBlock::new, Blocks.SUNFLOWER);
   Supplier<Block> CATTAIL = RUBlockUtils.register("cattail", CattailBlock::new, Blocks.SUNFLOWER);
   Supplier<Block> TASSEL = RUBlockUtils.register("tassel", RuDoubleFlowerBlock::new, Blocks.SUNFLOWER);
   Supplier<Block> DAY_LILY = RUBlockUtils.register("day_lily", RuDoubleFlowerBlock::new, Blocks.SUNFLOWER);
   List<NaturalSet> NATURAL_SETS = new ArrayList<>();
   NaturalSet ACACIA_NATURAL_SET = NaturalSet.create("acacia").withBranch().withShrub();
   NaturalSet BIRCH_NATURAL_SET = NaturalSet.create("birch").withBranch().withShrub();
   NaturalSet CHERRY_NATURAL_SET = NaturalSet.create("cherry").withBranch().withShrub();
   NaturalSet DARK_OAK_NATURAL_SET = NaturalSet.create("dark_oak").withBranch().withShrub();
   NaturalSet JUNGLE_NATURAL_SET = NaturalSet.create("jungle").withBranch().withShrub();
   NaturalSet MANGROVE_NATURAL_SET = NaturalSet.create("mangrove").withBranch().withShrub(MangroveShrubBlock::new);
   NaturalSet OAK_NATURAL_SET = NaturalSet.create("oak").withBranch().withShrub();
   NaturalSet SPRUCE_NATURAL_SET = NaturalSet.create("spruce").withBranch().withShrub();
   NaturalSet ALPHA_NATURAL_SET = NaturalSet.create("alpha").withLeaves(LeavesBlock::new).withSapling(RUTreeGrowers.ALPHA_OAK);
   NaturalSet APPLE_OAK_NATURAL_SET = NaturalSet.create("apple_oak").withLeaves(AppleLeavesBlock::new).withSapling(RUTreeGrowers.APPLE_OAK);
   NaturalSet ASHEN_NATURAL_SET = NaturalSet.ashen();
   NaturalSet BAMBOO_NATURAL_SET = NaturalSet.create("bamboo").withLeaves(LeavesBlock::new).withSapling(RUTreeGrowers.BAMBOO);
   NaturalSet BAOBAB_NATURAL_SET = NaturalSet.create("baobab")
      .withBranch()
      .withShrub()
      .withLeaves()
      .withSapling(p -> new BaobabSaplingBlock(RUTreeGrowers.BAOBAB, p));
   NaturalSet BLACKWOOD_NATURAL_SET = NaturalSet.create("blackwood", false)
      .withBranch()
      .withShrub()
      .withLeaves(MapColor.TERRACOTTA_GREEN, RUTintedParticlesLeavesBlock.pine(2571286))
      .withSapling(RUTreeGrowers.BLACKWOOD);
   NaturalSet BLUE_MAGNOLIA_NATURAL_SET = NaturalSet.create("blue_magnolia")
      .withShrub()
      .withLeaves(MapColor.COLOR_LIGHT_BLUE, RUUntintedParticlesLeavesBlock.of(RUParticleTypes.BLUE_MAGNOLIA_LEAVES))
      .withSapling(RUTreeGrowers.BLUE_MAGNOLIA);
   NaturalSet BRIMWOOD_NATURAL_SET = NaturalSet.create("brimwood")
      .withShrub(BrimwoodShrubBlock::new)
      .withLeaves(MapColor.COLOR_BROWN, BrimwoodLeavesBlock::new)
      .withSapling(p -> new RuBrimSaplingBlock(RUTreeGrowers.BRIMWOOD, p));
   NaturalSet COBALT_NATURAL_SET = NaturalSet.cobalt();
   NaturalSet CYPRESS_NATURAL_SET = NaturalSet.create("cypress")
      .withBranch()
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.standard(RUTintedParticlesLeavesBlock.TintGetter.defaultDarken(0.7F)))
      .withSapling(RUTreeGrowers.CYPRESS);
   NaturalSet DEAD_PINE_NATURAL_SET = NaturalSet.create("dead_pine", true)
      .withShrub()
      .withLeaves(MapColor.TERRACOTTA_GRAY, RUTintedParticlesLeavesBlock.pine(5980979))
      .withSapling(RUTreeGrowers.DEAD_PINE);
   NaturalSet DEAD_NATURAL_SET = NaturalSet.create("dead", true)
      .withBranch()
      .withShrub()
      .withLeaves(MapColor.TERRACOTTA_GRAY, RUTintedParticlesLeavesBlock.standard(RUTintedParticlesLeavesBlock.TintGetter.constant(6637104)))
      .withSapling(RUTreeGrowers.DEAD);
   NaturalSet EUCALYPTUS_NATURAL_SET = NaturalSet.create("eucalyptus")
      .withBranch()
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.large(RUTintedParticlesLeavesBlock.TintGetter.defaultDarken(0.8F)))
      .withSapling(RUTreeGrowers.EUCALYPTUS);
   NaturalSet FLOWERING_NATURAL_SET = NaturalSet.create("flowering").withShrub().withLeaves().withSapling(RUTreeGrowers.FLOWERING_OAK);
   NaturalSet GOLDEN_LARCH_NATURAL_SET = NaturalSet.create("golden_larch")
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.pine(9007671))
      .withSapling(RUTreeGrowers.GOLDEN_LARCH);
   NaturalSet JOSHUA_NATURAL_SET = NaturalSet.create("joshua").withBeard().withShrub().withLeaves(JoshuaLeavesBlock::new).withSapling(RUTreeGrowers.JOSHUA);
   NaturalSet KAPOK_NATURAL_SET = NaturalSet.create("kapok")
      .withBranch()
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.large(RUTintedParticlesLeavesBlock.TintGetter.defaultDarken(0.8F)))
      .withSapling(p -> new RuUltraFromSuperSaplingBlock(RUTreeGrowers.KAPOK, p));
   NaturalSet LARCH_NATURAL_SET = NaturalSet.create("larch")
      .withBranch()
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.pine(4344878))
      .withSapling(RUTreeGrowers.LARCH);
   NaturalSet MAGNOLIA_NATURAL_SET = NaturalSet.create("magnolia")
      .withBranch()
      .withShrub()
      .withLeaves(MapColor.GRASS, RUTintedParticlesLeavesBlock.standard(RUParticleTypes.MAGNOLIA_LEAVES, RUTintedParticlesLeavesBlock.TintGetter.DEFAULT))
      .withSapling(RUTreeGrowers.MAGNOLIA);
   NaturalSet MAPLE_NATURAL_SET = NaturalSet.create("maple")
      .withBranch()
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.small(RUTintedParticlesLeavesBlock.TintGetter.defaultDarken(0.6F)))
      .withSapling(RUTreeGrowers.MAPLE);
   NaturalSet ORANGE_MAPLE_NATURAL_SET = NaturalSet.create("orange_maple")
      .withShrub()
      .withLeaves(MapColor.COLOR_ORANGE, RUTintedParticlesLeavesBlock.small(RUTintedParticlesLeavesBlock.TintGetter.constant(9983007)))
      .withSapling(RUTreeGrowers.ORANGE_MAPLE);
   NaturalSet PALM_NATURAL_SET = NaturalSet.create("palm", false)
      .withBeard()
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.large(RUTintedParticlesLeavesBlock.TintGetter.defaultDarken(0.8F)))
      .withSapling(RUTreeGrowers.PALM);
   NaturalSet PINE_NATURAL_SET = NaturalSet.create("pine")
      .withBranch()
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.standard(RUParticleTypes.PINE_LEAVES, RUTintedParticlesLeavesBlock.TintGetter.defaultDarken(0.5F)))
      .withSapling(RUTreeGrowers.PINE);
   NaturalSet PINK_MAGNOLIA_NATURAL_SET = NaturalSet.create("pink_magnolia")
      .withShrub()
      .withLeaves(MapColor.COLOR_PINK, RUUntintedParticlesLeavesBlock.of(RUParticleTypes.PINK_MAGNOLIA_LEAVES))
      .withSapling(RUTreeGrowers.PINK_MAGNOLIA);
   NaturalSet RED_MAPLE_NATURAL_SET = NaturalSet.create("red_maple")
      .withShrub()
      .withLeaves(MapColor.COLOR_RED, RUTintedParticlesLeavesBlock.small(RUTintedParticlesLeavesBlock.TintGetter.constant(9380640)))
      .withSapling(RUTreeGrowers.RED_MAPLE);
   NaturalSet REDWOOD_NATURAL_SET = NaturalSet.create("redwood")
      .withBranch()
      .withShrub()
      .withLeaves()
      .withSapling(p -> new RuUltraFromSuperSaplingBlock(RUTreeGrowers.REDWOOD, p));
   NaturalSet SAGUARO_CACTUS_NATURAL_SET = NaturalSet.saguaroCactus();
   NaturalSet SILVER_BIRCH_NATURAL_SET = NaturalSet.create("silver_birch")
      .withBranch()
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.standard(RuColors::getAspenColor))
      .withSapling(RUTreeGrowers.SILVER_BIRCH);
   NaturalSet SMALL_OAK_NATURAL_SET = NaturalSet.create("small_oak").withLeaves().withSapling(RUTreeGrowers.SMALL_OAK);
   NaturalSet SOCOTRA_NATURAL_SET = NaturalSet.create("socotra").withBranch().withShrub().withLeaves().withSapling(RUTreeGrowers.SOCOTRA);
   NaturalSet WHITE_MAGNOLIA_NATURAL_SET = NaturalSet.create("white_magnolia")
      .withShrub()
      .withLeaves(MapColor.TERRACOTTA_WHITE, RUUntintedParticlesLeavesBlock.of(RUParticleTypes.WHITE_MAGNOLIA_LEAVES))
      .withSapling(RUTreeGrowers.WHITE_MAGNOLIA);
   NaturalSet WILLOW_NATURAL_SET = NaturalSet.create("willow")
      .withBranch()
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.standard(RUTintedParticlesLeavesBlock.TintGetter.defaultDarken(0.7F)))
      .withSapling(RUTreeGrowers.WILLOW);
   NaturalSet WISTERIA_NATURAL_SET = NaturalSet.create("wisteria").withBranch();
   NaturalSet SKY_WISTERIA_NATURAL_SET = NaturalSet.create("sky_wisteria")
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.small(RUTintedParticlesLeavesBlock.TintGetter.constant(6726853)))
      .withVines(MapColor.COLOR_LIGHT_BLUE)
      .withSapling(RUTreeGrowers.SKY_WISTERIA);
   NaturalSet LAVENDER_WISTERIA_NATURAL_SET = NaturalSet.create("lavender_wisteria")
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.small(RUTintedParticlesLeavesBlock.TintGetter.constant(12817647)))
      .withVines(MapColor.COLOR_PURPLE)
      .withSapling(RUTreeGrowers.LAVENDER_WISTERIA);
   NaturalSet SALMON_WISTERIA_NATURAL_SET = NaturalSet.create("salmon_wisteria")
      .withShrub()
      .withLeaves(RUTintedParticlesLeavesBlock.small(RUTintedParticlesLeavesBlock.TintGetter.constant(16753581)))
      .withVines(MapColor.COLOR_PINK)
      .withSapling(RUTreeGrowers.SALMON_WISTERIA);
   List<NaturalSet> WISTERIA_NATURAL_SETS = List.of(SKY_WISTERIA_NATURAL_SET, LAVENDER_WISTERIA_NATURAL_SET, SALMON_WISTERIA_NATURAL_SET);
   Supplier<Block> BLUE_BIOSHROOM = RUBlockUtils.register(
      "blue_bioshroom",
      p -> new BioshroomBlock(
         RUTreeGrowers.BLUE_BIOSHROOM,
         MobEffects.POISON,
         10,
         9364991,
         p.mapColor(MapColor.COLOR_LIGHT_BLUE)
            .pushReaction(PushReaction.DESTROY)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
            .hasPostProcess((bs, br, bp) -> true)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 10)
      )
   );
   Supplier<Block> GREEN_BIOSHROOM = RUBlockUtils.register(
      "green_bioshroom",
      p -> new BioshroomBlock(RUTreeGrowers.GREEN_BIOSHROOM, MobEffects.POISON, 10, 9956725, p.mapColor(MapColor.COLOR_LIGHT_GREEN)),
      BLUE_BIOSHROOM
   );
   Supplier<Block> PINK_BIOSHROOM = RUBlockUtils.register(
      "pink_bioshroom", p -> new BioshroomBlock(RUTreeGrowers.PINK_BIOSHROOM, MobEffects.POISON, 10, 16688362, p.mapColor(MapColor.COLOR_PINK)), BLUE_BIOSHROOM
   );
   Supplier<Block> YELLOW_BIOSHROOM = RUBlockUtils.register(
      "yellow_bioshroom",
      p -> new BioshroomBlock(RUTreeGrowers.YELLOW_BIOSHROOM, MobEffects.POISON, 10, 15455868, p.mapColor(MapColor.COLOR_YELLOW)),
      BLUE_BIOSHROOM
   );
   Supplier<Block> TALL_BLUE_BIOSHROOM = RUBlockUtils.register(
      "tall_blue_bioshroom",
      p -> new DoubleBioshroomBlock(
         9364991,
         VersionBlockHelper.postProcessed(p)
            .pushReaction(PushReaction.DESTROY)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XYZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 10)
      )
   );
   Supplier<Block> TALL_GREEN_BIOSHROOM = RUBlockUtils.register("tall_green_bioshroom", p -> new DoubleBioshroomBlock(9956725, p), TALL_BLUE_BIOSHROOM);
   Supplier<Block> TALL_PINK_BIOSHROOM = RUBlockUtils.register("tall_pink_bioshroom", p -> new DoubleBioshroomBlock(16688362, p), TALL_BLUE_BIOSHROOM);
   Supplier<Block> TALL_YELLOW_BIOSHROOM = RUBlockUtils.register("tall_yellow_bioshroom", p -> new DoubleBioshroomBlock(15455868, p), TALL_BLUE_BIOSHROOM);
   Supplier<Block> ICICLE = RUBlockUtils.register(
      "icicle",
      p -> new IcicleBlock(
         p.mapColor(MapColor.COLOR_LIGHT_BLUE).noOcclusion().sound(SoundType.GLASS).strength(1.0F, 0.6F).dynamicShape().offsetType(OffsetType.XZ)
      )
   );
   Supplier<Block> BARREL_CACTUS = RUBlockUtils.register(
      "barrel_cactus",
      p -> new BarrelCactusBlock(
         p.pushReaction(PushReaction.DESTROY).ignitedByLava().noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XZ)
      )
   );
   Supplier<Block> CAVE_HYSSOP = RUBlockUtils.register(
      "cave_hyssop",
      p -> new CaveFlowerBlock(
         MobEffects.LUCK, 10, p.pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XZ)
      )
   );
   Supplier<Block> DUCKWEED = RUBlockUtils.register(
      "duckweed",
      p -> new DuckweedBlock(
         p.sound(SoundType.CORAL_BLOCK)
            .instabreak()
            .noCollission()
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .ignitedByLava()
            .isRedstoneConductor((bs, br, bp) -> false)
      ),
      RUItemUtils::registerPlaceOnWaterBlock,
      null
   );
   Supplier<Block> SPANISH_MOSS = RUBlockUtils.register(
      "spanish_moss",
      p -> new SpanishMossBlock(p.pushReaction(PushReaction.DESTROY).ignitedByLava().randomTicks().noCollission().instabreak().sound(SoundType.LILY_PAD))
   );
   Supplier<Block> SPANISH_MOSS_PLANT = RUBlockUtils.registerNoItem("spanish_moss_plant", SpanishMossPlantBlock::new, SPANISH_MOSS);
   Supplier<Block> KAPOK_VINES = RUBlockUtils.register(
      "kapok_vines",
      p -> new KapokVinesBlock(p.pushReaction(PushReaction.DESTROY).ignitedByLava().randomTicks().noCollission().instabreak().sound(SoundType.LILY_PAD))
   );
   Supplier<Block> KAPOK_VINES_PLANT = RUBlockUtils.registerNoItem("kapok_vines_plant", KapokVinesPlantBlock::new, KAPOK_VINES);
   Supplier<Block> FLOWERING_LILY_PAD = RUBlockUtils.register(
      "flowering_lily_pad", FloweringLilyBlock::new, RUItemUtils::registerPlaceOnWaterBlock, () -> Blocks.LILY_PAD
   );
   Supplier<Block> GIANT_LILY_PAD = RUBlockUtils.registerNoItem("giant_lily_pad", GiantLilyBlock::new, FLOWERING_LILY_PAD);
   Supplier<Block> SALMONBERRY_BUSH = RUBlockUtils.registerNoItem(
      "salmonberry_bush",
      p -> new SalmonBerryBushBlock(p.pushReaction(PushReaction.BLOCK).ignitedByLava().randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH))
   );
   Supplier<Block> BLUE_BIOSHROOM_BLOCK = RUBlockUtils.register(
      "blue_bioshroom_block", p -> new Block(p.mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASS).sound(SoundType.WART_BLOCK).strength(0.6F))
   );
   Supplier<Block> GREEN_BIOSHROOM_BLOCK = RUBlockUtils.register(
      "green_bioshroom_block", p -> new Block(p.mapColor(MapColor.COLOR_GREEN)), BLUE_BIOSHROOM_BLOCK
   );
   Supplier<Block> PINK_BIOSHROOM_BLOCK = RUBlockUtils.register("pink_bioshroom_block", p -> new Block(p.mapColor(MapColor.COLOR_PINK)), BLUE_BIOSHROOM_BLOCK);
   Supplier<Block> YELLOW_BIOSHROOM_BLOCK = RUBlockUtils.register(
      "yellow_bioshroom_block", p -> new Block(p.mapColor(MapColor.COLOR_YELLOW)), BLUE_BIOSHROOM_BLOCK
   );
   Supplier<Block> GLOWING_BLUE_BIOSHROOM_BLOCK = RUBlockUtils.register(
      "glowing_blue_bioshroom_block",
      p -> new GlowingBioshroomBlock(
         9364991,
         0.05F,
         VersionBlockHelper.postProcessed(p)
            .mapColor(MapColor.COLOR_BLUE)
            .sound(SoundType.WART_BLOCK)
            .instrument(NoteBlockInstrument.BASS)
            .strength(0.6F)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 15)
      )
   );
   Supplier<Block> GLOWING_GREEN_BIOSHROOM_BLOCK = RUBlockUtils.register(
      "glowing_green_bioshroom_block", p -> new GlowingBioshroomBlock(9956725, 0.05F, p.mapColor(MapColor.COLOR_GREEN)), GLOWING_BLUE_BIOSHROOM_BLOCK
   );
   Supplier<Block> GLOWING_PINK_BIOSHROOM_BLOCK = RUBlockUtils.register(
      "glowing_pink_bioshroom_block", p -> new GlowingBioshroomBlock(16688362, 0.25F, p.mapColor(MapColor.COLOR_PINK)), GLOWING_BLUE_BIOSHROOM_BLOCK
   );
   Supplier<Block> GLOWING_YELLOW_BIOSHROOM_BLOCK = RUBlockUtils.register(
      "glowing_yellow_bioshroom_block", p -> new GlowingBioshroomBlock(15455868, 0.25F, p.mapColor(MapColor.COLOR_YELLOW)), GLOWING_BLUE_BIOSHROOM_BLOCK
   );
   Supplier<Block> BAMBOO_LOG = RUBlockUtils.register(
      "bamboo_log",
      p -> new BambooLogBlock(
         p.mapColor(MapColor.COLOR_LIGHT_GREEN)
            .instrument(NoteBlockInstrument.BASS)
            .sound(SoundType.BAMBOO)
            .strength(2.0F, 3.0F)
            .noOcclusion()
            .isRedstoneConductor((bs, br, bp) -> false)
      )
   );
   Supplier<Block> STRIPPED_BAMBOO_LOG = RUBlockUtils.register(
      "stripped_bamboo_log",
      p -> new StrippedBambooLogBlock(
         p.mapColor(MapColor.SAND)
            .instrument(NoteBlockInstrument.BASS)
            .sound(SoundType.BAMBOO)
            .strength(2.0F, 3.0F)
            .noOcclusion()
            .isRedstoneConductor((bs, br, bp) -> false)
      )
   );
   Supplier<Block> SMALL_OAK_LOG = RUBlockUtils.register(
      "small_oak_log",
      p -> new SmallOakLogBlock(
         p.mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .sound(SoundType.WOOD)
            .strength(2.0F, 3.0F)
            .noOcclusion()
            .isRedstoneConductor((bs, br, bp) -> false)
      )
   );
   Supplier<Block> STRIPPED_SMALL_OAK_LOG = RUBlockUtils.register(
      "stripped_small_oak_log",
      p -> new SmallOakLogBlock(
         p.mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .sound(SoundType.WOOD)
            .strength(2.0F, 3.0F)
            .noOcclusion()
            .isRedstoneConductor((bs, br, bp) -> false)
      )
   );
   Supplier<Block> SAGUARO_CACTUS = RUBlockUtils.register(
      "saguaro_cactus",
      p -> new SaguaroCactusBlock(p.mapColor(MapColor.COLOR_LIGHT_GREEN).instrument(NoteBlockInstrument.GUITAR).sound(SoundType.WOOL).strength(2.0F))
   );
   Supplier<Block> PEAT_DIRT_PATH = RUBlockUtils.register(
      "peat_dirt_path",
      p -> new RUDirtPathBlock(
         RUBlockIds.PEAT_DIRT, p.strength(0.65F).sound(SoundType.GRASS).isViewBlocking(RUBlockUtils::always).isSuffocating(RUBlockUtils::always)
      )
   );
   Supplier<Block> PEAT_FARMLAND = RUBlockUtils.register(
      "peat_farmland",
      p -> new RUFarmlandBlock(
         RUBlockIds.PEAT_DIRT, p.randomTicks().strength(0.6F).sound(SoundType.GRAVEL).isViewBlocking(RUBlockUtils::always).isSuffocating(RUBlockUtils::always)
      )
   );
   Supplier<Block> PEAT_MUD = RUBlockUtils.register(
      "peat_mud",
      p -> new MudBlock(
         p.mapColor(MapColor.TERRACOTTA_BROWN)
            .randomTicks()
            .isValidSpawn(RUBlockUtils::always)
            .isRedstoneConductor(RUBlockUtils::always)
            .isViewBlocking(RUBlockUtils::always)
            .isSuffocating(RUBlockUtils::always)
            .sound(SoundType.MUD)
      ),
      Blocks.DIRT
   );
   Supplier<Block> PEAT_PODZOL = RUBlockUtils.register("peat_podzol", SnowyDirtBlock::new, Blocks.PODZOL);
   Supplier<Block> PEAT_COARSE_DIRT = RUBlockUtils.register("peat_coarse_dirt", p -> new RUDirtBlock(PEAT_DIRT_PATH, PEAT_FARMLAND, p), Blocks.COARSE_DIRT);
   Supplier<Block> PEAT_DIRT = RUBlockUtils.register("peat_dirt", p -> new RUDirtBlock(PEAT_DIRT_PATH, PEAT_FARMLAND, p), Blocks.DIRT);
   Supplier<Block> PEAT_GRASS_BLOCK = RUBlockUtils.register(
      "peat_grass_block", p -> new RUGrassBlock(PEAT_DIRT, PEAT_DIRT_PATH, PEAT_FARMLAND, RUPlacedFeatures.BONEMEAL_PEAT_GRASS, p), Blocks.GRASS_BLOCK
   );
   Supplier<Block> SILT_DIRT_PATH = RUBlockUtils.register(
      "silt_dirt_path",
      p -> new RUDirtPathBlock(
         RUBlockIds.SILT_DIRT, p.strength(0.65F).sound(SoundType.GRASS).isViewBlocking(RUBlockUtils::always).isSuffocating(RUBlockUtils::always)
      )
   );
   Supplier<Block> SILT_FARMLAND = RUBlockUtils.register(
      "silt_farmland",
      p -> new RUFarmlandBlock(
         RUBlockIds.SILT_DIRT, p.randomTicks().strength(0.6F).sound(SoundType.GRAVEL).isViewBlocking(RUBlockUtils::always).isSuffocating(RUBlockUtils::always)
      )
   );
   Supplier<Block> SILT_MUD = RUBlockUtils.register(
      "silt_mud",
      p -> new MudBlock(
         p.mapColor(MapColor.TERRACOTTA_YELLOW)
            .randomTicks()
            .isValidSpawn(RUBlockUtils::always)
            .isRedstoneConductor(RUBlockUtils::always)
            .isViewBlocking(RUBlockUtils::always)
            .isSuffocating(RUBlockUtils::always)
            .sound(SoundType.MUD)
      ),
      Blocks.DIRT
   );
   Supplier<Block> SILT_PODZOL = RUBlockUtils.register("silt_podzol", SnowyDirtBlock::new, Blocks.PODZOL);
   Supplier<Block> SILT_COARSE_DIRT = RUBlockUtils.register("silt_coarse_dirt", p -> new RUDirtBlock(SILT_DIRT_PATH, SILT_FARMLAND, p), Blocks.COARSE_DIRT);
   Supplier<Block> SILT_DIRT = RUBlockUtils.register("silt_dirt", p -> new RUDirtBlock(SILT_DIRT_PATH, SILT_FARMLAND, p), Blocks.DIRT);
   Supplier<Block> SILT_GRASS_BLOCK = RUBlockUtils.register(
      "silt_grass_block", p -> new RUGrassBlock(SILT_DIRT, SILT_DIRT_PATH, SILT_FARMLAND, RUPlacedFeatures.BONEMEAL_SILT_GRASS, p), Blocks.GRASS_BLOCK
   );
   Supplier<Block> ALPHA_GRASS_BLOCK = RUBlockUtils.register(
      "alpha_grass_block",
      p -> RUGrassBlock.simple(
         () -> Blocks.DIRT, RUPlacedFeatures.BONEMEAL_ALPHA_GRASS, p.mapColor(MapColor.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRAVEL)
      )
   );
   Supplier<Block> CHALK = RUBlockUtils.register(
      "chalk",
      p -> new Block(p.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.TUFF).strength(0.5F).requiresCorrectToolForDrops())
   );
   Supplier<Block> CHALK_GRASS_BLOCK = RUBlockUtils.register(
      "chalk_grass_block",
      p -> RUGrassBlock.simple(
         CHALK,
         RUPlacedFeatures.BONEMEAL_CHALK_GRASS,
         p.mapColor(MapColor.GRASS).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.TUFF).randomTicks().strength(0.6F).requiresCorrectToolForDrops()
      )
   );
   Supplier<Block> CHALK_BRICKS = RUBlockUtils.register(
      "chalk_bricks",
      p -> new Block(p.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.TUFF).strength(0.6F).requiresCorrectToolForDrops())
   );
   Supplier<Block> CHALK_BRICK_SLAB = RUBlockUtils.register(
      "chalk_brick_slab",
      p -> new SlabBlock(p.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.TUFF).strength(0.6F).requiresCorrectToolForDrops())
   );
   Supplier<Block> CHALK_BRICK_STAIRS = RUBlockUtils.register("chalk_brick_stairs", p -> new StairBlock(CHALK.get().defaultBlockState(), p), CHALK_BRICKS);
   Supplier<Block> CHALK_PILLAR = RUBlockUtils.register(
      "chalk_pillar",
      p -> new RotatedPillarBlock(
         p.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.TUFF).strength(0.6F).requiresCorrectToolForDrops()
      )
   );
   Supplier<Block> CHALK_SLAB = RUBlockUtils.register(
      "chalk_slab",
      p -> new SlabBlock(p.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.TUFF).strength(0.5F).requiresCorrectToolForDrops())
   );
   Supplier<Block> CHALK_STAIRS = RUBlockUtils.register("chalk_stairs", p -> new StairBlock(CHALK.get().defaultBlockState(), p), CHALK);
   Supplier<Block> POLISHED_CHALK = RUBlockUtils.register(
      "polished_chalk",
      p -> new Block(p.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.TUFF).strength(0.5F).requiresCorrectToolForDrops())
   );
   Supplier<Block> POLISHED_CHALK_SLAB = RUBlockUtils.register(
      "polished_chalk_slab",
      p -> new SlabBlock(p.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.TUFF).strength(0.5F).requiresCorrectToolForDrops())
   );
   Supplier<Block> POLISHED_CHALK_STAIRS = RUBlockUtils.register(
      "polished_chalk_stairs", p -> new StairBlock(CHALK.get().defaultBlockState(), p), POLISHED_CHALK
   );
   Supplier<Block> MOSSY_STONE = RUBlockUtils.register("mossy_stone", Block::new, Blocks.STONE);
   Supplier<Block> HYACINTH_LAMP = RUBlockUtils.register(
      "hyacinth_lamp",
      p -> new HyacinthLampBlock(
         VersionBlockHelper.postProcessed(p)
            .noOcclusion()
            .instabreak()
            .sound(SoundType.DECORATED_POT)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 14)
      )
   );
   Supplier<Block> HYACINTH_BLOOM = RUBlockUtils.register(
      "hyacinth_bloom",
      p -> new SeagrassBlock(
         VersionBlockHelper.postProcessed(p)
            .replaceable()
            .noCollission()
            .instabreak()
            .sound(SoundType.WET_GRASS)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 9)
      )
   );
   Supplier<GlowLichenBlock> HYACINTH_FLOWERS = RUBlockUtils.register(
      "hyacinth_flowers",
      p -> new GlowLichenBlock(
         VersionBlockHelper.postProcessed(p)
            .replaceable()
            .mapColor(MapColor.GLOW_LICHEN)
            .noCollission()
            .strength(0.2F)
            .sound(SoundType.GLOW_LICHEN)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 8)
      )
   );
   Supplier<Block> TALL_HYACINTH_STOCK = RUBlockUtils.register(
      "tall_hyacinth_stock",
      p -> new TallHyacinthStockBlock(
         VersionBlockHelper.postProcessed(p).noCollission().instabreak().sound(SoundType.WET_GRASS).emissiveRendering((bs, br, bp) -> true).lightLevel(s -> 12)
      )
   );
   Supplier<Block> ASHEN_DIRT = RUBlockUtils.register(
      "ashen_dirt",
      p -> new AshenDirtBlock(
         p.mapColor(MapColor.COLOR_GRAY).strength(0.5F).sound(SoundType.GRAVEL).randomTicks().lightLevel(state -> AshenDirtBlock.isSmouldering(state) ? 7 : 0)
      )
   );
   Supplier<Block> ASHEN_GRASS = RUBlockUtils.register(
      "ashen_grass",
      p -> new AshenGrassBlock(
         p.pushReaction(PushReaction.DESTROY)
            .replaceable()
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XYZ)
            .hasPostProcess((bs, br, bp) -> AshenGrassBlock.isSmouldering(bs))
            .emissiveRendering((bs, br, bp) -> AshenGrassBlock.isSmouldering(bs))
            .lightLevel(bs -> AshenGrassBlock.isSmouldering(bs) ? 5 : 0)
      )
   );
   Supplier<Block> ASH = RUBlockUtils.register(
      "ash", p -> new AshBlock(p.mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.GRAVEL).randomTicks())
   );
   Supplier<Block> ASH_VENT = RUBlockUtils.register(
      "ash_vent",
      p -> new AshVentBlock(
         p.mapColor(MapColor.COLOR_BLACK)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(1.6F)
            .sound(SoundType.BASALT)
            .randomTicks()
            .requiresCorrectToolForDrops()
      )
   );
   Supplier<Block> VOLCANIC_ASH = RUBlockUtils.register(
      "volcanic_ash",
      p -> new AshBlock(p.mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.GRAVEL).randomTicks())
   );
   List<WoodSet> WOOD_SETS = new ArrayList<>();
   WoodSet ALPHA_WOOD_SET = WoodSet.alpha();
   WoodSet ASHEN_WOOD_SET = WoodSet.onlyLogs("ashen", SoundType.NETHER_WOOD, MapColor.COLOR_LIGHT_GRAY, MapColor.COLOR_GRAY, true, RotatedPillarBlock::new);
   WoodSet BAOBAB_WOOD_SET = WoodSet.simple(
      "baobab", RuWoodTypes.BAOBAB, RUSoundEvents.BAOBAB_SET.baseType(), MapColor.WOOD, MapColor.TERRACOTTA_LIGHT_GRAY, false
   );
   WoodSet BLACKWOOD_WOOD_SET = WoodSet.simple(
      "blackwood", RuWoodTypes.BLACKWOOD, SoundType.NETHER_WOOD, MapColor.TERRACOTTA_BLACK, MapColor.TERRACOTTA_BROWN, false
   );
   BrimwoodWoodSet BRIMWOOD_WOOD_SET = BrimwoodWoodSet.brimwood(
      "brimwood", RuWoodTypes.BRIMWOOD, SoundType.NETHER_WOOD, MapColor.COLOR_BROWN, MapColor.COLOR_ORANGE, true
   );
   WoodSet COBALT_WOOD_SET = WoodSet.simple(
      "cobalt", RuWoodTypes.COBALT, SoundType.NETHER_WOOD, MapColor.COLOR_BLUE, MapColor.COLOR_BLACK, true, RotatedPillarBlock::new, false
   );
   WoodSet CYPRESS_WOOD_SET = WoodSet.simple("cypress", RuWoodTypes.CYPRESS, SoundType.BAMBOO_WOOD, MapColor.WOOD, MapColor.WOOD, false);
   WoodSet DEAD_WOOD_SET = WoodSet.simple("dead", RuWoodTypes.DEAD, SoundType.WOOD, MapColor.WOOD, MapColor.WOOD, true);
   WoodSet EUCALYPTUS_WOOD_SET = WoodSet.simple("eucalyptus", RuWoodTypes.EUCALYPTUS, SoundType.WOOD, MapColor.WOOD, MapColor.WOOD, false);
   WoodSet JOSHUA_WOOD_SET = WoodSet.simple("joshua", RuWoodTypes.JOSHUA, SoundType.WOOD, MapColor.WOOD, MapColor.WOOD, false);
   WoodSet KAPOK_WOOD_SET = WoodSet.simple("kapok", RuWoodTypes.KAPOK, SoundType.WOOD, MapColor.TERRACOTTA_GREEN, MapColor.WOOD, false);
   WoodSet LARCH_WOOD_SET = WoodSet.simple("larch", RuWoodTypes.LARCH, SoundType.WOOD, MapColor.WOOD, MapColor.WOOD, false);
   WoodSet MAGNOLIA_WOOD_SET = WoodSet.simple("magnolia", RuWoodTypes.MAGNOLIA, SoundType.CHERRY_WOOD, MapColor.TERRACOTTA_PINK, MapColor.STONE, false);
   WoodSet MAPLE_WOOD_SET = WoodSet.simple("maple", RuWoodTypes.MAPLE, SoundType.WOOD, MapColor.WOOD, MapColor.WOOD, false);
   WoodSet PALM_WOOD_SET = WoodSet.simple("palm", RuWoodTypes.PALM, SoundType.BAMBOO_WOOD, MapColor.WOOD, MapColor.WOOD, false);
   WoodSet PINE_WOOD_SET = WoodSet.simple("pine", RuWoodTypes.PINE, SoundType.BAMBOO_WOOD, MapColor.WOOD, MapColor.WOOD, false, PineLogBlock::new, true);
   WoodSet REDWOOD_WOOD_SET = WoodSet.simple(
      "redwood", RuWoodTypes.REDWOOD, RUSoundEvents.REDWOOD_SET.baseType(), MapColor.TERRACOTTA_RED, MapColor.TERRACOTTA_RED, false
   );
   WoodSet SILVER_BIRCH_WOOD_SET = WoodSet.onlyLogs("silver_birch", SoundType.WOOD, MapColor.SAND, MapColor.QUARTZ, false, AspenLogBlock::new);
   WoodSet SOCOTRA_WOOD_SET = WoodSet.simple(
      "socotra", RuWoodTypes.SOCOTRA, SoundType.CHERRY_WOOD, MapColor.TERRACOTTA_ORANGE, MapColor.TERRACOTTA_ORANGE, false
   );
   WoodSet WILLOW_WOOD_SET = WoodSet.simple("willow", RuWoodTypes.WILLOW, SoundType.WOOD, MapColor.WOOD, MapColor.WOOD, false);
   WoodSet WISTERIA_WOOD_SET = WoodSet.simple("wisteria", RuWoodTypes.WISTERIA, SoundType.CHERRY_WOOD, MapColor.TERRACOTTA_PURPLE, MapColor.PODZOL, false);
   WoodSet PINK_BIOSHROOM_WOOD_SET = WoodSet.bioshroom("pink_bioshroom", RuWoodTypes.PINK_BIOSHROOM, SoundType.NETHER_WOOD, MapColor.COLOR_PINK, false);
   WoodSet YELLOW_BIOSHROOM_WOOD_SET = WoodSet.bioshroom("yellow_bioshroom", RuWoodTypes.YELLOW_BIOSHROOM, SoundType.NETHER_WOOD, MapColor.COLOR_YELLOW, true);
   WoodSet BLUE_BIOSHROOM_WOOD_SET = WoodSet.bioshroom("blue_bioshroom", RuWoodTypes.BLUE_BIOSHROOM, SoundType.NETHER_WOOD, MapColor.COLOR_LIGHT_BLUE, false);
   WoodSet GREEN_BIOSHROOM_WOOD_SET = WoodSet.bioshroom(
      "green_bioshroom", RuWoodTypes.GREEN_BIOSHROOM, SoundType.NETHER_WOOD, MapColor.COLOR_LIGHT_GREEN, false
   );
   ColoredSet PAINTED_PLANKS = new ColoredSet(
      color -> RUBlockUtils.register(color.getName() + "_painted_planks", p -> RUBlockUtils.planks(p, color.getMapColor(), SoundType.WOOD, false))
   );
   ColoredSet PAINTED_STAIRS = new ColoredSet(
      color -> RUBlockUtils.register(color.getName() + "_painted_stairs", p -> RUBlockUtils.stairs(p, color.getMapColor(), SoundType.WOOD, false))
   );
   ColoredSet PAINTED_SLABS = new ColoredSet(
      color -> RUBlockUtils.register(color.getName() + "_painted_slab", p -> RUBlockUtils.slab(p, color.getMapColor(), SoundType.WOOD, false))
   );
   Supplier<Block> BLACKSTONE_CLUSTER = RUBlockUtils.register("blackstone_cluster", p -> new RockPileBlock(p.strength(0.6F).sound(SoundType.NETHERRACK)));
   Supplier<Block> OVERGROWN_BONE_BLOCK = RUBlockUtils.register("overgrown_bone_block", Block::new, Blocks.BONE_BLOCK);
   Supplier<Block> BRIMSPROUT_NYLIUM = RUBlockUtils.register(
      "brimsprout_nylium",
      p -> new RuNyliumBlock(
         p.mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops().strength(0.4F).sound(SoundType.SCULK_SENSOR),
         RUConfiguredFeatures.BONEMEAL_BRIMSPROUT_NYLIUM
      )
   );
   Supplier<Block> BRIMSPROUT = RUBlockUtils.register(
      "brimsprout", p -> new BrimPlantBlock(p.replaceable().noCollission().instabreak().sound(SoundType.SCULK).offsetType(OffsetType.XYZ))
   );
   Supplier<Block> COBALT_EARLIGHT = RUBlockUtils.register(
      "cobalt_earlight",
      p -> new RuNetherPlantBlock(
         VersionBlockHelper.postProcessed(p)
            .replaceable()
            .noCollission()
            .instabreak()
            .sound(SoundType.NETHER_SPROUTS)
            .offsetType(OffsetType.XZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 9)
      )
   );
   Supplier<Block> TALL_COBALT_EARLIGHT = RUBlockUtils.register(
      "tall_cobalt_earlight",
      p -> new RuNetherDoublePlantBlock(
         VersionBlockHelper.postProcessed(p)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 13)
      )
   );
   Supplier<Block> COBALT_NYLIUM = RUBlockUtils.register(
      "cobalt_nylium",
      p -> new RuBlackstoneNyliumBlock(
         p.mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(0.4F).sound(SoundType.NYLIUM), RUConfiguredFeatures.BONEMEAL_COBALT_NYLIUM
      )
   );
   Supplier<Block> COBALT_OBSIDIAN = RUBlockUtils.register(
      "cobalt_obsidian", p -> new CobaltObsidianBlock(p.mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F))
   );
   Supplier<Block> COBALT_ROOTS = RUBlockUtils.register(
      "cobalt_roots", p -> new RuCobaltPlantBlock(p.replaceable().noCollission().instabreak().sound(SoundType.NETHER_SPROUTS).offsetType(OffsetType.XYZ))
   );
   Supplier<Block> HANGING_EARLIGHT = RUBlockUtils.registerNoItem(
      "hanging_earlight",
      p -> new HangingEarlightBlock(
         VersionBlockHelper.postProcessed(p)
            .mapColor(MapColor.COLOR_BLUE)
            .randomTicks()
            .noCollission()
            .instabreak()
            .sound(SoundType.WEEPING_VINES)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 14)
      )
   );
   Supplier<Block> HANGING_EARLIGHT_PLANT = RUBlockUtils.registerNoItem(
      "hanging_earlight_plant", p -> new HangingEarlightPlantBlock(p.mapColor(MapColor.COLOR_BLUE).noCollission().instabreak().sound(SoundType.WEEPING_VINES))
   );
   Supplier<Block> GLISTERING_IVY = RUBlockUtils.register(
      "glistering_ivy",
      p -> new GlisteringIvyBlock(
         VersionBlockHelper.postProcessed(p)
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .randomTicks()
            .noCollission()
            .instabreak()
            .sound(SoundType.WEEPING_VINES)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 15)
      )
   );
   Supplier<Block> GLISTERING_IVY_PLANT = RUBlockUtils.registerNoItem(
      "glistering_ivy_plant",
      p -> new GlisteringIvyPlantBlock(p.mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().instabreak().sound(SoundType.WEEPING_VINES))
   );
   Supplier<Block> GLISTERING_NYLIUM = RUBlockUtils.register(
      "glistering_nylium",
      p -> new RuNyliumBlock(
         p.mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(0.4F).sound(SoundType.NYLIUM), RUConfiguredFeatures.BONEMEAL_GLISTERING_NYLIUM
      )
   );
   Supplier<Block> GLISTERING_SPROUT = RUBlockUtils.register(
      "glistering_sprout", p -> new RuNetherPlantBlock(p.replaceable().noCollission().instabreak().sound(SoundType.TWISTING_VINES).offsetType(OffsetType.XZ))
   );
   Supplier<Block> GLISTERING_FERN = RUBlockUtils.register("glistering_fern", RuNetherPlantBlock::new, GLISTERING_SPROUT);
   Supplier<Block> GLISTERING_BLOOM = RUBlockUtils.register("glistering_bloom", RuNetherPlantBlock::new, GLISTERING_SPROUT);
   Supplier<Block> GLISTERING_WART = RUBlockUtils.register(
      "glistering_wart", p -> new Block(p.mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(0.4F).sound(SoundType.NYLIUM))
   );
   Supplier<Block> GLISTER_BULB = RUBlockUtils.register(
      "glister_bulb",
      p -> new RuNetherDoublePlantBlock(
         VersionBlockHelper.postProcessed(p)
            .replaceable()
            .noCollission()
            .instabreak()
            .sound(SoundType.NETHER_WART)
            .offsetType(OffsetType.XZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 13)
      )
   );
   Supplier<Block> GLISTER_SPIRE = RUBlockUtils.register(
      "glister_spire",
      p -> new RuNetherDoublePlantBlock(
         VersionBlockHelper.postProcessed(p)
            .replaceable()
            .noCollission()
            .instabreak()
            .sound(SoundType.NETHER_SPROUTS)
            .offsetType(OffsetType.XZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 5)
      )
   );
   Supplier<Block> MYCOTOXIC_MUSHROOMS = RUBlockUtils.register(
      "mycotoxic_mushrooms",
      p -> new NetherGroundCoverBlock(
         VersionBlockHelper.postProcessed(p)
            .pushReaction(PushReaction.DESTROY)
            .noCollission()
            .sound(SoundType.SHROOMLIGHT)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(state -> 3 + 3 * (Integer)state.getValue(NetherGroundCoverBlock.AMOUNT))
      )
   );
   Supplier<Block> MYCOTOXIC_DAISY = RUBlockUtils.register(
      "mycotoxic_daisy",
      p -> new RuNetherDoublePlantBlock(
         VersionBlockHelper.postProcessed(p)
            .replaceable()
            .noCollission()
            .instabreak()
            .sound(SoundType.NETHER_SPROUTS)
            .offsetType(OffsetType.XYZ)
            .emissiveRendering((bs, br, bp) -> true)
            .lightLevel(s -> 4)
      )
   );
   Supplier<Block> MYCOTOXIC_GRASS = RUBlockUtils.register(
      "mycotoxic_grass", p -> new RuNetherPlantBlock(p.replaceable().noCollission().instabreak().sound(SoundType.NETHER_SPROUTS).offsetType(OffsetType.XYZ))
   );
   Supplier<Block> MYCOTOXIC_NYLIUM = RUBlockUtils.register(
      "mycotoxic_moss",
      p -> new RuNyliumBlock(
         p.mapColor(MapColor.COLOR_YELLOW).requiresCorrectToolForDrops().strength(0.4F).sound(SoundType.NYLIUM), RUConfiguredFeatures.BONEMEAL_MYCOTOXIC_NYLIUM
      )
   );
   Supplier<Block> POTTED_ALPHA_DANDELION = RUBlockUtils.registerNoItem(
      "potted_alpha_dandelion", p -> new FlowerPotBlock(ALPHA_DANDELION.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_ALPHA_ROSE = RUBlockUtils.registerNoItem("potted_alpha_rose", p -> new FlowerPotBlock(ALPHA_ROSE.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_ASTER = RUBlockUtils.registerNoItem("potted_aster", p -> new FlowerPotBlock(ASTER.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_BLEEDING_HEART = RUBlockUtils.registerNoItem(
      "potted_bleeding_heart", p -> new FlowerPotBlock(BLEEDING_HEART.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_BLUE_LUPINE = RUBlockUtils.registerNoItem("potted_blue_lupine", p -> new FlowerPotBlock(BLUE_LUPINE.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_DAISY = RUBlockUtils.registerNoItem("potted_daisy", p -> new FlowerPotBlock(DAISY.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_DORCEL = RUBlockUtils.registerNoItem("potted_dorcel", p -> new FlowerPotBlock(DORCEL.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_FELICIA_DAISY = RUBlockUtils.registerNoItem(
      "potted_felicia_daisy", p -> new FlowerPotBlock(FELICIA_DAISY.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_FIREWEED = RUBlockUtils.registerNoItem("potted_fireweed", p -> new FlowerPotBlock(FIREWEED.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_HIBISCUS = RUBlockUtils.registerNoItem("potted_hibiscus", p -> new FlowerPotBlock(HIBISCUS.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_HYSSOP = RUBlockUtils.registerNoItem("potted_hyssop", p -> new FlowerPotBlock(HYSSOP.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_MALLOW = RUBlockUtils.registerNoItem("potted_mallow", p -> new FlowerPotBlock(MALLOW.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_PINK_LUPINE = RUBlockUtils.registerNoItem("potted_pink_lupine", p -> new FlowerPotBlock(PINK_LUPINE.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_POPPY_BUSH = RUBlockUtils.registerNoItem("potted_poppy_bush", p -> new FlowerPotBlock(POPPY_BUSH.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_SALMON_POPPY = RUBlockUtils.registerNoItem(
      "potted_salmon_poppy", p -> new FlowerPotBlock(SALMON_POPPY.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_SALMON_POPPY_BUSH = RUBlockUtils.registerNoItem(
      "potted_salmon_poppy_bush", p -> new FlowerPotBlock(SALMON_POPPY_BUSH.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_PURPLE_LUPINE = RUBlockUtils.registerNoItem(
      "potted_purple_lupine", p -> new FlowerPotBlock(PURPLE_LUPINE.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_RED_LUPINE = RUBlockUtils.registerNoItem("potted_red_lupine", p -> new FlowerPotBlock(RED_LUPINE.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_TSUBAKI = RUBlockUtils.registerNoItem("potted_tsubaki", p -> new FlowerPotBlock(TSUBAKI.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_ORANGE_CONEFLOWER = RUBlockUtils.registerNoItem(
      "potted_orange_coneflower", p -> new FlowerPotBlock(ORANGE_CONEFLOWER.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_PURPLE_CONEFLOWER = RUBlockUtils.registerNoItem(
      "potted_purple_coneflower", p -> new FlowerPotBlock(PURPLE_CONEFLOWER.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_WARATAH = RUBlockUtils.registerNoItem("potted_waratah", p -> new FlowerPotBlock(WARATAH.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_WHITE_TRILLIUM = RUBlockUtils.registerNoItem(
      "potted_white_trillium", p -> new FlowerPotBlock(WHITE_TRILLIUM.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_WILTING_TRILLIUM = RUBlockUtils.registerNoItem(
      "potted_wilting_trillium", p -> new FlowerPotBlock(WILTING_TRILLIUM.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_YELLOW_LUPINE = RUBlockUtils.registerNoItem(
      "potted_yellow_lupine", p -> new FlowerPotBlock(YELLOW_LUPINE.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_GLISTERING_BLOOM = RUBlockUtils.registerNoItem(
      "potted_glistering_bloom", p -> new FlowerPotBlock(GLISTERING_BLOOM.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_DAY_LILY = RUBlockUtils.registerNoItem("potted_day_lily", p -> new FlowerPotBlock(DAY_LILY.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_MEADOW_SAGE = RUBlockUtils.registerNoItem("potted_meadow_sage", p -> new FlowerPotBlock(MEADOW_SAGE.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_DUSKTRAP = RUBlockUtils.registerNoItem("potted_dusktrap", p -> new FlowerPotBlock(DUSKTRAP.get(), p), Blocks.POTTED_ALLIUM);
   Supplier<Block> POTTED_CORPSE_FLOWER = RUBlockUtils.registerNoItem(
      "potted_corpse_flower", p -> new FlowerPotBlock(CORPSE_FLOWER.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_COBALT_EARLIGHT = RUBlockUtils.registerNoItem(
      "potted_cobalt_earlight", p -> new FlowerPotBlock(COBALT_EARLIGHT.get(), p.lightLevel(s -> 8)), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_TALL_COBALT_EARLIGHT = RUBlockUtils.registerNoItem(
      "potted_tall_cobalt_earlight", p -> new FlowerPotBlock(TALL_COBALT_EARLIGHT.get(), p.lightLevel(s -> 8)), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_MYCOTOXIC_DAISY = RUBlockUtils.registerNoItem(
      "potted_mycotoxic_daisy", p -> new FlowerPotBlock(MYCOTOXIC_DAISY.get(), p.lightLevel(s -> 8)), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_GLISTER_SPIRE = RUBlockUtils.registerNoItem(
      "potted_glister_spire", p -> new FlowerPotBlock(GLISTER_SPIRE.get(), p), Blocks.POTTED_ALLIUM
   );
   Supplier<Block> POTTED_BLUE_BIOSHROOM = RUBlockUtils.registerNoItem(
      "potted_blue_bioshroom", p -> new FlowerPotBlock(BLUE_BIOSHROOM.get(), p.lightLevel(s -> 10)), Blocks.POTTED_BROWN_MUSHROOM
   );
   Supplier<Block> POTTED_GREEN_BIOSHROOM = RUBlockUtils.registerNoItem(
      "potted_green_bioshroom", p -> new FlowerPotBlock(GREEN_BIOSHROOM.get(), p.lightLevel(s -> 10)), Blocks.POTTED_BROWN_MUSHROOM
   );
   Supplier<Block> POTTED_PINK_BIOSHROOM = RUBlockUtils.registerNoItem(
      "potted_pink_bioshroom", p -> new FlowerPotBlock(PINK_BIOSHROOM.get(), p.lightLevel(s -> 10)), Blocks.POTTED_BROWN_MUSHROOM
   );
   Supplier<Block> POTTED_YELLOW_BIOSHROOM = RUBlockUtils.registerNoItem(
      "potted_yellow_bioshroom", p -> new FlowerPotBlock(YELLOW_BIOSHROOM.get(), p.lightLevel(s -> 10)), Blocks.POTTED_BROWN_MUSHROOM
   );
   Supplier<Block> POTTED_TALL_BLUE_BIOSHROOM = RUBlockUtils.registerNoItem(
      "potted_tall_blue_bioshroom", p -> new FlowerPotBlock(TALL_BLUE_BIOSHROOM.get(), p.lightLevel(s -> 10)), Blocks.POTTED_BROWN_MUSHROOM
   );
   Supplier<Block> POTTED_TALL_GREEN_BIOSHROOM = RUBlockUtils.registerNoItem(
      "potted_tall_green_bioshroom", p -> new FlowerPotBlock(TALL_GREEN_BIOSHROOM.get(), p.lightLevel(s -> 10)), Blocks.POTTED_BROWN_MUSHROOM
   );
   Supplier<Block> POTTED_TALL_PINK_BIOSHROOM = RUBlockUtils.registerNoItem(
      "potted_tall_pink_bioshroom", p -> new FlowerPotBlock(TALL_PINK_BIOSHROOM.get(), p.lightLevel(s -> 10)), Blocks.POTTED_BROWN_MUSHROOM
   );
   Supplier<Block> POTTED_TALL_YELLOW_BIOSHROOM = RUBlockUtils.registerNoItem(
      "potted_tall_yellow_bioshroom", p -> new FlowerPotBlock(TALL_YELLOW_BIOSHROOM.get(), p.lightLevel(s -> 10)), Blocks.POTTED_BROWN_MUSHROOM
   );
   Supplier<Block> POTTED_BARREL_CACTUS = RUBlockUtils.registerNoItem(
      "potted_barrel_cactus", p -> new FlowerPotBlock(BARREL_CACTUS.get(), p), Blocks.POTTED_CACTUS
   );
   Supplier<Block> POTTED_CAVE_HYSSOP = RUBlockUtils.registerNoItem("potted_cave_hyssop", p -> new FlowerPotBlock(CAVE_HYSSOP.get(), p), Blocks.POTTED_ALLIUM);
   ColoredSet SNOWBELLES = new ColoredSet(
      color -> RUBlockUtils.register(color.getName() + "_snowbelle", p -> new LargeFlowerBlock(MobEffects.MOVEMENT_SLOWDOWN, 10.0F, p), Blocks.DANDELION)
   );
   ColoredSet POTTED_SNOWBELLES = new ColoredSet(
      color -> RUBlockUtils.registerNoItem(
         "potted_" + color.getName() + "_snowbelle", p -> new FlowerPotBlock(SNOWBELLES.getMap().get(color), p), Blocks.POTTED_ALLIUM
      )
   );

   static void applyAliases(BiConsumer<ResourceLocation, ResourceLocation> consumer) {
      consumer.accept(RegionsUnexplored.id("medium_grass"), RegionsUnexplored.id("grass_sprouts"));
      consumer.accept(RegionsUnexplored.id("stone_bud"), RegionsUnexplored.id("grass_sprouts"));
      consumer.accept(RegionsUnexplored.id("cactus_flower"), RegionsUnexplored.id("saguaro_cactus_flower"));
      consumer.accept(RegionsUnexplored.id("potted_cactus_flower"), RegionsUnexplored.id("potted_saguaro_cactus_flower"));
      consumer.accept(RegionsUnexplored.id("maple_leaf_pile"), RegionsUnexplored.id("maple_leaf_litter"));
      consumer.accept(RegionsUnexplored.id("red_maple_leaf_pile"), RegionsUnexplored.id("red_maple_leaf_litter"));
      consumer.accept(RegionsUnexplored.id("orange_maple_leaf_pile"), RegionsUnexplored.id("orange_maple_leaf_litter"));
      consumer.accept(RegionsUnexplored.id("silver_birch_leaf_pile"), RegionsUnexplored.id("silver_birch_leaf_litter"));
      consumer.accept(RegionsUnexplored.id("enchanted_birch_leaf_pile"), RegionsUnexplored.id("enchanted_birch_leaf_litter"));
      consumer.accept(RegionsUnexplored.id("mauve_branch"), RegionsUnexplored.id("wisteria_branch"));
      consumer.accept(RegionsUnexplored.id("mauve_shrub"), RegionsUnexplored.id("lavender_wisteria_shrub"));
      consumer.accept(RegionsUnexplored.id("mauve_leaves"), RegionsUnexplored.id("lavender_wisteria_leaves"));
      consumer.accept(RegionsUnexplored.id("mauve_sapling"), RegionsUnexplored.id("lavender_wisteria_sapling"));
      consumer.accept(RegionsUnexplored.id("potted_mauve_sapling"), RegionsUnexplored.id("potted_lavender_wisteria_sapling"));
      consumer.accept(RegionsUnexplored.id("enchanted_birch_shrub"), RegionsUnexplored.id("sky_wisteria_shrub"));
      consumer.accept(RegionsUnexplored.id("enchanted_birch_leaves"), RegionsUnexplored.id("sky_wisteria_leaves"));
      consumer.accept(RegionsUnexplored.id("enchanted_birch_sapling"), RegionsUnexplored.id("sky_wisteria_sapling"));
      consumer.accept(RegionsUnexplored.id("potted_enchanted_birch_sapling"), RegionsUnexplored.id("potted_sky_wisteria_sapling"));
      consumer.accept(RegionsUnexplored.id("mauve_log"), RegionsUnexplored.id("wisteria_log"));
      consumer.accept(RegionsUnexplored.id("mauve_wood"), RegionsUnexplored.id("wisteria_wood"));
      consumer.accept(RegionsUnexplored.id("stripped_mauve_log"), RegionsUnexplored.id("stripped_wisteria_log"));
      consumer.accept(RegionsUnexplored.id("stripped_mauve_wood"), RegionsUnexplored.id("stripped_wisteria_wood"));
      consumer.accept(RegionsUnexplored.id("mauve_planks"), RegionsUnexplored.id("wisteria_planks"));
      consumer.accept(RegionsUnexplored.id("mauve_stairs"), RegionsUnexplored.id("wisteria_stairs"));
      consumer.accept(RegionsUnexplored.id("mauve_slab"), RegionsUnexplored.id("wisteria_slab"));
      consumer.accept(RegionsUnexplored.id("mauve_fence"), RegionsUnexplored.id("wisteria_fence"));
      consumer.accept(RegionsUnexplored.id("mauve_fence_gate"), RegionsUnexplored.id("wisteria_fence_gate"));
      consumer.accept(RegionsUnexplored.id("mauve_door"), RegionsUnexplored.id("wisteria_door"));
      consumer.accept(RegionsUnexplored.id("mauve_trapdoor"), RegionsUnexplored.id("wisteria_trapdoor"));
      consumer.accept(RegionsUnexplored.id("mauve_pressure_plate"), RegionsUnexplored.id("wisteria_pressure_plate"));
      consumer.accept(RegionsUnexplored.id("mauve_button"), RegionsUnexplored.id("wisteria_button"));
      consumer.accept(RegionsUnexplored.id("mauve_sign"), RegionsUnexplored.id("wisteria_sign"));
      consumer.accept(RegionsUnexplored.id("mauve_wall_sign"), RegionsUnexplored.id("wisteria_wall_sign"));
      consumer.accept(RegionsUnexplored.id("mauve_hanging_sign"), RegionsUnexplored.id("wisteria_hanging_sign"));
      consumer.accept(RegionsUnexplored.id("mauve_wall_hanging_sign"), RegionsUnexplored.id("wisteria_wall_hanging_sign"));
   }

   static void init() {
   }

   static void initPostRegistryFreeze() {
      ((VillagerProfessionAccessor)VillagerProfession.FARMER)
         .regionsUnexplored$setSecondaryPoi(
            ImmutableSet.builder().addAll(VillagerProfession.FARMER.secondaryPoi()).add(PEAT_FARMLAND.get()).add(SILT_FARMLAND.get()).build()
         );
   }
}
