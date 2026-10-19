package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class TorreyaVinesPlantBlock extends GrowingPlantBodyBlock {
   public static final MapCodec<TorreyaVinesPlantBlock> CODEC = simpleCodec(TorreyaVinesPlantBlock::new);
   public static final BooleanProperty TOP = BooleanProperty.create("top");

   public TorreyaVinesPlantBlock(Properties properties) {
      super(properties, Direction.DOWN, TorreyaVinesBlock.SHAPE, false);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(TOP, false));
   }

   protected MapCodec<TorreyaVinesPlantBlock> codec() {
      return CODEC;
   }

   protected GrowingPlantHeadBlock getHeadBlock() {
      return (GrowingPlantHeadBlock)ESBlocks.TORREYA_VINES.get();
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos attachPos = pos.relative(this.growthDirection.getOpposite());
      BlockState attachState = level.getBlockState(attachPos);
      return !this.canAttachTo(attachState)
         ? false
         : attachState.is(this.getHeadBlock())
            || attachState.is(this.getBodyBlock())
            || attachState.is(BlockTags.LEAVES)
            || attachState.isFaceSturdy(level, attachPos, this.growthDirection);
   }

   public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos blockPos, BlockPos blockPos2) {
      BlockState state = super.updateShape(blockState, direction, blockState2, level, blockPos, blockPos2);
      if (!level.getBlockState(blockPos.above()).is(this) && state.hasProperty(TOP)) {
         state = (BlockState)state.setValue(TOP, true);
      }

      return state;
   }

   public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return new ItemStack((ItemLike)ESItems.TORREYA_VINES.get());
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{TOP});
   }
}
