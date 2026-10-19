package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;

public class JukeboxUpgradeNoteParticle extends TextureSheetParticle {
   protected JukeboxUpgradeNoteParticle(ClientLevel level, double x, double y, double z) {
      super(level, x, y, z, 0.0, 0.0, 0.0);
      this.xd *= 0.01F;
      this.yd *= 0.05F;
      this.zd *= 0.01F;
      this.yd += 0.01;
      double color = level.getRandom().nextDouble();
      this.rCol = Math.max(0.0F, Mth.sin(((float)color + 0.0F) * (float) (Math.PI * 2)) * 0.65F + 0.35F);
      this.gCol = Math.max(0.0F, Mth.sin(((float)color + 0.33333334F) * (float) (Math.PI * 2)) * 0.65F + 0.35F);
      this.bCol = Math.max(0.0F, Mth.sin(((float)color + 0.6666667F) * (float) (Math.PI * 2)) * 0.65F + 0.35F);
      this.quadSize *= 1.5F;
      this.lifetime = 20;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public float getQuadSize(float scaleFactor) {
      return this.quadSize * Mth.clamp((this.age + scaleFactor) / this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.move(this.xd, this.yd, this.zd);
         if (this.y == this.yo) {
            this.xd *= 1.1;
            this.zd *= 1.1;
         }

         if (this.onGround) {
            this.xd *= 0.7F;
            this.zd *= 0.7F;
         }
      }
   }

   public static class Factory implements ParticleProvider<JukeboxUpgradeNoteParticleData> {
      private final SpriteSet spriteSet;

      public Factory(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      @Nullable
      public Particle createParticle(
         JukeboxUpgradeNoteParticleData type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         JukeboxUpgradeNoteParticle particle = new JukeboxUpgradeNoteParticle(level, x, y, z);
         particle.pickSprite(this.spriteSet);
         return particle;
      }
   }
}
