package cn.leolezury.eternalstarlight.common.client.particle.effect;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class EnergyParticle extends RisingParticle {
   protected EnergyParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz) {
      super(clientLevel, x, y, z, dx, dy, dz);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void move(double x, double y, double z) {
      this.setBoundingBox(this.getBoundingBox().move(x, y, z));
      this.setLocationFromBoundingbox();
   }

   public float getQuadSize(float f) {
      float progress = (this.age + f) / this.lifetime;
      return this.quadSize * (1.0F - progress * progress * 0.5F);
   }

   public int getLightColor(float f) {
      return 15728880;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet spriteSet) {
         this.sprite = spriteSet;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         EnergyParticle particle = new EnergyParticle(level, x, y, z, dx, dy, dz);
         particle.pickSprite(this.sprite);
         return particle;
      }
   }
}
