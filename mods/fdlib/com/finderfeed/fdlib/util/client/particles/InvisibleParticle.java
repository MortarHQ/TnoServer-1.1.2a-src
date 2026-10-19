package com.finderfeed.fdlib.util.client.particles;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class InvisibleParticle extends TextureSheetParticle {
   protected InvisibleParticle(ClientLevel p_i232448_1_, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_, double x, double y, double z) {
      super(p_i232448_1_, p_i232448_2_, p_i232448_4_, p_i232448_6_, x, y, z);
      this.rCol = 255.0F;
      this.gCol = 255.0F;
      this.bCol = 255.0F;
      this.xd = x;
      this.yd = y;
      this.zd = z;
      this.x = p_i232448_2_;
      this.y = p_i232448_4_;
      this.z = p_i232448_6_;
      this.lifetime = 0;
      this.quadSize = 1.0F;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.NO_RENDER;
   }

   public void tick() {
      super.tick();
      if (this.lifetime-- <= 0) {
         this.remove();
      }
   }

   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSetl;

      public Factory(SpriteSet sprite) {
         this.spriteSetl = sprite;
      }

      @Nullable
      public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xv, double yv, double zv) {
         InvisibleParticle particle = new InvisibleParticle(world, x, y, z, xv, yv, zv);
         particle.setColor(1.0F, 1.0F, 1.0F);
         particle.pickSprite(this.spriteSetl);
         return particle;
      }
   }
}
