package com.blackgear.platform.core.mixin.access;

import net.minecraft.client.Camera.NearPlane;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NearPlane.class)
public interface NearPlaneAccessor {
   @Accessor("forward")
   Vec3 getForward();
}
