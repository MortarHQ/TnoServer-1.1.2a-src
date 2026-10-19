package com.finderfeed.fdlib.systems.bedrock.animations.animation_system;

import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelPart;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

@FunctionalInterface
public interface BoneTransformationController<T extends AnimatedObject> {
   void transformBone(T var1, FDModel var2, FDModelPart var3, PoseStack var4, MultiBufferSource var5, String var6, int var7, int var8, float var9);
}
