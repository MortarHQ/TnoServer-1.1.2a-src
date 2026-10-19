package com.progwml6.ironchest.common.block.entity;

import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.network.TopStacksSyncPacket;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;

public interface ICrystalChest {
   default NonNullList<ItemStack> buildItemStackDataList() {
      if (this.getChestType().isTransparent()) {
         NonNullList<ItemStack> sortList = NonNullList.withSize(this.getTopItems().size(), ItemStack.EMPTY);
         int pos = 0;

         for (ItemStack is : this.getTopItems()) {
            if (!is.isEmpty()) {
               sortList.set(pos, is);
            } else {
               sortList.set(pos, ItemStack.EMPTY);
            }

            pos++;
         }

         return sortList;
      } else {
         return NonNullList.withSize(this.getTopItems().size(), ItemStack.EMPTY);
      }
   }

   IronChestsTypes getChestType();

   default void sendTopStacksPacket() {
      NonNullList<ItemStack> stacks = this.buildItemStackDataList();
      if (this.getChestLevel() != null && this.getChestLevel() instanceof ServerLevel serverLevel && !this.getChestLevel().isClientSide) {
         PacketDistributor.sendToPlayersTrackingChunk(
            serverLevel,
            serverLevel.getChunkAt(this.getChestWorldPosition()).getPos(),
            new TopStacksSyncPacket(this.getChestWorldPosition(), stacks),
            new CustomPacketPayload[0]
         );
      }
   }

   NonNullList<ItemStack> getTopItems();

   @Nullable
   Level getChestLevel();

   BlockPos getChestWorldPosition();

   void receiveMessageFromServer(NonNullList<ItemStack> var1);

   default void sortTopStacks() {
      if (this.getChestType().isTransparent() && (this.getChestLevel() == null || !this.getChestLevel().isClientSide)) {
         NonNullList<ItemStack> tempCopy = NonNullList.withSize(IronChestsTypes.CRYSTAL.size, ItemStack.EMPTY);
         boolean hasStuff = false;
         int compressedIdx = 0;

         label85:
         for (int i = 0; i < IronChestsTypes.CRYSTAL.size; i++) {
            ItemStack itemStack = (ItemStack)this.getCurrentItems().get(i);
            if (!itemStack.isEmpty()) {
               for (int j = 0; j < compressedIdx; j++) {
                  ItemStack tempCopyStack = (ItemStack)tempCopy.get(j);
                  if (ItemStack.isSameItemSameComponents(tempCopyStack, itemStack)) {
                     if (itemStack.getCount() != tempCopyStack.getCount()) {
                        tempCopyStack.grow(itemStack.getCount());
                     }
                     continue label85;
                  }
               }

               tempCopy.set(compressedIdx, itemStack.copy());
               compressedIdx++;
               hasStuff = true;
            }
         }

         if (!hasStuff && this.getHadStuff()) {
            this.setHadStuff(false);

            for (int i = 0; i < this.getTopItems().size(); i++) {
               this.getTopItems().set(i, ItemStack.EMPTY);
            }

            if (this.getChestLevel() != null) {
               BlockState blockState = this.getChestLevel().getBlockState(this.getChestWorldPosition());
               this.getChestLevel().sendBlockUpdated(this.getChestWorldPosition(), blockState, blockState, 3);
            }
         } else {
            this.setHadStuff(true);
            tempCopy.sort((stack1, stack2) -> {
               if (stack1.isEmpty()) {
                  return 1;
               } else {
                  return stack2.isEmpty() ? -1 : stack2.getCount() - stack1.getCount();
               }
            });
            int slot = 0;

            for (ItemStack itemStack : tempCopy) {
               if (!itemStack.isEmpty() && itemStack.getCount() > 0) {
                  if (slot == this.getTopItems().size()) {
                     break;
                  }

                  this.getTopItems().set(slot, itemStack);
                  slot++;
               }
            }

            for (int i = slot; i < this.getTopItems().size(); i++) {
               this.getTopItems().set(i, ItemStack.EMPTY);
            }

            if (this.getChestLevel() != null) {
               BlockState blockState = this.getChestLevel().getBlockState(this.getChestWorldPosition());
               this.getChestLevel().sendBlockUpdated(this.getChestWorldPosition(), blockState, blockState, 3);
            }

            this.sendTopStacksPacket();
         }
      }
   }

   NonNullList<ItemStack> getCurrentItems();

   void setHadStuff(boolean var1);

   boolean getHadStuff();
}
