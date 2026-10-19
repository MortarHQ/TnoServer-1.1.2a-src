package com.finderfeed.fdlib.systems.screen.default_components.misc;

import com.finderfeed.fdlib.systems.screen.FDScreen;
import com.finderfeed.fdlib.systems.screen.FDScreenComponent;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class FDVerticalComponentContainer extends FDScrollableComponent {
   public static final float SCROLLER_WIDTH = 4.0F;
   public float maxScroll = 0.0F;
   public float distBetweenChildren = 0.0F;
   public int scrollerTicker = 0;
   public static final int MAX_TIME = 20;

   public FDVerticalComponentContainer(FDScreen screen, String uniqueId, float x, float y, float height) {
      super(screen, uniqueId, x, y, 0.0F, height);
   }

   @Override
   public void renderComponent(GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
      FDRenderUtil.Scissor.pushScissors(x, y, this.getWidth(), this.getHeight());
   }

   @Override
   public void postRenderComponent(GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
      float max = this.getMaxScrollY();
      if (max != 0.0F) {
         PoseStack matrices = graphics.pose();
         matrices.pushPose();
         matrices.translate(0.0F, 0.0F, 100.0F);
         float a = 0.25F + 0.5F * (1.0F - (float)Math.pow(1.0F - this.scrollerTicker / 20.0F, 4.0));
         FDRenderUtil.renderScrollBar(
            graphics.pose(), x + this.getWidth() - 4.0F, y, 4.0F, this.getHeight(), this.scrollY, max, 0.15F, 0.15F, 0.15F, a, 0.5F, 0.5F, 0.5F, a
         );
         matrices.popPose();
      }

      FDRenderUtil.Scissor.popScissors();
   }

   public void replaceChildren() {
      float accumulatedH = -this.scrollY;

      for (FDScreenComponent component : this.getChildren()) {
         component.setY(accumulatedH);
         accumulatedH += component.getHeight() + this.distBetweenChildren;
      }
   }

   public void recalculateMaxScroll() {
      float h = 0.0F;

      for (FDScreenComponent component : this.getChildren()) {
         h += component.getHeight() + this.distBetweenChildren;
      }

      h = Mth.clamp(h - this.getHeight(), 0.0F, 2.1474836E9F);
      this.maxScroll = h;
   }

   public float calculateWidth() {
      float m = 0.0F;

      for (FDScreenComponent component : this.getChildren()) {
         m = Math.max(component.getWidth(), m);
      }

      return m;
   }

   @Override
   public void onChildAdded(FDScreenComponent component) {
      this.recalculateMaxScroll();
      if (component.getWidth() > this.getWidth()) {
         this.setWidth(component.getWidth());
      }

      this.replaceChildren();
   }

   @Override
   public void onChildRemoved(FDScreenComponent component) {
      this.recalculateMaxScroll();
      this.setWidth(this.calculateWidth());
      this.replaceChildren();
   }

   @Override
   public void onChildWidthChanged(FDScreenComponent child, float previous, float current) {
      super.onChildWidthChanged(child, previous, current);
      this.setWidth(this.calculateWidth());
   }

   @Override
   public void onChildHeightChanged(FDScreenComponent child, float previous, float current) {
      super.onChildHeightChanged(child, previous, current);
      this.recalculateMaxScroll();
      this.scrollY = 0.0F;
      this.scrollX = 0.0F;
   }

   @Override
   public void tick() {
      super.tick();
      this.scrollerTicker = Mth.clamp(this.scrollerTicker - 1, 0, 20);
   }

   @Override
   public void setHeight(float height) {
      super.setHeight(height);
      this.recalculateMaxScroll();
   }

   @Override
   public void setWidth(float width) {
      super.setWidth(width);
   }

   @Override
   public void onVerticalScroll(float delta) {
      this.replaceChildren();
      this.scrollerTicker = 20;
   }

   @Override
   public boolean lockScrollWhenAtMaximum() {
      return true;
   }

   @Override
   public float getScrollSpeed() {
      return 10.0F;
   }

   @Override
   public float getMaxScrollX() {
      return 0.0F;
   }

   @Override
   public float getMaxScrollY() {
      return this.maxScroll;
   }
}
