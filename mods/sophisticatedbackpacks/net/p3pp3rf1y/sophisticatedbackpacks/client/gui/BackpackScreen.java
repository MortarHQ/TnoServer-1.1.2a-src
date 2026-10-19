package net.p3pp3rf1y.sophisticatedbackpacks.client.gui;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.client.KeybindHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContainer;
import net.p3pp3rf1y.sophisticatedbackpacks.network.BackpackOpenPayload;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;

public class BackpackScreen extends StorageScreenBase<BackpackContainer> implements IBackpackScreen {
   public static BackpackScreen constructScreen(BackpackContainer screenContainer, Inventory inv, Component title) {
      return new BackpackScreen(screenContainer, inv, title);
   }

   public BackpackScreen(BackpackContainer screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.getFocused() != null) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }

      if (keyCode == 256 || KeybindHandler.BACKPACK_OPEN_KEYBIND.isActiveAndMatches(InputConstants.getKey(keyCode, scanCode))) {
         if (((BackpackContainer)this.getMenu()).isFirstLevelStorage() && (keyCode == 256 || this.mouseNotOverBackpack())) {
            if (((BackpackContainer)this.getMenu()).getBackpackContext().wasOpenFromInventory()) {
               this.getMinecraft().player.closeContainer();
               this.getMinecraft().setScreen(new InventoryScreen(this.getMinecraft().player));
            } else {
               this.onClose();
            }

            return true;
         }

         if (!((BackpackContainer)this.getMenu()).isFirstLevelStorage()) {
            PacketDistributor.sendToServer(new BackpackOpenPayload(), new CustomPacketPayload[0]);
            return true;
         }
      }

      return super.keyPressed(keyCode, scanCode, modifiers);
   }

   private boolean mouseNotOverBackpack() {
      Slot selectedSlot = this.getSlotUnderMouse();
      return selectedSlot == null || !(selectedSlot.getItem().getItem() instanceof BackpackItem);
   }

   protected String getStorageSettingsTabTooltip() {
      return SBPTranslationHelper.INSTANCE.translGui("settings.tooltip");
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      if (((BackpackContainer)this.getMenu()).getNumberOfStorageInventorySlots() == 0 && Minecraft.getInstance().player != null) {
         Minecraft.getInstance().player.closeContainer();
      }
   }
}
