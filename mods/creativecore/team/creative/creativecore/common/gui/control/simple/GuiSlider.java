package team.creative.creativecore.common.gui.control.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.client.render.GuiRenderHelper;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.IGuiParent;
import team.creative.creativecore.common.gui.event.GuiSliderUpdateEvent;
import team.creative.creativecore.common.gui.parser.DoubleValueParser;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.util.math.geo.Rect;

public class GuiSlider extends GuiControl implements IGuiParent {
   protected double maxValue;
   protected double minValue;
   protected double value;
   private final DoubleValueParser parser;
   protected boolean grabbedSlider;
   public int sliderSize = 4;
   protected GuiTextfield textfield;
   private GuiSlider minSlider;
   private GuiSlider maxSlider;

   public GuiSlider(String name, double value, double min, double max) {
      this(name, value, min, max, DoubleValueParser.NONE);
   }

   public GuiSlider(String name, double value, double min, double max, DoubleValueParser parser) {
      super(name);
      this.minValue = min;
      this.maxValue = max;
      this.parser = parser;
      this.setValue(value);
   }

   public String getTextByValue() {
      return this.parser.parse(this.getValue(), this.getMaxValue());
   }

   public String getTextfieldValue() {
      return this.getTextByValue();
   }

   public double getPercentage() {
      return (this.value - this.minValue) / (this.maxValue - this.minValue);
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (button == 0) {
         if (this.textfield != null) {
            return this.textfield.mouseClicked(x, y, button);
         }

         playSound(SoundEvents.UI_BUTTON_CLICK);
         this.grabbedSlider = true;
         this.mouseMoved(x, y);
         return true;
      } else if (button == 1) {
         this.grabbedSlider = false;
         this.textfield = this.createTextfield();
         this.textfield.focus();
         this.textfield.setText(this.getTextfieldValue());
         this.textfield.setCursorPositionEnd();
         this.textfield.setParent(this);
         int width = this.rect.getWidth();
         this.textfield.rect.setWidth(width, width);
         this.textfield.rect.flowX();
         int height = this.rect.getHeight();
         this.textfield.rect.setHeight(height, height);
         this.textfield.rect.flowY();
         return true;
      } else {
         return false;
      }
   }

   protected GuiTextfield createTextfield() {
      return new GuiTextfield(this.getNestedName() + ".text").setFloatOnly().setDim(this.rect.getContentWidth(), this.rect.getContentHeight());
   }

   public void closeTextField() {
      double value = this.value;

      try {
         this.setValue(this.textfield.parseFloat());
         playSound(SoundEvents.UI_BUTTON_CLICK);
      } catch (NumberFormatException e) {
         this.setValue(value);
      }

      this.textfield = null;
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.textfield != null) {
         if (keyCode == 257) {
            this.closeTextField();
            return true;
         } else {
            return this.textfield.keyPressed(keyCode, scanCode, modifiers);
         }
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   @Override
   public boolean charTyped(char codePoint, int modifiers) {
      return this.textfield != null ? this.textfield.charTyped(codePoint, modifiers) : super.charTyped(codePoint, modifiers);
   }

   public void setMaxValue(double maxValue) {
      if (this.maxValue != maxValue) {
         this.maxValue = Math.max(this.minValue, maxValue);
         this.setValue(this.value);
      }
   }

   public void setMinValue(double minValue) {
      if (this.minValue != minValue) {
         this.minValue = Math.min(minValue, this.maxValue);
         this.setValue(this.value);
      }
   }

   public void setValue(double value) {
      this.value = Math.max(this.minValue, value);
      this.value = Math.min(this.maxValue, this.value);
      if (this.getParent() != null) {
         this.raiseEvent(new GuiSliderUpdateEvent(this));
      }

      if (this.minSlider != null) {
         this.minSlider.setMaxValue(value);
      }

      if (this.maxSlider != null) {
         this.maxSlider.setMinValue(value);
      }
   }

   public double getValue() {
      return this.value;
   }

   public double getMinValue() {
      return this.minValue;
   }

   public double getMaxValue() {
      return this.maxValue;
   }

   public GuiSlider setSliderSize(int size) {
      this.sliderSize = size;
      return this;
   }

   public GuiSlider setMinSlider(GuiSlider slider) {
      if (slider == this) {
         throw new IllegalArgumentException("slider argument is current slider");
      }

      this.minSlider = slider;
      return this;
   }

   public GuiSlider setMaxSlider(GuiSlider slider) {
      if (slider == this) {
         throw new IllegalArgumentException("slider argument is current slider");
      }

      this.maxSlider = slider;
      return this;
   }

   @Override
   public void mouseMoved(double x, double y) {
      if (this.grabbedSlider) {
         int width = this.rect.getContentWidth() - this.sliderSize;
         if (x < this.getContentOffset()) {
            this.value = this.minValue;
         } else if (x > this.getContentOffset() + width + this.sliderSize / 2.0F) {
            this.value = this.maxValue;
         } else {
            int mouseOffsetX = (int)(x - this.getContentOffset() - this.sliderSize / 2);
            this.value = (float)(this.minValue + (float)((this.maxValue - this.minValue) * ((float)mouseOffsetX / width)));
         }

         this.setValue(this.value);
      }
   }

   @Override
   public void looseFocus() {
      if (this.textfield != null) {
         this.closeTextField();
      }

      super.looseFocus();
   }

   @Override
   public void mouseReleased(double x, double y, int button) {
      if (this.grabbedSlider) {
         this.grabbedSlider = false;
      }
   }

   @Override
   public boolean isContainer() {
      return this.getParent().isContainer();
   }

   @Override
   public void closeTopLayer() {
      this.getParent().closeTopLayer();
   }

   @Override
   public Rect toLayerRect(GuiControl control, Rect rect) {
      return this.getParent().toLayerRect(this, rect);
   }

   @Override
   public Rect toScreenRect(GuiControl control, Rect rect) {
      return this.getParent().toScreenRect(this, rect);
   }

   @Override
   public void init() {
   }

   @Override
   public void closed() {
   }

   @Override
   public void tick() {
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.NESTED_NO_PADDING;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY) {
      double percent = this.getPercentage();
      int posX = (int)((this.rect.getContentWidth() - this.sliderSize) * percent);
      GuiStyle style = this.getStyle();
      style.get(ControlFormatting.ControlStyleFace.CLICKABLE, false).render(graphics, posX, 0.0, this.sliderSize, this.rect.getContentHeight());
      if (this.textfield != null) {
         this.textfield.render(graphics, controlRect, controlRect, 1.0, mouseX, mouseY);
      } else {
         GuiRenderHelper.drawStringCentered(graphics, this.getTextByValue(), this.rect.getContentWidth(), this.rect.getContentHeight(), -1, true);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
   }

   @Override
   public void flowX(int width, int preferred) {
      if (this.textfield != null) {
         this.textfield.flowX(width, preferred);
      }
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      if (this.textfield != null) {
         this.textfield.flowY(width, height, preferred);
      }
   }

   @Override
   protected int preferredWidth(int availableWidth) {
      return 40;
   }

   @Override
   protected int preferredHeight(int width, int availableHeight) {
      return 10;
   }

   @Override
   public void closeLayer(GuiLayer layer) {
      this.getParent().closeLayer(layer);
   }

   @Override
   public GuiControl setEnabled(boolean enabled) {
      if (!enabled) {
         this.grabbedSlider = false;
      }

      return super.setEnabled(enabled);
   }
}
