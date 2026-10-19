package com.tno_tensura_extras.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.GlStateBackup;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL11;

final class MagicCameraHud {
   private static final int WIDTH = 224;
   private static final int HEIGHT = 192;
   private static final long TICKS_PER_DAY = 24000L;
   private static final int MINUTES_PER_DAY = 1440;
   private static final int HUD_COLOR = -4201217;
   private static final int SHADOW_COLOR = -1342175212;
   private static final int BADGE_COLOR = -536672497;
   private static final int SIGIL_COLOR = -2580225;
   private static final int SIGIL_DIM_COLOR = -11059078;
   private static final int CRYSTAL_COLOR = -6558721;
   private static final int CRYSTAL_SPENT_COLOR = -13941160;
   private static final ResourceLocation RUNE_FONT = ResourceLocation.withDefaultNamespace("alt");
   private static final Component RUNE_MANA = rune("MANA");
   private static final Component RUNE_SIGHT = rune("SIGHT");
   private static final Component RUNE_SEAL = rune("SEAL");
   private static final BufferSource HUD_BUFFER = MultiBufferSource.immediate(new ByteBufferBuilder(65536));

   private MagicCameraHud() {
   }

   static void render(Minecraft minecraft, RenderTarget target) {
      Player player = minecraft.player;
      if (minecraft.level != null && player != null) {
         RenderSystem.assertOnRenderThread();
         int previousReadFramebuffer = GlStateManager._getInteger(36010);
         int previousDrawFramebuffer = GlStateManager._getInteger(36006);
         int[] previousViewport = new int[4];
         GL11.glGetIntegerv(2978, previousViewport);
         Matrix4f previousProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
         VertexSorting previousVertexSorting = RenderSystem.getVertexSorting();
         ShaderInstance previousShader = RenderSystem.getShader();
         int previousTexture = RenderSystem.getShaderTexture(0);
         float[] previousShaderColor = (float[])RenderSystem.getShaderColor().clone();
         GlStateBackup stateBackup = new GlStateBackup();
         RenderSystem.backupGlState(stateBackup);
         Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
         boolean modelViewPushed = false;

         try {
            target.bindWrite(true);
            RenderSystem.disableScissor();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(0.0F, 224.0F, 192.0F, 0.0F, -1000.0F, 1000.0F), VertexSorting.ORTHOGRAPHIC_Z);
            modelViewStack.pushMatrix();
            modelViewPushed = true;
            modelViewStack.identity();
            RenderSystem.applyModelViewMatrix();
            GuiGraphics graphics = new GuiGraphics(minecraft, HUD_BUFFER);
            graphics.drawManaged(() -> drawHud(graphics, minecraft.font, player, minecraft.level.getDayTime(), minecraft.level.getGameTime()));
         } finally {
            if (modelViewPushed) {
               modelViewStack.popMatrix();
               RenderSystem.applyModelViewMatrix();
            }

            RenderSystem.setProjectionMatrix(previousProjection, previousVertexSorting);
            RenderSystem.setShaderColor(previousShaderColor[0], previousShaderColor[1], previousShaderColor[2], previousShaderColor[3]);
            RenderSystem.setShaderTexture(0, previousTexture);
            if (previousShader != null) {
               RenderSystem.setShader(() -> previousShader);
            }

            RenderSystem.restoreGlState(stateBackup);
            GlStateManager._glBindFramebuffer(36008, previousReadFramebuffer);
            GlStateManager._glBindFramebuffer(36009, previousDrawFramebuffer);
            GlStateManager._viewport(previousViewport[0], previousViewport[1], previousViewport[2], previousViewport[3]);
         }
      }
   }

   private static void drawHud(GuiGraphics graphics, Font font, Player player, long dayTime, long gameTime) {
      drawOuterFrame(graphics);
      drawMagicArray(graphics);
      drawTopIndicators(graphics, font, dayTime, gameTime);
      drawCoordinates(graphics, font, player.blockPosition());
   }

   private static void drawOuterFrame(GuiGraphics graphics) {
      int left = 7;
      int right = 216;
      int top = 7;
      int bottom = 184;
      int horizontalLength = 42;
      int verticalLength = 27;
      outlinedHLine(graphics, left, left + horizontalLength, top);
      outlinedVLine(graphics, left, top, top + verticalLength);
      outlinedHLine(graphics, right - horizontalLength, right, top);
      outlinedVLine(graphics, right, top, top + verticalLength);
      outlinedHLine(graphics, left, left + horizontalLength, bottom);
      outlinedVLine(graphics, left, bottom - verticalLength, bottom);
      outlinedHLine(graphics, right - horizontalLength, right, bottom);
      outlinedVLine(graphics, right, bottom - verticalLength, bottom);
      drawStud(graphics, left, top, 3);
      drawStud(graphics, right, top, 3);
      drawStud(graphics, left, bottom, 3);
      drawStud(graphics, right, bottom, 3);
   }

   private static void drawMagicArray(GuiGraphics graphics) {
      int centerX = 112;
      int centerY = 96;
      int radius = 22;
      outlinedSquare(graphics, centerX, centerY, radius);
      outlinedDiamond(graphics, centerX, centerY, radius);
      outlinedHLine(graphics, centerX - 38, centerX - 33, centerY);
      outlinedHLine(graphics, centerX + 33, centerX + 38, centerY);
      outlinedVLine(graphics, centerX, centerY - 38, centerY - 33);
      outlinedVLine(graphics, centerX, centerY + 33, centerY + 38);
      drawStud(graphics, centerX, centerY, 2);
   }

   private static void drawTopIndicators(GuiGraphics graphics, Font font, long dayTime, long gameTime) {
      graphics.fill(13, 11, 44, 25, -1342175212);
      graphics.fill(14, 12, 43, 24, -536672497);
      drawStud(graphics, 13, 18, 3);
      drawStud(graphics, 44, 18, 3);
      graphics.drawCenteredString(font, RUNE_MANA, 28, 14, -4201217);
      graphics.drawString(font, formatTime(dayTime), 14, 27, -4201217, true);
      graphics.drawString(font, RUNE_SIGHT, 112 - font.width(RUNE_SIGHT) / 2 - 8, 13, -4201217, true);
      drawMagiculeGauge(graphics, 130, 18);
      boolean lit = Math.floorMod(gameTime, 24L) < 14L;
      filledDiamond(graphics, 179, 18, 5, -1342175212);
      filledDiamond(graphics, 179, 18, 3, lit ? -2580225 : -11059078);
      graphics.drawString(font, RUNE_SEAL, 187, 13, -4201217, true);
   }

   private static void drawMagiculeGauge(GuiGraphics graphics, int x, int y) {
      int charged = 3;

      for (int i = 0; i < 4; i++) {
         int centerX = x + i * 9;
         filledDiamond(graphics, centerX, y, 4, -1342175212);
         filledDiamond(graphics, centerX, y, 3, i < charged ? -6558721 : -13941160);
      }
   }

   private static void drawCoordinates(GuiGraphics graphics, Font font, BlockPos position) {
      String coordinates = String.format(Locale.ROOT, "X:%d  Y:%d  Z:%d", position.getX(), position.getY(), position.getZ());
      int textWidth = font.width(coordinates);
      float scale = Math.min(1.0F, 196.0F / Math.max(1, textWidth));
      graphics.pose().pushPose();
      graphics.pose().translate(112.0F, 170.0F, 0.0F);
      graphics.pose().scale(scale, scale, 1.0F);
      graphics.drawString(font, coordinates, -textWidth / 2.0F, 0.0F, -4201217, true);
      graphics.pose().popPose();
   }

   private static String formatTime(long dayTime) {
      long timeOfDay = Math.floorMod(dayTime, 24000L);
      long shiftedTime = (timeOfDay + 6000L) % 24000L;
      int totalMinutes = (int)(shiftedTime * 1440L / 24000L);
      return String.format(Locale.ROOT, "%02d:%02d", totalMinutes / 60, totalMinutes % 60);
   }

   private static Component rune(String text) {
      return Component.literal(text).withStyle(style -> style.withFont(RUNE_FONT));
   }

   private static void drawStud(GuiGraphics graphics, int centerX, int centerY, int radius) {
      filledDiamond(graphics, centerX, centerY, radius + 1, -1342175212);
      filledDiamond(graphics, centerX, centerY, radius, -4201217);
   }

   private static void filledDiamond(GuiGraphics graphics, int centerX, int centerY, int radius, int color) {
      graphics.pose().pushPose();
      graphics.pose().translate(centerX + 0.5F, centerY + 0.5F, 0.0F);
      graphics.pose().mulPose(Axis.ZP.rotationDegrees(45.0F));
      graphics.fill(-radius, -radius, radius, radius, color);
      graphics.pose().popPose();
   }

   private static void outlinedSquare(GuiGraphics graphics, int centerX, int centerY, int radius) {
      outlinedHLine(graphics, centerX - radius, centerX + radius, centerY - radius);
      outlinedHLine(graphics, centerX - radius, centerX + radius, centerY + radius);
      outlinedVLine(graphics, centerX - radius, centerY - radius, centerY + radius);
      outlinedVLine(graphics, centerX + radius, centerY - radius, centerY + radius);
   }

   private static void outlinedDiamond(GuiGraphics graphics, int centerX, int centerY, int radius) {
      graphics.pose().pushPose();
      graphics.pose().translate(centerX + 0.5F, centerY + 0.5F, 0.0F);
      graphics.pose().mulPose(Axis.ZP.rotationDegrees(45.0F));
      outlinedHLine(graphics, -radius, radius, -radius);
      outlinedHLine(graphics, -radius, radius, radius);
      outlinedVLine(graphics, -radius, -radius, radius);
      outlinedVLine(graphics, radius, -radius, radius);
      graphics.pose().popPose();
   }

   private static void outlinedHLine(GuiGraphics graphics, int minX, int maxX, int y) {
      graphics.fill(minX - 1, y - 1, maxX + 2, y + 2, -1342175212);
      graphics.hLine(minX, maxX, y, -4201217);
   }

   private static void outlinedVLine(GuiGraphics graphics, int x, int minY, int maxY) {
      graphics.fill(x - 1, minY - 1, x + 2, maxY + 2, -1342175212);
      graphics.vLine(x, minY, maxY, -4201217);
   }
}
