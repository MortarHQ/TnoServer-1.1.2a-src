package com.tom.storagemod.client;

import net.minecraft.world.phys.HitResult;
import org.vivecraft.client_vr.ClientDataHolderVR;
import org.vivecraft.client_vr.VRState;

public class ViveCraftHelper {
   public static HitResult rayTraceVR(double maxDist, boolean hitFluids) {
      if (VRState.VR_RUNNING) {
         ClientDataHolderVR holder = ClientDataHolderVR.getInstance();
         if (holder.vrPlayer != null && holder.vrPlayer.vrdata_world_render != null && !holder.vrSettings.seated) {
            return holder.vrPlayer.rayTraceBlocksVR(holder.vrPlayer.vrdata_world_render, 0, maxDist, hitFluids);
         }
      }

      return null;
   }
}
