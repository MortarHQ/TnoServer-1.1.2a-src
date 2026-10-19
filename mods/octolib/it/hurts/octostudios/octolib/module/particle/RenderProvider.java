package it.hurts.octostudios.octolib.module.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;

public interface RenderProvider<P extends RenderProvider<P, B>, B extends RenderBuffer<P, B>> {
   Vec3 getRenderPosition(float var1);

   void render(float var1, PoseStack var2, MultiBufferSource var3);

   default double getRenderDistance() {
      return 64.0;
   }

   boolean shouldRender(B var1);

   int getUpdateFrequency();

   B createBuffer();
}
