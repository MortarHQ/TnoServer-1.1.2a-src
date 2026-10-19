package team.creative.creativecore.common.gui.control.timeline;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.gui.style.display.StyleDisplay;
import team.creative.creativecore.common.util.math.geo.Rect;

public class GuiTimelineKey<T> extends GuiControl implements Comparable<GuiTimelineKey> {
   public static final double DRAG_TIME = 2.0;
   public GuiTimelineChannel channel;
   public boolean modifiable = true;
   public int tick;
   public T value;
   private boolean selected;
   private boolean clicked;

   public GuiTimelineKey(GuiTimelineChannel channel, int tick, T value) {
      super("");
      this.channel = channel;
      this.tick = tick;
      this.value = value;
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (!this.modifiable) {
         return false;
      }

      if (button == 0) {
         this.channel.select(this);
         playSound(SoundEvents.UI_BUTTON_CLICK);
         this.clicked = true;
      } else if (button == 1) {
         if (this.selected) {
            this.channel.timeline.deselect();
         }

         this.channel.removeKey(this);
         playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM, 0.1F, 0.6F);
      }

      return true;
   }

   @Override
   public void mouseDragged(double x, double y, int button, double dragX, double dragY, double time) {
      if (this.modifiable) {
         if (this.clicked && time > 2.0) {
            this.channel.dragKey(this);
            this.clicked = false;
         }

         super.mouseDragged(x, y, button, dragX, dragY, time);
      }
   }

   @Override
   public void mouseReleased(double x, double y, int button) {
      this.clicked = false;
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }

   public void removeKey() {
      this.channel.removeKey(this);
   }

   public int compareTo(GuiTimelineKey o) {
      return Integer.compare(this.tick, o.tick);
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
      return 6;
   }

   @Override
   protected int preferredHeight(int width, int availableHeight) {
      return 6;
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT_NO_DISABLE;
   }

   @Override
   public Rect createChildRect(Rect contentRect, double scale, double xOffset, double yOffset) {
      Rect temp = this.rect.rectCopy();
      temp.grow(Math.max(temp.getWidth() / 4.0, temp.getHeight() / 4.0));
      return contentRect.child(temp, scale, xOffset, yOffset);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void render(GuiGraphics graphics, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY) {
      RenderSystem.clear(256, Minecraft.ON_OSX);
      PoseStack pose = graphics.pose();
      GuiStyle style = this.getStyle();
      pose.pushPose();
      int width = this.rect.getWidth();
      int height = this.rect.getHeight();
      pose.translate(width * 0.5, height * 0.5, 0.0);
      pose.mulPose(Axis.ZP.rotationDegrees(45.0F));
      pose.translate(width * -0.5, height * -0.5, 0.0);
      int borderWidth = style.getBorder(ControlFormatting.ControlStyleBorder.SMALL);
      style.border.render(graphics, width, height);
      StyleDisplay foreground = style.clickable;
      if (!this.enabled || !this.modifiable) {
         foreground = style.disabledBackground;
      } else if (this.selected) {
         foreground = style.headerBackground;
      } else if (controlRect.inside(mouseX, mouseY)) {
         foreground = style.clickableHighlight;
      }

      foreground.render(graphics, borderWidth, borderWidth, width - borderWidth * 2, height - borderWidth * 2);
      pose.popPose();
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
   }
}
