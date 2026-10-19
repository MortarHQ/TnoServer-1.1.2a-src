package com.shin.tensura_kumodesu.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface CustomTensuraModelFormat<T extends LivingEntity> {
   void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6, Model var7);

   void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8, Player var9);
}
