package cn.leolezury.eternalstarlight.common.client.particle.environment;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;

public class AshenSnowParticle extends BaseAshSmokeParticle {
   protected AshenSnowParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
      super(level, x, y, z, 1.0F, 1.0F, 1.0F, xSpeed, ySpeed, zSpeed, 1.25F, sprites, 1.0F, 20, 0.0125F, true);
      this.rCol = this.gCol = this.bCol = 1.0F;
      Vec3 speed = new Vec3(xSpeed, ySpeed, zSpeed).normalize().scale(0.15);
      this.xd = speed.x() * 1.75;
      this.yd = speed.y() * 0.2;
      this.zd = speed.z() * 1.75;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xs, double ys, double zs) {
         return new AshenSnowParticle(level, x, y, z, xs, ys, zs, this.sprites);
      }
   }
}
