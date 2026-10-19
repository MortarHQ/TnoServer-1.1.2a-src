package com.tom.storagemod.emi;

import com.tom.storagemod.item.IItemFilter;
import com.tom.storagemod.menu.AbstractFilteredMenu;
import com.tom.storagemod.menu.slot.FilterSlot;
import com.tom.storagemod.menu.slot.PhantomSlot;
import com.tom.storagemod.screen.AbstractFilteredScreen;
import dev.emi.emi.api.EmiDragDropHandler;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class EmiGhostIngredientHandler implements EmiDragDropHandler<Screen> {
   private static ItemStack getStack(EmiIngredient ing) {
      return ing.getEmiStacks().stream().<ItemStack>map(EmiStack::getItemStack).filter(s -> !s.isEmpty()).findFirst().orElse(ItemStack.EMPTY);
   }

   public boolean dropStack(Screen screen, EmiIngredient stackIn, int x, int y) {
      if (screen instanceof AbstractFilteredScreen scr) {
         ItemStack stack = getStack(stackIn);
         if (stack.isEmpty()) {
            return false;
         }

         for (EmiGhostIngredientHandler.DropTarget b : this.getTargets(scr, stack)) {
            if (b.getArea().contains(x, y)) {
               b.accept(stack);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public void render(Screen screen, EmiIngredient dragged, GuiGraphics matrices, int mouseX, int mouseY, float delta) {
      if (screen instanceof AbstractFilteredScreen scr) {
         ItemStack stack = getStack(dragged);
         if (!stack.isEmpty()) {
            for (EmiGhostIngredientHandler.DropTarget t : this.getTargets(scr, stack)) {
               Bounds b = t.getArea();
               matrices.fill(b.x(), b.y(), b.x() + b.width(), b.y() + b.height(), -2010989773);
            }
         }
      }
   }

   private <I> List<EmiGhostIngredientHandler.DropTarget> getTargets(AbstractFilteredScreen gui, ItemStack stack) {
      List<EmiGhostIngredientHandler.DropTarget> targets = new ArrayList<>();
      boolean filter = stack.getItem() instanceof IItemFilter;

      for (Slot slot : gui.getMenu().slots) {
         if (slot instanceof PhantomSlot) {
            targets.add(new EmiGhostIngredientHandler.SlotTarget(gui, slot));
         } else if (!filter && slot instanceof FilterSlot) {
            ItemStack s = slot.getItem();
            boolean sf = !s.isEmpty() && s.getItem() instanceof IItemFilter;
            if (!sf) {
               targets.add(new EmiGhostIngredientHandler.SlotTarget(gui, slot));
            }
         }
      }

      return targets;
   }

   interface DropTarget {
      Bounds getArea();

      void accept(ItemStack var1);
   }

   private static class SlotTarget implements EmiGhostIngredientHandler.DropTarget {
      private Slot slot;
      private Bounds area;
      private AbstractFilteredScreen gui;

      public SlotTarget(AbstractFilteredScreen gui, Slot slot) {
         this.slot = slot;
         this.gui = gui;
         this.area = new Bounds(gui.getGuiLeft() + slot.x, gui.getGuiTop() + slot.y, 16, 16);
      }

      @Override
      public Bounds getArea() {
         return this.area;
      }

      @Override
      public void accept(ItemStack ingredient) {
         ((AbstractFilteredMenu)this.gui.getMenu()).setPhantom(this.slot, ingredient);
      }
   }
}
