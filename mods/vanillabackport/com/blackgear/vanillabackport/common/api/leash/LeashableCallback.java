package com.blackgear.vanillabackport.common.api.leash;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public interface LeashableCallback {
   default double vb$leashSnapDistance() {
      return 12.0;
   }

   default double vb$leashElasticDistance() {
      return 6.0;
   }

   default boolean vb$supportsQuadLeash() {
      return true;
   }

   default Vec3[] vb$getQuadLeashOffsets() {
      return LeashPhysics.createQuadOffsets((Entity)this, 0.0, 0.5, 0.5, 0.5);
   }

   default void vb$onElasticLeashPull() {
      ((Entity)this).checkSlowFallDistance();
   }

   default boolean vb$shouldResetAngularMomentum() {
      return false;
   }
}
