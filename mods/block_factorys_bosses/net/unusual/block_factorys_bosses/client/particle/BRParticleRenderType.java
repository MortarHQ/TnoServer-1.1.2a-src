package net.unusual.block_factorys_bosses.client.particle;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.jetbrains.annotations.Nullable;

public interface BRParticleRenderType extends ParticleRenderType {
   ParticleRenderType NORMAL_TRANSLUCENT = new BRParticleRenderType() {
      @Override
      public void end() {
         if (Minecraft.useShaderTransparency()) {
            Minecraft instance = Minecraft.getInstance();
            RenderTarget particlesTarget = instance.levelRenderer.getParticlesTarget();
            particlesTarget.copyDepthFrom(instance.getMainRenderTarget());
            particlesTarget.bindWrite(false);
         }

         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
      }

      @Nullable
      public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
         if (Minecraft.useShaderTransparency()) {
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
         }

         RenderSystem.depthMask(false);
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(770, 771);
         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.PARTICLE);
      }
   };
   BRParticleRenderType PARTICLE_SHEET_BLEND = new BRParticleRenderType() {
      public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
         if (Minecraft.useShaderTransparency()) {
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
         }

         RenderSystem.depthMask(false);
         RenderSystem.enableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
         RenderStateShard.LIGHTMAP.setupRenderState();
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.PARTICLE);
      }

      @Override
      public void end() {
         if (Minecraft.useShaderTransparency()) {
            BRParticleRenderType.bindParticleRenderTarget();
         }
      }

      @Override
      public String toString() {
         return "block_factorys_bosses:PARTICLE_SHEET_BLEND";
      }
   };
   ParticleRenderType PARTICLE_SHEET_BLEND_NO_CULL = new BRParticleRenderType() {
      public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
         RenderSystem.disableCull();
         return PARTICLE_SHEET_BLEND.begin(tesselator, textureManager);
      }

      @Override
      public void end() {
         RenderSystem.enableCull();
         PARTICLE_SHEET_BLEND.end();
      }

      @Override
      public String toString() {
         return "block_factorys_bosses:PARTICLE_SHEET_BLEND_NO_CULL";
      }
   };

   static void bindParticleRenderTarget() {
      Minecraft instance = Minecraft.getInstance();
      RenderTarget particlesTarget = instance.levelRenderer.getParticlesTarget();
      particlesTarget.copyDepthFrom(instance.getMainRenderTarget());
      particlesTarget.bindWrite(false);
   }

   void end();
}
