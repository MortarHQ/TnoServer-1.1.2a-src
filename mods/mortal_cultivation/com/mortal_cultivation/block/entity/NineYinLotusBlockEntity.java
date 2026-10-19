package com.mortal_cultivation.block.entity;

import com.mortal_cultivation.block.NineYinLotusBlock;
import com.mortal_cultivation.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;

public final class NineYinLotusBlockEntity extends BlockEntity {
   public static final int REGROWTH_TICKS = 36000;
   private static final String REGROW_AT_TAG = "RegrowAtGameTime";
   private long regrowAtGameTime = -1L;

   public NineYinLotusBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)BlockEntityRegistry.NINE_YIN_LOTUS.get(), pos, state);
   }

   public void startRegrowth(long gameTime) {
      this.regrowAtGameTime = gameTime + 36000L;
      this.setChanged();
   }

   public static void serverTick(Level level, BlockPos pos, BlockState state, NineYinLotusBlockEntity lotus) {
      if (!level.isClientSide && !(Boolean)state.getValue(NineYinLotusBlock.BLOOMING)) {
         if (lotus.regrowAtGameTime < 0L) {
            lotus.startRegrowth(level.getGameTime());
         }

         if (level.getGameTime() >= lotus.regrowAtGameTime) {
            if (!state.canSurvive(level, pos)) {
               level.destroyBlock(pos, false);
            } else {
               BlockState blooming = (BlockState)state.setValue(NineYinLotusBlock.BLOOMING, true);
               if (level.setBlock(pos, blooming, 2)) {
                  lotus.regrowAtGameTime = -1L;
                  lotus.setChanged();
                  level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(blooming));
               }
            }
         }
      }
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.regrowAtGameTime = tag.contains("RegrowAtGameTime", 4) ? tag.getLong("RegrowAtGameTime") : -1L;
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      if (this.regrowAtGameTime >= 0L) {
         tag.putLong("RegrowAtGameTime", this.regrowAtGameTime);
      }
   }
}
