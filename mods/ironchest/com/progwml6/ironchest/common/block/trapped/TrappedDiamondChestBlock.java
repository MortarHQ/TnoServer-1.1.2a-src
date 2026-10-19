package com.progwml6.ironchest.common.block.trapped;

import com.mojang.serialization.MapCodec;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedDiamondChestBlockEntity;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TrappedDiamondChestBlock extends AbstractTrappedIronChestBlock {
   public static final MapCodec<TrappedDiamondChestBlock> CODEC = simpleCodec(TrappedDiamondChestBlock::new);

   public TrappedDiamondChestBlock(Properties properties) {
      super(properties, IronChestsBlockEntityTypes.TRAPPED_DIAMOND_CHEST::get, IronChestsTypes.DIAMOND);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return new TrappedDiamondChestBlockEntity(blockPos, blockState);
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }
}
