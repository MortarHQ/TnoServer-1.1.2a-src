package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class ParticleSparkle extends TextureSheetParticle {
   private final float red;
   private final float green;
   private final float blue;
   private final float scale;

   public ParticleSparkle(
      ClientLevel world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b, double scale, int duration
   ) {
      super(world, x, y, z);
      this.scale = (float)scale * 1.0F;
      this.lifetime = duration;
      this.xd = vx;
      this.yd = vy;
      this.zd = vz;
      this.red = (float)r;
      this.green = (float)g;
      this.blue = (float)b;
      this.hasPhysics = false;
   }

   protected float getU1() {
      return super.getU1() - (super.getU1() - super.getU0()) / 16.0F;
   }

   protected float getV1() {
      return super.getV1() - (super.getV1() - super.getV0()) / 16.0F;
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      float a = (this.age + partialTicks) / this.lifetime;
      this.alpha = -4.0F * a * a + 4.0F * a;
      if (this.alpha < 0.01) {
         this.alpha = 0.01F;
      }

      this.quadSize = (-4.0F * a * a + 4.0F * a) * this.scale;
      super.render(buffer, renderInfo, partialTicks);
   }

   public ParticleRenderType getRenderType() {
      return MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   }

   public static final class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ParticleSparkle particle = new ParticleSparkle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, 1.0, 1.0, 1.0, 0.4, 13);
         particle.pickSprite(this.spriteSet);
         return particle;
      }
   }
}
