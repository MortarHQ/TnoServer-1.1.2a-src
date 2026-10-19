package net.blay09.mods.trashslot.api;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public interface InternalMethods {
   ISimpleGuiContainerLayout registerSimpleLayout(Class<? extends AbstractContainerScreen<?>> var1);

   void registerLayout(Class<? extends AbstractContainerScreen<?>> var1, IGuiContainerLayout var2);
}
