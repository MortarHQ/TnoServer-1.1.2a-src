package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.registry.ModFeatures;
import artifacts.world.CampsiteFeatureConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class ConfiguredFeatures {
   public static final ResourceKey<ConfiguredFeature<?, ?>> CAMPSITE = Artifacts.key(Registries.CONFIGURED_FEATURE, "campsite");

   public static void create(BootstrapContext<ConfiguredFeature<?, ?>> context) {
      ConfiguredFeature<?, ?> campsite = new ConfiguredFeature(
         ModFeatures.CAMPSITE.get(),
         new CampsiteFeatureConfiguration(
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add((BlockState)Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, true), 9)
                  .add((BlockState)Blocks.SOUL_CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, true), 1)
            ),
            SimpleStateProvider.simple((BlockState)Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, false)),
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(Blocks.POTTED_DEAD_BUSH.defaultBlockState(), 2)
                  .add(Blocks.POTTED_BAMBOO.defaultBlockState(), 2)
                  .add(Blocks.POTTED_RED_TULIP.defaultBlockState(), 2)
                  .add(Blocks.BREWING_STAND.defaultBlockState(), 1)
                  .add((BlockState)Blocks.CANDLE_CAKE.defaultBlockState().setValue(CandleCakeBlock.LIT, true), 1)
            ),
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(Blocks.CRAFTING_TABLE.defaultBlockState(), 5)
                  .add(Blocks.SMITHING_TABLE.defaultBlockState(), 5)
                  .add(Blocks.FLETCHING_TABLE.defaultBlockState(), 5)
                  .add(Blocks.CARTOGRAPHY_TABLE.defaultBlockState(), 5)
                  .add(Blocks.ANVIL.defaultBlockState(), 2)
                  .add(Blocks.CHIPPED_ANVIL.defaultBlockState(), 2)
                  .add(Blocks.DAMAGED_ANVIL.defaultBlockState(), 1)
            ),
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add((BlockState)Blocks.FURNACE.defaultBlockState().setValue(FurnaceBlock.LIT, false), 2)
                  .add((BlockState)Blocks.BLAST_FURNACE.defaultBlockState().setValue(BlastFurnaceBlock.LIT, false), 1)
                  .add((BlockState)Blocks.SMOKER.defaultBlockState().setValue(SmokerBlock.LIT, false), 1)
            ),
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(Blocks.COBBLESTONE_WALL.defaultBlockState(), 2)
                  .add(Blocks.COBBLED_DEEPSLATE_WALL.defaultBlockState(), 2)
                  .add(Blocks.STONE_BRICK_WALL.defaultBlockState(), 1)
                  .add(Blocks.DEEPSLATE_BRICK_WALL.defaultBlockState(), 1)
            ),
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(Blocks.RED_BED.defaultBlockState(), 1)
                  .add(Blocks.YELLOW_BED.defaultBlockState(), 1)
                  .add(Blocks.CYAN_BED.defaultBlockState(), 1)
                  .add(Blocks.GRAY_BED.defaultBlockState(), 1)
                  .add(Blocks.MAGENTA_BED.defaultBlockState(), 1)
                  .add(Blocks.GREEN_BED.defaultBlockState(), 1)
            ),
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add(Blocks.LANTERN.defaultBlockState(), 4)
                  .add((BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true), 1)
                  .add((BlockState)((BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true)).setValue(CandleBlock.CANDLES, 2), 1)
                  .add((BlockState)((BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true)).setValue(CandleBlock.CANDLES, 3), 1)
                  .add((BlockState)((BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true)).setValue(CandleBlock.CANDLES, 4), 1)
                  .add(Blocks.SOUL_LANTERN.defaultBlockState(), 1)
            ),
            new WeightedStateProvider(
               SimpleWeightedRandomList.builder()
                  .add((BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, false), 1)
                  .add((BlockState)((BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, false)).setValue(CandleBlock.CANDLES, 2), 1)
                  .add((BlockState)((BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, false)).setValue(CandleBlock.CANDLES, 3), 1)
                  .add((BlockState)((BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, false)).setValue(CandleBlock.CANDLES, 4), 1)
            ),
            SimpleStateProvider.simple(Blocks.OAK_PLANKS)
         )
      );
      context.register(CAMPSITE, campsite);
   }
}
