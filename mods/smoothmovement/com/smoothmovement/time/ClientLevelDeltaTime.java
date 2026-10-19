package com.smoothmovement.time;

public interface ClientLevelDeltaTime {
   double getSlownessFactor();

   void onTimePacket(long var1, long var3);

   float getVisualDayTimeSpeed();

   void onScoreBoardPacket(int var1);
}
