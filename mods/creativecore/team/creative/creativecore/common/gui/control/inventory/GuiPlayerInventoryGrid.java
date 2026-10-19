package team.creative.creativecore.common.gui.control.inventory;

import java.util.function.BiFunction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GuiPlayerInventoryGrid extends GuiInventoryGrid {
   public GuiPlayerInventoryGrid(Player player) {
      super("player", player.getInventory(), 9, 4);
      this.setExpandableX();
   }

   @Override
   protected void createInventoryGrid(BiFunction<Container, Integer, Slot> slotFactory) {
      for (int i = 9; i < this.fixedSize; i++) {
         this.addSlot(new GuiSlot(slotFactory.apply(this.container, i)));
      }

      for (int i = 0; i < 9; i++) {
         this.addSlot(new GuiSlot(slotFactory.apply(this.container, i)));
      }
   }

   @Override
   public ItemStack moveInside(ItemStack toAdd, int slot) {
      if (slot < 10) {
         this.insertClever(toAdd, 10, 36);
      } else {
         this.insertClever(toAdd, 0, 10);
      }

      return toAdd;
   }
}
