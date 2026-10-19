package com.tom.storagemod.screen;

import com.tom.storagemod.menu.StorageTerminalMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class StorageTerminalScreen extends AbstractStorageTerminalScreen<StorageTerminalMenu> {
   private static final ResourceLocation gui = ResourceLocation.tryBuild("toms_storage", "textures/gui/storage_terminal.png");

   public StorageTerminalScreen(StorageTerminalMenu screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn, 5, 202, 7, 17);
   }

   @Override
   protected void init() {
      this.imageWidth = 194;
      this.imageHeight = 202;
      super.init();
      this.onPacket();
   }

   @Override
   public ResourceLocation getGui() {
      return gui;
   }
}
