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
public class RedSparkParticle extends TextureSheetParticle {
   private float xOffset = (float)(Math.random() * 20.0);
   private float zOffset = (float)(Math.random() * 20.0);

   public static RedSparkParticle.RedSparkParticleProvider provider(SpriteSet spriteSet) {
      return new RedSparkParticle.RedSparkParticleProvider(spriteSet);
   }

   protected RedSparkParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.yd = (Math.random() * 2.0 - 1.0) * 0.01 + 0.25;
      this.quadSize = 0.5F;
      this.lifetime = 40;
      this.setColor(0.87F, 0.18F, 0.07F);
      this.pickSprite(spriteSet);
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public void tick() {
      super.tick();
      this.xd = Math.sin(this.age / 3.0F + this.xOffset) * 0.04;
      this.zd = Math.sin(this.age / 3.0F + this.zOffset) * 0.04;
      this.alpha = 1.0F - (float)this.age / this.lifetime;
   }

   public static class RedSparkParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public RedSparkParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new RedSparkParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
