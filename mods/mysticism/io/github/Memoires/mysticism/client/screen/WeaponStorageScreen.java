package io.github.Memoires.mysticism.client.screen;

import io.github.Memoires.mysticism.menu.WeaponStorageMenu;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class WeaponStorageScreen extends AbstractContainerScreen<WeaponStorageMenu> {
   private static final ResourceLocation BACKGROUND_SMALL = ResourceLocation.fromNamespaceAndPath("mysticism", "textures/gui/gatekeeper_small.png");
   private static final ResourceLocation BACKGROUND_BIG = ResourceLocation.fromNamespaceAndPath("mysticism", "textures/gui/gatekeeper_big.png");
   private final ResourceLocation background;
   private final Player player;
   private final int maxSize;
   private final int page;
   private final boolean mastered;
   private final int rows;
   private static final int GRID_TOP = 30;
   private static final int BUTTON_ROW_GAP = 8;

   public WeaponStorageScreen(WeaponStorageMenu pMenu, Inventory pPlayerInventory, int maxSize, int page) {
      super(pMenu, pPlayerInventory, pMenu.getSkill().getName());
      this.player = pPlayerInventory.player;
      this.maxSize = maxSize;
      this.page = page;
      this.mastered = pMenu.isMastered();
      this.rows = pMenu.getRows();
      this.background = this.mastered ? BACKGROUND_BIG : BACKGROUND_SMALL;
      this.imageWidth = 201;
      this.imageHeight = this.mastered ? 211 + (this.rows - 2) * 18 : 211;
   }

   protected void init() {
      super.init();
   }

   public void render(GuiGraphics graphics, int x, int y, float f) {
      super.render(graphics, x, y, f);
      this.renderTooltip(graphics, x, y);
   }

   protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
      RenderHelper.drawCenteredText(guiGraphics, this.font, this.title, this.imageWidth / 2 + 15, this.titleLabelY, Color.WHITE.getRGB(), false);
   }

   private int buttonY(int height) {
      return height + 30 + this.rows * 18 + 8;
   }

   protected void renderBg(GuiGraphics graphics, float f, int x, int y) {
      int width = (this.width - this.imageWidth) / 2;
      int height = (this.height - this.imageHeight) / 2;
      graphics.blit(this.background, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      int btnY = this.buttonY(height);
      if (this.page > 0) {
         boolean hovering = x >= width + 45 && x < width + 57 && y >= btnY && y < btnY + 12;
         if (hovering) {
            graphics.blit(this.background, width + 45, btnY, 0, 213, 13, 13);
         }
      }

      int perPage = this.rows * 5;
      if (this.page < (this.maxSize - 1) / perPage) {
         boolean hovering = x >= width + 144 && x < width + 156 && y >= btnY && y < btnY + 12;
         if (hovering) {
            graphics.blit(this.background, width + 144, btnY, 14, 213, 13, 13);
         }
      }
   }

   protected void renderTooltip(GuiGraphics graphics, int x, int y) {
      super.renderTooltip(graphics, x, y);
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (this.minecraft == null) {
         return false;
      }

      int width = (this.width - this.imageWidth) / 2;
      int height = (this.height - this.imageHeight) / 2;
      int btnY = this.buttonY(height);
      int perPage = this.rows * 5;
      if (x >= width + 45
         && x < width + 57
         && y >= btnY
         && y < btnY + 12
         && this.page > 0
         && ((WeaponStorageMenu)this.menu).clickMenuButton(this.minecraft.player, 0)) {
         if (this.minecraft.gameMode == null) {
            return false;
         }

         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         this.minecraft.gameMode.handleInventoryButtonClick(((WeaponStorageMenu)this.menu).containerId, 0);
         return true;
      } else {
         if (!(x >= width + 144)
            || !(x < width + 156)
            || !(y >= btnY)
            || !(y < btnY + 12)
            || this.page >= (this.maxSize - 1) / perPage
            || !((WeaponStorageMenu)this.menu).clickMenuButton(this.minecraft.player, 1)) {
            return super.mouseClicked(x, y, button);
         }

         if (this.minecraft.gameMode == null) {
            return false;
         }

         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         this.minecraft.gameMode.handleInventoryButtonClick(((WeaponStorageMenu)this.menu).containerId, 1);
         return true;
      }
   }

   public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
      if (this.minecraft != null) {
         if (this.minecraft.options.keyInventory.matches(pKeyCode, pScanCode)) {
            return true;
         }

         if (this.minecraft.options.keySwapOffhand.matches(pKeyCode, pScanCode)) {
            return true;
         }
      }

      return super.keyPressed(pKeyCode, pScanCode, pModifiers);
   }
}
