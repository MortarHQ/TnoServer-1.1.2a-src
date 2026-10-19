package cn.leolezury.eternalstarlight.common.block.spawner;

import cn.leolezury.eternalstarlight.common.block.entity.spawner.BossSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.StarlightGolemSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.serialization.MapCodec;
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

public class StarlightGolemSpawnerBlock extends BaseEntityBlock {
   public static final MapCodec<StarlightGolemSpawnerBlock> CODEC = simpleCodec(StarlightGolemSpawnerBlock::new);

   public StarlightGolemSpawnerBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new StarlightGolemSpawnerBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, ESBlockEntities.STARLIGHT_GOLEM_SPAWNER.get(), BossSpawnerBlockEntity::tick);
   }
}
