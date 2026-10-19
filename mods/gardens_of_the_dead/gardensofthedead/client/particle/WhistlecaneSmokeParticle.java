package gardensofthedead.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class WhistlecaneSmokeParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   private WhistlecaneSmokeParticle(ClientLevel level, double x, double y, double z, double u, double v, double w, SpriteSet sprites) {
      super(level, x, y, z);
      this.sprites = sprites;
      this.lifetime = this.random.nextInt(50) + 100;
      this.gravity = 3.0E-6F;
      this.xd = u;
      this.yd = v;
      this.zd = w;
      this.lifetime = (int)(8.0 / (level.random.nextFloat() * 0.8 + 0.2));
      this.lifetime = Math.max(this.lifetime, 1);
      this.setSpriteFromAge(sprites);
      this.setAlpha(0.9F);
      int color = 16758222;
      float r = (color >> 16 & 0xFF) / 255.0F;
      float g = (color >> 8 & 0xFF) / 255.0F;
      float b = (color & 0xFF) / 255.0F;
      this.setColor(r, g, b);
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.sprites);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double u, double v, double w) {
         return new WhistlecaneSmokeParticle(level, x, y, z, u, v, w, this.sprites);
      }
   }
}
