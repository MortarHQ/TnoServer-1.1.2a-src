package com.nododiiiii.ponderer.compat.jei;

import com.nododiiiii.ponderer.ui.InterfaceSlotEditState;
import com.nododiiiii.ponderer.ui.InterfaceSlotOverlayRenderer;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler.Target;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.inventory.Slot;

public class InterfaceSlotGhostHandler<T extends AbstractContainerScreen<?>> implements IGhostIngredientHandler<T> {
   public <I> List<Target<I>> getTargetsTyped(T gui, ITypedIngredient<I> ingredient, boolean doStart) {
      if (!InterfaceSlotEditState.isActive()) {
         return List.of();
      }

      JeiCompat.IngredientDescriptor descriptor = JeiCompat.resolveIngredientDescriptor(ingredient);
      return descriptor != null && descriptor.id() != null && !descriptor.id().isBlank() ? createTargets(gui, descriptor) : List.of();
   }

   static <I> List<Target<I>> createTargets(AbstractContainerScreen<?> gui, final JeiCompat.IngredientDescriptor descriptor) {
      InterfaceSlotOverlayRenderer.ContainerBounds bounds = InterfaceSlotOverlayRenderer.readContainerBounds(gui);
      List<Target<I>> targets = new ArrayList<>();
      List<Slot> slots = gui.getMenu().slots;

      for (int slotIndex = 0; slotIndex < slots.size(); slotIndex++) {
         final Slot slot = slots.get(slotIndex);
         final Rect2i area = new Rect2i(bounds.left() + slot.x, bounds.top() + slot.y, 16, 16);
         final int finalSlotIndex = slotIndex;
         targets.add(new Target<I>() {
            public Rect2i getArea() {
               return area;
            }

            public void accept(I ignored) {
               InterfaceSlotEditState.putBinding(finalSlotIndex, slot.x, slot.y, descriptor.id(), descriptor.kind());
            }
         });
      }

      return targets;
   }

   public void onComplete() {
   }
}
