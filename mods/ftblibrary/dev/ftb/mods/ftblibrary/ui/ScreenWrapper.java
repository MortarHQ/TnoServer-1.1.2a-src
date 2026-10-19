package dev.ftb.mods.ftblibrary.ui;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.KeyModifiers;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.Optional;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;

public class ScreenWrapper extends Screen implements IScreenWrapper {
   private final BaseScreen wrappedGui;
   private final TooltipList tooltipList = new TooltipList();

   public ScreenWrapper(BaseScreen g) {
      super(g.getTitle());
      this.wrappedGui = g;
   }

   public void init() {
      super.init();
      this.wrappedGui.initGui();
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

   public boolean mouseDragged(double x, double y, int button, double dragX, double dragY) {
      return this.wrappedGui.mouseDragged(button, dragX, dragY) || super.mouseDragged(x, y, button, dragX, dragY);
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
            this.wrappedGui.closeGui(true);
         }

         return true;
      } else {
         if (Platform.isModLoaded("jei")) {
            this.wrappedGui.getIngredientUnderMouse().ifPresent(underMouse -> this.handleIngredientKey(key, underMouse.ingredient()));
         }

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

   private void handleIngredientKey(Key key, Object object) {
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.wrappedGui.updateGui(mouseX, mouseY, partialTicks);
      this.renderBackground(graphics, mouseX, mouseY, partialTicks);
      GuiHelper.setupDrawing();
      int x = this.wrappedGui.getX();
      int y = this.wrappedGui.getY();
      int w = this.wrappedGui.width;
      int h = this.wrappedGui.height;
      Theme theme = this.wrappedGui.getTheme();
      this.wrappedGui.draw(graphics, theme, x, y, w, h);
      this.wrappedGui.drawForeground(graphics, theme, x, y, w, h);
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
         graphics.pose().translate(0.0F, 0.0F, zLevel);
         graphics.setColor(1.0F, 1.0F, 1.0F, 0.8F);
         graphics.renderTooltip(theme.getFont(), this.tooltipList.getLines(), Optional.empty(), mouseX, Math.max(mouseY, 18));
         graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      }

      graphics.pose().popPose();
      this.tooltipList.reset();
   }

   public void renderBackground(GuiGraphics matrixStack, int x, int y, float partialTicks) {
      if (this.wrappedGui.drawDefaultBackground(matrixStack)) {
         super.renderBackground(matrixStack, x, y, partialTicks);
      }
   }

   protected void renderBlurredBackground(float f) {
      if (this.wrappedGui.shouldRenderBlur()) {
         super.renderBlurredBackground(f);
      }
   }

   public void tick() {
      super.tick();
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

   public boolean shouldCloseOnEsc() {
      return this.getGui().shouldCloseOnEsc();
   }
}
