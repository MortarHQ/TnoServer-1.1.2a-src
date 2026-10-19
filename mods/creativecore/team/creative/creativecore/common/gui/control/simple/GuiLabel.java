package team.creative.creativecore.common.gui.control.simple;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.client.render.text.CompiledText;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiLabel extends GuiControl {
   protected CompiledText text = CompiledText.createAnySize();

   public GuiLabel(String name) {
      super(name);
   }

   public GuiLabel setDefaultColor(int color) {
      this.text.setDefaultColor(color);
      return this;
   }

   public GuiLabel setDropShadow(boolean shadow) {
      this.text.setShadow(shadow);
      return this;
   }

   public GuiLabel setAlign(Align align) {
      this.text.setAlign(align);
      return this;
   }

   public GuiLabel setVAlign(VAlign valgin) {
      this.text.setVAlign(valgin);
      return this;
   }

   public GuiLabel setTranslate(String translate) {
      return this.setTitle(translatable(translate));
   }

   public GuiLabel setTranslate(String translate, Object... params) {
      return this.setTitle(translatable(translate, params));
   }

   public GuiLabel setTitle(Component component) {
      this.text.setText(component);
      if (this.hasGui()) {
         this.reflow();
      }

      return this;
   }

   public GuiLabel setTitle(List<Component> components) {
      this.text.setText(components);
      if (this.hasGui()) {
         this.reflow();
      }

      return this;
   }

   public GuiLabel setText(CompiledText text) {
      this.text = text;
      if (this.hasGui()) {
         this.reflow();
      }

      return this;
   }

   public GuiLabel setScale(double scale) {
      this.text.setScale(scale);
      return this;
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
      return ControlFormatting.TRANSPARENT;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
      this.text.render(graphics);
   }

   @Override
   public void flowX(int width, int preferred) {
      this.text.setDimension(width, Integer.MAX_VALUE);
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      this.text.setMaxHeight(height);
   }

   @Override
   protected int minWidth(int availableWidth) {
      return 0;
   }

   @Override
   protected int preferredWidth(int availableWidth) {
      return this.text.getTotalWidth();
   }

   @Override
   protected int minHeight(int width, int availableHeight) {
      return 9;
   }

   @Override
   protected int preferredHeight(int width, int availableHeight) {
      return this.text.getTotalHeight();
   }
}
