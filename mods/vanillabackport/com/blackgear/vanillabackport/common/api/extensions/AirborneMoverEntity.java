package com.blackgear.vanillabackport.common.api.extensions;

public interface AirborneMoverEntity {
   default boolean omnidirectionalAirMover() {
      return false;
   }

   default void afterTravelInFluid() {
   }
}
