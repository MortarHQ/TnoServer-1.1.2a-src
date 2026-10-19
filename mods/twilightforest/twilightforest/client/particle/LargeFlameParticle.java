package twilightforest.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class LargeFlameParticle extends TextureSheetParticle {
   private final float flameScale;

   public LargeFlameParticle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz) {
      super(level, x, y, z, vx, vy, vz);
      this.xd = this.xd * 0.01 + vx;
      this.yd = this.yd * 0.01 + vy;
      this.zd = this.zd * 0.01 + vz;
      this.quadSize *= 5.0F;
      this.flameScale = this.quadSize;
      this.rCol = this.gCol = this.bCol = 1.0F;
      this.lifetime = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
      this.hasPhysics = false;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public float getQuadSize(float partialTicks) {
      float relativeAge = (this.age + partialTicks) / this.lifetime;
      return this.flameScale * (1.0F - relativeAge * relativeAge * 0.5F);
   }

   public int getLightColor(float partialTicks) {
      float var2 = (this.age + partialTicks) / this.lifetime;
      if (var2 < 0.0F) {
         var2 = 0.0F;
      }

      if (var2 > 1.0F) {
         var2 = 1.0F;
      }

      int var3 = super.getLightColor(partialTicks);
      int var4 = var3 & 0xFF;
      int var5 = var3 >> 16 & 0xFF;
      var4 += (int)(var2 * 15.0F * 16.0F);
      if (var4 > 240) {
         var4 = 240;
      }

      return var4 | var5 << 16;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      }

      this.yd += 0.004;
      this.move(this.xd, this.yd, this.zd);
      this.xd *= 0.96;
      this.yd *= 0.96;
      this.zd *= 0.96;
      if (this.onGround) {
         this.xd *= 0.7;
         this.zd *= 0.7;
      }
   }

   public record Factory(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         LargeFlameParticle particle = new LargeFlameParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
         particle.pickSprite(this.sprite);
         return particle;
      }
   }
}
