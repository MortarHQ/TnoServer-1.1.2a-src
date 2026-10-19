package net.jadenxgamer.netherexp.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedHazeParticle extends TextureSheetParticle {
   private final float rotationSpeed;

   RedHazeParticle(ClientLevel level, double x, double y, double z) {
      super(level, x, y, z);
      this.quadSize = 3.4F;
      this.setSize(0.25F, 1.0F);
      this.lifetime = 360;
      this.hasPhysics = true;
      this.rotationSpeed = 0.005F;
      this.roll = (float)Math.random() * (float) (Math.PI * 2);
   }

   protected int getLightColor(float f) {
      return 15728880;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ < this.lifetime && !(this.alpha <= 0.0F)) {
         if (this.age <= this.lifetime / 4 && this.alpha < 0.5F) {
            this.alpha += 0.015F;
         }

         if (this.age >= this.lifetime / 4 && this.alpha > 0.01F) {
            this.alpha -= 0.005F;
         }

         this.oRoll = this.roll;
         this.roll = this.roll + (float) Math.PI * this.rotationSpeed;
         this.move(this.xd, this.yd, this.zd);
         this.yd -= 0.003F;
         this.yd = Math.max(this.yd, -0.03F);
      } else {
         this.remove();
      }
   }

   @NotNull
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Factory(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      @Nullable
      public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
         RedHazeParticle redHazeParticle = new RedHazeParticle(clientLevel, d, e, f);
         redHazeParticle.pickSprite(this.spriteSet);
         redHazeParticle.setAlpha(0.001F);
         return redHazeParticle;
      }
   }
}
