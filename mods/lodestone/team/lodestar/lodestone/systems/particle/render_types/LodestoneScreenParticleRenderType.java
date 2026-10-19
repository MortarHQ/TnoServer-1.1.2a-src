package team.lodestar.lodestone.systems.particle.render_types;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.function.Supplier;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import team.lodestar.lodestone.registry.client.LodestoneShaders;

public interface LodestoneScreenParticleRenderType {
   LodestoneScreenParticleRenderType ADDITIVE = new LodestoneScreenParticleRenderType() {
      @Override
      public BufferBuilder begin(Tesselator tesselator, TextureManager manager) {
         RenderSystem.depthMask(false);
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(770, 1);
         RenderSystem.setShader(LodestoneShaders.SCREEN_PARTICLE.supplyShaderInstance());
         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
      }

      @Override
      public void end(BufferBuilder builder) {
         MeshData meshdata = builder.build();
         if (meshdata != null) {
            BufferUploader.drawWithShader(meshdata);
         }

         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
      }
   };
   LodestoneScreenParticleRenderType TRANSPARENT = new LodestoneScreenParticleRenderType() {
      @Override
      public BufferBuilder begin(Tesselator tesselator, TextureManager manager) {
         RenderSystem.depthMask(false);
         RenderSystem.enableBlend();
         RenderSystem.setShader(LodestoneShaders.SCREEN_PARTICLE.supplyShaderInstance());
         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
      }

      @Override
      public void end(BufferBuilder builder) {
         MeshData meshdata = builder.build();
         if (meshdata != null) {
            BufferUploader.drawWithShader(meshdata);
         }

         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
      }
   };
   LodestoneScreenParticleRenderType LUMITRANSPARENT = new LodestoneScreenParticleRenderType() {
      @Override
      public BufferBuilder begin(Tesselator tesselator, TextureManager manager) {
         RenderSystem.depthMask(false);
         RenderSystem.enableBlend();
         Supplier<ShaderInstance> instance = LodestoneShaders.SCREEN_PARTICLE.supplyShaderInstance();
         RenderSystem.setShader(instance);
         instance.get().safeGetUniform("LumiTransparency").set(1.0F);
         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         return tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
      }

      @Override
      public void end(BufferBuilder builder) {
         MeshData meshdata = builder.build();
         if (meshdata != null) {
            BufferUploader.drawWithShader(meshdata);
         }

         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
         Supplier<ShaderInstance> instance = LodestoneShaders.SCREEN_PARTICLE.supplyShaderInstance();
         instance.get().safeGetUniform("LumiTransparency").set(0.0F);
      }
   };

   BufferBuilder begin(Tesselator var1, TextureManager var2);

   void end(BufferBuilder var1);
}
