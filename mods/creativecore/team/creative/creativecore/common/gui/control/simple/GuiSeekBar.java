package team.creative.creativecore.common.gui.control.simple;

import java.util.List;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.client.render.GuiRenderHelper;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.parser.LongValueParser;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiSeekBar extends GuiControl {
   private final LongSupplier posSupplier;
   private final LongSupplier maxSupplier;
   public LongConsumer timeUpdate;
   public LongConsumer lastTimeUpdate;
   private long pos;
   private long max;
   public final LongValueParser parser;
   public boolean grabbedSlider;

   public GuiSeekBar(String name, LongSupplier posSupplier, LongSupplier maxSupplier) {
      this(name, posSupplier, maxSupplier, LongValueParser.TIME_DURATION);
   }

   public GuiSeekBar(String name, LongSupplier posSupplier, LongSupplier maxSupplier, LongValueParser parser) {
      super(name);
      this.posSupplier = posSupplier;
      this.maxSupplier = maxSupplier;
      this.parser = parser;
      this.tick();
   }

   public GuiSeekBar setOnTimeUpdate(LongConsumer consumer) {
      this.timeUpdate = consumer;
      return this;
   }

   public GuiSeekBar setOnLastTimeUpdate(LongConsumer consumer) {
      this.lastTimeUpdate = consumer;
      return this;
   }

   public void setPosition(long value) {
      if (this.pos >= this.max) {
         value = this.max;
      }

      this.timeUpdate.accept(value);
      this.pos = value;
      if (this.getParent() != null) {
         this.raiseEvent(new GuiControlChangedEvent<>(this));
      }
   }

   public GuiSeekBar setTooltip(List<Component> tooltip) {
      super.setTooltip(tooltip);
      return this;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
      double percent = this.max > 0L ? (double)this.pos / this.max : 0.0;
      this.renderProgress(graphics, percent);
      GuiRenderHelper.drawStringCentered(
         graphics, this.parser.parse(this.pos, this.max), this.rect.getContentWidth(), this.rect.getContentHeight(), this.getStyle().fontColor.toInt(), true
      );
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected void renderProgress(GuiGraphics graphics, double percent) {
      this.getStyle().clickable.render(graphics, 0.0, 0.0, this.rect.getContentWidth() * Math.min(percent, 1.0), this.rect.getContentHeight());
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (button == 0) {
         playSound(SoundEvents.UI_BUTTON_CLICK);
         this.grabbedSlider = this.max > 0L;
         this.mouseMoved(x, y);
         return true;
      } else {
         return false;
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void mouseMoved(double x, double y) {
      if (this.grabbedSlider) {
         int width = this.rect.getWidth() - this.getContentOffset() * 2;
         long value;
         if (x < this.getContentOffset()) {
            value = 0L;
         } else if (x > this.getContentOffset() + width) {
            value = this.maxSupplier.getAsLong();
         } else {
            int mouseOffsetX = (int)(x - this.getContentOffset());
            value = (long)((float)this.maxSupplier.getAsLong() * ((float)mouseOffsetX / width));
         }

         this.setPosition(value);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void mouseReleased(double x, double y, int button) {
      if (this.grabbedSlider) {
         this.lastTimeUpdate.accept(this.pos);
         this.grabbedSlider = false;
      }
   }

   @Override
   public void init() {
   }

   @Override
   public void closed() {
   }

   @Override
   public void tick() {
      if (!this.grabbedSlider) {
         this.pos = this.posSupplier.getAsLong();
         this.max = this.maxSupplier.getAsLong();
      }
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.PROGRESSBAR;
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
      return 14;
   }
}
