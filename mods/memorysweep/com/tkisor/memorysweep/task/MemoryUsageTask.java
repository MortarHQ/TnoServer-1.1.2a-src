package com.tkisor.memorysweep.task;

import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class MemoryUsageTask extends TimerTask {
   private long totalMemory = 0L;
   private long maxMemory = 0L;
   private double averageUsage = 0.0;
   private int count = 0;
   private final CountDownLatch latch;

   public MemoryUsageTask(CountDownLatch latch) {
      this.latch = latch;
   }

   public double getAverageUsage() {
      return this.averageUsage;
   }

   @Override
   public void run() {
      long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      this.totalMemory += usedMemory;
      this.maxMemory = this.maxMemory + Runtime.getRuntime().maxMemory();
      this.count++;
      if (this.count >= 10) {
         this.averageUsage = (double)this.totalMemory / this.maxMemory;
         this.cancel();
         this.latch.countDown();
      }
   }
}
