package team.lodestar.lodestone.systems.rendering;

import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexBuffer.Usage;
import java.nio.FloatBuffer;
import java.util.function.Consumer;
import net.minecraft.client.renderer.ShaderInstance;
import org.joml.Matrix4f;

public interface IVertexBuffer {
   static IVertexBuffer cast(VertexBuffer vertexBuffer) {
      return (IVertexBuffer)vertexBuffer;
   }

   void drawInstanced(int var1);

   void drawWithShaderInstanced(Matrix4f var1, Matrix4f var2, ShaderInstance var3, int var4);

   void addAttributeVBO(int var1, FloatBuffer var2, Usage var3, Consumer<Integer> var4);
}
