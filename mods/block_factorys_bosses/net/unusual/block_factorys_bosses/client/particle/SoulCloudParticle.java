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
public class SoulCloudParticle extends CampfireSmokeParticle {
   private final SpriteSet spriteSet;

   public static SoulCloudParticle.SoulCloudParticleProvider provider(SpriteSet spriteSet) {
      return new SoulCloudParticle.SoulCloudParticleProvider(spriteSet);
   }

   protected SoulCloudParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
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
      this.rCol = 0.25F;
      this.gCol = 0.415F;
      this.bCol = 0.39F;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
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
      if (this.age >= this.lifetime - 5 && this.alpha > 0.01F) {
         this.alpha -= 0.12F;
         if (this.alpha < 0.0F) {
            this.alpha = 0.0F;
         }

         this.setAlpha(this.alpha);
         float t = (this.lifetime - this.age) / this.lifetime;
         if (t < 1.0F) {
            this.rCol = 0.02F;
            this.gCol = 0.02F;
            this.bCol = 0.02F;
         } else {
            this.rCol = 0.25F * (1.0F - t) + 0.02F * t;
            this.gCol = 0.415F * (1.0F - t) + 0.02F * t;
            this.bCol = 0.39F * (1.0F - t) + 0.02F * t;
         }
      }
   }

   public static class SoulCloudParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public SoulCloudParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new SoulCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
