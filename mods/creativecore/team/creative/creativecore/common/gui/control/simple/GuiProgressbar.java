package team.creative.creativecore.common.gui.control.simple;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.client.render.GuiRenderHelper;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.parser.DoubleValueParser;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.text.TextBuilder;

public class GuiProgressbar extends GuiControl {
   public double pos;
   public double max;
   public boolean showToolTip = true;
   public final DoubleValueParser parser;

   public GuiProgressbar(String name, double pos, double max) {
      this(name, pos, max, DoubleValueParser.PERCENT);
   }

   public GuiProgressbar(String name, double pos, double max, DoubleValueParser valueParser) {
      super(name);
      this.pos = pos;
      this.max = max;
      this.parser = valueParser;
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
   public List<Component> getTooltip() {
      return this.showToolTip
         ? new TextBuilder().number(this.pos, true).text("/").number(this.max, true).text(" (").number(this.pos / this.max * 100.0, true).text("%)").build()
         : super.getTooltip();
   }

   public double getPercentage() {
      return this.pos / this.max;
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.PROGRESSBAR;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
      this.renderProgress(graphics, this.getPercentage());
      GuiRenderHelper.drawStringCentered(
         graphics, this.parser.parse(this.pos, this.max), this.rect.getContentWidth(), this.rect.getContentHeight(), this.getStyle().fontColor.toInt(), true
      );
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected void renderProgress(GuiGraphics graphics, double percent) {
      this.getStyle().clickable.render(graphics, 0.0, 0.0, (int)(this.rect.getContentWidth() * percent), this.rect.getContentHeight());
   }

   @Override
   public void flowX(int width, int preferred) {
   }

   @Override
   public void flowY(int width, int height, int preferred) {
   }

   @Override
   protected int preferredWidth(int availableWidth) {
      return 40;
   }

   @Override
   protected int preferredHeight(int width, int availableHeight) {
      return 10;
   }
}
