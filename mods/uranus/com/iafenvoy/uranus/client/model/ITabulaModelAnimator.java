package com.iafenvoy.uranus.client.model;

import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@FunctionalInterface
@OnlyIn(Dist.CLIENT)
public interface ITabulaModelAnimator<T extends Entity> {
   void setRotationAngles(TabulaModel<T> var1, T var2, float var3, float var4, float var5, float var6, float var7, float var8);
}
