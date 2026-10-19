package com.finderfeed.raids_enhanced.content.particles.slash_particle;

import com.finderfeed.fdlib.util.math.FDMathUtil;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class SlashParticle extends TextureSheetParticle {
   public SlashParticleOptions options;
   public SpriteSet spriteSet;

   public SlashParticle(SpriteSet spriteSet, SlashParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
      super(level, x, y, z, xd, yd, zd);
      this.x = x;
      this.y = y;
      this.z = z;
      this.xd = xd;
      this.yd = yd;
      this.zd = zd;
      this.options = options;
      this.quadSize = options.size;
      this.lifetime = options.lifetime;
      this.setSpriteFromAge(spriteSet);
      this.spriteSet = spriteSet;
   }

   public void render(VertexConsumer vertex, Camera camera, float pticks) {
      Matrix4f mat = new Matrix4f();
      Vec3 thisPos = new Vec3(FDMathUtil.lerp(this.xo, this.x, pticks), FDMathUtil.lerp(this.yo, this.y, pticks), FDMathUtil.lerp(this.zo, this.z, pticks));
      Vec3 offset = thisPos.subtract(camera.getPosition());
      mat.translate((float)offset.x, (float)offset.y, (float)offset.z);
      FDRenderUtil.applyMovementMatrixRotations(mat, this.options.direction);
      mat.rotateY(this.options.pitch);
      float u1 = this.getU0();
      float u2 = this.getU1();
      float v1 = this.getV0();
      float v2 = this.getV1();
      if (this.options.reversedSide) {
         u1 = this.getU1();
         u2 = this.getU0();
      }

      float w = this.options.size;
      vertex.addVertex(mat, -w / 2.0F, -w / 2.0F, 0.0F).setUv(u1, v1).setColor(1.0F, 1.0F, 1.0F, 1.0F).setLight(15728880);
      vertex.addVertex(mat, -w / 2.0F, w / 2.0F, 0.0F).setUv(u1, v2).setColor(1.0F, 1.0F, 1.0F, 1.0F).setLight(15728880);
      vertex.addVertex(mat, w / 2.0F, w / 2.0F, 0.0F).setUv(u2, v2).setColor(1.0F, 1.0F, 1.0F, 1.0F).setLight(15728880);
      vertex.addVertex(mat, w / 2.0F, -w / 2.0F, 0.0F).setUv(u2, v1).setColor(1.0F, 1.0F, 1.0F, 1.0F).setLight(15728880);
      vertex.addVertex(mat, w / 2.0F, -w / 2.0F, 0.0F).setUv(u2, v1).setColor(1.0F, 1.0F, 1.0F, 1.0F).setLight(15728880);
      vertex.addVertex(mat, w / 2.0F, w / 2.0F, 0.0F).setUv(u2, v2).setColor(1.0F, 1.0F, 1.0F, 1.0F).setLight(15728880);
      vertex.addVertex(mat, -w / 2.0F, w / 2.0F, 0.0F).setUv(u1, v2).setColor(1.0F, 1.0F, 1.0F, 1.0F).setLight(15728880);
      vertex.addVertex(mat, -w / 2.0F, -w / 2.0F, 0.0F).setUv(u1, v1).setColor(1.0F, 1.0F, 1.0F, 1.0F).setLight(15728880);
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.spriteSet);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_LIT;
   }

   public static class Provider implements ParticleProvider<SlashParticleOptions> {
      private SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      @Nullable
      public Particle createParticle(
         SlashParticleOptions p_107421_,
         ClientLevel p_107422_,
         double p_107423_,
         double p_107424_,
         double p_107425_,
         double p_107426_,
         double p_107427_,
         double p_107428_
      ) {
         return new SlashParticle(this.spriteSet, p_107421_, p_107422_, p_107423_, p_107424_, p_107425_, p_107426_, p_107427_, p_107428_);
      }
   }
}
