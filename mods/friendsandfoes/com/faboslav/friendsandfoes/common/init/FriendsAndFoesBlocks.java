package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.block.CopperButtonBlock;
import com.faboslav.friendsandfoes.common.block.CrabEggBlock;
import com.faboslav.friendsandfoes.common.block.OxidizableButtonBlock;
import com.faboslav.friendsandfoes.common.block.OxidizableLightningRodBlock;
import com.faboslav.friendsandfoes.common.events.client.RegisterRenderLayersEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterFlammabilityEvent;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class FriendsAndFoesBlocks {
   public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, "friendsandfoes");
   public static final RegistryEntry<Block> BUTTERCUP = registerBlock(
      "buttercup",
      properties -> new FlowerBlock(MobEffects.SATURATION, 6.0F, properties),
      () -> Properties.of()
         .mapColor(MapColor.PLANT)
         .noCollission()
         .instabreak()
         .sound(SoundType.GRASS)
         .offsetType(OffsetType.XZ)
         .pushReaction(PushReaction.DESTROY)
   );
   public static final RegistryEntry<Block> POTTED_BUTTERCUP = registerBlock(
      "potted_buttercup",
      properties -> new FlowerPotBlock((Block)BUTTERCUP.get(), properties),
      () -> Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)
   );
   public static final RegistryEntry<Block> CRAB_EGG = registerBlock(
      "crab_egg",
      CrabEggBlock::new,
      () -> Properties.of()
         .mapColor(MapColor.COLOR_ORANGE)
         .forceSolidOn()
         .strength(0.5F)
         .sound(SoundType.METAL)
         .randomTicks()
         .noOcclusion()
         .pushReaction(PushReaction.DESTROY)
   );
   public static final RegistryEntry<Block> ACACIA_BEEHIVE = registerBlock(
      "acacia_beehive",
      BeehiveBlock::new,
      () -> Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()
   );
   public static final RegistryEntry<Block> BAMBOO_BEEHIVE = registerBlock(
      "bamboo_beehive",
      BeehiveBlock::new,
      () -> Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.BAMBOO_WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()
   );
   public static final RegistryEntry<Block> BIRCH_BEEHIVE = registerBlock(
      "birch_beehive",
      BeehiveBlock::new,
      () -> Properties.of().mapColor(MapColor.SAND).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()
   );
   public static final RegistryEntry<Block> CHERRY_BEEHIVE = registerBlock(
      "cherry_beehive",
      BeehiveBlock::new,
      () -> Properties.of()
         .mapColor(MapColor.TERRACOTTA_WHITE)
         .strength(0.6F)
         .sound(SoundType.CHERRY_WOOD)
         .instrument(NoteBlockInstrument.BASS)
         .ignitedByLava()
   );
   public static final RegistryEntry<Block> CRIMSON_BEEHIVE = registerBlock(
      "crimson_beehive",
      BeehiveBlock::new,
      () -> Properties.of().mapColor(MapColor.CRIMSON_STEM).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS)
   );
   public static final RegistryEntry<Block> DARK_OAK_BEEHIVE = registerBlock(
      "dark_oak_beehive",
      BeehiveBlock::new,
      () -> Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()
   );
   public static final RegistryEntry<Block> JUNGLE_BEEHIVE = registerBlock(
      "jungle_beehive",
      BeehiveBlock::new,
      () -> Properties.of().mapColor(MapColor.DIRT).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()
   );
   public static final RegistryEntry<Block> MANGROVE_BEEHIVE = registerBlock(
      "mangrove_beehive",
      BeehiveBlock::new,
      () -> Properties.of().mapColor(MapColor.COLOR_RED).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()
   );
   public static final RegistryEntry<Block> SPRUCE_BEEHIVE = registerBlock(
      "spruce_beehive",
      BeehiveBlock::new,
      () -> Properties.of().mapColor(MapColor.PODZOL).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()
   );
   public static final RegistryEntry<Block> WARPED_BEEHIVE = registerBlock(
      "warped_beehive",
      BeehiveBlock::new,
      () -> Properties.of().mapColor(MapColor.WARPED_STEM).strength(0.6F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS)
   );
   public static final RegistryEntry<Block> COPPER_BUTTON = registerBlock(
      "copper_button",
      properties -> new OxidizableButtonBlock(WeatherState.UNAFFECTED, 10, properties),
      () -> Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY).sound(SoundType.COPPER)
   );
   public static final RegistryEntry<Block> EXPOSED_COPPER_BUTTON = registerBlock(
      "exposed_copper_button",
      properties -> new OxidizableButtonBlock(WeatherState.EXPOSED, 7, properties),
      () -> Properties.ofFullCopy((BlockBehaviour)COPPER_BUTTON.get())
   );
   public static final RegistryEntry<Block> WEATHERED_COPPER_BUTTON = registerBlock(
      "weathered_copper_button",
      properties -> new OxidizableButtonBlock(WeatherState.WEATHERED, 4, properties),
      () -> Properties.ofFullCopy((BlockBehaviour)COPPER_BUTTON.get())
   );
   public static final RegistryEntry<Block> OXIDIZED_COPPER_BUTTON = registerBlock(
      "oxidized_copper_button",
      properties -> new OxidizableButtonBlock(WeatherState.OXIDIZED, 1, properties),
      () -> Properties.ofFullCopy((BlockBehaviour)COPPER_BUTTON.get())
   );
   public static final RegistryEntry<Block> WAXED_COPPER_BUTTON = registerBlock(
      "waxed_copper_button", properties -> new CopperButtonBlock(10, properties), () -> Properties.ofFullCopy((BlockBehaviour)COPPER_BUTTON.get())
   );
   public static final RegistryEntry<Block> WAXED_EXPOSED_COPPER_BUTTON = registerBlock(
      "waxed_exposed_copper_button", properties -> new CopperButtonBlock(7, properties), () -> Properties.ofFullCopy((BlockBehaviour)COPPER_BUTTON.get())
   );
   public static final RegistryEntry<Block> WAXED_WEATHERED_COPPER_BUTTON = registerBlock(
      "waxed_weathered_copper_button", properties -> new CopperButtonBlock(4, properties), () -> Properties.ofFullCopy((BlockBehaviour)COPPER_BUTTON.get())
   );
   public static final RegistryEntry<Block> WAXED_OXIDIZED_COPPER_BUTTON = registerBlock(
      "waxed_oxidized_copper_button", properties -> new CopperButtonBlock(1, properties), () -> Properties.ofFullCopy((BlockBehaviour)COPPER_BUTTON.get())
   );
   public static final RegistryEntry<Block> EXPOSED_LIGHTNING_ROD = registerBlock(
      "exposed_lightning_rod",
      properties -> new OxidizableLightningRodBlock(WeatherState.EXPOSED, properties),
      () -> Properties.ofFullCopy(Blocks.LIGHTNING_ROD)
   );
   public static final RegistryEntry<Block> WEATHERED_LIGHTNING_ROD = registerBlock(
      "weathered_lightning_rod",
      properties -> new OxidizableLightningRodBlock(WeatherState.WEATHERED, properties),
      () -> Properties.ofFullCopy(Blocks.LIGHTNING_ROD)
   );
   public static final RegistryEntry<Block> OXIDIZED_LIGHTNING_ROD = registerBlock(
      "oxidized_lightning_rod",
      properties -> new OxidizableLightningRodBlock(WeatherState.OXIDIZED, properties),
      () -> Properties.ofFullCopy(Blocks.LIGHTNING_ROD)
   );
   public static final RegistryEntry<Block> WAXED_LIGHTNING_ROD = registerBlock(
      "waxed_lightning_rod", LightningRodBlock::new, () -> Properties.ofFullCopy(Blocks.LIGHTNING_ROD)
   );
   public static final RegistryEntry<Block> WAXED_EXPOSED_LIGHTNING_ROD = registerBlock(
      "waxed_exposed_lightning_rod", LightningRodBlock::new, () -> Properties.ofFullCopy(Blocks.LIGHTNING_ROD)
   );
   public static final RegistryEntry<Block> WAXED_WEATHERED_LIGHTNING_ROD = registerBlock(
      "waxed_weathered_lightning_rod", LightningRodBlock::new, () -> Properties.ofFullCopy(Blocks.LIGHTNING_ROD)
   );
   public static final RegistryEntry<Block> WAXED_OXIDIZED_LIGHTNING_ROD = registerBlock(
      "waxed_oxidized_lightning_rod", LightningRodBlock::new, () -> Properties.ofFullCopy(Blocks.LIGHTNING_ROD)
   );

   private static RegistryEntry<Block> registerBlock(String id, Function<Properties, Block> factory, Supplier<Properties> getter) {
      return BLOCKS.register(id, () -> factory.apply(getter.get()));
   }

   public static void registerRenderLayers(RegisterRenderLayersEvent event) {
      event.register(RenderType.cutout(), (Block)BUTTERCUP.get(), (Block)POTTED_BUTTERCUP.get());
   }

   public static void registerFlammablity(RegisterFlammabilityEvent event) {
      BLOCKS.stream()
         .<Block>map(RegistryEntry::get)
         .filter(block -> block instanceof BeehiveBlock && block != WARPED_BEEHIVE && block != CRIMSON_BEEHIVE)
         .map(block -> (BeehiveBlock)block)
         .forEach(block -> event.register(block, 20, 5));
   }

   private FriendsAndFoesBlocks() {
   }
}
