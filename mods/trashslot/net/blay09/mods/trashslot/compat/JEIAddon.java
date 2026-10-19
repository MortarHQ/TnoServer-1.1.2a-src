package net.blay09.mods.trashslot.compat;

import java.util.Collection;
import java.util.Collections;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGlobalGuiHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.blay09.mods.trashslot.client.TrashSlotGuiHandler;
import net.blay09.mods.trashslot.client.gui.TrashSlotComponent;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIAddon implements IModPlugin {
   public ResourceLocation getPluginUid() {
      return ResourceLocation.fromNamespaceAndPath("trashslot", "trashslot");
   }

   public void registerGuiHandlers(IGuiHandlerRegistration registration) {
      registration.addGlobalGuiHandler(new IGlobalGuiHandler() {
         public Collection<Rect2i> getGuiExtraAreas() {
            TrashSlotComponent slot = TrashSlotGuiHandler.getTrashSlotComponent();
            return slot != null && slot.isVisible() ? Collections.singletonList(slot.getRectangle()) : Collections.emptyList();
         }
      });
   }
}
