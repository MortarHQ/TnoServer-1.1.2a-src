package com.cerbon.bosses_of_mass_destruction.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public interface IRendererWithModel {
   void render(BakedGeoModel var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6, float var7, float var8, float var9, float var10);
}
