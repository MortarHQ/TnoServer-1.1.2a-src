package twilightforest.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class SnowWarningParticle extends SnowParticle {
   SnowWarningParticle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz, float scale, int lifetime) {
      super(level, x, y, z, vx, vy, vz, scale);
      this.lifetime = lifetime;
   }

   @Override
   public void tick() {
      super.tick();
      this.yd -= 0.02;
   }

   public record ExtendedFactory(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         SnowWarningParticle particle = new SnowWarningParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 1.0F, 100);
         particle.pickSprite(this.sprite);
         return particle;
      }
   }

   public record SimpleFactory(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         SnowWarningParticle particle = new SnowWarningParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 1.0F, 50);
         particle.pickSprite(this.sprite);
         return particle;
      }
   }
}
