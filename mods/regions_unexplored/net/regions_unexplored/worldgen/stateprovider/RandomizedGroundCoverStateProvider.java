package net.regions_unexplored.worldgen.stateprovider;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.regions_unexplored.world.level.block.plant.flower.GroundCoverBlock;

public class RandomizedGroundCoverStateProvider extends BlockStateProvider {
   public static final MapCodec<RandomizedGroundCoverStateProvider> CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(RandomizedGroundCoverStateProvider::block))
         .apply(i, RandomizedGroundCoverStateProvider::new)
   );
   public static final BlockStateProviderType<RandomizedGroundCoverStateProvider> TYPE = new BlockStateProviderType(CODEC);
   private final Block block;

   public RandomizedGroundCoverStateProvider(Block block) {
      BlockState state = block.defaultBlockState();
      if (state.hasProperty(GroundCoverBlock.FACING) && state.hasProperty(GroundCoverBlock.AMOUNT)) {
         this.block = block;
      } else {
         throw new IllegalStateException("randomized_ground_cover state provider requires a block with FACING and AMOUNT properties");
      }
   }

   public static SimpleBlockConfiguration asConfig(Supplier<Block> block) {
      return new SimpleBlockConfiguration(new RandomizedGroundCoverStateProvider(block.get()));
   }

   private Block block() {
      return this.block;
   }

   protected BlockStateProviderType<?> type() {
      return TYPE;
   }

   public BlockState getState(RandomSource random, BlockPos pos) {
      return (BlockState)((BlockState)this.block.defaultBlockState().setValue(GroundCoverBlock.AMOUNT, random.nextIntBetweenInclusive(1, 4)))
         .setValue(GroundCoverBlock.FACING, Plane.HORIZONTAL.getRandomDirection(random));
   }
}
