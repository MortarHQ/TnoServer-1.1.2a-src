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
public class StarParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;

   public static StarParticle.StarParticleProvider provider(SpriteSet spriteSet) {
      return new StarParticle.StarParticleProvider(spriteSet);
   }

   protected StarParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.spriteSet = spriteSet;
      this.quadSize = 0.5F;
      this.lifetime = 30;
      this.setColor(0.0F, 0.961F, 0.98F);
      this.setAlpha(0.0F);
      this.pickSprite(spriteSet);
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public float getQuadSize(float scale) {
      return this.age < 15 ? this.age / 15.0F * this.quadSize : (30.0F - this.age) / 15.0F * this.quadSize;
   }

   public void tick() {
      super.tick();
      this.setAlpha(this.getQuadSize(1.0F) / this.quadSize);
   }

   public static class StarParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public StarParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new StarParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
