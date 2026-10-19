package com.progwml6.ironchest.common.block.trapped;

import com.mojang.serialization.MapCodec;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedDirtChestBlockEntity;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TrappedDirtChestBlock extends AbstractTrappedIronChestBlock {
   public static final MapCodec<TrappedDirtChestBlock> CODEC = simpleCodec(TrappedDirtChestBlock::new);

   public TrappedDirtChestBlock(Properties properties) {
      super(properties, IronChestsBlockEntityTypes.TRAPPED_DIRT_CHEST::get, IronChestsTypes.DIRT);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return new TrappedDirtChestBlockEntity(blockPos, blockState);
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }
}
