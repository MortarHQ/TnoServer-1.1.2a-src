package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Post;

public final class IstarothScreenFx {
   private static final int LAST_SECOND_TOTAL_TICKS = 600;

   private IstarothScreenFx() {
   }

   public static void renderOverlay(Post event) {
      render(event.getGuiGraphics(), event.getPartialTick().getGameTimeDeltaPartialTick(false));
   }

   public static void render(GuiGraphics graphics, float partialTick) {
      Minecraft minecraft = Minecraft.getInstance();
      Player player = minecraft.player;
      if (player != null) {
         MobEffectInstance effect = player.getEffect(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN));
         if (effect != null) {
            int width = minecraft.getWindow().getGuiScaledWidth();
            int height = minecraft.getWindow().getGuiScaledHeight();
            int amp = Mth.clamp(effect.getAmplifier(), 0, 8);
            int duration = effect.getDuration();
            float time = player.tickCount + partialTick;
            if (amp == 8) {
               renderTemporalBanishmentVoid(graphics, width, height, time, duration);
            } else if (amp == 6) {
               renderLastSecondDoomTick(graphics, width, height, time, duration);
               if (duration <= 60) {
                  renderMirrorShatterCollapse(graphics, width, height, time, duration);
               }
            } else if (amp >= 7) {
               renderMirrorShatterCollapse(graphics, width, height, time, duration);
            } else {
               renderNormalTemporalDampen(graphics, width, height, time, amp, duration);
            }
         }
      }
   }

   private static void renderNormalTemporalDampen(GuiGraphics graphics, int width, int height, float time, int amp, int duration) {
      float pulse = wave(time, 0.22F);
      int black = alpha(12.0F + amp * 9.0F);
      graphics.fill(0, 0, width, height, black << 24);
      int cx = width / 2;
      int cy = height / 2;
      int radius = 28 + amp * 11 + (int)(pulse * 5.0F);
      drawClockRing(graphics, cx, cy, radius, 2013265919, 8, 1);
      drawClockRing(graphics, cx, cy, radius + 16, 1440270735, 12, 1);
      double a1 = time * 0.04;
      double a2 = -time * 0.11;
      drawLine(graphics, cx, cy, cx + (int)(Math.cos(a1) * radius * 0.8), cy + (int)(Math.sin(a1) * radius * 0.8), -1426063361, 1);
      drawLine(graphics, cx, cy, cx + (int)(Math.cos(a2) * radius * 0.55), cy + (int)(Math.sin(a2) * radius * 0.55), -1713845873, 1);
      if (duration < 8) {
         int flash = alpha((8.0F - duration) * 12.0F);
         graphics.fill(0, 0, width, height, flash << 24 | 16777215);
      }
   }

   private static void renderTemporalBanishmentVoid(GuiGraphics graphics, int width, int height, float time, int duration) {
      float pulse = wave(time, 0.95F);
      float fast = wave(time, 3.8F);
      float slow = wave(time, 0.17F);
      float ending = duration <= 40 ? Mth.clamp(1.0F - duration / 40.0F, 0.0F, 1.0F) : 0.0F;
      int cx = width / 2;
      int cy = height / 2;
      int baseDark = alpha(120.0F + pulse * 45.0F + ending * 75.0F);
      graphics.fill(0, 0, width, height, baseDark << 24);
      renderVoidChromaticPanels(graphics, width, height, time, pulse, ending);
      renderTimeEraseRings(graphics, cx, cy, width, height, time, pulse, ending);
      renderBanishmentGhostFrames(graphics, width, height, time, slow, ending);
      renderVoidThreads(graphics, cx, cy, width, height, time, fast, ending);
      renderTemporalNoise(graphics, width, height, time, ending);
      int eyeRadius = 42 + (int)(pulse * 15.0F) + (int)(ending * 65.0F);
      drawBrokenClockRing(graphics, cx, cy, eyeRadius, -570425345, time * 2.1F, 14);
      drawBrokenClockRing(graphics, cx, cy, eyeRadius + 34, -1716152577, -time * 1.6F, 18);
      drawBrokenClockRing(graphics, cx, cy, eyeRadius + 76, 2010696079, time * 1.2F, 22);
      double angle = -time * 0.23;
      double reverse = time * 0.61;
      drawLine(graphics, cx, cy, cx + (int)(Math.cos(angle) * eyeRadius * 1.35), cy + (int)(Math.sin(angle) * eyeRadius * 1.35), -855638017, 2);
      drawLine(graphics, cx, cy, cx + (int)(Math.cos(reverse) * eyeRadius * 0.72), cy + (int)(Math.sin(reverse) * eyeRadius * 0.72), -860514561, 2);
      int slashAlpha = alpha(100.0F + fast * 95.0F + ending * 55.0F);

      for (int i = 0; i < 16; i++) {
         double a = Math.toRadians(i * 22.5 + time * 8.0F);
         int x1 = cx + (int)(Math.cos(a) * 18.0);
         int y1 = cy + (int)(Math.sin(a) * 18.0);
         int x2 = cx + (int)(Math.cos(a + Math.sin(time * 0.04F + i) * 0.18) * Math.max(width, height));
         int y2 = cy + (int)(Math.sin(a + Math.cos(time * 0.04F + i) * 0.18) * Math.max(width, height));
         drawLine(graphics, x1, y1, x2, y2, slashAlpha << 24 | (i % 3 == 0 ? 11900671 : 16777215), i % 4 == 0 ? 3 : 1);
      }

      int crush = alpha(65.0F + ending * 160.0F + fast * 45.0F);
      int side = width / 16 + (int)(ending * width / 3.0F) + (int)(fast * 8.0F);
      int top = height / 16 + (int)(ending * height / 3.0F) + (int)(pulse * 8.0F);
      graphics.fill(0, 0, width, top, crush << 24);
      graphics.fill(0, height - top, width, height, crush << 24);
      graphics.fill(0, 0, side, height, crush << 24);
      graphics.fill(width - side, 0, width, height, crush << 24);
      if (duration <= 24) {
         int flash = alpha((24.0F - duration) * 8.0F + fast * 55.0F);
         graphics.fill(0, 0, width, height, flash << 24 | 13154047);
      }
   }

   private static void renderVoidChromaticPanels(GuiGraphics graphics, int width, int height, float time, float pulse, float ending) {
      int bands = 11;
      int bandH = height / bands + 2;

      for (int i = 0; i < bands; i++) {
         int offset = (int)(Math.sin(time * 0.09F + i * 1.8F) * (10.0F + ending * 26.0F));
         int alpha = alpha(14.0F + pulse * 28.0F + ending * 38.0F);
         int color = i % 3 == 0 ? 5057681 : (i % 3 == 1 ? 16777215 : 14207375);
         graphics.fill(offset, i * bandH, width + offset, i * bandH + bandH / 2, alpha << 24 | color);
      }
   }

   private static void renderTimeEraseRings(GuiGraphics graphics, int cx, int cy, int width, int height, float time, float pulse, float ending) {
      int max = Math.max(width, height);

      for (int i = 0; i < 7; i++) {
         int radius = 36 + i * 32 + (int)(pulse * 8.0F) + (int)(ending * i * 14.0F);
         int color = i % 2 == 0 ? -1426063361 : -2001365249;
         drawBrokenClockRing(graphics, cx, cy, radius, color, time * (i % 2 == 0 ? 1.0F : -1.0F) + i * 17.0F, 8 + i * 2);
         if (i % 2 == 0) {
            double a = time * 0.05 + i;
            drawLine(graphics, cx, cy, cx + (int)(Math.cos(a) * Math.min(radius, max)), cy + (int)(Math.sin(a) * Math.min(radius, max)), 1442840575, 1);
         }
      }
   }

   private static void renderBanishmentGhostFrames(GuiGraphics graphics, int width, int height, float time, float slow, float ending) {
      int cx = width / 2;
      int cy = height / 2;

      for (int i = 0; i < 9; i++) {
         float phase = time * 0.05F + i * 0.77F;
         int w = 18 + i * 10 + (int)(slow * 7.0F);
         int h = 34 + i * 14;
         int x = cx + (int)(Math.sin(phase) * (18.0F + ending * 32.0F)) - w / 2;
         int y = cy + (int)(Math.cos(phase * 0.8F) * (12.0F + ending * 20.0F)) - h / 2;
         int alpha = alpha(28.0F + i * 7.0F + ending * 22.0F);
         int color = i % 2 == 0 ? 16777215 : 11900671;
         graphics.fill(x, y, x + w, y + 1, alpha << 24 | color);
         graphics.fill(x, y + h, x + w, y + h + 1, alpha << 24 | color);
         graphics.fill(x, y, x + 1, y + h, alpha << 24 | color);
         graphics.fill(x + w, y, x + w + 1, y + h, alpha << 24 | color);
      }
   }

   private static void renderVoidThreads(GuiGraphics graphics, int cx, int cy, int width, int height, float time, float fast, float ending) {
      int amount = 38;
      int radius = Math.max(width, height);
      int alpha = alpha(70.0F + fast * 80.0F + ending * 60.0F);

      for (int i = 0; i < amount; i++) {
         double a = Math.toRadians(i * 137.5 + time * 3.5F);
         double bend = Math.sin(time * 0.07F + i) * 0.42;
         int x1 = cx + (int)(Math.cos(a) * 20.0);
         int y1 = cy + (int)(Math.sin(a) * 20.0);
         int x2 = cx + (int)(Math.cos(a + bend) * radius);
         int y2 = cy + (int)(Math.sin(a - bend) * radius);
         drawLine(graphics, x1, y1, x2, y2, alpha << 24 | (i % 4 == 0 ? 14207375 : 16777215), i % 7 == 0 ? 2 : 1);
      }
   }

   private static void renderTemporalNoise(GuiGraphics graphics, int width, int height, float time, float ending) {
      int rows = 18;

      for (int i = 0; i < rows; i++) {
         int y = (int)((i * 97 + time * (7 + i % 5)) % Math.max(1, height));
         int x1 = (int)(Math.sin(time * 0.13F + i) * 18.0);
         int x2 = width + (int)(Math.cos(time * 0.11F + i) * 18.0);
         int alpha = alpha(18.0F + ending * 55.0F + i % 3 * 12.0F);
         graphics.fill(x1, y, x2, y + 1 + i % 2, alpha << 24 | (i % 2 == 0 ? 16777215 : 11900671));
      }
   }

   private static void renderLastSecondDoomTick(GuiGraphics graphics, int width, int height, float time, int duration) {
      float tickPulse = wave(time, 1.25F);
      float tremble = wave(time, 3.7F);
      int remaining = Math.max(0, duration);
      int seconds = Math.max(0, (int)Math.ceil(remaining / 20.0));
      float progress = Mth.clamp(1.0F - remaining / 600.0F, 0.0F, 1.0F);
      int vignette = alpha(28.0F + progress * 80.0F + tickPulse * 22.0F);
      graphics.fill(0, 0, width, height / 10, vignette << 24);
      graphics.fill(0, height - height / 10, width, height, vignette << 24);
      graphics.fill(0, 0, width / 18, height, vignette << 24);
      graphics.fill(width - width / 18, 0, width, height, vignette << 24);
      int clockX = width - 58;
      int clockY = 48;
      int radius = 27 + (int)(tickPulse * 2.0F) + (seconds <= 5 ? (int)(tremble * 3.0F) : 0);
      drawClockRing(graphics, clockX, clockY, radius + 8, -1428633201, 8, 1);
      drawClockRing(graphics, clockX, clockY, radius, -285212673, 6, 1);
      drawClockRing(graphics, clockX, clockY, radius - 9, 2013265919, 12, 1);
      double doomAngle = (-Math.PI / 2) + progress * Math.PI * 2.0;
      double secondAngle = -time * 0.55;
      drawLine(
         graphics,
         clockX,
         clockY,
         clockX + (int)(Math.cos(doomAngle) * radius * 0.86),
         clockY + (int)(Math.sin(doomAngle) * radius * 0.86),
         seconds <= 5 ? -12182 : -3656,
         2
      );
      drawLine(
         graphics, clockX, clockY, clockX + (int)(Math.cos(secondAngle) * radius * 0.62), clockY + (int)(Math.sin(secondAngle) * radius * 0.62), -570425345, 1
      );
      graphics.fill(clockX - 3, clockY - 3, clockX + 4, clockY + 4, -1);
      int numberColor = seconds <= 3 ? -12182 : (seconds <= 10 ? -3656 : -268435457);
      drawSevenSegNumber(graphics, width - 42, 83, seconds, numberColor, 2);
      int cx = width / 2;
      int cy = height / 2;
      if (duration <= 100) {
         int warningAlpha = alpha((100.0F - duration) * 1.6F + tremble * 50.0F);
         drawBrokenClockRing(graphics, cx, cy, 72 + (int)(tremble * 12.0F), warningAlpha << 24 | 16777215, time, 2);
      }

      if (duration <= 60) {
         int lineAlpha = alpha((60.0F - duration) * 2.8F);
         drawLine(graphics, cx, 0, cx, height, lineAlpha << 24 | 14207375, 1);
         drawLine(graphics, 0, cy, width, cy, lineAlpha << 24 | 16777215, 1);
      }

      if (duration <= 20) {
         int crush = alpha((20.0F - duration) * 8.0F);
         graphics.fill(0, 0, width, height, crush << 24);
      }
   }

   private static void renderMirrorShatterCollapse(GuiGraphics graphics, int width, int height, float time, int duration) {
      float violent = wave(time, 4.6F);
      float fast = wave(time, 2.1F);
      float impact = duration > 60 ? 0.0F : Mth.clamp(1.0F - duration / 60.0F, 0.0F, 1.0F);
      if (impact <= 0.02F) {
         impact = 0.35F;
      }

      int cx = width / 2;
      int cy = height / 2;
      int dark = alpha(60.0F + impact * 95.0F + fast * 35.0F);
      graphics.fill(0, 0, width, height, dark << 24);
      renderHardMirrorPanels(graphics, width, height, time, impact);
      renderHardGlassShards(graphics, width, height, time, impact);
      renderHardCrackWeb(graphics, cx, cy, width, height, time, impact);
      renderHardClockFracture(graphics, cx, cy, width, height, time, impact);
      int crossAlpha = alpha(170.0F + impact * 80.0F);
      drawLine(graphics, 0, cy, width, cy, crossAlpha << 24 | 16777215, 4);
      drawLine(graphics, cx, 0, cx, height, crossAlpha << 24 | 16777215, 4);

      for (int i = 0; i < 42; i++) {
         double angle = Math.toRadians(i * 8.571 + time * 11.0F);
         int x1 = cx + (int)(Math.cos(angle) * 10.0);
         int y1 = cy + (int)(Math.sin(angle) * 10.0);
         int x2 = cx + (int)(Math.cos(angle) * Math.max(width, height));
         int y2 = cy + (int)(Math.sin(angle) * Math.max(width, height));
         int color = i % 2 == 0 ? -855638017 : -1713845873;
         drawLine(graphics, x1, y1, x2, y2, color, i % 4 == 0 ? 3 : 1);
      }

      int crushAlpha = alpha(100.0F + impact * 125.0F + violent * 40.0F);
      int crushX = width / 13 + (int)(impact * width / 4.2F) + (int)(violent * width / 20.0F);
      int crushY = height / 13 + (int)(impact * height / 4.2F) + (int)(violent * height / 20.0F);
      graphics.fill(0, 0, width, crushY, crushAlpha << 24);
      graphics.fill(0, height - crushY, width, height, crushAlpha << 24);
      graphics.fill(0, 0, crushX, height, crushAlpha << 24);
      graphics.fill(width - crushX, 0, width, height, crushAlpha << 24);
      if (duration <= 40) {
         int blastAlpha = alpha((40.0F - duration) * 7.5F + violent * 90.0F);
         drawExplosionStar(graphics, cx, cy, Math.max(width, height), blastAlpha << 24 | 16777215, time);
      }

      if (duration <= 24) {
         int flash = alpha((24.0F - duration) * 9.0F + violent * 90.0F);
         graphics.fill(0, 0, width, height, flash << 24 | 16777215);
      }

      if (duration <= 8) {
         int finalBlack = alpha((8.0F - duration) * 28.0F);
         graphics.fill(0, 0, width, height, finalBlack << 24);
      }
   }

   private static void renderHardMirrorPanels(GuiGraphics graphics, int width, int height, float time, float impact) {
      int rows = 6;
      int cols = 8;
      int panelW = width / cols + 3;
      int panelH = height / rows + 3;

      for (int y = 0; y < rows; y++) {
         for (int x = 0; x < cols; x++) {
            float flicker = wave(time + x * 7.13F + y * 3.7F, 1.4F);
            int alpha = alpha(18.0F + flicker * 50.0F + impact * 55.0F);
            int ox = (int)(Math.sin(time * 0.16F + x * 1.4F) * impact * 9.0);
            int oy = (int)(Math.cos(time * 0.15F + y * 1.9F) * impact * 8.0);
            int x1 = x * panelW + ox;
            int y1 = y * panelH + oy;
            int x2 = x1 + panelW - 4;
            int y2 = y1 + panelH - 4;
            int tint = (x + y) % 2 == 0 ? 16777215 : 13228287;
            graphics.fill(x1, y1, x2, y2, alpha << 24 | tint);
            drawLine(graphics, x1, y1, x2, y1, 1728053247, 1);
            drawLine(graphics, x1, y2, x2, y2, 1157627903, 1);
            drawLine(graphics, x1, y1, x1, y2, 1157627903, 1);
            drawLine(graphics, x2, y1, x2, y2, 1157627903, 1);
         }
      }
   }

   private static void renderHardGlassShards(GuiGraphics graphics, int width, int height, float time, float impact) {
      int cx = width / 2;
      int cy = height / 2;

      for (int i = 0; i < 70; i++) {
         double angle = Math.toRadians(i * 137.5 + time * 5.2);
         double base = 22.0 + i % 11 * 15.0;
         double fly = impact * (45.0 + i % 13 * 12.0);
         int x = cx + (int)(Math.cos(angle) * (base + fly));
         int y = cy + (int)(Math.sin(angle) * (base + fly * 0.75));
         int size = 4 + i % 7;
         int alpha = alpha(95.0F + impact * 130.0F + wave(time + i, 2.6F) * 40.0F);
         int color = i % 3 == 0 ? 16777215 : (i % 3 == 1 ? 14207375 : 11520255);
         drawShard(graphics, x, y, size, alpha << 24 | color, i % 4);
      }
   }

   private static void renderHardCrackWeb(GuiGraphics graphics, int cx, int cy, int width, int height, float time, float impact) {
      int branches = 26;
      int alpha = alpha(190.0F + impact * 65.0F);

      for (int i = 0; i < branches; i++) {
         double angle = Math.toRadians(i * (360.0 / branches) + Math.sin(time * 0.08F + i) * 10.0);
         int length = (int)(Math.max(width, height) * (0.35 + i % 6 * 0.055 + impact * 0.26));
         int x2 = cx + (int)(Math.cos(angle) * length);
         int y2 = cy + (int)(Math.sin(angle) * length);
         drawLine(graphics, cx, cy, x2, y2, alpha << 24 | 16777215, i % 3 == 0 ? 3 : 2);
         int splits = 3 + i % 4;

         for (int s = 0; s < splits; s++) {
            double p = 0.26 + s * 0.18;
            int bx = cx + (int)((x2 - cx) * p);
            int by = cy + (int)((y2 - cy) * p);
            double side = angle + (i % 2 == 0 ? 1.0 : -1.0) * (0.42 + s * 0.14);
            int sl = (int)(length * (0.12 + s * 0.035));
            int ex = bx + (int)(Math.cos(side) * sl);
            int ey = by + (int)(Math.sin(side) * sl);
            drawLine(graphics, bx, by, ex, ey, -1426063361, 1);
         }
      }
   }

   private static void renderHardClockFracture(GuiGraphics graphics, int cx, int cy, int width, int height, float time, float impact) {
      int radius = 80 + (int)(impact * 90.0F);
      drawBrokenClockRing(graphics, cx, cy, radius, -1, time * 2.4F, 10);
      drawBrokenClockRing(graphics, cx, cy, radius + 46, -858207857, -time * 1.8F, 10);
      drawBrokenClockRing(graphics, cx, cy, radius + 108, -1711276033, time, 10);
      double a1 = (-Math.PI / 2) + impact * Math.PI * 2.0 + Math.sin(time * 0.25F) * 0.25;
      double a2 = -time * 0.42;
      drawLine(graphics, cx, cy, cx + (int)(Math.cos(a1) * radius * 1.05), cy + (int)(Math.sin(a1) * radius * 1.05), -1, 5);
      drawLine(graphics, cx, cy, cx + (int)(Math.cos(a2) * radius * 0.62), cy + (int)(Math.sin(a2) * radius * 0.62), -8032, 3);
      graphics.fill(cx - 6, cy - 6, cx + 7, cy + 7, -1);
   }

   private static void drawExplosionStar(GuiGraphics graphics, int cx, int cy, int radius, int color, float time) {
      for (int i = 0; i < 34; i++) {
         double angle = Math.toRadians(i * 10.588 + time * 11.0F);
         int r1 = 8 + i % 4 * 5;
         int x1 = cx + (int)(Math.cos(angle) * r1);
         int y1 = cy + (int)(Math.sin(angle) * r1);
         int x2 = cx + (int)(Math.cos(angle) * radius);
         int y2 = cy + (int)(Math.sin(angle) * radius);
         drawLine(graphics, x1, y1, x2, y2, color, i % 3 == 0 ? 5 : 2);
      }
   }

   private static void drawShard(GuiGraphics graphics, int x, int y, int size, int color, int variant) {
      if (variant == 0) {
         drawLine(graphics, x, y - size, x + size, y + size, color, 1);
         drawLine(graphics, x + size, y + size, x - size, y + size / 2, color, 1);
         drawLine(graphics, x - size, y + size / 2, x, y - size, color, 1);
      } else if (variant == 1) {
         drawLine(graphics, x - size, y - size, x + size, y, color, 1);
         drawLine(graphics, x + size, y, x, y + size, color, 1);
         drawLine(graphics, x, y + size, x - size, y - size, color, 1);
      } else if (variant == 2) {
         drawLine(graphics, x, y - size, x + size, y, color, 1);
         drawLine(graphics, x + size, y, x, y + size, color, 1);
         drawLine(graphics, x, y + size, x - size, y, color, 1);
         drawLine(graphics, x - size, y, x, y - size, color, 1);
      } else {
         graphics.fill(x - size, y - 1, x + size, y + 1, color);
         graphics.fill(x - 1, y - size, x + 1, y + size, color);
      }
   }

   private static void drawClockRing(GuiGraphics graphics, int cx, int cy, int radius, int color, int step, int thickness) {
      int i = 0;

      while (i < 360) {
         double rad = Math.toRadians(i);
         int x = cx + (int)(Math.cos(rad) * radius);
         int y = cy + (int)(Math.sin(rad) * radius);
         graphics.fill(x - thickness, y - thickness, x + thickness, y + thickness, color);
         i += step;
      }

      for (int ix = 0; ix < 12; ix++) {
         double rad = Math.toRadians(ix * 30);
         int x1 = cx + (int)(Math.cos(rad) * (radius - 6));
         int y1 = cy + (int)(Math.sin(rad) * (radius - 6));
         int x2 = cx + (int)(Math.cos(rad) * (radius + 8));
         int y2 = cy + (int)(Math.sin(rad) * (radius + 8));
         drawLine(graphics, x1, y1, x2, y2, color, thickness);
      }
   }

   private static void drawBrokenClockRing(GuiGraphics graphics, int cx, int cy, int radius, int color, float time, int amp) {
      for (int i = 0; i < 360; i += 6) {
         if ((i / 18 + (int)(time * 0.22F)) % 4 != 0) {
            double rad = Math.toRadians(i + Mth.sin(time * 0.06F + i) * amp);
            int x = cx + (int)(Math.cos(rad) * radius);
            int y = cy + (int)(Math.sin(rad) * radius);
            graphics.fill(x - 1, y - 1, x + 3, y + 3, color);
         }
      }
   }

   private static void drawSevenSegNumber(GuiGraphics graphics, int x, int y, int value, int color, int scale) {
      value = Mth.clamp(value, 0, 99);
      int tens = value / 10;
      int ones = value % 10;
      if (tens > 0) {
         drawDigit(graphics, x - 12 * scale, y, tens, color, scale);
      }

      drawDigit(graphics, x, y, ones, color, scale);
   }

   private static void drawDigit(GuiGraphics graphics, int x, int y, int digit, int color, int s) {
      boolean[] seg = switch (digit) {
         case 0 -> new boolean[]{true, true, true, true, true, true, false};
         case 1 -> new boolean[]{false, true, true, false, false, false, false};
         case 2 -> new boolean[]{true, true, false, true, true, false, true};
         case 3 -> new boolean[]{true, true, true, true, false, false, true};
         case 4 -> new boolean[]{false, true, true, false, false, true, true};
         case 5 -> new boolean[]{true, false, true, true, false, true, true};
         case 6 -> new boolean[]{true, false, true, true, true, true, true};
         case 7 -> new boolean[]{true, true, true, false, false, false, false};
         case 8 -> new boolean[]{true, true, true, true, true, true, true};
         case 9 -> new boolean[]{true, true, true, true, false, true, true};
         default -> new boolean[]{false, false, false, false, false, false, false};
      };
      int w = 7 * s;
      int h = 12 * s;
      int t = s;
      if (seg[0]) {
         graphics.fill(x, y, x + w, y + t, color);
      }

      if (seg[1]) {
         graphics.fill(x + w - t, y, x + w, y + h / 2, color);
      }

      if (seg[2]) {
         graphics.fill(x + w - t, y + h / 2, x + w, y + h, color);
      }

      if (seg[3]) {
         graphics.fill(x, y + h - t, x + w, y + h, color);
      }

      if (seg[4]) {
         graphics.fill(x, y + h / 2, x + t, y + h, color);
      }

      if (seg[5]) {
         graphics.fill(x, y, x + t, y + h / 2, color);
      }

      if (seg[6]) {
         graphics.fill(x, y + h / 2 - t / 2, x + w, y + h / 2 + t / 2 + 1, color);
      }
   }

   private static void drawLine(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color, int thickness) {
      int dx = Math.abs(x2 - x1);
      int dy = Math.abs(y2 - y1);
      int sx = x1 < x2 ? 1 : -1;
      int sy = y1 < y2 ? 1 : -1;
      int err = dx - dy;
      int x = x1;
      int y = y1;

      while (true) {
         graphics.fill(x - thickness / 2, y - thickness / 2, x + 1 + thickness / 2, y + 1 + thickness / 2, color);
         if (x == x2 && y == y2) {
            return;
         }

         int e2 = err * 2;
         if (e2 > -dy) {
            err -= dy;
            x += sx;
         }

         if (e2 < dx) {
            err += dx;
            y += sy;
         }
      }
   }

   private static float wave(float time, float speed) {
      return Mth.sin(time * speed) * 0.5F + 0.5F;
   }

   private static int alpha(float value) {
      return Mth.clamp((int)value, 0, 255);
   }
}
