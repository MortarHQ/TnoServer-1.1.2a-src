package cn.leolezury.eternalstarlight.common.client.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public interface WorldVisualEffect {
   void worldTick();

   void render(MultiBufferSource var1, PoseStack var2, float var3);

   boolean shouldRemove();
}
