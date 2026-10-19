package com.blorb.morerelics.dataComponents;

public record StarCounter(int count, int timerCurrent) {
   public StarCounter getUpdatedCounter(int count, int timer) {
      return new StarCounter(count, timer);
   }
}
