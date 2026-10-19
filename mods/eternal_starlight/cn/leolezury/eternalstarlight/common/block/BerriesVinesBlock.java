package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public class BerriesVinesBlock extends GrowingPlantHeadBlock implements BerriesVines {
   public static final MapCodec<BerriesVinesBlock> CODEC = simpleCodec(BerriesVinesBlock::new);

   public BerriesVinesBlock(Properties properties) {
      super(properties, Direction.DOWN, SHAPE, false, 0.1);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(AGE, 0)).setValue(BERRIES, false));
   }

   protected MapCodec<BerriesVinesBlock> codec() {
      return CODEC;
   }

   protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
      return 1;
   }

   protected boolean canGrowInto(BlockState state) {
      return state.isAir();
   }

   protected Block getBodyBlock() {
      return (Block)ESBlocks.BERRIES_VINES_PLANT.get();
   }

   protected BlockState updateBodyAfterConvertedFromHead(BlockState state, BlockState blockState) {
      return (BlockState)blockState.setValue(BERRIES, (Boolean)state.getValue(BERRIES));
   }

   protected BlockState getGrowIntoState(BlockState state, RandomSource randomSource) {
      return (BlockState)super.getGrowIntoState(state, randomSource).setValue(BERRIES, randomSource.nextFloat() < 0.11F);
   }

   public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos pos, BlockState state) {
      return new ItemStack((ItemLike)ESItems.LUNAR_BERRIES.get());
   }

   protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
      return BerriesVines.use(blockState, level, blockPos);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{BERRIES});
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return !(Boolean)blockState.getValue(BERRIES);
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
      serverLevel.setBlock(pos, (BlockState)state.setValue(BERRIES, true), 2);
   }
}
