package net.regions_unexplored.client.particle.spore;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ColorParticleOption;
import org.jetbrains.annotations.Nullable;

public class FallingSporeParticle extends TextureSheetParticle {
   private int ticksOnGround;

   protected FallingSporeParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
      super(level, x, y, z);
      this.pickSprite(sprites);
      this.setSize(0.01F, 0.01F);
      this.gravity = 0.007F;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public int getLightColor(float color) {
      return 240;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (!this.removed) {
         this.yd = this.yd - this.gravity;
         if (this.onGround) {
            this.ticksOnGround++;
            if (this.ticksOnGround == 1) {
               this.move(0.0, 0.02F, 0.0);
               this.onGround = true;
            } else if (this.ticksOnGround >= 5) {
               this.alpha = 1.0F - (this.ticksOnGround - 5.0F) / 5.0F;
               if (this.ticksOnGround >= 10) {
                  this.remove();
               }
            }
         } else {
            this.ticksOnGround = 0;
            this.move(this.xd, this.yd, this.zd);
         }

         if (!this.removed) {
            this.xd *= 0.95F;
            this.yd *= 0.95F;
            this.zd *= 0.95F;
         }
      }
   }

   public static class Provider implements ParticleProvider<ColorParticleOption> {
      private final SpriteSet sprites;

      public Provider(SpriteSet sprites) {
         this.sprites = sprites;
      }

      @Nullable
      public Particle createParticle(ColorParticleOption option, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux) {
         FallingSporeParticle particle = new FallingSporeParticle(level, x, y, z, this.sprites);
         particle.setColor(option.getRed(), option.getGreen(), option.getBlue());
         return particle;
      }
   }
}
