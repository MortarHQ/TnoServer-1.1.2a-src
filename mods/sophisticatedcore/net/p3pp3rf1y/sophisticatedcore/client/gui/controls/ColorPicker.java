package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import it.unimi.dsi.fastutil.floats.FloatConsumer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.item.DyeColor;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.util.ColorHelper;

public class ColorPicker extends CompositeWidgetBase<WidgetBase> {
   public static final int COLOR_GRADIENT_WIDTH = 50;
   public static final int RAINBOW_SLIDER_WIDTH = 10;
   public static final int COLOR_ENTRY_WIDTH = 62;
   public static final int COLOR_GRADIENT_HEIGHT = 50;
   public static final Dimension DIMENSIONS = new Dimension(114, 84);
   private final TextBox textColorEntry;
   private final ColorPicker.ColorPreview colorPreview;
   private final ColorPicker.ColorGradientArea colorGradientArea;
   private final ColorPicker.RainbowSlider rainbowSlider;
   private final Button confirmButton;
   private final Button cancelButton;
   private final Button transparentColorButton;
   private final List<ColorButton> defaultColorButtons = new ArrayList<>();

   public ColorPicker(final Screen screen, Position position, int color, IntConsumer colorSetter) {
      super(position, DIMENSIONS);
      this.colorPreview = new ColorPicker.ColorPreview(new Position(0, 0), new Dimension(50, 12), color);
      this.addChild(this.colorPreview);
      this.textColorEntry = new TextBox(new Position(0, 0), new Dimension(62, 12)) {
         public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.isEditable()) {
               this.setFocused(true);
               screen.setFocused(ColorPicker.this.textColorEntry);
            }

            return super.mouseClicked(mouseX, mouseY, button);
         }
      };
      this.addChild(this.textColorEntry);
      this.colorGradientArea = new ColorPicker.ColorGradientArea(new Position(0, 0), new Dimension(50, 50), gradientColor -> {
         this.textColorEntry.setValueWithoutNotification(ColorHelper.getHexColor(gradientColor));
         this.colorPreview.setColor(gradientColor);
      });
      this.colorGradientArea.setColor(color);
      this.rainbowSlider = new ColorPicker.RainbowSlider(new Position(0, 0), new Dimension(10, 50), this.colorGradientArea::setHue);
      this.rainbowSlider.setColor(color);
      this.addChild(this.rainbowSlider);
      this.addChild(this.colorGradientArea);
      this.textColorEntry.setValue(ColorHelper.getHexColor(color));
      this.textColorEntry.setResponder(s -> {
         if (s.length() == 7) {
            try {
               int c = ARGB32.opaque(Integer.parseInt(s.substring(1), 16));
               this.colorPreview.setColor(c);
               this.colorGradientArea.setColor(c);
               this.rainbowSlider.setColor(c);
            } catch (NumberFormatException var3x) {
            }
         }
      });
      this.addChild(this.textColorEntry);
      this.confirmButton = new Button(new Position(0, 0), ButtonDefinitions.CONFIRM, button -> colorSetter.accept(this.colorPreview.getColor()));
      this.addChild(this.confirmButton);
      this.transparentColorButton = new Button(new Position(0, 0), ButtonDefinitions.TRANSPARENT, button -> colorSetter.accept(-1));
      this.addChild(this.transparentColorButton);
      this.cancelButton = new Button(new Position(0, 0), ButtonDefinitions.CANCEL, button -> colorSetter.accept(color));
      this.addChild(this.cancelButton);
      this.addDefaultColorButtons();
      this.setPosition(position);
   }

   private void addDefaultColorButtons() {
      for (DyeColor value : DyeColor.values()) {
         ColorButton colorButton = new ColorButton(new Position(0, 0), new Dimension(11, 11), value::getTextureDiffuseColor, button -> {
            int color = value.getTextureDiffuseColor();
            this.colorGradientArea.setColor(color);
            this.rainbowSlider.setColor(color);
            this.colorPreview.setColor(color);
            this.textColorEntry.setValueWithoutNotification(ColorHelper.getHexColor(color));
         }, null);
         this.defaultColorButtons.add(colorButton);
         this.addChild(colorButton);
      }
   }

   @Override
   public void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      GuiHelper.renderControlBackground(guiGraphics, this.x - 5, this.y - 5, this.getWidth() + 5 + 5, this.getHeight() + 5 + 5, 128, 0, 128, 256);
   }

   @Override
   public void setPosition(Position position) {
      super.setPosition(position);
      this.textColorEntry.setPosition(new Position(this.x, this.y + this.colorGradientArea.getHeight() + 2));
      this.colorPreview.setPosition(new Position(this.x + this.textColorEntry.getWidth() + 2, this.y + this.colorGradientArea.getHeight() + 2));
      this.colorGradientArea.setPosition(new Position(this.x, this.y));
      this.rainbowSlider.setPosition(new Position(this.x + this.colorGradientArea.getWidth() + 2, this.y));
      this.confirmButton
         .setPosition(new Position(this.x + this.getWidth() / 2 - 32, this.y + this.colorGradientArea.getHeight() + 2 + this.textColorEntry.getHeight() + 2));
      this.transparentColorButton.setPosition(new Position(this.x + this.getWidth() / 2 - 8, this.confirmButton.getY()));
      this.cancelButton.setPosition(new Position(this.x + this.getWidth() / 2 + 16, this.confirmButton.getY()));
      int row = 0;
      int column = 0;

      for (ColorButton defaultColorButton : this.defaultColorButtons) {
         defaultColorButton.setPosition(new Position(this.rainbowSlider.getX() + this.rainbowSlider.getWidth() + 2 + column * 13, this.y + row * 13));
         if (++column > 3) {
            column = 0;
            row++;
         }
      }
   }

   private static class ColorGradientArea extends WidgetBase {
      private int color;
      private float hue;
      private final IntConsumer colorSetter;

      protected ColorGradientArea(Position position, Dimension dimension, IntConsumer colorSetter) {
         super(position, dimension);
         this.colorSetter = colorSetter;
      }

      public void setColor(int color) {
         this.color = color;
         this.hue = Color.RGBtoHSB(ARGB32.red(color), ARGB32.green(color), ARGB32.blue(color), null)[0];
      }

      public void setHue(float hue) {
         this.hue = hue;
         float[] hsv = Color.RGBtoHSB(ARGB32.red(this.color), ARGB32.green(this.color), ARGB32.blue(this.color), null);
         float saturation = hsv[1];
         float value = hsv[2];
         this.color = ARGB32.opaque(Mth.hsvToRgb(hue, saturation, value));
         this.colorSetter.accept(this.color);
      }

      @Override
      protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
         int topRightCornerColor = Mth.hsvToRgb(this.hue, 1.0F, 1.0F);
         int red = ARGB32.red(topRightCornerColor);
         int green = ARGB32.green(topRightCornerColor);
         int blue = ARGB32.blue(topRightCornerColor);

         for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
               float horizontalFactor = (float)i / this.getWidth();
               float verticalFactor = (float)j / this.getHeight();
               int color = ARGB32.opaque(
                  ARGB32.color(
                     (int)((1.0F - verticalFactor) * ((1.0F - horizontalFactor) * 255.0F + red * horizontalFactor)),
                     (int)((1.0F - verticalFactor) * ((1.0F - horizontalFactor) * 255.0F + green * horizontalFactor)),
                     (int)((1.0F - verticalFactor) * ((1.0F - horizontalFactor) * 255.0F + blue * horizontalFactor))
                  )
               );
               guiGraphics.fill(this.x + i, this.y + j, this.x + i + 1, this.y + j + 1, color);
            }
         }
      }

      @Override
      protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
         float[] hsv = Color.RGBtoHSB(ARGB32.red(this.color), ARGB32.green(this.color), ARGB32.blue(this.color), null);
         int x = (int)(hsv[1] * this.getWidth());
         int y = (int)((1.0F - hsv[2]) * this.getHeight());
         GuiHelper.fill(guiGraphics, this.x, this.y + Math.max(y - 0.2F, 0.0F), this.x + this.getWidth(), this.y + Math.min(y + 1.2F, this.getHeight()), -1);
         GuiHelper.fill(guiGraphics, this.x + Math.max(x - 0.2F, 0.0F), this.y, this.x + Math.min(x + 1.2F, this.getWidth()), this.y + this.getHeight(), -1);
         GuiHelper.fill(guiGraphics, this.x, this.y + y, this.x + this.getWidth(), this.y + y + 1, -16777216);
         GuiHelper.fill(guiGraphics, this.x + x, this.y, this.x + x + 1, this.y + this.getHeight(), -16777216);
      }

      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         this.setColorBasedOnMouseCoords(mouseX, mouseY);
         return true;
      }

      private void setColorBasedOnMouseCoords(double mouseX, double mouseY) {
         double xClicked = mouseX - this.x;
         double yClicked = mouseY - this.y;
         float saturation = (float)xClicked / this.getWidth();
         float value = 1.0F - (float)yClicked / this.getHeight();
         this.color = ARGB32.opaque(Mth.hsvToRgb(this.hue, saturation, value));
         this.colorSetter.accept(this.color);
      }

      public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
         this.setColorBasedOnMouseCoords(mouseX, mouseY);
         return true;
      }
   }

   private static class ColorPreview extends WidgetBase {
      private int color;

      protected ColorPreview(Position position, Dimension dimension, int color) {
         super(position, dimension);
         this.color = color;
      }

      public void setColor(int color) {
         this.color = color;
      }

      @Override
      protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      }

      @Override
      protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
         guiGraphics.fill(this.x, this.y, this.x + this.getWidth(), this.y + this.getHeight(), this.color);
      }

      public int getColor() {
         return this.color;
      }
   }

   private static class RainbowSlider extends WidgetBase {
      private final FloatConsumer hueSetter;
      private float hue;

      protected RainbowSlider(Position position, Dimension dimension, FloatConsumer hueSetter) {
         super(position, dimension);
         this.hueSetter = hueSetter;
      }

      public void setColor(int color) {
         float[] hsl = Color.RGBtoHSB(ARGB32.red(color), ARGB32.green(color), ARGB32.blue(color), null);
         this.hue = hsl[0];
      }

      @Override
      protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
         for (int i = 0; i < this.getHeight(); i++) {
            float renderedHue = (float)i / this.getHeight();
            int color = ARGB32.opaque(Mth.hsvToRgb(renderedHue, 1.0F, 1.0F));
            guiGraphics.fill(this.x, this.y + this.getHeight() - i, this.x + this.getWidth(), this.y + this.getHeight() - i - 1, color);
         }
      }

      @Override
      protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
         int hueMarker = (int)(this.hue * this.getHeight());
         GuiHelper.fill(
            guiGraphics, this.x, this.y + this.getHeight() - hueMarker - 1, this.x + this.getWidth(), this.y + this.getHeight() - hueMarker - 1.2F, -1
         );
         GuiHelper.fill(
            guiGraphics, this.x, this.y + this.getHeight() - hueMarker, this.x + this.getWidth(), this.y + this.getHeight() - hueMarker - 1, -16777216
         );
         GuiHelper.fill(guiGraphics, this.x, this.y + this.getHeight() - hueMarker, this.x + this.getWidth(), this.y + this.getHeight() - hueMarker + 0.2F, -1);
      }

      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         this.setHueBasedOnMouseY(mouseY);
         return true;
      }

      private void setHueBasedOnMouseY(double mouseY) {
         double yClicked = mouseY - this.y;
         this.hue = 1.0F - (float)yClicked / this.getHeight();
         this.hueSetter.accept(this.hue);
      }

      public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
         this.setHueBasedOnMouseY(mouseY);
         return true;
      }
   }
}
