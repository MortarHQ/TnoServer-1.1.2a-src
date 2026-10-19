package com.mongoose.royalvariations.common.entities;

public interface IRoyalInterface {
   default boolean isFlashing() {
      return this.getFlashTick() > 0;
   }

   int getFlashTick();

   void setFlashTick(int var1);

   default boolean isMarked() {
      return this.getMarkedTick() > 0;
   }

   int getMarkedTick();

   void setMarkedTick(int var1);

   default boolean isGazed() {
      return this.getGazeTick() > 0;
   }

   int getGazeTick();

   void setGazeTick(int var1);
}
