package noobanidus.mods.lootr.common.block.entity;

import com.google.auto.service.AutoService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;

public class LootrInventoryBlockEntity extends LootrChestBlockEntity {
   public LootrInventoryBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
      super(LootrRegistry.getInventoryBlockEntity(), pWorldPosition, pBlockState);
   }

   @Override
   protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int p_155868_, int p_155869_) {
      super.signalOpenCount(level, pos, state, p_155868_, p_155869_);
      if (LootrAPI.isCustomTrapped() && p_155868_ != p_155869_) {
         Block block = state.getBlock();
         level.updateNeighborsAt(pos, block);
         level.updateNeighborsAt(pos.below(), block);
      }
   }

   @Override
   public ILootrType getInfoNewType() {
      return BuiltInLootrTypes.INVENTORY;
   }

   @Override
   public boolean isInfoReferenceInventory() {
      return true;
   }

   @Deprecated
   @Override
   public LootrBlockType getInfoBlockType() {
      return LootrBlockType.INVENTORY;
   }

   @AutoService(ILootrBlockEntityConverter.class)
   public static class DefaultBlockEntityConverter implements ILootrBlockEntityConverter<LootrInventoryBlockEntity> {
      public ILootrBlockEntity apply(LootrInventoryBlockEntity blockEntity) {
         return blockEntity;
      }

      @Override
      public BlockEntityType<?> getBlockEntityType() {
         return LootrRegistry.getInventoryBlockEntity();
      }
   }
}
