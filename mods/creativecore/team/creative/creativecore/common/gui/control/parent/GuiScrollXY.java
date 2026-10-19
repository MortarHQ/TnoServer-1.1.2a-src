package team.creative.creativecore.common.gui.control.parent;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.math.vec.SmoothValue;

public class GuiScrollXY extends GuiParent {
   public int maxScrollX = 0;
   public SmoothValue scrolledX = new SmoothValue(200L);
   public boolean draggedX;
   public int maxScrollY = 0;
   public SmoothValue scrolledY = new SmoothValue(200L);
   public boolean draggedY;
   public int scrollbarThickness = 3;
   public ControlFormatting.ControlStyleFace scrollbarFace = ControlFormatting.ControlStyleFace.CLICKABLE;
   public boolean alternativeScrolling = false;
   protected int cachedWidth;
   protected int cachedHeight;

   public GuiScrollXY() {
      this("");
   }

   public GuiScrollXY(String name) {
      this(name, GuiFlow.STACK_X);
   }

   public GuiScrollXY(String name, GuiFlow flow) {
      super(name, flow);
   }

   @Override
   public double getOffsetX() {
      return -this.scrolledX.current();
   }

   @Override
   public double getOffsetY() {
      return -this.scrolledY.current();
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.NESTED;
   }

   public void onScrolledX() {
      if (this.scrolledX.aimed() < 0.0) {
         this.scrolledX.set(0.0);
      }

      if (this.scrolledX.aimed() > this.maxScrollX) {
         this.scrolledX.set(this.maxScrollX);
      }
   }

   public void onScrolledY() {
      if (this.scrolledY.aimed() < 0.0) {
         this.scrolledY.set(0.0);
      }

      if (this.scrolledY.aimed() > this.maxScrollY) {
         this.scrolledY.set(this.maxScrollY);
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
      if (this.alternativeScrolling) {
         if (Screen.hasShiftDown()) {
            if (this.needsScrollbarX()) {
               this.scrolledX.set(this.scrolledX.aimed() - scrolled * 10.0);
               this.onScrolledX();
            }
         } else {
            this.scrolledY.set(this.scrolledY.aimed() - scrolled * 10.0);
            this.onScrolledY();
         }
      } else {
         boolean shouldScrollY = this.needsScrollbarY();
         if (shouldScrollY) {
            if (scrolled > 0.0 && this.scrolledY.aimed() == 0.0) {
               shouldScrollY = false;
            } else if (scrolled < 0.0 && this.scrolledY.aimed() == this.maxScrollY) {
               shouldScrollY = false;
            }
         }

         if (shouldScrollY) {
            this.scrolledY.set(this.scrolledY.aimed() - scrolled * 10.0);
            this.onScrolledY();
         } else {
            this.scrolledX.set(this.scrolledX.aimed() - scrolled * 10.0);
            this.onScrolledX();
         }
      }
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (button == 0 && this.rect.getHeight() - y <= this.scrollbarThickness && this.needsScrollbarX()) {
         playSound(SoundEvents.UI_BUTTON_CLICK);
         this.draggedX = true;
         return true;
      } else if (button == 0 && this.rect.getWidth() - x <= this.scrollbarThickness && this.needsScrollbarY()) {
         playSound(SoundEvents.UI_BUTTON_CLICK);
         this.draggedY = true;
         return true;
      } else {
         return super.mouseClicked(x, y, button);
      }
   }

   @Override
   public void mouseMoved(double x, double y) {
      if (this.draggedX) {
         GuiStyle style = this.getStyle();
         ControlFormatting formatting = this.getControlFormatting();
         int completeWidth = this.rect.getWidth() - style.getBorder(formatting.border) * 2;
         int scrollThingWidth = Math.max(10, Math.min(completeWidth, (int)((float)completeWidth / this.cachedWidth * completeWidth)));
         if (this.cachedWidth < completeWidth) {
            scrollThingWidth = completeWidth;
         }

         double percent = x / (completeWidth - scrollThingWidth);
         this.scrolledX.set((int)(percent * this.maxScrollX));
         this.onScrolledX();
      }

      if (this.draggedY) {
         GuiStyle style = this.getStyle();
         ControlFormatting formatting = this.getControlFormatting();
         int completeHeight = this.rect.getHeight() - style.getBorder(formatting.border) * 2;
         int scrollThingHeight = Math.max(10, Math.min(completeHeight, (int)((float)completeHeight / this.cachedHeight * completeHeight)));
         if (this.cachedHeight < completeHeight) {
            scrollThingHeight = completeHeight;
         }

         double percent = y / (completeHeight - scrollThingHeight);
         this.scrolledY.set((int)(percent * this.maxScrollY));
         this.onScrolledY();
      }

      super.mouseMoved(x, y);
   }

   @Override
   public void mouseReleased(double x, double y, int button) {
      super.mouseReleased(x, y, button);
      this.draggedX = this.draggedY = false;
   }

   public boolean needsScrollbarX() {
      return this.cachedWidth > this.rect.getContentWidth();
   }

   public boolean needsScrollbarY() {
      return this.cachedHeight > this.rect.getContentHeight();
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
      float controlInvScale = (float)this.scaleFactorInv();
      pose.scale(controlInvScale, controlInvScale, controlInvScale);
      realRect.scissor();
      GuiStyle style = this.getStyle();
      RenderSystem.disableDepthTest();
      this.scrolledX.tick();
      if (this.needsScrollbarX()) {
         int completeWidth = this.rect.getWidth() - borderWidth * 2;
         int scrollThingWidth = Math.max(10, Math.min(completeWidth, (int)((float)completeWidth / this.cachedWidth * completeWidth)));
         if (this.cachedWidth < completeWidth) {
            scrollThingWidth = completeWidth;
         }

         double percent = this.scrolledX.current() / this.maxScrollX;
         style.get(this.scrollbarFace, false)
            .render(
               graphics,
               (int)(percent * (completeWidth - scrollThingWidth)) + borderWidth,
               this.rect.getHeight() - this.scrollbarThickness - borderWidth,
               scrollThingWidth,
               this.scrollbarThickness
            );
         this.maxScrollX = Math.max(0, this.cachedWidth - completeWidth + formatting.padding * 2 + 1);
      }

      this.scrolledY.tick();
      if (this.needsScrollbarY()) {
         int completeHeight = this.rect.getHeight() - borderWidth * 2;
         int scrollThingHeight = Math.max(10, Math.min(completeHeight, (int)((float)completeHeight / this.cachedHeight * completeHeight)));
         if (this.cachedHeight < completeHeight) {
            scrollThingHeight = completeHeight;
         }

         double percent = this.scrolledY.current() / this.maxScrollY;
         style.get(this.scrollbarFace, false)
            .render(
               graphics,
               this.rect.getWidth() - this.scrollbarThickness - borderWidth,
               (int)(percent * (completeHeight - scrollThingHeight)) + borderWidth,
               this.scrollbarThickness,
               scrollThingHeight
            );
         this.maxScrollY = Math.max(0, this.cachedHeight - completeHeight + formatting.padding * 2 + 1);
      }

      float controlScale = (float)this.scaleFactor();
      pose.scale(controlScale, controlScale, controlScale);
      RenderSystem.enableDepthTest();
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
   public void flowX(int width, int preferred) {
      super.flowX(preferred, preferred);
      this.updateWidth();
   }

   protected void updateWidth() {
      int maxX = 0;

      for (GuiControl control : this.controls) {
         maxX = Math.max(control.rect.getRight(), maxX);
      }

      this.cachedWidth = maxX;
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      super.flowY(this.cachedWidth, preferred, preferred);
      this.updateHeight();
   }

   protected void updateHeight() {
      int maxY = 0;

      for (GuiControl control : this.controls) {
         maxY = Math.max(control.rect.getBottom(), maxY);
      }

      this.cachedHeight = maxY;
   }
}
