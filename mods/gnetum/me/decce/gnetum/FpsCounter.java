package me.decce.gnetum;

import me.decce.gnetum.mixins.MinecraftAccessor;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;

public class FpsCounter {
   private int fps;
   private int frames;
   private long lastTime;

   public void reset() {
      this.lastTime = ((MinecraftAccessor)Minecraft.getInstance()).getLastTime();
      this.frames = 0;
      this.fps = 0;
   }

   public void tick() {
      this.frames++;

      while (Util.getMillis() >= this.lastTime + 1000L) {
         this.fps = this.frames;
         this.frames = 0;
         this.lastTime += 1000L;
      }
   }

   public int getFps() {
      return this.fps;
   }
}
