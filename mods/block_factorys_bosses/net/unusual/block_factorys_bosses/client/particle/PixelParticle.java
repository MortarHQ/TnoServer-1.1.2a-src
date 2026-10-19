package net.unusual.block_factorys_bosses.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ColorParticleOption;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PixelParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;

   public static PixelParticle.PixelParticleProvider provider(SpriteSet spriteSet) {
      return new PixelParticle.PixelParticleProvider(spriteSet);
   }

   protected PixelParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.xd = (Math.random() * 2.0 - 1.0) * 0.01;
      this.yd = (Math.random() * 2.0 - 1.0) * 0.01 + 0.05;
      this.zd = (Math.random() * 2.0 - 1.0) * 0.01;
      this.spriteSet = spriteSet;
      this.quadSize = 0.5F;
      this.lifetime = 10;
      this.setColor(0.0F, 0.961F, 0.98F);
      this.pickSprite(spriteSet);
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public float getQuadSize(float scale) {
      return this.age > 7 ? (10.0F - this.age) / 3.0F * (this.quadSize + 0.25F) : this.age / 7.0F * this.quadSize + 0.25F;
   }

   public void tick() {
      super.tick();
      this.setAlpha((10.0F - this.age) / 10.0F);
      if (this.age > 6) {
         this.rCol = (this.rCol + 1.0F) / 2.0F;
         this.gCol = (this.gCol + 1.0F) / 2.0F;
         this.bCol = (this.bCol + 1.0F) / 2.0F;
      }
   }

   public static class PixelParticleProvider implements ParticleProvider<ColorParticleOption> {
      private final SpriteSet spriteSet;

      public PixelParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(ColorParticleOption typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         PixelParticle particle = new PixelParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
         particle.setColor(typeIn.getRed(), typeIn.getGreen(), typeIn.getBlue());
         return particle;
      }
   }
}
