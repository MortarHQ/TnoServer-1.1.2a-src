package com.chronodawn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.Nullable;

public class BossRoomDoorBlock extends DoorBlock implements EntityBlock {
   public static final MapCodec<BossRoomDoorBlock> CODEC = simpleCodec(BossRoomDoorBlock::new);

   public BossRoomDoorBlock(Properties properties) {
      super(BlockSetType.IRON, properties);
   }

   public MapCodec<? extends DoorBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new BossRoomDoorBlockEntity(pos, state);
   }
}
