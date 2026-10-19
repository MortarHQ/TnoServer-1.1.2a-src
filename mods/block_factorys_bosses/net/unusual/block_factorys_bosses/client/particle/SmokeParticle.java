package net.unusual.block_factorys_bosses.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmokeParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;
   private final float alpha = (float)(1.0 - Math.random()) * 0.25F;

   public static SmokeParticle.SmokeParticleProvider provider(SpriteSet spriteSet) {
      return new SmokeParticle.SmokeParticleProvider(spriteSet);
   }

   protected SmokeParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.spriteSet = spriteSet;
      this.quadSize = 0.8F;
      this.lifetime = 18;
      this.gravity = 0.0F;
      this.roll = (float)((Math.PI * 2) * Math.random());
      this.oRoll = this.roll;
      this.xd = vx;
      this.yd = vy;
      this.zd = vz;
      this.friction = 0.75F;
      this.setSpriteFromAge(spriteSet);
      this.setAlpha(this.alpha);
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public float getQuadSize(float scale) {
      return this.quadSize;
   }

   public void tick() {
      super.tick();
      if (!this.removed) {
         this.setSpriteFromAge(this.spriteSet);
         this.setAlpha(this.alpha * (1.0F - (float)this.age / this.lifetime));
      }
   }

   public static class SmokeParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public SmokeParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new SmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
