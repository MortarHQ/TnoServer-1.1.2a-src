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
public class FireCloudParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;

   public static FireCloudParticle.FireCloudParticleProvider provider(SpriteSet spriteSet) {
      return new FireCloudParticle.FireCloudParticleProvider(spriteSet);
   }

   protected FireCloudParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.spriteSet = spriteSet;
      this.setSize(40.0F, 40.0F);
      this.lifetime = 20;
      this.gravity = 0.0F;
      this.hasPhysics = true;
      this.xd = 0.0;
      this.yd = 0.0;
      this.zd = 0.0;
      this.pickSprite(spriteSet);
      this.rCol = 1.0F;
      this.gCol = 1.0F;
      this.bCol = 1.0F;
      this.alpha = 1.0F;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public void tick() {
      super.tick();
      this.xd *= 0.2F;
      this.yd *= 0.2F;
      this.zd *= 0.2F;
      this.yd += 0.05F;
      float ageRatio = (float)this.age / this.lifetime;
      this.alpha = 1.0F - ageRatio;
   }

   public static class FireCloudParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public FireCloudParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new FireCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
