package team.lodestar.lodestone.handlers;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;
import org.joml.Matrix4f;
import team.lodestar.lodestone.helpers.RenderHelper;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.buffer.LodestoneRenderLayer;
import team.lodestar.lodestone.systems.rendering.rendeertype.ShaderUniformHandler;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;

public class LodestoneRenderHandler {
   public static RenderTarget LODESTONE_DEPTH_CACHE;
   public static LodestoneRenderLayer DEFERRED_RENDER = new LodestoneRenderLayer();
   public static LodestoneRenderLayer LATE_DEFERRED_RENDER = new LodestoneRenderLayer();
   public static Matrix4f MODEL_VIEW;
   public static float FOG_NEAR;
   public static float FOG_FAR;
   public static float FOG_RED;
   public static float FOG_GREEN;
   public static float FOG_BLUE;
   public static FogShape FOG_SHAPE;

   public static void resize(int width, int height) {
      if (LODESTONE_DEPTH_CACHE != null) {
         LODESTONE_DEPTH_CACHE.resize(width, height, Minecraft.ON_OSX);
      }
   }

   public static void render() {
      copyDepthBuffer(LODESTONE_DEPTH_CACHE);
      applyCachedFogData();
      DEFERRED_RENDER.endBatches();
      LATE_DEFERRED_RENDER.endBatches();
      restoreFogData();
   }

   public static void cacheModelViewMatrix(Matrix4f modelViewMatrix) {
      MODEL_VIEW = new Matrix4f(modelViewMatrix);
   }

   public static void restoreModelViewMatrix() {
      setModelViewMatrix(MODEL_VIEW);
   }

   public static void clearModelViewMatrix() {
      setModelViewMatrix(new Matrix4f());
   }

   public static void setModelViewMatrix(Matrix4f modelViewMatrix) {
      RenderSystem.getModelViewMatrix().set(modelViewMatrix);
   }

   public static void copyDepthBuffer(RenderTarget tempRenderTarget) {
      setupDepthBuffer();
      enableStencil();
      if (tempRenderTarget != null) {
         RenderTarget mainRenderTarget = Minecraft.getInstance().getMainRenderTarget();
         tempRenderTarget.copyDepthFrom(mainRenderTarget);
         GlStateManager._glBindFramebuffer(36009, mainRenderTarget.frameBufferId);
      }
   }

   public static void setupDepthBuffer() {
      if (LODESTONE_DEPTH_CACHE == null) {
         LODESTONE_DEPTH_CACHE = new TextureTarget(
            Minecraft.getInstance().getMainRenderTarget().width, Minecraft.getInstance().getMainRenderTarget().height, true, Minecraft.ON_OSX
         );
      }
   }

   public static void enableStencil() {
      if (Minecraft.getInstance().getMainRenderTarget().isStencilEnabled()) {
         LODESTONE_DEPTH_CACHE.enableStencil();
      }
   }

   public static void cacheFogData(RenderFog event) {
      FOG_NEAR = event.getNearPlaneDistance();
      FOG_FAR = event.getFarPlaneDistance();
      FOG_SHAPE = event.getFogShape();
   }

   public static void cacheFogColors(ComputeFogColor event) {
      FOG_RED = event.getRed();
      FOG_GREEN = event.getGreen();
      FOG_BLUE = event.getBlue();
   }

   public static void applyCachedFogData() {
      float[] shaderFogColor = RenderSystem.getShaderFogColor();
      float fogRed = shaderFogColor[0];
      float fogGreen = shaderFogColor[1];
      float fogBlue = shaderFogColor[2];
      float shaderFogStart = RenderSystem.getShaderFogStart();
      float shaderFogEnd = RenderSystem.getShaderFogEnd();
      FogShape shaderFogShape = RenderSystem.getShaderFogShape();
      RenderSystem.setShaderFogStart(FOG_NEAR);
      RenderSystem.setShaderFogEnd(FOG_FAR);
      RenderSystem.setShaderFogShape(FOG_SHAPE);
      RenderSystem.setShaderFogColor(FOG_RED, FOG_GREEN, FOG_BLUE);
      FOG_RED = fogRed;
      FOG_GREEN = fogGreen;
      FOG_BLUE = fogBlue;
      FOG_NEAR = shaderFogStart;
      FOG_FAR = shaderFogEnd;
      FOG_SHAPE = shaderFogShape;
   }

   public static void restoreFogData() {
      RenderSystem.setShaderFogStart(FOG_NEAR);
      RenderSystem.setShaderFogEnd(FOG_FAR);
      RenderSystem.setShaderFogShape(FOG_SHAPE);
      RenderSystem.setShaderFogColor(FOG_RED, FOG_GREEN, FOG_BLUE);
   }

   public static void updateUniforms(RenderType renderType) {
      Optional<ShaderInstance> optional = RenderHelper.getShader(renderType);
      if (optional.isPresent()) {
         ShaderInstance shader = optional.get();
         if (renderType instanceof LodestoneRenderType lodestoneRenderType) {
            ShaderUniformHandler handler = lodestoneRenderType.getUniformHandler();
            if (handler != null) {
               handler.updateShaderData(shader);
            }
         }

         shader.setSampler("SceneDepthBuffer", LODESTONE_DEPTH_CACHE.getDepthTextureId());
         shader.setSampler("SceneDiffuseBuffer", Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
         shader.safeGetUniform("InvProjMat").set(new Matrix4f(RenderSystem.getProjectionMatrix()).invert());
      }
   }

   public static void resetUniforms(RenderType renderType) {
      Optional<ShaderInstance> optional = RenderHelper.getShader(renderType);
      if (optional.isPresent() && optional.get() instanceof ExtendedShaderInstance shader) {
         shader.setUniformDefaults();
      }
   }
}
