package com.tensurabossstructure.block;

import com.mojang.serialization.MapCodec;
import com.tensurabossstructure.block.entity.BossSpawnerBlockEntity;
import com.tensurabossstructure.registry.ModBlockEntities;
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

public class BossSpawnerBlock extends BaseEntityBlock {
   public static final MapCodec<BossSpawnerBlock> CODEC = simpleCodec(BossSpawnerBlock::new);
   private final String entityId;

   public BossSpawnerBlock(Properties properties) {
      this(properties, "");
   }

   public BossSpawnerBlock(Properties properties, String entityId) {
      super(properties);
      this.entityId = entityId;
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new BossSpawnerBlockEntity(pos, state, this.entityId);
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)ModBlockEntities.BOSS_SPAWNER.get(), BossSpawnerBlockEntity::tick);
   }
}
