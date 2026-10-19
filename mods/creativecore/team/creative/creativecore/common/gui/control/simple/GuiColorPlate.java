package team.creative.creativecore.common.gui.control.simple;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.display.DisplayColor;
import team.creative.creativecore.common.util.type.Color;

public class GuiColorPlate extends GuiControl {
   private Color color;
   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   private DisplayColor colorPlate;

   public GuiColorPlate(String name, Color color) {
      super(name);
      if (this.isClient()) {
         this.setColor(color);
      }
   }

   public void setColor(Color color) {
      this.color = color;
      this.colorPlate = new DisplayColor(color);
   }

   public Color getColor() {
      return this.color;
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.NESTED_NO_PADDING;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
      this.getStyle().transparencyBackground.render(graphics, this.rect.getContentWidth(), this.rect.getContentHeight());
      this.colorPlate.set(this.color);
      this.colorPlate.render(graphics, this.rect.getContentWidth(), this.rect.getContentHeight());
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
   public void flowX(int width, int preferred) {
   }

   @Override
   public void flowY(int width, int height, int preferred) {
   }

   @Override
   protected int preferredWidth(int availableWidth) {
      return 20;
   }

   @Override
   protected int preferredHeight(int width, int availableHeight) {
      return 20;
   }
}
