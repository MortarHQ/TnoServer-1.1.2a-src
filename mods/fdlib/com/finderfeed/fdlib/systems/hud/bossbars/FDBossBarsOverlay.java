package com.finderfeed.fdlib.systems.hud.bossbars;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.client.gui.components.BossHealthOverlay;

public class FDBossBarsOverlay implements Layer {
   public static final float BASE_BOSS_BAR_TOP_OFFSET = 3.0F;

   public void render(GuiGraphics graphics, DeltaTracker deltraTracker) {
      if (!Minecraft.getInstance().options.hideGui) {
         float pticks = deltraTracker.getGameTimeDeltaPartialTick(false);
         PoseStack matrices = graphics.pose();
         Window window = Minecraft.getInstance().getWindow();
         float width = window.getGuiScaledWidth();
         matrices.pushPose();
         float baseOffset = calculateBossBarsOffset();
         matrices.translate(width / 2.0F, baseOffset, 0.0F);

         for (FDBossBar bossBar : FDBossbars.BOSS_BARS.values()) {
            float height = bossBar.height();
            bossBar.render(graphics, pticks);
            matrices.translate(0.0F, height, 0.0F);
         }

         matrices.popPose();
      }
   }

   public static float calculateBossBarsOffset() {
      BossHealthOverlay bossOverlay = Minecraft.getInstance().gui.getBossOverlay();
      int size = bossOverlay.events.size();
      return 3.0F + size * 25;
   }
}
