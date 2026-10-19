package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public final class ContainerSculptorTrade extends ContainerTradeBase {
   private final EntitySculptor sculptor;
   private final InventorySculptor inventorySculptor;

   public ContainerSculptorTrade(int id, Inventory playerInventory) {
      this(id, (EntitySculptor)MMCommon.PROXY.getReferencedMob(), playerInventory);
   }

   public ContainerSculptorTrade(int id, EntitySculptor sculptor, Inventory playerInv) {
      this(id, sculptor, new InventorySculptor(sculptor), playerInv);
   }

   public ContainerSculptorTrade(int id, EntitySculptor sculptor, InventorySculptor inventory, Inventory playerInv) {
      super((MenuType<?>)ContainerHandler.CONTAINER_SCULPTOR_TRADE.get(), id, sculptor, inventory, playerInv);
      this.sculptor = sculptor;
      this.inventorySculptor = inventory;
   }

   @Override
   protected void addCustomSlots(Inventory playerInv) {
      this.addSlot(new Slot(this.inventory, 0, 69, 54));
   }

   @Override
   public void removed(Player player) {
      super.removed(player);
      if (this.sculptor != null) {
         this.sculptor.setCustomer(null);
      }
   }

   public EntitySculptor getSculptor() {
      return this.sculptor;
   }

   public InventorySculptor getInventorySculptor() {
      return this.inventorySculptor;
   }

   @Override
   public boolean stillValid(Player player) {
      return super.stillValid(player) && !this.sculptor.isTesting();
   }
}
