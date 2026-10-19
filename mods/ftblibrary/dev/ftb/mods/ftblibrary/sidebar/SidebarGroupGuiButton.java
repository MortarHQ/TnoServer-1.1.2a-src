package dev.ftb.mods.ftblibrary.sidebar;

import dev.ftb.mods.ftblibrary.api.sidebar.ButtonOverlayRender;
import dev.ftb.mods.ftblibrary.config.FTBLibraryClientConfig;
import dev.ftb.mods.ftblibrary.config.manager.ConfigManagerClient;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class SidebarGroupGuiButton extends AbstractButton {
   private static final int BUTTON_SPACING = 17;
   private static final List<Component> noButtonComponents = List.of(
      Component.translatable("sidebar_button.ftblibrary.config"), Component.translatable("sidebar_button.ftblibrary.config.enter_edit_mode")
   );
   public static Rect2i lastDrawnArea = new Rect2i(0, 0, 0, 0);
   private final Map<SidebarGuiButton, GridLocation> realLocationMap = new HashMap<>();
   boolean gridStartBottom = false;
   boolean gridStartRight = false;
   int yRenderStart;
   int xRenderStart;
   private SidebarGuiButton mouseOver;
   private SidebarGuiButton selectedButton;
   private GridLocation selectedLocation;
   private int lastMouseClickButton = 0;
   private boolean isEditMode;
   private int currentMouseX;
   private int currentMouseY;
   private int mouseOffsetX;
   private int mouseOffsetY;
   private int currentGirdWidth = 1;
   private int currentGridHeight = 1;
   private boolean addBoxOpen;
   private boolean isMouseOverAdd;
   private boolean mouseOverSettingsIcon;

   public SidebarGroupGuiButton() {
      super(0, 0, 0, 0, Component.empty());
      this.ensureGridAlignment();
   }

   private static void drawGrid(
      GuiGraphics graphics, int x, int y, int width, int height, int spacingWidth, int spacingHeight, Color4I backgroundColor, Color4I gridColor
   ) {
      backgroundColor.draw(graphics, x, y, width * spacingWidth, height * spacingHeight);

      for (int i = 0; i < width + 1; i++) {
         gridColor.draw(graphics, x + i * spacingWidth, y, 1, height * spacingHeight + 1);
      }

      for (int i = 0; i < height + 1; i++) {
         gridColor.draw(graphics, x, y + i * spacingHeight, width * spacingWidth, 1);
      }
   }

   public static void drawGrid(GuiGraphics graphics, int x, int y, int width, int height, int spacing, Color4I backgroundColor, Color4I gridColor) {
      drawGrid(graphics, x, y, width, height, spacing, spacing, backgroundColor, gridColor);
   }

   private static void drawHoveredGrid(
      GuiGraphics graphics,
      int x,
      int y,
      int width,
      int height,
      int spacing,
      Color4I backgroundColor,
      Color4I gridColor,
      int mx,
      int my,
      boolean gridStartBottom,
      boolean gridStartRight
   ) {
      drawGrid(graphics, x, y, width, height, spacing, backgroundColor, gridColor);
      int adjustedMx = gridStartRight ? x + width * spacing - (mx - x) : mx;
      int adjustedMy = gridStartBottom ? y + height * spacing - (my - y) : my;
      if (adjustedMx >= x && adjustedMy >= y && adjustedMx < x + width * spacing && adjustedMy < y + height * spacing) {
         int gridX = (adjustedMx - x) / spacing;
         int gridY = (adjustedMy - y) / spacing;
         if (gridStartRight) {
            gridX = width - gridX - 1;
         }

         if (gridStartBottom) {
            gridY = height - gridY - 1;
         }

         Color4I.WHITE.withAlpha(127).draw(graphics, x + gridX * spacing + 1, y + gridY * spacing + 1, spacing - 1, spacing - 1);
      }
   }

   public void renderWidget(GuiGraphics graphics, int mx, int my, float partialTicks) {
      graphics.pose().pushPose();
      graphics.pose().translate(0.0F, 0.0F, 5000.0F);
      this.currentMouseX = mx;
      this.currentMouseY = my;
      this.mouseOver = null;
      this.isMouseOverAdd = false;
      this.mouseOverSettingsIcon = false;
      GridLocation gridLocation = this.getGridLocation();

      for (Entry<SidebarGuiButton, GridLocation> entry : this.realLocationMap.entrySet()) {
         SidebarGuiButton button = entry.getKey();
         if (entry.getValue().equals(gridLocation)) {
            this.mouseOver = button;
         }
      }

      if (this.isEditMode) {
         this.renderEditMode(graphics, mx, my);
      }

      this.renderSidebarButtons(graphics, mx, my);
      graphics.pose().popPose();
   }

   private void renderSidebarButtons(GuiGraphics graphics, int mx, int my) {
      Font font = Minecraft.getInstance().font;
      graphics.pose().translate(0.0F, 0.0F, 50.0F);
      if (!this.isEditMode && SidebarButtonManager.INSTANCE.getEnabledButtonList(false).isEmpty()) {
         if (mx >= this.xRenderStart + 2 && my >= this.yRenderStart + 2 && mx < this.xRenderStart + 18 && my < this.yRenderStart + 18) {
            graphics.renderTooltip(font, noButtonComponents, Optional.empty(), mx, my + 5);
            Color4I.WHITE.withAlpha(33).draw(graphics, this.xRenderStart + 1, this.yRenderStart + 1, 16, 16);
            this.mouseOverSettingsIcon = true;
         }

         Icons.SETTINGS.draw(graphics, this.xRenderStart + 1, this.yRenderStart + 1, 16, 16);
      } else {
         for (SidebarGuiButton button : SidebarButtonManager.INSTANCE.getButtonList()) {
            graphics.pose().pushPose();
            this.renderSidebarButton(graphics, button, mx, my, font);
            graphics.pose().popPose();
         }
      }
   }

   private void renderSidebarButton(GuiGraphics graphics, SidebarGuiButton button, int mouseX, int mouseY, Font font) {
      GridLocation realGridLocation = this.realLocationMap.get(button);
      if (this.isEditMode || button.equals(this.selectedButton) || button.isEnabled()) {
         if (this.isEditMode && button == this.selectedButton) {
            graphics.pose().translate(0.0F, 0.0F, 1000.0F);
            button.x = mouseX - this.mouseOffsetX;
            button.y = mouseY - this.mouseOffsetY;
         } else {
            if (realGridLocation == null) {
               return;
            }

            int adjustedX = this.gridStartRight
               ? this.xRenderStart + (this.currentGirdWidth - realGridLocation.x() - 1) * 17
               : this.xRenderStart + realGridLocation.x() * 17;
            int adjustedY = this.gridStartBottom
               ? this.yRenderStart + (this.currentGridHeight - realGridLocation.y() - 1) * 17
               : this.yRenderStart + realGridLocation.y() * 17;
            button.x = adjustedX + 1;
            button.y = adjustedY + 1;
         }

         GuiHelper.setupDrawing();
         button.getSidebarButton().getData().icon().draw(graphics, button.x, button.y, 16, 16);
         if (this.isEditMode) {
            if (mouseX >= button.x + 12 && mouseY <= button.y + 4 && mouseX < button.x + 16 && mouseY >= button.y) {
               Icons.CANCEL.draw(graphics, button.x + 11, button.y - 1, 6, 6);
            } else {
               Icons.CANCEL.draw(graphics, button.x + 12, button.y, 4, 4);
            }
         } else {
            graphics.pose().pushPose();
            graphics.pose().translate(button.x, button.y, 0.0F);

            for (ButtonOverlayRender buttonOverlayRender : button.getSidebarButton().getExtraRenderers()) {
               buttonOverlayRender.render(graphics, font, 16);
            }

            graphics.pose().popPose();
         }

         if (button == this.mouseOver) {
            Color4I.WHITE.withAlpha(33).draw(graphics, button.x, button.y, 16, 16);
         }
      }

      if (!this.isEditMode && this.mouseOver == button) {
         graphics.renderTooltip(font, button.getSidebarButton().getTooltip(Screen.hasShiftDown()), Optional.empty(), mouseX, Math.max(7, mouseY - 9) + 10);
      }
   }

   private void renderEditMode(GuiGraphics graphics, int mx, int my) {
      drawHoveredGrid(
         graphics,
         this.xRenderStart,
         this.yRenderStart,
         this.currentGirdWidth,
         this.currentGridHeight,
         17,
         Color4I.GRAY.withAlpha(70),
         Color4I.BLACK.withAlpha(90),
         mx,
         my,
         this.gridStartBottom,
         this.gridStartRight
      );
      List<SidebarGuiButton> disabledButtonList = SidebarButtonManager.INSTANCE.getDisabledButtonList(this.isEditMode);
      if (!disabledButtonList.isEmpty()) {
         int addIconY = this.gridStartBottom ? this.yRenderStart + (this.currentGridHeight - 1) * 17 : 0;
         int addIconX = this.gridStartRight ? this.xRenderStart - 34 : (this.currentGirdWidth + 1) * 17;
         drawGrid(graphics, addIconX, addIconY, 1, 1, 17, 17, Color4I.GRAY, Color4I.BLACK);
         Icons.ADD.draw(graphics, addIconX + 1, addIconY + 1, 16, 16);
         if (mx >= addIconX && my >= addIconY && mx < addIconX + 16 && my < addIconY + 16) {
            this.isMouseOverAdd = true;
            Color4I.WHITE.withAlpha(137).draw(graphics, addIconX + 1, addIconY + 1, 16, 16);
         }

         if (this.addBoxOpen) {
            int maxWidth = 0;

            for (SidebarGuiButton button : disabledButtonList) {
               String s = I18n.get(button.getSidebarButton().getLangKey(), new Object[0]);
               int width = Minecraft.getInstance().font.width(s);
               maxWidth = Math.max(maxWidth, width);
            }

            int gridY = this.gridStartBottom ? addIconY - disabledButtonList.size() * 17 : 17;
            int gridX = this.gridStartRight ? addIconX - maxWidth - 6 : (this.currentGirdWidth + 1) * 17;
            graphics.pose().pushPose();
            graphics.pose().translate(0.0F, 0.0F, 1000.0F);
            if (this.gridStartRight) {
               drawHoveredGrid(
                  graphics, addIconX, gridY, 1, disabledButtonList.size(), 17, Color4I.GRAY, Color4I.BLACK, mx, my, this.gridStartBottom, this.gridStartRight
               );
               drawGrid(graphics, addIconX - maxWidth - 6, gridY, 1, disabledButtonList.size(), maxWidth + 6, 17, Color4I.GRAY, Color4I.BLACK);
            } else {
               drawHoveredGrid(
                  graphics, gridX, gridY, 1, disabledButtonList.size(), 17, Color4I.GRAY, Color4I.BLACK, mx, my, this.gridStartBottom, this.gridStartRight
               );
               drawGrid(graphics, gridX + 17, gridY, 1, disabledButtonList.size(), maxWidth + 6, 17, Color4I.GRAY, Color4I.BLACK);
            }

            for (int i = 0; i < disabledButtonList.size(); i++) {
               SidebarGuiButton button = disabledButtonList.get(i);
               if (this.selectedButton == null || this.selectedButton != button) {
                  int buttonY = gridY + 17 * i;
                  button.x = this.gridStartRight ? addIconX : gridX;
                  button.y = buttonY;
                  GuiHelper.setupDrawing();
                  if (mx >= button.x && my >= button.y && mx < button.x + 16 && my < button.y + 16) {
                     Color4I.WHITE.withAlpha(137).draw(graphics, button.x + 1, button.y + 1, 16, 16);
                     this.mouseOver = button;
                  }

                  button.getSidebarButton().getData().icon().draw(graphics, button.x + 1, button.y + 1, 16, 16);
                  String langText = I18n.get(button.getSidebarButton().getLangKey(), new Object[0]);
                  int textXPos = this.gridStartRight ? addIconX - Minecraft.getInstance().font.width(langText) - 2 : gridX + 17 + 3;
                  graphics.drawString(Minecraft.getInstance().font, langText, textXPos, buttonY + 5, -1);
               }
            }

            graphics.pose().popPose();
         }
      }
   }

   public void onRelease(double d, double e) {
      if (this.lastMouseClickButton != 1) {
         super.onRelease(d, e);
         if (!this.isEditMode && this.mouseOver != null) {
            this.mouseOver.getSidebarButton().clickButton(Screen.hasShiftDown());
         } else if (this.selectedButton != null) {
            GridLocation gLocation = this.getGridLocation();
            if (!gLocation.isOutOfBounds() && !gLocation.equals(this.selectedLocation)) {
               this.updateButtonLocations(gLocation);
            }

            this.selectedButton = null;
            this.ensureGridAlignment();
         }
      }
   }

   private void updateButtonLocations(GridLocation gLocation) {
      boolean isFrom0XTo1X = this.selectedLocation.y() == gLocation.y() && this.selectedLocation.x() == 0 && gLocation.x() == 1;
      this.selectedButton.setGridLocation(gLocation.x(), gLocation.y());

      for (SidebarGuiButton button : SidebarButtonManager.INSTANCE.getButtonList()) {
         GridLocation realGridLocation = this.realLocationMap.get(button);
         if (realGridLocation != null
            && !this.selectedButton.getSidebarButton().getId().equals(button.getSidebarButton().getId())
            && gLocation.isLaterInColumn(realGridLocation)) {
            int moveAmount = isFrom0XTo1X && realGridLocation.x() == 1 ? -1 : 1;
            button.setGridLocation(realGridLocation.x() + moveAmount, realGridLocation.y());
         }
      }

      if (!this.selectedButton.isEnabled()) {
         this.selectedButton.setEnabled(true);
         if (SidebarButtonManager.INSTANCE.getDisabledButtonList(this.isEditMode).isEmpty()) {
            this.addBoxOpen = false;
         }
      }
   }

   private void ensureGridAlignment() {
      List<SidebarGuiButton> enabledButtonList = SidebarButtonManager.INSTANCE.getEnabledButtonList(this.isEditMode);
      Map<Integer, List<SidebarGuiButton>> buttonMap = enabledButtonList.stream()
         .filter(SidebarGuiButton::isEnabled)
         .collect(Collectors.groupingBy(buttonx -> buttonx.getGridLocation().y(), TreeMap::new, Collectors.toCollection(LinkedList::new)));
      this.realLocationMap.clear();
      int y = 0;

      for (Entry<Integer, List<SidebarGuiButton>> entry : buttonMap.entrySet()) {
         entry.getValue().sort(Comparator.comparingInt(b -> b.getGridLocation().x()));
         int x = 0;

         for (SidebarGuiButton button : entry.getValue()) {
            this.realLocationMap.put(button, new GridLocation(x, y));
            x++;
         }

         if (x != 0) {
            y++;
         }
      }

      SidebarButtonManager.INSTANCE.saveConfigFromButtonList();
      this.updateWidgetSize();
   }

   private void updateWidgetSize() {
      int girdAmountX = 1;
      int girdAmountY = 1;

      for (SidebarGuiButton b : SidebarButtonManager.INSTANCE.getEnabledButtonList(this.isEditMode)) {
         girdAmountX = Math.max(girdAmountX, b.getGridLocation().x() + 1);
         girdAmountY = Math.max(girdAmountY, b.getGridLocation().y() + 1);
      }

      if (this.isEditMode && this.addBoxOpen) {
         int disabledList = SidebarButtonManager.INSTANCE.getDisabledButtonList(this.isEditMode).size();
         girdAmountX += 4;
         girdAmountY = Math.max(girdAmountY, disabledList);
      }

      if (this.isEditMode) {
         girdAmountX += 3;
         girdAmountY++;
      }

      int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
      int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
      int maxGirdAmountX = screenWidth / 17;
      int maxGirdAmountY = screenHeight / 17;
      girdAmountX = Math.min(girdAmountX, maxGirdAmountX);
      girdAmountY = Math.min(girdAmountY, maxGirdAmountY);
      this.width = girdAmountX * 17;
      this.height = girdAmountY * 17;
      FTBLibraryClientConfig.SidebarPosition sidebarPosition = FTBLibraryClientConfig.SIDEBAR_POSITION.get();
      if (sidebarPosition.isBottom()) {
         this.setY(screenHeight - this.height - 2);
         this.gridStartBottom = true;
      } else {
         this.setY(0);
         this.gridStartBottom = false;
      }

      if (sidebarPosition.isRight()) {
         this.setX(screenWidth - this.width - 2);
         this.gridStartRight = true;
      } else {
         this.setX(0);
         this.gridStartRight = false;
      }

      this.currentGirdWidth = 1;
      this.currentGridHeight = 1;

      for (Entry<SidebarGuiButton, GridLocation> value : this.realLocationMap.entrySet()) {
         GridLocation location = value.getValue();
         this.currentGirdWidth = Math.max(this.currentGirdWidth, location.x() + 1);
         this.currentGridHeight = Math.max(this.currentGridHeight, location.y() + 1);
      }

      if (this.isEditMode) {
         this.currentGirdWidth++;
         this.currentGridHeight++;
      }

      this.yRenderStart = this.gridStartBottom ? screenHeight - this.currentGridHeight * 17 - 2 : 0;
      this.xRenderStart = this.gridStartRight ? screenWidth - this.currentGirdWidth * 17 - 1 : 0;
      lastDrawnArea = new Rect2i(this.getX(), this.getY(), this.width, this.height);
   }

   private GridLocation getGridLocation() {
      int gridX = (this.currentMouseX - this.xRenderStart - 1) / 17;
      int gridY = (this.currentMouseY - this.yRenderStart - 1) / 17;
      if (this.gridStartRight) {
         gridX = this.currentGirdWidth - gridX - 1;
      }

      if (this.gridStartBottom) {
         gridY = this.currentGridHeight - gridY - 1;
      }

      return gridX < this.currentGirdWidth && gridY < this.currentGridHeight && gridX >= 0 && gridY >= 0
         ? new GridLocation(gridX, gridY)
         : GridLocation.OUT_OF_BOUNDS;
   }

   public void onPress() {
      if (this.lastMouseClickButton == 1) {
         this.isEditMode = !this.isEditMode;
         this.ensureGridAlignment();
      } else if (this.mouseOverSettingsIcon) {
         ConfigManagerClient.editConfig("ftblibrary-client");
      } else if (this.isEditMode && this.isMouseOverAdd) {
         this.addBoxOpen = !this.addBoxOpen;
         this.updateWidgetSize();
      } else if (this.mouseOver != null) {
         this.mouseOffsetX = this.currentMouseX - this.mouseOver.x;
         this.mouseOffsetY = this.currentMouseY - this.mouseOver.y;
         if (this.isEditMode) {
            if (this.currentMouseX >= this.mouseOver.x + 12
               && this.currentMouseY <= this.mouseOver.y + 4
               && this.currentMouseX < this.mouseOver.x + 16
               && this.currentMouseY >= this.mouseOver.y) {
               this.mouseOver.setEnabled(false);
               this.mouseOver = null;
               this.ensureGridAlignment();
            } else {
               this.selectedButton = this.mouseOver;
               GridLocation realGridLocation = this.realLocationMap.get(this.selectedButton);
               this.selectedLocation = realGridLocation == null ? this.selectedButton.getGridLocation() : realGridLocation;
            }
         }
      }
   }

   public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
      this.defaultButtonNarrationText(narrationElementOutput);
   }

   protected boolean isValidClickButton(int i) {
      boolean inBounds = this.clicked(this.currentMouseX, this.currentMouseY);
      if (!inBounds && this.isEditMode) {
         this.isEditMode = false;
         this.ensureGridAlignment();
         return false;
      }

      this.lastMouseClickButton = i;
      if (i == 1) {
         return inBounds;
      }

      if (!super.isValidClickButton(i)) {
         return false;
      }

      if (this.isEditMode) {
         return this.isMouseOverAdd || this.selectedButton != null || this.mouseOver != null;
      }

      GridLocation gridLocation = this.getGridLocation();
      return gridLocation.y() == 0 && gridLocation.x() == 0 || this.mouseOver != null;
   }
}
