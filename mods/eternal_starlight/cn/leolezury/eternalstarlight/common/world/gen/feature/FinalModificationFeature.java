package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.block.ThioquartzBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FinalModificationFeature extends ESFeature<NoneFeatureConfiguration> {
   public FinalModificationFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos chunkCoord = this.getChunkCoordinate(context.origin());
      RandomSource random = context.random();
      MutableBlockPos pos = new MutableBlockPos();

      for (int x = chunkCoord.getX(); x < chunkCoord.getX() + 16; x++) {
         for (int z = chunkCoord.getZ(); z < chunkCoord.getZ() + 16; z++) {
            pos.set(x, 48, z);

            while (level.getBlockState(pos).is((Block)ESBlocks.ETHER.get())) {
               for (Direction direction : Direction.values()) {
                  BlockPos relativePos = pos.relative(direction);
                  BlockState relativeState = level.getBlockState(relativePos);
                  if (!relativeState.is((Block)ESBlocks.ETHER.get()) && !relativeState.is(ESBlocks.THIOQUARTZ_BLOCK.get()) && !relativeState.isAir()) {
                     this.setBlock(
                        level, pos, (BlockState)ESBlocks.THIOQUARTZ_BLOCK.get().defaultBlockState().setValue(ThioquartzBlock.SEED, random.nextDouble() < 0.1)
                     );

                     for (Direction dir : Direction.values()) {
                        if (random.nextInt(3) == 0
                           && (
                              level.getBlockState(relativePos.relative(dir)).getBlock() instanceof LiquidBlock || level.isEmptyBlock(relativePos.relative(dir))
                           )) {
                           this.setBlock(
                              level,
                              relativePos.relative(dir),
                              (BlockState)ESBlocks.THIOQUARTZ_BLOCK.get().defaultBlockState().setValue(ThioquartzBlock.SEED, random.nextDouble() < 0.1)
                           );
                        }
                     }
                  }
               }

               pos.move(Direction.DOWN);
            }
         }
      }

      return true;
   }
}
