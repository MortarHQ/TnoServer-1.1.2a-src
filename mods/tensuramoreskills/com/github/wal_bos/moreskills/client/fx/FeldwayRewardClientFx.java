package com.github.wal_bos.moreskills.client.fx;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public final class FeldwayRewardClientFx {
   private FeldwayRewardClientFx() {
   }

   public static void renderBackground(GuiGraphics graphics, int width, int height, float time) {
      graphics.fill(0, 0, width, height, -101390385);
      graphics.fill(0, 0, width, height / 2, 1291844070);
      graphics.fill(0, height / 2, width, height, 586078370);
      renderHolyVignette(graphics, width, height, time);
      renderSoftColumns(graphics, width, height, time);
      renderAngelicCircle(graphics, width / 2, 74, 138, time);
      renderSunRays(graphics, width / 2, 74, width, height, time);
      renderOrbitingLights(graphics, width / 2, height / 2 + 8, time);
      renderFeatherFall(graphics, width, height, time);
      renderDust(graphics, width, height, time);
   }

   public static void renderForeground(GuiGraphics graphics, int width, int height, float time) {
      renderFloatingCrosses(graphics, width, height, time);
      renderWingFlares(graphics, width, height, time);
      renderEdgeGlow(graphics, width, height, time);
   }

   private static void renderHolyVignette(GuiGraphics graphics, int width, int height, float time) {
      float pulse = 0.5F + 0.5F * Mth.sin(time * 0.035F);
      int cx = width / 2;
      int cy = height / 2;
      int w = (int)(width * (0.28F + pulse * 0.06F));
      int h = (int)(height * (0.23F + pulse * 0.05F));
      graphics.fill(cx - w, cy - h, cx + w, cy + h, 587202559);
      graphics.fill(cx - w / 2, cy - h / 2, cx + w / 2, cy + h / 2, 587199688);
      graphics.fill(0, 0, width, 8, 587202559);
      graphics.fill(0, height - 8, width, height, 583771738);
   }

   private static void renderSoftColumns(GuiGraphics graphics, int width, int height, float time) {
      int cols = 7;

      for (int i = 0; i < cols; i++) {
         float pulse = 0.5F + 0.5F * Mth.sin(time * 0.04F + i * 0.7F);
         int x = width / 10 + i * width / 7;
         int alpha = 15 + (int)(pulse * 20.0F);
         graphics.fill(x - 18, 0, x + 18, height, alpha << 24 | 16773554);
         graphics.fill(x - 5, 0, x + 5, height, alpha + 20 << 24 | 16777215);
         graphics.fill(x - 1, 0, x + 1, height, alpha + 32 << 24 | 16771240);
      }
   }

   private static void renderAngelicCircle(GuiGraphics graphics, int cx, int cy, int radius, float time) {
      for (int i = 0; i < 6; i++) {
         int r = radius - i * 15;
         int color = 35 - i * 4 << 24 | 16774354;
         drawRectRing(graphics, cx, cy, r, (int)(r * 0.55F), color);
      }

      for (int i = 0; i < 18; i++) {
         float angle = time * 0.021F + i * (float) (Math.PI / 9);
         int x = cx + (int)(Math.cos(angle) * (radius + 13));
         int y = cy + (int)(Math.sin(angle) * (radius * 0.55F + 4.0F));
         graphics.fill(x - 3, y - 3, x + 3, y + 3, 1560277432);
         graphics.fill(x - 8, y, x + 8, y + 1, 1073741823);
         graphics.fill(x, y - 8, x + 1, y + 8, 1073741823);
      }

      graphics.fill(cx - 1, cy - radius / 2 - 28, cx + 1, cy + radius / 2 + 28, 1728049592);
      graphics.fill(cx - radius - 26, cy, cx + radius + 26, cy + 1, 1442840575);

      for (int i = 0; i < 8; i++) {
         int y = cy - 48 + i * 14;
         int half = radius - i * 6;
         graphics.hLine(cx - half, cx + half, y, 419420536);
      }

      drawLine(graphics, cx - 102, cy - 9, cx, cy - 52, 1157618040);
      drawLine(graphics, cx + 102, cy - 9, cx, cy - 52, 1157618040);
      drawLine(graphics, cx - 102, cy + 9, cx, cy + 52, 822073720);
      drawLine(graphics, cx + 102, cy + 9, cx, cy + 52, 822073720);
   }

   private static void renderSunRays(GuiGraphics graphics, int cx, int cy, int width, int height, float time) {
      for (int i = 0; i < 28; i++) {
         float angle = time * 0.009F + i * (float) (Math.PI / 14);
         float len = 170.0F + i % 5 * 30.0F;
         int x2 = cx + (int)(Math.cos(angle) * len);
         int y2 = cy + (int)(Math.sin(angle) * (len * 0.43F));
         drawThickLine(graphics, cx, cy, x2, y2, 335540935, 2 + i % 3);
      }

      for (int i = 0; i < 9; i++) {
         int alpha = 13 + i * 4;
         int y = 102 + i * 13;
         graphics.fill(0, y, width, y + 1, alpha << 24 | 16775128);
      }
   }

   private static void renderOrbitingLights(GuiGraphics graphics, int cx, int cy, float time) {
      for (int ring = 0; ring < 4; ring++) {
         float radiusX = 205.0F + ring * 82.0F;
         float radiusY = 54.0F + ring * 18.0F;

         for (int i = 0; i < 12; i++) {
            float a = time * (0.022F + ring * 0.008F) + i * (float) (Math.PI / 6) + ring * 0.43F;
            int x = cx + (int)(Math.cos(a) * radiusX);
            int y = cy + (int)(Math.sin(a) * radiusY);
            int alpha = 78 - ring * 11;
            graphics.fill(x - 2, y - 2, x + 2, y + 2, alpha << 24 | 16775105);
            graphics.fill(x - 6, y, x + 6, y + 1, alpha / 2 << 24 | 16777215);
            graphics.fill(x, y - 6, x + 1, y + 6, alpha / 2 << 24 | 16777215);
         }
      }
   }

   private static void renderFeatherFall(GuiGraphics graphics, int width, int height, float time) {
      for (int i = 0; i < 34; i++) {
         float speed = 0.45F + i % 5 * 0.1F;
         int x = Math.floorMod((int)(i * 83 + Mth.sin(time * 0.018F + i) * 34.0F), Math.max(1, width));
         int y = Math.floorMod((int)(i * 79 + time * speed * 7.0F), Math.max(1, height + 80)) - 40;
         int alpha = 28 + i % 6 * 8;
         int color = alpha << 24 | 16775389;
         drawLine(graphics, x, y, x + 8, y - 15, color);
         drawLine(graphics, x + 2, y - 4, x + 10, y - 10, color);
         drawLine(graphics, x + 1, y - 7, x - 4, y - 13, color);
      }
   }

   private static void renderDust(GuiGraphics graphics, int width, int height, float time) {
      for (int i = 0; i < 150; i++) {
         int x = Math.floorMod(i * 97 + 23, Math.max(1, width));
         int baseY = Math.floorMod(i * 59 + 41, Math.max(1, height));
         int y = baseY + (int)(Mth.sin(time * 0.03F + i * 0.91F) * 7.0F);
         float pulse = 0.5F + 0.5F * Mth.sin(time * 0.06F + i * 0.7F);
         int alpha = 16 + (int)(pulse * 58.0F);
         int size = i % 9 == 0 ? 2 : 1;
         graphics.fill(x, y, x + size, y + size, alpha << 24 | 16775652);
      }
   }

   private static void renderFloatingCrosses(GuiGraphics graphics, int width, int height, float time) {
      for (int i = 0; i < 22; i++) {
         float a = time * 0.02F + i * 0.45F;
         int x = Math.floorMod((int)(i * 119 + time * (1.4F + i % 3 * 0.35F)), Math.max(1, width + 120)) - 60;
         int y = 100 + i * 53 % Math.max(120, height - 160);
         y += (int)(Mth.sin(a) * 11.0F);
         int size = 5 + i % 3 * 2;
         int alpha = 30 + (int)(20.0F * (0.5F + 0.5F * Mth.sin(a * 2.0F)));
         int color = alpha << 24 | 16777215;
         graphics.fill(x - size, y, x + size, y + 1, color);
         graphics.fill(x, y - size, x + 1, y + size, color);
      }
   }

   private static void renderWingFlares(GuiGraphics graphics, int width, int height, float time) {
      int cy = 88;
      int cx = width / 2;
      float pulse = 0.5F + 0.5F * Mth.sin(time * 0.055F);

      for (int i = 0; i < 8; i++) {
         int len = 70 + i * 18 + (int)(pulse * 10.0F);
         int y = cy + i * 6;
         drawLine(graphics, cx - 36, cy, cx - len, y, 587199695);
         drawLine(graphics, cx + 36, cy, cx + len, y, 587199695);
      }
   }

   private static void renderEdgeGlow(GuiGraphics graphics, int width, int height, float time) {
      int pulse = 24 + (int)(14.0F * (0.5F + 0.5F * Mth.sin(time * 0.08F)));
      int color = pulse << 24 | 16773303;
      graphics.fill(0, 0, width, 5, color);
      graphics.fill(0, height - 5, width, height, color);
      graphics.fill(0, 0, 5, height, color);
      graphics.fill(width - 5, 0, width, height, color);
      graphics.fill(0, 0, width, 1, 1728053247);
      graphics.fill(0, height - 1, width, height, 1442840575);
      graphics.fill(0, 0, 1, height, 1442840575);
      graphics.fill(width - 1, 0, width, height, 1442840575);
   }

   private static void drawRectRing(GuiGraphics graphics, int cx, int cy, int rx, int ry, int color) {
      graphics.hLine(cx - rx, cx + rx, cy - ry, color);
      graphics.hLine(cx - rx, cx + rx, cy + ry, color);
      graphics.vLine(cx - rx, cy - ry, cy + ry, color);
      graphics.vLine(cx + rx, cy - ry, cy + ry, color);
   }

   private static void drawThickLine(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color, int thickness) {
      int steps = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
      if (steps <= 0) {
         graphics.fill(x1, y1, x1 + 1, y1 + 1, color);
      } else {
         int radius = Math.max(1, thickness / 2);

         for (int i = 0; i <= steps; i++) {
            float t = (float)i / steps;
            int x = Mth.lerpInt(t, x1, x2);
            int y = Mth.lerpInt(t, y1, y2);
            graphics.fill(x - radius, y - radius, x + radius, y + radius, color);
         }
      }
   }

   private static void drawLine(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color) {
      int steps = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
      if (steps <= 0) {
         graphics.fill(x1, y1, x1 + 1, y1 + 1, color);
      } else {
         for (int i = 0; i <= steps; i++) {
            float t = (float)i / steps;
            int x = Mth.lerpInt(t, x1, x2);
            int y = Mth.lerpInt(t, y1, y2);
            graphics.fill(x, y, x + 1, y + 1, color);
         }
      }
   }
}
