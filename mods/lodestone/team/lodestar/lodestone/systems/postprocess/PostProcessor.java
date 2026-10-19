package team.lodestar.lodestone.systems.postprocess;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderSystem;

public abstract class PostProcessor {
   protected static final Minecraft MC = Minecraft.getInstance();
   public static final Collection<Pair<String, Consumer<Uniform>>> COMMON_UNIFORMS = Lists.newArrayList(
      new Pair[]{
         Pair.of("cameraPos", (Consumer<Uniform>)u -> u.set(new Vector3f(MC.gameRenderer.getMainCamera().getPosition().toVector3f()))),
         Pair.of("lookVector", (Consumer<Uniform>)u -> u.set(MC.gameRenderer.getMainCamera().getLookVector())),
         Pair.of("upVector", (Consumer<Uniform>)u -> u.set(MC.gameRenderer.getMainCamera().getUpVector())),
         Pair.of("leftVector", (Consumer<Uniform>)u -> u.set(MC.gameRenderer.getMainCamera().getLeftVector())),
         Pair.of("invViewMat", (Consumer<Uniform>)u -> u.set(PostProcessor.viewModelMatrix.invert(new Matrix4f()))),
         Pair.of("invProjMat", (Consumer<Uniform>)u -> u.set(RenderSystem.getProjectionMatrix().invert(new Matrix4f()))),
         Pair.of("nearPlaneDistance", (Consumer<Uniform>)u -> u.set(0.05F)),
         Pair.of("farPlaneDistance", (Consumer<Uniform>)u -> u.set(MC.gameRenderer.getDepthFar())),
         Pair.of(
            "fov",
            (Consumer<Uniform>)u -> u.set(
               (float)Math.toRadians(MC.gameRenderer.getFov(MC.gameRenderer.getMainCamera(), MC.getTimer().getGameTimeDeltaPartialTick(false), true))
            )
         ),
         Pair.of("aspectRatio", (Consumer<Uniform>)u -> u.set((float)MC.getWindow().getWidth() / MC.getWindow().getHeight())),
         Pair.of(
            "bobOffset",
            (Consumer<Uniform>)u -> u.set(
               PostProcessor.viewModelMatrix.invert(new Matrix4f()).transformPosition(LodestoneRenderSystem.getViewBobOffset(), new Vector3f())
            )
         )
      }
   );
   public static Matrix4f viewModelMatrix;
   private boolean initialized = false;
   protected PostChain postChain;
   protected EffectInstance[] effects;
   private RenderTarget tempDepthBuffer;
   private Collection<Pair<Uniform, Consumer<Uniform>>> defaultUniforms;
   private boolean isActive = true;
   protected double time;

   public abstract ResourceLocation getPostChainLocation();

   public void init() {
      this.loadPostChain();
      if (this.postChain != null) {
         this.tempDepthBuffer = this.postChain.getTempTarget("depthMain");
         this.defaultUniforms = new ArrayList<>();

         for (EffectInstance e : this.effects) {
            for (Pair<String, Consumer<Uniform>> pair : COMMON_UNIFORMS) {
               Uniform u = e.getUniform((String)pair.getFirst());
               if (u != null) {
                  this.defaultUniforms.add(Pair.of(u, (Consumer)pair.getSecond()));
               }
            }
         }
      }

      this.initialized = true;
   }

   public final void loadPostChain() {
      if (this.postChain != null) {
         this.postChain.close();
         this.postChain = null;
      }

      try {
         ResourceLocation file = this.getPostChainLocation();
         file = ResourceLocation.fromNamespaceAndPath(file.getNamespace(), "shaders/post/" + file.getPath() + ".json");
         this.postChain = new PostChain(MC.getTextureManager(), MC.getResourceManager(), MC.getMainRenderTarget(), file);
         this.postChain.resize(MC.getWindow().getWidth(), MC.getWindow().getHeight());
         this.effects = this.postChain.passes.stream().map(PostPass::getEffect).toArray(EffectInstance[]::new);
      } catch (IOException | JsonParseException e) {
         LodestoneLib.LOGGER.error("Failed to load post-processing shader: ", e);
      }
   }

   public final void copyDepthBuffer() {
      if (this.isActive) {
         if (this.postChain == null || this.tempDepthBuffer == null) {
            return;
         }

         this.tempDepthBuffer.copyDepthFrom(MC.getMainRenderTarget());
         GlStateManager._glBindFramebuffer(36009, MC.getMainRenderTarget().frameBufferId);
      }
   }

   public void resize(int width, int height) {
      if (this.postChain != null) {
         this.postChain.resize(width, height);
         if (this.tempDepthBuffer != null) {
            this.tempDepthBuffer.resize(width, height, Minecraft.ON_OSX);
         }
      }
   }

   private void applyDefaultUniforms() {
      Arrays.stream(this.effects).forEach(e -> e.safeGetUniform("time").set((float)this.time));
      this.defaultUniforms.forEach(pair -> ((Consumer)pair.getSecond()).accept((Uniform)pair.getFirst()));
   }

   public final void applyPostProcess() {
      if (this.isActive) {
         if (!this.initialized) {
            this.init();
         }

         if (this.postChain != null) {
            this.time = this.time + MC.getTimer().getGameTimeDeltaPartialTick(false) / 20.0;
            this.beforeProcess(viewModelMatrix);
            this.applyDefaultUniforms();
            if (!this.isActive) {
               return;
            }

            this.postChain.process(MC.getTimer().getGameTimeDeltaPartialTick(false));
            GlStateManager._glBindFramebuffer(36009, MC.getMainRenderTarget().frameBufferId);
            this.afterProcess();
         }
      }
   }

   public abstract void beforeProcess(Matrix4f var1);

   public abstract void afterProcess();

   public void setActive(boolean active) {
      this.isActive = active;
      if (!active) {
         this.time = 0.0;
      }
   }

   public final boolean isActive() {
      return this.isActive;
   }
}
