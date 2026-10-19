package team.lodestar.lodestone.systems.rendering.shader.compute;

import com.mojang.blaze3d.shaders.ProgramManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL43;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.rendering.IBufferObject;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderSystem;
import team.lodestar.lodestone.systems.rendering.shader.LodestoneShader;

public class ComputeProgram implements IBufferObject, LodestoneShader {
   private int programId;
   private ComputeProgram.Shader shader;
   private ResourceLocation shaderLocation;
   private Map<String, ShaderStorageBufferObject> ssbos = new HashMap<>();
   private static final int[] maxWorkGroupSize = new int[]{-1, -1, -1};
   private static final int[] maxWorkGroupCount = new int[]{-1, -1, -1};
   private static int maxWorkGroupInvocations = -1;

   public ComputeProgram(ResourceLocation shaderLocation) {
      this.shaderLocation = shaderLocation;
      this.registerBufferObject();
   }

   @Override
   public void register(RegisterShadersEvent event) {
      loadConstraints();
      this.loadShader(event.getResourceProvider());
   }

   private void loadShader(ResourceProvider provider) {
      int[] version = SystemDetails.getOpenglVersion();
      if (version[0] >= 4 && (version[0] != 4 || version[1] >= 3)) {
         this.destroy();

         try {
            this.programId = ProgramManager.createProgram();
         } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create compute shader program");
         }

         this.shader = new ComputeProgram.Shader(provider, this.shaderLocation);
         this.attachShader(this.shader);
         this.link();
      } else {
         LodestoneLib.LOGGER.warn("Compute shaders are not supported on this system (OpenGL {}.{})", version[0], version[1]);
      }
   }

   public void bindAndDispatch(int x, int y, int z) {
      this.bind();
      GL43.glDispatchCompute(x, y, z);
   }

   public void memoryBarrier(int barrier) {
      GL43.glMemoryBarrier(barrier);
   }

   public void bindSSBO(String name, ShaderStorageBufferObject.Usage usage, int bindingIndex) {
      ShaderStorageBufferObject ssbo = this.ssbos.get(name);
      if (ssbo == null) {
         ssbo = new ShaderStorageBufferObject(usage, bindingIndex);
         this.ssbos.put(name, ssbo);
      }

      LodestoneRenderSystem.bindBufferBase(37074, bindingIndex, ssbo.getId());
   }

   public void bindSSBO(String name, ShaderStorageBufferObject ssbo, int bindingIndex) {
      this.ssbos.put(name, ssbo);
      LodestoneRenderSystem.bindBufferBase(37074, bindingIndex, ssbo.getId());
   }

   private void attachShader(ComputeProgram.Shader shader) {
      GL43.glAttachShader(this.programId, shader.getId());
   }

   private void detachShader(ComputeProgram.Shader shader) {
      GL43.glDetachShader(this.programId, shader.getId());
   }

   private void link() {
      GL43.glLinkProgram(this.programId);
   }

   public void bind() {
      ProgramManager.glUseProgram(this.programId);
   }

   public static void loadConstraints() {
      for (int i = 0; i < 3; i++) {
         if (maxWorkGroupSize[i] == -1) {
            maxWorkGroupSize[i] = GL43.glGetIntegeri(37311, i);
         }

         if (maxWorkGroupCount[i] == -1) {
            maxWorkGroupCount[i] = GL43.glGetIntegeri(37310, i);
         }
      }

      if (maxWorkGroupInvocations == -1) {
         maxWorkGroupInvocations = GL43.glGetInteger(37099);
      }
   }

   public int queryLinearWorkGroupSize() {
      int[] size = new int[3];
      GL43.glGetProgramiv(this.programId, 33383, size);
      return size[0] * size[1] * size[2];
   }

   public ComputeProgram.Shader getShader() {
      return this.shader;
   }

   public void reload() {
      this.loadShader(Minecraft.getInstance().getResourceManager());
   }

   @Override
   public void destroy() {
      if (this.programId != 0) {
         GL43.glDeleteProgram(this.programId);
      }

      if (this.shader != null) {
         this.shader.destroy();
      }

      this.programId = 0;
      this.shader = null;
   }

   public static class Shader implements IBufferObject {
      private ResourceLocation shaderLocation;
      private int shaderId;
      private String source;
      private int[] localSize;

      public Shader(ResourceProvider provider, ResourceLocation shaderLocation) {
         this.shaderLocation = shaderLocation;
         this.openShader(provider, shaderLocation);
         this.preProcess();
         this.compile();
      }

      private void openShader(ResourceProvider provider, ResourceLocation shaderId) {
         ResourceLocation shaderLocation = shaderId.withPrefix("shaders/compute/").withSuffix(".comp");

         try (InputStream stream = provider.getResourceOrThrow(shaderLocation).open()) {
            this.source = IOUtils.toString(stream, StandardCharsets.UTF_8);
         } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to open resource: " + shaderLocation);
         }
      }

      public void preProcess() {
         this.localSize = ComputePreprocessor.INSTANCE.getLocalSize(this.source);
         this.verifySize();
         StringBuilder builder = new StringBuilder();
         ComputePreprocessor.INSTANCE.process(this.source).forEach(builder::append);
         this.source = builder.toString();
      }

      public void compile() {
         this.shaderId = GL43.glCreateShader(37305);
         GL43.glShaderSource(this.shaderId, this.source);
         GL43.glCompileShader(this.shaderId);
         if (GL43.glGetShaderi(this.shaderId, 35713) == 0) {
            throw new RuntimeException("Failed to compile shader: " + GL43.glGetShaderInfoLog(this.shaderId, 1024));
         }
      }

      public void reload(ResourceProvider provider) {
         this.destroy();
         this.openShader(provider, this.shaderLocation);
         this.preProcess();
         this.compile();
      }

      private void verifySize() {
         if (this.localSize[0] * this.localSize[1] * this.localSize[2] > ComputeProgram.maxWorkGroupInvocations) {
            throw new RuntimeException("Local size exceeds max work group invocations of " + ComputeProgram.maxWorkGroupInvocations);
         }
      }

      public ResourceLocation getShaderLocation() {
         return this.shaderLocation;
      }

      public String getSource() {
         return this.source;
      }

      public int getId() {
         return this.shaderId;
      }

      public int[] getLocalSize() {
         return this.localSize;
      }

      @Override
      public void destroy() {
         if (this.shaderId != 0) {
            GL43.glDeleteShader(this.shaderId);
         }

         this.shaderId = 0;
      }
   }
}
