package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;

public class RedVelvetumossBlock extends Block implements BonemealableBlock {
   public static final MapCodec<RedVelvetumossBlock> CODEC = simpleCodec(RedVelvetumossBlock::new);

   public RedVelvetumossBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<RedVelvetumossBlock> codec() {
      return CODEC;
   }

   protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      if (randomSource.nextInt(12) == 0
         && Arrays.stream(Direction.values())
            .noneMatch(directionx -> serverLevel.getBlockState(blockPos.relative(directionx)).is((Block)ESBlocks.RED_VELVETUMOSS_FLOWER.get()))) {
         for (Direction direction : Direction.values()) {
            BlockPos growPos = blockPos.relative(direction);
            if (serverLevel.getBlockState(growPos).is(Blocks.WATER) && randomSource.nextInt(5) == 0) {
               serverLevel.setBlockAndUpdate(
                  growPos,
                  (BlockState)(direction == Direction.UP && randomSource.nextInt(12) == 0
                        ? ((AquaticFlowerBlock)ESBlocks.RED_VELVETUMOSS_FLOWER.get()).defaultBlockState()
                        : (BlockState)ESBlocks.RED_VELVETUMOSS_VILLI.get().defaultBlockState().setValue(DirectionalBudBlock.FACING, direction))
                     .setValue(DirectionalBudBlock.WATERLOGGED, true)
               );
            }
         }
      }
   }

   public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
      super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
      if (!EnchantmentHelper.hasTag(itemStack, EnchantmentTags.PREVENTS_ICE_MELTING)) {
         if (level.dimensionType().ultraWarm()) {
            level.removeBlock(blockPos, false);
            return;
         }

         level.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
      }
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return Arrays.stream(Direction.values()).anyMatch(direction -> levelReader.getBlockState(blockPos.relative(direction)).is(Blocks.WATER))
         && Arrays.stream(Direction.values())
            .noneMatch(direction -> levelReader.getBlockState(blockPos.relative(direction)).is((Block)ESBlocks.RED_VELVETUMOSS_FLOWER.get()));
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      return Arrays.stream(Direction.values()).anyMatch(direction -> level.getBlockState(blockPos.relative(direction)).is(Blocks.WATER))
         && Arrays.stream(Direction.values())
            .noneMatch(direction -> level.getBlockState(blockPos.relative(direction)).is((Block)ESBlocks.RED_VELVETUMOSS_FLOWER.get()));
   }

   public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      if (Arrays.stream(Direction.values())
         .noneMatch(directionx -> serverLevel.getBlockState(blockPos.relative(directionx)).is((Block)ESBlocks.RED_VELVETUMOSS_FLOWER.get()))) {
         for (Direction direction : Direction.values()) {
            BlockPos growPos = blockPos.relative(direction);
            if (serverLevel.getBlockState(growPos).is(Blocks.WATER) && randomSource.nextBoolean()) {
               serverLevel.setBlockAndUpdate(
                  growPos,
                  (BlockState)(direction == Direction.UP && randomSource.nextInt(12) == 0
                        ? ((AquaticFlowerBlock)ESBlocks.RED_VELVETUMOSS_FLOWER.get()).defaultBlockState()
                        : (BlockState)ESBlocks.RED_VELVETUMOSS_VILLI.get().defaultBlockState().setValue(DirectionalBudBlock.FACING, direction))
                     .setValue(DirectionalBudBlock.WATERLOGGED, true)
               );
            }
         }
      }
   }
}
