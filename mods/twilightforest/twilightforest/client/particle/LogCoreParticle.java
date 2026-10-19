package twilightforest.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

public class LogCoreParticle extends RisingParticle {
   LogCoreParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
      super(pLevel, pX, pY, pZ, 0.0, 0.02, 0.0);
      this.rCol = (float)pXSpeed;
      this.gCol = (float)pYSpeed;
      this.bCol = (float)pZSpeed;
      this.alpha = 0.0F;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public void move(double pX, double pY, double pZ) {
      this.setBoundingBox(this.getBoundingBox().move(pX, pY, pZ));
      this.setLocationFromBoundingbox();
   }

   public float getQuadSize(float pScaleFactor) {
      float f = (this.age + pScaleFactor) / this.lifetime;
      return this.quadSize * (1.0F - f * f * 0.5F);
   }

   public int getLightColor(float partialTicks) {
      float f = (this.age + partialTicks) / this.lifetime;
      f = Mth.clamp(f, 0.0F, 1.0F);
      int i = super.getLightColor(partialTicks);
      int j = i & 0xFF;
      int k = i >> 16 & 0xFF;
      j += (int)(f * 15.0F * 16.0F);
      if (j > 240) {
         j = 240;
      }

      return j | k << 16;
   }

   public void tick() {
      super.tick();
      float f = (float)this.age / this.lifetime;
      this.alpha = Math.min(f * 1.35F, 1.0F);
   }

   public AABB getRenderBoundingBox(float partialTicks) {
      return AABB.INFINITE;
   }

   public record Factory(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double x2, double y2, double z2) {
         LogCoreParticle logCoreParticle = new LogCoreParticle(level, x, y, z, x2, y2, z2);
         logCoreParticle.pickSprite(this.sprite);
         return logCoreParticle;
      }
   }
}
