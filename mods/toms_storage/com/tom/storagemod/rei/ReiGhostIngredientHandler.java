package com.tom.storagemod.rei;

import com.tom.storagemod.item.IItemFilter;
import com.tom.storagemod.menu.AbstractFilteredMenu;
import com.tom.storagemod.menu.slot.FilterSlot;
import com.tom.storagemod.menu.slot.PhantomSlot;
import com.tom.storagemod.screen.AbstractFilteredScreen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.drag.DraggableStack;
import me.shedaniel.rei.api.client.gui.drag.DraggableStackVisitor;
import me.shedaniel.rei.api.client.gui.drag.DraggedAcceptorResult;
import me.shedaniel.rei.api.client.gui.drag.DraggingContext;
import me.shedaniel.rei.api.client.gui.drag.DraggableStackVisitor.BoundsProvider;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ReiGhostIngredientHandler implements DraggableStackVisitor<AbstractFilteredScreen> {
   public <R extends Screen> boolean isHandingScreen(R screen) {
      return screen instanceof AbstractFilteredScreen;
   }

   public Stream<BoundsProvider> getDraggableAcceptingBounds(DraggingContext<AbstractFilteredScreen> context, DraggableStack stack) {
      List<ReiGhostIngredientHandler.DropTarget> targets = this.getTargets((AbstractFilteredScreen)context.getScreen(), stack.get());
      return targets.stream().map(target -> BoundsProvider.ofRectangle(target.getArea()));
   }

   public DraggedAcceptorResult acceptDraggedStack(DraggingContext<AbstractFilteredScreen> context, DraggableStack stack) {
      List<ReiGhostIngredientHandler.DropTarget> targets = this.getTargets((AbstractFilteredScreen)context.getScreen(), stack.get());
      Point pos = context.getCurrentPosition();

      for (ReiGhostIngredientHandler.DropTarget target : targets) {
         if (target.getArea().contains(pos)) {
            target.accept(stack);
            return DraggedAcceptorResult.ACCEPTED;
         }
      }

      return DraggedAcceptorResult.PASS;
   }

   private <I> List<ReiGhostIngredientHandler.DropTarget> getTargets(AbstractFilteredScreen gui, EntryStack<?> ingredient) {
      if (ingredient.getType() != VanillaEntryTypes.ITEM) {
         return Collections.emptyList();
      }

      ItemStack stack = (ItemStack)ingredient.castValue();
      List<ReiGhostIngredientHandler.DropTarget> targets = new ArrayList<>();
      boolean filter = stack.getItem() instanceof IItemFilter;

      for (Slot slot : gui.getMenu().slots) {
         if (slot instanceof PhantomSlot) {
            targets.add(new ReiGhostIngredientHandler.SlotTarget(gui, slot));
         } else if (!filter && slot instanceof FilterSlot) {
            ItemStack s = slot.getItem();
            boolean sf = !s.isEmpty() && s.getItem() instanceof IItemFilter;
            if (!sf) {
               targets.add(new ReiGhostIngredientHandler.SlotTarget(gui, slot));
            }
         }
      }

      return targets;
   }

   interface DropTarget {
      Rectangle getArea();

      void accept(DraggableStack var1);
   }

   private static class SlotTarget implements ReiGhostIngredientHandler.DropTarget {
      private Slot slot;
      private Rectangle area;
      private AbstractFilteredScreen gui;

      public SlotTarget(AbstractFilteredScreen gui, Slot slot) {
         this.slot = slot;
         this.gui = gui;
         this.area = new Rectangle(gui.getGuiLeft() + slot.x, gui.getGuiTop() + slot.y, 16, 16);
      }

      @Override
      public Rectangle getArea() {
         return this.area;
      }

      @Override
      public void accept(DraggableStack ingredient) {
         ((AbstractFilteredMenu)this.gui.getMenu()).setPhantom(this.slot, (ItemStack)ingredient.get().castValue());
      }
   }
}
