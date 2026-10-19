package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.Trade;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public final class ContainerUmvuthanaTrade extends ContainerTradeBase {
   private final EntityUmvuthanaMinion umvuthanaMinion;
   private final InventoryUmvuthana inventoryUmvuthana;

   public ContainerUmvuthanaTrade(int id, Inventory playerInventory) {
      this(id, (EntityUmvuthanaMinion)MMCommon.PROXY.getReferencedMob(), playerInventory);
   }

   public ContainerUmvuthanaTrade(int id, EntityUmvuthanaMinion barakoaya, Inventory playerInv) {
      this(id, barakoaya, new InventoryUmvuthana(barakoaya), playerInv);
   }

   public ContainerUmvuthanaTrade(int id, EntityUmvuthanaMinion umvuthanaMinion, InventoryUmvuthana inventory, Inventory playerInv) {
      super((MenuType<?>)ContainerHandler.CONTAINER_UMVUTHANA_TRADE.get(), id, umvuthanaMinion, inventory, playerInv);
      this.inventoryUmvuthana = inventory;
      this.umvuthanaMinion = umvuthanaMinion;
   }

   @Override
   protected void addCustomSlots(Inventory playerInv) {
      this.addSlot(new Slot(this.getInventory(), 0, 80, 54));
      this.addSlot(new ContainerUmvuthanaTrade.SlotResult(this.getInventory(), 1, 133, 54));
   }

   public void slotsChanged(Container inv) {
      this.inventoryUmvuthana.reset();
      super.slotsChanged(inv);
   }

   @Override
   public void removed(Player player) {
      super.removed(player);
      if (this.umvuthanaMinion != null) {
         this.umvuthanaMinion.setCustomer(null);
      }
   }

   public EntityUmvuthanaMinion getUmvuthana() {
      return this.umvuthanaMinion;
   }

   public InventoryUmvuthana getInventoryUmvuthana() {
      return this.inventoryUmvuthana;
   }

   private class SlotResult extends Slot {
      private int removeCount;

      public SlotResult(Container inventory, int index, int x, int y) {
         super(inventory, index, x, y);
      }

      public boolean mayPlace(ItemStack stack) {
         return false;
      }

      public ItemStack remove(int amount) {
         if (this.hasItem()) {
            this.removeCount = this.removeCount + Math.min(amount, this.getItem().getCount());
         }

         return super.remove(amount);
      }

      protected void onQuickCraft(ItemStack stack, int amount) {
         this.removeCount += amount;
         super.onQuickCraft(stack, amount);
      }

      protected void checkTakeAchievements(ItemStack stack) {
         stack.onCraftedBy(ContainerUmvuthanaTrade.this.umvuthanaMinion.level(), ContainerUmvuthanaTrade.this.player, this.removeCount);
         this.removeCount = 0;
      }

      public ItemStack safeTake(int p_150648_, int p_150649_, Player p_150650_) {
         return super.safeTake(p_150648_, p_150649_, p_150650_);
      }

      public void onTake(Player player, ItemStack stack) {
         this.checkTakeAchievements(stack);
         if (ContainerUmvuthanaTrade.this.umvuthanaMinion != null && ContainerUmvuthanaTrade.this.umvuthanaMinion.isOfferingTrade()) {
            Trade trade = ContainerUmvuthanaTrade.this.umvuthanaMinion.getOfferingTrade();
            ItemStack input = this.container.getItem(0);
            ItemStack tradeInput = trade.getInput();
            if (input.getItem() == tradeInput.getItem() && input.getCount() >= tradeInput.getCount()) {
               input.shrink(tradeInput.getCount());
               if (input.getCount() <= 0) {
                  input = ItemStack.EMPTY;
               }

               this.container.setItem(0, input);
            }
         }
      }
   }
}
