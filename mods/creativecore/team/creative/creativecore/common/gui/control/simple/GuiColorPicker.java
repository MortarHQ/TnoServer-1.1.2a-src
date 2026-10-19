package team.creative.creativecore.common.gui.control.simple;

import net.minecraft.network.chat.Component;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.control.parent.GuiColumn;
import team.creative.creativecore.common.gui.control.parent.GuiRow;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.mc.ColorUtils;
import team.creative.creativecore.common.util.type.Color;

public class GuiColorPicker extends GuiParent {
   public Color color;

   public GuiColorPicker(String name, Color color, boolean hasAlpha, int alphaMin) {
      super(name);
      this.color = color;
      GuiRow row = new GuiRow();
      this.add(row);
      GuiColumn sliders = new GuiColumn(GuiFlow.STACK_Y);
      sliders.spacing = -1;
      row.addColumn(sliders);
      GuiParent red = new GuiParent(GuiFlow.STACK_X).setVAlign(VAlign.CENTER);
      sliders.add(red);
      red.add(new GuiButtonHoldSlim("r-", x -> {
         this.<GuiColoredSteppedSlider>get("r").stepDown();
         this.onColorChanged();
      }).setTitle(Component.literal("<")));
      red.add(new GuiColoredSteppedSlider("r", this, ColorUtils.ColorPart.RED).setExpandableX());
      red.add(new GuiButtonHoldSlim("r+", x -> {
         this.<GuiColoredSteppedSlider>get("r").stepUp();
         this.onColorChanged();
      }).setTitle(Component.literal(">")));
      GuiParent green = new GuiParent(GuiFlow.STACK_X).setVAlign(VAlign.CENTER);
      sliders.add(green);
      green.add(new GuiButtonHoldSlim("g-", x -> {
         this.<GuiColoredSteppedSlider>get("g").stepDown();
         this.onColorChanged();
      }).setTitle(Component.literal("<")));
      green.add(new GuiColoredSteppedSlider("g", this, ColorUtils.ColorPart.GREEN).setExpandableX());
      green.add(new GuiButtonHoldSlim("g+", x -> {
         this.<GuiColoredSteppedSlider>get("g").stepUp();
         this.onColorChanged();
      }).setTitle(Component.literal(">")));
      GuiParent blue = new GuiParent(GuiFlow.STACK_X).setVAlign(VAlign.CENTER);
      sliders.add(blue);
      blue.add(new GuiButtonHoldSlim("b-", x -> {
         this.<GuiColoredSteppedSlider>get("b").stepDown();
         this.onColorChanged();
      }).setTitle(Component.literal("<")));
      blue.add(new GuiColoredSteppedSlider("b", this, ColorUtils.ColorPart.BLUE).setExpandableX());
      blue.add(new GuiButtonHoldSlim("b+", x -> {
         this.<GuiColoredSteppedSlider>get("b").stepUp();
         this.onColorChanged();
      }).setTitle(Component.literal(">")));
      if (hasAlpha) {
         GuiParent alpha = new GuiParent(GuiFlow.STACK_X).setVAlign(VAlign.CENTER);
         sliders.add(alpha);
         alpha.add(new GuiButtonHoldSlim("a-", x -> {
            this.<GuiColoredSteppedSlider>get("a").stepDown();
            this.onColorChanged();
         }).setTitle(Component.literal("<")));
         alpha.add(new GuiColoredSteppedSlider("a", this, ColorUtils.ColorPart.ALPHA).setExpandableX());
         alpha.add(new GuiButtonHoldSlim("a+", x -> {
            this.<GuiColoredSteppedSlider>get("a").stepUp();
            this.onColorChanged();
         }).setTitle(Component.literal(">")));
      } else {
         color.setAlpha(255);
      }

      GuiColumn plate = new GuiColumn(30, GuiFlow.STACK_Y);
      plate.align = Align.CENTER;
      plate.valign = VAlign.CENTER;
      row.addColumn(plate);
      plate.add(new GuiColorPlate("plate", color).setDim(20, 20));
      this.registerEventChanged(x -> {
         if (x.control.is("r", "g", "b", "a")) {
            this.onColorChanged();
         }
      });
   }

   public void setColor(Color color) {
      this.color.set(color);
      this.<GuiColoredSteppedSlider>get("r").setValue(color.getRed());
      this.<GuiColoredSteppedSlider>get("g").setValue(color.getGreen());
      this.<GuiColoredSteppedSlider>get("b").setValue(color.getBlue());
      if (this.has("a")) {
         this.<GuiColoredSteppedSlider>get("a").setValue(color.getAlpha());
      }
   }

   public void onColorChanged() {
      this.raiseEvent(new GuiControlChangedEvent<>(this));
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT;
   }
}
