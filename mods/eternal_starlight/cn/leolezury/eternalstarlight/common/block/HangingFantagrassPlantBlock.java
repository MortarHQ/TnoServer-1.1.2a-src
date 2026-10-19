package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class HangingFantagrassPlantBlock extends GrowingPlantBodyBlock {
   public static final MapCodec<HangingFantagrassPlantBlock> CODEC = simpleCodec(HangingFantagrassPlantBlock::new);

   public HangingFantagrassPlantBlock(Properties properties) {
      super(properties, Direction.DOWN, HangingFantagrassBlock.SHAPE, false);
   }

   protected MapCodec<HangingFantagrassPlantBlock> codec() {
      return CODEC;
   }

   public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return ESItems.HANGING_FANTAGRASS.get().getDefaultInstance();
   }

   protected GrowingPlantHeadBlock getHeadBlock() {
      return (GrowingPlantHeadBlock)ESBlocks.HANGING_FANTAGRASS.get();
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
}
