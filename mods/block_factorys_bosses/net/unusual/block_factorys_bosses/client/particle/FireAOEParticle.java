package net.unusual.block_factorys_bosses.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FireAOEParticle extends TextureSheetParticle {
   public static FireAOEParticle.FireAoeParticleProvider provider(SpriteSet spriteSet) {
      return new FireAOEParticle.FireAoeParticleProvider(spriteSet);
   }

   protected FireAOEParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.setSize(0.3F, 0.3F);
      this.lifetime = 20;
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.xd = vx * 1.0;
      this.yd = vy * 1.0;
      this.zd = vz * 1.0;
      this.pickSprite(spriteSet);
      this.roll = (float)(Math.random() <= 0.5 ? 0.0 : Math.PI);
      this.oRoll = this.roll;
      this.alpha = 0.0F;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public void tick() {
      super.tick();
      float dragFactor = 0.33333334F;
      this.xd *= dragFactor;
      this.yd *= dragFactor;
      this.zd *= dragFactor;
      this.yd += 0.01F;
      float ageRatio = (float)this.age / this.lifetime;
      if (ageRatio <= 0.11F) {
         float t = ageRatio / 0.11F;
         this.rCol = 1.0F;
         this.gCol = 1.0F;
         this.bCol = 1.0F;
         this.alpha = t;
      } else if (ageRatio <= 0.26F) {
         float t = (ageRatio - 0.11F) / 0.15F;
         this.rCol = 1.0F;
         this.gCol = Mth.lerp(t, 1.0F, 0.831F);
         this.bCol = Mth.lerp(t, 1.0F, 0.0F);
         this.alpha = 1.0F;
      } else if (ageRatio <= 0.5F) {
         float t = (ageRatio - 0.26F) / 0.24F;
         float easedT = t * t * t;
         this.rCol = Mth.lerp(easedT, 1.0F, 0.365F);
         this.gCol = Mth.lerp(easedT, 0.831F, 0.078F);
         this.bCol = Mth.lerp(easedT, 0.0F, 0.016F);
         this.alpha = 1.0F;
      } else {
         float t = (ageRatio - 0.5F) / 0.5F;
         this.rCol = Mth.lerp(t, 0.365F, 0.0F);
         this.gCol = Mth.lerp(t, 0.078F, 0.0F);
         this.bCol = Mth.lerp(t, 0.016F, 0.0F);
         this.alpha = 1.0F - t;
      }
   }

   public static class FireAoeParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public FireAoeParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new FireAOEParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
