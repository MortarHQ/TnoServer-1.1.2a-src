package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.renderer;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.entity.Entity;

@FunctionalInterface
public interface IShouldEntityRender<T extends Entity & AnimatedObject> {
   boolean shouldRender(T var1, Frustum var2, double var3, double var5, double var7);
}
