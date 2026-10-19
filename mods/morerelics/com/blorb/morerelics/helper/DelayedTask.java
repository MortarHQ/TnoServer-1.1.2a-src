package com.blorb.morerelics.helper;

public class DelayedTask {
   public Runnable runnable;
   public int tickDelay;

   public DelayedTask(Runnable task, int tickDelay) {
      this.runnable = task;
      this.tickDelay = tickDelay;
   }
}
