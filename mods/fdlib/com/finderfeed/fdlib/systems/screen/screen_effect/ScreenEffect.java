package com.finderfeed.fdlib.systems.screen.screen_effect;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public abstract class ScreenEffect<T extends ScreenEffectData> {
   private int inTime;
   private int stayTime;
   private int outTime;
   private T data;

   public ScreenEffect(T data, int inTime, int stayTime, int outTime) {
      this.inTime = inTime;
      this.stayTime = stayTime;
      this.outTime = outTime;
      this.data = data;
   }

   public abstract void render(GuiGraphics var1, DeltaTracker var2, int var3, float var4, float var5);

   public void setData(T data) {
      this.data = data;
   }

   public T getData() {
      return this.data;
   }

   public boolean isInTime(int currentTick) {
      return this.inTime == 0 ? false : currentTick <= this.inTime;
   }

   public boolean isStayTime(int currentTick) {
      if (this.stayTime == 0) {
         return false;
      }

      int time = currentTick - this.inTime;
      return this.inTime == 0 ? time > 0 && time <= this.stayTime : time > 0 && time <= this.stayTime;
   }

   public boolean isOutTime(int currentTick) {
      if (this.outTime == 0) {
         return false;
      }

      int time = currentTick - this.inTime - this.stayTime;
      return this.stayTime == 0 && this.inTime == 0 ? time >= 0 && time <= this.outTime : time > 0 && time <= this.outTime;
   }

   public float getInTimePercent(int currentTick, float pticks) {
      if (this.inTime == 0) {
         return 0.0F;
      }

      float time = Mth.clamp(currentTick + pticks, 0.0F, this.inTime);
      return time / this.inTime;
   }

   public float getStayTimePercent(int currentTick, float pticks) {
      if (this.stayTime == 0) {
         return 0.0F;
      }

      float time = Mth.clamp(currentTick + pticks - this.inTime, 0.0F, this.stayTime);
      return time / this.stayTime;
   }

   public float getOutTimePercent(int currentTick, float pticks) {
      if (this.outTime == 0) {
         return 0.0F;
      }

      float time = Mth.clamp(currentTick + pticks - this.inTime - this.stayTime, 0.0F, this.outTime);
      return time / this.outTime;
   }

   public void setOutTime(int outTime) {
      this.outTime = outTime;
   }

   public void setInTime(int inTime) {
      this.inTime = inTime;
   }

   public void setStayTime(int stayTime) {
      this.stayTime = stayTime;
   }

   public int getOutTime() {
      return this.outTime;
   }

   public int getInTime() {
      return this.inTime;
   }

   public int getStayTime() {
      return this.stayTime;
   }

   public int getLifetime() {
      return this.stayTime + this.outTime + this.inTime;
   }
}
