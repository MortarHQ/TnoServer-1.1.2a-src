package twilightforest.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class CloudPuffParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   CloudPuffParticle(ClientLevel level, double X, double Y, double Z, double XSpeed, double YSpeed, double ZSpeed, SpriteSet sprites) {
      super(level, X, Y, Z, 0.0, 0.0, 0.0);
      this.friction = 0.96F;
      this.sprites = sprites;
      this.xd *= 0.1;
      this.yd *= 0.1;
      this.zd *= 0.1;
      this.xd += XSpeed;
      this.yd += YSpeed;
      this.zd += ZSpeed;
      float f1 = 1.0F - (float)(Math.random() * 0.3F);
      this.rCol = f1;
      this.gCol = f1;
      this.bCol = f1;
      this.quadSize *= 0.875F;
      int i = (int)(8.0 / (Math.random() * 0.8 + 0.3));
      this.lifetime = (int)Math.max(i * 2.5F, 1.0F);
      this.hasPhysics = false;
      this.setSpriteFromAge(sprites);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public float getQuadSize(float partialTick) {
      return this.quadSize * Mth.clamp((this.age + partialTick) / this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   public void tick() {
      super.tick();
      if (!this.removed) {
         this.setSpriteFromAge(this.sprites);
      }
   }

   public record Factory(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new CloudPuffParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
      }
   }
}
