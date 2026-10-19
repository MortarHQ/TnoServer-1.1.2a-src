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
public class RedSmokeParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;
   private final float alpha = 0.5F;

   public static RedSmokeParticle.RedSmokeParticleProvider provider(SpriteSet spriteSet) {
      return new RedSmokeParticle.RedSmokeParticleProvider(spriteSet);
   }

   protected RedSmokeParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
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
      this.friction = 0.0F;
      this.setSpriteFromAge(spriteSet);
      this.setAlpha(0.5F);
      float ratio = (float)(Math.random() * 0.75);
      this.setColor(ratio, ratio * 0.44F, ratio * 0.31F);
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public float getQuadSize(float scale) {
      return this.quadSize;
   }

   public void tick() {
      super.tick();
      this.yd += 0.1;
      if (!this.removed) {
         this.setSpriteFromAge(this.spriteSet);
         this.setAlpha(0.5F * (1.0F - (float)this.age / this.lifetime));
      }
   }

   public static class RedSmokeParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public RedSmokeParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new RedSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
