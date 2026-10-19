package net.unusual.block_factorys_bosses.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RiftParticle extends RisingParticle {
   private final SpriteSet spriteSet;

   public static RiftParticle.RiftParticleProvider provider(SpriteSet spriteSet) {
      return new RiftParticle.RiftParticleProvider(spriteSet);
   }

   protected RiftParticle(ClientLevel world, double x, double y, double z, double lifetime, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
      this.spriteSet = spriteSet;
      this.quadSize = 7.5F;
      this.lifetime = (int)lifetime;
      if (this.lifetime < 1) {
         this.lifetime = 1;
      }

      this.setColor(0.0F, 0.961F, 0.98F);
      this.setSpriteFromAge(spriteSet);
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public float getQuadSize(float scale) {
      return this.quadSize + (float)Math.sin(this.age / 4.0F) * 0.2F;
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.spriteSet);
   }

   public static class RiftParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public RiftParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new RiftParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
