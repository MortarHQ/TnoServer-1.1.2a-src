package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.block.BrittleSandstoneBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.FrostLilyBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.IceSheetBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.IcicleBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyPeachCactusBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyVinesBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyVinesPlantBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.RareIceBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.block.SuspiciousAncientSandBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

@AutoRegister("yungscavebiomes")
public class BlockModule {
   @AutoRegister("icicle")
   public static final AutoRegisterBlock ICICLE = AutoRegisterBlock.of(
         () -> new IcicleBlock(
            Properties.of()
               .mapColor(MapColor.ICE)
               .noOcclusion()
               .randomTicks()
               .strength(0.5F)
               .forceSolidOn()
               .dynamicShape()
               .pushReaction(PushReaction.DESTROY)
               .offsetType(OffsetType.XZ)
               .instrument(NoteBlockInstrument.CHIME)
               .isRedstoneConductor((blockState, blockGetter, blockPos) -> false)
               .sound(SoundType.GLASS)
         )
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("frost_lily")
   public static final AutoRegisterBlock FROST_LILY = AutoRegisterBlock.of(
         () -> new FrostLilyBlock(
            Properties.of()
               .mapColor(MapColor.ICE)
               .noOcclusion()
               .instabreak()
               .dynamicShape()
               .pushReaction(PushReaction.DESTROY)
               .lightLevel(blockState -> 10)
               .offsetType(OffsetType.XZ)
               .sound(SoundType.GLASS)
         )
      )
      .withItem(() -> new net.minecraft.world.item.Item.Properties().stacksTo(16));
   @AutoRegister("rare_ice")
   public static final AutoRegisterBlock RARE_ICE = AutoRegisterBlock.of(
         () -> new RareIceBlock(
            Properties.of()
               .mapColor(MapColor.ICE)
               .friction(0.98F)
               .noOcclusion()
               .lightLevel(blockState -> 11)
               .requiresCorrectToolForDrops()
               .strength(3.0F)
               .instrument(NoteBlockInstrument.CHIME)
               .isRedstoneConductor((blockState, blockGetter, blockPos) -> false)
               .sound(SoundType.GLASS)
         )
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("ice_sheet")
   public static final AutoRegisterBlock ICE_SHEET = AutoRegisterBlock.of(
         () -> new IceSheetBlock(
            Properties.of()
               .mapColor(MapColor.ICE)
               .friction(0.98F)
               .noOcclusion()
               .noCollission()
               .strength(0.3F)
               .lightLevel(blockState -> blockState.getValue(BlockStateProperties.LIT) ? 4 : 0)
               .randomTicks()
               .isValidSpawn((state, world, pos, entityType) -> true)
               .isRedstoneConductor((blockState, blockGetter, blockPos) -> false)
               .pushReaction(PushReaction.DESTROY)
               .sound(SoundType.GLASS)
         )
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("ancient_sand")
   public static final AutoRegisterBlock ANCIENT_SAND = AutoRegisterBlock.of(
         () -> new ColoredFallingBlock(
            new ColorRGBA(13743234), Properties.of().mapColor(MapColor.SAND).strength(0.5F).instrument(NoteBlockInstrument.SNARE).sound(SoundType.SAND)
         )
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("suspicious_ancient_sand")
   public static final AutoRegisterBlock SUSPICIOUS_ANCIENT_SAND = AutoRegisterBlock.of(
         () -> new SuspiciousAncientSandBlock(
            Properties.of()
               .mapColor(MapColor.SAND)
               .instrument(NoteBlockInstrument.SNARE)
               .strength(0.25F)
               .sound(SoundType.SUSPICIOUS_SAND)
               .pushReaction(PushReaction.DESTROY),
            SoundEvents.BRUSH_SAND,
            SoundEvents.BRUSH_SAND_COMPLETED
         )
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("ancient_sandstone")
   public static final AutoRegisterBlock ANCIENT_SANDSTONE = AutoRegisterBlock.of(
         () -> new Block(Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(0.8F))
      )
      .withStairs()
      .withSlab()
      .withWall()
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("brittle_ancient_sandstone")
   public static final AutoRegisterBlock BRITTLE_ANCIENT_SANDSTONE = AutoRegisterBlock.of(
         () -> new BrittleSandstoneBlock(
            13743234, Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F)
         )
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("brittle_sandstone")
   public static final AutoRegisterBlock BRITTLE_SANDSTONE = AutoRegisterBlock.of(
         () -> new BrittleSandstoneBlock(
            14406560, Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F)
         )
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("brittle_red_sandstone")
   public static final AutoRegisterBlock BRITTLE_RED_SANDSTONE = AutoRegisterBlock.of(
         () -> new BrittleSandstoneBlock(
            11098145, Properties.of().mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F)
         )
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("cut_ancient_sandstone")
   public static final AutoRegisterBlock CUT_ANCIENT_SANDSTONE = AutoRegisterBlock.of(
         () -> new Block(Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(0.8F))
      )
      .withSlab()
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("chiseled_ancient_sandstone")
   public static final AutoRegisterBlock CHISELED_ANCIENT_SANDSTONE = AutoRegisterBlock.of(
         () -> new Block(Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(0.8F))
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("smooth_ancient_sandstone")
   public static final AutoRegisterBlock SMOOTH_ANCIENT_SANDSTONE = AutoRegisterBlock.of(
         () -> new Block(Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(0.8F))
      )
      .withStairs()
      .withSlab()
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("layered_ancient_sandstone")
   public static final AutoRegisterBlock LAYERED_ANCIENT_SANDSTONE = AutoRegisterBlock.of(
         () -> new Block(Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(0.8F))
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("layered_sandstone")
   public static final AutoRegisterBlock LAYERED_SANDSTONE = AutoRegisterBlock.of(
         () -> new Block(Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(0.8F))
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("layered_red_sandstone")
   public static final AutoRegisterBlock LAYERED_RED_SANDSTONE = AutoRegisterBlock.of(
         () -> new Block(Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.8F))
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("prickly_peach_cactus")
   public static final AutoRegisterBlock PRICKLY_PEACH_CACTUS = AutoRegisterBlock.of(
         () -> new PricklyPeachCactusBlock(
            Properties.of().mapColor(MapColor.PLANT).randomTicks().sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY).strength(0.4F)
         )
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("potted_prickly_peach_cactus")
   public static final AutoRegisterBlock POTTED_PRICKLY_PEACH_CACTUS = AutoRegisterBlock.of(() -> Services.PLATFORM.getPottedPricklyPeachCactusBlock());
   @AutoRegister("prickly_vines")
   public static final AutoRegisterBlock PRICKLY_VINES = AutoRegisterBlock.of(
         () -> new PricklyVinesBlock(
            Properties.of()
               .mapColor(MapColor.PLANT)
               .randomTicks()
               .noCollission()
               .instabreak()
               .pushReaction(PushReaction.DESTROY)
               .sound(SoundType.WEEPING_VINES)
         )
      )
      .withItem(net.minecraft.world.item.Item.Properties::new);
   @AutoRegister("prickly_vines_plant")
   public static final AutoRegisterBlock PRICKLY_VINES_PLANT = AutoRegisterBlock.of(
      () -> new PricklyVinesPlantBlock(
         Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.WEEPING_VINES)
      )
   );
   public static final TagKey<Block> SAND_SNAPPER_BLOCKS = TagKey.create(Registries.BLOCK, YungsCaveBiomesCommon.id("sand_snapper_blocks"));
   public static final TagKey<Block> ICE_SHEET_FEATURE_AVOID = TagKey.create(Registries.BLOCK, YungsCaveBiomesCommon.id("ice_sheet_feature_avoid"));
   public static final TagKey<Block> CREEPING_ICE_GLOWS_ON = TagKey.create(Registries.BLOCK, YungsCaveBiomesCommon.id("creeping_ice_glows_on"));
}
