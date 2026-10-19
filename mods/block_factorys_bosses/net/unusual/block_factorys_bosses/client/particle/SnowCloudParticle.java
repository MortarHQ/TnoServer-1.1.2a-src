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
import net.unusual.block_factorys_bosses.procedures.SnowCloudParticleVisualScaleProcedure;

@OnlyIn(Dist.CLIENT)
public class SnowCloudParticle extends CampfireSmokeParticle {
   private final SpriteSet spriteSet;

   public static SnowCloudParticle.SnowCloudParticleProvider provider(SpriteSet spriteSet) {
      return new SnowCloudParticle.SnowCloudParticleProvider(spriteSet);
   }

   protected SnowCloudParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
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
      this.rCol = 1.0F;
      this.gCol = 1.0F;
      this.bCol = 1.0F;
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public float getQuadSize(float scale) {
      return super.getQuadSize(scale) * (float)SnowCloudParticleVisualScaleProcedure.execute(this.age);
   }

   public void tick() {
      super.tick();
      this.oRoll = this.roll;
      if (this.age >= this.lifetime - 10 && this.alpha > 0.01F) {
         this.alpha -= 0.12F;
         if (this.alpha < 0.0F) {
            this.alpha = 0.0F;
         }

         this.setAlpha(this.alpha);
         float t = 1.0F - this.alpha;
         this.rCol = 1.0F - 0.832F * t;
         this.gCol = 1.0F - 0.21600002F * t;
         this.bCol = 1.0F;
      }
   }

   public static class SnowCloudParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public SnowCloudParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new SnowCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
