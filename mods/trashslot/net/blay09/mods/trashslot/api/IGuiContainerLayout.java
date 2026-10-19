package net.blay09.mods.trashslot.api;

import java.util.List;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;

public interface IGuiContainerLayout {
   List<Rect2i> getCollisionAreas(AbstractContainerScreen<?> var1);

   List<Snap> getSnaps(AbstractContainerScreen<?> var1, SlotRenderStyle var2);

   SlotRenderStyle getSlotRenderStyle(AbstractContainerScreen<?> var1, int var2, int var3);

   int getDefaultSlotX(AbstractContainerScreen<?> var1);

   int getDefaultSlotY(AbstractContainerScreen<?> var1);

   boolean isEnabledByDefault();

   int getSlotOffsetX(AbstractContainerScreen<?> var1, SlotRenderStyle var2);

   int getSlotOffsetY(AbstractContainerScreen<?> var1, SlotRenderStyle var2);

   default String getContainerId(AbstractContainerScreen<?> screen) {
      return screen.getClass().getName().replace('.', '/');
   }
}
