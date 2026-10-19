package twilightforest.world.components.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.BlockFamily.Variant;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.storage.loot.LootTable;
import twilightforest.data.TFBlockFamilies;
import twilightforest.loot.TFLootTables;

public record RuinedFoundationConfig(
   RuinedFoundationConfig.RuinedFoundationDimensions dimensions, RuinedFoundationConfig.RuinedFoundationBlocks blocks, ResourceKey<LootTable> lootTable
) implements FeatureConfiguration {
   public static final Codec<RuinedFoundationConfig> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
            RuinedFoundationConfig.RuinedFoundationDimensions.CODEC.forGetter(RuinedFoundationConfig::dimensions),
            RuinedFoundationConfig.RuinedFoundationBlocks.CODEC.forGetter(RuinedFoundationConfig::blocks),
            ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(RuinedFoundationConfig::lootTable)
         )
         .apply(inst, RuinedFoundationConfig::new)
   );

   public static RuinedFoundationConfig withDefaultBlocks(boolean floorWaterlogged) {
      RuinedFoundationConfig.RuinedFoundationBlocks foundationBlocks = RuinedFoundationConfig.RuinedFoundationBlocks.blocksFromFamilies(
         floorWaterlogged, BlockFamilies.OAK_PLANKS, BlockFamilies.COBBLESTONE, BlockFamilies.MOSSY_COBBLESTONE, BlockStateProvider.simple(Blocks.CHEST)
      );
      return new RuinedFoundationConfig(RuinedFoundationConfig.RuinedFoundationDimensions.makeDefault(), foundationBlocks, TFLootTables.FOUNDATION_BASEMENT);
   }

   public record RuinedFoundationBlocks(
      BlockStateProvider floor,
      BlockStateProvider basementPosts,
      BlockStateProvider lootContainer,
      BlockStateProvider wallBlock,
      BlockStateProvider wallTop,
      BlockStateProvider decayedWall,
      BlockStateProvider decayedTop
   ) {
      public static final MapCodec<RuinedFoundationConfig.RuinedFoundationBlocks> CODEC = RecordCodecBuilder.mapCodec(
         inst -> inst.group(
               BlockStateProvider.CODEC.fieldOf("floor").forGetter(RuinedFoundationConfig.RuinedFoundationBlocks::floor),
               BlockStateProvider.CODEC.fieldOf("basement_posts").forGetter(RuinedFoundationConfig.RuinedFoundationBlocks::basementPosts),
               BlockStateProvider.CODEC.fieldOf("loot_container").forGetter(RuinedFoundationConfig.RuinedFoundationBlocks::lootContainer),
               BlockStateProvider.CODEC.fieldOf("wall_block").forGetter(RuinedFoundationConfig.RuinedFoundationBlocks::wallBlock),
               BlockStateProvider.CODEC.fieldOf("wall_top_block").forGetter(RuinedFoundationConfig.RuinedFoundationBlocks::wallTop),
               BlockStateProvider.CODEC.fieldOf("decayed_wall_block").forGetter(RuinedFoundationConfig.RuinedFoundationBlocks::decayedWall),
               BlockStateProvider.CODEC.fieldOf("decayed_wall_top_block").forGetter(RuinedFoundationConfig.RuinedFoundationBlocks::decayedTop)
            )
            .apply(inst, RuinedFoundationConfig.RuinedFoundationBlocks::new)
      );

      public static RuinedFoundationConfig.RuinedFoundationBlocks blocksFromFamilies(
         boolean floorWaterlogged, BlockFamily floorMaterial, BlockFamily wallMaterial, BlockFamily decayedMaterial, BlockStateProvider chestBlockProvider
      ) {
         boolean doFence = floorMaterial.get(Variant.FENCE) != null;
         Variant basementSupports = doFence ? Variant.FENCE : Variant.WALL;
         TFBlockFamilies.verifyFamilyShapes(floorMaterial, Variant.SLAB, Variant.STAIRS, basementSupports);
         TFBlockFamilies.verifyFamilyShapes(wallMaterial, Variant.SLAB, Variant.STAIRS);
         TFBlockFamilies.verifyFamilyShapes(decayedMaterial, Variant.SLAB, Variant.STAIRS);
         BlockState floorStairs = floorMaterial.get(Variant.STAIRS).defaultBlockState();
         BlockState wallBlock = wallMaterial.getBaseBlock().defaultBlockState();
         BlockState wallStairs = wallMaterial.get(Variant.STAIRS).defaultBlockState();
         BlockState decayedWall = decayedMaterial.getBaseBlock().defaultBlockState();
         BlockState decayedStairs = decayedMaterial.get(Variant.STAIRS).defaultBlockState();
         WeightedStateProvider floor = new WeightedStateProvider(
            SimpleWeightedRandomList.builder()
               .add(floorMaterial.getBaseBlock().defaultBlockState(), 39)
               .add((BlockState)floorMaterial.get(Variant.SLAB).defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, floorWaterlogged), 1)
               .add(
                  (BlockState)((BlockState)floorStairs.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH))
                     .setValue(BlockStateProperties.WATERLOGGED, floorWaterlogged),
                  6
               )
               .add(
                  (BlockState)((BlockState)floorStairs.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST))
                     .setValue(BlockStateProperties.WATERLOGGED, floorWaterlogged),
                  2
               )
               .add(
                  (BlockState)((BlockState)floorStairs.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH))
                     .setValue(BlockStateProperties.WATERLOGGED, floorWaterlogged),
                  6
               )
               .add(
                  (BlockState)((BlockState)floorStairs.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST))
                     .setValue(BlockStateProperties.WATERLOGGED, floorWaterlogged),
                  2
               )
               .build()
         );
         WeightedStateProvider wallTop = new WeightedStateProvider(
            SimpleWeightedRandomList.builder()
               .add(wallBlock, 5)
               .add(wallMaterial.get(Variant.SLAB).defaultBlockState(), 1)
               .add((BlockState)wallStairs.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST), 2)
               .add((BlockState)wallStairs.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), 2)
               .build()
         );
         WeightedStateProvider decayedTop = new WeightedStateProvider(
            SimpleWeightedRandomList.builder()
               .add(decayedWall, 5)
               .add(decayedMaterial.get(Variant.SLAB).defaultBlockState(), 1)
               .add((BlockState)decayedStairs.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST), 2)
               .add((BlockState)decayedStairs.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), 2)
               .build()
         );
         return new RuinedFoundationConfig.RuinedFoundationBlocks(
            floor,
            BlockStateProvider.simple(floorMaterial.get(basementSupports).defaultBlockState()),
            chestBlockProvider,
            BlockStateProvider.simple(wallBlock),
            wallTop,
            BlockStateProvider.simple(decayedWall),
            decayedTop
         );
      }
   }

   public record RuinedFoundationDimensions(IntProvider wallWidth, IntProvider wallHeights, IntProvider basementHeight, FloatProvider placeFloorTest) {
      public static final MapCodec<RuinedFoundationConfig.RuinedFoundationDimensions> CODEC = RecordCodecBuilder.mapCodec(
         inst -> inst.group(
               IntProvider.codec(1, 16).fieldOf("wall_width").forGetter(RuinedFoundationConfig.RuinedFoundationDimensions::wallWidth),
               IntProvider.codec(1, 32).fieldOf("wall_heights").forGetter(RuinedFoundationConfig.RuinedFoundationDimensions::wallHeights),
               IntProvider.codec(0, 16).fieldOf("basement_height").forGetter(RuinedFoundationConfig.RuinedFoundationDimensions::basementHeight),
               FloatProvider.codec(-8.0F, 8.0F).fieldOf("random_floor_chance").forGetter(RuinedFoundationConfig.RuinedFoundationDimensions::placeFloorTest)
            )
            .apply(inst, RuinedFoundationConfig.RuinedFoundationDimensions::new)
      );

      public static RuinedFoundationConfig.RuinedFoundationDimensions makeDefault() {
         WeightedListInt basementHeights = new WeightedListInt(SimpleWeightedRandomList.builder().add(ConstantInt.of(3), 1).add(ConstantInt.of(0), 1).build());
         return new RuinedFoundationConfig.RuinedFoundationDimensions(UniformInt.of(5, 9), UniformInt.of(1, 5), basementHeights, UniformFloat.of(-2.0F, 1.0F));
      }
   }
}
