package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BouldershroomRootsPlantBlock extends GrowingPlantBodyBlock {
   public static final MapCodec<BouldershroomRootsPlantBlock> CODEC = simpleCodec(BouldershroomRootsPlantBlock::new);

   public BouldershroomRootsPlantBlock(Properties properties) {
      super(properties, Direction.DOWN, BouldershroomRootsBlock.SHAPE, false);
   }

   protected MapCodec<BouldershroomRootsPlantBlock> codec() {
      return CODEC;
   }

   public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return ESItems.BOULDERSHROOM_ROOTS.get().getDefaultInstance();
   }

   protected GrowingPlantHeadBlock getHeadBlock() {
      return (GrowingPlantHeadBlock)ESBlocks.BOULDERSHROOM_ROOTS.get();
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return false;
   }
}
