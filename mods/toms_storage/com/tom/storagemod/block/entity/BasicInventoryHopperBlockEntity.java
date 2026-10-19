package com.tom.storagemod.block.entity;

import com.tom.storagemod.Config;
import com.tom.storagemod.Content;
import com.tom.storagemod.block.AbstractInventoryHopperBlock;
import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.InventorySlot;
import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.inventory.filter.ItemPredicate;
import com.tom.storagemod.item.IItemFilter;
import com.tom.storagemod.util.BlockFaceReference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class BasicInventoryHopperBlockEntity extends AbstractInventoryHopperBlockEntity implements Clearable {
   private ItemStack filter = ItemStack.EMPTY;
   private int cooldown;
   private long topChange;
   private long bottomChange;
   public int waiting = 0;
   private ItemPredicate filterPred;
   private InventorySlot topSlot;

   public BasicInventoryHopperBlockEntity(BlockPos pos, BlockState state) {
      super(Content.basicInvHopperBE.get(), pos, state);
   }

   public void saveAdditional(CompoundTag compound, Provider provider) {
      super.saveAdditional(compound, provider);
      ItemStack is = this.getFilter();
      if (!is.isEmpty()) {
         compound.put("Filter", is.save(provider, new CompoundTag()));
      }
   }

   public void loadAdditional(CompoundTag nbtIn, Provider provider) {
      super.loadAdditional(nbtIn, provider);
      this.filter = ItemStack.parseOptional(provider, nbtIn.getCompound("Filter"));
   }

   public void setFilter(ItemStack filter) {
      this.filter = filter;
      if (this.filter.isEmpty()) {
         this.filterPred = null;
      } else if (this.filter.getItem() instanceof IItemFilter i) {
         this.filterPred = i.createFilter(
            BlockFaceReference.touching(this.level, this.worldPosition, (Direction)this.getBlockState().getValue(AbstractInventoryHopperBlock.FACING)), filter
         );
      } else {
         this.filterPred = s -> ItemStack.isSameItemSameComponents(s.getStack(), filter);
      }

      this.waiting = 0;
      this.setChanged();
   }

   public ItemStack getFilter() {
      return this.filter;
   }

   @Override
   public void updateServer() {
      if (!this.filter.isEmpty() && this.filterPred == null) {
         this.setFilter(this.filter);
      }

      BlockState state = this.level.getBlockState(this.worldPosition);
      Direction facing = (Direction)state.getValue(AbstractInventoryHopperBlock.FACING);
      IInventoryAccess top = this.topCache.getAccess(this.level, this.worldPosition.relative(facing.getOpposite()));
      IInventoryAccess bottom = this.bottomCache.getAccess(this.level, this.worldPosition.relative(facing));
      boolean topNet = this.topCache.isNetwork();
      if (this.topCache.isValid() && this.bottomCache.isValid()) {
         if (topNet || this.bottomCache.isNetwork()) {
            int baseCd = Math.max(1, Config.get().basicHopperCooldown);
            int midCd = Math.max(1, baseCd * 4 / 10);
            int fastCd = Math.max(1, baseCd / 10);
            if (this.cooldown > 0) {
               this.cooldown--;
            } else {
               boolean hasFilter = this.filterPred != null;
               if (!topNet || hasFilter) {
                  if (this.isEnabled()) {
                     IInventoryAccess.IInventoryChangeTracker tt = top.tracker();
                     long t = tt.getChangeTracker(this.level);
                     if (this.topChange != t) {
                        this.topChange = t;
                        this.waiting = 0;
                        this.topSlot = null;
                     } else {
                        this.cooldown = midCd;
                     }

                     if (this.waiting != 1) {
                        IInventoryAccess.IInventoryChangeTracker bt = bottom.tracker();
                        long b = bt.getChangeTracker(this.level);
                        if (this.bottomChange != b) {
                           this.bottomChange = b;
                           this.waiting = 0;
                        } else {
                           this.cooldown = midCd;
                        }

                        if (this.waiting != 2) {
                           boolean topWasNull = this.topSlot == null;
                           if (hasFilter) {
                              this.filterPred.updateState();
                           }

                           if (this.topSlot == null || this.waiting == 3) {
                              this.topSlot = tt.findSlotAfter(this.topSlot, hasFilter ? this.filterPred : s -> true, false, true);
                           }

                           if (this.topSlot == null) {
                              if (topWasNull) {
                                 this.waiting = 1;
                                 this.cooldown = baseCd;
                              } else {
                                 this.cooldown = midCd;
                              }
                           } else {
                              ItemStack is = this.topSlot.getStack();
                              if (is.isEmpty()) {
                                 this.waiting = 3;
                                 this.cooldown = fastCd;
                              } else {
                                 StoredItemStack st = new StoredItemStack(is);
                                 if (hasFilter && !this.filterPred.test(st)) {
                                    this.waiting = 3;
                                    this.cooldown = fastCd;
                                 } else {
                                    InventorySlot bottomSlot = bt.findSlotDest(st);
                                    if (bottomSlot == null) {
                                       this.waiting = 3;
                                       this.cooldown = baseCd;
                                    } else {
                                       if (this.topSlot.transferTo(1, bottomSlot)) {
                                          this.cooldown = baseCd;
                                       } else {
                                          this.waiting = 3;
                                          this.cooldown = baseCd;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void clearContent() {
      this.setFilter(ItemStack.EMPTY);
   }
}
