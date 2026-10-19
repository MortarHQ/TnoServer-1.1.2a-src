package net.unusual.block_factorys_bosses.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExplosionParticle extends FlameParticle {
   private final SpriteSet spriteSet;

   public static ExplosionParticle.ExplosionParticleProvider provider(SpriteSet spriteSet) {
      return new ExplosionParticle.ExplosionParticleProvider(spriteSet);
   }

   protected ExplosionParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
      this.spriteSet = spriteSet;
      this.quadSize = 2.0F;
      this.lifetime = 5 + this.random.nextInt(3);
      this.alpha = 0.75F;
      this.roll = (float)Math.random() * (float) (Math.PI * 2);
      this.oRoll = this.roll;
      this.setColor(1.0F, 0.471F, 0.318F);
      this.setSpriteFromAge(spriteSet);
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public float getQuadSize(float scale) {
      return this.quadSize;
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.spriteSet);
      this.rCol = (this.rCol + 1.0F) / 2.0F;
      this.gCol = (this.gCol + 1.0F) / 2.0F;
      this.bCol = (this.bCol + 1.0F) / 2.0F;
   }

   public static class ExplosionParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public ExplosionParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new ExplosionParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
