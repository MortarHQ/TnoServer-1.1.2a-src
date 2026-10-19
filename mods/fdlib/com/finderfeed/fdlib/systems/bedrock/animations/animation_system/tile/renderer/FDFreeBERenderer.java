package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile.renderer;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.BlockEntity;

@FunctionalInterface
public interface FDFreeBERenderer<T extends BlockEntity & AnimatedObject> {
   void render(T var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6);
}
