package twilightforest.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class PerfectDodgeParticle extends PortalParticle {
   public static final int LIFE_TIME = 10;

   public PerfectDodgeParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(level, x, y, z, xSpeed, ySpeed, zSpeed);
      this.lifetime = (int)(10.0 * (1.0 + this.level.random.nextFloat() / 1.5));
   }

   public void tick() {
      super.tick();
      float f = (float)this.age / this.lifetime;
      this.y -= 1.0F - f;
      this.setPos(this.x, this.y, this.z);
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet sprite) {
         this.sprite = sprite;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         PerfectDodgeParticle perfectDodgeParticle = new PerfectDodgeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
         perfectDodgeParticle.pickSprite(this.sprite);
         return perfectDodgeParticle;
      }
   }
}
