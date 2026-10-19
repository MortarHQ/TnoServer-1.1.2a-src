package com.finderfeed.fdlib.util.client.particles.ball_particle;

import com.finderfeed.fdlib.systems.particle.FDParticleRenderType;
import com.finderfeed.fdlib.util.math.ComplexEasingFunction;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import com.finderfeed.fdlib.util.rendering.FDEasings;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class BallParticle extends TextureSheetParticle {
   public static final ResourceLocation LOCATION = ResourceLocation.tryBuild("fdlib", "ball_particle");
   private BallParticleOptions options;
   private ComplexEasingFunction scalingFunction;
   private float quadSizeO;
   private float quadSizeC;
   private float maxQuadSize;
   public static final ParticleRenderType RENDER_TYPE = new FDParticleRenderType() {
      @Nullable
      public BufferBuilder begin(Tesselator bufferBuilder, TextureManager textureManager) {
         RenderSystem.depthMask(false);
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(770, 1);
         RenderSystem.setShader(GameRenderer::getParticleShader);
         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
         Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_PARTICLES).setBlurMipmap(true, true);
         return bufferBuilder.begin(Mode.QUADS, DefaultVertexFormat.PARTICLE);
      }

      @Override
      public void end() {
         Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();
      }

      public boolean isTranslucent() {
         return true;
      }

      @Override
      public String toString() {
         return "fdlib:ball_particle";
      }
   };

   public BallParticle(BallParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
      super(level, x, y, z, xd, yd, zd);
      this.options = options;
      this.x = x;
      this.y = y;
      this.z = z;
      this.xd = xd;
      this.yd = yd;
      this.zd = zd;
      this.lifetime = options.scalingOptions.fullTime();
      this.scalingFunction = ComplexEasingFunction.builder()
         .addArea(options.scalingOptions.inTime, FDEasings::linear)
         .addArea(options.scalingOptions.stayTime, FDEasings::one)
         .addArea(options.scalingOptions.outTime, FDEasings::reversedLinear)
         .build();
      this.friction = options.friction;
      this.hasPhysics = options.hasPhysics;
      this.maxQuadSize = options.size;
      this.quadSizeO = this.scalingFunction.apply(0.0F) * this.maxQuadSize;
      this.quadSizeC = this.quadSizeO;
      this.rCol = options.color.r;
      this.gCol = options.color.g;
      this.bCol = options.color.b;
      this.alpha = options.color.a;
      options.particleProcessor.init(this);
   }

   public void render(VertexConsumer vertex, Camera camera, float pticks) {
      this.quadSize = FDMathUtil.lerp(this.quadSizeO, this.quadSizeC, pticks);

      for (int i = 0; i < Math.min(this.options.brightness, 30); i++) {
         super.render(vertex, camera, pticks);
      }
   }

   public void tick() {
      this.options.particleProcessor.processParticle(this);
      super.tick();
      this.quadSizeO = this.quadSizeC;
      this.quadSizeC = this.scalingFunction.apply(this.age) * this.maxQuadSize;
   }

   protected int getLightColor(float pticks) {
      return 15728880;
   }

   protected float getU0() {
      float v = super.getU0();
      ParticleEngine engine = Minecraft.getInstance().particleEngine;
      int w = engine.textureAtlas.width;
      return v + 1.0F / w;
   }

   protected float getU1() {
      float v = super.getU1();
      ParticleEngine engine = Minecraft.getInstance().particleEngine;
      int w = engine.textureAtlas.width;
      return v - 1.0F / w;
   }

   protected float getV0() {
      float v = super.getV0();
      ParticleEngine engine = Minecraft.getInstance().particleEngine;
      int w = engine.textureAtlas.height;
      return v + 1.0F / w;
   }

   protected float getV1() {
      float v = super.getV1();
      ParticleEngine engine = Minecraft.getInstance().particleEngine;
      int w = engine.textureAtlas.height;
      return v - 1.0F / w;
   }

   public ParticleRenderType getRenderType() {
      return RENDER_TYPE;
   }

   public static class Factory implements ParticleProvider<BallParticleOptions> {
      private SpriteSet spriteSet;

      public Factory(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      @Nullable
      public Particle createParticle(BallParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
         BallParticle particle = new BallParticle(options, level, x, y, z, xd, yd, zd);
         particle.pickSprite(this.spriteSet);
         return particle;
      }
   }
}
