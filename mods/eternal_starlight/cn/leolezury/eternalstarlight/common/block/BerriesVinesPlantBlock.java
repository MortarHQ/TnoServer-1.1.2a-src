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
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public class BerriesVinesPlantBlock extends GrowingPlantBodyBlock implements BerriesVines {
   public static final MapCodec<BerriesVinesPlantBlock> CODEC = simpleCodec(BerriesVinesPlantBlock::new);

   public BerriesVinesPlantBlock(Properties properties) {
      super(properties, Direction.DOWN, SHAPE, false);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(BERRIES, false));
   }

   protected MapCodec<BerriesVinesPlantBlock> codec() {
      return null;
   }

   protected GrowingPlantHeadBlock getHeadBlock() {
      return (GrowingPlantHeadBlock)ESBlocks.BERRIES_VINES.get();
   }

   protected BlockState updateHeadAfterConvertedFromBody(BlockState state, BlockState blockState) {
      return (BlockState)blockState.setValue(BERRIES, (Boolean)state.getValue(BERRIES));
   }

   public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return new ItemStack((ItemLike)ESItems.LUNAR_BERRIES.get());
   }

   protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
      return BerriesVines.use(blockState, level, blockPos);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{BERRIES});
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return !(Boolean)blockState.getValue(BERRIES);
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      serverLevel.setBlock(blockPos, (BlockState)blockState.setValue(BERRIES, true), 2);
   }
}
