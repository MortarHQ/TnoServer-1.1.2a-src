package net.jadenxgamer.netherexp.registry;

import java.awt.Color;
import java.util.function.Supplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.BeardBlock;
import net.jadenxgamer.netherexp.core.block.BlackIceBlock;
import net.jadenxgamer.netherexp.core.block.BlackIcicleBlock;
import net.jadenxgamer.netherexp.core.block.BlottedNetherrackBlock;
import net.jadenxgamer.netherexp.core.block.BoneFenceBlock;
import net.jadenxgamer.netherexp.core.block.BonePikeBlock;
import net.jadenxgamer.netherexp.core.block.BonemealSpreadBlock;
import net.jadenxgamer.netherexp.core.block.BrazierChestBlock;
import net.jadenxgamer.netherexp.core.block.BuriedConverterBlock;
import net.jadenxgamer.netherexp.core.block.BurningSkullBlock;
import net.jadenxgamer.netherexp.core.block.CarvedSorrowsquashBlock;
import net.jadenxgamer.netherexp.core.block.CerebrageSkullBlock;
import net.jadenxgamer.netherexp.core.block.CiergeOfTreacheryBlock;
import net.jadenxgamer.netherexp.core.block.DiscernmentGlassBlock;
import net.jadenxgamer.netherexp.core.block.DriftingSoulsBlock;
import net.jadenxgamer.netherexp.core.block.EctoSoulSandBlock;
import net.jadenxgamer.netherexp.core.block.EctoplasmCauldronBlock;
import net.jadenxgamer.netherexp.core.block.FrogmistBlock;
import net.jadenxgamer.netherexp.core.block.GargoyleStatueBlock;
import net.jadenxgamer.netherexp.core.block.GeyserBlock;
import net.jadenxgamer.netherexp.core.block.GildedBlock;
import net.jadenxgamer.netherexp.core.block.HazeBlock;
import net.jadenxgamer.netherexp.core.block.InscribedPanelBlock;
import net.jadenxgamer.netherexp.core.block.IvyBlock;
import net.jadenxgamer.netherexp.core.block.JNECampfireBlock;
import net.jadenxgamer.netherexp.core.block.JNEHorizontalDirectionalBlock;
import net.jadenxgamer.netherexp.core.block.JNELayerBlock;
import net.jadenxgamer.netherexp.core.block.JNEPathBlock;
import net.jadenxgamer.netherexp.core.block.JNETorchBlock;
import net.jadenxgamer.netherexp.core.block.LesionBlock;
import net.jadenxgamer.netherexp.core.block.LiquidloggedTransparentBlock;
import net.jadenxgamer.netherexp.core.block.MagmaCreamBlock;
import net.jadenxgamer.netherexp.core.block.NetherFlowerBlock;
import net.jadenxgamer.netherexp.core.block.NetherPizzaBlock;
import net.jadenxgamer.netherexp.core.block.OreFallingBlock;
import net.jadenxgamer.netherexp.core.block.PetrifiedSwirlsBlock;
import net.jadenxgamer.netherexp.core.block.PyroclastCrustsBlock;
import net.jadenxgamer.netherexp.core.block.SculkGrinderBlock;
import net.jadenxgamer.netherexp.core.block.ShotgunBarrelBlock;
import net.jadenxgamer.netherexp.core.block.SiltMarramBlock;
import net.jadenxgamer.netherexp.core.block.SixDirectionalBlock;
import net.jadenxgamer.netherexp.core.block.SkullCandleBlock;
import net.jadenxgamer.netherexp.core.block.SorrowsquashBlock;
import net.jadenxgamer.netherexp.core.block.SoulCandleBlock;
import net.jadenxgamer.netherexp.core.block.SoulGlassBlock;
import net.jadenxgamer.netherexp.core.block.SoulMagmaBlock;
import net.jadenxgamer.netherexp.core.block.SoulTorchflowerCropBlock;
import net.jadenxgamer.netherexp.core.block.SporeshroomBlock;
import net.jadenxgamer.netherexp.core.block.SuspiciousSoulSandBlock;
import net.jadenxgamer.netherexp.core.block.SwirlsBlock;
import net.jadenxgamer.netherexp.core.block.ThinBlackIceBlock;
import net.jadenxgamer.netherexp.core.block.TreacherousCandleBlock;
import net.jadenxgamer.netherexp.core.block.TreacherousFireBlock;
import net.jadenxgamer.netherexp.core.block.VineStemBodyBlock;
import net.jadenxgamer.netherexp.core.block.VineStemHeadBlock;
import net.jadenxgamer.netherexp.core.block.WarpedWartBlock;
import net.jadenxgamer.netherexp.core.block.WaxenLightableBlock;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.core.misc.JNESoundType;
import net.jadenxgamer.netherexp.core.worldgen.feature.JNEConfiguredFeatures;
import net.jadenxgamer.netherexp.registry.compat.CavernsAndChasmsCompat;
import net.jadenxgamer.netherexp.registry.compat.GardensOfTheDeadCompat;
import net.jadenxgamer.netherexp.registry.compat.OreganizedCompat;
import net.jadenxgamer.netherexp.registry.compat.RubinatedNetherCompat;
import net.jadenxgamer.netherexp.util.CompatUtil;
import net.jadenxgamer.netherexp.util.RegistryHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.NetherSproutsBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;
import team.lodestar.lodestone.systems.block.LodestoneLogBlock;

public class JNEBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, "netherexp");
   public static final Supplier<Block> SOUL_SLATE = RegistryHelper.registerBlock(
      "soul_slate",
      () -> new Block(Properties.of().mapColor(MapColor.COLOR_BROWN).strength(4.0F, 12.0F).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE))
   );
   public static final Supplier<Block> PALE_SOUL_SLATE = RegistryHelper.registerBlock(
      "pale_soul_slate",
      () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(3.0F, 1.0F).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE))
   );
   public static final Supplier<Block> SOUL_SLATE_SLAB = RegistryHelper.registerBlock(
      "soul_slate_slab", () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE.get()))
   );
   public static final Supplier<Block> SOUL_SLATE_STAIRS = RegistryHelper.registerBlock(
      "soul_slate_stairs", () -> new StairBlock(SOUL_SLATE.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE.get()))
   );
   public static final Supplier<Block> SOUL_SLATE_WALL = RegistryHelper.registerBlock(
      "soul_slate_wall", () -> new WallBlock(Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE.get()))
   );
   public static final Supplier<Block> SOUL_SLATE_BRICKS = RegistryHelper.registerBlock(
      "soul_slate_bricks",
      () -> new Block(Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE_BRICKS))
   );
   public static final Supplier<Block> SOUL_SLATE_BRICK_SLAB = RegistryHelper.registerBlock(
      "soul_slate_brick_slab", () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_BRICKS.get()))
   );
   public static final Supplier<Block> SOUL_SLATE_BRICK_STAIRS = RegistryHelper.registerBlock(
      "soul_slate_brick_stairs",
      () -> new StairBlock(SOUL_SLATE_BRICKS.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_BRICKS.get()))
   );
   public static final Supplier<Block> SOUL_SLATE_BRICK_WALL = RegistryHelper.registerBlock(
      "soul_slate_brick_wall", () -> new WallBlock(Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_BRICKS.get()))
   );
   public static final Supplier<Block> ETCHED_SOUL_SLATE_BRICKS = RegistryHelper.registerBlock(
      "etched_soul_slate_bricks",
      () -> new WaxenLightableBlock(
         () -> ParticleTypes.SOUL_FIRE_FLAME,
         Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_BRICKS.get()).lightLevel(WaxenLightableBlock::stateForProperty)
      )
   );
   public static final Supplier<Block> CRACKED_SOUL_SLATE_BRICKS = RegistryHelper.registerBlock(
      "cracked_soul_slate_bricks", () -> new Block(Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_BRICKS.get()))
   );
   public static final Supplier<Block> SOUL_SLATE_BRICK_PILLAR = RegistryHelper.registerBlock(
      "soul_slate_brick_pillar", () -> new RotatedPillarBlock(Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_BRICKS.get()))
   );
   public static final Supplier<Block> CHISELED_SOUL_SLATE_BRICKS = RegistryHelper.registerBlock(
      "chiseled_soul_slate_bricks",
      () -> new WaxenLightableBlock(
         () -> ParticleTypes.SOUL_FIRE_FLAME,
         Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_BRICKS.get()).lightLevel(WaxenLightableBlock::stateForProperty)
      )
   );
   public static final Supplier<Block> SOUL_SLATE_TILES = RegistryHelper.registerBlock(
      "soul_slate_tiles",
      () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(2.0F).requiresCorrectToolForDrops().sound(JNESoundType.SOUL_SLATE_BRICKS))
   );
   public static final Supplier<Block> SOUL_SLATE_TILE_SLAB = RegistryHelper.registerBlock(
      "soul_slate_tile_slab", () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_TILES.get()))
   );
   public static final Supplier<Block> SOUL_SLATE_TILE_STAIRS = RegistryHelper.registerBlock(
      "soul_slate_tile_stairs",
      () -> new StairBlock(SOUL_SLATE_TILES.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_TILES.get()))
   );
   public static final Supplier<Block> SOUL_SLATE_TILE_WALL = RegistryHelper.registerBlock(
      "soul_slate_tile_wall", () -> new WallBlock(Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_TILES.get()))
   );
   public static final Supplier<Block> ETCHED_SOUL_SLATE_TILES = RegistryHelper.registerBlock(
      "etched_soul_slate_tiles",
      () -> new WaxenLightableBlock(
         () -> ParticleTypes.SOUL_FIRE_FLAME, Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_TILES.get()).lightLevel(WaxenLightableBlock::stateForProperty)
      )
   );
   public static final Supplier<Block> CHISELED_SOUL_SLATE_TILES = RegistryHelper.registerBlock(
      "chiseled_soul_slate_tiles",
      () -> new WaxenLightableBlock(
         () -> ParticleTypes.SOUL_FIRE_FLAME, Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE_TILES.get()).lightLevel(WaxenLightableBlock::stateForProperty)
      )
   );
   public static final Supplier<Block> FOSSIL_FUEL_ORE = RegistryHelper.registerBlock(
      "fossil_fuel_ore",
      () -> new DropExperienceBlock(UniformInt.of(0, 2), Properties.ofLegacyCopy(Blocks.SOUL_SOIL).strength(0.6F).sound(JNESoundType.FOSSIL_ORE))
   );
   public static final Supplier<Block> FOSSIL_ORE = RegistryHelper.registerBlock(
      "fossil_ore",
      () -> new BuriedConverterBlock(
         ConstantInt.of(0),
         FOSSIL_FUEL_ORE,
         JNEConfigs.FOSSIL_ORE_CONVERSION_CHANCE,
         Properties.ofLegacyCopy(Blocks.SOUL_SOIL).randomTicks().strength(0.6F).sound(JNESoundType.FOSSIL_ORE)
      )
   );
   public static final Supplier<Block> SOUL_SWIRLS = RegistryHelper.registerBlock(
      "soul_swirls",
      () -> new SwirlsBlock(
         null,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .replaceable()
            .noCollission()
            .instabreak()
            .lightLevel(state -> state.getValue(SwirlsBlock.ACTIVE) ? 6 : 0)
            .sound(JNESoundType.SOUL_SWIRLS)
      )
   );
   public static final Supplier<Block> PETRIFIED_SWIRLS = RegistryHelper.registerBlock(
      "petrified_swirls", () -> new PetrifiedSwirlsBlock(Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.7F, 1200.0F).sound(JNESoundType.BLACK_ICE))
   );
   public static final Supplier<Block> SOUL_CANDLE = RegistryHelper.registerBlock(
      "soul_candle",
      () -> new SoulCandleBlock(
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .noOcclusion()
            .strength(1.0F)
            .lightLevel(SoulCandleBlock.STATE_TO_LUMINANCE)
            .sound(JNESoundType.SOUL_CANDLE)
      )
   );
   public static final Supplier<Block> PHASMO_SLAB = RegistryHelper.registerBlock(
      "phasmo_slab",
      () -> new SlabBlock(
         Properties.of()
            .strength(1.0F, 1200.0F)
            .lightLevel(state -> 2)
            .emissiveRendering((a, b, c) -> false)
            .requiresCorrectToolForDrops()
            .instrument(NoteBlockInstrument.CHIME)
            .sound(JNESoundType.BLACK_ICE)
      )
   );
   public static final Supplier<Block> SOUL_GLASS = RegistryHelper.registerBlock(
      "soul_glass",
      () -> new SoulGlassBlock(
         new Color(937550),
         () -> ParticleTypes.SOUL_FIRE_FLAME,
         Properties.ofLegacyCopy(Blocks.GLASS)
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
            .lightLevel(state -> state.getValue(SoulGlassBlock.LIT) ? 12 : 0)
            .noOcclusion()
            .strength(0.3F, 1200.0F)
            .sound(JNESoundType.SOUL_GLASS)
      )
   );
   public static final Supplier<Block> WAXEN_SOUL_GLASS = RegistryHelper.registerBlock(
      "waxen_soul_glass",
      () -> new SoulGlassBlock(
         new Color(7540238),
         JNEParticleTypes.TREACHEROUS_FLAME,
         Properties.ofLegacyCopy(Blocks.GLASS)
            .mapColor(MapColor.TERRACOTTA_RED)
            .lightLevel(state -> state.getValue(SoulGlassBlock.LIT) ? 10 : 0)
            .noOcclusion()
            .strength(0.3F, 1200.0F)
            .sound(JNESoundType.SOUL_GLASS)
      )
   );
   public static final Supplier<Block> DISCERNMENT_GLASS = RegistryHelper.registerBlock(
      "discernment_glass",
      () -> new DiscernmentGlassBlock(
         Properties.ofLegacyCopy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(0.3F, 1200.0F).sound(JNESoundType.SOUL_GLASS)
      )
   );
   public static final Supplier<Block> ECTO_SOUL_SAND = RegistryHelper.registerBlock(
      "ecto_soul_sand", () -> new EctoSoulSandBlock(Properties.ofLegacyCopy(Blocks.SOUL_SAND).lightLevel(state -> 4).randomTicks())
   );
   public static final Supplier<Block> SUSPICIOUS_SOUL_SAND = RegistryHelper.registerBlock(
      "suspicious_soul_sand",
      () -> new SuspiciousSoulSandBlock(
         Properties.ofLegacyCopy(Blocks.SOUL_SAND)
            .randomTicks()
            .speedFactor(0.2F)
            .strength(0.25F)
            .pushReaction(PushReaction.DESTROY)
            .sound(JNESoundType.SUSPICIOUS_SOUL_SAND)
      )
   );
   public static final Supplier<Block> SOUL_MAGMA_BLOCK = RegistryHelper.registerBlock(
      "soul_magma_block",
      () -> new SoulMagmaBlock(
         Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .strength(0.5F)
            .lightLevel(state -> 3)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .emissiveRendering((a, b, blockPos) -> false)
            .requiresCorrectToolForDrops()
            .isValidSpawn((state, level, pos, entity) -> entity.fireImmune())
            .sound(JNESoundType.SOUL_MAGMA_BLOCK)
      )
   );
   public static final Supplier<Block> SOUL_SOIL_LAYER = RegistryHelper.registerBlock(
      "soul_soil_layer",
      () -> new JNELayerBlock(
         JNETags.Blocks.SOUL_LAYER_CAN_SURVIVE_ON,
         JNETags.Blocks.SOUL_LAYER_CANNOT_SURVIVE_ON,
         Properties.ofLegacyCopy(Blocks.SOUL_SOIL)
            .strength(0.1F)
            .requiresCorrectToolForDrops()
            .replaceable()
            .forceSolidOff()
            .isViewBlocking((state, world, pos) -> (Integer)state.getValue(JNELayerBlock.LAYERS) >= 8)
      )
   );
   public static final Supplier<Block> ECTOPLASM_CAULDRON = RegistryHelper.registerBlockWithoutItem(
      "ectoplasm_cauldron", () -> new EctoplasmCauldronBlock(Properties.ofLegacyCopy(Blocks.CAULDRON).lightLevel(state -> 12))
   );
   public static final Supplier<Block> DRIFTING_SOULS = RegistryHelper.registerBlock(
      "drifting_souls",
      () -> new DriftingSoulsBlock(
         Properties.of()
            .noCollission()
            .noOcclusion()
            .replaceable()
            .instabreak()
            .lightLevel(state -> 1)
            .pushReaction(PushReaction.DESTROY)
            .sound(JNESoundType.HAZE_BLOCK)
      )
   );
   public static final Supplier<Block> BRAZIER_CHEST = RegistryHelper.registerBlock(
      "brazier_chest",
      () -> new BrazierChestBlock(
         Properties.of()
            .strength(120.0F, 1200.0F)
            .lightLevel(state -> state.getValue(BrazierChestBlock.LOCKED) ? 0 : 15)
            .isRedstoneConductor((a, b, c) -> false)
            .sound(JNESoundType.SOUL_SLATE)
      )
   );
   public static final Supplier<Block> CIERGE_OF_TREACHERY = RegistryHelper.registerBlock(
      "cierge_of_treachery",
      () -> new CiergeOfTreacheryBlock(
         Properties.of()
            .strength(120.0F, 1200.0F)
            .noOcclusion()
            .lightLevel(blockState -> blockState.getValue(CiergeOfTreacheryBlock.LIT) ? 10 : 0)
            .sound(JNESoundType.SOUL_SLATE)
      )
   );
   public static final Supplier<Block> HAZE_BLOCK = RegistryHelper.registerBlockWithoutItem(
      "haze_block", () -> new HazeBlock(Properties.of().mapColor(MapColor.COLOR_PINK).instabreak().noOcclusion().sound(JNESoundType.HAZE_BLOCK))
   );
   public static final Supplier<Block> SCULK_GRINDER = RegistryHelper.registerBlock(
      "sculk_grinder", () -> new SculkGrinderBlock(Properties.of().strength(80.0F, 1200.0F).lightLevel(state -> 13).sound(SoundType.SCULK_CATALYST))
   );
   public static final Supplier<Block> BLACK_ICE = RegistryHelper.registerBlock(
      "black_ice",
      () -> new BlackIceBlock(
         Properties.ofLegacyCopy(Blocks.PACKED_ICE)
            .mapColor(MapColor.COLOR_BLACK)
            .randomTicks()
            .requiresCorrectToolForDrops()
            .strength(0.3F)
            .lightLevel(state -> 2)
            .sound(JNESoundType.BLACK_ICE)
      )
   );
   public static final Supplier<Block> BLACK_ICICLE = RegistryHelper.registerBlock(
      "black_icicle",
      () -> new BlackIcicleBlock(
         Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .requiresCorrectToolForDrops()
            .forceSolidOn()
            .noOcclusion()
            .randomTicks()
            .strength(0.1F, 3.0F)
            .dynamicShape()
            .offsetType(OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)
            .sound(JNESoundType.BLACK_ICE)
      )
   );
   public static final Supplier<Block> THIN_BLACK_ICE = RegistryHelper.registerBlock(
      "thin_black_ice",
      () -> new ThinBlackIceBlock(
         Properties.ofLegacyCopy((BlockBehaviour)BLACK_ICE.get()).mapColor(MapColor.COLOR_BLACK).strength(0.05F).randomTicks().noOcclusion()
      )
   );
   public static final Supplier<Block> SOUL_PERMAFROST = RegistryHelper.registerBlock(
      "soul_permafrost", () -> new Block(Properties.ofLegacyCopy(Blocks.SOUL_SOIL).speedFactor(0.9F).friction(0.98F))
   );
   public static final Supplier<Block> SOUL_PATH = RegistryHelper.registerBlock(
      "soul_path", () -> new JNEPathBlock(() -> Blocks.SOUL_SOIL, Properties.ofLegacyCopy(Blocks.SOUL_SOIL))
   );
   public static final Supplier<Block> CRIMSON_NYLIUM_PATH = RegistryHelper.registerBlock(
      "crimson_nylium_path", () -> new JNEPathBlock(() -> Blocks.NETHERRACK, Properties.ofLegacyCopy(Blocks.CRIMSON_NYLIUM))
   );
   public static final Supplier<Block> WARPED_NYLIUM_PATH = RegistryHelper.registerBlock(
      "warped_nylium_path", () -> new JNEPathBlock(() -> Blocks.NETHERRACK, Properties.ofLegacyCopy(Blocks.WARPED_NYLIUM))
   );
   public static final Supplier<Block> BLOTTED_NETHERRACK = RegistryHelper.registerBlock(
      "blotted_netherrack", () -> new BlottedNetherrackBlock(Properties.ofLegacyCopy(Blocks.NETHERRACK))
   );
   public static final Supplier<Block> SMOOTH_NETHERRACK = RegistryHelper.registerBlock(
      "smooth_netherrack", () -> new Block(Properties.ofLegacyCopy(Blocks.NETHERRACK).strength(0.4F).requiresCorrectToolForDrops())
   );
   public static final Supplier<Block> SMOOTH_NETHERRACK_SLAB = RegistryHelper.registerBlock(
      "smooth_netherrack_slab", () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)SMOOTH_NETHERRACK.get()))
   );
   public static final Supplier<Block> SMOOTH_NETHERRACK_STAIRS = RegistryHelper.registerBlock(
      "smooth_netherrack_stairs",
      () -> new StairBlock(SMOOTH_NETHERRACK.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)SMOOTH_NETHERRACK.get()))
   );
   public static final Supplier<Block> SMOOTH_NETHERRACK_WALL = RegistryHelper.registerBlock(
      "smooth_netherrack_wall", () -> new WallBlock(Properties.ofLegacyCopy((BlockBehaviour)SMOOTH_NETHERRACK.get()))
   );
   public static final Supplier<Block> NETHERRACK_BRICKS = RegistryHelper.registerBlock(
      "netherrack_bricks",
      () -> new Block(Properties.ofLegacyCopy(Blocks.NETHERRACK).strength(0.4F).requiresCorrectToolForDrops().sound(JNESoundType.NETHERRACK_BRICKS))
   );
   public static final Supplier<Block> NETHERRACK_BRICK_SLAB = RegistryHelper.registerBlock(
      "netherrack_brick_slab", () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)NETHERRACK_BRICKS.get()))
   );
   public static final Supplier<Block> NETHERRACK_BRICK_STAIRS = RegistryHelper.registerBlock(
      "netherrack_brick_stairs",
      () -> new StairBlock(NETHERRACK_BRICKS.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)NETHERRACK_BRICKS.get()))
   );
   public static final Supplier<Block> NETHERRACK_BRICK_WALL = RegistryHelper.registerBlock(
      "netherrack_brick_wall", () -> new WallBlock(Properties.ofLegacyCopy((BlockBehaviour)NETHERRACK_BRICKS.get()))
   );
   public static final Supplier<Block> NETHERRACK_TILES = RegistryHelper.registerBlock(
      "netherrack_tiles", () -> new Block(Properties.ofLegacyCopy((BlockBehaviour)NETHERRACK_BRICKS.get()))
   );
   public static final Supplier<Block> NETHERRACK_PILLAR = RegistryHelper.registerBlock(
      "netherrack_pillar", () -> new RotatedPillarBlock(Properties.ofLegacyCopy((BlockBehaviour)NETHERRACK_BRICKS.get()))
   );
   public static final Supplier<Block> BASALT_SLAB = RegistryHelper.registerBlock("basalt_slab", () -> new SlabBlock(Properties.ofLegacyCopy(Blocks.BASALT)));
   public static final Supplier<Block> BASALT_STAIRS = RegistryHelper.registerBlock(
      "basalt_stairs", () -> new StairBlock(Blocks.BASALT.defaultBlockState(), Properties.ofLegacyCopy(Blocks.BASALT))
   );
   public static final Supplier<Block> BASALT_WALL = RegistryHelper.registerBlock("basalt_wall", () -> new WallBlock(Properties.ofLegacyCopy(Blocks.BASALT)));
   public static final Supplier<Block> POLISHED_BASALT_SLAB = RegistryHelper.registerBlock(
      "polished_basalt_slab", () -> new SlabBlock(Properties.ofLegacyCopy(Blocks.POLISHED_BASALT))
   );
   public static final Supplier<Block> POLISHED_BASALT_STAIRS = RegistryHelper.registerBlock(
      "polished_basalt_stairs", () -> new StairBlock(Blocks.POLISHED_BASALT.defaultBlockState(), Properties.ofLegacyCopy(Blocks.POLISHED_BASALT))
   );
   public static final Supplier<Block> POLISHED_BASALT_WALL = RegistryHelper.registerBlock(
      "polished_basalt_wall", () -> new WallBlock(Properties.ofLegacyCopy(Blocks.POLISHED_BASALT))
   );
   public static final Supplier<Block> POLISHED_BASALT_BRICKS = RegistryHelper.registerBlock(
      "polished_basalt_bricks", () -> new GildedBlock(Properties.ofLegacyCopy(Blocks.POLISHED_BASALT).sound(JNESoundType.POLISHED_BASALT_BRICKS))
   );
   public static final Supplier<Block> POLISHED_BASALT_BRICK_SLAB = RegistryHelper.registerBlock(
      "polished_basalt_brick_slab", () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)POLISHED_BASALT_BRICKS.get()))
   );
   public static final Supplier<Block> POLISHED_BASALT_BRICK_STAIRS = RegistryHelper.registerBlock(
      "polished_basalt_brick_stairs",
      () -> new StairBlock(POLISHED_BASALT_BRICKS.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)POLISHED_BASALT_BRICKS.get()))
   );
   public static final Supplier<Block> POLISHED_BASALT_BRICK_WALL = RegistryHelper.registerBlock(
      "polished_basalt_brick_wall", () -> new WallBlock(Properties.ofLegacyCopy((BlockBehaviour)POLISHED_BASALT_BRICKS.get()))
   );
   public static final Supplier<Block> NETHERITE_PLATED_BLOCK = RegistryHelper.registerItemPropertiesBlock(
      "netherite_plated_block",
      () -> new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.5F, 0.5F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> CUT_NETHERITE_BLOCK = RegistryHelper.registerItemPropertiesBlock(
      "cut_netherite_block",
      () -> new Block(Properties.ofLegacyCopy((BlockBehaviour)NETHERITE_PLATED_BLOCK.get())),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> CUT_NETHERITE_SLAB = RegistryHelper.registerItemPropertiesBlock(
      "cut_netherite_slab",
      () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)CUT_NETHERITE_BLOCK.get())),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> CUT_NETHERITE_STAIRS = RegistryHelper.registerItemPropertiesBlock(
      "cut_netherite_stairs",
      () -> new StairBlock(CUT_NETHERITE_BLOCK.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)CUT_NETHERITE_BLOCK.get())),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> CUT_NETHERITE_PILLAR = RegistryHelper.registerItemPropertiesBlock(
      "cut_netherite_pillar",
      () -> new RotatedPillarBlock(Properties.ofLegacyCopy((BlockBehaviour)CUT_NETHERITE_BLOCK.get())),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> NETHERITE_GRATE = RegistryHelper.registerItemPropertiesBlock(
      "netherite_grate",
      () -> new LiquidloggedTransparentBlock(
         Properties.ofLegacyCopy((BlockBehaviour)CUT_NETHERITE_BLOCK.get())
            .noOcclusion()
            .sound(JNESoundType.NETHERITE_GRATE)
            .lightLevel(LiquidloggedTransparentBlock::stateToProperty)
      ),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> RUSTY_NETHERITE_PLATED_BLOCK = RegistryHelper.registerItemPropertiesBlock(
      "rusty_netherite_plated_block",
      () -> new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.5F, 0.5F).requiresCorrectToolForDrops().sound(SoundType.ANCIENT_DEBRIS)),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> RUSTY_CUT_NETHERITE_BLOCK = RegistryHelper.registerItemPropertiesBlock(
      "rusty_cut_netherite_block",
      () -> new Block(Properties.ofLegacyCopy((BlockBehaviour)RUSTY_NETHERITE_PLATED_BLOCK.get())),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> RUSTY_CUT_NETHERITE_SLAB = RegistryHelper.registerItemPropertiesBlock(
      "rusty_cut_netherite_slab",
      () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)RUSTY_CUT_NETHERITE_BLOCK.get())),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> RUSTY_CUT_NETHERITE_STAIRS = RegistryHelper.registerItemPropertiesBlock(
      "rusty_cut_netherite_stairs",
      () -> new StairBlock(RUSTY_CUT_NETHERITE_BLOCK.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)RUSTY_CUT_NETHERITE_BLOCK.get())),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> RUSTY_CUT_NETHERITE_PILLAR = RegistryHelper.registerItemPropertiesBlock(
      "rusty_cut_netherite_pillar",
      () -> new RotatedPillarBlock(Properties.ofLegacyCopy((BlockBehaviour)RUSTY_CUT_NETHERITE_BLOCK.get())),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> RUSTY_NETHERITE_GRATE = RegistryHelper.registerItemPropertiesBlock(
      "rusty_netherite_grate",
      () -> new LiquidloggedTransparentBlock(
         Properties.ofLegacyCopy((BlockBehaviour)RUSTY_CUT_NETHERITE_BLOCK.get())
            .noOcclusion()
            .sound(JNESoundType.RUSTY_NETHERITE_GRATE)
            .lightLevel(LiquidloggedTransparentBlock::stateToProperty)
      ),
      new net.minecraft.world.item.Item.Properties().fireResistant()
   );
   public static final Supplier<Block> CEREBRAGE_KALE = RegistryHelper.registerBlock(
      "cerebrage_kale",
      () -> new AmethystClusterBlock(
         7.0F, 3.0F, Properties.of().instabreak().noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.AZALEA_LEAVES)
      )
   );
   public static final Supplier<Block> CEREBRAGE_CLARET_STEM = RegistryHelper.registerBlock(
      "cerebrage_claret_stem",
      () -> new LodestoneLogBlock(
         Properties.ofLegacyCopy(Blocks.WARPED_STEM).mapColor(MapColor.CRIMSON_HYPHAE).sound(JNESoundType.CEREBRAGE_STEM), JNEBlocks.STRIPPED_CLARET_STEM
      )
   );
   public static final Supplier<Block> CEREBRAGE_CLARET_HYPHAE = RegistryHelper.registerBlock(
      "cerebrage_claret_hyphae",
      () -> new LodestoneLogBlock(
         Properties.ofLegacyCopy(Blocks.WARPED_HYPHAE).mapColor(MapColor.CRIMSON_HYPHAE).sound(JNESoundType.CEREBRAGE_STEM), JNEBlocks.STRIPPED_CLARET_HYPHAE
      )
   );
   public static final Supplier<Block> STRIPPED_CLARET_STEM = RegistryHelper.registerBlock(
      "stripped_claret_stem",
      () -> new RotatedPillarBlock(Properties.ofLegacyCopy(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.NETHER).sound(JNESoundType.CEREBRAGE_STEM))
   );
   public static final Supplier<Block> STRIPPED_CLARET_HYPHAE = RegistryHelper.registerBlock(
      "stripped_claret_hyphae",
      () -> new RotatedPillarBlock(Properties.ofLegacyCopy(Blocks.STRIPPED_WARPED_HYPHAE).mapColor(MapColor.NETHER).sound(JNESoundType.CEREBRAGE_STEM))
   );
   public static final Supplier<Block> CLARET_PLANKS = RegistryHelper.registerBlock(
      "claret_planks", () -> new Block(Properties.ofLegacyCopy(Blocks.WARPED_PLANKS).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_SLAB = RegistryHelper.registerBlock(
      "claret_slab", () -> new SlabBlock(Properties.ofLegacyCopy(Blocks.WARPED_SLAB).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_STAIRS = RegistryHelper.registerBlock(
      "claret_stairs", () -> new StairBlock(CLARET_PLANKS.get().defaultBlockState(), Properties.ofLegacyCopy(Blocks.WARPED_STAIRS).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_FENCE = RegistryHelper.registerBlock(
      "claret_fence", () -> new FenceBlock(Properties.ofLegacyCopy(Blocks.WARPED_FENCE).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_FENCE_GATE = RegistryHelper.registerBlock(
      "claret_fence_gate", () -> new FenceGateBlock(JNEWoodType.CLARET, Properties.ofLegacyCopy(Blocks.WARPED_FENCE_GATE).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_DOOR = RegistryHelper.registerBlock(
      "claret_door", () -> new DoorBlock(JNEBlockSetType.CLARET, Properties.ofLegacyCopy(Blocks.WARPED_DOOR).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_TRAPDOOR = RegistryHelper.registerBlock(
      "claret_trapdoor", () -> new TrapDoorBlock(JNEBlockSetType.CLARET, Properties.ofLegacyCopy(Blocks.WARPED_TRAPDOOR).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_BUTTON = RegistryHelper.registerBlock(
      "claret_button", () -> new ButtonBlock(JNEBlockSetType.CLARET, 30, Properties.ofLegacyCopy(Blocks.WARPED_BUTTON).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_PRESSURE_PLATE = RegistryHelper.registerBlock(
      "claret_pressure_plate",
      () -> new PressurePlateBlock(JNEBlockSetType.CLARET, Properties.ofLegacyCopy(Blocks.WARPED_PRESSURE_PLATE).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_SIGN = RegistryHelper.registerBlockWithoutItem(
      "claret_sign", () -> new StandingSignBlock(JNEWoodType.CLARET, Properties.ofLegacyCopy(Blocks.WARPED_SIGN).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_WALL_SIGN = RegistryHelper.registerBlockWithoutItem(
      "claret_wall_sign",
      () -> new WallSignBlock(JNEWoodType.CLARET, Properties.ofLegacyCopy(Blocks.WARPED_WALL_SIGN).mapColor(MapColor.NETHER).dropsLike(CLARET_SIGN.get()))
   );
   public static final Supplier<Block> CLARET_HANGING_SIGN = RegistryHelper.registerBlockWithoutItem(
      "claret_hanging_sign",
      () -> new CeilingHangingSignBlock(JNEWoodType.CLARET, Properties.ofLegacyCopy(Blocks.WARPED_HANGING_SIGN).mapColor(MapColor.NETHER))
   );
   public static final Supplier<Block> CLARET_WALL_HANGING_SIGN = RegistryHelper.registerBlockWithoutItem(
      "claret_wall_hanging_sign",
      () -> new WallHangingSignBlock(
         JNEWoodType.CLARET, Properties.ofLegacyCopy(Blocks.WARPED_WALL_HANGING_SIGN).mapColor(MapColor.NETHER).dropsLike(CLARET_HANGING_SIGN.get())
      )
   );
   public static final Supplier<Block> MAGMA_CREAM_BLOCK = RegistryHelper.registerBlock(
      "magma_cream_block", () -> new MagmaCreamBlock(Properties.of().instabreak().noOcclusion().lightLevel(state -> 10).sound(SoundType.HONEY_BLOCK))
   );
   public static final Supplier<Block> QUARTZ_CRYSTAL = RegistryHelper.registerBlock(
      "quartz_crystal",
      () -> new AmethystClusterBlock(7.0F, 3.0F, Properties.of().mapColor(MapColor.QUARTZ).noOcclusion().strength(1.5F).sound(JNESoundType.QUARTZ_BLOCK))
   );
   public static final Supplier<Block> QUARTZ_CRYSTAL_BLOCK = RegistryHelper.registerBlock(
      "quartz_crystal_block",
      () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.QUARTZ).strength(2.5F).requiresCorrectToolForDrops().sound(JNESoundType.QUARTZ_BLOCK))
   );
   public static final Supplier<Block> CRACKED_QUARTZ_BRICKS = RegistryHelper.registerBlock(
      "cracked_quartz_bricks", () -> new Block(Properties.ofLegacyCopy(Blocks.QUARTZ_BLOCK))
   );
   public static final Supplier<Block> CHISELED_QUARTZ_PILLAR = RegistryHelper.registerBlock(
      "chiseled_quartz_pillar", () -> new SixDirectionalBlock(Properties.ofLegacyCopy(Blocks.QUARTZ_PILLAR))
   );
   public static final Supplier<Block> NETHER_BRICK_PILLAR = RegistryHelper.registerBlock(
      "nether_brick_pillar", () -> new RotatedPillarBlock(Properties.ofLegacyCopy(Blocks.NETHER_BRICKS))
   );
   public static final Supplier<Block> RED_MIXED_NETHER_BRICKS = RegistryHelper.registerBlock(
      "red_mixed_nether_bricks", () -> new Block(Properties.ofLegacyCopy(Blocks.NETHER_BRICKS))
   );
   public static final Supplier<Block> BLUE_MIXED_NETHER_BRICKS = RegistryHelper.registerBlock(
      "blue_mixed_nether_bricks", () -> new Block(Properties.ofLegacyCopy(Blocks.NETHER_BRICKS))
   );
   public static final Supplier<Block> BLUE_NETHER_BRICKS = RegistryHelper.registerBlock(
      "blue_nether_bricks", () -> new Block(Properties.ofLegacyCopy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.WARPED_WART_BLOCK))
   );
   public static final Supplier<Block> BLUE_NETHER_BRICK_SLAB = RegistryHelper.registerBlock(
      "blue_nether_brick_slab", () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)BLUE_NETHER_BRICKS.get()))
   );
   public static final Supplier<Block> BLUE_NETHER_BRICK_STAIRS = RegistryHelper.registerBlock(
      "blue_nether_brick_stairs",
      () -> new StairBlock(BLUE_NETHER_BRICKS.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)BLUE_NETHER_BRICKS.get()))
   );
   public static final Supplier<Block> BLUE_NETHER_BRICK_WALL = RegistryHelper.registerBlock(
      "blue_nether_brick_wall", () -> new WallBlock(Properties.ofLegacyCopy((BlockBehaviour)BLUE_NETHER_BRICKS.get()))
   );
   public static final Supplier<Block> NETHER_PIZZA = RegistryHelper.registerBlock(
      "nether_pizza", () -> new NetherPizzaBlock(Properties.of().strength(0.5F).noLootTable().noOcclusion().sound(SoundType.WOOL))
   );
   public static final Supplier<Block> WARPED_WART = RegistryHelper.registerBlock(
      "warped_wart",
      () -> new WarpedWartBlock(
         Properties.of()
            .mapColor(MapColor.WARPED_WART_BLOCK)
            .instabreak()
            .noCollission()
            .noOcclusion()
            .randomTicks()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.NETHER_WART)
      )
   );
   public static final Supplier<Block> WRAITHING_LESION = RegistryHelper.registerBlock(
      "wraithing_lesion",
      () -> new LesionBlock(
         JNEItems.WRAITHING_FLESH,
         Properties.of().mapColor(MapColor.COLOR_BROWN).strength(1.5F, 1.0F).pushReaction(PushReaction.DESTROY).randomTicks().sound(JNESoundType.LESION_BLOCK)
      )
   );
   public static final Supplier<Block> SOUL_TORCHFLOWER = RegistryHelper.registerBlock(
      "soul_torchflower", () -> new NetherFlowerBlock(MobEffects.DIG_SPEED, 12.0F, Properties.ofLegacyCopy(Blocks.TORCHFLOWER))
   );
   public static final Supplier<Block> SOUL_TORCHFLOWER_CROP = RegistryHelper.registerBlockWithoutItem(
      "soul_torchflower_crop", () -> new SoulTorchflowerCropBlock(Properties.ofLegacyCopy(Blocks.TORCHFLOWER_CROP))
   );
   public static final Supplier<Block> SORROWEED = RegistryHelper.registerBlock(
      "sorroweed",
      () -> new BonemealSpreadBlock(
         JNEConfiguredFeatures.SORROWEED_PATCH_BONEMEAL, Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.MOSS)
      )
   );
   public static final Supplier<Block> SORROWSQUASH = RegistryHelper.registerBlock(
      "sorrowsquash",
      () -> new SorrowsquashBlock(Properties.ofLegacyCopy(Blocks.PUMPKIN).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.NETHER_WOOD))
   );
   public static final Supplier<Block> CARVED_SORROWSQUASH = RegistryHelper.registerBlock(
      "carved_sorrowsquash",
      () -> new CarvedSorrowsquashBlock(
         Properties.ofLegacyCopy(Blocks.CARVED_PUMPKIN).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.NETHER_WOOD)
      )
   );
   public static final Supplier<Block> GHOUL_O_LANTERN = RegistryHelper.registerBlock(
      "ghoul_o_lantern",
      () -> new CarvedSorrowsquashBlock(
         Properties.ofLegacyCopy(Blocks.JACK_O_LANTERN).mapColor(MapColor.COLOR_ORANGE).strength(1.0F).lightLevel(state -> 10).sound(SoundType.NETHER_WOOD)
      )
   );
   public static final Supplier<Block> SORROWSQUASH_STEM = RegistryHelper.registerBlockWithoutItem(
      "sorrowsquash_stem",
      () -> new VineStemHeadBlock(
         SORROWSQUASH,
         () -> Items.PUMPKIN_SEEDS,
         Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().randomTicks().sound(SoundType.NETHER_WOOD)
      )
   );
   public static final Supplier<Block> SORROWSQUASH_STEM_PLANT = RegistryHelper.registerBlockWithoutItem(
      "sorrowsquash_stem_plant",
      () -> new VineStemBodyBlock(Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.NETHER_WOOD))
   );
   public static final Supplier<Block> CEREBRAGE_SKULL = RegistryHelper.registerBlockWithoutItem(
      "cerebrage_skull", () -> new CerebrageSkullBlock(Properties.ofLegacyCopy(Blocks.SKELETON_SKULL).randomTicks())
   );
   public static final Supplier<Block> SHROOMNIGHT = RegistryHelper.registerBlock(
      "shroomnight", () -> new Block(Properties.ofLegacyCopy(Blocks.SHROOMLIGHT).lightLevel(state -> 8))
   );
   public static final Supplier<Block> NETHER_WART_BEARD = RegistryHelper.registerBlock(
      "nether_wart_beard", () -> new BeardBlock(Properties.of().mapColor(MapColor.COLOR_RED).instabreak().noCollission().sound(SoundType.WART_BLOCK))
   );
   public static final Supplier<Block> WARPED_WART_BEARD = RegistryHelper.registerBlock(
      "warped_wart_beard", () -> new BeardBlock(Properties.ofLegacyCopy((BlockBehaviour)NETHER_WART_BEARD.get()).mapColor(MapColor.WARPED_WART_BLOCK))
   );
   public static final Supplier<Block> WEEPING_IVY = RegistryHelper.registerBlock(
      "weeping_ivy",
      () -> new IvyBlock(JNEItems.WEEPING_HELIX, Properties.of().mapColor(MapColor.COLOR_RED).instabreak().noCollission().sound(SoundType.WEEPING_VINES))
   );
   public static final Supplier<Block> TWISTING_IVY = RegistryHelper.registerBlock(
      "twisting_ivy",
      () -> new IvyBlock(
         JNEItems.TWISTING_HELIX, Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).instabreak().noCollission().sound(SoundType.WEEPING_VINES)
      )
   );
   public static final Supplier<Block> CRIMSON_SPROUTS = RegistryHelper.registerBlock(
      "crimson_sprouts", () -> new NetherSproutsBlock(Properties.ofLegacyCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_RED))
   );
   public static final Supplier<Block> CRIMSON_SPORESHROOM = RegistryHelper.registerBlock(
      "crimson_sporeshroom",
      () -> new SporeshroomBlock(
         JNETags.Biomes.HAS_CRIMSON_SPORES,
         () -> ParticleTypes.CRIMSON_SPORE,
         JNEParticleTypes.CRIMSON_SMOG,
         Properties.of().strength(0.5F).sound(SoundType.FUNGUS)
      )
   );
   public static final Supplier<Block> WARPED_SPORESHROOM = RegistryHelper.registerBlock(
      "warped_sporeshroom",
      () -> new SporeshroomBlock(
         JNETags.Biomes.HAS_WARPED_SPORES,
         () -> ParticleTypes.WARPED_SPORE,
         JNEParticleTypes.WARPED_SMOG,
         Properties.of().strength(0.5F).sound(SoundType.FUNGUS)
      )
   );
   public static final Supplier<Block> SOULED_GEYSER = RegistryHelper.registerBlock(
      "souled_geyser",
      () -> new GeyserBlock(
         JNETags.Biomes.HAS_ASH,
         JNEParticleTypes.WINDY_ASH,
         JNEParticleTypes.BLACK_SMOKE,
         Properties.ofLegacyCopy((BlockBehaviour)SOUL_SLATE.get()).sound(JNESoundType.SOUL_SLATE)
      )
   );
   public static final Supplier<Block> BASALTIC_GEYSER = RegistryHelper.registerBlock(
      "basaltic_geyser",
      () -> new GeyserBlock(
         JNETags.Biomes.HAS_WHITE_ASH,
         () -> ParticleTypes.WHITE_ASH,
         JNEParticleTypes.WHITE_SMOKE,
         Properties.ofLegacyCopy(Blocks.BASALT).sound(SoundType.BASALT)
      )
   );
   public static final Supplier<Block> POTTED_SOUL_SWIRLS = RegistryHelper.registerBlockWithoutItem(
      "potted_soul_swirls", () -> new FlowerPotBlock(SOUL_SWIRLS.get(), Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY))
   );
   public static final Supplier<Block> POTTED_CRIMSON_SPORESHROOM = RegistryHelper.registerBlockWithoutItem(
      "potted_crimson_sporeshroom",
      () -> new FlowerPotBlock(CRIMSON_SPORESHROOM.get(), Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY))
   );
   public static final Supplier<Block> POTTED_WARPED_SPORESHROOM = RegistryHelper.registerBlockWithoutItem(
      "potted_warped_sporeshroom",
      () -> new FlowerPotBlock(WARPED_SPORESHROOM.get(), Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY))
   );
   public static final Supplier<Block> POTTED_SOUL_TORCHFLOWER = RegistryHelper.registerBlockWithoutItem(
      "potted_soul_torchflower",
      () -> new FlowerPotBlock(SOUL_TORCHFLOWER.get(), Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY))
   );
   public static final Supplier<Block> POLISHED_BLACKSTONE_PILLAR = RegistryHelper.registerBlock(
      "polished_blackstone_pillar", () -> new RotatedPillarBlock(Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE))
   );
   public static final Supplier<Block> POLISHED_BLACKSTONE_FENCE = RegistryHelper.registerBlock(
      "polished_blackstone_fence", () -> new FenceBlock(Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE))
   );
   public static final Supplier<Block> WEEPING_POLISHED_BLACKSTONE_BRICKS = RegistryHelper.registerBlock(
      "weeping_polished_blackstone_bricks", () -> new Block(Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS))
   );
   public static final Supplier<Block> WEEPING_POLISHED_BLACKSTONE_BRICK_SLAB = RegistryHelper.registerBlock(
      "weeping_polished_blackstone_brick_slab", () -> new SlabBlock(Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS))
   );
   public static final Supplier<Block> WEEPING_POLISHED_BLACKSTONE_BRICK_STAIRS = RegistryHelper.registerBlock(
      "weeping_polished_blackstone_brick_stairs",
      () -> new StairBlock(WEEPING_POLISHED_BLACKSTONE_BRICKS.get().defaultBlockState(), Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS))
   );
   public static final Supplier<Block> WEEPING_POLISHED_BLACKSTONE_BRICK_WALL = RegistryHelper.registerBlock(
      "weeping_polished_blackstone_brick_wall", () -> new WallBlock(Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS))
   );
   public static final Supplier<Block> TWISTING_POLISHED_BLACKSTONE_BRICKS = RegistryHelper.registerBlock(
      "twisting_polished_blackstone_bricks", () -> new Block(Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS))
   );
   public static final Supplier<Block> TWISTING_POLISHED_BLACKSTONE_BRICK_SLAB = RegistryHelper.registerBlock(
      "twisting_polished_blackstone_brick_slab", () -> new SlabBlock(Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS))
   );
   public static final Supplier<Block> TWISTING_POLISHED_BLACKSTONE_BRICK_STAIRS = RegistryHelper.registerBlock(
      "twisting_polished_blackstone_brick_stairs",
      () -> new StairBlock(TWISTING_POLISHED_BLACKSTONE_BRICKS.get().defaultBlockState(), Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS))
   );
   public static final Supplier<Block> TWISTING_POLISHED_BLACKSTONE_BRICK_WALL = RegistryHelper.registerBlock(
      "twisting_polished_blackstone_brick_wall", () -> new WallBlock(Properties.ofLegacyCopy(Blocks.POLISHED_BLACKSTONE_BRICKS))
   );
   public static final Supplier<Block> SKELETON_SKULL_CANDLE = RegistryHelper.registerBlock(
      "skeleton_skull_candle",
      () -> new SkullCandleBlock(() -> ParticleTypes.SMALL_FLAME, Properties.ofLegacyCopy(Blocks.SKELETON_SKULL).lightLevel(state -> 14))
   );
   public static final Supplier<Block> SOUL_SKELETON_SKULL_CANDLE = RegistryHelper.registerBlock(
      "soul_skeleton_skull_candle",
      () -> new SkullCandleBlock(JNEParticleTypes.SMALL_SOUL_FIRE_FLAME, Properties.ofLegacyCopy(Blocks.SKELETON_SKULL).lightLevel(state -> 10))
   );
   public static final Supplier<Block> TREACHEROUS_SKELETON_SKULL_CANDLE = RegistryHelper.registerBlock(
      "treacherous_skeleton_skull_candle",
      () -> new SkullCandleBlock(JNEParticleTypes.TREACHEROUS_FLAME, Properties.ofLegacyCopy(Blocks.SKELETON_SKULL).lightLevel(state -> 13))
   );
   public static final Supplier<Block> BONE_PIKE = RegistryHelper.registerBlock(
      "bone_pike", () -> new BonePikeBlock(Properties.of().noCollission().strength(0.5F).sound(JNESoundType.BONE_PIKE))
   );
   public static final Supplier<Block> BONE_FENCE = RegistryHelper.registerBlock(
      "bone_fence", () -> new BoneFenceBlock(Properties.of().strength(0.5F).sound(JNESoundType.BONE_PIKE))
   );
   public static final Supplier<Block> TETHERED_BONE_BLOCK = RegistryHelper.registerBlockWithoutItem(
      "tethered_bone_block", () -> new RotatedPillarBlock(Properties.ofFullCopy(Blocks.BONE_BLOCK))
   );
   public static final Supplier<Block> SKULL_BLOCK = RegistryHelper.registerBlock(
      "skull_block", () -> new JNEHorizontalDirectionalBlock(Properties.ofLegacyCopy(Blocks.BONE_BLOCK))
   );
   public static final Supplier<Block> BURNING_SKULL_BLOCK = RegistryHelper.registerBlock(
      "burning_skull_block", () -> new BurningSkullBlock(() -> ParticleTypes.FLAME, Properties.ofLegacyCopy(Blocks.BONE_BLOCK).lightLevel(state -> 15))
   );
   public static final Supplier<Block> SOUL_BURNING_SKULL_BLOCK = RegistryHelper.registerBlock(
      "soul_burning_skull_block",
      () -> new BurningSkullBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, Properties.ofLegacyCopy(Blocks.BONE_BLOCK).lightLevel(state -> 10))
   );
   public static final Supplier<Block> TREACHEROUS_BURNING_SKULL_BLOCK = RegistryHelper.registerBlock(
      "treacherous_burning_skull_block",
      () -> new BurningSkullBlock(JNEParticleTypes.TREACHEROUS_FLAME, Properties.ofLegacyCopy(Blocks.BONE_BLOCK).lightLevel(state -> 9))
   );
   public static final Supplier<Block> STACKED_BONES = RegistryHelper.registerBlock(
      "stacked_bones", () -> new Block(Properties.ofLegacyCopy(Blocks.BONE_BLOCK))
   );
   public static final Supplier<Block> STACKED_BONE_SLAB = RegistryHelper.registerBlock(
      "stacked_bone_slab", () -> new SlabBlock(Properties.ofLegacyCopy(Blocks.BONE_BLOCK))
   );
   public static final Supplier<Block> STACKED_BONE_STAIRS = RegistryHelper.registerBlock(
      "stacked_bone_stairs", () -> new StairBlock(STACKED_BONES.get().defaultBlockState(), Properties.ofLegacyCopy(Blocks.BONE_BLOCK))
   );
   public static final Supplier<Block> WITHER_BONE_BLOCK = RegistryHelper.registerBlock(
      "wither_bone_block", () -> new RotatedPillarBlock(Properties.ofLegacyCopy(Blocks.BONE_BLOCK).strength(4.5F, 9.0F).sound(JNESoundType.WITHER_BONE_BLOCK))
   );
   public static final Supplier<Block> WITHER_SKULL_BLOCK = RegistryHelper.registerBlock(
      "wither_skull_block", () -> new JNEHorizontalDirectionalBlock(Properties.ofLegacyCopy((BlockBehaviour)WITHER_BONE_BLOCK.get()))
   );
   public static final Supplier<Block> BURNING_WITHER_SKULL_BLOCK = RegistryHelper.registerBlock(
      "burning_wither_skull_block",
      () -> new BurningSkullBlock(() -> ParticleTypes.FLAME, Properties.ofLegacyCopy((BlockBehaviour)WITHER_BONE_BLOCK.get()).lightLevel(state -> 15))
   );
   public static final Supplier<Block> SOUL_BURNING_WITHER_SKULL_BLOCK = RegistryHelper.registerBlock(
      "soul_burning_wither_skull_block",
      () -> new BurningSkullBlock(() -> ParticleTypes.SOUL_FIRE_FLAME, Properties.ofLegacyCopy((BlockBehaviour)WITHER_BONE_BLOCK.get()).lightLevel(state -> 10))
   );
   public static final Supplier<Block> TREACHEROUS_BURNING_WITHER_SKULL_BLOCK = RegistryHelper.registerBlock(
      "treacherous_burning_wither_skull_block",
      () -> new BurningSkullBlock(JNEParticleTypes.TREACHEROUS_FLAME, Properties.ofLegacyCopy((BlockBehaviour)WITHER_BONE_BLOCK.get()).lightLevel(state -> 9))
   );
   public static final Supplier<Block> STACKED_WITHER_BONES = RegistryHelper.registerBlock(
      "stacked_wither_bones", () -> new Block(Properties.ofLegacyCopy((BlockBehaviour)WITHER_BONE_BLOCK.get()))
   );
   public static final Supplier<Block> STACKED_WITHER_BONE_SLAB = RegistryHelper.registerBlock(
      "stacked_wither_bone_slab", () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)WITHER_BONE_BLOCK.get()))
   );
   public static final Supplier<Block> STACKED_WITHER_BONE_STAIRS = RegistryHelper.registerBlock(
      "stacked_wither_bone_stairs",
      () -> new StairBlock(STACKED_WITHER_BONES.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)WITHER_BONE_BLOCK.get()))
   );
   public static final Supplier<Block> OSSIFIED_GARGOYLE_STATUE = RegistryHelper.registerGargoyleStatue(
      "ossified_gargoyle_statue",
      () -> new GargoyleStatueBlock(Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE)),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.COMMON)
   );
   public static final Supplier<Block> TRAMPLE_GARGOYLE_STATUE = RegistryHelper.registerGargoyleStatue(
      "trample_gargoyle_statue",
      () -> new GargoyleStatueBlock(Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE)),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.COMMON)
   );
   public static final Supplier<Block> PHASE_GARGOYLE_STATUE = RegistryHelper.registerGargoyleStatue(
      "phase_gargoyle_statue",
      () -> new GargoyleStatueBlock(Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE)),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.COMMON)
   );
   public static final Supplier<Block> GHOUL_GARGOYLE_STATUE = RegistryHelper.registerGargoyleStatue(
      "ghoul_gargoyle_statue",
      () -> new GargoyleStatueBlock(Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE)),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.COMMON)
   );
   public static final Supplier<Block> WRETCHED_GARGOYLE_STATUE = RegistryHelper.registerGargoyleStatue(
      "wretched_gargoyle_statue",
      () -> new GargoyleStatueBlock(Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE)),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.UNCOMMON)
   );
   public static final Supplier<Block> TREACHEROUS_GARGOYLE_STATUE = RegistryHelper.registerGargoyleStatue(
      "treacherous_gargoyle_statue",
      () -> new GargoyleStatueBlock(Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE)),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.UNCOMMON)
   );
   public static final Supplier<Block> CIRRIPEDIA_GARGOYLE_STATUE = RegistryHelper.registerGargoyleStatue(
      "cirripedia_gargoyle_statue",
      () -> new GargoyleStatueBlock(Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE)),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.RARE)
   );
   public static final Supplier<Block> OCCULT_GARGOYLE_STATUE = RegistryHelper.registerGargoyleStatue(
      "occult_gargoyle_statue",
      () -> new GargoyleStatueBlock(Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE)),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.COMMON)
   );
   public static final Supplier<Block> SEALED_GARGOYLE_STATUE = RegistryHelper.registerGargoyleStatue(
      "sealed_gargoyle_statue",
      () -> new GargoyleStatueBlock(Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE)),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.UNCOMMON)
   );
   public static final Supplier<Block> OBFUSCATED_GARGOYLE_STATUE = RegistryHelper.registerGargoyleStatue(
      "obfuscated_gargoyle_statue",
      () -> new GargoyleStatueBlock(Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE)),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.EPIC)
   );
   public static final Supplier<Block> INSCRIBED_PANEL = RegistryHelper.registerBlock(
      "inscribed_panel",
      () -> new InscribedPanelBlock(
         Properties.of().noOcclusion().strength(1.0F, 5.0F).sound(JNESoundType.SOUL_SLATE).lightLevel(InscribedPanelBlock.STATE_TO_LUMINANCE)
      )
   );
   public static final Supplier<Block> SHOTGUN_BARREL = RegistryHelper.registerBlock(
      "shotgun_barrel", () -> new ShotgunBarrelBlock(Properties.of().strength(3.5F).requiresCorrectToolForDrops().sound(JNESoundType.SHOTGUN_BARREL))
   );
   public static final Supplier<Block> TREACHEROUS_WAX_BLOCK = RegistryHelper.registerBlock(
      "treacherous_wax_block", () -> new Block(Properties.ofLegacyCopy(Blocks.HONEYCOMB_BLOCK).sound(JNESoundType.TREACHEROUS_WAX))
   );
   public static final Supplier<Block> TREACHEROUS_FIRE = RegistryHelper.registerBlockWithoutItem(
      "treacherous_fire", () -> new TreacherousFireBlock(Properties.ofLegacyCopy(Blocks.SOUL_FIRE).mapColor(MapColor.COLOR_RED).lightLevel(state -> 9))
   );
   public static final Supplier<Block> TREACHEROUS_TORCH = RegistryHelper.registerBlockWithoutItem(
      "treacherous_torch",
      () -> new JNETorchBlock.Standing(
         JNEParticleTypes.TREACHEROUS_FLAME, Properties.ofLegacyCopy(Blocks.TORCH).mapColor(MapColor.COLOR_RED).lightLevel(state -> 9)
      )
   );
   public static final Supplier<Block> TREACHEROUS_WALL_TORCH = RegistryHelper.registerBlockWithoutItem(
      "treacherous_wall_torch",
      () -> new JNETorchBlock.Wall(
         JNEParticleTypes.TREACHEROUS_FLAME,
         Properties.ofLegacyCopy(Blocks.WALL_TORCH).mapColor(MapColor.COLOR_RED).dropsLike(TREACHEROUS_TORCH.get()).lightLevel(state -> 9)
      )
   );
   public static final Supplier<Block> TREACHEROUS_LANTERN = RegistryHelper.registerBlock(
      "treacherous_lantern", () -> new LanternBlock(Properties.ofLegacyCopy(Blocks.SOUL_LANTERN).mapColor(MapColor.COLOR_RED).lightLevel(state -> 9))
   );
   public static final Supplier<Block> TREACHEROUS_CAMPFIRE = RegistryHelper.registerBlock(
      "treacherous_campfire",
      () -> new JNECampfireBlock(
         false, 0, Properties.ofLegacyCopy(Blocks.SOUL_CAMPFIRE).mapColor(MapColor.COLOR_RED).lightLevel(state -> state.getValue(JNECampfireBlock.LIT) ? 9 : 0)
      )
   );
   public static final Supplier<Block> TREACHEROUS_CANDLE = RegistryHelper.registerBlockWithoutItem(
      "treacherous_candle",
      () -> new TreacherousCandleBlock(
         Properties.ofLegacyCopy((BlockBehaviour)SOUL_CANDLE.get())
            .mapColor(MapColor.COLOR_RED)
            .lightLevel(state -> state.getValue(TreacherousCandleBlock.LIT) ? 4 : 0)
            .sound(JNESoundType.TREACHEROUS_CANDLE)
      )
   );
   public static final Supplier<Block> OCHRE_FROGMIST = RegistryHelper.registerBlockWithoutItem(
      "ochre_frogmist",
      () -> new FrogmistBlock(
         Properties.of()
            .noCollission()
            .pushReaction(PushReaction.DESTROY)
            .instabreak()
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .sound(JNESoundType.FROGMIST)
      )
   );
   public static final Supplier<Block> PEARLESCENT_FROGMIST = RegistryHelper.registerBlockWithoutItem(
      "pearlescent_frogmist", () -> new FrogmistBlock(Properties.ofLegacyCopy((BlockBehaviour)OCHRE_FROGMIST.get()))
   );
   public static final Supplier<Block> VERDANT_FROGMIST = RegistryHelper.registerBlockWithoutItem(
      "verdant_frogmist", () -> new FrogmistBlock(Properties.ofLegacyCopy((BlockBehaviour)OCHRE_FROGMIST.get()))
   );
   public static final Supplier<Block> PYROCLAST_CRUSTS = RegistryHelper.registerBlockWithoutItem(
      "pyroclast_crusts",
      () -> new PyroclastCrustsBlock(Properties.of().strength(0.5F, 1.5F).noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.BASALT))
   );
   public static final Supplier<Block> PYROCLAST = RegistryHelper.registerBlock(
      "pyroclast", () -> new Block(Properties.of().strength(0.8F, 1.5F).noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.BASALT))
   );
   public static final Supplier<Block> PYROCLAST_SLAB = RegistryHelper.registerBlock(
      "pyroclast_slab", () -> new SlabBlock(Properties.ofLegacyCopy((BlockBehaviour)PYROCLAST.get()))
   );
   public static final Supplier<Block> PYROCLAST_STAIRS = RegistryHelper.registerBlock(
      "pyroclast_stairs", () -> new StairBlock(PYROCLAST.get().defaultBlockState(), Properties.ofLegacyCopy((BlockBehaviour)PYROCLAST.get()))
   );
   public static final Supplier<Block> PYROCLAST_WALL = RegistryHelper.registerBlock(
      "pyroclast_wall", () -> new WallBlock(Properties.ofLegacyCopy((BlockBehaviour)PYROCLAST.get()))
   );
   public static final Supplier<Block> SILT = RegistryHelper.registerBlock(
      "silt", () -> new ColoredFallingBlock(new ColorRGBA(10581094), Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(0.4F).sound(SoundType.GRAVEL))
   );
   public static final Supplier<Block> SILT_FLINT_ORE = RegistryHelper.registerBlock(
      "silt_flint_ore",
      () -> new OreFallingBlock(
         UniformInt.of(0, 2), new ColorRGBA(10581094), Properties.of().mapColor(MapColor.TERRACOTTA_PINK).strength(0.6F).sound(SoundType.GRAVEL)
      )
   );
   public static final Supplier<Block> DAMP_SILTMARRAM = RegistryHelper.registerBlock(
      "damp_siltmarram",
      () -> new SiltMarramBlock(
         JNETags.Biomes.SPAWNS_DAMP_VARIANT_STRIDERS_AND_SILTMARRAM,
         LodestoneBlockProperties.ofLegacyCopy(Blocks.NETHER_SPROUTS).randomTicks().mapColor(MapColor.TERRACOTTA_PINK)
      )
   );
   public static final Supplier<Block> MOIST_SILTMARRAM = RegistryHelper.registerBlock(
      "moist_siltmarram",
      () -> new SiltMarramBlock(
         JNETags.Biomes.SPAWNS_MOIST_VARIANT_STRIDERS_AND_SILTMARRAM,
         LodestoneBlockProperties.ofLegacyCopy(Blocks.NETHER_SPROUTS).randomTicks().mapColor(MapColor.TERRACOTTA_PINK)
      )
   );
   public static final Supplier<Block> DRY_SILTMARRAM = RegistryHelper.registerBlock(
      "dry_siltmarram",
      () -> new SiltMarramBlock(
         JNETags.Biomes.SPAWNS_DRY_VARIANT_STRIDERS_AND_SILTMARRAM,
         LodestoneBlockProperties.ofLegacyCopy(Blocks.NETHER_SPROUTS).randomTicks().mapColor(MapColor.TERRACOTTA_PINK)
      )
   );
   public static final Supplier<Block> WHITE_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "white_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of().mapColor(MapColor.SNOW).strength(0.3F).sound(SoundType.GLASS).lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> ORANGE_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "orange_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_ORANGE)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> MAGENTA_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "magenta_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_MAGENTA)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> LIGHT_BLUE_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "light_blue_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> YELLOW_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "yellow_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_YELLOW)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> LIME_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "lime_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_LIGHT_GREEN)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> PINK_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "pink_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_PINK)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> GRAY_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "gray_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_GRAY)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> LIGHT_GRAY_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "light_gray_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> CYAN_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "cyan_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_CYAN)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> PURPLE_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "purple_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_PURPLE)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> BLUE_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "blue_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_BLUE)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> BROWN_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "brown_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_BROWN)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> GREEN_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "green_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_GREEN)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> RED_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "red_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_RED)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );
   public static final Supplier<Block> BLACK_STAINED_REDSTONE_LAMP = RegistryHelper.registerBlock(
      "black_stained_redstone_lamp",
      () -> new RedstoneLampBlock(
         Properties.of()
            .mapColor(MapColor.TERRACOTTA_BLACK)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0)
      )
   );

   public static void init(IEventBus eventBus) {
      registerAliases();
      if (CompatUtil.OREGANIZED) {
         OreganizedCompat.Blocks.init();
      }

      if (CompatUtil.RUBINATED_NETHER) {
         RubinatedNetherCompat.Blocks.init();
      }

      if (CompatUtil.GARDENS_OF_THE_DEAD) {
         GardensOfTheDeadCompat.Blocks.init();
      }

      if (CompatUtil.CAVERNS_AND_CHASMS) {
         CavernsAndChasmsCompat.Blocks.init();
      }

      BLOCKS.register(eventBus);
   }

   private static void registerAliases() {
      BLOCKS.addAlias(NetherExp.netherexpPath("soul_jack_o_lantern"), NetherExp.minecraftPath("jack_o_lantern"));
      BLOCKS.addAlias(NetherExp.netherexpPath("soul_ghoul_o_lantern"), NetherExp.minecraftPath("ghoul_o_lantern"));
      BLOCKS.addAlias(NetherExp.netherexpPath("bone_cortical"), NetherExp.minecraftPath("bone_block"));
      BLOCKS.addAlias(NetherExp.netherexpPath("bone_rod"), NetherExp.netherexpPath("bone_pike"));
      BLOCKS.addAlias(NetherExp.netherexpPath("ancient_wax_block"), NetherExp.netherexpPath("treacherous_wax_block"));
      BLOCKS.addAlias(NetherExp.netherexpPath("ancient_fire"), NetherExp.netherexpPath("treacherous_fire"));
      BLOCKS.addAlias(NetherExp.netherexpPath("ancient_torch"), NetherExp.netherexpPath("treacherous_torch"));
      BLOCKS.addAlias(NetherExp.netherexpPath("ancient_wall_torch"), NetherExp.netherexpPath("treacherous_wall_torch"));
      BLOCKS.addAlias(NetherExp.netherexpPath("ancient_lantern"), NetherExp.netherexpPath("treacherous_lantern"));
      BLOCKS.addAlias(NetherExp.netherexpPath("ancient_campfire"), NetherExp.netherexpPath("treacherous_campfire"));
      BLOCKS.addAlias(NetherExp.netherexpPath("ancient_candle"), NetherExp.netherexpPath("treacherous_candle"));
      BLOCKS.addAlias(NetherExp.netherexpPath("ancient_skeleton_skull_candle"), NetherExp.netherexpPath("treacherous_skeleton_skull_candle"));
      BLOCKS.addAlias(NetherExp.netherexpPath("ancient_burning_skull_block"), NetherExp.netherexpPath("treacherous_burning_skull_block"));
      BLOCKS.addAlias(NetherExp.netherexpPath("ancient_burning_wither_skull_block"), NetherExp.netherexpPath("treacherous_burning_wither_skull_block"));
   }
}
