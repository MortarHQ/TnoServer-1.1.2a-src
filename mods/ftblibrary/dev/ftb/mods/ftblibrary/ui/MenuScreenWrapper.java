package dev.ftb.mods.ftblibrary.ui;

import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.KeyModifiers;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MenuScreenWrapper<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> implements IScreenWrapper {
   private final BaseScreen wrappedGui;
   private final TooltipList tooltipList = new TooltipList();
   private boolean drawSlots = true;

   public MenuScreenWrapper(BaseScreen g, T menu, Inventory playerInventory, Component title) {
      super(menu, playerInventory, title);
      this.wrappedGui = g;
   }

   public MenuScreenWrapper<T> disableSlotDrawing() {
      this.drawSlots = false;
      return this;
   }

   public void init() {
      super.init();
      this.wrappedGui.initGui();
      this.leftPos = this.wrappedGui.getX();
      this.topPos = this.wrappedGui.getY();
      this.imageWidth = this.wrappedGui.width;
      this.imageHeight = this.wrappedGui.height;
   }

   public boolean isPauseScreen() {
      return this.wrappedGui.doesGuiPauseGame();
   }

   public boolean mouseClicked(double x, double y, int button) {
      this.wrappedGui.updateMouseOver((int)x, (int)y);
      if (button == MouseButton.BACK.id) {
         this.wrappedGui.onBack();
         return true;
      } else {
         return this.wrappedGui.mousePressed(MouseButton.get(button)) || super.mouseClicked(x, y, button);
      }
   }

   public boolean mouseReleased(double x, double y, int button) {
      this.wrappedGui.updateMouseOver((int)x, (int)y);
      this.wrappedGui.mouseReleased(MouseButton.get(button));
      return super.mouseReleased(x, y, button);
   }

   public boolean mouseScrolled(double x, double y, double dirX, double dirY) {
      return this.wrappedGui.mouseScrolled(dirY) || super.mouseScrolled(x, y, dirX, dirY);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      Key key = new Key(keyCode, scanCode, modifiers);
      if (this.wrappedGui.keyPressed(key)) {
         return true;
      }

      if (key.backspace()) {
         this.wrappedGui.onBack();
         return true;
      }

      if (this.wrappedGui.onClosedByKey(key)) {
         if (this.shouldCloseOnEsc()) {
            this.wrappedGui.closeGui(false);
         }

         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      Key key = new Key(keyCode, scanCode, modifiers);
      this.wrappedGui.keyReleased(key);
      return super.keyReleased(keyCode, scanCode, modifiers);
   }

   public boolean charTyped(char keyChar, int modifiers) {
      return this.wrappedGui.charTyped(keyChar, new KeyModifiers(modifiers)) ? true : super.charTyped(keyChar, keyChar);
   }

   protected void renderBg(GuiGraphics graphics, float f, int mx, int my) {
      Theme theme = this.wrappedGui.getTheme();
      GuiHelper.setupDrawing();
      this.renderBackground(graphics, mx, my, f);
      GuiHelper.setupDrawing();
      this.wrappedGui.draw(graphics, theme, this.leftPos, this.topPos, this.imageWidth, this.imageHeight);
      if (this.drawSlots) {
         GuiHelper.setupDrawing();

         for (Slot slot : this.menu.slots) {
            theme.drawContainerSlot(graphics, this.leftPos + slot.x, this.topPos + slot.y, 16, 16);
         }
      }
   }

   protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
      graphics.pose().pushPose();
      graphics.pose().translate(-this.leftPos, -this.topPos, 0.0F);
      GuiHelper.setupDrawing();
      Theme theme = this.wrappedGui.getTheme();
      this.wrappedGui.drawForeground(graphics, theme, this.leftPos, this.topPos, this.imageWidth, this.imageHeight);
      this.wrappedGui.addMouseOverText(this.tooltipList);
      int zLevel = this.wrappedGui.getMaxZLevel() + 100;
      graphics.pose().pushPose();
      if (!this.tooltipList.shouldRender()) {
         this.wrappedGui.getIngredientUnderMouse().ifPresent(underMouse -> {
            if (underMouse.tooltip()) {
               Object ingredient = underMouse.ingredient();
               if (ingredient instanceof ItemStack stack && !stack.isEmpty()) {
                  graphics.pose().translate(0.0F, 0.0F, zLevel);
                  graphics.renderTooltip(theme.getFont(), (ItemStack)ingredient, mouseX, mouseY);
               }
            }
         });
      } else {
         List<FormattedCharSequence> lines = Tooltip.splitTooltip(
            this.minecraft, this.tooltipList.getLines().stream().reduce((c1, c2) -> c1.copy().append("\n").append(c2)).orElse(Component.empty())
         );
         graphics.pose().translate(0.0F, 0.0F, zLevel);
         graphics.setColor(1.0F, 1.0F, 1.0F, 0.8F);
         graphics.renderTooltip(theme.getFont(), lines, DefaultTooltipPositioner.INSTANCE, mouseX, Math.max(mouseY, 18));
         graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      }

      graphics.pose().popPose();
      this.tooltipList.reset();
      graphics.pose().popPose();
   }

   public void renderBackground(GuiGraphics graphics, int x, int y, float partialTicks) {
      if (this.wrappedGui.drawDefaultBackground(graphics)) {
         super.renderBackground(graphics, x, y, partialTicks);
      }
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(graphics, mouseX, mouseY, partialTicks);
      this.wrappedGui.updateGui(mouseX, mouseY, partialTicks);
      super.render(graphics, mouseX, mouseY, partialTicks);
   }

   public void containerTick() {
      super.containerTick();
      this.wrappedGui.tick();
   }

   @Override
   public BaseScreen getGui() {
      return this.wrappedGui;
   }

   public void removed() {
      this.wrappedGui.onClosed();
      super.removed();
   }
}
