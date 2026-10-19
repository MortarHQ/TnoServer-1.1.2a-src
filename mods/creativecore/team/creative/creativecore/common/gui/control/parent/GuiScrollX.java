package team.creative.creativecore.common.gui.control.parent;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.flow.GuiSizeRule;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.gui.style.display.StyleDisplay;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.math.vec.SmoothValue;

public class GuiScrollX extends GuiParent {
   public int maxScroll = 0;
   public SmoothValue scrolled = new SmoothValue(200L);
   public boolean dragged;
   public int scrollbarHeight = 3;
   public boolean hoveredScroll;
   protected int cachedWidth;

   public GuiScrollX() {
      this("");
   }

   public GuiScrollX(String name) {
      super(name, GuiFlow.STACK_X);
   }

   public GuiScrollX setHovered() {
      this.hoveredScroll = true;
      return this;
   }

   public GuiScrollX setHover(boolean hover) {
      this.hoveredScroll = hover;
      return this;
   }

   public GuiScrollX setDim(int width, int height) {
      return (GuiScrollX)super.setDim(width, height);
   }

   public GuiScrollX setDim(GuiSizeRule dim) {
      return (GuiScrollX)super.setDim(dim);
   }

   public GuiScrollX setExpandable() {
      return (GuiScrollX)super.setExpandable();
   }

   @Override
   public double getOffsetX() {
      return -this.scrolled.current();
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.NESTED;
   }

   public void onScrolled() {
      if (this.scrolled.aimed() < 0.0) {
         this.scrolled.set(0.0);
      }

      if (this.scrolled.aimed() > this.maxScroll) {
         this.scrolled.set(this.maxScroll);
      }
   }

   @Override
   public boolean mouseScrolled(double x, double y, double scrolled) {
      if (super.mouseScrolled(x, y, scrolled)) {
         return true;
      }

      this.scroll(scrolled);
      return true;
   }

   public void scroll(double scrolled) {
      this.scrolled.set(this.scrolled.aimed() - scrolled * 10.0);
      this.onScrolled();
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (button == 0 && this.rect.getHeight() - y <= this.scrollbarHeight && this.needsScrollbar()) {
         playSound(SoundEvents.UI_BUTTON_CLICK);
         this.dragged = true;
         return true;
      } else {
         return super.mouseClicked(x, y, button);
      }
   }

   @Override
   public void mouseMoved(double x, double y) {
      if (this.dragged) {
         GuiStyle style = this.getStyle();
         ControlFormatting formatting = this.getControlFormatting();
         int completeWidth = this.rect.getWidth() - style.getBorder(formatting.border) * 2;
         int scrollThingWidth = Math.max(10, Math.min(completeWidth, (int)((float)completeWidth / this.cachedWidth * completeWidth)));
         if (this.cachedWidth < completeWidth) {
            scrollThingWidth = completeWidth;
         }

         double percent = x / (completeWidth - scrollThingWidth);
         this.scrolled.set((int)(percent * this.maxScroll));
         this.onScrolled();
      }

      super.mouseMoved(x, y);
   }

   @Override
   public void mouseReleased(double x, double y, int button) {
      super.mouseReleased(x, y, button);
      this.dragged = false;
   }

   public boolean needsScrollbar() {
      return this.cachedWidth > this.rect.getContentWidth();
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(
      GuiGraphics graphics, ControlFormatting formatting, int borderWidth, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY
   ) {
      PoseStack pose = graphics.pose();
      pose.pushPose();
      super.renderContent(graphics, formatting, borderWidth, controlRect, realRect, scale, mouseX, mouseY);
      pose.popPose();
      if (this.needsScrollbar() || !this.hoveredScroll) {
         if (this.hoveredScroll) {
            RenderSystem.disableDepthTest();
         }

         float controlInvScale = (float)this.scaleFactorInv();
         pose.scale(controlInvScale, controlInvScale, controlInvScale);
         realRect.scissor();
         GuiStyle style = this.getStyle();
         this.scrolled.tick();
         int completeWidth = this.rect.getWidth() - borderWidth * 2;
         int scrollThingWidth = Math.max(10, Math.min(completeWidth, (int)((float)completeWidth / this.cachedWidth * completeWidth)));
         if (scrollThingWidth > completeWidth) {
            scrollThingWidth = completeWidth;
         }

         double percent = this.scrolled.current() / this.maxScroll;
         StyleDisplay display = this.hoveredScroll ? style.disabled : style.get(ControlFormatting.ControlStyleFace.CLICKABLE, false);
         display.render(
            graphics,
            (int)(percent * (completeWidth - scrollThingWidth)) + borderWidth,
            this.rect.getHeight() - borderWidth * this.scrollbarHeight - borderWidth,
            scrollThingWidth,
            this.scrollbarHeight
         );
         this.maxScroll = Math.max(0, this.cachedWidth - completeWidth + formatting.padding * 2 + 1);
         float controlScale = (float)this.scaleFactor();
         pose.scale(controlScale, controlScale, controlScale);
         if (this.hoveredScroll) {
            RenderSystem.enableDepthTest();
         }
      }
   }

   @Override
   protected int minWidth(int availableWidth) {
      return 10;
   }

   @Override
   protected int minHeight(int width, int availableHeight) {
      return 10;
   }

   @Override
   protected boolean endlessX() {
      return true;
   }

   @Override
   public void flowX(int width, int preferred) {
      super.flowX(width, preferred);
      this.cachedWidth = preferred;
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      if (!this.hoveredScroll) {
         height -= this.scrollbarHeight;
      }

      super.flowY(width, height, preferred);
   }
}
