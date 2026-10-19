package net.unusual.block_factorys_bosses.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DustCloudParticle extends CampfireSmokeParticle {
   private final SpriteSet spriteSet;

   public static DustCloudParticle.DustCloudParticleProvider provider(SpriteSet spriteSet) {
      return new DustCloudParticle.DustCloudParticleProvider(spriteSet);
   }

   protected DustCloudParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, 0.0, 0.0, 0.0, true);
      this.spriteSet = spriteSet;
      this.setSize(0.9F, 0.9F);
      this.lifetime = 22 + this.random.nextInt(5);
      this.gravity = 0.0F;
      this.hasPhysics = true;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
      this.roll = 0.1F * (float)Math.random();
      this.pickSprite(spriteSet);
      this.setAlpha(0.9F);
      float rdm = (float)Math.random();
      this.rCol = 0.639F * (1.0F - rdm) + 0.533F * rdm;
      this.gCol = 0.518F * (1.0F - rdm) + 0.396F * rdm;
      this.bCol = 0.404F * (1.0F - rdm) + 0.251F * rdm;
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public float getQuadSize(float scale) {
      return super.getQuadSize(scale) * (1.0F + (float)(this.lifetime - this.age) / this.lifetime);
   }

   public void tick() {
      super.tick();
      this.xd *= 0.9F;
      this.yd *= 0.9F;
      this.zd *= 0.9F;
      this.oRoll = this.roll;
      this.roll = this.roll + (float)(this.zd * 0.1F);
      if (this.age >= this.lifetime - 5 && this.alpha > 0.01F) {
         this.alpha -= 0.12F;
         if (this.alpha < 0.0F) {
            this.alpha = 0.0F;
         }

         this.setAlpha(this.alpha);
      }
   }

   public static class DustCloudParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public DustCloudParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new DustCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
