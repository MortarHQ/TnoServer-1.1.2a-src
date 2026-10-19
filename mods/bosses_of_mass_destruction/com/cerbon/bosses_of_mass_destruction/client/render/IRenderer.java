package com.cerbon.bosses_of_mass_destruction.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;

public interface IRenderer<T extends Entity> {
   void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6);
}
