package cc.sighs.oelib.bless.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractShaderOverlay {
   private final long showDuration;
   private final long fadeDuration;
   private final long slideDuration;
   private long startTime;
   private boolean active;

   protected AbstractShaderOverlay(long showDuration, long fadeDuration, long slideDuration) {
      this.showDuration = showDuration;
      this.fadeDuration = fadeDuration;
      this.slideDuration = slideDuration;
   }

   public boolean isActive() {
      return this.active;
   }

   public void show() {
      this.active = true;
      this.startTime = Util.getMillis();
      Minecraft minecraft = Minecraft.getInstance();
      this.onShow(minecraft);
   }

   public void render(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, int width, int height) {
      if (this.active) {
         ShaderInstance shader = this.getShader();
         if (shader != null) {
            AbstractShaderOverlay.AnimationState animationState = this.calculateAnimationState(width, height);
            if (!animationState.shouldRender()) {
               this.active = false;
               Minecraft minecraft = Minecraft.getInstance();
               this.onHide(minecraft);
            } else {
               this.renderShaderBackground(guiGraphics, shader, animationState, mouseX, mouseY, width, height);
               this.renderTextContent(guiGraphics, animationState);
            }
         }
      }
   }

   private AbstractShaderOverlay.AnimationState calculateAnimationState(int screenWidth, int screenHeight) {
      long now = Util.getMillis();
      long visibleTime = Math.max(now - this.startTime, 0L);
      boolean leaving = visibleTime >= this.showDuration;
      long fadeTime = leaving ? visibleTime - this.showDuration : visibleTime;
      float fade = Mth.clamp((float)fadeTime / (float)this.fadeDuration, 0.0F, 1.0F);
      float alpha = leaving ? 1.0F - fade : fade;
      if (leaving && alpha <= 0.0F) {
         return new AbstractShaderOverlay.AnimationState(false, 0.0F, 0.0F, 0.0F, 0, 0, 0L);
      }

      int overlayWidth = this.overlayWidth();
      int overlayHeight = this.overlayHeight();
      float baseX = this.baseX(screenWidth, overlayWidth);
      float baseY = this.baseY(screenHeight, overlayHeight);
      float slideT = this.calculateSlideT(visibleTime);
      float slideOffset = (1.0F - slideT) * overlayWidth;
      float x = baseX + slideOffset;
      float y = baseY;
      return new AbstractShaderOverlay.AnimationState(true, x, y, alpha, overlayWidth, overlayHeight, visibleTime);
   }

   private float calculateSlideT(long visibleTime) {
      if (visibleTime <= this.slideDuration) {
         return (float)visibleTime / (float)this.slideDuration;
      } else if (visibleTime >= this.showDuration) {
         long leaveElapsed = Math.min(visibleTime - this.showDuration, this.slideDuration);
         return 1.0F - (float)leaveElapsed / (float)this.slideDuration;
      } else {
         return 1.0F;
      }
   }

   private void renderShaderBackground(
      GuiGraphics guiGraphics, ShaderInstance shader, AbstractShaderOverlay.AnimationState state, int mouseX, int mouseY, int screenWidth, int screenHeight
   ) {
      float timeSeconds = (float)(Util.getMillis() % 100000L) / 1000.0F;
      guiGraphics.flush();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShader(this::getShader);
      PoseStack pose = guiGraphics.pose();
      pose.pushPose();
      pose.translate(state.x, state.y, 0.0F);
      Matrix4f currentPose = pose.last().pose();
      this.applyUniforms(shader, timeSeconds, state.alpha, state.overlayWidth, state.overlayHeight, mouseX, mouseY, screenWidth, screenHeight);
      Tesselator tesselator = Tesselator.getInstance();
      BufferBuilder bufferBuilder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      bufferBuilder.addVertex(currentPose, 0.0F, 0.0F, 0.0F).setUv(0.0F, 0.0F);
      bufferBuilder.addVertex(currentPose, 0.0F, state.overlayHeight, 0.0F).setUv(0.0F, 1.0F);
      bufferBuilder.addVertex(currentPose, state.overlayWidth, state.overlayHeight, 0.0F).setUv(1.0F, 1.0F);
      bufferBuilder.addVertex(currentPose, state.overlayWidth, 0.0F, 0.0F).setUv(1.0F, 0.0F);
      BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
      pose.popPose();
   }

   private void renderTextContent(GuiGraphics guiGraphics, AbstractShaderOverlay.AnimationState state) {
      Minecraft minecraft = Minecraft.getInstance();
      Font font = minecraft.font;
      Component[] lines = this.textLines(minecraft);
      if (lines.length > 0) {
         int color = this.textColor();
         float centerX = state.x + state.overlayWidth / 2.0F;
         float innerWidth = state.overlayWidth - this.horizontalPadding() * 2.0F;
         if (innerWidth < 0.0F) {
            innerWidth = 0.0F;
         }

         float titleScale = this.titleScale();
         float bodyScale = this.bodyScale();
         float currentY = state.y + 8.0F;
         Component title = lines[0];
         this.drawScaledCenteredString(guiGraphics, font, title, centerX, currentY, color, (int)innerWidth, titleScale);
         currentY += 9.0F * titleScale + 2.0F;
         if (this.hasSeparator() && lines.length > 1) {
            float factor = Mth.clamp(this.separatorLengthFactor(), 0.0F, 1.0F);
            float length = innerWidth * factor;
            float half = length / 2.0F;
            int lineY = Mth.floor(currentY + 9.0F * bodyScale / 2.0F);
            int startX = Mth.floor(centerX - half);
            int endX = Mth.floor(centerX + half);
            guiGraphics.fill(startX, lineY, endX, lineY + 1, color);
            currentY += 9.0F * bodyScale + 2.0F;
         }

         for (int i = 1; i < lines.length; i++) {
            this.drawScaledCenteredString(guiGraphics, font, lines[i], centerX, currentY, color, (int)innerWidth, bodyScale);
            currentY += 9.0F * bodyScale + 2.0F;
         }
      }
   }

   protected float baseX(int screenWidth, int overlayWidth) {
      return (float)screenWidth - overlayWidth - 10.0F;
   }

   protected float baseY(int screenHeight, int overlayHeight) {
      return 10.0F;
   }

   protected void onShow(Minecraft minecraft) {
   }

   protected void onHide(Minecraft minecraft) {
   }

   protected Component[] textLines(Minecraft minecraft) {
      return new Component[0];
   }

   protected int textColor() {
      return -465227;
   }

   protected float horizontalPadding() {
      return 16.0F;
   }

   protected boolean hasSeparator() {
      return false;
   }

   protected float separatorLengthFactor() {
      return 0.6F;
   }

   protected float titleScale() {
      return 1.0F;
   }

   protected float bodyScale() {
      return 0.78F;
   }

   public boolean isChineseFestival() {
      return false;
   }

   public abstract String festivalId();

   public abstract String festivalName();

   private void drawScaledCenteredString(GuiGraphics guiGraphics, Font font, Component text, float centerX, float y, int color, int maxWidth, float baseScale) {
      PoseStack pose = guiGraphics.pose();
      pose.pushPose();
      pose.translate(centerX, y, 0.0F);
      pose.scale(baseScale, baseScale, 1.0F);
      guiGraphics.drawCenteredString(font, text, 0, 0, color);
      pose.popPose();
   }

   protected abstract ShaderInstance getShader();

   protected abstract int overlayWidth();

   protected abstract int overlayHeight();

   protected abstract void applyUniforms(ShaderInstance var1, float var2, float var3, int var4, int var5, int var6, int var7, int var8, int var9);

   private record AnimationState(boolean shouldRender, float x, float y, float alpha, int overlayWidth, int overlayHeight, long visibleTime) {
   }
}
