package team.creative.creativecore.common.gui.control.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.gui.style.display.DisplayColor;
import team.creative.creativecore.common.gui.style.display.StyleDisplay;

public class GuiCheckBox extends GuiLabel {
   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   private static StyleDisplay PARTIAL_STYLE;
   public static final int CHECKBOX_WIDTH = 7;
   public boolean value;
   public boolean partial = false;
   public Consumer<Boolean> changed;

   public GuiCheckBox(String name, boolean value) {
      super(name);
      this.value = value;
   }

   public GuiCheckBox consumeChanged(Consumer<Boolean> changed) {
      this.changed = changed;
      return this;
   }

   public GuiCheckBox setTranslate(String translate) {
      return (GuiCheckBox)super.setTranslate(translate);
   }

   public GuiCheckBox setTitle(Component component) {
      return (GuiCheckBox)super.setTitle(component);
   }

   public GuiCheckBox setTitle(List<Component> components) {
      return (GuiCheckBox)super.setTitle(components);
   }

   @Override
   protected int preferredWidth(int availableWidth) {
      return super.preferredWidth(availableWidth) + 7 + 3;
   }

   @Override
   protected int preferredHeight(int width, int availableHeight) {
      return Math.max(10, super.preferredHeight(width, availableHeight));
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT_NO_DISABLE;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
      int yoffset = 0;
      PoseStack pose = graphics.pose();
      GuiStyle style = this.getStyle();
      if (!this.enabled && this.getControlFormatting().hasDisabledEffect) {
         style.disabled.render(graphics, 0.0, yoffset, 7.0, 7.0);
      }

      style.get(ControlFormatting.ControlStyleBorder.SMALL).render(graphics, 0.0, yoffset, 7.0, 7.0);
      style.get(ControlFormatting.ControlStyleFace.NESTED_BACKGROUND, this.rect.inside(mouseX, mouseY)).render(graphics, 1.0, yoffset + 1, 5.0, 5.0);
      if (this.value) {
         int var10004 = yoffset - 1;
         int var10005 = this.enabled ? -1 : style.fontColorHighlight.toInt();
         graphics.drawString(Minecraft.getInstance().font, "x", 1, var10004, var10005, false);
      } else if (this.partial) {
         if (PARTIAL_STYLE == null) {
            PARTIAL_STYLE = new DisplayColor();
         }

         PARTIAL_STYLE.render(graphics, 2.0, yoffset + 2, 3.0, 3.0);
      }

      pose.pushPose();
      pose.translate(10.0F, 0.0F, 0.0F);
      this.text.render(graphics);
      pose.popPose();
   }

   public void set(boolean value) {
      if (this.value != value) {
         this.value = value;
         this.raiseEvent(new GuiControlChangedEvent<>(this));
         if (this.changed != null) {
            this.changed.accept(value);
         }
      }
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      playSound(SoundEvents.UI_BUTTON_CLICK);
      this.set(!this.value);
      return true;
   }
}
