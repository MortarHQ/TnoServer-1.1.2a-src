package net.jadenxgamer.netherexp.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FallingParticle extends TextureSheetParticle {
   private final float rotationSpeed;
   private final SpriteSet spriteSet;

   FallingParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
      super(level, x, y, z);
      this.spriteSet = spriteSet;
      this.rCol = 1.0F;
      this.gCol = 1.0F;
      this.bCol = 1.0F;
      this.quadSize *= 0.67499995F;
      int i = (int)(32.0 / (Math.random() * 0.8 + 0.2));
      this.lifetime = (int)Math.max(i * 0.9F, 1.0F);
      this.setSpriteFromAge(spriteSet);
      this.rotationSpeed = ((float)Math.random() - 0.5F) * 0.1F;
      this.roll = (float)Math.random() * (float) (Math.PI * 2);
      this.hasPhysics = true;
   }

   protected int getLightColor(float f) {
      return 15728880;
   }

   public float getQuadSize(float tickDelta) {
      return this.quadSize * Mth.clamp((this.age + tickDelta) / this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.setSpriteFromAge(this.spriteSet);
         this.oRoll = this.roll;
         this.roll = this.roll + (float) Math.PI * this.rotationSpeed * 2.0F;
         if (this.onGround) {
            this.roll = 0.0F;
            this.oRoll = 0.0F;
         }

         this.move(this.xd, this.yd, this.zd);
         this.yd -= 0.003F;
         this.yd = Math.max(this.yd, -0.14F);
      }
   }

   @NotNull
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Factory(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      @Nullable
      public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
         return new FallingParticle(clientLevel, d, e, f, this.spriteSet);
      }
   }
}
