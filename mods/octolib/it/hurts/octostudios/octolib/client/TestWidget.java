package it.hurts.octostudios.octolib.client;

import it.hurts.octostudios.octolib.client.animation.Tween;
import it.hurts.octostudios.octolib.client.animation.easing.EaseType;
import it.hurts.octostudios.octolib.client.animation.easing.TransitionType;
import it.hurts.octostudios.octolib.client.particle.GalacticUIParticle;
import it.hurts.octostudios.octolib.client.particle.UIParticle;
import it.hurts.octostudios.octolib.util.OctoColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class TestWidget extends AbstractWidget {
   protected Screen screen;
   private Vector2f position = new Vector2f();
   private Vector2f scale = new Vector2f(1.0F, 1.0F);
   private OctoColor color = new OctoColor(1.0F, 1.0F, 1.0F, 1.0F);
   public TransitionType transitionType;
   public EaseType easeType;
   public Tween tween;
   private Matrix4f renderPose = new Matrix4f();
   public Tween hoverTween;
   private boolean hasHovered = false;

   public TestWidget(int x, int y, TransitionType transitionType, EaseType easeType, Screen screen) {
      super(x, y, 8, 8, Component.literal(easeType.name() + "_" + transitionType.name()));
      this.transitionType = transitionType;
      this.easeType = easeType;
      this.screen = screen;
   }

   protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float partialTick) {
      float actualPartialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(this.position.x + this.getX(), this.position.y + this.getY(), 0.0F);
      guiGraphics.pose().translate(this.width / 2.0F, this.height / 2.0F, 0.0F);
      guiGraphics.pose().scale(this.scale.x, this.scale.y, 1.0F);
      guiGraphics.pose().translate(-this.width / 2.0F, -this.height / 2.0F, 0.0F);
      this.renderPose = new Matrix4f(guiGraphics.pose().last().pose());
      guiGraphics.renderOutline(0, 0, this.getWidth(), this.getHeight(), this.color.getARGB());
      guiGraphics.pose().popPose();
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(this.getX(), this.getY(), 0.0F);
      guiGraphics.renderOutline(-2, -12, 112, this.getHeight() + 14, 1358954495);
      guiGraphics.pose().scale(0.75F, 1.0F, 1.0F);
      guiGraphics.drawString(Minecraft.getInstance().font, this.getMessage(), 0, -10, 1358954495, true);
      guiGraphics.pose().popPose();
   }

   protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
   }

   public boolean isHovered() {
      boolean hovered = super.isHovered();
      if (hovered && !this.hasHovered) {
         this.hasHovered = true;
         if (this.hoverTween != null) {
            this.hoverTween.kill();
         }

         this.hoverTween = Tween.create().setTransitionType(TransitionType.QUART).setEase(EaseType.EASE_OUT);
         this.hoverTween.tweenProperty(this, "scale", new Vector2f(1.25F, 1.25F), 0.2);
      } else if (!hovered && this.hasHovered) {
         this.hasHovered = false;
         if (this.hoverTween != null) {
            this.hoverTween.kill();
         }

         this.hoverTween = Tween.create().setTransitionType(TransitionType.QUART).setEase(EaseType.EASE_OUT);
         this.hoverTween.tweenProperty(this, "scale", new Vector2f(1.0F, 1.0F), 0.2);
      }

      return hovered;
   }

   public void onClick(double mouseX, double mouseY) {
      if (this.tween != null) {
         this.tween.kill();
      }

      Runnable spawnParticle = () -> {
         UIParticle uiParticle = new GalacticUIParticle(20.0F, 1, this.getX() + this.position.x, this.getY() + this.position.y, UIParticle.Layer.SCREEN, 1.0F);
         uiParticle.setScreen(this.screen);
         uiParticle.instantiate();
      };
      this.tween = Tween.create().setTransitionType(this.transitionType).setEase(this.easeType).setLoops(3);
      this.tween.tweenMethod(this::setColor, OctoColor.GREEN, OctoColor.WHITE, 0.5).setTransitionType(TransitionType.LINEAR);
      this.tween.parallel().tweenProperty(this, "position", new Vector2f(10.0F, 10.0F), 0.5);
      this.tween.parallel().tweenRunnable(spawnParticle);
      this.tween.tweenInterval(0.5);
      this.tween.tweenMethod(this::setColor, OctoColor.RED, OctoColor.WHITE, 0.5).setTransitionType(TransitionType.LINEAR);
      this.tween.parallel().tweenProperty(this, "position", new Vector2f(0.0F, 0.0F), 0.5);
      this.tween.parallel().tweenRunnable(spawnParticle);
      this.tween.tweenInterval(0.5);
      super.onClick(mouseX, mouseY);
   }

   public void setColor(OctoColor color) {
      this.color = color;
   }
}
