package com.tensura_guild.block;

import com.mojang.serialization.MapCodec;
import com.tensura_guild.block.entity.YuukiSpawnerBlockEntity;
import com.tensura_guild.registry.GuildBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;

public class YuukiSpawnerBlock extends BaseEntityBlock {
   public static final MapCodec<YuukiSpawnerBlock> CODEC = simpleCodec(YuukiSpawnerBlock::new);

   public YuukiSpawnerBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new YuukiSpawnerBlockEntity(pos, state);
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, GuildBlockEntities.YUUKI_SPAWNER.get(), YuukiSpawnerBlockEntity::tick);
   }
}
