package team.lodestar.lodestone.systems.rendering.renderpass;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

public abstract class BeforeLevelRenderPass {
   public abstract void render(DeltaTracker var1, Camera var2, GameRenderer var3, Matrix4f var4, Matrix4f var5);

   public abstract boolean shouldRender(DeltaTracker var1, Camera var2, GameRenderer var3, Matrix4f var4, Matrix4f var5);

   public abstract void resize(int var1, int var2);
}
