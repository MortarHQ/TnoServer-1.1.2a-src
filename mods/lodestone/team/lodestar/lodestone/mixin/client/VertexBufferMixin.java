package team.lodestar.lodestone.mixin.client;

import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer.Usage;
import com.mojang.blaze3d.vertex.VertexFormat.IndexType;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL31;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.systems.rendering.IVertexBuffer;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderSystem;

@Mixin(VertexBuffer.class)
public abstract class VertexBufferMixin implements IVertexBuffer {
   @Unique
   private Map<Integer, Integer> extraVBOs = new HashMap<>();
   @Shadow
   private Mode mode;
   @Shadow
   private VertexFormat format;
   @Shadow
   private int indexCount;

   @Override
   public void drawInstanced(int instances) {
      LodestoneRenderSystem.wrap(() -> GL31.glDrawElementsInstanced(this.mode.asGLMode, this.indexCount, this.getIndexType().asGLType, 0L, instances));
   }

   @Override
   public void drawWithShaderInstanced(Matrix4f modelViewMatrix, Matrix4f projectionMatrix, ShaderInstance shader, int instances) {
      LodestoneRenderSystem.wrap(() -> {
         shader.setDefaultUniforms(this.mode, modelViewMatrix, projectionMatrix, Minecraft.getInstance().getWindow());
         shader.apply();
         this.drawInstanced(instances);
         shader.clear();
      });
   }

   @Override
   public void addAttributeVBO(int binding, FloatBuffer buffer, Usage usage, Consumer<Integer> setup) {
      this.bind();
      int vbo = this.extraVBOs.computeIfAbsent(binding, v -> GL31.glGenBuffers());
      GL31.glBindBuffer(34962, vbo);
      GL31.glBufferData(34962, buffer, usage.id);
      setup.accept(binding);
   }

   @Inject(method = "close", at = @At("HEAD"))
   public void close(CallbackInfo ci) {
      this.extraVBOs.forEach((binding, vbo) -> GL31.glDeleteBuffers(vbo));
   }

   @Shadow
   public abstract IndexType getIndexType();

   @Shadow
   public abstract void bind();
}
