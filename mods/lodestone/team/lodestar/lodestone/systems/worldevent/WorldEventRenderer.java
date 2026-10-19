package team.lodestar.lodestone.systems.worldevent;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public abstract class WorldEventRenderer<T extends WorldEventInstance> {
   public abstract boolean canRender(T var1);

   public abstract void render(T var1, PoseStack var2, MultiBufferSource var3, float var4);
}
