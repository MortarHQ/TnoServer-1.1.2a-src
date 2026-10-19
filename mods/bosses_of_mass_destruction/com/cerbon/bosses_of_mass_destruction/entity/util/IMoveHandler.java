package com.cerbon.bosses_of_mass_destruction.entity.util;

import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

public interface IMoveHandler {
   boolean canMove(MoverType var1, Vec3 var2);
}
