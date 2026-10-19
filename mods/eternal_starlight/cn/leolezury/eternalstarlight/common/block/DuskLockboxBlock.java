package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.DuskLockboxBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;

public class DuskLockboxBlock extends BaseEntityBlock {
   public static final MapCodec<DuskLockboxBlock> CODEC = simpleCodec(DuskLockboxBlock::new);

   public MapCodec<DuskLockboxBlock> codec() {
      return CODEC;
   }

   public DuskLockboxBlock(Properties properties) {
      super(properties);
   }

   protected boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
      return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new DuskLockboxBlockEntity(pos, state);
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
      ItemStack itemStack = super.getCloneItemStack(level, pos, state);
      level.getBlockEntity(pos, ESBlockEntities.DUSK_LOCKBOX.get()).ifPresent(blockEntity -> blockEntity.saveToItem(itemStack, level.registryAccess()));
      return itemStack;
   }
}
