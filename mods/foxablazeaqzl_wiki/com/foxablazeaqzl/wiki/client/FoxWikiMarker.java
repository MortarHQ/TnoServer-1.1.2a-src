package com.foxablazeaqzl.wiki.client;

import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public final class FoxWikiMarker {
   private static final ResourceLocation MARKER = ResourceLocation.fromNamespaceAndPath("foxablazeaqzl_wiki", "textures/gui/unread_marker.png");
   private static final int TEXTURE_SIZE = 20;
   public static final int LIST_SIZE = 8;
   public static final int TAB_SIZE = 8;

   private FoxWikiMarker() {
   }

   public static void render(GuiGraphics graphics, int x, int y, int size) {
      float halfSize = size / 2.0F;
      graphics.pose().pushPose();
      graphics.pose().translate(x + halfSize, y + halfSize, 0.0F);
      graphics.pose().mulPose(Axis.ZP.rotationDegrees(swingAngle()));
      float scale = scaleFactor();
      graphics.pose().scale(scale, scale, 1.0F);
      graphics.pose().translate(-halfSize, -halfSize, 0.0F);
      graphics.blit(MARKER, 0, 0, size, size, 0.0F, 0.0F, 20, 20, 20, 20);
      graphics.pose().popPose();
   }

   private static float swingAngle() {
      return (float)Math.sin(Util.getMillis() / 180.0) * 12.0F;
   }

   private static float scaleFactor() {
      return 1.0F + (float)Math.sin(Util.getMillis() / 260.0) * 0.04F;
   }
}
