package team.creative.creativecore.common.gui.control.simple;

import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.client.render.text.CompiledText;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.mc.ColorUtils;

public class GuiRowLabel extends GuiLabel {
   public final int index;
   public final Consumer<Integer> consumer;
   public final boolean selected;

   public GuiRowLabel(String name, int index, boolean selected, Consumer<Integer> consumer) {
      super(name);
      this.index = index;
      this.consumer = consumer;
      this.selected = selected;
      this.setExpandableX();
   }

   public GuiRowLabel set(CompiledText text) {
      this.text = text;
      return this;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY) {
      if (this.selected) {
         this.text.setDefaultColor(realRect.inside(mouseX, mouseY) ? ColorUtils.rgba(230, 230, 0, 255) : ColorUtils.rgba(200, 200, 0, 255));
      } else if (realRect.inside(mouseX, mouseY)) {
         this.text.setDefaultColor(-256);
      } else {
         this.text.setDefaultColor(-1);
      }

      super.renderContent(graphics, controlRect, realRect, scale, mouseX, mouseY);
      this.text.setDefaultColor(-1);
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      this.consumer.accept(button);
      playSound(SoundEvents.UI_BUTTON_CLICK);
      return true;
   }
}
