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
public class PoisonSpitParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;
   private float angularVelocity;
   private float angularAcceleration;

   public static PoisonSpitParticle.PoisonSpitParticleProvider provider(SpriteSet spriteSet) {
      return new PoisonSpitParticle.PoisonSpitParticleProvider(spriteSet);
   }

   protected PoisonSpitParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.spriteSet = spriteSet;
      this.setSize(0.2F, 0.2F);
      this.lifetime = Math.max(1, 90 + (this.random.nextInt(4) - 2));
      this.quadSize = this.quadSize * (1.5F + ((float)Math.random() - 0.5F) * 2.0F);
      this.gravity = 0.8F;
      this.hasPhysics = true;
      this.xd = vx * 1.0;
      this.yd = vy * 1.0;
      this.zd = vz * 1.0;
      this.roll = -1.0F;
      this.angularVelocity = 0.3F;
      this.angularAcceleration = 0.0F;
      this.pickSprite(spriteSet);
      this.rCol = 0.651F;
      this.gCol = 1.0F;
      this.bCol = 0.333F;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public void tick() {
      super.tick();
      this.oRoll = this.roll;
      if (this.roll < 3.0F) {
         this.roll = this.roll + this.angularVelocity;
         this.angularVelocity = this.angularVelocity + this.angularAcceleration;
      }

      if (this.onGround) {
         this.age++;
      }

      float t = (float)(this.age * 0.0111);
      if (t < 1.0F) {
         this.rCol = 0.651F * (1.0F - t) + 0.078F * t;
         this.gCol = 1.0F * (1.0F - t) + 0.607F * t;
         this.bCol = 0.333F * (1.0F - t) + 0.129F * t;
      }
   }

   public static class PoisonSpitParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public PoisonSpitParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new PoisonSpitParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
