package net.pixeldreamstudios.mythika.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public final class ScreenShake {
   private static final float MAX_DEGREES = 3.5F;
   private static final int DURATION_TICKS = 12;
   private static final double RANGE = 18.0;
   private static float strength;
   private static long endTick;

   private ScreenShake() {
   }

   public static void onSlam(Entity source) {
      Minecraft client = Minecraft.getInstance();
      LocalPlayer player = client.player;
      if (player != null && client.level != null) {
         double distance = player.distanceTo(source);
         float nearness = (float)Mth.clamp(1.0 - distance / 18.0, 0.0, 1.0);
         if (!(nearness <= 0.0F)) {
            strength = Math.max(strength, nearness * nearness);
            endTick = client.level.getGameTime() + 12L;
         }
      }
   }

   public static float amplitude(float partialTick) {
      Minecraft client = Minecraft.getInstance();
      if (client.level != null && !(strength <= 0.0F)) {
         float remaining = (float)(endTick - client.level.getGameTime()) - partialTick;
         if (remaining <= 0.0F) {
            strength = 0.0F;
            return 0.0F;
         } else {
            return strength * (remaining / 12.0F) * 3.5F;
         }
      } else {
         return 0.0F;
      }
   }
}
