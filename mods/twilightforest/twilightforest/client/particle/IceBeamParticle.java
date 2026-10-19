package twilightforest.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class IceBeamParticle extends TextureSheetParticle {
   final float initialParticleScale;

   public IceBeamParticle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz, float scale) {
      super(level, x, y, z, 0.0, 0.0, 0.0);
      this.xd *= 0.1;
      this.yd *= 0.1;
      this.zd *= 0.1;
      this.xd += vx * 0.4;
      this.yd += vy * 0.4;
      this.zd += vz * 0.4;
      this.rCol = this.gCol = this.bCol = 1.0F;
      this.quadSize *= 0.75F;
      this.quadSize *= scale;
      this.initialParticleScale = this.quadSize;
      this.lifetime = 50;
      this.hasPhysics = true;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      }

      this.move(this.xd, this.yd, this.zd);
      this.xd *= 0.96;
      this.yd *= 0.96;
      this.zd *= 0.96;
      if (this.onGround) {
         this.xd *= 0.7;
         this.zd *= 0.7;
      }
   }

   public int getLightColor(float partialTicks) {
      return 15728880;
   }

   public record Factory(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         IceBeamParticle particle = new IceBeamParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 0.75F);
         particle.pickSprite(this.sprite);
         return particle;
      }
   }
}
