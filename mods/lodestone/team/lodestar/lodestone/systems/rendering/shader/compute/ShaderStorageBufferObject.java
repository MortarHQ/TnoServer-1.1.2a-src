package team.lodestar.lodestone.systems.rendering.shader.compute;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL43;
import org.lwjgl.system.MemoryUtil;
import team.lodestar.lodestone.systems.rendering.IBufferObject;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderSystem;

public class ShaderStorageBufferObject implements IBufferObject {
   private int bufferId;
   private final ShaderStorageBufferObject.Usage usage;
   private final int bindingIndex;
   private FloatBuffer floatBuffer;

   public ShaderStorageBufferObject(ShaderStorageBufferObject.Usage usage, int bindingIndex) {
      this(usage, bindingIndex, GL43.glGenBuffers());
   }

   public ShaderStorageBufferObject(ShaderStorageBufferObject.Usage usage, int bindingIndex, int bufferId) {
      this.usage = usage;
      this.bindingIndex = bindingIndex;
      this.bufferId = bufferId;
      LodestoneRenderSystem.glBindBuffer(37074, this.bufferId);
      LodestoneRenderSystem.bindBufferBase(37074, bindingIndex, this.bufferId);
      this.registerBufferObject();
   }

   public void upload(FloatBuffer floatBuffer) {
      int size = floatBuffer.remaining();
      if (floatBuffer.remaining() > SystemDetails.getMaxShaderStorageBlockSize()) {
         throw new RuntimeException("Buffer size exceeds maximum shader storage block size");
      }

      LodestoneRenderSystem.glBindBuffer(37074, this.bufferId);
      GL43.glBufferData(37074, floatBuffer, this.usage.getGlEnum());
      LodestoneRenderSystem.glBindBuffer(37074, 0);
      this.floatBuffer = floatBuffer;
   }

   public static void unbind() {
      LodestoneRenderSystem.glBindBuffer(37074, 0);
   }

   public int getId() {
      return this.bufferId;
   }

   public int getBindingIndex() {
      return this.bindingIndex;
   }

   public ShaderStorageBufferObject.Usage getUsage() {
      return this.usage;
   }

   public FloatBuffer getFloatBuffer() {
      return this.floatBuffer;
   }

   @Override
   public String toString() {
      return "ShaderStorageBufferObject{bufferId=" + this.bufferId + ", usage=" + this.usage + ", bindingIndex=" + this.bindingIndex + "}";
   }

   @Override
   public void destroy() {
      if (this.bufferId != 0) {
         GL43.glDeleteProgram(this.bufferId);
      }

      if (this.floatBuffer != null) {
         MemoryUtil.memFree(this.floatBuffer);
      }
   }

   public enum Usage {
      STREAM_DRAW(35040),
      STREAM_READ(35041),
      STREAM_COPY(35042),
      STATIC_DRAW(35044),
      STATIC_READ(35045),
      STATIC_COPY(35046),
      DYNAMIC_DRAW(35048),
      DYNAMIC_READ(35049),
      DYNAMIC_COPY(35050);

      private final int glEnum;

      Usage(int glEnum) {
         this.glEnum = glEnum;
      }

      public int getGlEnum() {
         return this.glEnum;
      }
   }
}
