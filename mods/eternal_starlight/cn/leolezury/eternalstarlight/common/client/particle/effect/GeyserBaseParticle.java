package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.GeyserBaseParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.joml.Vector3f;

public class GeyserBaseParticle extends BaseAshSmokeParticle {
   private GeyserBaseParticle(
      ClientLevel level,
      double x,
      double y,
      double z,
      double xAux,
      double yAux,
      double zAux,
      Vector3f color,
      int strength,
      float burstImpulseBase,
      SpriteSet sprites
   ) {
      super(
         level,
         x,
         y,
         z,
         burstImpulseBase + 0.25F * strength,
         burstImpulseBase + 0.25F * strength,
         burstImpulseBase + 0.25F * strength,
         xAux,
         yAux,
         zAux,
         3.0F + 0.125F * strength,
         sprites,
         0.0F,
         0,
         0.0F,
         true
      );
      this.friction = 0.725F;
      this.setColor(color.x(), color.y(), color.z());
      this.yd = Math.abs(this.yd);
      float lifetimeFactor = 0.8F + 0.2F * level.getRandom().nextFloat();
      this.lifetime = (int)(25.0F * lifetimeFactor);
   }

   public static class Provider implements ParticleProvider<GeyserBaseParticleOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet sprites) {
         this.sprites = sprites;
      }

      public Particle createParticle(GeyserBaseParticleOptions options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux) {
         double randomX = x + (level.random.nextFloat() - 0.5F) * 0.5F;
         double randomY = y + (level.random.nextFloat() - 0.5F) * 0.5F + 0.2F;
         double randomZ = z + (level.random.nextFloat() - 0.5F) * 0.5F;
         return new GeyserBaseParticle(
            level, randomX, randomY, randomZ, xAux, yAux, zAux, options.color(), options.strength(), options.burstImpulseBase(), this.sprites
         );
      }
   }
}
