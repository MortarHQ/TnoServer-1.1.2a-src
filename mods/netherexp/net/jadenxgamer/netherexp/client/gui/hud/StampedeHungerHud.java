package net.jadenxgamer.netherexp.client.gui.hud;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.Stampede;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class StampedeHungerHud {
   private static final ResourceLocation TEXTURE = NetherExp.netherexpPath("textures/gui/sprites/hud/stampede_hunger.png");
   private static final int ICON_SIZE = 20;
   private static final int MAX_HUNGER = 20;
   private static final int FILL_TOP = 4;
   private static final int FILL_HEIGHT = 13;
   private static final int FLASH_DURATION_TICKS = 50;
   private static int lastHunger = -1;
   private static int flashTimer = 0;
   private static float tickAccumulator = 0.0F;
   private static boolean wasRiding = false;
   public static final Layer OVERLAY = StampedeHungerHud::render;

   private static void render(GuiGraphics gui, DeltaTracker deltaTracker) {
      Minecraft client = Minecraft.getInstance();
      Player player = client.player;
      if (player != null) {
         if (player.getVehicle() instanceof Stampede stampede) {
            int var14 = stampede.getHunger();
            tickAccumulator = tickAccumulator + deltaTracker.getGameTimeDeltaTicks();

            while (tickAccumulator >= 1.0F) {
               tickAccumulator--;
               if (flashTimer > 0) {
                  flashTimer--;
               }
            }

            if (wasRiding) {
               if (var14 != lastHunger) {
                  if (var14 > lastHunger) {
                     flashTimer = 50;
                  }

                  lastHunger = var14;
               }
            } else {
               lastHunger = var14;
               wasRiding = true;
            }

            int screenHeight = client.getWindow().getGuiScaledHeight();
            int baseY = screenHeight - (stampede.getControllingPassenger() == player ? 50 : 60);
            int shakeOffset = 0;
            if (var14 <= 4) {
               shakeOffset = player.tickCount / 4 % 2 == 0 ? -1 : 0;
            }

            int x = (client.getWindow().getGuiScaledWidth() - 20) / 2;
            int y = baseY + shakeOffset;
            gui.blit(TEXTURE, x, y, 20, 20, 20.0F, 0.0F, 20, 20, 60, 20);
            if (var14 > 0) {
               int fillPixels = (int)(var14 / 20.0F * 13.0F);
               int srcV = 4 + (13 - fillPixels);
               int destY = y + srcV;
               gui.blit(TEXTURE, x, destY, 20, fillPixels, 0.0F, srcV, 20, fillPixels, 60, 20);
            }

            if (flashTimer > 0 && flashTimer / 10 % 2 == 0) {
               gui.blit(TEXTURE, x, y, 20, 20, 40.0F, 0.0F, 20, 20, 60, 20);
            }
         } else {
            lastHunger = -1;
            flashTimer = 0;
            tickAccumulator = 0.0F;
            wasRiding = false;
         }
      }
   }
}
