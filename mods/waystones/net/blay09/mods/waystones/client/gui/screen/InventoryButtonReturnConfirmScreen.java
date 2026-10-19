package net.blay09.mods.waystones.client.gui.screen;

import java.util.Optional;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.network.message.InventoryButtonMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class InventoryButtonReturnConfirmScreen extends ConfirmScreen {
   private final Component waystoneName;

   public InventoryButtonReturnConfirmScreen() {
      this("");
   }

   public InventoryButtonReturnConfirmScreen(String targetWaystone) {
      super(result -> {
         if (result) {
            Balm.getNetworking().sendToServer(new InventoryButtonMessage());
         }

         Minecraft.getInstance().setScreen(null);
      }, Component.translatable("gui.waystones.inventory.confirm_return"), Component.empty());
      this.waystoneName = getWaystoneName(targetWaystone);
   }

   private static Component getWaystoneName(String targetWaystone) {
      if (!targetWaystone.isEmpty()) {
         return Component.translatable("gui.waystones.inventory.confirm_return_bound_to", new Object[]{targetWaystone}).withStyle(ChatFormatting.GRAY);
      }

      LocalPlayer player = Minecraft.getInstance().player;
      Optional<Waystone> nearestWaystone = PlayerWaystoneManager.getNearestWaystone(player);
      return (Component)nearestWaystone.map(Waystone::getName)
         .map(it -> Component.translatable("gui.waystones.inventory.confirm_return_bound_to", new Object[]{it}).withStyle(ChatFormatting.GRAY))
         .orElse(Component.translatable("gui.waystones.inventory.no_waystones_activated").withStyle(ChatFormatting.GRAY));
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      Font font = Minecraft.getInstance().font;
      guiGraphics.drawCenteredString(font, this.waystoneName, this.width / 2, 100, 16777215);
   }
}
