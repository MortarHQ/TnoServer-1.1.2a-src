package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public final class ContainerUmvuthiTrade extends ContainerTradeBase {
   private final EntityUmvuthi barako;
   private final InventoryUmvuthi inventoryUmvuthi;

   public ContainerUmvuthiTrade(int id, Inventory playerInventory) {
      this(id, (EntityUmvuthi)MMCommon.PROXY.getReferencedMob(), playerInventory);
   }

   public ContainerUmvuthiTrade(int id, EntityUmvuthi barako, Inventory playerInv) {
      this(id, barako, new InventoryUmvuthi(barako), playerInv);
   }

   public ContainerUmvuthiTrade(int id, EntityUmvuthi barako, InventoryUmvuthi inventory, Inventory playerInv) {
      super((MenuType<?>)ContainerHandler.CONTAINER_UMVUTHI_TRADE.get(), id, barako, inventory, playerInv);
      this.barako = barako;
      this.inventoryUmvuthi = inventory;
   }

   @Override
   protected void addCustomSlots(Inventory playerInv) {
      EntityUmvuthi barako = (EntityUmvuthi)this.getTradingMob();
      InventoryUmvuthi inventoryUmvuthi = (InventoryUmvuthi)this.inventory;
      if (barako != null && !barako.hasTradedWith(playerInv.player)) {
         this.addSlot(new Slot(inventoryUmvuthi, 0, 69, 54));
      }
   }

   @Override
   public void removed(Player player) {
      super.removed(player);
      if (this.barako != null) {
         this.barako.setCustomer(null);
      }
   }

   public EntityUmvuthi getUmvuthi() {
      return this.barako;
   }

   public InventoryUmvuthi getInventoryUmvuthi() {
      return this.inventoryUmvuthi;
   }
}
