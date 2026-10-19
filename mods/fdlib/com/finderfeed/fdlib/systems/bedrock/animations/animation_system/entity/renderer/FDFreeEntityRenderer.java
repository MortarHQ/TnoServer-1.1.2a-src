package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.renderer;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;

@FunctionalInterface
public interface FDFreeEntityRenderer<T extends Entity & AnimatedObject> {
   void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6);
}
