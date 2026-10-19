package com.progwml6.ironchest.common.block.trapped.entity;

import com.progwml6.ironchest.common.Util;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.datacomponents.IronChestsDataComponents;
import com.progwml6.ironchest.common.inventory.IronChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;

public class TrappedDirtChestBlockEntity extends AbstractTrappedIronChestBlockEntity {
   private static final ItemStack DIRT_CHEST_BOOK = Util.createDirtGuideBook();

   public TrappedDirtChestBlockEntity(BlockPos blockPos, BlockState blockState) {
      super(
         (BlockEntityType<?>)IronChestsBlockEntityTypes.TRAPPED_DIRT_CHEST.get(),
         blockPos,
         blockState,
         IronChestsTypes.DIRT,
         IronChestsBlocks.TRAPPED_DIRT_CHEST::get
      );
   }

   protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
      return IronChestMenu.createDirtContainer(containerId, playerInventory, this);
   }

   @Override
   public void removeAdornments() {
      if (!((ItemStack)this.getItems().get(0)).isEmpty() && ItemStack.isSameItem((ItemStack)this.getItems().get(0), DIRT_CHEST_BOOK)) {
         this.getItems().set(0, ItemStack.EMPTY);
      }
   }

   @Override
   public void saveAdditional(CompoundTag pTag, Provider pRegistries) {
      super.saveAdditional(pTag, pRegistries);
      pTag.putBoolean("chest_placed_already", true);
   }

   protected void applyImplicitComponents(DataComponentInput pComponentInput) {
      super.applyImplicitComponents(pComponentInput);
      if (!(Boolean)pComponentInput.getOrDefault((DataComponentType)IronChestsDataComponents.CHEST_PLACED_ALREADY.get(), false)) {
         this.setItem(0, DIRT_CHEST_BOOK.copy());
      }
   }

   protected void collectImplicitComponents(Builder pComponents) {
      super.collectImplicitComponents(pComponents);
      pComponents.set((DataComponentType)IronChestsDataComponents.CHEST_PLACED_ALREADY.get(), true);
   }

   public void removeComponentsFromTag(CompoundTag pTag) {
      super.removeComponentsFromTag(pTag);
      pTag.remove("chest_placed_already");
   }
}
