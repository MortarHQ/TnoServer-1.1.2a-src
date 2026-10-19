package it.hurts.octostudios.octolib.client.screen.widget;

import com.mojang.math.Axis;
import it.hurts.octostudios.octolib.client.animation.Tween;
import it.hurts.octostudios.octolib.client.animation.easing.EaseType;
import it.hurts.octostudios.octolib.client.animation.easing.TransitionType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class TestGear extends AbstractWidget implements HasRenderMatrix, ContainerEventHandler {
   private Matrix4f renderMatrix;
   Tween tween = Tween.create();
   private float rot;
   List<GuiEventListener> children = new ArrayList<>();

   public TestGear(int x, int y) {
      super(x, y, 128, 128, Component.empty());
      this.children.add(new TestPin(16, 16, this));
   }

   @Override
   public Matrix4f getMatrix() {
      return this.renderMatrix;
   }

   @Override
   public void setMatrix(Matrix4f matrix) {
      this.renderMatrix = matrix;
   }

   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(this.getX() + this.width / 2.0F, this.getY() + this.height / 2.0F, 0.0F);
      guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(this.rot));
      guiGraphics.pose().translate(-this.width / 2.0F - this.getX(), -this.height / 2.0F - this.getY(), 0.0F);
      this.setMatrix(new Matrix4f(guiGraphics.pose().last().pose()));
      guiGraphics.renderOutline(this.getX(), this.getY(), this.width, this.height, -1);
      this.children().forEach(child -> {
         if (child instanceof Renderable renderable) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
         }
      });
      guiGraphics.pose().popPose();
   }

   protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      boolean result = super.mouseClicked(mouseX, mouseY, button);
      if (!super.mouseClicked(mouseX, mouseY, button) && result) {
         this.tween.kill();
         this.tween = Tween.create();
         this.tween.tweenMethod(this::setRot, this.rot, this.rot + 60.0F, 0.75).setEaseType(EaseType.EASE_OUT).setTransitionType(TransitionType.QUART);
         this.tween.start();
         return true;
      } else {
         return false;
      }
   }

   public List<? extends GuiEventListener> children() {
      return this.children;
   }

   public boolean isDragging() {
      return false;
   }

   public void setDragging(boolean isDragging) {
   }

   @Nullable
   public GuiEventListener getFocused() {
      return null;
   }

   public void setFocused(@Nullable GuiEventListener focused) {
   }

   public void setRot(float rot) {
      this.rot = rot;
   }
}
