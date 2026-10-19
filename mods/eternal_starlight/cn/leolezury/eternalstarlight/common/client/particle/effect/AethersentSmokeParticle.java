package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.util.Color;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class AethersentSmokeParticle extends TextureSheetParticle {
   private final Color fromColor = Color.rgbi(163, 76, 186).blend(Color.WHITE, 0.4);
   private final Color fadeColor = Color.rgbi(69, 18, 82);
   private final double initialX;
   private final double initialY;
   private final double initialZ;
   private final double targetX;
   private final double targetY;
   private final double targetZ;

   protected AethersentSmokeParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, SpriteSet spriteSet) {
      super(clientLevel, d, e, f, g, h, i);
      this.lifetime = 100;
      this.initialX = d;
      this.initialY = e;
      this.initialZ = f;
      this.targetX = d + g;
      this.targetY = e + h;
      this.targetZ = f + i;
      this.setSpriteFromAge(spriteSet);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else if (this.age > 10) {
         this.setPos(this.targetX, this.targetY, this.targetZ);
      } else {
         double progress = this.age / 10.0;
         progress = Math.pow(progress, 1.5);
         this.setPos(
            Mth.lerp(progress, this.initialX, this.targetX), Mth.lerp(progress, this.initialY, this.targetY), Mth.lerp(progress, this.initialZ, this.targetZ)
         );
      }

      Color currentColor;
      if (this.age < 30) {
         currentColor = this.fromColor;
      } else if (this.age < 60) {
         double progress = (this.age - 30.0) / 30.0;
         currentColor = this.fromColor.blend(this.fadeColor, Math.pow(progress, 1.5));
      } else {
         currentColor = this.fadeColor;
      }

      this.rCol = currentColor.rf();
      this.gCol = currentColor.gf();
      this.bCol = currentColor.bf();
   }

   public float getQuadSize(float f) {
      return (this.age > 60 ? (float)(Math.pow((this.lifetime - this.age) / 40.0, 1.5) * this.quadSize) : this.quadSize) * 2.5F;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_LIT;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz) {
         return new AethersentSmokeParticle(clientLevel, x, y, z, dx, dy, dz, this.sprites);
      }
   }
}
