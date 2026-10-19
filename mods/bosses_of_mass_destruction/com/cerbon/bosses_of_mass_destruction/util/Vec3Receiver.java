package com.cerbon.bosses_of_mass_destruction.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;

public interface Vec3Receiver {
   void clientHandler(ClientLevel var1, Vec3 var2);
}
