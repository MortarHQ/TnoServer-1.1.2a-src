package com.mortal_cultivation.gui.menu;

import com.mortal_cultivation.block.entity.AlchemyFurnaceBlockEntity;
import com.mortal_cultivation.init.MortalCultivationModMenus;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class AlchemyFurnaceMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
   public static final HashMap<String, Object> guistate = new HashMap<>();
   public final Level world;
   public final Player entity;
   public int x;
   public int y;
   public int z;
   private IItemHandler internal;
   private final Map<Integer, Slot> customSlots = new HashMap<>();
   private boolean bound = false;
   private final ContainerData data;

   public AlchemyFurnaceMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
      this(id, inv, extraData, new ItemStackHandler(7), new SimpleContainerData(4));
   }

   public AlchemyFurnaceMenu(int id, Inventory inv, FriendlyByteBuf extraData, IItemHandler handler, ContainerData data) {
      super((MenuType)MortalCultivationModMenus.ALCHEMY_FURNACE.get(), id);
      this.entity = inv.player;
      this.world = inv.player.level();
      this.internal = handler;
      BlockPos pos = null;
      if (extraData != null) {
         pos = extraData.readBlockPos();
         this.x = pos.getX();
         this.y = pos.getY();
         this.z = pos.getZ();
      }

      ContainerData blockEntityData = data;
      if (pos != null) {
         if (extraData.readableBytes() == 1) {
            byte hand = extraData.readByte();
            ItemStack itemstack;
            if (hand == 0) {
               itemstack = this.entity.getMainHandItem();
            } else {
               itemstack = this.entity.getOffhandItem();
            }

            IItemHandler capability = (IItemHandler)itemstack.getCapability(ItemHandler.ITEM);
            if (capability != null) {
               this.internal = capability;
               this.bound = true;
            }
         } else if (extraData.readableBytes() > 1) {
            extraData.readByte();
            Entity entity = this.world.getEntity(extraData.readVarInt());
            if (entity != null) {
               IItemHandler capability = (IItemHandler)entity.getCapability(ItemHandler.ENTITY);
               if (capability != null) {
                  this.internal = capability;
                  this.bound = true;
               }
            }
         } else {
            BlockEntity ent = inv.player != null ? inv.player.level().getBlockEntity(pos) : null;
            if (ent != null) {
               if (ent instanceof AlchemyFurnaceBlockEntity furnace) {
                  this.internal = furnace.getItemHandler();
                  this.bound = true;
                  blockEntityData = furnace.getContainerData();
               } else {
                  IItemHandler capability = (IItemHandler)this.world.getCapability(ItemHandler.BLOCK, pos, null);
                  if (capability != null) {
                     this.internal = capability;
                     this.bound = true;
                  }
               }
            }
         }
      }

      this.data = blockEntityData;
      this.customSlots.put(0, this.addSlot(new SlotItemHandler(this.internal, 0, 25, 25) {}));
      this.customSlots.put(1, this.addSlot(new SlotItemHandler(this.internal, 1, 43, 59) {
         public boolean mayPlace(ItemStack stack) {
            return stack.getBurnTime(null) > 0;
         }
      }));
      this.customSlots.put(2, this.addSlot(new SlotItemHandler(this.internal, 2, 43, 25) {}));
      this.customSlots.put(3, this.addSlot(new SlotItemHandler(this.internal, 3, 61, 25) {}));
      this.customSlots.put(4, this.addSlot(new SlotItemHandler(this.internal, 4, 133, 32) {
         public boolean mayPlace(ItemStack stack) {
            return false;
         }
      }));
      this.customSlots.put(5, this.addSlot(new SlotItemHandler(this.internal, 5, 124, 50) {
         public boolean mayPlace(ItemStack stack) {
            return false;
         }
      }));
      this.customSlots.put(6, this.addSlot(new SlotItemHandler(this.internal, 6, 142, 50) {
         public boolean mayPlace(ItemStack stack) {
            return false;
         }
      }));

      for (int si = 0; si < 3; si++) {
         for (int sj = 0; sj < 9; sj++) {
            this.addSlot(new Slot(inv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
         }
      }

      for (int si = 0; si < 9; si++) {
         this.addSlot(new Slot(inv, si, 8 + si * 18, 142));
      }

      this.addDataSlots(this.data);
   }

   public boolean isBurning() {
      return this.data.get(0) > 0;
   }

   public int getBurnProgress() {
      int burnTime = this.data.get(0);
      int totalBurnTime = this.data.get(1);
      return totalBurnTime != 0 && burnTime != 0 ? burnTime * 100 / totalBurnTime : 0;
   }

   public int getCookProgress() {
      int cookTime = this.data.get(2);
      int cookTimeTotal = this.data.get(3);
      return cookTimeTotal != 0 && cookTime != 0 ? cookTime * 100 / cookTimeTotal : 0;
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public ItemStack quickMoveStack(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(index);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (index >= 7) {
            boolean isFuel = itemstack1.getBurnTime(null) > 0;
            if (isFuel && this.moveItemStackTo(itemstack1, 1, 2, false)) {
               return ItemStack.EMPTY;
            }

            if (!this.moveItemStackTo(itemstack1, 0, 1, false) && !this.moveItemStackTo(itemstack1, 2, 4, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(itemstack1, 7, this.slots.size(), true)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.getCount() == 0) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(playerIn, itemstack1);
      }

      return itemstack;
   }

   protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
      boolean flag = false;
      int i = p_38905_;
      if (p_38907_) {
         i = p_38906_ - 1;
      }

      if (p_38904_.isStackable()) {
         while (!p_38904_.isEmpty() && (p_38907_ ? i >= p_38905_ : i < p_38906_)) {
            Slot slot = (Slot)this.slots.get(i);
            ItemStack itemstack = slot.getItem();
            if (slot.mayPlace(itemstack) && !itemstack.isEmpty() && ItemStack.isSameItemSameComponents(p_38904_, itemstack)) {
               int j = itemstack.getCount() + p_38904_.getCount();
               int maxSize = Math.min(slot.getMaxStackSize(), p_38904_.getMaxStackSize());
               if (j <= maxSize) {
                  p_38904_.setCount(0);
                  itemstack.setCount(j);
                  slot.set(itemstack);
                  flag = true;
               } else if (itemstack.getCount() < maxSize) {
                  p_38904_.shrink(maxSize - itemstack.getCount());
                  itemstack.setCount(maxSize);
                  slot.set(itemstack);
                  flag = true;
               }
            }

            if (p_38907_) {
               i--;
            } else {
               i++;
            }
         }
      }

      if (!p_38904_.isEmpty()) {
         if (p_38907_) {
            i = p_38906_ - 1;
         } else {
            i = p_38905_;
         }

         while (p_38907_ ? i >= p_38905_ : i < p_38906_) {
            Slot slot1 = (Slot)this.slots.get(i);
            ItemStack itemstack1 = slot1.getItem();
            if (itemstack1.isEmpty() && slot1.mayPlace(p_38904_)) {
               if (p_38904_.getCount() > slot1.getMaxStackSize()) {
                  slot1.set(p_38904_.split(slot1.getMaxStackSize()));
               } else {
                  slot1.set(p_38904_.split(p_38904_.getCount()));
               }

               slot1.setChanged();
               flag = true;
               break;
            }

            if (p_38907_) {
               i--;
            } else {
               i++;
            }
         }
      }

      return flag;
   }

   public void removed(Player playerIn) {
      super.removed(playerIn);
      if (!this.bound && playerIn instanceof ServerPlayer serverPlayer) {
         if (serverPlayer.isAlive() && !serverPlayer.hasDisconnected()) {
            for (int i = 0; i < this.internal.getSlots(); i++) {
               playerIn.getInventory().placeItemBackInInventory(this.internal.extractItem(i, this.internal.getStackInSlot(i).getCount(), false));
            }
         } else {
            for (int j = 0; j < this.internal.getSlots(); j++) {
               playerIn.drop(this.internal.extractItem(j, this.internal.getStackInSlot(j).getCount(), false), false);
            }
         }
      }
   }

   public Map<Integer, Slot> get() {
      return this.customSlots;
   }
}
