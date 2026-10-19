package com.benji.netherman.common.block;

import com.benji.netherman.common.block.entity.SamsoniteBellBlockEntity;
import com.benji.netherman.init.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;

public class SamsoniteBellBlock extends BellBlock {
   public static final MapCodec<SamsoniteBellBlock> CODEC = simpleCodec(SamsoniteBellBlock::new);

   public SamsoniteBellBlock(Properties properties) {
      super(properties);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new SamsoniteBellBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)ModBlockEntities.SAMSONITE_BELL.get(), SamsoniteBellBlockEntity::tick);
   }
}
