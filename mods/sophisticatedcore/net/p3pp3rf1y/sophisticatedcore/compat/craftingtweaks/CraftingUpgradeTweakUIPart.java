package net.p3pp3rf1y.sophisticatedcore.compat.craftingtweaks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.blay09.mods.craftingtweaks.CraftingTweaksProviderManager;
import net.blay09.mods.craftingtweaks.api.CraftingTweaksClientAPI;
import net.blay09.mods.craftingtweaks.api.TweakType;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.inventory.Slot;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.crafting.ICraftingUIPart;

public class CraftingUpgradeTweakUIPart implements ICraftingUIPart {
   private StorageScreenBase<?> storageScreen;
   private static final Method ADD_RENDERABLE_WIDGET = ObfuscationReflectionHelper.findMethod(
      Screen.class, "addRenderableWidget", new Class[]{GuiEventListener.class}
   );
   private final List<AbstractWidget> buttons = new ArrayList<>();

   public static void register() {
      StorageScreenBase.setCraftingUIPart(new CraftingUpgradeTweakUIPart());
   }

   private void addButton(AbstractWidget button) {
      this.buttons.add(button);

      try {
         ADD_RENDERABLE_WIDGET.invoke(this.storageScreen, button);
      } catch (IllegalAccessException | InvocationTargetException e) {
         SophisticatedCore.LOGGER.error("Error calling addButton in Screen class", e);
      }
   }

   @Override
   public void onCraftingSlotsHidden() {
      if (!this.buttons.isEmpty() && this.storageScreen != null) {
         this.buttons.forEach(this.storageScreen.children()::remove);
         this.buttons.forEach(this.storageScreen.renderables::remove);
         this.buttons.clear();
      }
   }

   @Override
   public int getWidth() {
      return 18;
   }

   @Override
   public void setStorageScreen(StorageScreenBase<?> screen) {
      this.storageScreen = screen;
   }

   @Override
   public void onCraftingSlotsDisplayed(List<Slot> slots) {
      if (!slots.isEmpty() && this.storageScreen != null) {
         Slot firstSlot = slots.getFirst();
         CraftingTweaksProviderManager.getDefaultCraftingGrid(this.storageScreen.getMenu())
            .ifPresent(
               craftingGrid -> {
                  this.addButton(
                     CraftingTweaksClientAPI.createTweakButtonRelative(
                        craftingGrid, this.storageScreen, this.getButtonX(firstSlot), this.getButtonY(firstSlot, 0), TweakType.Rotate
                     )
                  );
                  this.addButton(
                     CraftingTweaksClientAPI.createTweakButtonRelative(
                        craftingGrid, this.storageScreen, this.getButtonX(firstSlot), this.getButtonY(firstSlot, 1), TweakType.Balance
                     )
                  );
                  this.addButton(
                     CraftingTweaksClientAPI.createTweakButtonRelative(
                        craftingGrid, this.storageScreen, this.getButtonX(firstSlot), this.getButtonY(firstSlot, 2), TweakType.Clear
                     )
                  );
               }
            );
      }
   }

   private int getButtonX(Slot firstSlot) {
      return firstSlot.x - 19;
   }

   private int getButtonY(Slot firstSlot, int index) {
      return firstSlot.y + 18 * index;
   }
}
