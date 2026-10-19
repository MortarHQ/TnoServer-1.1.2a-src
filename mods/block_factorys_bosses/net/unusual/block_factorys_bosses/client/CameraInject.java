package net.unusual.block_factorys_bosses.client;

import net.minecraft.world.phys.Vec3;

public interface CameraInject {
   boolean isCinematic();

   void setCinematic(boolean var1);

   void setCinematicPosition(Vec3 var1);
}
