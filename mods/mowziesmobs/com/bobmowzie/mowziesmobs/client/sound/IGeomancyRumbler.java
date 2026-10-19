package com.bobmowzie.mowziesmobs.client.sound;

public interface IGeomancyRumbler {
   boolean isRumbling();

   boolean isFinishedRumbling();

   default float getRumbleVolume() {
      return 1.0F;
   }

   default float getRumblePitch() {
      return 1.0F;
   }

   float getRumblerX();

   float getRumblerY();

   float getRumblerZ();
}
