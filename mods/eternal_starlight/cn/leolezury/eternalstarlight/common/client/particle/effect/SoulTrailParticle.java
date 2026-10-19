package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.util.Easing;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class SoulTrailParticle extends SimpleAnimatedParticle {
   protected SoulTrailParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, SpriteSet spriteSet) {
      super(level, x, y, z, spriteSet, 0.01F);
      this.setParticleSpeed(dx, dy, dz);
      this.friction = 0.99F;
      this.quadSize *= 0.75F;
      this.lifetime = 15 + this.random.nextInt(10);
      this.setSpriteFromAge(spriteSet);
   }

   public float getQuadSize(float f) {
      return Easing.IN_CUBIC.interpolate(Mth.abs(Math.min(this.age + f, this.lifetime) / this.lifetime - 0.5F) * 2.0F, this.quadSize, 0.0F);
   }

   public int getLightColor(float f) {
      return 15728880;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new SoulTrailParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
      }
   }
}
