package twilightforest.client;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.io.IOException;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;

public class TFShaders {
   public static ShaderInstance RED_THREAD;
   public static TFShaders.PositionAwareShaderInstance AURORA;

   public static void registerShaders(RegisterShadersEvent event) {
      try {
         event.registerShader(
            new ShaderInstance(event.getResourceProvider(), TwilightForestMod.prefix("red_thread/red_thread"), DefaultVertexFormat.BLOCK),
            shader -> RED_THREAD = shader
         );
         event.registerShader(
            new TFShaders.PositionAwareShaderInstance(
               event.getResourceProvider(), TwilightForestMod.prefix("aurora/aurora"), DefaultVertexFormat.POSITION_COLOR
            ),
            shader -> AURORA = (TFShaders.PositionAwareShaderInstance)shader
         );
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static class BindableShaderInstance extends ShaderInstance {
      private ShaderInstance last;

      public BindableShaderInstance(ResourceProvider p_173336_, ResourceLocation shaderLocation, VertexFormat p_173338_) throws IOException {
         super(p_173336_, shaderLocation, p_173338_);
      }

      ShaderInstance getSelf() {
         return this;
      }

      public final void bind(@Nullable Runnable exec) {
         this.last = RenderSystem.getShader();
         RenderSystem.setShader(this::getSelf);
         if (exec != null) {
            exec.run();
         }

         this.apply();
      }

      public final void runThenClear(Runnable exec) {
         exec.run();
         this.clear();
         RenderSystem.setShader(() -> this.last);
         this.last = null;
      }

      public final void invokeThenClear(@Nullable Runnable execBind, Runnable execPost) {
         this.bind(execBind);
         this.runThenClear(execPost);
      }

      public final void invokeThenClear(Runnable execPost) {
         this.invokeThenClear(null, execPost);
      }

      public final void invokeThenEndTesselator(@Nullable Runnable execBind, BufferBuilder builder) {
         this.invokeThenClear(execBind, () -> BufferUploader.drawWithShader(builder.buildOrThrow()));
      }

      public final void invokeThenEndTesselator(BufferBuilder builder) {
         this.invokeThenClear(() -> BufferUploader.drawWithShader(builder.buildOrThrow()));
      }
   }

   public static class PositionAwareShaderInstance extends TFShaders.BindableShaderInstance {
      @Nullable
      public final Uniform SEED = this.getUniform("SeedContext");
      @Nullable
      public final Uniform POSITION = this.getUniform("PositionContext");

      public PositionAwareShaderInstance(ResourceProvider p_173336_, ResourceLocation shaderLocation, VertexFormat p_173338_) throws IOException {
         super(p_173336_, shaderLocation, p_173338_);
      }

      public final void setValue(int seed, float x, float y, float z) {
         if (this.SEED != null) {
            this.SEED.set(seed);
         }

         if (this.POSITION != null) {
            this.POSITION.set(x, y, z);
         }
      }

      public final void setValueBindApply(int seed, float x, float y, float z) {
         this.bind(() -> this.setValue(seed, x, y, z));
      }

      public final void reset() {
         this.setValue(0, 0.0F, 0.0F, 0.0F);
      }

      public final void resetClear() {
         this.runThenClear(this::reset);
      }

      public final void invokeThenClear(int seed, float x, float y, float z, Runnable exec) {
         this.setValueBindApply(seed, x, y, z);
         exec.run();
         this.resetClear();
      }

      public final void invokeThenEndTesselator(int seed, float x, float y, float z, BufferBuilder builder) {
         this.invokeThenClear(seed, x, y, z, () -> BufferUploader.drawWithShader(builder.buildOrThrow()));
      }
   }
}
