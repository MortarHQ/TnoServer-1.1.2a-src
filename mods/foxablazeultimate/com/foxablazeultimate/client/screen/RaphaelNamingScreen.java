package com.foxablazeultimate.client.screen;

import com.foxablazeultimate.menu.RaphaelNamingMenu;
import com.foxablazeultimate.network.RequestRaphaelRenamePayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

public class RaphaelNamingScreen extends AbstractContainerScreen<RaphaelNamingMenu> {
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/gui/raphael/naming.png");
   private static final ResourceLocation CIEL_ID = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "ciel");
   private final String defaultName;
   private EditBox editBox;

   public RaphaelNamingScreen(RaphaelNamingMenu menu, Inventory inventory, String defaultNameLangKey, ResourceLocation namingSkill) {
      super(
         menu,
         inventory,
         Component.translatable(CIEL_ID.equals(namingSkill) ? "foxablazeultimate.skill.ciel.naming.title" : "foxablazeultimate.skill.raphael.naming.title")
      );
      this.imageWidth = 144;
      this.imageHeight = 96;
      this.defaultName = Component.translatable(defaultNameLangKey).getString();
   }

   protected void init() {
      super.init();
      this.editBox = new EditBox(this.font, this.leftPos + 19, this.topPos + 27, 85, 11, Component.empty());
      this.editBox.setBordered(false);
      this.editBox.setMaxLength(64);
      this.editBox.setValue(this.defaultName);
      this.addRenderableWidget(this.editBox);
      this.setInitialFocus(this.editBox);
   }

   protected void renderBg(GuiGraphics graphics, float particleTick, int pX, int pY) {
      graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      if (RenderHelper.mouseOver(pX, pY, this.leftPos + 36, this.leftPos + 107, this.topPos + 73, this.topPos + 89)) {
         graphics.blit(BACKGROUND, this.leftPos + 37, this.topPos + 74, 1.0F, 176.0F, 70, 15, 256, 256);
      }

      if (RenderHelper.mouseOver(pX, pY, this.leftPos + 113, this.leftPos + 128, this.topPos + 22, this.topPos + 39)) {
         graphics.blit(BACKGROUND, this.leftPos + 114, this.topPos + 23, 79.0F, 97.0F, 14, 16, 256, 256);
      }

      RenderHelper.drawCenteredText(
         graphics, this.font, Component.translatable("tensura.naming.name"), this.leftPos + 72, this.topPos + 78, Color.WHITE.getRGB(), false
      );
   }

   protected void renderTooltip(GuiGraphics graphics, int pX, int pY) {
      if (RenderHelper.mouseOver(pX, pY, this.leftPos + 114, this.leftPos + 128, this.topPos + 23, this.topPos + 39)) {
         graphics.renderTooltip(this.font, Component.translatable("foxablazeultimate.skill.raphael.naming.reset"), pX, pY);
      }

      super.renderTooltip(graphics, pX, pY);
   }

   protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
   }

   public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
      if (RenderHelper.mouseOver(pMouseX, pMouseY, this.leftPos + 114, this.leftPos + 128, this.topPos + 23, this.topPos + 39)) {
         playClick();
         this.editBox.setValue(this.defaultName);
         this.editBox.setCursorPosition(0);
         this.editBox.setHighlightPos(0);
         this.editBox.setFocused(false);
         return true;
      }

      if (!RenderHelper.mouseOver(pMouseX, pMouseY, this.leftPos + 37, this.leftPos + 107, this.topPos + 74, this.topPos + 89)) {
         return super.mouseClicked(pMouseX, pMouseY, pButton);
      }

      playClick();
      String value = this.editBox.getValue();
      if (value == null || value.isBlank()) {
         value = "";
      } else if (value.equals(this.defaultName)) {
         value = "";
      }

      NetworkManager.sendToServer(new RequestRaphaelRenamePayload(value));
      this.onClose();
      return true;
   }

   public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
      if (this.editBox.keyPressed(pKeyCode, pScanCode, pModifiers)) {
         return true;
      } else if (this.editBox.isFocused() && this.editBox.isVisible() && pKeyCode != 256) {
         return true;
      } else {
         return this.minecraft != null && this.minecraft.options.keyInventory.matches(pKeyCode, pScanCode)
            ? true
            : super.keyPressed(pKeyCode, pScanCode, pModifiers);
      }
   }

   private static void playClick() {
      Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
   }
}
