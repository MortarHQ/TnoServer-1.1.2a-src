package com.nododiiiii.ponderer.compat.jei;

import com.nododiiiii.ponderer.ui.InterfaceSlotEditState;
import com.nododiiiii.ponderer.ui.UiAnchorViewport;
import java.util.List;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler.Target;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.createmod.ponder.foundation.ui.PonderUI;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class PonderUiGhostHandler implements IGhostIngredientHandler<PonderUI> {
   public <I> List<Target<I>> getTargetsTyped(PonderUI gui, ITypedIngredient<I> ingredient, boolean doStart) {
      if (!InterfaceSlotEditState.isActive()) {
         return List.of();
      } else if (UiAnchorViewport.getEmbeddedMirrorScreen() instanceof AbstractContainerScreen<?> container) {
         JeiCompat.IngredientDescriptor descriptor = JeiCompat.resolveIngredientDescriptor(ingredient);
         return descriptor != null && descriptor.id() != null && !descriptor.id().isBlank()
            ? InterfaceSlotGhostHandler.createTargets(container, descriptor)
            : List.of();
      } else {
         return List.of();
      }
   }

   public void onComplete() {
   }
}
